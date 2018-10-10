package com.jt.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class QueryIndex {

	/**
	 * lucene查询搜索的测试
	 * 1 指定路径
	 * 2 封装查询对象
	 * 3 查询获取结果集
	 * 4 从结果集封装业务需要的数据
	 * @throws Exception 
	 */
	
	
	//分词查询
	@Test
	public void queryIndex() throws Exception{
		//路径指定
		Path path = Paths.get("index");
		FSDirectory directory = FSDirectory.open(path);
		//使用输入流打开索引文件
		DirectoryReader reader = DirectoryReader.open(directory);
		//获取查询对象
		IndexSearcher searcher=new IndexSearcher(reader);
		//指定一个查询时可能需要的分词对象
		Analyzer analyzer=new IKAnalyzer6x();
		//封装查询query对象
		//name时域名称，针对哪个域进行搜索
		//啊，analyzer分词器
		QueryParser parser=new QueryParser("title", analyzer);
		parser.setDefaultOperator(Operator.AND);
		//查询条件  利用分词器对查询字符串分词
		//Operator.AND表示查询条件的分词结果的词项必须同时存在于一批都蹙眉net，才能查询到结果
		Query query=parser.parse("平板");
		TopDocs docs = searcher.search(query, 10);
		ScoreDoc[] scoreDocs = docs.scoreDocs;
		for(ScoreDoc scoreDoc:scoreDocs){
			Document document=searcher.doc(scoreDoc.doc);
			//获取document中的数据
			System.out.println("id:"+document.get("id"));
			System.out.println("title:"+document.get("title"));
			System.out.println("desc:"+document.get("desc"));
			System.out.println("文档评分:"+scoreDoc.score);
		}
				
	}
	
	//域查询
	@Test
	public void queryMuti() throws Exception{
		//路径指定
		Path path = Paths.get("index");
		FSDirectory directory = FSDirectory.open(path);
		//使用输入流打开索引文件
		DirectoryReader reader = DirectoryReader.open(directory);
		//获取查询对象
		IndexSearcher searcher=new IndexSearcher(reader);
		//指定一个查询时可能需要的分词对象
		Analyzer analyzer=new IKAnalyzer6x();
		//封装查询query对象
		//name时域名称，针对哪个域进行搜索
		
		String[]  fields={"title","content"};
		MultiFieldQueryParser parser=new MultiFieldQueryParser(fields, analyzer);
		Query query = parser.parse("华为");
		
//		QueryParser parser=new QueryParser("title", analyzer);
//		parser.setDefaultOperator(Operator.AND);
		//查询条件  利用分词器对查询字符串分词
		//Operator.AND表示查询条件的分词结果的词项必须同时存在于一批都蹙眉net，才能查询到结果
//		Query query=parser.parse("平板");
		
		TopDocs docs = searcher.search(query, 10);
		ScoreDoc[] scoreDocs = docs.scoreDocs;
		for(ScoreDoc scoreDoc:scoreDocs){
			Document document=searcher.doc(scoreDoc.doc);
			//获取document中的数据
			System.out.println("id:"+document.get("id"));
			System.out.println("title:"+document.get("title"));
			System.out.println("desc:"+document.get("desc"));
			System.out.println("content:"+document.get("content"));
//			System.out.println("content:"+document.get("content"));
			System.out.println("文档评分:"+scoreDoc.score);
		}
				
	}
	
	//词项查询
	@Test
	public void queryTerm() throws Exception{
		//路径指定
		Path path = Paths.get("index");
		FSDirectory directory = FSDirectory.open(path);
		//使用输入流打开索引文件
		DirectoryReader reader = DirectoryReader.open(directory);
		//获取查询对象
		IndexSearcher searcher=new IndexSearcher(reader);
		//指定一个查询时可能需要的分词对象
		Analyzer analyzer=new IKAnalyzer6x();
		//封装查询query对象
		//name时域名称，针对哪个域进行搜索
		
		Term term=new Term("title","华为");
		Query query=new TermQuery(term);
		
//		QueryParser parser=new QueryParser("title", analyzer);
//		parser.setDefaultOperator(Operator.AND);
		//查询条件  利用分词器对查询字符串分词
		//Operator.AND表示查询条件的分词结果的词项必须同时存在于一批都蹙眉net，才能查询到结果
//		Query query=parser.parse("平板");
		
		TopDocs docs = searcher.search(query, 10);
		ScoreDoc[] scoreDocs = docs.scoreDocs;
		for(ScoreDoc scoreDoc:scoreDocs){
			Document document=searcher.doc(scoreDoc.doc);
			//获取document中的数据
			System.out.println("id:"+document.get("id"));
			System.out.println("title:"+document.get("title"));
			System.out.println("desc:"+document.get("desc"));
			System.out.println("content:"+document.get("content"));
//			System.out.println("content:"+document.get("content"));
			System.out.println("文档评分:"+scoreDoc.score);
		}
				
	}
	
	//范围查询
		@Test
		public void queryRange() throws Exception{
			//路径指定
			Path path = Paths.get("index");
			FSDirectory directory = FSDirectory.open(path);
			//使用输入流打开索引文件
			DirectoryReader reader = DirectoryReader.open(directory);
			//获取查询对象
			IndexSearcher searcher=new IndexSearcher(reader);
			//指定一个查询时可能需要的分词对象
			Analyzer analyzer=new IKAnalyzer6x();
			//封装查询query对象
			//name时域名称，针对哪个域进行搜索
			
			Query query=DoublePoint.newRangeQuery("price", 900, 1500);
			
			TopDocs docs = searcher.search(query, 10);
			ScoreDoc[] scoreDocs = docs.scoreDocs;
			for(ScoreDoc scoreDoc:scoreDocs){
				Document document=searcher.doc(scoreDoc.doc);
				//获取document中的数据
				System.out.println("id:"+document.get("id"));
				System.out.println("title:"+document.get("title"));
				System.out.println("desc:"+document.get("desc"));
				System.out.println("content:"+document.get("content"));
//				System.out.println("content:"+document.get("content"));
				System.out.println("文档评分:"+scoreDoc.score);
				System.out.println("price::"+document.get("price"));
			}
					
		}
}
