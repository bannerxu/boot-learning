
package top.banner.quartz.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 *
 * @author xiaoleilu
 */
public class StrUtil {
	public static final String EMPTY = "";

	/**
	 * {@link CharSequence} 转为字符串，null安全
	 *
	 * @param cs {@link CharSequence}
	 * @return 字符串
	 */
	public static String str(CharSequence cs) {
		return null == cs ? null : cs.toString();
	}

	/**
	 * 改进JDK subString<br>
	 * index从0开始计算，最后一个字符为-1<br>
	 * 如果from和to位置一样，返回 "" <br>
	 * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
	 * 如果经过修正的index中from大于to，则互换from和to example: <br>
	 * abcdefgh 2 3 =》 c <br>
	 * abcdefgh 2 -3 =》 cde <br>
	 *
	 * @param str       String
	 * @param fromIndex 开始的index（包括）
	 * @param toIndex   结束的index（不包括）
	 * @return 字串
	 */
	public static String sub(CharSequence str, int fromIndex, int toIndex) {
		if (StringUtils.isEmpty(str)) {
			return str(str);
		}
		int len = str.length();

		if (fromIndex < 0) {
			fromIndex = len + fromIndex;
			if (fromIndex < 0) {
				fromIndex = 0;
			}
		} else if (fromIndex > len) {
			fromIndex = len;
		}

		if (toIndex < 0) {
			toIndex = len + toIndex;
			if (toIndex < 0) {
				toIndex = len;
			}
		} else if (toIndex > len) {
			toIndex = len;
		}

		if (toIndex < fromIndex) {
			int tmp = fromIndex;
			fromIndex = toIndex;
			toIndex = tmp;
		}

		if (fromIndex == toIndex) {
			return EMPTY;
		}

		return str.toString().substring(fromIndex, toIndex);
	}
}
