package com.yang.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.yang.configration.Configure;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：HTMLReportGenerator
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月7日 下午2:26:05
 * @version
 */
public class HTMLReporterUtil {

	private Configure configure = Configure.getConfigure();

	public void create(String templateName, Map<String, Object> rootMap,String writeTo) {

		try {

			String t = configure.getProjectHome() + "\\src\\main\\resources\\"+ templateName;

			File file = new File(t);
			String path = "";
			if (file.isDirectory()) {
				path = file.getPath();
			}
			path = file.getParentFile().getAbsolutePath();
			String name = file.getName();

			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(writeTo)),configure.getCharEncoder()));

			Configuration conf = new Configuration();
			conf.setDefaultEncoding(configure.getCharEncoder());
			conf.setDirectoryForTemplateLoading(new File(path));
			conf.setObjectWrapper(new DefaultObjectWrapper());
			Template template = conf.getTemplate(templateName);
			template.setEncoding(configure.getCharEncoder());
			template.process(rootMap, out);
			out.flush();
			out.close();
		} catch (TemplateNotFoundException e) {

			e.printStackTrace();

		} catch (MalformedTemplateNameException e) {

			e.printStackTrace();

		} catch (ParseException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} catch (TemplateException e) {

			e.printStackTrace();
		}
	}

}
