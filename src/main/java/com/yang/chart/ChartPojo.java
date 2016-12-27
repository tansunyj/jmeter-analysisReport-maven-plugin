package com.yang.chart;

import java.util.List;
import java.util.Vector;

import com.yang.pojo.AggregeteResultHistory;
import com.yang.pojo.Serie;

/** 
 * @项目名称：jmeter-analysisReport-maven-plugin 
 * @类名称：ChartPojo 
 * @类描述： 
 * @看云：
 * @url：
 * @创建人：jerry 
 * @作者单位：三一重工流程信息化总部 
 * @联系方式：jerry@hisany.com
 * @创建时间：2016年12月19日 下午7:27:05 
 * @version 
 */
public class ChartPojo<T> {

	private List<T> list;
	private Vector<Serie> series;
	private String[] buildTime;
	private String theme;
	private String xDesc;
	private String yDesc;
	private String picSaveAs;
	private int width;
	private int height;
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Vector<Serie> getSeries() {
		return series;
	}
	public void setSeries(Vector<Serie> series) {
		this.series = series;
	}
	public String[] getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(String[] buildTime) {
		this.buildTime = buildTime;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getxDesc() {
		return xDesc;
	}
	public void setxDesc(String xDesc) {
		this.xDesc = xDesc;
	}
	public String getyDesc() {
		return yDesc;
	}
	public void setyDesc(String yDesc) {
		this.yDesc = yDesc;
	}
	public String getPicSaveAs() {
		return picSaveAs;
	}
	public void setPicSaveAs(String picSaveAs) {
		this.picSaveAs = picSaveAs;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
