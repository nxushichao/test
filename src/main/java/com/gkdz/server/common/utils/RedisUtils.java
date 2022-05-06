package com.gkdz.server.common.utils;

import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Mark
 */
@Component
public class RedisUtils {
    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;
    private static final Gson gson = new Gson();
    //  @Autowired private RedisTemplate<String, Object> thisRedisTemplate;

    public static String getSerialNumber(RedisTemplate redisTemplate, String redisKeypre) {
        String yyyyMMdd = DateUtil.format(new Date(), "yyyyMMdd");
        Long increment = redisTemplate.opsForValue().increment(redisKeypre + ":" + yyyyMMdd);
        redisTemplate.expire(redisKeypre + ":" + yyyyMMdd, 1, TimeUnit.DAYS);
        return yyyyMMdd + "" + increment;
    }

}
