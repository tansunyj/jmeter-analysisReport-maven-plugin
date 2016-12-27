package com.yang.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yang.configration.Configure;
import com.yang.pojo.Sampler;
import com.yang.util.TxtUtil;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：ParserSamplers
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月6日 上午11:22:48
 * @version
 */
public final class Dom4jParserSampler {

	public static List<Sampler> parser(File file) throws DocumentException {

		List<Sampler> list = new ArrayList<Sampler>();

		Document doc = DocumentHelper.parseText(TxtUtil.read(file));
		Element ele = doc.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> elist = ele.elements();
		for (Element e : elist) {
			if (!Configure.getConfigure().getIgnoreSamplerNames().contains(e.getName()))
				list.add(parser(e));
		}

		return list;
	}

	private static Sampler parser(Element ele) {
		Sampler attr = new Sampler();

		attr.setT(Integer.parseInt(ele.attributeValue("t")));
		attr.setLt(Integer.parseInt(ele.attributeValue("lt")));
		attr.setTs(Long.parseLong(ele.attributeValue("ts")));
		attr.setS(Boolean.parseBoolean(ele.attributeValue("s")));
		attr.setLb(ele.attributeValue("lb"));
		attr.setRc(ele.attributeValue("rc"));
		attr.setRm(ele.attributeValue("rm"));
		attr.setTn(ele.attributeValue("tn"));
		attr.setDt(ele.attributeValue("dt"));
		attr.setBy(Integer.parseInt(ele.attributeValue("by")));

		return attr;
	}

}
