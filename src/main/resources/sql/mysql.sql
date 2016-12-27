CREATE TABLE `tab_jmeter_aggregate_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `buildTime` varchar(20) NOT NULL COMMENT '构建时间',
  `projectName` varchar(50) NOT NULL COMMENT '工程名',
  `fileName` varchar(50) NOT NULL COMMENT 'jmeter结果集文件名',
  `samplerName` varchar(50) NOT NULL COMMENT '各sampler名称',
  `threadsCount` int(11) DEFAULT NULL COMMENT '各sampler线程数',
  `totalSamplers` int(11) DEFAULT NULL COMMENT '各sampler总请求数',
  `errorSamplers` int(11) DEFAULT NULL COMMENT '各sampler总失败请求数',
  `average` int(11) DEFAULT NULL COMMENT '各sampler平均响应时间',
  `median` int(11) DEFAULT NULL COMMENT '各sampler响应时间中值',
  `line90` int(11) DEFAULT NULL COMMENT '各sampler响应时间90%line',
  `min` int(11) DEFAULT NULL COMMENT '各sampler响应时间最小值',
  `max` int(11) DEFAULT NULL COMMENT '各sampler响应时间最大值',
  `tps` varchar(10) DEFAULT NULL COMMENT '各sampler tps',
  `traffic` varchar(20) DEFAULT NULL COMMENT '各sampler每秒网络吞吐量',
  `duration` int(11) DEFAULT NULL COMMENT '各sampler执行时间',
  `start` varchar(20) DEFAULT NULL COMMENT '各sampler开始执行时间',
  `end` varchar(20) DEFAULT NULL COMMENT '各sampler截止执行时间',
  `pass` varchar(10) DEFAULT NULL COMMENT '各sampler成功率',
  PRIMARY KEY (`id`),
  KEY `idx_buildTime` (`buildTime`) USING BTREE,
  KEY `idx_byName` (`projectName`,`fileName`,`samplerName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8;

;
CREATE TABLE `tab_jmeter_aggregate_result_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `buildTime` varchar(20) NOT NULL COMMENT '构建时间',
  `projectName` varchar(50) NOT NULL COMMENT '工程名',
  `fileName` varchar(50) NOT NULL COMMENT 'jmeter结果集文件名',
  `samplerName` varchar(50) NOT NULL COMMENT '各sampler名称',
  `threadsCount` int(11) DEFAULT NULL COMMENT '各sampler线程数',
  `totalSamplers` int(11) DEFAULT NULL COMMENT '各sampler总请求数',
  `errorSamplers` int(11) DEFAULT NULL COMMENT '各sampler总失败请求数',
  `average` int(11) DEFAULT NULL COMMENT '各sampler平均响应时间',
  `median` int(11) DEFAULT NULL COMMENT '各sampler响应时间中值',
  `line90` int(11) DEFAULT NULL COMMENT '各sampler响应时间90%line',
  `min` int(11) DEFAULT NULL COMMENT '各sampler响应时间最小值',
  `max` int(11) DEFAULT NULL COMMENT '各sampler响应时间最大值',
  `tps` varchar(10) DEFAULT NULL COMMENT '各sampler tps',
  `traffic` varchar(20) DEFAULT NULL COMMENT '各sampler每秒网络吞吐量',
  `duration` int(11) DEFAULT NULL COMMENT '各sampler执行时间',
  `start` varchar(20) DEFAULT NULL COMMENT '各sampler开始执行时间',
  `end` varchar(20) DEFAULT NULL COMMENT '各sampler截止执行时间',
  `pass` varchar(10) DEFAULT NULL COMMENT '各sampler成功率',
  PRIMARY KEY (`id`),
  KEY `idx_buildTime` (`buildTime`) USING BTREE,
  KEY `idx_byName` (`projectName`,`fileName`,`samplerName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8;

;
CREATE PROCEDURE `proc_jmeter_aggregate_result_bakup`()
BEGIN
	INSERT INTO tab_jmeter_aggregate_result_history SELECT
		*
	FROM
		tab_jmeter_aggregate_result t
	WHERE
		DATEDIFF(
			DATE_FORMAT(NOW(), '%Y-%m-%d'),
			DATE_FORMAT(t.`start`, '%Y-%m-%d')
		) >= 30;

DELETE
FROM
	tab_jmeter_aggregate_result
WHERE
	DATEDIFF(
		DATE_FORMAT(NOW(), '%Y-%m-%d'),
		DATE_FORMAT(`start`, '%Y-%m-%d')
	) >= 30;

END

;
CREATE EVENT `event_jmeter_aggregate_result_bakup` ON SCHEDULE EVERY 1 DAY STARTS '2016-12-15 18:00:00' ON COMPLETION PRESERVE DO
	CALL proc_jmeter_aggregate_result_bakup ();

