package cn.ken.thirdauth;

import cn.ken.thirdauth.cache.AuthStateCache;
import cn.ken.thirdauth.config.AuthPlatformConfig;
import cn.ken.thirdauth.enums.AuthExceptionCode;
import cn.ken.thirdauth.exception.AuthException;
import cn.ken.thirdauth.request.*;
import cn.ken.thirdauth.autoconfigure.ExtendProperties;
import cn.ken.thirdauth.autoconfigure.ThirdAuthProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * <pre>
 * 三方平台请求工厂
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/3/17 17:36
 */
@Slf4j
public class ThirdAuthRequestFactory {

    private final ThirdAuthProperties properties;

    private final AuthStateCache cache;

    private final List<String> platformNames;

    private final Map<String, ExtendProperties.ExtendRequestConfig> extendRequestConfigMap;

    public ThirdAuthRequestFactory(ThirdAuthProperties properties, AuthStateCache cache) {
        this.properties = properties;
        this.cache = cache;
        this.platformNames = new ArrayList<>(properties.getType().keySet());
        this.extendRequestConfigMap = new HashMap<>(6);
        ExtendProperties extend = properties.getExtend();
        if (null != extend) {
            Map<String, ExtendProperties.ExtendRequestConfig> config = extend.getConfig();
            config.forEach((k, v) -> this.extendRequestConfigMap.put(k.toUpperCase(), v));
            Class urlsClass = extend.getUrlsClass();
            List<String> names = getNames(urlsClass);
            List<String> extendList = this.extendRequestConfigMap
                    .keySet()
                    .stream()
                    .filter(names::contains)
                    .toList();
            this.platformNames.addAll(extendList);
        }
    }

    /**
     * 返回当前支持的三方平台名称
     *
     * @return 支持的平台名称列表
     */
    public List<String> platformNames() {
        return this.platformNames;
    }

    private List<String> getNames(Class<? extends Enum<?>> clazz) {
        Enum<?>[] enums = clazz.getEnumConstants();
        if (null == enums) {
            return Collections.emptyList();
        } else {
            List<String> list = new ArrayList<>(enums.length);
            for (Enum<?> e : enums) {
                list.add(e.name());
            }
            return list;
        }
    }

    /**
     * 返回AuthRequest对象
     *
     * @param platform {@link AuthRequest}
     * @return {@link AuthRequest}
     */
    public AuthRequest get(String platform) {
        if (platform == null || platform.isBlank()) {
            throw new AuthException(AuthExceptionCode.NO_AUTH_SOURCE);
        }

        AuthRequest authRequest = getDefaultRequest(platform);

        // 如果获取不到则尝试取自定义的
        if (authRequest == null) {
            authRequest = getExtendRequest(properties.getExtend().getUrlsClass(), platform);
        }

        if (authRequest == null) {
            throw new AuthException(AuthExceptionCode.UNSUPPORTED);
        }

        return authRequest;
    }

    /**
     * 获取自定义的 request
     *
     * @param clazz  三方平台请求类 {@link DefaultAuthRequest}
     * @param source 三方平台名称
     * @return {@link AuthRequest}
     */
    private AuthRequest getExtendRequest(Class clazz, String source) {

        source = source.toUpperCase();

        if (!platformNames.contains(source)) {
            return null;
        }

        ExtendProperties.ExtendRequestConfig extendRequestConfig = this.extendRequestConfigMap.get(source);

        if (extendRequestConfig != null) {

            Class<? extends AuthRequest> requestClass = extendRequestConfig.getRequestClass();

            if (requestClass != null) {
                // 反射获取 Request 对象，所以必须实现 2 个参数的构造方法
                try {
                    return requestClass.getDeclaredConstructor(requestClass, ExtendProperties.ExtendRequestConfig.class, AuthStateCache.class).newInstance(clazz, extendRequestConfig, cache);
                } catch (Exception e) {
                    log.error(clazz.getName() + "需要实现两个参数(ExtendRequestConfig, AuthStateCache)的构造方法");
                }
            }
        }

        return null;
    }


    /**
     * 获取默认的 Request
     *
     * @param source {@link AuthRequest}
     * @return {@link AuthRequest}
     */
    private AuthRequest getDefaultRequest(String source) {
        source = source.toUpperCase();

        if (!platformNames.contains(source)) {
            return null;
        }

        AuthPlatformConfig config = properties.getType().get(source);
        // 对应平台配置信息不存在则直接返回空
        if (config == null) {
            return null;
        }

        return switch (source) {
            case "GITHUB" -> new GithubAuthRequest(config, cache);
            case "GITEE" -> new GiteeAuthRequest(config, cache);
            case "QQ" -> new QqAuthRequest(config, cache);
            default -> null;
        };
    }

}
