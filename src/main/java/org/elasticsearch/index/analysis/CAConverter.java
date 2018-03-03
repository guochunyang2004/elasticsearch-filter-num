package org.elasticsearch.index.analysis;

import org.elasticsearch.index.analysis.util.ChineseArabConvertUtil;

/**
 * some parts of code copied from:http://code.google.com/p/java-zhconverter/
 */
public class CAConverter {

	private static CAConverter instance = new CAConverter();

	public CAConverter() {

	}

	public String convert(CAConvertType type, String in) {
		return ChineseArabConvertUtil.numberTranslator(in);
	}

	public static CAConverter getInstance() {
		if (instance == null) {
			instance = new CAConverter();
		}
		return instance;
	}

	public String convert(String text, CAConvertType converterType) {
		return getInstance().convert(converterType, text);
	}

}
