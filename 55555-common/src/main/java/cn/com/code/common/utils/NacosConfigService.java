package cn.com.code.common.utils;


import cn.com.code.base.bean.CommonException;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * Nacos 配置中心服务
 *
 * @author CenXiaolou
 * @date 2019-08-28
 */
@Component
@ConditionalOnClass(ConfigService.class)
@ConditionalOnProperty("spring.cloud.nacos.config.server-addr")
public class NacosConfigService {

    /**
     * 默认分组
     */
    final static String GROUP = "DEFAULT_GROUP";

    /**
     * 共用配置 common
     */
    final static String APPLICATION_NAME_COMMON = "common";

    /**
     * 超时时间(毫秒)
     */
    final static long TIMEOUT = 60 * 1000L;

    @Value("${spring.profiles.active:}")
    private String active;

    @Value("${spring.cloud.nacos.config.file-extension}")
    private String fileExtension;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    private ConfigService configService;

    /**
     * 获取 nacos 配置服务
     *
     * @return
     */
    public ConfigService createConfigService() {
        try {
            if (configService == null) {
                Properties properties = new Properties();
                properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
                configService = NacosFactory.createConfigService(properties);
            }
            return configService;
        } catch (NacosException e) {
            throw new CommonException(e);
        }
    }

    /**
     * 获取 nacos 配置
     *
     * @param applicationName
     * @param Key
     * @return
     */
    public String getPropertie(String applicationName, String Key) {
        try {
            String dataId;
            if (active.isEmpty()) {
                dataId = applicationName + "." + fileExtension;
            } else {
                dataId = applicationName + "-" + active + "." + fileExtension;
            }
            String config = createConfigService().getConfig(dataId, GROUP, TIMEOUT);
            if (config == null) {
                return null;
            }
            Properties properties = new Properties();
            properties.load(new StringReader(config));
            Object value = properties.get(Key);
            if (value == null) {
                return null;
            }
            return value.toString();
        } catch (NacosException | IOException e) {
            throw new CommonException(e);
        }
    }

    /**
     * 获取 nacos 配置（Json 扩展）
     *
     * @param applicationName
     * @param Key
     * @return
     */
    public String getJsonPropertie(String applicationName, String Key) {
        try {
            String dataId;
            if (active.isEmpty()) {
                dataId = applicationName + ".json";
            } else {
                dataId = applicationName + "-" + active + ".json";
            }
            String config = createConfigService().getConfig(dataId, GROUP, TIMEOUT);
            if (config == null || config.isEmpty()) {
                return null;
            }
            return JSON.parseObject(config).getString(Key);
        } catch (NacosException e) {
            throw new CommonException(e);
        }
    }

    /**
     * 获取公共 nacos 配置
     *
     * @param Key
     * @return
     */
    public String getCommonPropertie(String Key) {
        return getPropertie(APPLICATION_NAME_COMMON, Key);
    }
}
