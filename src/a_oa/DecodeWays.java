package a_oa;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A message containing letters from A-Z is being encoded to numbers using the
 * following mapping:
 * 
 * 'A' -> 1 'B' -> 2 ... 'Z' -> 26
 * 
 * Example Given encoded message 12, it could be decoded as AB (1 2) or L (12).
 * The number of ways decoding 12 is 2.
 */
public class DecodeWays {

	public static void main(String[] args) {
		System.out.println(numDecodings("101"));
		System.out.println(decodedWays("101"));
	}

	/**
	 * jiuzhang: http://www.jiuzhang.com/solutions/decode-ways/
	 */
	public static int numDecodings(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		int len = s.length();
		int[] dp = new int[len + 1];
		// dp[0]=1, 方便计算
		dp[0] = 1;
		// 是'0', 不是 0
		dp[1] = s.charAt(0) == '0' ? 0 : 1;

		for (int i = 2; i <= len; i++) {
			dp[i] = s.charAt(i - 1) == '0' ? 0 : dp[i - 1];

			int twoDigit = (s.charAt(i - 2) - '0') * 10
					+ (s.charAt(i - 1) - '0');
			// >=10 AND <=26才是合理的二位数
			if (twoDigit >= 10 && twoDigit <= 26) {
				dp[i] = dp[i] + dp[i - 2];
			}
		}
		System.out.println(Arrays.toString(dp));
		return dp[len];
	}

	/**
	 * follow up :print out all decoded ways
	 * 
	 * RestoreIPAddress.java 几乎一模一样
	 */
	static String dict = "0ABCDEFGHIGKLMNOPQRSTUVWXYZ";

	// 原理 和 PalindromePartitioning.java 一样
	public static ArrayList<String> decodedWays(String s) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<Character> list = new ArrayList<Character>();

		if (s.length() < 1) {
			return result;
		}
		if (s.length() == 1) {
			list.add(s.charAt(0));
			return result;
		}
		// 0 track position
		helper(result, list, s, 0);
		return result;
	}

	public static void helper(ArrayList<String> result,
			ArrayList<Character> list, String s, int pos) {
		if (pos == s.length()) {
			StringBuffer sb = new StringBuffer();
			for (char tmp : list) {
				sb.append(tmp);
			}
			result.add(sb.toString());
			return;
		}

		for (int i = pos; i < s.length() && i < pos + 2; i++) {
			// 当i = s.length-1 的时候 s.substring(pos, i + 1);
			String tmp = s.substring(pos, i + 1);
			if (!isValid(tmp)) {
				continue;
			}
			// 把取到的数字String转换为字母
			list.add(dict.charAt(Integer.valueOf(tmp)));
			helper(result, list, s, i + 1);
			list.remove(list.size() - 1);
		}
	}

	public static boolean isValid(String s) {
		// Note
		if (s.charAt(0) == '0') {
			return false;
		}
		int code = Integer.parseInt(s);
		return code >= 1 && code <= 26;
	}

}
