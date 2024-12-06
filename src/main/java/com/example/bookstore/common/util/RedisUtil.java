package com.example.bookstore.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author chaoluo
 */
@Slf4j
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置值
     *
     * @author jqlin
     */
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置值失败：" + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 设置值
     */
    public void set(String key, String value, Long expire) {
        redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);

    }

    /**
     * 获取值
     *
     * @author jqlin
     */
    public String get(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .map(String::valueOf).orElse(null);
    }


    /**
     * 判断key是否存在
     *
     * @author jqlin
     */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除key
     */
    public Long delete(String... keys) {
        return redisTemplate.delete(Arrays.asList(keys));
    }

    /**
     * 根据key 获取过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }


    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public Cursor scan(String match, int count){
        ScanOptions scanOptions = ScanOptions.scanOptions().match(match).count(count).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
        return (Cursor) stringRedisTemplate.executeWithStickyConnection((RedisCallback) redisConnection ->
                new ConvertingCursor<>(redisConnection.scan(scanOptions), redisSerializer::deserialize));
    }
}
