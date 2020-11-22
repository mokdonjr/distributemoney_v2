# 머니뿌리기

유저가 일정 금액 X원 만큼 채팅방에 최대 N명이 수령할 수 있게 뿌리면,
채팅방에 참여중인 다른 유저들은 최대 10분간 최대 N명이 선착순으로 랜덤한 금액을 수령한다.

### Tech

이 프로젝트는 아래 language/middleware/framework 로 구성되어있습니다.

* [Java11] - 머니뿌리기 비지니스 로직 구현에 활용되었습니다.
* [SpringBoot] - 머니뿌리기 비지니스 오브젝트 관리 및 데이터베이스 연동을 위한 프레임워크로 활용되었습니다.
* [mysql8] - 동시성 제어 및 비지니스 데이터 관리에 활용되었습니다.
* [shell] - 빌드 자동화에 활용되었습니다.

### Installation

java11 과 docker 가 필요합니다.
```sh
$ git clone https://github.com/mokdonjr/distributemoney_v2.git
$ cd distributemoney_v2/script
$ sh app_run.sh
```

### Uninstall

mysql 을 활용하기 위해 생성된 my.cnf 설정 파일 volume, 데이터 volume 및 mysql container 를 제거합니다.

```sh
$ sh app_remove.sh
$ cd ..
$ rm -r distributemoney_v2
```

### Scenario

즉시 실행 가능하도록 curl 명령어가 작성되어있습니다.
위에서 아래로 순차적으로 테스트가 진행되어야 합니다. 먼저, 한 방에 (room_id=1) 머니를 뿌리고 수령할 최소한의 유저를 (user_id=1, user_id=2) 생성하고 방에 참가시켜야합니다. (테스트에 필요하다면 더 많은 유저가 생성될 수 있습니다.)

| 요약 | 명령 |
| ------ | ------ |
| 뿌리기 유저 (user_id=1) 생성 | curl -X POST "http://localhost:8080/distributemoney_2/user" -H "accept: application/json;charset=utf-8" -H "X-ROOM-ID: 1" -H "X-USER-ID: 1" |
| 수령할 유저 (user_id=2) 생성 | curl -X POST "http://localhost:8080/distributemoney_2/user" -H "accept: application/json;charset=utf-8" -H "X-ROOM-ID: 1" -H "X-USER-ID: 2" |
| 뿌리기 (user_id=1) | curl -X POST "http://localhost:8080/distributemoney_2/distribute?receiveUserCount=1&totalValue=1000" -H "accept: application/json;charset=utf-8" -H "X-ROOM-ID: 1" -H "X-USER-ID: 1" |
| 수령하기 (user_id=2) | curl -X POST "http://localhost:8080/distributemoney/receive?token=asdf" -H "accept: application/json;charset=utf-8" -H "X-ROOM-ID: 1" -H "X-USER-ID: 2" |
| 조회하기 (user_id=1) | curl -X GET "http://localhost:8080/distributemoney/distribute/status?token=asdf" -H "accept: application/json;charset=utf-8" -H "X-ROOM-ID: 1" -H "X-USER-ID: 1" |


### 주요 문제 해결 전략

  - 뿌리기 수행시 3자리 token
    - 2^(16 + 16 + 16) 가지의 경우의수를 예측 가능하지 말아야 하기 때문에 compact 하게 사용하지 않고 각 자리수 마다 random 처리
    - random 처리했기 때문에 여러번 뿌리기 수행시 경합이 될 수 있으나, 각 방에 참여한 인원들만 수령가능하기 때문에 room_id 별로 격리되어 있기 때문에 뿌리기 데이터는 방별로 유니크한지 체크
    - 10분간 수령가능하기 때문에 기존 발급된 token 은 정리해 새로운 뿌리기로 인한 token 발급시 경합을 최소화
    - 그럼에도 불구하고 혹시라도 겹칠 경우 뿌리기 트랜잭션 최대 3회 재시도 처리
  - 뿌리기 수령 유효기간
    - 뿌리기 요청후 10분 안에 수령 요청되지 않은 분배건이 있는 경우 뿌린 유저에게 되돌려줌
    - 배치 스케쥴 checkDistributeEndSchedule() 구현
  - 뿌리기 조회 유효기간
    - 뿌리기 요청후 7일간 상태 조회가 가능하고 이후에 불가능, 배치 스케쥴 스레드 없이 조회 요청시 시간 비교해 처리
  - 수령할 다수의 유저간 선착순 경합 동시성 제어
    - 여러 서버의 여러 인스턴스에서 수행가능하기 때문에 DB를 이용한 동시성 제어가 필요
    - 뿌리기 요청시 등록된 분배건 row 들을 read lock 으로 동시성 제어
  - 수령할 다수의 유저간 선착순 체크 퍼포먼스 개선
    - DB 를 이용한 read lock 으로 동시성을 보장 가능하나, 여러 유저가 지속적으로 경합시 유저 경험이 좋지 못할 수 있음 (/receive)
    - 수령 요청을 예약받아 동기화된 스케쥴러가 차례대로 선착순을 판별해 처리하는 효율적인 구조로 재설계 (/receive/reserve)
      - 여러 서버의 여러 인스턴스간 스케쥴 경합을 피하기 위해 mysql8 의 새 기능인 SKIP LOCKED 를 활용
    - 다만, 수령 요청 예약 방식은 수령 성공 및 실패 여부를 클라이언트APP 에 알릴 방법이 필요
      - socket 이 구현되어있다면, 노티 송신 (배치 스케쥴 checkReserveReceiveSchedule())
      - 그렇지 않다면, 클라이언트 수령 요청시 그리고 재접속시 polling 방식으로 낚아채기 (/receive/check)
    - 이러한 스케쥴링 방식은 부하가 심할시 큐가 밀릴 수 있음

### 주요 테스트 케이스
  - 뿌리기 요청시 요청자 잔액 및 분배 검증 : ApiServiceTest.distribute()
  - 뿌리기 수령 가능시간 10분인가 : ApiServiceTest.testReceiveAfter10Minutes(), ApiServiceTest.testReceiveBefore10Minutes()
  - 뿌리기 조회 상태값 검증 : ApiServiceTest.testDistributeStatus()
  - 뿌리기 조회 가능시간 7일인가 : ApiServiceTest.testDistributeStatusAfter7Days(), ApiServiceTest.testDistributeStatusBefore7Days()
  - 수령 요청 성공시 수령자 잔액 체크 : ApiServiceTest.testReceive()
  - 수령 요청 경합시 선착순을 넘어간 유저들 수령 실패 검증 : ApiServiceTest.testReceiveMoreThanAvailable()
  - 뿌리기 기능 단위 테스트
    - 토큰 단순 여러번 실행했을때 유니크한가 : ObjectServiceDistributeTest.testTokenUnique()
    - X원 만큼 뿌리기 요청시 예약된 분배건 N개의 총 금액 합이 일치하는지 parameterized 테스트 : ObjectServiceDistributeTest.testDistributeSeq(int, long)
  - 유저 계좌 기능 단위 테스트
    - 입금 결과 검증 : ObjectDataAccountTest.addBalancePositive(), ObjectDataUserTest.addBalancePositive()
    - 출금 결과 검증 : ObjectDataAccountTest.addBalanceNegative(), ObjectDataUserTest.addBalanceNegative()
    - 입금 누적 결과 검증 : ObjectDataAccountTest.addBalanceCumulative(), ObjectDataUserTest.addBalanceCumulative()
    - 입출금 가능 여부 parameterized 테스트 : ObjectDataAccountTest.testIsAvailableAddBalance(long, boolean), ObjectDataUserTest.testIsAvailableAddBalance(long, boolean)
  - 스케쥴링 방식의 선착순 수령 예약 처리기 테스트 : ApiServiceTest.reserveReceive()