# jmeter-analysisReport-maven-plugin

the plugin parser jmeter XML results and generate html reporters only

while using the plugin in a maven project,please install like this:
a.downLoad the plugin;
b.maven install the plugin in the eclipse;
c.execute the sql scripts "~/src/main/resources/sql/mysql.sql" on the database(eg:mysql);
d.unzip the rar file "~/samplers/zhaoSheBeiAPI.rar" or create a new maven project like this;
e.set the maven project's pom.xml like this:


<!--first:generate the maven buildtime-->

			<plugin>
			
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>build-helper-maven-plugin</artifactId>
				<version>1.9.1</version>
				<executions>
					<execution>
						<id>timestamp-property</id>
						<phase>test</phase>
						<goals>
							<goal>timestamp-property</goal>
						</goals>

					</execution>
				</executions>
				<configuration>
					<name>timeStamp</name>
					<pattern>yyyyMMddHHmmss</pattern>
					<timeZone>GMT+8</timeZone>
				</configuration>
			</plugin>
			
<!--second:run jmeter scripts-->			

     			<plugin>
				<configuration>
					<testFilesDirectory>./src/test/jmeter/</testFilesDirectory>
					<testFilesIncluded>
						<jMeterTestFile>*.jmx</jMeterTestFile>
					</testFilesIncluded>
					<suppressJMeterOutput>true</suppressJMeterOutput>
					<testResultsTimestamp>false</testResultsTimestamp>
					
				</configuration>
				<groupId>com.lazerycode.jmeter</groupId>
				<artifactId>jmeter-maven-plugin</artifactId>
				<version>1.9.0</version>
				<executions>
					<execution>
						<id>jmeter-run</id>
						<phase>integration-test</phase>
						<goals>
							<goal>jmeter</goal>
						</goals>
					</execution>
				</executions>
			</plugin>


<!--third:parser jmeter results and generate html reporters-->
			 <plugin>
				<groupId>com.yang</groupId>
				<artifactId>jmeter-analysisReport-maven-plugin</artifactId>
				<version>0.0.2-SNAPSHOT</version>
				<executions>
					<execution>
						<goals>
							<goal>analysis</goal>
						</goals>
						<phase>verify</phase>
						<configuration>
            
                            <!--projectName-->
							<projectName>myProject</projectName>
                            <!--projectHome-->
							<projectHome>${basedir}</projectHome>
                            <!--buildTime-->
							<buildTime>${timeStamp}</buildTime>
                            <!--jmeter result files path-->
							<jmeterResultPath>${project.build.directory}/jmeter/results/</jmeterResultPath>
                            <!--where the html reporters saved-->
							<htmlReportOutputPath>${project.build.directory}/jmeter/reports/</htmlReportOutputPath>
                            <!--ignored sampler names-->
							<ignoreSamplerNames>
								<ignoreSamplerName>sample</ignoreSamplerName>
							</ignoreSamplerNames>
							<jdbc>
								<driver>com.mysql.jdbc.Driver</driver>
								<url>jdbc:mysql://domain:3306/dbname</url>
								<useUnicode>true</useUnicode>
								<characterEncoding>utf-8</characterEncoding>
								<user>userName</user>
								<password>password</password>
							</jdbc>
                             <!--the detail reporters contains how much history builds-->
							<fetchLimit>10</fetchLimit><!-- 历史记录中列举多少条记录 -->
                             <!--the runtime charts samplers interval time-->
							<chartPointInterval>10</chartPointInterval><!-- 运行时结果中每隔多少秒采样一次 -->
							<dateFormat>yyyyMMddHHmmss</dateFormat>
							<charEncoder>utf-8</charEncoder>
                             <!--delete the jtl files after parser-->
							<removeJTLAfterHandler>false</removeJTLAfterHandler>
							<configurationCharts>
								<width>950</width>
								<height>500</height>
							</configurationCharts>
						</configuration>
					</execution>
				</executions>

			</plugin>
