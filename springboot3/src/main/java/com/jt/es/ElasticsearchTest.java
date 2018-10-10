package com.jt.es;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.Book;

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
	
	/**
	 * 删除索引
	 * @throws Exception
	 */
	@Test
	public void test02() throws Exception{
		TransportClient client=
				new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(
				new InetSocketTransportAddress(
						InetAddress.getByName("192.168.194.133"),9300));
		IndicesAdminClient indexClient = client.admin().indices();
		DeleteIndexResponse response = indexClient.prepareDelete("index02").get();
		System.out.println(response.isAcknowledged());
	}
	
	/**
	 * 对象转换成json
	 * @throws JsonProcessingException
	 */
	@Test
	public void test03() throws JsonProcessingException{
		Book book=new Book();
		book.setId(5);
		book.setTitle("es分布式式集群");
		book.setContent("如果实现企业应用");
		
		ObjectMapper mapper=new ObjectMapper();
		String jsonData=mapper.writeValueAsString(book);
		System.out.println(jsonData);
	}
	
	/**
	 * 新增文档
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void test04() throws Exception{
		TransportClient client=
				new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(
				new InetSocketTransportAddress(
						InetAddress.getByName("192.168.194.133"), 9300));
		Book book=new Book();
		book.setId(5);
		book.setTitle("es分布式式集群");
		book.setContent("如果实现企业应用");
		
		ObjectMapper mapper=new ObjectMapper();
		String jsonData=mapper.writeValueAsString(book);
		IndexResponse response = client.prepareIndex("index01","book","5").setSource(jsonData).execute().get();
		System.out.println(response.status());
	}
	
	/**
	 * 查询索引
	 * @throws Exception
	 */
	@Test
	public void test05() throws Exception{
		TransportClient client=
				new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(
				new InetSocketTransportAddress(
						InetAddress.getByName("192.168.194.133"), 9300));
		
		GetRequestBuilder response = client.prepareGet("index01","book","5");
		String jsonData=response.get().getSourceAsString();
		ObjectMapper mapper=new ObjectMapper();
		Book book = mapper.readValue(jsonData, Book.class);
		
//		Map<String, Object> source = response.get().getSource();
		System.out.println(book.getContent());
	}
	
	/**
	 * 搜索索引
	 * @throws Exception
	 */
	@Test
	public void test06() throws Exception{
		TransportClient client=
				new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(
				new InetSocketTransportAddress(
						InetAddress.getByName("192.168.194.133"), 9300));
		
		QueryBuilder query=QueryBuilders.matchQuery("title","es").operator(Operator.AND);
		 SearchResponse response = client.prepareSearch("index01").setQuery(query).setSize(10).get();
		 SearchHits hits = response.getHits();
		 
		System.out.println(hits.getTotalHits());
		
		for(SearchHit hit:hits){
			System.out.println(hit.getSourceAsString());
		}
	}
	
	@Test
	public void test07() throws Exception{
		
		String indexName="index03";
		String typeName="school";
		TransportClient client=new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(
				new InetSocketTransportAddress(
						InetAddress.getByName("192.168.194.133"),9300));
		client.admin().indices().prepareCreate(indexName).execute().get();
		XContentBuilder builder=XContentFactory.jsonBuilder()
				                .startObject()
				                .startObject("properties")
				                .startObject("id").field("type","integer").field("store","yes").endObject()
				                .startObject("name").field("type","string").field("store","yes").field("analyzer","ik_max_word").field("search_analyzer","ik_max_word").endObject()
				                .endObject().endObject();
		PutMappingRequest mappingBind = Requests.putMappingRequest(indexName).type(typeName).source(builder);
		client.admin().indices().putMapping(mappingBind).actionGet();
	}
	
	
}
