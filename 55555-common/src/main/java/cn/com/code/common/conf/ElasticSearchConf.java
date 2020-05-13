package cn.com.code.common.conf;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName: ElasticSearchConf
 * @Description: TODO
 * @author: 55555
 * @date: 2020年05月13日 10:50 下午
 */
@Configuration
@ConditionalOnClass(ElasticsearchTemplate.class)
public class ElasticSearchConf {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String hosts;

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String clusterName;

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() throws UnknownHostException {

        return new ElasticsearchTemplate(transportClient());
    }

    @Bean
    public TransportClient transportClient() throws NumberFormatException, UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", clusterName) // 设置ES实例的名称
                .put("client.transport.sniff", true) // 自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);

        String[] hostsList = hosts.split(",");
        for (String host : hostsList) {
            String[] split = host.split(":");
            client.addTransportAddress(new TransportAddress(InetAddress.getByName(split[0]), Integer.parseInt(split[1])));
        }
        return client;
    }

}
