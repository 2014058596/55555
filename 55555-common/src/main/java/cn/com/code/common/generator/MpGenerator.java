package cn.com.code.common.generator;


import cn.com.code.common.utils.DateUtils;
import cn.com.code.common.utils.SpringContextHelper;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王柳敬
 * @version 创建时间：2018年8月19日---下午8:09:35
 * @description
 */
@Log4j2
public class MpGenerator {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(MpGenerator.getStringValue("outputDir"));
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor(MpGenerator.getStringValue("author"));

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dataSourceConfig.setDriverName(MpGenerator.getStringValue("driverName"));
        dataSourceConfig.setUsername(MpGenerator.getStringValue("userName"));
        dataSourceConfig.setPassword(MpGenerator.getStringValue("password"));
        dataSourceConfig.setUrl(MpGenerator.getStringValue("url"));
        mpg.setDataSource(dataSourceConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityColumnConstant(true); // 生成常量字段
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        String tablePrefix1 = MpGenerator.getStringValue("tablePrefix");
        String[] tablePrefix = null;
        if(StringUtils.isNoneBlank(tablePrefix1)){
            tablePrefix = tablePrefix1.split(",");
        }
        if (tablePrefix != null && tablePrefix.length > 0) {
            strategy.setTablePrefix(tablePrefix);
        }
        String tableName = MpGenerator.getStringValue("tableName");
        String[] tableNames = new String[0];
        if(StringUtils.isNoneBlank(tableName)){
            tableNames = tableName.split(",");
        }
        if (!StringUtils.isAllBlank(tableNames)) {
            strategy.setInclude(tableNames); // 需要生成的表
        }

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(null);
        packageConfig.setEntity(MpGenerator.getStringValue("entityPackage"));
        packageConfig.setMapper(MpGenerator.getStringValue("mapperPackage"));
        packageConfig.setController(MpGenerator.getStringValue("controllerPackage"));
        packageConfig.setService(MpGenerator.getStringValue("servicePackage"));
        packageConfig.setServiceImpl(MpGenerator.getStringValue("serviceImplPackage"));
        packageConfig.setXml("mapper.oracle");
        mpg.setPackageInfo(packageConfig);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>(16);
                map.put("time", DateUtils.getDate());
                map.put("controllerApiPackage", MpGenerator.getStringValue("controllerApiPackage"));
                map.put("constantsClass", MpGenerator.getStringValue("constantsClass"));
                map.put("applicationName", MpGenerator.getStringValue("applicationName"));
                map.put("entityModelPackage", MpGenerator.getStringValue("entityModelPackage"));
                map.put("entityPackage", MpGenerator.getStringValue("entityPackage"));
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        //添加FeignAPI接口对象
        focList.add(new FileOutConfig("templates/controllerApi.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return MpGenerator.getStringValue("outputDir") + "/" + (MpGenerator.getStringValue("controllerApiPackage").replaceAll("\\.", "/" ))+
                        "/I" + tableInfo.getControllerName() + ".java";
            }
        });

        //添加Entity扩展对象
        focList.add(new FileOutConfig("templates/entityModel.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return MpGenerator.getStringValue("outputDir") + "/" + (MpGenerator.getStringValue("entityModelPackage").replaceAll("\\.", "/")) +
                        "/" + tableInfo.getEntityName() + "Model.java";
            }
        });
        //这块是set到上面自定义配置中
        cfg.setFileOutConfigList(focList);
        //set进去代码生成器对象中
        mpg.setCfg(cfg);
        // 执行生成
        mpg.execute();
    }


    public static PropertiesConfiguration cfg = null;

    public static final String propFileName = "mp.properties";


    static {
        try {
            cfg = new PropertiesConfiguration(URLDecoder.decode(SpringContextHelper.class.getResource("/").getPath(),"utf-8") + propFileName);
        } catch (Exception e) {
            log.debug(e);
        }
    }

    /*获取String*/
    public static String getStringValue(String key) {
        return cfg.getString(key);
    }


}