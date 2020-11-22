package com.seungchan.distributemoney_v2;

import com.seungchan.distributemoney_v2.common.config.BusinessConfig;
import com.seungchan.distributemoney_v2.common.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@SpringBootTest
public class TestBase extends TestBaseWithoutContext {

    @BeforeEach
    public void beforeEacheTestBase() {
        BusinessConfig.setIsTest(true);
        // 시간 테스트 초기화
        DateUtil.setTestDate(null);
        // 테스트 시간 조회
        logger.info("CURRENT_DATETIME: {} / {}", DateUtil.getCurrentDate(), new Date());
    }

}
