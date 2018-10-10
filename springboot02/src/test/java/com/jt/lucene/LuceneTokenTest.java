package com.jt.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

public class LuceneTokenTest {

	public static void printAnalyer(Analyzer analyzer,String str) throws IOException{
		StringReader reader=new StringReader(str);
		TokenStream tokenStream=analyzer.tokenStream("test", reader);
		tokenStream.reset();
		CharTermAttribute attribute=tokenStream.getAttribute(CharTermAttribute.class);
		while(tokenStream.incrementToken()){
			System.out.println(attribute.toString());
		}
	}
	
	@Test
	public void run() throws IOException{
		Analyzer a1=new SmartChineseAnalyzer();
		Analyzer a2=new WhitespaceAnalyzer();
		Analyzer a3=new SimpleAnalyzer();
		Analyzer a4=new IKAnalyzer6x();
		
		String str="快捷酒店房间，的房价开发的酒店房间";
		System.out.println("智能分词器***************");
		LuceneTokenTest.printAnalyer(a1, str);
		System.out.println("空格分词器***************");
		LuceneTokenTest.printAnalyer(a2, str);
		System.out.println("简单分词器***************");
		LuceneTokenTest.printAnalyer(a3, str);
		System.out.println("IK分词器***************");
		LuceneTokenTest.printAnalyer(a4, str);
	}
}
