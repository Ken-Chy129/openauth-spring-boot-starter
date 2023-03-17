package cn.ken.thridauth.autoconfigure;

import cn.ken.thirdauth.config.AuthPlatformConfig;
import cn.ken.thirdauth.config.AuthUrls;
import cn.ken.thirdauth.request.DefaultAuthRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 自定义扩展三方平台配置类
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/3/17 17:35
 */
@Getter
@Setter
public class ExtendProperties {

    private Class<? extends AuthUrls> urlsClass;

    private Map<String, ExtendRequestConfig> config = new HashMap<>();

    @Getter
    @Setter
    public static class ExtendRequestConfig extends AuthPlatformConfig {

        /**
         * 请求对应全路径
         */
        private Class<? extends DefaultAuthRequest> requestClass;
    }
}
