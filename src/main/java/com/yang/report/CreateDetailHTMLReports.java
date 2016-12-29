package com.yang.report;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;
import org.dom4j.DocumentException;

import com.yang.calculate.Calculate;
import com.yang.chart.LineChart;
import com.yang.configration.Configure;
import com.yang.dao.AggregateDao;
import com.yang.pojo.AggregateResult;
import com.yang.pojo.AggregeteResultHistory;
import com.yang.util.HTMLReporterUtil;

/** 
 * @项目名称：jmeter-analysisReport-maven-plugin 
 * @类名称：CreateDetailHTMLReports 
 * @类描述： 
 * @看云：
 * @url：
 * @创建人：jerry 
 * @作者单位：三一重工流程信息化总部 
 * @联系方式：jerry@hisany.com
 * @创建时间：2016年12月21日 下午2:21:14 
 * @version 
 */
public class CreateDetailHTMLReports{

	public void handler(List<Object> total,Map<String, String> detail,File f) {
		
		Configure configure = Configure.getConfigure();
		String postFix = configure.getJmeterResultPostFix();
		String htmlReportPath = configure.getHtmlReportOutputPath();
		String buildTime = configure.getBuildTime();
		Log logger = configure.getLogger();
		AggregateDao dao = new AggregateDao();
		HTMLReporterUtil html = new HTMLReporterUtil();
		String detailsTemplate = Configure.TEMPLATE_DETAILS;
		int count = configure.getFetchLimit();
		
		String name = f.getName().replace(postFix, "");
		String detailsHtml = htmlReportPath + Configure.FILE_SEPARATOR+ buildTime + Configure.FILE_SEPARATOR + name + ".html";
		detailsHtml = detailsHtml.replace("/\\", "/").replace("\\", "/");

		logger.info("startting to parser...." + name);

		// 得到当前文件的解析数据
		List<AggregateResult> list=null;
		List<AggregeteResultHistory> arhList=null;
		try {
			list = Calculate.calculate(f);
			arhList=Calculate.calculateRunTime(f);
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}

		if(list.size()<=0||arhList.size()<=0)
			return ;
		
		total.add(list);
	
		logger.info("startting to save data to db....");
		// 入库
		dao.save(list);
		
		logger.info("startting to fetch data to for png....");
		// 产生详情页
		List<AggregeteResultHistory> list2 = dao.fetchDataForPNG(list,name, count);
	
		Map<String, Object> detailsMap = new HashMap<String, Object>();
		// 生成图片

		logger.info("startting to draw png....");
		LineChart chart=new LineChart();
		detailsMap = chart.createHisChart(list2);
		detailsMap.putAll(chart.createRunTimeChart(arhList));

		logger.info("startting to fetch data for details....");
		// 查询构建历史
		List<AggregeteResultHistory> list3 = dao.fetchDataForList(list,name, count);
		detailsMap.put("dataList", list3);
		detailsMap.put("scriptName", f.getName());
	
		logger.info("startting to create html report " + detailsHtml + "....");
		// 生成脚本报告html
		html.create(detailsTemplate, detailsMap, detailsHtml);

		if(f.exists()&&configure.isRemoveJTLAfterHandler()){
			f.delete();
		}
		
		detail.put(f.getName(), detailsHtml);

	}

}
