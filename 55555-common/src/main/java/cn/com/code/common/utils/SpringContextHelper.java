package cn.com.code.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * 获取注入的bean
 *
 * @author yangxp
 * @version 2018-08-28
 */
@Order(1)
@Component
public class SpringContextHelper implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * Nacos 配置中心服务
     */
    private static NacosConfigService nacosConfigService;

    /**
     * 当前工程名
     */
    private static String currApplicationName;

    /**
     * 重写并初始化上下文
     *
     * @param applicationContext spring 上下文
     * @throws BeansException bean 异常
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContextHelper.applicationContext = applicationContext;
    }

    @Autowired(required = false)
    public void setNacosConfigService(NacosConfigService nacosConfigService) {
        SpringContextHelper.nacosConfigService = nacosConfigService;
    }

    @Value("${spring.application.name}")
    public void setCurrApplicationName(String currApplicationName) {
        SpringContextHelper.currApplicationName = currApplicationName;
    }

    /**
     * 通过类获取
     *
     * @param clazz 注入的类
     * @param <T>   返回类型
     * @return 返回这个bean
     * @throws BeansException bean异常
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过名字获取
     *
     * @param name 名字
     * @param <T>  返回类型
     * @return 返回这个bean
     * @throws BeansException bean异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从配置文件获取
     *
     * @param key 属性名
     * @return
     */
    public static String getApplicationProValue(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 获取统一配置资源中心的值
     *
     * @param key 属性名
     * @return
     */
    public static String getStringProValue(String key) {
        String proValue = getStringProValue(currApplicationName, key);
        if (proValue != null) {
            return proValue;
        }
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 获取统一配置资源中心的值
     *
     * @param applicationName 应用名
     * @param key             属性名
     * @return
     */
    public static String getStringProValue(String applicationName, String key) {
        if (nacosConfigService != null) {
            String propertie = nacosConfigService.getPropertie(applicationName, key);
            if (propertie != null && !propertie.isEmpty()) {
                return propertie;
            }
        }
        return null;
    }

    /**
     * 获取统一配置资源中心的值（Json 扩展）
     *
     * @param applicationName 应用名
     * @param key             属性名
     * @return
     */
    public static String getStringProValueForJson(String applicationName, String key) {
        if (nacosConfigService != null) {
            String propertie = nacosConfigService.getJsonPropertie(applicationName, key);
            if (propertie != null && !propertie.isEmpty()) {
                return propertie;
            }
        }
        return null;
    }

    /**
     * 获取统一配置资源中心的值
     *
     * @param key 属性名
     * @return
     */
    public static Integer getIntegerProValue(String key) {
        return Integer.valueOf(getStringProValue(key));
    }

    /**
     * 获取统一配置资源中心的值
     *
     * @param key 属性名
     * @return
     */
    public static Boolean getBooleanProValue(String key) {
        return Boolean.valueOf(getStringProValue(key));
    }

    /**
     * 获取统一配置资源中心的值
     *
     * @param key          属性名
     * @param defaultValue 默认值
     * @return
     */
    public static Boolean getBooleanProValue(String key, Boolean defaultValue) {
        String proValue = getStringProValue(key);
        if (proValue == null || proValue.isEmpty()) {
            return defaultValue;
        }
        return Boolean.valueOf(proValue);
    }

    /**
     * 获取统一配置资源中心的值
     *
     * @param key 属性名
     * @return
     */
    public static Long getLongProValue(String key) {
        return Long.valueOf(getStringProValue(key));
    }

    /**
     * 获取统一配置资源中心的值
     *
     * @param key 属性名
     * @return
     */
    public static Double getDoubleProValue(String key) {
        return Double.valueOf(getStringProValue(key));
    }

    /**
     * 获取统一配置资源中心的值
     *
     * @param key          属性名
     * @param defaultValue 默认值
     * @return
     */
    public static String getStringProForDefaultValue(String key, String defaultValue) {
        String proValue = getStringProValue(key);
        if (proValue == null || proValue.isEmpty()) {
            return defaultValue;
        }
        return proValue;
    }

    /**
     * 获取统一配置资源中心的值(Common 公共配置)
     *
     * @param key 属性名
     * @return
     */
    public static String getStringInCommon(String key) {
        return nacosConfigService.getCommonPropertie(key);
    }


}
