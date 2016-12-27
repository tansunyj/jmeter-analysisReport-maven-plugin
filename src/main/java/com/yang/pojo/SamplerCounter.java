package com.yang.pojo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：ResultSummer
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月6日 下午5:03:40
 * @version
 */
public class SamplerCounter {

	private String label;
	private int totalSamplers = 0;
	private int errorSamplers = 0;
	private Set<String> threads;
	private Map<String,Integer> errorMSG;
	private List<Long> rt;
	private long responseTime = 0;
	private long traffic = 0;
	private long min = 0;
	private long max = 0;
	private long start = 0;
	private long end = 0;
	private long t = 0;
	private long lt = 0;

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public Set<String> getThreads() {
		return threads;
	}

	public void setThreads(Set<String> threads) {
		this.threads = threads;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getTotalSamplers() {
		return totalSamplers;
	}

	public void setTotalSamplers(int totalSamplers) {
		this.totalSamplers = totalSamplers;
	}

	public int getErrorSamplers() {
		return errorSamplers;
	}

	public void setErrorSamplers(int errorSamplers) {
		this.errorSamplers = errorSamplers;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public long getTraffic() {
		return traffic;
	}

	public void setTraffic(long traffic) {
		this.traffic = traffic;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public List<Long> getRt() {
		return rt;
	}

	public void setRt(List<Long> rt) {
		this.rt = rt;
	}

	public long getT() {
		return t;
	}

	public void setT(long t) {
		this.t = t;
	}

	public long getLt() {
		return lt;
	}

	public void setLt(long lt) {
		this.lt = lt;
	}

	public Map<String,Integer> getErrorMSG() {
		return errorMSG;
	}

	public void setErrorMSG(Map<String,Integer> errorMSG) {
		this.errorMSG = errorMSG;
	}

}
