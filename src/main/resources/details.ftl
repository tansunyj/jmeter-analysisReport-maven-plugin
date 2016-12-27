<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${scriptName}-Details</title>

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
<h2 style="margin:0 auto;text-align:left;"><font color=#545454  size=5><strong>${scriptName}</strong></font></h2>

<br>
<h3 ><font color=#545454  size=4><strong>一、本次运行时性能趋势</strong></h3>
<ul>
	<li>并发线程</li><br/>
	<img src="${threadsPNG_rt}"/>
	<li>tps  </li><br/>
	<img src="${tpsPNG_rt}"/>
	<li>网络吞吐量</li><br/>
	<img src="${trafficPNG_rt}"/>
	<li>响应时间</li><br/>
	<img src="${rtPNG_rt}"/>
</ul>

<br>
<h3 ><font color=#545454  size=4><strong>二、历史性能变化趋势</strong></h3>
<ul>
	<li>并发线程</li><br/>
	<img src="${threadsPNG_his}"/>
	<li>tps  </li><br/>
	<img src="${tpsPNG_his}"/>
	<li>网络吞吐量</li><br/>
	<img src="${trafficPNG_his}"/>
	<li>响应时间</li><br/>
	<img src="${rtPNG_his}"/>
</ul>

<h3 ><font color=#545454  size=4><strong>三、测试历史记录</strong></h3>

<table class="bordered" border=2>
  <tr>
	  <th scope="col" width="100" >脚本名称</th>
	  <th scope="col" width="150" >请求名称</th>
	  <th scope="col" width="150" >测试时间</th>
	  <th scope="col" width="80" >并发线程数</th>  
	  <th scope="col" width="80" >总请求数</th> 
	  <th scope="col" width="50" >TPS</th>	  
	  <th scope="col" width="120" >平均响应时间(ms)</th>
	  <th scope="col" width="150" >响应时间中位数(ms)</th>
	  <th scope="col" width="120" >响应时间90%(ms)</th>	  
	  <th scope="col" width="120" >网络吞吐量(KB/s)</th>
	  <th scope="col" width="80" >成功率(%)</th>	  
  </tr>

  <#list dataList as list1>
  	<#list list1.list as list2>
		<tr>
			<td>${scriptName}</td>
	    	<td>${list2.label?default("N/A")}</td>
	    	<td>${list2.start?default("0")}</td>
	    	<td>${list2.threads?default("N/A")}</td>
	    	<td>${list2.totalSamplers?default("N/A")}</td>
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

</html>
