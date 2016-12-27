package com.yang.pojo;

/**
 * @项目名称：jmeter-analysisReport-maven-plugin
 * @类名称：JdbcPoJo
 * @类描述：
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年12月12日 下午2:34:27
 * @version
 */
public class Jdbc {

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public boolean isUseUnicode() {
		return useUnicode;
	}

	public void setUseUnicode(boolean useUnicode) {
		this.useUnicode = useUnicode;
	}

	private String driver;
	private String url;
	private String characterEncoding;
	private boolean useUnicode;
	private String user;
	private String password;
}
