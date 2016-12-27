package com.yang.pojo;

import java.util.List;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：AggregeteResultHistory
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月8日 上午11:06:40
 * @version
 */
public class AggregeteResultHistory {

	private String fileName;
	private String samplerName;
	private List<AggregateResult> list;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSamplerName() {
		return samplerName;
	}

	public void setSamplerName(String samplerName) {
		this.samplerName = samplerName;
	}

	public List<AggregateResult> getList() {
		return list;
	}

	public void setList(List<AggregateResult> list) {
		this.list = list;
	}

}
