package com.yang.configration;

import java.util.Set;

import org.apache.maven.plugin.logging.Log;

import com.yang.pojo.ConfigurationCharts;
import com.yang.pojo.Jdbc;

public class Configure {

	private static Configure configure = null;

	private Configure() {
	}

	public static Configure getConfigure() {

		if (configure == null) {
			configure = new Configure();
		}
		return configure;
	}

	private String projectName;

	private int workingThreads;

	private String projectHome;

	private String buildTime;

	private String jmeterResultPath;

	private String htmlReportOutputPath;

	private String dateFormat;
	
	private ConfigurationCharts configurationCharts;

	private Set<String> IgnoreSamplerNames;

	private Jdbc jdbc;

	private int fetchLimit;

	private Log logger;

	private String CHAR_ENCODER = "utf-8";

	private String jmeterResultPostFix = ".jtl";

	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	public static final String AGGREGATE_SAVE = "insert into `tab_jmeter_aggregate_result`(`buildTime`,`projectName`,`fileName`,`samplerName`,`threadsCount`,`totalSamplers`,`errorSamplers`,`average`,`median`,`line90`,`min`,`max`,`pass`,`tps`,`traffic`,`duration`,`start`,`end`) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String QUERY = "select `buildTime`,`projectName`,`fileName`,`samplerName`,`threadsCount`,`totalSamplers`,`errorSamplers`,`average`,`median`,`line90`,`min`,`max`,`pass`,`tps`,`traffic`,`duration`,`start`,`end` from `tab_jmeter_aggregate_result` where projectName=? and fileName=? and samplerName=? order by `buildTime` asc limit ?";

	public static final String QUERY_BY_NAME = "select DISTINCT samplerName from tab_jmeter_aggregate_result where projectName=? and fileName=? order by samplerName asc";

	public static final String QUERY_DETAILS = "select `buildTime`,`projectName`,`fileName`,`samplerName`,`threadsCount`,`totalSamplers`,`errorSamplers`,`average`,`median`,`line90`,`min`,`max`,`pass`,`tps`,`traffic`,`duration`,`start`,`end` from `tab_jmeter_aggregate_result` where projectName=? and fileName=? order by `buildTime` desc,`samplerName` asc limit ?";

	public static int FILE_SIZE = 1024 * 1024 * 50;

	public static final String PNG_TPS_THEME = "tps(单位:请求数/秒)";

	public static final String PNG_TPS_XAXIS = "测试时间";

	public static final String PNG_TPS_YAXIS = "tps";

	public static final String PNG_ENTWORK_THEME = "网络吞吐量(单位:KB/s)";

	public static final String PNG_NETWORK_XAXIS = "测试时间";

	public static final String PNG_NETWORK_YAXIS = "网络流量";

	public static final String PNG_RT_THEME = "响应时间(单位:毫秒)";

	public static final String PNG_RT_XAXIS = "测试时间";

	public static final String PNG_RT_YAXIS = "平均响应时间";

	public static final String PNG_THREADS_THEME = "并发线程(单位:个)";

	public static final String PNG_THREADS_XAXIS = "测试时间";

	public static final String PNG_THREADS_YAXIS = "线程数";

	public static final String TEMPLATE_DETAILS = "details.ftl";

	public static final String TEMPLATE_MAIN = "main.ftl";

	public static final String DECIMAL_FORMAT="###0.00";
	
	private int CHART_PIXEX_LIMIT=15;
	
	private boolean removeJTLAfterHandler=true;
	
	public String getProjectHome() {
		return projectHome;
	}

	public void setProjectHome(String projectHome) {
		this.projectHome = projectHome;
	}

	public String getJmeterResultPath() {
		return jmeterResultPath;
	}

	public void setJmeterResultPath(String jmeterResultPath) {
		this.jmeterResultPath = jmeterResultPath;
	}

	public String getHtmlReportOutputPath() {
		return htmlReportOutputPath;
	}

	public void setHtmlReportOutputPath(String htmlReportOutputPath) {
		this.htmlReportOutputPath = htmlReportOutputPath;
	}

	public Set<String> getIgnoreSamplerNames() {
		return IgnoreSamplerNames;
	}

	public void setIgnoreSamplerNames(Set<String> ignoreSamplerNames) {
		IgnoreSamplerNames = ignoreSamplerNames;
	}

	public int getFetchLimit() {
		return fetchLimit;
	}

	public void setFetchLimit(int fetchLimit) {
		this.fetchLimit = fetchLimit;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public ConfigurationCharts getConfigurationCharts() {
		return configurationCharts;
	}

	public void setConfigurationCharts(ConfigurationCharts configurationCharts) {
		this.configurationCharts = configurationCharts;
	}

	public Log getLogger() {
		return logger;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public String getCharEncoder() {
		return CHAR_ENCODER;
	}

	public void setCharEncoder(String charEncoder) {
		this.CHAR_ENCODER = charEncoder;
	}

	public String getJmeterResultPostFix() {
		return jmeterResultPostFix;
	}

	public void setJmeterResultPostFix(String jmeterResultPostFix) {
		this.jmeterResultPostFix = jmeterResultPostFix;
	}

	public int getWorkingThreads() {
		return workingThreads;
	}

	public void setWorkingThreads(int workingThreads) {
		this.workingThreads = workingThreads;
	}

	public Jdbc getJdbc() {
		return jdbc;
	}

	public void setJdbc(Jdbc jdbc) {
		this.jdbc = jdbc;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getCHART_PIXEX_LIMIT() {
		return CHART_PIXEX_LIMIT;
	}

	public void setCHART_PIXEX_LIMIT(int cHART_PIXEX_LIMIT) {
		CHART_PIXEX_LIMIT = cHART_PIXEX_LIMIT;
	}

	public boolean isRemoveJTLAfterHandler() {
		return removeJTLAfterHandler;
	}

	public void setRemoveJTLAfterHandler(boolean removeJTLAfterHandler) {
		this.removeJTLAfterHandler = removeJTLAfterHandler;
	}

}
