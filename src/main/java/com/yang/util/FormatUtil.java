package com.yang.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yang.configration.Configure;

/** 
 * @项目名称：jmeter-analysisReport-maven-plugin 
 * @类名称：FormatUtil 
 * @类描述： 
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月15日 上午9:06:33 
 * @version 
 */
public class FormatUtil {

	public String unixTimeStamp2String(Long timeStamp){

		return new SimpleDateFormat(Configure.getConfigure().getDateFormat()).format(new Date(timeStamp));
	}
	
	public String Decimal2String(Long data){
		
		return new DecimalFormat(Configure.DECIMAL_FORMAT).format(data);
	}

	public String Decimal2String(int data) {

		return new DecimalFormat(Configure.DECIMAL_FORMAT).format(data);
	}
}
