package org.talkapp.service;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

/**
 * @author Budnikau Aliaksandr
 */
@Configuration
@PropertySource(value = "classpath:elasticsearch.properties")
@EnableElasticsearchRepositories(basePackages = "org.talkapp")
public class ElasticsearchConfiguration {
    @Value("${core.srv.elasticsearch.clusterName}")
    private String clusterName;
    @Value("${core.srv.elasticsearch.host}")
    private String host;
    @Value("${core.srv.elasticsearch.port}")
    private int port;

    @Bean(destroyMethod = "close")
    public Client client() throws Exception {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName)
                .build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        return client;
    }
}