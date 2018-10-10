package com.jt.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;


public class CreateIndex {

	 /**
	  * 使用Lucene创建索引文件
	  * 1 指定输出文件的位置，当前工程“index”文件夹
	  * 2 设置索引创建时的配置对象，指定分词等环节信息
	  * 3 手动创建保持的数据对象--document
	  * 4 使用Lucene的流，将数据输出
	 * @throws Exception 
	  */
	
	@Test
	public void createIndex() throws Exception{
		//指定文件夹
		Path path=Paths.get("index");
		//指定lucene格式的输出路径对象
		FSDirectory directory=FSDirectory.open(path);
		//生成配置对象，指定分词器
		Analyzer analyzer=new IKAnalyzer6x();
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		//create表示每次创建都覆盖，append每次创建都追加 
		//create_or_append 有就追加，没有就创建
		config.setOpenMode(OpenMode.CREATE);
		//生成存储的数据对象
		Document doc1=new Document();
		Document doc2=new Document();
		//对文档对象进行数据的添加，需要使用到field（字段，属性）
		//name表示当前域的，名称
		//value当前添加的域属性的值
		//Store.yes 在创建索引文件时，document的这个域值会不会存到索引文件里
        doc1.add(new StringField("id", "1000", Store.YES));		
        doc1.add(new TextField("title","华为(HUAWEI)M3 8.4英寸平板电脑（2K高清屏 麒麟950 哈曼卡顿音效 4G/32G WiFi）皓月银", Store.YES));
        doc1.add(new TextField("desc","分辨率：超高清屏（2K/3K/4K）", Store.YES));
//        doc1.add(new DoublePoint("price", 1200));
        //Store.yes和no的区别，StringField和TextField区别
        doc2.add(new StringField("id","666",Store.YES));
//        doc2.add(new DoublePoint("price", 1000));
        doc2.add(new TextField("content","今天学习lucene的华为",Store.NO));
        //数据来源需要读取数据，不是手动添加
        
        //输出到索引文件创建索引
        //在writer对document进行输出时，会根据配置分词器，进行数据的分词计算
        IndexWriter writer=new IndexWriter(directory, config);
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.commit();
        writer.close();
        directory.close();
        
	}
	
}
