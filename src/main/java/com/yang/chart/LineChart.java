package com.yang.chart;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.maven.plugin.logging.Log;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.yang.callback.DrawPNGCallBack;
import com.yang.configration.Configure;
import com.yang.pojo.AggregateResult;
import com.yang.pojo.AggregeteResultHistory;
import com.yang.pojo.Serie;
import com.yang.util.ChartUtil;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：LineChart
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月6日 下午8:02:30
 * @version
 */
public class LineChart implements DrawPNGCallBack{
	Configure configure=Configure.getConfigure();
	Log logger=configure.getLogger();
	int width = configure.getConfigurationCharts().getWidth();
	int height = configure.getConfigurationCharts().getHeight();
	String name="";
	String path="";
	
	private int count=0;
	private static final Object synObj = new Object();
	volatile boolean mExit = false;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Map<String, Object> createHisChart(List<AggregeteResultHistory> list) {

		name=list.get(0).getFileName();

		String picPath = configure.getHtmlReportOutputPath()+ Configure.FILE_SEPARATOR + configure.getBuildTime()+ Configure.FILE_SEPARATOR + "png";
		File file = new File(picPath);
		if (!file.exists()) {
			file.mkdirs();
		}
				
		path = file.getAbsolutePath();
		String tps = path + Configure.FILE_SEPARATOR + name + "_tps_his.png";
		String threads = path + Configure.FILE_SEPARATOR + name+ "_threads_his.png";
		String traffic = path + Configure.FILE_SEPARATOR + name+ "_traffic_his.png";
		String rt = path + Configure.FILE_SEPARATOR + name + "_rt_his.png";

		tps = tps.replace("/\\", "/").replace("\\", "/");
		threads = threads.replace("/\\", "/").replace("\\", "/");
		traffic = traffic.replace("/\\", "/").replace("\\", "/");
		rt = rt.replace("/\\", "/").replace("\\", "/");

		Map<String,Vector<Serie>> vectorMap =new HashMap<String,Vector<Serie>>();
		getPerformanceData(vectorMap,list,1);
		String[] testTime = fetchStartTestTime(list);
		
		ChartPojo<AggregeteResultHistory> tpsPojo=new ChartPojo<AggregeteResultHistory>();
		tpsPojo.setBuildTime(testTime);
		tpsPojo.setHeight(height);
		tpsPojo.setList(list);
		tpsPojo.setPicSaveAs(tps);
		tpsPojo.setSeries(vectorMap.get("tps"));
		tpsPojo.setTheme(Configure.PNG_TPS_THEME);
		tpsPojo.setWidth(width);
		tpsPojo.setxDesc(Configure.PNG_TPS_XAXIS);
		tpsPojo.setyDesc(Configure.PNG_TPS_YAXIS);
		
		ChartPojo<AggregeteResultHistory> threadsPojo=new ChartPojo<AggregeteResultHistory>();
		threadsPojo.setBuildTime(testTime);
		threadsPojo.setHeight(height);
		threadsPojo.setList(list);
		threadsPojo.setPicSaveAs(threads);
		threadsPojo.setSeries(vectorMap.get("threads"));
		threadsPojo.setTheme(Configure.PNG_THREADS_THEME);
		threadsPojo.setWidth(width);
		threadsPojo.setxDesc(Configure.PNG_THREADS_XAXIS);
		threadsPojo.setyDesc(Configure.PNG_THREADS_YAXIS);
		
		ChartPojo<AggregeteResultHistory> networkPojo=new ChartPojo<AggregeteResultHistory>();
		networkPojo.setBuildTime(testTime);
		networkPojo.setHeight(height);
		networkPojo.setList(list);
		networkPojo.setPicSaveAs(traffic);
		networkPojo.setSeries(vectorMap.get("traffic"));
		networkPojo.setTheme(Configure.PNG_ENTWORK_THEME);
		networkPojo.setWidth(width);
		networkPojo.setxDesc(Configure.PNG_NETWORK_XAXIS);
		networkPojo.setyDesc(Configure.PNG_NETWORK_YAXIS);
		
		ChartPojo<AggregeteResultHistory> rtPojo=new ChartPojo<AggregeteResultHistory>();
		rtPojo.setBuildTime(testTime);
		rtPojo.setHeight(height);
		rtPojo.setList(list);
		rtPojo.setPicSaveAs(rt);
		rtPojo.setSeries(vectorMap.get("rt"));
		rtPojo.setTheme(Configure.PNG_RT_THEME);
		rtPojo.setWidth(width);
		rtPojo.setxDesc(Configure.PNG_RT_XAXIS);
		rtPojo.setyDesc(Configure.PNG_RT_YAXIS);

		final Map<String,ChartPojo<AggregeteResultHistory>> charMap=new HashMap<String,ChartPojo<AggregeteResultHistory>>();
		charMap.put("tpsPNG_his", tpsPojo);
		charMap.put("threadsPNG_his", threadsPojo);
		charMap.put("trafficPNG_his", networkPojo);
		charMap.put("rtPNG_his", rtPojo);
		
		ExecutorService pool=Executors.newFixedThreadPool(charMap.size());
		count=charMap.size();
		
		for(final String key:charMap.keySet()){
			pool.execute(new MyRunnable<AggregeteResultHistory>(this,key,charMap.get(key)));
		}
		
		while (!mExit){
			try{
				Thread.sleep(10);
			}catch(Exception ex){
				
			}
		}
		pool.shutdown();
	
		return map;
	}

	private class MyRunnable<T> implements Runnable{
		DrawPNGCallBack dcb;
		ChartPojo<T> pojo;
		String key;
		public MyRunnable(DrawPNGCallBack dcb,String key,ChartPojo<T> pojo){
			this.dcb=dcb;
			this.pojo=pojo;
			this.key=key;
		}
		
		public void run() {		
			String path=createChart(pojo);
			Map<String,Object> m=new HashMap<String,Object>();
			m.put(key, path);
			dcb.notify(m);
		}
	}
	
	public Map<String,Object> createRunTimeChart(List<AggregeteResultHistory> list){
		
		String tps = path + Configure.FILE_SEPARATOR + name + "_tps_rt.png";
		String threads = path + Configure.FILE_SEPARATOR + name+ "_threads_rt.png";
		String traffic = path + Configure.FILE_SEPARATOR + name+ "_traffic_rt.png";
		String rt = path + Configure.FILE_SEPARATOR + name + "_rt_rt.png";

		tps = tps.replace("/\\", "/").replace("\\", "/");
		threads = threads.replace("/\\", "/").replace("\\", "/");
		traffic = traffic.replace("/\\", "/").replace("\\", "/");
		rt = rt.replace("/\\", "/").replace("\\", "/");
		
		Map<String,Vector<Serie>> vectorMap =new HashMap<String,Vector<Serie>>();
		getPerformanceData(vectorMap,list,configure.getCHART_PIXEX_LIMIT());
		
		String[] testTime = fetchRunTime(list);
		List<AggregeteResultHistory> l=handler(list,configure.getCHART_PIXEX_LIMIT());
		ChartPojo<AggregeteResultHistory> tpsPojo=new ChartPojo<AggregeteResultHistory>();
		tpsPojo.setBuildTime(testTime);
		tpsPojo.setHeight(height);
		tpsPojo.setList(l);
		tpsPojo.setPicSaveAs(tps);
		tpsPojo.setSeries(vectorMap.get("tps"));
		tpsPojo.setTheme(Configure.PNG_TPS_THEME);
		tpsPojo.setWidth(width);
		tpsPojo.setxDesc(Configure.PNG_TPS_XAXIS);
		tpsPojo.setyDesc(Configure.PNG_TPS_YAXIS);
		
		ChartPojo<AggregeteResultHistory> threadsPojo=new ChartPojo<AggregeteResultHistory>();
		threadsPojo.setBuildTime(testTime);
		threadsPojo.setHeight(height);
		threadsPojo.setList(l);
		threadsPojo.setPicSaveAs(threads);
		threadsPojo.setSeries(vectorMap.get("threads"));
		threadsPojo.setTheme(Configure.PNG_THREADS_THEME);
		threadsPojo.setWidth(width);
		threadsPojo.setxDesc(Configure.PNG_THREADS_XAXIS);
		threadsPojo.setyDesc(Configure.PNG_THREADS_YAXIS);
		
		ChartPojo<AggregeteResultHistory> networkPojo=new ChartPojo<AggregeteResultHistory>();
		networkPojo.setBuildTime(testTime);
		networkPojo.setHeight(height);
		networkPojo.setList(l);
		networkPojo.setPicSaveAs(traffic);
		networkPojo.setSeries(vectorMap.get("traffic"));
		networkPojo.setTheme(Configure.PNG_ENTWORK_THEME);
		networkPojo.setWidth(width);
		networkPojo.setxDesc(Configure.PNG_NETWORK_XAXIS);
		networkPojo.setyDesc(Configure.PNG_NETWORK_YAXIS);
		
		ChartPojo<AggregeteResultHistory> rtPojo=new ChartPojo<AggregeteResultHistory>();
		rtPojo.setBuildTime(testTime);
		rtPojo.setHeight(height);
		rtPojo.setList(l);
		rtPojo.setPicSaveAs(rt);
		rtPojo.setSeries(vectorMap.get("rt"));
		rtPojo.setTheme(Configure.PNG_RT_THEME);
		rtPojo.setWidth(width);
		rtPojo.setxDesc(Configure.PNG_RT_XAXIS);
		rtPojo.setyDesc(Configure.PNG_RT_YAXIS);

		final Map<String,ChartPojo<AggregeteResultHistory>> charMap=new HashMap<String,ChartPojo<AggregeteResultHistory>>();
		charMap.put("tpsPNG_rt", tpsPojo);
		charMap.put("threadsPNG_rt", threadsPojo);
		charMap.put("trafficPNG_rt", networkPojo);
		charMap.put("rtPNG_rt", rtPojo);
		
		ExecutorService pool=Executors.newFixedThreadPool(charMap.size());
		count=charMap.size();
		
		for(final String key:charMap.keySet()){
			pool.execute(new MyRunnable<AggregeteResultHistory>(this,key,charMap.get(key)));
		}
		
		while (!mExit){
			try{
				Thread.sleep(10);
			}catch(Exception ex){
				
			}
		}
		pool.shutdown();
	
		return map;
	}
	
	private String[] fetchRunTime(List<AggregeteResultHistory> list){
		String[] runTime=new String[]{};
		List<AggregateResult> li=list.get(0).getList();
		List<String> l=new ArrayList<String>();
		int size=li.size();
		for(int i=0;i<size;i+=configure.getCHART_PIXEX_LIMIT()){
			l.add(li.get(i).getStart().substring(8, 14));
		}
		
		runTime=l.toArray(runTime);

		return runTime;
	}
	
	private String createChart(ChartPojo<?> pojo){
		DefaultCategoryDataset dataset = ChartUtil.createDefaultCategoryDataset(pojo.getSeries(), pojo.getBuildTime());

		JFreeChart chart = setChartProperties(dataset, pojo.getTheme(), pojo.getxDesc(), pojo.getyDesc());

		try {
			ChartUtil.saveAsFile(chart, pojo.getPicSaveAs(), pojo.getWidth(), pojo.getHeight());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return pojo.getPicSaveAs();
	}

	private JFreeChart setChartProperties(DefaultCategoryDataset dataset,String theme, String xDesc, String yDesc) {
		JFreeChart chart = null;
		// 2：创建Chart[创建不同图形]
		chart = ChartFactory.createLineChart(theme, xDesc, yDesc, dataset,PlotOrientation.VERTICAL, true, true, true);
		// 3:设置抗锯齿，防止字体显示不清楚
		ChartUtil.setAntiAlias(chart);// 抗锯齿
		// 4:对柱子进行渲染[[采用不同渲染]]
		ChartUtil.setLineRender(chart.getCategoryPlot(), false, true);//
		// 5:对其他部分进行渲染
		ChartUtil.setXAixs(chart.getCategoryPlot());// X坐标轴渲染
		ChartUtil.setYAixs(chart.getCategoryPlot());// Y坐标轴渲染
		// 设置标注无边框
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		// 6:使用chartPanel接收
		return chart;
	}

	private void getPerformanceData(Map<String,Vector<Serie>> map, List<AggregeteResultHistory> list,int step) {
		Vector<Serie> tps=new Vector<Serie>();
		Vector<Serie> rt=new Vector<Serie>();
		Vector<Serie> threads=new Vector<Serie>();
		Vector<Serie> traffic=new Vector<Serie>();
		
		for (AggregeteResultHistory ad : list) {

			String samplerName = ad.getSamplerName();
			List<AggregateResult> arlist = ad.getList();
			int size = arlist.size()/step;
			Object[] tpsArray = new Object[size];
			Object[] rtArray = new Object[size];
			Object[] threadsArray = new Object[size];
			Object[] trafficArray = new Object[size];
			
			int t=arlist.size();
			int count=0;
			for(int i=0;i<t;i+=step){
				tpsArray[count] = arlist.get(i).getThroughput();
				rtArray[count]=arlist.get(i).getAverage();
				threadsArray[count]=arlist.get(i).getThreads();
				trafficArray[count]=arlist.get(i).getNetWorkTraffic();
				count++;
			}

			tps.add(new Serie(samplerName, tpsArray));
			rt.add(new Serie(samplerName,rtArray));
			threads.add(new Serie(samplerName, threadsArray));
			traffic.add(new Serie(samplerName,trafficArray));
		}
		map.put("tps", tps);
		map.put("rt", rt);
		map.put("threads", threads);
		map.put("traffic", traffic);
	}

	private List<AggregeteResultHistory> handler(List<AggregeteResultHistory> list,int count){
		List<AggregeteResultHistory> li=new ArrayList<AggregeteResultHistory>();
		for(AggregeteResultHistory arh:list){
			AggregeteResultHistory ar=new AggregeteResultHistory();
			ar.setFileName(arh.getFileName());
			ar.setSamplerName(arh.getSamplerName());
			List<AggregateResult> l=arh.getList();
			List<AggregateResult> l1=new ArrayList<AggregateResult>();
			int size=l.size();
			for(int i=0;i<size;i+=count){
				l1.add(l.get(i));
			}
			ar.setList(l1);
			li.add(ar);
		}

		return li;
	}
	
	private String[] fetchStartTestTime(List<AggregeteResultHistory> list) {
		String[] buildTime = new String[] {};
		AggregeteResultHistory ad = list.get(0);
		List<AggregateResult> arList = ad.getList();
		Set<String> set = new TreeSet<String>();
		for (AggregateResult ar : arList) {
			set.add(ar.getStart());
		}

		buildTime = set.toArray(buildTime);
		return buildTime;
	}
	

	public void notify(Map<String, Object> m) {
		synchronized(synObj){
			count--;
			this.map.putAll(m);
			
			if(count<=0){
				mExit=true;
			}
		}
	}
}
