package com.yang.mojo;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.yang.configration.Configure;
import com.yang.pojo.ConfigurationCharts;
import com.yang.pojo.Jdbc;
import com.yang.report.CreateMainHTMLReports;

import java.util.HashSet;
import java.util.Set;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * 
 * @phase process-sources
 */
@Mojo(name = "analysis")
public class JMeterMojo extends AbstractMojo {
	@Parameter(required = true)
	private String projectName;

	@Parameter(required = true, defaultValue = "${basedir}")
	private String projectHome;

	@Parameter(defaultValue = "${timeStamp}")
	private String buildTime;

	@Parameter(required = true, defaultValue = "10")
	private int workingThreads;

	@Parameter(required = true, defaultValue = "${project.build.directory}/target/jmeter/results/")
	private String jmeterResultPath;

	@Parameter(defaultValue = ".jtl")
	private String jmeterResultPostFix;

	@Parameter(required = true, defaultValue = "${project.build.directory}/target/jmeter/reports/")
	private String htmlReportOutputPath;

	@Parameter()
	private Set<String> ignoreSamplerNames = new HashSet<String>();

	@Parameter(required = true)
	private Jdbc jdbc;

	@Parameter(required = true, defaultValue = "10")
	private int fetchLimit;

	@Parameter(required = true, defaultValue = "10")
	private int chartPointInterval;
	
	@Parameter(required = true, defaultValue = "yyyyMMddHHmmss")
	private String dateFormat;

	@Parameter(required = true, defaultValue = "true")
	private boolean removeJTLAfterHandler;
	
	@Parameter
	private ConfigurationCharts configurationCharts;

	@Parameter(defaultValue = "utf-8")
	private String charEncoder;

	private Log log = getLog();

	
	public void execute() throws MojoExecutionException {
		configureInitialization();
		log.info("********************handler start....********************");
		long t1 = System.currentTimeMillis();

		try {
			new CreateMainHTMLReports().handler();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		long t2 = System.currentTimeMillis();
		log.info("handler these jtl files total cost " + (t2 - t1) + "ms.");
		log.info("********************handler finished....********************");
	}

	private void configureInitialization() {
		Configure configure = Configure.getConfigure();
		configure.setProjectHome(projectHome);
		configure.setJmeterResultPath(jmeterResultPath);
		configure.setHtmlReportOutputPath(htmlReportOutputPath);
		configure.setIgnoreSamplerNames(ignoreSamplerNames);
		configure.setJdbc(jdbc);
		configure.setFetchLimit(fetchLimit);
		configure.setDateFormat(dateFormat);
		configure.setConfigurationCharts(configurationCharts);
		configure.setLogger(log);
		configure.setCharEncoder(charEncoder);
		configure.setJmeterResultPostFix(jmeterResultPostFix);
		configure.setWorkingThreads(workingThreads);
		configure.setBuildTime(buildTime);
		configure.setProjectName(projectName);
		configure.setCHART_PIXEX_LIMIT(chartPointInterval);
		configure.setRemoveJTLAfterHandler(removeJTLAfterHandler);
	}
}
