package org.talkapp.service;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

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
    @Value("${core.srv.elasticsearch.data.files}")
    private String file;

    @Bean(destroyMethod = "close")
    public Client client() throws Exception {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName)
                .build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        return client;
    }

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        List<Resource> resources = new LinkedList<>();
        if (!"".equals(file)) {
            resources.add(new ClassPathResource(file));
        }
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(resources.toArray(new Resource[]{}));
        return factory;
    }
}