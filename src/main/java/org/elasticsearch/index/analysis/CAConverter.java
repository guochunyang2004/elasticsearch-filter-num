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

	private static final Logger LOGGER = Logger.getLogger(CAConverter.class.getName());

	private Properties charMap = new Properties();

	private Properties revCharMap = new Properties();

	private Set<String> conflictingSets = new HashSet<String>();

	private static CAConverter instance = new CAConverter();

	public CAConverter() {

		InputStream file1 = null;
		file1 = this.getClass().getResourceAsStream("/t2s.properties");
		InputStreamReader is = null;
		try {
			is = new InputStreamReader(file1, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			LOGGER.log(Level.SEVERE, "Unsupported character encoding " + e.getMessage(), e);
		}
		if (is != null) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(is);
				charMap.load(reader);
			}
			catch (FileNotFoundException e) {
			}
			catch (IOException e) {
				LOGGER.log(Level.SEVERE, "IOException in loading charMap: " + e.getMessage(), e);
			}
			finally {
				try {
					if (reader != null) {
						reader.close();
					}
					is.close();
				}
				catch (IOException e) {
				}
			}
		}
		initializeHelper();
	}

	private void initializeHelper() {

		Map stringPossibilities = new HashMap();
		Iterator iter = charMap.keySet().iterator();
		while (iter.hasNext()) {

			// fill revmap
			String key = (String) iter.next();
			revCharMap.put(charMap.get(key), key);

			if (key.length() >= 1) {

				for (int i = 0; i < (key.length()); i++) {
					String keySubstring = key.substring(0, i + 1);
					if (stringPossibilities.containsKey(keySubstring)) {
						Integer integer = (Integer) (stringPossibilities.get(keySubstring));
						stringPossibilities.put(keySubstring, Integer.valueOf(integer) + 1);

					}
					else {
						stringPossibilities.put(keySubstring, 1);
					}

				}
			}
		}

		iter = stringPossibilities.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			int value = (Integer) stringPossibilities.get(key);
			if (value > 1) {
				conflictingSets.add(key);
			}
		}
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

	private static void mapping(Map<Object, Object> map, StringBuilder outString, StringBuilder stackString) {
		while (stackString.length() > 0) {
			if (map.containsKey(stackString.toString())) {
				outString.append(map.get(stackString.toString()));
				stackString.setLength(0);

			}
			else {
				outString.append(Character.toString(stackString.charAt(0)));
				stackString.delete(0, 1);
			}

		}
	}

}
