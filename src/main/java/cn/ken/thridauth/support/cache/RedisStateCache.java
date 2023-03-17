package cn.ken.thridauth.support.cache;

import cn.ken.thirdauth.cache.AuthStateCache;
import cn.ken.thridauth.autoconfigure.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * state的Redis缓存解决方案
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/3/17 19:53
 */
public class RedisStateCache implements AuthStateCache {

    private final RedisTemplate<String, String> redisTemplate;

    private final CacheProperties cacheProperties;

    public RedisStateCache(RedisTemplate<String, String> redisTemplate, CacheProperties cacheProperties) {
        this.redisTemplate = redisTemplate;
        this.cacheProperties = cacheProperties;
    }

    @Override
    public void set(String key, String value) {
        this.set(key, value, cacheProperties.getTimeout());
    }

    @Override
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(cacheProperties.getPrefix() + key, value, timeout, TimeUnit.MINUTES);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(cacheProperties.getPrefix() + key);
    }

    @Override
    public boolean containsKey(String key) {
        return redisTemplate.opsForValue().get(cacheProperties.getPrefix() + key) != null;
    }
}
