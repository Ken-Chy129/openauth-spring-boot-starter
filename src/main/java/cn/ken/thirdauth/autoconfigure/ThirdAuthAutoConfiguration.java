package cn.ken.thirdauth.autoconfigure;

import cn.ken.thirdauth.cache.AuthStateCache;
import cn.ken.thirdauth.ThirdAuthRequestFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <pre>
 * ThirdAuth自动装配类
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/3/17 17:13
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ThirdAuthProperties.class)
public class ThirdAuthAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "thirdauth", value = "enabled", havingValue = "true", matchIfMissing = true)
    public ThirdAuthRequestFactory authRequestFactory(ThirdAuthProperties properties, AuthStateCache authStateCache) {
        return new ThirdAuthRequestFactory(properties, authStateCache);
    }

    @Configuration
    @Import({ThirdAuthStateCacheConfiguration.Default.class, ThirdAuthStateCacheConfiguration.Redis.class, ThirdAuthStateCacheConfiguration.Custom.class})
    protected static class AuthStateCacheAutoConfiguration {

    }
}
