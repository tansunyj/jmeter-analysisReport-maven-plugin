package com.yang.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.yang.configration.Configure;

/**
 * 
 * @项目名称：com.namtso
 * @类名称：TxtUtil
 * @类描述： 文本文件读写类，实现指定文件名后读取、写入功能；
 * @看云：
 * @url：
 * @创建人：杨杰
 * @作者单位：三一重工流程信息化总部
 * @联系方式：jiejie0406@sohu.com
 * @创建时间：2016年6月23日 上午11:01:00
 * @version
 */

public class TxtUtil {

	/**
	 * 
	 * @param readPath
	 *            带路径的文件名
	 * @return 返回文件内容
	 */
	public static String read(File f) {
		StringBuilder readTxt = new StringBuilder(Configure.FILE_SIZE);
		try {
			if (f.isFile() && f.exists()) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), Configure.getConfigure().getCharEncoder()));
				String line = "";
				while ((line = reader.readLine()) != null) {
					readTxt.append(line);
				}
				reader.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return readTxt.toString();
	}
}
