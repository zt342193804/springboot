package com.jt.config;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig implements FactoryBean<TransportClient>,InitializingBean{
	@Value("${cluster-nodes}")
	private String clusterNodes;
	@Value("${cluster-name}")
	private String clusterName;
	
	private TransportClient client;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Settings settings = Settings.builder().put("cluster.name",clusterName).build();
		client=new PreBuiltTransportClient(settings);
		String[] nodes=clusterNodes.split(",");
		for(String node:nodes){
			String[] hostAndPort=node.split(":");
			String host=hostAndPort[0];
			int port=Integer.parseInt(hostAndPort[1]);
			client.addTransportAddress(
					new InetSocketTransportAddress(
							InetAddress.getByName(host),port));
		}
		
	}
	@Override
	public TransportClient getObject() throws Exception {
		
		return client;
	}
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return TransportClient.class;
	}
	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
