package com.yang.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.maven.plugin.logging.Log;

import com.yang.callback.CallBack;
import com.yang.configration.Configure;
import com.yang.util.HTMLReporterUtil;

/** 
 * @项目名称：jmeter-analysisReport-maven-plugin 
 * @类名称：CreateMainHTMLReports 
 * @类描述： 
 * @看云：
 * @url：
 * @创建人：jerry 
 * @作者单位：三一重工流程信息化总部 
 * @联系方式：jerry@hisany.com
 * @创建时间：2016年12月21日 下午3:54:34 
 * @version 
 */
public class CreateMainHTMLReports implements CallBack{
	
	private Configure configure = Configure.getConfigure();
	private String buildTime = configure.getBuildTime();
	private String jmeterResultPath = configure.getJmeterResultPath();
	private String htmlReportPath = configure.getHtmlReportOutputPath();
	private Log logger = configure.getLogger();		
	private String mainTemplate = Configure.TEMPLATE_MAIN;
	private String projectName = configure.getProjectName();
	
	ExecutorService es;
	volatile boolean mExit = false;
	int count = 0;
	private static final Object synObj = new Object();
	Map<String, String> detail = new HashMap<String, String>();
	List<Object> total = new ArrayList<Object>();
	/**
	 * 解析结果文件、入库、生成详情HTML页
	 * @param f
	 * @return
	 */
	
	private void createSubReport(File[] files,Map<String, String> detail, List<Object> total) {
		if (null != files){
			es = Executors.newFixedThreadPool(configure.getWorkingThreads());
			for (final File f : files) {
				es.execute(new MyRunnable(f,this));
			}
		}
	}
	
	private class MyRunnable implements Runnable{
		CallBack cb;
		File f;
		
		public MyRunnable(File f,CallBack cb){
			this.f = f;
			this.cb = cb;
		}
		
		public void run() {
			Map<String, String> detail = new HashMap<String, String>();
			List<Object> total = new ArrayList<Object>(); 
			new CreateDetailHTMLReports().handler(total, detail, f);
			
			cb.notify(detail,total);
		}
		
	}

	public void handler() throws InterruptedException, ExecutionException, FileNotFoundException {
		
		HTMLReporterUtil html = new HTMLReporterUtil();
		Map<String, Object> mainMap = new HashMap<String, Object>();
		String mainHtml = htmlReportPath + Configure.FILE_SEPARATOR+ buildTime + Configure.FILE_SEPARATOR + "main.html";
		mainHtml = mainHtml.replace("/\\", "/").replace("\\", "/");

		File file = new File(jmeterResultPath);
		File[] files = file.listFiles();
		if (null != files){
			count = files.length;
		}

		createSubReport(files,detail,total);
		
		while (!mExit){
			try{
				Thread.sleep(10);
			}catch(Exception ex){
				
			}
		}
		
		es.shutdown();
		
		if(total.size()<=0)
			return ;
		// index页添加数据
		mainMap.put("buildtime", buildTime);
		mainMap.put("agList", total);
		mainMap.put("details", detail);
		mainMap.put("projectName", projectName);

		logger.info("creating " + mainHtml + "....");
		// 生成index.html页面
		html.create(mainTemplate, mainMap, mainHtml);
	}

	public void notify(Map<String, String> detail,List<Object> total) {
		synchronized (synObj) {
			count--;
			this.detail.putAll(detail);
			this.total.addAll(total);
			if (count <= 0) {
				mExit = true;
			}
		}
	}

}
