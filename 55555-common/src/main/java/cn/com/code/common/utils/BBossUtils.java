package cn.com.code.common.utils;

import cn.com.code.base.bean.CommonException;
import com.frameworkset.orm.annotation.ESId;
import com.frameworkset.orm.annotation.ESIndex;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.frameworkset.elasticsearch.entity.ESBaseData;
import org.frameworkset.tran.DataStream;
import org.frameworkset.tran.db.input.es.DB2ESImportBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: GeoSocial
 * @description: bboss工具类
 * @author: 韩玉坤
 * @create: 2019-09-17 17:03
 **/
public class BBossUtils {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 生成esJson
     * @param clazz
     * @return
     */
    public static String generatorEsJson(Class<?> clazz) {
        try {

            Field[] declaredFields = clazz.getDeclaredFields();
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
            ESIndex annotation = clazz.getAnnotation(ESIndex.class);
            if(annotation == null){
                throw new CommonException("实体类缺少ESIndex注解");
            }
            String indexName = annotation.name();
            xContentBuilder.startObject()
                    .startObject("settings")
                    .field("number_of_shards", "1")
                    .field("number_of_replicas", "1")
                    .field("index.refresh_interval", "5s")
                    .endObject()
                    .startObject("mappings")
                    .startObject(indexName)
                    .startObject("properties");
            for(int i = 0 ; i < declaredFields.length ; i++){
                Field currentField = declaredFields[i];
                String name = currentField.getName();
                //不设置常量字段
                boolean isFinal = Modifier.isFinal(currentField.getModifiers());
                if(isFinal){
                    continue;
                }
                // 如果类型是String
                if (currentField.getGenericType().toString().equals("class java.lang.String")) {
                    xContentBuilder.startObject(name)
                            .field("type", "text")
                            .endObject();
                }

                // 如果类型是Integer
                if (currentField.getGenericType().toString().equals("class java.lang.Integer")) {
                    xContentBuilder.startObject(name)
                            .field("type", "integer")
                            .endObject();
                }

                // 如果类型是Double
                if (currentField.getGenericType().toString().equals("class java.lang.Double")) {
                    xContentBuilder.startObject(name)
                            .field("type", "double")
                            .endObject();
                }

                // 如果类型是Boolean 是封装类
                if (currentField.getGenericType().toString().equals("class java.lang.Boolean")) {
                    xContentBuilder.startObject(name)
                            .field("type", "boolean")
                            .endObject();
                }

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名
                if (currentField.getGenericType().toString().equals("boolean")) {
                    xContentBuilder.startObject(name)
                            .field("type", "boolean")
                            .endObject();
                }
                // 如果类型是Date
                if (currentField.getGenericType().toString().equals("class java.util.Date")) {
                    xContentBuilder.startObject(name)
                            .field("type", "date")
                            .field("format", "yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd HH:mm:ss.SSS||yyyy-MM-dd HH:mm:ss||epoch_millis||yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                            .endObject();
                }
                // 如果类型是Short
                if (currentField.getGenericType().toString().equals("class java.lang.Short")) {
                    xContentBuilder.startObject(name)
                            .field("type", "short")
                            .endObject();
                }
            }
            xContentBuilder.endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            System.out.println( xContentBuilder.toString());
            return xContentBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取索引id
     * @param clazz
     * @return
     */
    public static String getElasticSearchIndexId(Class<? extends ESBaseData> clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            ESId annotation = declaredField.getAnnotation(ESId.class);
            if(annotation != null){
                return declaredField.getName();
            }
        }
        throw new CommonException("没有在属性中找到ESId注解！");
    }

    /**
     * 获取索引名称
     * @param clazz
     * @return
     */
    public static String getElasticSearchIndex(Class<? extends ESBaseData> clazz){
        ESIndex annotation = clazz.getAnnotation(ESIndex.class);
        if(annotation == null){
            throw new CommonException("没有在属性中找到ESIndex注解！");
        }
        return annotation.name();
    }

    /**
     * 获取索引类型
     * @param clazz
     * @return
     */
    public static String getElasticSearchIndexType(Class<? extends ESBaseData> clazz){
        ESIndex annotation = clazz.getAnnotation(ESIndex.class);
        if(annotation == null){
            throw new CommonException("没有在属性中找到ESIndex注解！");
        }
        return annotation.type();
    }

    /**
     * 从数据库批量导入数据
     * @param clazz  实体类
     * @param sql
     */
    public static void initElasticSearchByClazz(Class<? extends ESBaseData> clazz, String sql){
        DB2ESImportBuilder importBuilder = DB2ESImportBuilder.newInstance();
        //数据源相关配置，可选项，可以在外部启动数据源
        importBuilder.setDbName(getElasticSearchIndex(clazz))
                //数据库驱动程序，必须导入相关数据库的驱动jar包
                .setDbDriver(SpringContextHelper.getApplicationProValue("spring.datasource.driverClassName"))
                //通过useCursorFetch=true启用mysql的游标fetch机制，否则会有严重的性能隐患，useCursorFetch必须和jdbcFetchSize参数配合使用，否则不会生效
                .setDbUrl(SpringContextHelper.getApplicationProValue("spring.datasource.url"))
                .setDbUser(SpringContextHelper.getApplicationProValue("spring.datasource.username"))
                .setDbPassword(SpringContextHelper.getApplicationProValue("spring.datasource.password"))
                .setValidateSQL("select 1")
                //是否使用连接池
                .setUsePool(false);


        //指定导入数据的sql语句，必填项，可以设置自己的提取逻辑
        importBuilder.setSql(sql);
        importBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        /**
         * es相关配置
         */
        importBuilder
                .setIndex(getElasticSearchIndex(clazz))
                .setIndexType(getElasticSearchIndexType(clazz))
                //可选项，null表示不实时刷新，importBuilder.setRefreshOption("refresh");表示实时刷新
                .setRefreshOption("refresh")
                //可选项,将数据库字段名称转换为java驼峰规范的名称，例如:doc_id -> docId
                .setUseJavaName(true)
                //可选项,批量导入es的记录数，默认为-1，逐条处理，> 0时批量处理
                .setBatchSize(3000)
                //设置数据库的查询fetchsize，同时在mysql url上设置useCursorFetch=true启用mysql的游标fetch机制，否则会有严重的性能隐患，jdbcFetchSize必须和useCursorFetch参数配合使用，否则不会生效
                .setJdbcFetchSize(10000)
                //参考jdk timer task文档对fixedRate的说明
                .setFixedRate(false);

        //一次、作业创建一个内置的线程池，实现多线程并行数据导入elasticsearch功能，作业完毕后关闭线程池
        //设置为多线程并行批量导入
        importBuilder.setParallel(false);
        //设置批量导入线程池等待队列长度
        importBuilder.setQueue(10);
        //设置批量导入线程池工作线程数量
        importBuilder.setThreadCount(50);
        //任务出现异常，是否继续执行作业：true（默认值）继续执行 false 中断作业执行
        importBuilder.setContinueOnError(true);
        //true 异步方式执行，不等待所有导入作业任务结束，方法快速返回；false（默认值） 同步方式执行，等待所有导入作业任务结束，所有作业结束后方法才返回
        importBuilder.setAsyn(false);
        //设置文档主键，不设置，则自动产生文档id
        importBuilder.setEsIdField(humpToLine2(getElasticSearchIndexId(clazz)));
        //设置是否将每次处理的reponse打印到日志文件中，默认false，不打印响应报文将大大提升性能，只有在需要的时候才，log日志级别同时要设置为INFO
        importBuilder.setDebugResponse(true);
        //执行数据库表数据导入es操作
        DataStream dataStream = importBuilder.builder();
        dataStream.execute();
    }
    /** 驼峰转下划线*/
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }




}
