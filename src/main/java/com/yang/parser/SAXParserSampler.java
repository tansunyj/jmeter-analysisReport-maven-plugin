package com.yang.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.yang.configration.Configure;
import com.yang.pojo.Sampler;

/** 
 * @项目名称：test 
 * @类名称：SAXParserSampler 
 * @类描述： 
 * @看云：
 * @url：
 * @创建人：jerry 
 * @作者单位：三一重工流程信息化总部 
 * @联系方式：jerry@hisany.com
 * @创建时间：2016年12月21日 上午8:44:45 
 * @version 
 */
public class SAXParserSampler {
	Configure configure=Configure.getConfigure();
	
    public List<Sampler> parser(File file){
    	SAXParser saxParser = null;
    	JTLParser parser=new JTLParser();
    	try {
    		saxParser = SAXParserFactory.newInstance().newSAXParser();
			Reader isr=new BufferedReader(new InputStreamReader(new FileInputStream(file),configure.getCharEncoder()));
			saxParser.parse(new InputSource(isr), parser);
			isr.close();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

    	return parser.getList();
    }
	
	private class JTLParser extends DefaultHandler{
		private List<Sampler> list;
		private Sampler sampler;
		
	    public List<Sampler> getList() {
			return list;
		}
	    
	    @Override
	    public void startDocument(){
	    	list=new ArrayList<Sampler>();
	    }
	    
		@Override
	    public void startElement(String u, String localName, String qName, Attributes attributes) throws SAXException {
	    	
	    	if(!configure.getIgnoreSamplerNames().contains(qName)&&attributes.getLength()>1){
	    		sampler=new Sampler();
	        	sampler.setBy(Integer.valueOf(attributes.getValue("by")));
	        	sampler.setDt(attributes.getValue("dt"));
	        	sampler.setLb(attributes.getValue("lb"));
	        	sampler.setLt(Integer.valueOf(attributes.getValue("lt")));
	        	sampler.setRc(attributes.getValue("rc"));
	        	sampler.setRm(attributes.getValue("rm"));
	        	sampler.setS(Boolean.valueOf(attributes.getValue("s")));
	        	sampler.setT(Integer.valueOf(attributes.getValue("t")));
	        	sampler.setTn(attributes.getValue("tn"));
	        	sampler.setTs(Long.valueOf(attributes.getValue("ts")));
	    	}
	    	
	    }	
		
		@Override
		public void endElement(String u, String localName, String qName){
			if(sampler!=null){
				list.add(sampler);
			}
		}
	}

}
