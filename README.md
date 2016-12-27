# jmeter-analysisReport-maven-plugin

while using the plugin in a maven project,please set as follows:

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
