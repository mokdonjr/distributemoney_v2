package com.seungchan.distributemoney_v2.distribute.service;

import com.seungchan.distributemoney_v2.common.service.AbsObjectPersistenceServiceTest;
import com.seungchan.distributemoney_v2.common.service.IObjectPersistenceService;
import com.seungchan.distributemoney_v2.distribute.data.ObjectDataDistribute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ObjectServiceDistributeTest extends AbsObjectPersistenceServiceTest {

    @Autowired
    private ObjectServiceDistribute objectServiceDistribute;

    @Override
    protected IObjectPersistenceService getObjectPersistenceService() {
        return objectServiceDistribute;
    }

    @Override
    protected ObjectDataDistribute getDefaultObjectData() {
        var data = new ObjectDataDistribute();
        data.setRoomId(defaultRoomId);
        data.setUserId(defaultUserId);
        data.setToken(defaultToken);
        data.setSeqCount(2);
        data.setTotalValue(1000L);
        return data;
    }

    /**
     * 토큰이 유니크하게 발급되는지 검증
     */
    @Test
    public void testTokenUnique() {

        // 서로 다른지~
        Map<String, Integer> counter = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            String token = objectServiceDistribute.createToken();
            int count = counter.getOrDefault(token, 0);
            counter.put(token, count + 1);
        }

        for (var entry : counter.entrySet()) {
            if (entry.getValue() > 1)
                Assertions.fail("토큰이 중복됩니다. " + entry.getKey());
        }
    }

    private static Stream<Arguments> parametersForTestDistributeSeq() {
        return Stream.of(
                // 딱 떨어지는 케이스
                Arguments.of(1, 1L),
                Arguments.of(2, 2L),
                Arguments.of(3, 3L),
                Arguments.of(10, 10L),
                Arguments.of(2, 1000L),
                // 딱 안떨어지는 케이스
                Arguments.of(3, 10L),
                Arguments.of(6, 10L),
                Arguments.of(9, 10L)
        );
    }

    /**
     * N개로 분배된 뿌리기 총합이 원래 뿌리기 총 금액과 같은지 검증
     */
    @ParameterizedTest
    @MethodSource(value = "parametersForTestDistributeSeq")
    public void testDistributeSeq(int n, long totalValue) {

        List<Long> seqs = ObjectServiceDistribute.getDistributedValues(n, totalValue);
        Assertions.assertEquals(n, seqs.size());

        long sum = 0;
        for (int i = 0; i < seqs.size(); i++) {
            long value = seqs.get(i);
            logger.info("seq:({}/{}) value:{}", i, seqs.size(), value);
            Assertions.assertNotEquals(0, value);
            sum += value;
        }
        Assertions.assertEquals(totalValue, sum);
    }
}