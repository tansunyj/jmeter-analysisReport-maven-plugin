package com.yang.pojo;

import java.io.Serializable;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：AttributionPojo
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月6日 下午2:27:38
 * @version
 */
public class Sampler implements Comparable<Sampler>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2107187923910285616L;
	private int t;
	private int lt;
	private long ts;
	private boolean s;
	private String lb;
	private String rc;
	private String rm;
	private String tn;
	private String dt;
	private int by;

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getLt() {
		return lt;
	}

	public void setLt(int lt) {
		this.lt = lt;
	}

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

	public boolean isS() {
		return s;
	}

	public void setS(boolean s) {
		this.s = s;
	}

	public String getLb() {
		return lb;
	}

	public void setLb(String lb) {
		this.lb = lb;
	}

	public String getRc() {
		return rc;
	}

	public void setRc(String rc) {
		this.rc = rc;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public int getBy() {
		return by;
	}

	public void setBy(int by) {
		this.by = by;
	}

	@Override
	public String toString() {
		return "Sampler [t=" + t + ", lt=" + lt + ", ts=" + ts + ", s=" + s
				+ ", lb=" + lb + ", rc=" + rc + ", rm=" + rm + ", tn=" + tn
				+ ", dt=" + dt + ", by=" + by + "]";
	}

	public int compareTo(Sampler o) {
		
		return this.ts<o.ts?-1:(this.ts==o.ts?0:1);
	}

}
