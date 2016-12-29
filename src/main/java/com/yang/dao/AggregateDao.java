package com.yang.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yang.configration.Configure;
import com.yang.pojo.AggregateResult;
import com.yang.pojo.AggregeteResultHistory;
import com.yang.pojo.Jdbc;

/**
 * @项目名称：zhaoSheBeiAPI
 * @类名称：AggregateDao
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月7日 上午8:46:12
 * @version
 */
public class AggregateDao {

	private Connection conn = null;
	private PreparedStatement pstmt;
	private ResultSet rs = null;
	private boolean status = false;
	private Configure configure = Configure.getConfigure();
	private String buildTime = configure.getBuildTime();
	private String projectName = configure.getProjectName();
	private Jdbc jdbc = configure.getJdbc();
	
	private void initial() {

		try {
			Class.forName(jdbc.getDriver()).newInstance();
			conn = DriverManager.getConnection(jdbc.getUrl() + "?useUnicode=" + jdbc.isUseUnicode()+ "&characterEncoding="+ jdbc.getCharacterEncoding(), jdbc.getUser(),jdbc.getPassword());

			status = true;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		}

	}

	public void save(List<AggregateResult> list) {
		PreparedStatement pstmt = null;
		if (!status)
			initial();

		try {
			pstmt = conn.prepareStatement(Configure.AGGREGATE_SAVE);

			for (AggregateResult ar : list) {

				pstmt.setString(1, buildTime);
				pstmt.setString(2, projectName);
				pstmt.setString(3, ar.getFileName());
				pstmt.setString(4, ar.getLabel());
				pstmt.setInt(5, ar.getThreads());
				pstmt.setInt(6, ar.getTotalSamplers());
				pstmt.setInt(7, ar.getErrorSamplers());
				pstmt.setInt(8, (int) ar.getAverage());
				pstmt.setInt(9, (int) ar.getMedian());
				pstmt.setInt(10, (int) ar.getLine90());
				pstmt.setInt(11, (int) ar.getMin());
				pstmt.setInt(12, (int) ar.getMax());
				pstmt.setString(13, ar.getPass());
				pstmt.setString(14, ar.getThroughput());
				pstmt.setString(15, ar.getNetWorkTraffic());
				pstmt.setInt(16, ar.getDuration());
				pstmt.setString(17, ar.getStart());
				pstmt.setString(18, ar.getEnd());
				

				pstmt.addBatch();
			}
			pstmt.executeBatch();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		free();
	}

	public List<AggregeteResultHistory> fetchDataForPNG(List<AggregateResult> arlist,String fileName, int count) {
		List<AggregeteResultHistory> list = new ArrayList<AggregeteResultHistory>();

		Set<String> samplerNames = getSamplerNames(arlist);

		for (String samplerName : samplerNames) {
			AggregeteResultHistory ad = new AggregeteResultHistory();
			ad.setFileName(fileName);
			ad.setSamplerName(samplerName);
			ad.setList(fetch(fileName, samplerName, Configure.QUERY, count));
			list.add(ad);
		}

		return list;
	}

	public List<AggregeteResultHistory> fetchDataForList(List<AggregateResult> arlist,String fileName, int count) {

		List<AggregeteResultHistory> list = new ArrayList<AggregeteResultHistory>();
		Set<String> samplerNames = getSamplerNames(arlist);

		count = samplerNames.size() * count;

		AggregeteResultHistory ad = new AggregeteResultHistory();
		ad.setFileName(fileName);
		ad.setList(fetch(fileName, Configure.QUERY_DETAILS, count));
		list.add(ad);

		return list;
	}

	private List<AggregateResult> fetch(String fileName, String sql, int count) {
		PreparedStatement pstmt = null;

		List<AggregateResult> list = new ArrayList<AggregateResult>();
		initial();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectName);
			pstmt.setString(2, fileName);
			pstmt.setInt(3, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				AggregateResult ar = new AggregateResult();
				ar.setBuildTime(rs.getString("buildTime"));
				ar.setFileName(rs.getString("fileName"));
				ar.setLabel(rs.getString("samplerName"));
				ar.setThreads(rs.getInt("threadsCount"));
				ar.setTotalSamplers(rs.getInt("totalSamplers"));
				ar.setErrorSamplers(rs.getInt("errorSamplers"));
				ar.setAverage(rs.getInt("average"));
				ar.setMedian(rs.getInt("median"));
				ar.setLine90(rs.getInt("line90"));
				ar.setMin(rs.getInt("min"));
				ar.setMax(rs.getInt("max"));
				ar.setPass(rs.getString("pass"));
				ar.setThroughput(rs.getString("tps"));
				ar.setNetWorkTraffic(rs.getString("traffic"));
				ar.setDuration(rs.getInt("duration"));
				ar.setStart(rs.getString("start"));
				ar.setEnd((rs.getString("end")));

				list.add(ar);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return list;
	}

	private List<AggregateResult> fetch(String fileName, String samplerName,
			String sql, int count) {
		PreparedStatement pstmt = null;

		List<AggregateResult> list = new ArrayList<AggregateResult>();
		initial();

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectName);
			pstmt.setString(2, fileName);
			pstmt.setString(3, samplerName);
			pstmt.setInt(4, count);

			rs = pstmt.executeQuery();
			while (rs.next()) {

				AggregateResult ar = new AggregateResult();
				ar.setBuildTime(rs.getString("buildTime"));
				ar.setFileName(rs.getString("fileName"));
				ar.setLabel(rs.getString("samplerName"));
				ar.setThreads(rs.getInt("threadsCount"));
				ar.setTotalSamplers(rs.getInt("totalSamplers"));
				ar.setErrorSamplers(rs.getInt("errorSamplers"));
				ar.setAverage(rs.getInt("average"));
				ar.setMedian(rs.getInt("median"));
				ar.setLine90(rs.getInt("line90"));
				ar.setMin(rs.getInt("min"));
				ar.setMax(rs.getInt("max"));
				ar.setPass(rs.getString("pass"));
				ar.setThroughput(rs.getString("tps"));
				ar.setNetWorkTraffic(rs.getString("traffic"));
				ar.setDuration(rs.getInt("duration"));
				ar.setStart(rs.getString("start"));
				ar.setEnd((rs.getString("end")));

				list.add(ar);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return list;
	}

	private List<String> fetch(String fileName) {
		PreparedStatement pstmt = null;
		List<String> list = new ArrayList<String>();
		if (!status)
			initial();

		try {
			pstmt = conn.prepareStatement(Configure.QUERY_BY_NAME);
			pstmt.setString(1, projectName);
			pstmt.setString(2, fileName);
			ResultSet rs2 = pstmt.executeQuery();

			while (rs2.next()) {
				list.add(rs2.getString(1));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	private void free() {

		if (!status)
			return;

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		status = false;
	}
	
	private Set<String> getSamplerNames(List<AggregateResult> arlist){
		Set<String> set=new HashSet<String>();
		for(AggregateResult ar:arlist){
			set.add(ar.getLabel());
		}
		
		return set;
	}
}
