package org.elasticsearch.index.analysis.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 *
 * @author haoyuan.yang
 * @version 1.0
 * @date: 2017/12/20
 * @since JDK 1.8
 */
public class TimeExtractUtil {

	/**
	 * 该方法可以将字符串中所有的用汉字表示的数字转化为用阿拉伯数字表示的数字 如"这里有一千两百个人，六百零五个来自中国"可以转化为
	 * "这里有1200个人，605个来自中国" 此外添加支持了部分不规则表达方法 如两万零六百五可转化为20650 两百一十四和两百十四都可以转化为214
	 * 一六零加一五八可以转化为160+158 该方法目前支持的正确转化范围是0-99999999 该功能模块具有良好的复用性
	 *
	 * @param target 待转化的字符串
	 * @return 转化完毕后的字符串
	 */
	private final static Pattern P1 = Pattern.compile("[一二两三四五六七八九123456789]万[一二两三四五六七八九123456789](?!(千|百|十))");

	private final static Pattern P2 = Pattern.compile("[一二两三四五六七八九123456789]千[一二两三四五六七八九123456789](?!(百|十))");

	private final static Pattern P3 = Pattern.compile("[一二两三四五六七八九123456789]百[一二两三四五六七八九123456789](?!十)");

	private final static Pattern P4 = Pattern.compile("[零一二两三四五六七八九]");

	private final static Pattern P5 = Pattern.compile("(?<=(周|星期))[末天日]");

	private final static Pattern P6 = Pattern.compile("(?<!(周|星期))0?[0-9]?十[0-9]?");

	private final static Pattern P7 = Pattern.compile("0?[1-9]百[0-9]?[0-9]?");

	private final static Pattern P8 = Pattern.compile("0?[1-9]千[0-9]?[0-9]?[0-9]?");

	private final static Pattern P9 = Pattern.compile("[0-9]+万[0-9]?[0-9]?[0-9]?[0-9]?");

	public static String numberTranslator(String target) {
		Matcher m = P1.matcher(target);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("万");
			int num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 10000 + wordToNumber(s[1]) * 1000;
			}
			m.appendReplacement(sb, Integer.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		m = P2.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("千");
			int num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 1000 + wordToNumber(s[1]) * 100;
			}
			m.appendReplacement(sb, Integer.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		m = P3.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("百");
			int num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 100 + wordToNumber(s[1]) * 10;
			}
			m.appendReplacement(sb, Integer.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		m = P4.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, Integer.toString(wordToNumber(m.group())));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		m = P5.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			m.appendReplacement(sb, Integer.toString(wordToNumber(m.group())));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		m = P6.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("十");
			int num = 0;
			if (s.length == 0) {
				num += 10;
			}
			else if (s.length == 1) {
				int ten = Integer.parseInt(s[0]);
				if (ten == 0) {
					num += 10;
				}
				else {
					num += ten * 10;
				}
			}
			else if (s.length == 2) {
				if ("".equals(s[0])) {
					num += 10;
				}
				else {
					int ten = Integer.parseInt(s[0]);
					if (ten == 0) {
						num += 10;
					}
					else {
						num += ten * 10;
					}
				}
				num += Integer.parseInt(s[1]);
			}
			m.appendReplacement(sb, Integer.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		m = P7.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("百");
			int num = 0;
			if (s.length == 1) {
				int hundred = Integer.parseInt(s[0]);
				num += hundred * 100;
			}
			else if (s.length == 2) {
				int hundred = Integer.parseInt(s[0]);
				num += hundred * 100;
				num += Integer.parseInt(s[1]);
			}
			m.appendReplacement(sb, Integer.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		m = P8.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("千");
			int num = 0;
			if (s.length == 1) {
				int thousand = Integer.parseInt(s[0]);
				num += thousand * 1000;
			}
			else if (s.length == 2) {
				int thousand = Integer.parseInt(s[0]);
				num += thousand * 1000;
				num += Integer.parseInt(s[1]);
			}
			m.appendReplacement(sb, Integer.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		m = P9.matcher(target);
		sb = new StringBuffer();
		result = m.find();
		while (result) {
			String group = m.group();
			String[] s = group.split("万");
			int num = 0;
			if (s.length == 1) {
				int tenthousand = Integer.parseInt(s[0]);
				num += tenthousand * 10000;
			}
			else if (s.length == 2) {
				int tenthousand = Integer.parseInt(s[0]);
				num += tenthousand * 10000;
				num += Integer.parseInt(s[1]);
			}
			m.appendReplacement(sb, Integer.toString(num));
			result = m.find();
		}
		m.appendTail(sb);
		target = sb.toString();

		return target;
	}

	/**
	 * 方法numberTranslator的辅助方法，可将[零-九]正确翻译为[0-9]
	 */
	private final static List<String> ZERO = Arrays.asList("零", "0");

	private final static List<String> ONE = Arrays.asList("一", "1");

	private final static List<String> TWO = Arrays.asList("二", "两", "2");

	private final static List<String> THREE = Arrays.asList("三", "3");

	private final static List<String> FOUR = Arrays.asList("四", "4");

	private final static List<String> FIVE = Arrays.asList("五", "5");

	private final static List<String> SIX = Arrays.asList("六", "6");

	private final static List<String> SEVEN = Arrays.asList("七", "7");

	private final static List<String> EIGHT = Arrays.asList("八", "8");

	private final static List<String> NINE = Arrays.asList("九", "9");

	private static int wordToNumber(String s) {
		if (ZERO.contains(s)) {
			return 0;
		}
		else if (ONE.contains(s)) {
			return 1;
		}
		else if (TWO.contains(s)) {
			return 2;
		}
		else if (THREE.contains(s)) {
			return 3;
		}
		else if (FOUR.contains(s)) {
			return 4;
		}
		else if (FIVE.contains(s)) {
			return 5;
		}
		else if (SIX.contains(s)) {
			return 6;
		}
		else if (SEVEN.contains(s)) {
			return 7;
		}
		else if (EIGHT.contains(s)) {
			return 8;
		}
		else if (NINE.contains(s)) {
			return 9;
		}
		else {
			return -1;
		}
	}

}
