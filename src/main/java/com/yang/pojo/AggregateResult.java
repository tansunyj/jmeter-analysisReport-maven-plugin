package com.yang.pojo;

import java.util.Map;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：AggregateResult
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月6日 下午4:10:34
 * @version
 */
public class AggregateResult {

	private String label;
	private int threads;
	private int totalSamplers;
	private int errorSamplers;
	private long average;
	private long median;
	private long line90;
	private long min;
	private long max;
	private String throughput;
	private String netWorkTraffic;
	private String fileName;
	private int duration;
	private String start;
	private String end;
	private String buildTime;
	private String pass;
	private Map<String,Integer> errMSG;

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getErrorSamplers() {
		return errorSamplers;
	}

	public void setErrorSamplers(int errorSamplers) {
		this.errorSamplers = errorSamplers;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public int getTotalSamplers() {
		return totalSamplers;
	}

	public void setTotalSamplers(int totalSamplers) {
		this.totalSamplers = totalSamplers;
	}

	public long getAverage() {
		return average;
	}

	public void setAverage(long average) {
		this.average = average;
	}

	public long getMedian() {
		return median;
	}

	public void setMedian(long median) {
		this.median = median;
	}

	public long getLine90() {
		return line90;
	}

	public void setLine90(long line90) {
		this.line90 = line90;
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

	public String getThroughput() {
		return throughput;
	}

	public void setThroughput(String throughput) {
		this.throughput = throughput;
	}

	public String getNetWorkTraffic() {
		return netWorkTraffic;
	}

	public void setNetWorkTraffic(String netWorkTraffic) {
		this.netWorkTraffic = netWorkTraffic;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public Map<String,Integer> getErrMSG() {
		return errMSG;
	}

	public void setErrMSG(Map<String,Integer> errMSG) {
		this.errMSG = errMSG;
	}

}
