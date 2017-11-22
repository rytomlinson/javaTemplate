package com.navis.insightserver.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.navis.insightserver.Utils.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.navis.insightserver.Utils.Util.trim;

/**
 * Created by darrell-shofstall on 8/12/17.
 */

@Configuration
@EnableHazelcastHttpSession
public class HazelcastConfiguration {
    @Value("${hazelcast.group.groupName}")
    private String groupName;
    @Value("${hazelcast.group.groupPassword}")
    private String groupPassword;
    @Value("${hazelcast.network.networkMembers}")
    private String networkMembers;
    @Value("${hazelcast.connectionAttemptLimit}")
    private Integer connectionAttemptLimit;

//    @Order(value = 1)
//    @Bean
//    public HazelcastInstance getHazelcastClientInstance() throws IOException {
//
//        final GroupConfig groupConfig = new GroupConfig(trim(groupName), trim(groupPassword));
//        final List<String> members = Arrays.stream(trim(networkMembers).split(","))
//                .map(Util::trim).collect(Collectors.toList());
//        final ClientNetworkConfig networkConfig = new ClientNetworkConfig()
//                .setAddresses(members).setRedoOperation(true)
//                .setConnectionAttemptLimit(connectionAttemptLimit);
//        final ClientConfig clientConfig = new ClientConfig()
//                .setGroupConfig(groupConfig).setNetworkConfig(networkConfig);
//
//        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
//
//        return hazelcastInstance;
//    }

    @Order(value = 2)
    @Bean
    public HazelcastInstance hazelcastInstance() {

        final GroupConfig groupConfig = new GroupConfig(trim(groupName), trim(groupPassword));
        final List<String> members = Arrays.stream(trim(networkMembers).split(","))
                .map(Util::trim).collect(Collectors.toList());
        final List<Integer> outboundPorts = new ArrayList<>();
        outboundPorts.add(0);
        final Set<String> trustedInterfaces = new HashSet<String>();
        trustedInterfaces.add("127.0.0.1");

        final MulticastConfig multicastConfig = new MulticastConfig();
        multicastConfig.setEnabled(true)
                .setMulticastGroup("224.2.2.3")
                .setMulticastPort(54327)
                .setMulticastTimeToLive(32)
                .setMulticastTimeoutSeconds(2)
                .setTrustedInterfaces(trustedInterfaces);

        final TcpIpConfig tcpIpConfig = new TcpIpConfig();
        tcpIpConfig.setEnabled(true).setConnectionTimeoutSeconds(10).setMembers(members).setEnabled(false);

        final JoinConfig joinConfig = new JoinConfig();
        joinConfig.setMulticastConfig(multicastConfig).setTcpIpConfig(tcpIpConfig);


        final NetworkConfig networkConfig = new NetworkConfig();
        networkConfig
                .setJoin(joinConfig)
                .setPortAutoIncrement(true)
                .setPort(5801)
                .setOutboundPorts(outboundPorts)
                .setPortCount(100);


        MapAttributeConfig attributeConfig = new MapAttributeConfig()
                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractor(PrincipalNameExtractor.class.getName());

        Config config = new Config();
        config.getMapConfig("spring:session:sessions")
                .addMapAttributeConfig(attributeConfig)
                .addMapIndexConfig(new MapIndexConfig(
                        HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));

        config.setGroupConfig(groupConfig);
        config.setNetworkConfig(networkConfig);

        return Hazelcast.newHazelcastInstance(config);
    }
}
