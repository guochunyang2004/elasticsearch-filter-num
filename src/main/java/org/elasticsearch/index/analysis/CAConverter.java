package org.elasticsearch.index.analysis;

import org.elasticsearch.index.analysis.util.ChineseArabConvertUtil;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * some parts of code copied from:http://code.google.com/p/java-zhconverter/
 */
public class CAConverter {


	private static CAConverter instance = new CAConverter();

	public String convert(CAConvertType type, String in) {
		return ChineseArabConvertUtil.numberTranslator(in);
	}

	public static CAConverter getInstance() {
		if (instance == null) {
			instance = new CAConverter();
		}
		return instance;
	}



}
