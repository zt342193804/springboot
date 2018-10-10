package com.jt.lucene;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class test {

	@Test
	public void test() throws IOException{
		//指定文件夹的名称
		Path path=Paths.get("index2");
		//指定lucene输出路径对象
		FSDirectory directory=FSDirectory.open(path);
		//生成配置对象，指定分词器
		Analyzer analyzer=new IKAnalyzer6x();
		IndexWriterConfig config=new IndexWriterConfig(analyzer);
		config.setOpenMode(OpenMode.CREATE);
		//生成存储对象
		Document d1=new Document();
		Document d2=new Document();
		d1.add(new StringField("id","1000",Store.YES));
		d1.add(new TextField("name","lucene索引测试",Store.YES));
		d1.add(new DoublePoint("price1", 20000));
		d2.add(new IntPoint("price2", 1000));
		IndexWriter write=new IndexWriter(directory, config);
		write.addDocument(d1);
		write.addDocument(d2);
		write.commit();
		write.close();
		directory.close();
	}
}
