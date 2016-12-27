<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>autoTestReport</title>

<style type="text/css">
body{font-family:'微软雅黑', 'Lucida sans', Arial; font-size:12px;}
table{font-size: 12px; border-collapse:collapse; border-spacing: 0; table-layout:fixed;}
.bordered {border:1px #ccc solid; -webkit-box-shadow:0 1px 1px #ddd; -moz-box-shadow:0 1px 1px #ddd; box-shadow: 0 1px 1px #ddd;}
.bordered tr:hover{background-color:#FFC;}
.bordered td, .bordered th {padding:6px 10px; text-align:left; border-bottom:1px #ddd solid;}
.bordered th{font-size:14px; color:#ffffff; font-family:bold; background-color:#555555;}
.unusual{color:#fff; font-weight:bold; background-color:#F00;}
</style>
</head>

<body>
<h2 style="margin:0 auto;text-align:center;"><font color=#545454  size=5><strong> 自动化性能测试报告 </strong></font></h2>

<h3><strong>0.  说明</strong></h3>

<ol>
	<li style="font-size:24px">项目名称：${projectName}</li>
	<li style="font-size:24px">构建时间：${buildtime}</li>
</ol>
<br>
<h3><strong>1. 概要</strong></h3>
<table class="bordered" border=2>
  <tr>
 
	  <th scope="col" width="150" >脚本名称</th>
	  <th scope="col" width="150" >请求名称</th>	
	  <th scope="col" width="100" >测试时间</th> 	  
	  <th scope="col" width="80" >并发线程数</th> 
	  <th scope="col" width="80" >总请求数</th>  
	  <th scope="col" width="50" >TPS</th>	  
	  <th scope="col" width="120" >平均响应时间(ms)</th>
	  <th scope="col" width="150" >响应时间中位数(ms)</th>
	  <th scope="col" width="150" >90%响应时间(ms)</th>	  
	  <th scope="col" width="120" >网络吞吐量(KB/s)</th>
	  <th scope="col" width="80" >成功率(%)</th>	  
  </tr>

  <#list agList as list>
     <#list list as list2>
		<tr>
	    	<td>${list2.fileName?default("N/A")}.jtl</td>
	    	<td>${list2.label?default("N/A")}</td>
	    	<td>${list2.start?default("0")}</td>	    	
	    	<td>${list2.threads?default("0")}</td>
	    	<td>${list2.totalSamplers?default("0")}</td>
	    	<td>${list2.throughput?default("0")}</td>
	    	<td>${list2.average?default("0")}</td>
	    	<td>${list2.median?default("0")}</td>
	    	<td>${list2.line90?default("0")}</td>
	    	<td>${list2.netWorkTraffic?default("0")}</td>
	    	<td>${list2.pass?default("0")}%</td>
		</tr>
	</#list>
  </#list>

</table>
<br>
<h3><strong>2. 详情</strong></h3>

<table class="bordered" border=2>
  <tr>
	  <th scope="col" width="200" >脚本名称</th>
	  <th scope="col" width="200" style="text-align:center;">详情</th>
  </tr>

  <#list details?keys as key>
	<tr>
		<td>${key}</td>
		<td style="text-align:center;"><a href="${details[key]}">详情</a></td>
	</tr>
  </#list>

</table>

<br>


<h3><strong>3. 错误信息</strong></h3>

<table class="bordered" border=2>
  <tr>
	  <th scope="col" width="150" >脚本名称</th>
	  <th scope="col" width="150" >请求名称</th>
	  <th scope="col" width="400" style="text-align:center;">错误信息</th>
	  <th scope="col" width="80" style="text-align:center;">数量</th>
  </tr>

  <#list agList as list>
     <#list list as list2>
     	<#list list2.errMSG?keys as key>
			<tr>
		    	<td>${list2.fileName?default("N/A")}</td>
		    	<td>${list2.label?default("N/A")}</td>
		    	<td>${key?default("N/A")}</td>
		    	<td>${list2.errMSG[key]?default("N/A")}</td>
			</tr>
		</#list>
	</#list>
  </#list>

</table>





</html>
