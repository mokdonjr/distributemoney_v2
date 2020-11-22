package com.seungchan.distributemoney_v2.common.util;

import com.seungchan.distributemoney_v2.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class LogUtil {
    static Logger loggerStatic = LoggerFactory.getLogger(LogUtil.class);

    public interface SimpleFunction {
        public void run() throws BusinessException;
    }

    public static StopWatch runWithStopWatch(String commonPrefix, String startMessage, String endMessage, SimpleFunction function) throws BusinessException {
        StopWatch s = new StopWatch();
        s.start();
        var functionId = function.toString();
        loggerStatic.info("[STOPWATCH][START] {} {}", NullUtil.getOrDefault(commonPrefix), NullUtil.getOrDefault(startMessage, "start-" + functionId));
        function.run();
        s.stop();
        loggerStatic.info("[STOPWATCH][END]{} duration : {} / {}", NullUtil.getOrDefault(commonPrefix), s.getTotalTimeMillis() + " (ms)", NullUtil.getOrDefault(endMessage, "end-" + functionId));
        return s;
    }

    public static StopWatch runWithStopWatch(String commonPrefix, SimpleFunction function) throws BusinessException {
        return runWithStopWatch(commonPrefix, null, null, function);
    }

    public static StopWatch runWithStopWatch(SimpleFunction function) throws BusinessException {
        return runWithStopWatch(null, null, null, function);
    }
}
