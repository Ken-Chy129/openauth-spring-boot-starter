package cn.ken.thridauth.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * 缓存配置类
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/3/17 17:35
 */
@Getter
@Setter
public class CacheProperties {

    /**
     * 缓存类型
     */
    private CacheType type = CacheType.DEFAULT;

    /**
     * 缓存前缀，目前只对redis缓存生效
     */
    private String prefix = "thirdauth:state:";

    /**
     * 超时时长，目前只对redis缓存生效，默认3分钟
     */
    private long timeout = 3;

    /**
     * 缓存类型
     */
    @Getter
    @ToString
    public enum CacheType {

        /**
         * 使用JustAuth内置的缓存
         */
        DEFAULT,

        /**
         * 使用Redis缓存
         */
        REDIS,

        /**
         * 自定义缓存
         */
        CUSTOM
    }
}
