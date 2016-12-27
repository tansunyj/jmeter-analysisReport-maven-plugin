package com.yang.calculate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;

import com.yang.configration.Configure;
import com.yang.parser.SAXParserSampler;
import com.yang.pojo.AggregateResult;
import com.yang.pojo.AggregeteResultHistory;
import com.yang.pojo.Sampler;
import com.yang.pojo.SamplerCounter;
import com.yang.util.FormatUtil;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：Calculate
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月6日 下午4:08:05
 * @version
 */
public final class Calculate {
	private static Configure configure = Configure.getConfigure();
	private static String seperator = Configure.FILE_SEPARATOR;
	private static String postFix = configure.getJmeterResultPostFix();
	private static String fileName = "";
	private static List<Sampler> list;
	
	public static List<AggregateResult> calculate(File file)
			throws DocumentException {

		fileName = file.getName().replace(postFix, "");
		fileName = fileName.substring(fileName.lastIndexOf(seperator) + 1,fileName.length());
		list=parser(file);
	//	return calculate(statistics(Dom4jParserSampler.parser(file)));
		return calculate(statistics(list));
	}

	private static List<Sampler> parser(File file){
		if(list==null)
			list=new SAXParserSampler().parser(file);
		return list;
	}
	
	/**
	 * 该方法按照samplerName分组计算性能聚合报告结果
	 * 
	 * @param map
	 * @return
	 */
	private static List<AggregateResult> calculate(Map<String, SamplerCounter> map) {

		List<AggregateResult> list = new ArrayList<AggregateResult>();

		FormatUtil fu=new FormatUtil();
		
		for (String key : map.keySet()) {
			SamplerCounter sc = map.get(key);
			AggregateResult ar = new AggregateResult();
			int total = sc.getTotalSamplers();

			// 该samplerName的平均响应时间
			ar.setAverage(sc.getResponseTime() / total);
			// 断言错误的sampler数
			ar.setErrorSamplers(sc.getErrorSamplers());

			// 错误响应集合
			ar.setErrMSG(sc.getErrorMSG());

			// 保存中位数
			long[] medianAndLine90=getMedianAndLine90(sc.getRt());
			ar.setMedian(medianAndLine90[0]);

			// 保存90%line
			ar.setLine90(medianAndLine90[1]);

			// 保存文件名
			ar.setFileName(fileName);
			// 保存samplerName
			ar.setLabel(key);
			// 保存响应时间最大值
			ar.setMax(sc.getMax());
			// 保存响应时间最小值
			ar.setMin(sc.getMin());

			// 计算网络吞吐量
			ar.setNetWorkTraffic(fu.Decimal2String(sc.getTraffic()* 1000/ ((sc.getEnd() - sc.getStart() + sc.getLt()) * 1024)));
			// 计算线程数
			ar.setThreads(sc.getThreads().size());
			// 保存总请求数
			ar.setTotalSamplers(total);
			// 保存执行时间
			ar.setDuration((int) Math.ceil(sc.getEnd() - sc.getStart()+ sc.getT()) / 1000);

			// 保存终止执行时间
			ar.setEnd(fu.unixTimeStamp2String(sc.getEnd()));
			// 保存开始执行时间
			ar.setStart(fu.unixTimeStamp2String(sc.getStart()));
			/**
			 * 参照jmeter计算tps的方式进行计算；
			 */
			// 计算tps
			ar.setThroughput(fu.Decimal2String(total* 1000 / (sc.getEnd() - sc.getStart() + sc.getT())));
			// 计算通过率
			ar.setPass(fu.Decimal2String((total - sc.getErrorSamplers()) * 100 / total));

			list.add(ar);
		}

		return list;
	}

	/**
	 * 该方法按samplerName分组统计各性能指标总数
	 * 
	 * @param map
	 * @param sampler
	 */
	private static Map<String, SamplerCounter> statistics(List<Sampler> list) {

		Map<String, SamplerCounter> map = new HashMap<String, SamplerCounter>();

		for (Sampler sampler : list) {

			String lb = sampler.getLb();
			if (map.containsKey(lb)) {
				SamplerCounter rs = map.get(lb);
				rs.setErrorSamplers(rs.getErrorSamplers()+ (sampler.isS() ? 0 : 1));
				rs.setMax(rs.getMax() >= sampler.getT() ? rs.getMax() : sampler.getT());
				rs.setMin(rs.getMin() <= sampler.getT() ? rs.getMin() : sampler.getT());
				rs.setResponseTime(rs.getResponseTime() + sampler.getT());

				if (rs.getEnd() >= sampler.getTs()) {
					rs.setEnd(rs.getEnd());
				} else {
					rs.setEnd(sampler.getTs());
					rs.setT(sampler.getT());
					rs.setLt(sampler.getLt());
				}

				rs.setStart(rs.getStart() <= sampler.getTs() ? rs.getStart(): sampler.getTs());
				Set<String> threads = rs.getThreads();
				threads.add(sampler.getTn());
				rs.setThreads(threads);

				Map<String,Integer> errMSG = rs.getErrorMSG();

				if (!sampler.isS()) {
					if(errMSG.containsKey(sampler.getRm())){
						int count=errMSG.get(sampler.getRm());
						errMSG.put(sampler.getRm(),count+1);
					}else{
						errMSG.put(sampler.getRm(),1);
					}
				}
				rs.setErrorMSG(errMSG);

				List<Long> coll = rs.getRt();
				coll.add((long) sampler.getT());
				rs.setRt(coll);

				rs.setTotalSamplers(rs.getTotalSamplers() + 1);
				rs.setTraffic(rs.getTraffic() + sampler.getBy());

			} else {
				SamplerCounter rs = new SamplerCounter();
				rs.setEnd(sampler.getTs());
				rs.setErrorSamplers((sampler.isS() ? 0 : 1));
				rs.setLabel(lb);
				rs.setMax(sampler.getT());
				rs.setMin(sampler.getT());
				rs.setResponseTime(sampler.getT());
				rs.setStart(sampler.getTs());

				Set<String> threads = new HashSet<String>();
				threads.add(sampler.getTn());
				rs.setThreads(threads);

				Map<String,Integer> errMSG = new HashMap<String,Integer>();
				if (!sampler.isS()) {
					errMSG.put(sampler.getRm(),1);
				}
				rs.setErrorMSG(errMSG);

				List<Long> coll = new ArrayList<Long>();
				coll.add((long) sampler.getT());
				rs.setRt(coll);

				rs.setTotalSamplers(1);
				rs.setTraffic(sampler.getBy());
				rs.setT(sampler.getT());
				rs.setLt(sampler.getLt());

				map.put(lb, rs);

			}
		}

		return map;
	}
	
	public static List<AggregeteResultHistory> calculateRunTime(File file) {
		List<AggregeteResultHistory> m=new ArrayList<AggregeteResultHistory>();
		Map<String,Sampler[]> map=handler(parser(file));
		for(String key:map.keySet()){
			AggregeteResultHistory arh=new AggregeteResultHistory();
			List<Sampler> li=new ArrayList<Sampler>();
			List<AggregateResult> li2=new ArrayList<AggregateResult>();
			Sampler[] samplerArray=map.get(key);

			long ts=samplerArray[0].getTs()/1000;
			for(Sampler s:samplerArray){
				if(s.getTs()/1000==ts){
					li.add(s);
				}else{
					li2.addAll(calculate(statistics(li)));
					li.clear();
					ts=s.getTs()/1000;
				}
			}
			arh.setFileName(fileName);
			arh.setSamplerName(key);
			arh.setList(li2);
			m.add(arh);
		}

		return m;
	}
		
	private static Map<String,Sampler[]> handler(List<Sampler> list){
		Map<String,List<Sampler>> map=new HashMap<String,List<Sampler>>();
		Map<String,Sampler[]> m=new HashMap<String,Sampler[]>();
		
		for(Sampler sampler:list){
			String lb=sampler.getLb();
			if(map.containsKey(lb)){
				List<Sampler> li=map.get(lb);
				li.add(sampler);
				map.put(lb, li);
			}else{
				List<Sampler> samplerList=new ArrayList<Sampler>();
				samplerList.add(sampler);
				map.put(lb, samplerList);
			}
		}
		
		for(String key:map.keySet()){
			List<Sampler> li=map.get(key);
			Sampler[] samplerArray=new Sampler[]{};
			Collections.sort(li);
			samplerArray=li.toArray(samplerArray);
			
			m.put(key, samplerArray);
		}

		return m;
	}
	
	private static long[] getMedianAndLine90(List<Long> coll){
		Long[] lo = new Long[]{};
		Collections.sort(coll);
		lo = coll.toArray(lo);
		int size = lo.length;

		if(size>1){

			return new long[]{size%2==0?((lo[size>>1] + lo[(size + 1)>>1])>>1):lo[(size + 1)>>1],lo[size * 9 / 10]};
		}else{
			return new long[]{lo[0],lo[0]};
		}
	}

}
