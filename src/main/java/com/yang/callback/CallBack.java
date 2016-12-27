package com.yang.callback;

import java.util.List;
import java.util.Map;

/** 
 * @项目名称：jmeter-analysisReport-maven-plugin 
 * @类名称：CallBack 
 * @类描述： 
 * @看云：
 * @url：
 * @创建人：jerry 
 * @作者单位：三一重工流程信息化总部 
 * @联系方式：jerry@hisany.com
 * @创建时间：2016年12月21日 下午3:18:41 
 * @version 
 */
public interface CallBack {
	
	public void notify(Map<String, String> detail,List<Object> total);

}
