package com.jt.controller;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;

import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.jt.pojo.Product;

@Controller
public class ESController {

	@Autowired
	private TransportClient client;
	public List<Product> searchItem(@PathVariable String keyword,Integer page,Integer rows){
		  QueryBuilder query = QueryBuilders.matchQuery("title", keyword).operator(Operator.AND);
		 SearchResponse response = client.prepareSearch("jtdb_item").setQuery(query).setFrom((page-1)*rows).setSize(rows).get();
		 List<Product> list=new ArrayList<>();
		 SearchHits hits = response.getHits();
		 for(SearchHit hit:hits){
			 Product p=new Product();
				p.setImage(hit.getSource().get("image")+"");
				p.setTitle(""+hit.getSource().get("title"));
				p.setPrice((int)(hit.getSource().get("price"))+0l);
				p.setSellPoint(""+hit.getSource().get("sell_point"));
				list.add(p);
		 }
		 return list;
	}
}
