package org.elasticsearch.index.analysis;

import junit.framework.TestCase;

/**
 * User: Medcl Date: 12-10-28 Time: 上午8:44
 */
public class CAConverterTest extends TestCase {
	public void testConvert() throws Exception {
		CAConverter stConverter = new CAConverter();
		String str1 = stConverter.convert(CAConvertType.CHINESE_TO_ARAB, "超过二百人");
		System.out.println(str1);
	}
}
