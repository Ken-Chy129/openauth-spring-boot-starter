package cn.ken.thirdauth.autoconfigure;

import cn.ken.thirdauth.config.AuthPlatformConfig;
import cn.ken.thirdauth.config.AuthUrls;
import cn.ken.thirdauth.request.DefaultAuthRequest;

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
public class ExtendProperties {

    private Class<? extends AuthUrls> urlsClass;

    private Map<String, ExtendRequestConfig> config = new HashMap<>();

    public static class ExtendRequestConfig extends AuthPlatformConfig {

        /**
         * 请求对应全路径
         */
        private Class<? extends DefaultAuthRequest> requestClass;

        public Class<? extends DefaultAuthRequest> getRequestClass() {
            return requestClass;
        }

        public void setRequestClass(Class<? extends DefaultAuthRequest> requestClass) {
            this.requestClass = requestClass;
        }
    }

    public Class<? extends AuthUrls> getUrlsClass() {
        return urlsClass;
    }

    public void setUrlsClass(Class<? extends AuthUrls> urlsClass) {
        this.urlsClass = urlsClass;
    }

    public Map<String, ExtendRequestConfig> getConfig() {
        return config;
    }

    public void setConfig(Map<String, ExtendRequestConfig> config) {
        this.config = config;
    }
}
