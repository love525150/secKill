package org.allen.seckill.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Zhou Zhengwen
 */
public class RedisUtils {

    public static final String SEPARATOR = ":";

    public static String keyGenarator(Object... objects) {
        return StringUtils.join(objects, SEPARATOR);
    }
}
