package com.yang.pojo;

import java.io.Serializable;
import java.util.Vector;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：Serie
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月6日 下午7:54:37
 * @version
 */
public class Serie implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;// 名字
	private Vector<Object> data;// 数据值ֵ

	public Serie() {

	}

	/**
	 * 
	 * @param name
	 *            名称（线条名称）
	 * @param data
	 *            数据（线条上的所有数据值）
	 */
	public Serie(String name, Vector<Object> data) {

		this.name = name;
		this.data = data;
	}

	/**
	 * 
	 * @param name
	 *            名称（线条名称）
	 * @param array
	 *            数据（线条上的所有数据值）
	 */
	public Serie(String name, Object[] array) {
		this.name = name;
		if (array != null) {
			data = new Vector<Object>(array.length);
			for (int i = 0; i < array.length; i++) {
				data.add(array[i]);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<Object> getData() {
		return data;
	}

	public void setData(Vector<Object> data) {
		this.data = data;
	}

}
