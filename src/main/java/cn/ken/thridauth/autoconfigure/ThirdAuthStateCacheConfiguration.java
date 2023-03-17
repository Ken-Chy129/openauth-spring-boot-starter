package cn.ken.thridauth.autoconfigure;

import cn.ken.thirdauth.cache.AuthStateCache;
import cn.ken.thirdauth.cache.DefaultAuthStateCache;
import cn.ken.thridauth.support.cache.RedisStateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <pre>
 * 缓存装配类
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/3/17 18:07
 */
@Slf4j
public class ThirdAuthStateCacheConfiguration {

    @ConditionalOnMissingBean(AuthStateCache.class)
    @ConditionalOnProperty(name = "thirdauth.cache.type", havingValue = "default", matchIfMissing = true)
    static class Default {

        static {
            log.debug("ThirdAuth 使用内存存储 state 值");
        }

        @Bean
        public AuthStateCache authStateCache() {
            return DefaultAuthStateCache.INSTANCE;
        }
    }

    @ConditionalOnMissingBean(AuthStateCache.class)
    @ConditionalOnClass(RedisTemplate.class)
    @AutoConfigureAfter(RedisConfiguration.class)
    @ConditionalOnProperty(name = "thirdauth.cache.type", havingValue = "redis", matchIfMissing = true)
    static class Redis {

        static {
            log.debug("ThirdAuth 使用 Redis 存储 state 值");
        }

        @Bean
        public AuthStateCache authStateCache(RedisTemplate<String, String> redisTemplate, ThirdAuthProperties thirdAuthProperties) {
            return new RedisStateCache(redisTemplate, thirdAuthProperties.getCache());
        }

    }

    @ConditionalOnMissingBean(AuthStateCache.class)
    @ConditionalOnProperty(name = "thirdauth.cache.type", havingValue = "custom", matchIfMissing = true)
    static class Custom {

        static {
            log.debug("ThirdAuth 使用自定义缓存存储 state 值");
        }

        @Bean
        public AuthStateCache authStateCache() {
            log.error("自定义缓存器不存在，请自行实现cn.ken.thirdauth.cache.AuthStateCache接口或选择其他缓存策略");
            throw new RuntimeException();
        }

    }
}
