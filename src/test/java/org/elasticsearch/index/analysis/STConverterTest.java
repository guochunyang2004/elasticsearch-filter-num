package org.elasticsearch.index.analysis;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * User: Medcl Date: 12-10-28 Time: 上午8:44
 */
public class STConverterTest extends TestCase {
	public void testConvert() throws Exception {

		CAConverter stConverter = new CAConverter();
		String str = stConverter.convert(CAConvertType.CHINESE_TO_ARAB, "二百");
		System.out.println(str);
	}
}
