package cn.ken.thirdauth.autoconfigure;

import cn.ken.thirdauth.config.AuthPlatformConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * ThirdAuth配置类
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/3/17 17:12
 */

@ConfigurationProperties(prefix = "thirdauth")
public class ThirdAuthProperties {

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 第三方平台配置
     */
    private Map<String, AuthPlatformConfig> type = new HashMap<>();

    /**
     * 自定义配置
     */
    @NestedConfigurationProperty
    private ExtendProperties extend;

    /**
     * 缓存配置
     */
    @NestedConfigurationProperty
    private CacheProperties cache = new CacheProperties();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Map<String, AuthPlatformConfig> getType() {
        return type;
    }

    public void setType(Map<String, AuthPlatformConfig> type) {
        this.type = type;
    }

    public ExtendProperties getExtend() {
        return extend;
    }

    public void setExtend(ExtendProperties extend) {
        this.extend = extend;
    }

    public CacheProperties getCache() {
        return cache;
    }

    public void setCache(CacheProperties cache) {
        this.cache = cache;
    }
}
