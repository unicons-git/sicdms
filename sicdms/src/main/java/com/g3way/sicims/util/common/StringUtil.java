/**
 *
 */
package com.g3way.sicims.util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author dykim
 *
 */
public class StringUtil {
	private StringUtil() {
	}


	public static String removeDelimChar(String s, String old, String replacement) {
		if (s == null || s.equals("")) {
			return "";
		}

		return replace(s, old, replacement);
	}


	public static String replace(String s, String old, String replacement) {
		int i = s.indexOf(old);
		StringBuffer r = new StringBuffer();

		if (i == -1) {
			return s;
		}

		r.append(s.substring(0, i) + replacement);

		if ((i + old.length()) < s.length()) {
			r.append(replace(s.substring(i + old.length(), s.length()), old,
					replacement));
		}

		return r.toString();
	}


	public static String toSqlString(String s) {
		return replace(s, "'", "''");
	}


	public static String nToBr(String s) {
		if(s == null) {
			return "";
		}
		return replace(s, "\n", "<br>");
	}


	public static int parseInt(String s) {
		return parseInt(s, -999);
	}

	public static int parseInt(String str, int defaultValue) {
		int result = defaultValue;

		if (str == null) {
			return defaultValue;
		}

		String tempStr = str.trim();

		try {
			result = Integer.parseInt(tempStr);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}

		return result;
	}

	public static long parseLong(String s) {
		return parseLong(s, -999);
	}

	public static long parseLong(String str, long defaultValue) {
		long result = defaultValue;

		if (str == null) {
			return defaultValue;
		}

		String tempStr = str.trim();

		try {
			result = Long.parseLong(tempStr);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}

		return result;
	}

	public static double parseDouble(String s) {
		return parseDouble(s, -999);
	}

	public static double parseDouble(String str, double defaultValue) {
		double result = defaultValue;

		if (str == null) {
			return defaultValue;
		}

		String tempStr = str.trim();
		tempStr = replace(tempStr, "O", "0");

		try {
			result = Double.parseDouble(tempStr);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}

		return result;
	}

	public static boolean parseBoolean(String s, String trueValue) {
		return trueValue != null && trueValue.equals(s);
	}

	public static boolean parseBoolean(String s) {
		return parseBoolean(s, "Y");
	}

	public static String getNotNullAndTrimedString(String s) {
		return s == null ? "" : s.trim();
	}

	public static String intToString(int n) {
		if(n == -999) {
			return "";
		}
		return n + "";
	}

	public static String doubleToString(double d) {
		if(d == -999.0 || d == -999 || d == -99.9) {
			return "";
		}
		return d + "";
	}

	public static String absIntToString(int n) {
		if(n == -999) {
			return "";
		}
		return Math.abs(n) + "";
	}


	public static String nvlString(String param, String defaultvalue){
		if ( param == null || param.equals("null"))
			return defaultvalue;
		else
			return param;
	}



	public static String getTruncatedText(String str, int len) {
		if (str == null || "".equals(str)) {
			return "...";
		}
		int slen = 0, blen = 0;
		char c;

		String tempStr = str;
		try {
			if (str.getBytes("MS949").length > len) {
				while (blen + 1 < len) {
					c = str.charAt(slen);
					blen++;
					slen++;
					if (c > 127) {
						blen++; //2-byte character..
					}
				}
				tempStr =  str.substring(0, slen) + "...";
			}
		} catch (java.io.UnsupportedEncodingException e) {
			tempStr = "";
		}
		return tempStr;
	}

	public static String nvl(String src) {
		if(src == null || src.equals("null")) {
			return "";
		}
		return src;
	}

	public static String nvl(String src, String replace) {
		if(src == null) {
			return replace;
		}
		return src;
	}

	public static String nvl(Object src) {
		if(src == null) {
			return "&nbsp;";
		}
		return src.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] getArrayString(String str, String strToken) {
		Vector vec = new Vector();
		StringTokenizer st = new StringTokenizer(str, strToken);
		while(st.hasMoreTokens()){
			vec.addElement((String)st.nextToken());
		}
		return (String[])vec.toArray(new String[0]);
	}

	public static String round(double dblValue, int nDigit) {
		DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(new DecimalFormatSymbols());
		df.setMinimumIntegerDigits(1); //
		df.setMaximumFractionDigits(nDigit); //
		df.setMinimumFractionDigits(nDigit); //
		df.setGroupingUsed(false);
		return df.format(dblValue);
	}

	public static String trim(String param){
		return trim(param,"");
	}

	public static String trim(String param, String defaultvalue){
		if ( param == null || param.trim().equals(""))
			return defaultvalue;
		else
			return param.trim();
	}

	public static String dtos(String strDate) {
		return dtos(strDate, ".");
	}
	public static String dtos(String strDate, String strValue) {
		return dtos(strDate, strValue, strValue, "");
	}
	public static String dtos(String strDate, String strYY, String strMM, String strDD) {
		String tempDate = trim(strDate);
		if (tempDate.length() != 8)
			return tempDate;
		return (tempDate.substring(0,4) + strYY + tempDate.substring(4,6) + strMM + tempDate.substring(6,8) + strDD);
	}


	public static String substring(String src, int ix1, int ix2) {
		if(src == null || src.length() < ix2) {
			return "";
		}

		return src.substring(ix1, ix2);
	}

	public static String substring(String src, int ix) {
		if(src == null || src.length() < ix) {
			return "";
		}
		return src.substring(ix);
	}

	/**
	 * @param str
	 * @return blank 문자열인지 여부
	 */
	public static boolean isBlank(String str) {

		return StringUtils.isBlank(str);
	}

	/**
	 * @param str
	 * @return empty 문자열인지 여부
	 */
	public static boolean isEmpty(String str) {

		return StringUtils.isEmpty(str);
	}

	/**
	 * blank 문자열이 있는지 체크한다.
	 *
	 * @param strArray
	 * @return blank 문자열이 있으면 true, 없으면 false
	 */
	public static boolean checkBlank(String... strArray) {

		for (String str : strArray) {
			if (StringUtils.isBlank(str)) {
				return true;
			}
		}

		return false;
	}

	// + - 월
	public static String getIncrementMonth(String yyyymm, int inc) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, Integer.parseInt(yyyymm.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.parseInt(yyyymm.substring(4, 6)) - 1);
		cal.add(Calendar.MONTH, inc);

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");
		return df4.format(cal.get(Calendar.YEAR)) + df2.format(cal.get(Calendar.MONTH) + 1);
	}

	// 월 차이
	public static int getMonthDiff(String startMonth, String endMonth) {
		Calendar sCal = Calendar.getInstance();
		Calendar eCal = Calendar.getInstance();
		sCal.clear();
		eCal.clear();
		sCal.set(Calendar.YEAR, Integer.parseInt(startMonth.substring(0, 4)));
		sCal.set(Calendar.MONTH, Integer.parseInt(startMonth.substring(4, 6)) - 1);
		eCal.set(Calendar.YEAR, Integer.parseInt(endMonth.substring(0, 4)));
		eCal.set(Calendar.MONTH, Integer.parseInt(endMonth.substring(4, 6)) - 1);

		int yearDiff  = eCal.get(Calendar.YEAR)  - sCal.get(Calendar.YEAR);
		int monthDiff = eCal.get(Calendar.MONTH) - sCal.get(Calendar.MONTH);
		return yearDiff * 12 + monthDiff;
	}

	// 천단위 comma 삽입
	public static String getDecimalFormat(double numberValue){
		DecimalFormat df = new DecimalFormat("###,###");

		return df.format(numberValue);
	}

	//
	public static int getByteSize(String str) {
		int enSize = 0;
		int koSize = 0;
		int etcSize = 0;

		char[] charArr = str.toCharArray();

		for (int i = 0; i < charArr.length; ++i) {
			if (charArr[i] >='A' && charArr[i] <= 'Z') {
				++enSize;
			} else if (charArr[i]>='\uAC00' && charArr[i]<='\uD7A3') {
				++koSize;
				++koSize;
			} else {
				++etcSize;
			}
		}
		return (enSize + koSize + etcSize);
	}

	// numeric check
	@SuppressWarnings("unused")
	public static boolean isNumeric(String str)   {
		try  {
			double d = Double.parseDouble(str);
		 } catch(NumberFormatException nfe) {
			return false;
		 }
		return true;
	}

	public static String clobToString(Clob clob) throws  IOException, SQLException {
		if (clob == null) {
			return "";
		}

		BufferedReader br = null;
		StringBuffer strOut = new StringBuffer();
		String str = "";
		try {
			br = new BufferedReader(clob.getCharacterStream());
			while (true) {
				str = br.readLine();
				if (str == null ) break;
				strOut.append(str);
			}
			br.close();
		} catch (IOException e) {
			strOut = new StringBuffer();
		} finally {
			if (br != null) {
				br.close();
			}
		}

		return strOut.toString();
	}


	// 날짜 형식 제거
	public static String delimToEmpty(Object obj) {
		String result = "";

		if (obj == null || obj.equals("")) {
			result = "";
		} else {
			result = objToString(obj);
		}

		return delimToEmpty(result);
	}



	// 구분자 빈공간으로 반환
	public static String delimToEmpty(String str) {
		String result = str;

		result = result.replace(" ", "");  // 공백
		result = result.replace("-", "");  // -
		result = result.replace(".", "");  // .
		result = result.replace(":", "");  // :
		result = result.replace("/", "");  // /

		return result;
	}




	// Object to String
	public static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}

	// null or "" to zero
	public static String nullToZero(Object obj) {
		if (obj == null || obj.equals("")) {
			return "0";
		} else {
			return String.valueOf(obj);
		}
	}


	// number value 설정
	public static BigDecimal strToBigDecimal(Object obj) {
		try {
			if (obj == null || obj.equals("")) {
				return null;
			} else {
				String value = nullToZero(obj);
				value = removeDelimChar(value, " ", "");
				value = removeDelimChar(value, ",", "");
				return new BigDecimal(value);
			}
		} catch (NumberFormatException e) {
			return null;
		}
	}


	public static String rPad(String str, int len, String chr){
    	String result = "";
    	StringBuilder sb = new StringBuilder();
    	for( int i = str.length(); i < len; i++ ) { // iLen길이 만큼 strChar문자로 채운다.
    		sb.append( chr );
    	}
    	result = str + sb;

    	return result;
	}

	public static String lPad(String str, int len, String chr){
    	String result = "";
    	StringBuilder sb = new StringBuilder();
    	for( int i = str.length(); i < len; i++ ) { // iLen길이 만큼 strChar문자로 채운다.
    		sb.append( chr );
    	}
    	result = sb + str; // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.

    	return result;
	}






	public static String getPhoneFormat(String str){
		if (str == null) {
			return "";
		}

		if (str.trim().length() == 8) {
			return str.trim().replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (str.trim().length() == 12) {
			return str.trim().replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		} else if (str.trim().length() == 10) {
			if("02".equals(str.substring(0, 2))) {													//서울 지역번호의 경우
				return str.trim().replaceFirst("(^[0-9]{2})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
			} else {																				//그외 지역번호의 경우
				return str.trim().replaceFirst("(^[0-9]{3})([0-9]{3})([0-9]{4})$", "$1-$2-$3");
			}
		}
		return str.trim().replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	}


	public static boolean checkExp(String checkMenu, String str){

		String userIdPattern 		= "^[a-zA-Z]{1}[a-zA-Z0-9_]{3,11}$";
		String userPswdPattern 		= "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$";
		String telnoPattern 		= "^0?([0-9]{1,2})-?([0-9]{3,4})-?([0-9]{4})$";
		String emlAddrPattern 		= "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		String hrscRplcKeyPattern 	= "^A?([0-9]{12})+$";

		Pattern patten = null ;
		if("userPswd".equals(checkMenu)){
			patten = Pattern.compile(userPswdPattern);
		} else if("userId".equals(checkMenu)){
			patten = Pattern.compile(userIdPattern);
		} else if("emlAddr".equals(checkMenu)){
			patten = Pattern.compile(emlAddrPattern);
		} else if("telno".equals(checkMenu)){
			patten = Pattern.compile(telnoPattern);
		} else if("hrscRplcKey".equals(checkMenu)){
			patten = Pattern.compile(hrscRplcKeyPattern);
		} else {
			return false;
		}

		if(patten != null ) {
			Matcher match = patten.matcher(str);

			if(match.matches()){
				return true;
			}
		}
		return false;
	}
}
