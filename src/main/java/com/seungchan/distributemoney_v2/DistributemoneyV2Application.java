package com.seungchan.distributemoney_v2;

import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import com.seungchan.distributemoney_v2.common.util.LogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributemoneyV2Application {

	public static void main(String[] args) throws BusinessException {
		LogUtil.runWithStopWatch("[어플리케이션 부팅]", () -> {
			SpringApplication.run(DistributemoneyV2Application.class, args);
		});
	}

}
