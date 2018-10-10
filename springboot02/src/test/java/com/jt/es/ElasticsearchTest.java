package com.jt.es;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

public class ElasticsearchTest {
	
	/**
	 * 创建索引
	 * 操作es都是必须通过DSL
	 * 需要连接es的对象TransportClient，底层
	 * 封装了HTTP协议的连接，把方法最终转换成一个DSL
	 * @throws UnknownHostException 
	 */
	
	@Test
	public void createIndex() throws UnknownHostException{
		//如果目前阶段es的集群名称
		//不是elasticsearch，设置setting必须制定
		/**
		 * settings就是当前连接对象的设置，可以管理es连接信息
		 * 比如集群名称
		 */
		
		TransportClient client=
				new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(
				new InetSocketTransportAddress(
						InetAddress.getByName("192.168.194.133"), 9300));
		//创建索引，需要从client中获取一个索引的管理对象
		IndicesAdminClient indexClient = client.admin().indices();
		//连接底层都是http协议的封装，看到的都是request对象和response响应
		CreateIndexRequestBuilder creater = indexClient.prepareCreate("index02");
		CreateIndexResponse response = creater.get();
		boolean acknowledged = response.isAcknowledged();
		System.out.println(acknowledged);
	}
}
