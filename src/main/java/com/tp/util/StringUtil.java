package com.tp.util;

import sun.misc.BASE64Encoder;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.Collator;
import java.util.*;
import java.util.regex.Pattern;


public class StringUtil {
	public static final String CHARFORM_NOHORN = "aaaaaaaaaaaaaaaaaeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyd"
			+ "AAAAAAAAAAAAAAAAAEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYYD";
	public static final String CHARFORM_UNICODE = "àáảãạâầấẩẫậăằắẳẵặèéẻẽẹê�?ếểễệìíỉĩịòó�?õ�?ôồốổỗộơ�?ớởỡợùúủũụưừứửữựỳýỷỹỵđ"
			+ "À�?ẢÃẠÂẦẤẨẪẬĂẰẮẲẴẶÈÉẺẼẸÊỀẾỂỄỆÌ�?ỈĨỊÒÓỎÕỌÔỒ�?ỔỖỘƠỜỚỞỠỢÙÚỦŨỤƯỪỨỬỮỰỲ�?ỶỸỴ�?";
	public static final String CHARFORM_TCVN = "µ¸¶·¹©ÇÊÈÉË¨»¾¼½ÆÌ�?Î�?ÑªÒÕÓÔÖ×�?ØÜÞßãáâä«åèæçé¬êíëìîïóñòô­õøö÷ùúýûüþ®"
			+ "µ¸¶·¹©ÇÊÈÉË¨»¾¼½ÆÌ�?Î�?ÑªÒÕÓÔÖ×�?ØÜÞßãáâä«åèæçé¬êíëìîïóñòô­õøö÷ùúýûüþ®";
	public static final int ALIGN_CENTER = 0;
	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_RIGHT = 2;

	// //////////////////////////////////////////////////////
	/**
	 * Encrypt a string
	 * 
	 * @param strValue
	 *            string to encrypt
	 * @param strAlgorithm
	 *            ecrypt algorithm
	 * @return encrypted string
	 * @throws Exception
	 *             if error occured
	 */
	// //////////////////////////////////////////////////////
	public static String encrypt(String strValue, String strAlgorithm) throws Exception {
		return encrypt(strValue.getBytes(), strAlgorithm);
	}

	// //////////////////////////////////////////////////////
	/**
	 * Encrypt a byte array
	 * 
	 * @param btValue
	 *            array to encrypt
	 * @param strAlgorithm
	 *            ecrypt algorithm
	 * @return encrypted string
	 * @throws Exception
	 *             if error occured
	 */
	// //////////////////////////////////////////////////////
	public static String encrypt(byte[] btValue, String strAlgorithm) throws Exception {
		BASE64Encoder enc = new BASE64Encoder();
		if ("DONT_ENCRYPT".equalsIgnoreCase(strAlgorithm) || StringUtil.stringIsNullOrEmty(strAlgorithm)){
			return enc.encodeBuffer(btValue);
		}
		MessageDigest md = MessageDigest.getInstance(strAlgorithm);
		return enc.encodeBuffer(md.digest(btValue));
	}

	// //////////////////////////////////////////////////////
	/**
	 * Format long number
	 * 
	 * @param lngNumber
	 *            number to format
	 * @param strPattern
	 *            format pattern
	 * @return formatted string
	 */
	// //////////////////////////////////////////////////////
	public static String format(long lngNumber, String strPattern) {
		java.text.DecimalFormat fmt = new java.text.DecimalFormat(strPattern);
		return fmt.format(lngNumber);
	}

	// //////////////////////////////////////////////////////
	/**
	 * Format double number
	 * 
	 * @param dblNumber
	 *            number to format
	 * @param strPattern
	 *            format pattern
	 * @return formatted string
	 */
	// //////////////////////////////////////////////////////
	public static String format(double dblNumber, String strPattern) {
		java.text.DecimalFormat fmt = new java.text.DecimalFormat(strPattern);
		return fmt.format(dblNumber);
	}

	// //////////////////////////////////////////////////////
	/**
	 * Replace all occurred of string with another
	 * 
	 * @param strSrc
	 *            main string
	 * @param strFind
	 *            string to find
	 * @param strReplace
	 *            string to replace
	 * @return main string after replace
	 */
	// //////////////////////////////////////////////////////
	public static String replaceAll(String strSrc, String strFind, String strReplace) {
		if (strFind == null || strFind.length() == 0)
			return strSrc;
		int iLocation = 0;
		int iPrevLocation = 0;
		StringBuffer strResult = new StringBuffer();
		while ((iLocation = strSrc.indexOf(strFind, iLocation)) >= 0) {
			strResult.append(strSrc.substring(iPrevLocation, iLocation));
			strResult.append(strReplace);
			iLocation += strFind.length();
			iPrevLocation = iLocation;
		}
		strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
		return strResult.toString();
	}

	// //////////////////////////////////////////////////////
	/**
	 * Replace all occurred of string with another
	 * 
	 * @param strSrc
	 *            main string
	 * @param strFind
	 *            string to find
	 * @param strReplace
	 *            string to replace
	 * @param iMaxReplacement
	 *            max replacement allowed
	 * @return main string after replace
	 */
	// //////////////////////////////////////////////////////
	public static String replaceAll(String strSrc, String strFind, String strReplace, int iMaxReplacement) {
		int iLocation = 0;
		if (strFind == null || strFind.length() == 0)
			return strSrc;
		int iPrevLocation = 0;
		int iCount = 0;
		StringBuffer strResult = new StringBuffer();
		while ((iLocation = strSrc.indexOf(strFind, iLocation)) >= 0 && iCount < iMaxReplacement) {
			strResult.append(strSrc.substring(iPrevLocation, iLocation));
			strResult.append(strReplace);
			iCount++;
			iLocation += strFind.length();
			iPrevLocation = iLocation;
		}
		strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
		return strResult.toString();
	}

	// //////////////////////////////////////////////////////
	/**
	 * Replace all occurred of string ignore case with another
	 * 
	 * @param strSrc
	 *            main string
	 * @param strFind
	 *            string to find
	 * @param strReplace
	 *            string to replace
	 * @return main string after replace
	 */
	// //////////////////////////////////////////////////////
	public static String replaceAllIgnoreCase(String strSrc, String strFind, String strReplace) {
		if (strFind == null || strFind.length() == 0)
			return strSrc;
		String strSrcUpper = strSrc.toUpperCase();
		strFind = strFind.toUpperCase();

		int iLocation = 0;
		int iPrevLocation = 0;
		StringBuffer strResult = new StringBuffer();
		while ((iLocation = strSrcUpper.indexOf(strFind, iLocation)) >= 0) {
			strResult.append(strSrc.substring(iPrevLocation, iLocation));
			strResult.append(strReplace);
			iLocation += strFind.length();
			iPrevLocation = iLocation;
		}
		strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
		return strResult.toString();
	}

	// //////////////////////////////////////////////////////
	/**
	 * Replace null string
	 * 
	 * @param objInput
	 *            object value
	 * @param strNullValue
	 *            object to replace if objInput is null
	 * @return String value of objInput after replace
	 */
	// //////////////////////////////////////////////////////
	public static String nvl(Object objInput, String strNullValue) {
		if (objInput == null)
			return strNullValue;
		return objInput.toString();
	}

	// //////////////////////////////////////////////////////
	/**
	 * Return first position of letter (printable character) in String
	 * 
	 * @param strSource
	 *            String to search
	 * @param iOffset
	 *            start offset
	 * @return first position of letter (printable character) in strSource, -1
	 *         if not found
	 */
	// //////////////////////////////////////////////////////
	public static int indexOfLetter(String strSource, int iOffset) {
		char c;
		while (iOffset < strSource.length()) {
			c = strSource.charAt(iOffset);
			if (c > ' ')
				return iOffset;
			else
				iOffset++;
		}
		return -1;
	}

	// //////////////////////////////////////////////////////
	/**
	 * Return first position of space in String
	 * 
	 * @param strSource
	 *            String to search
	 * @param iOffset
	 *            start offset
	 * @return first position of space (unprintable character) in strSource, -1
	 *         if not found
	 */
	// //////////////////////////////////////////////////////
	public static int indexOfSpace(String strSource, int iOffset) {
		char c;
		while (iOffset < strSource.length()) {
			c = strSource.charAt(iOffset);
			if (c > ' ')
				iOffset++;
			else
				return iOffset;
		}
		return -1;
	}

	// //////////////////////////////////////////////////////
	/**
	 * Return number occurence of symbol in a string
	 * 
	 * @param strSource
	 *            String to search
	 * @param chrSymbol
	 *            symbol to count
	 * @param iOffset
	 *            start offset
	 * @return number occurence of symbol in strSource
	 */
	// //////////////////////////////////////////////////////
	public static int countSymbol(String strSource, String chrSymbol, int iOffset) {
		if (chrSymbol == null || chrSymbol.length() == 0)
			return 0;
		int iCount = 0;
		while ((iOffset = strSource.indexOf(chrSymbol, iOffset) + 1) > 0)
			iCount++;
		return iCount;
	}

	// //////////////////////////////////////////////////////
	/**
	 * Convert a string to string array base on separator
	 * 
	 * @param strSource
	 *            String to convert
	 * @param strSeparator
	 *            separator symbol
	 * @return String array after convert
	 */
	// //////////////////////////////////////////////////////
	public static String[] toStringArray(String strSource, String strSeparator) {
		Vector vtReturn = toStringVector(strSource, strSeparator);
		String[] strReturn = new String[vtReturn.size()];
		for (int iIndex = 0; iIndex < strReturn.length; iIndex++)
			strReturn[iIndex] = (String) vtReturn.elementAt(iIndex);
		return strReturn;
	}

	// //////////////////////////////////////////////////////
	/**
	 * Convert a string to string array base on separator
	 * 
	 * @param strSource
	 *            String to convert
	 * @param strSeparator
	 *            separator symbol
	 * @return String array after convert
	 */
	// //////////////////////////////////////////////////////
	public static Vector toStringVector(String strSource, String strSeparator) {
		Vector vtReturn = new Vector();
		int iIndex = 0;
		int iLastIndex = 0;
		while ((iIndex = strSource.indexOf(strSeparator, iLastIndex)) >= 0) {
			vtReturn.addElement(strSource.substring(iLastIndex, iIndex).trim());
			iLastIndex = iIndex + strSeparator.length();
		}
		if (iLastIndex <= strSource.length())
			vtReturn.addElement(strSource.substring(iLastIndex, strSource.length()).trim());
		return vtReturn;
	}

	// //////////////////////////////////////////////////////
	/**
	 * Convert a string to string array base on ',' symbol
	 * 
	 * @param strSource
	 *            String to convert
	 * @return String array after convert
	 */
	// //////////////////////////////////////////////////////
	public static String[] toStringArray(String strSource) {
		return toStringArray(strSource, ",");
	}

	// //////////////////////////////////////////////////////
	/**
	 * Convert a string to string array base on ',' symbol
	 * 
	 * @param strSource
	 *            String to convert
	 * @return String array after convert
	 */
	// //////////////////////////////////////////////////////
	public static Vector toStringVector(String strSource) {
		return toStringVector(strSource, ",");
	}

	// //////////////////////////////////////////////////////
	/**
	 * Convert char form
	 * 
	 * @param strSource
	 *            String to convert
	 * @param strCharformSource
	 *            source charform
	 * @param strCharformDestination
	 *            destination charform
	 * @return String after convert
	 */
	// //////////////////////////////////////////////////////
	public static String convertCharForm(String strSource, String strCharformSource, String strCharformDestination) {
		if (strSource == null)
			return null;
		int iLength = strSource.length();
		int iResult = 0;
		StringBuffer strReturn = new StringBuffer();
		for (int iIndex = 0; iIndex < iLength; iIndex++) {
			char c = strSource.charAt(iIndex);
			if ((iResult = strCharformSource.indexOf(c)) >= 0)
				strReturn.append(strCharformDestination.charAt(iResult));
			else
				strReturn.append(c);
		}
		return strReturn.toString();
	}

	// //////////////////////////////////////////////////////
	/**
	 * Convert an unicode string to tcvn string
	 * 
	 * @param strSource
	 *            String to convert
	 * @return String array after convert
	 */
	// //////////////////////////////////////////////////////
	public static String unicodeToTCVN(String strSource) {
		return convertCharForm(strSource, CHARFORM_UNICODE, CHARFORM_TCVN);
	}

	// //////////////////////////////////////////////////////
	/**
	 * Convert an tcvn string to unicode string
	 * 
	 * @param strSource
	 *            String to convert
	 * @return String array after convert
	 */
	// //////////////////////////////////////////////////////
	public static String tcvnToUnicode(String strSource) {
		return convertCharForm(strSource, CHARFORM_TCVN, CHARFORM_UNICODE);
	}

	// //////////////////////////////////////////////////////
	/**
	 * Clear all horn in unicode string
	 * 
	 * @param strSource
	 *            String to convert
	 * @return String array after convert
	 */
	// //////////////////////////////////////////////////////
	public static String clearHornUnicode(String strSource) {
		return convertCharForm(strSource, CHARFORM_UNICODE, CHARFORM_NOHORN);
	}

	// //////////////////////////////////////////////////////
	/**
	 * Clear all horn in unicode string
	 * 
	 * @param strSource
	 *            String to convert
	 * @return String array after convert
	 */
	// //////////////////////////////////////////////////////
	public static String clearHornTCVN(String strSource) {
		return convertCharForm(strSource, CHARFORM_TCVN, CHARFORM_NOHORN);
	}

	// //////////////////////////////////////////////////////
	/**
	 * Pronounce a number to vietnamese
	 * 
	 * @param lNumber
	 *            nuber to pronoun
	 * @return String contain sound of number
	 */
	// //////////////////////////////////////////////////////
	public static String pronounceVietnameseNumber(long lNumber) {
		String strUnit[] = new String[] { "", "nghìn", "triệu", "tỷ", "nghìn tỷ", "triệu tỷ", "nghìn triệu tỷ",
				"tỷ tỷ" };

		// Analyse the number to array
		byte btDecimalNumber[] = new byte[30];
		byte btDecimalCount = 0;
		boolean bNegative = (lNumber < 0);
		if (bNegative)
			lNumber = -lNumber;
		while (lNumber > 0) {
			byte btValue = (byte) (lNumber - 10 * (lNumber / 10));
			lNumber /= 10;
			btDecimalNumber[btDecimalCount++] = btValue;
		}

		// Pronounce array
		String strReturn = "";
		int iUnitIndex = 0;
		while (iUnitIndex < strUnit.length && iUnitIndex * 3 < btDecimalCount) {
			String str = pronounceVietnameseNumber(btDecimalNumber[iUnitIndex * 3], btDecimalNumber[iUnitIndex * 3 + 1],
					btDecimalNumber[iUnitIndex * 3 + 2], iUnitIndex * 3 + 2 < btDecimalCount);
			if (str.length() > 0) {
				if (strReturn.length() > 0)
					strReturn = str + " " + strUnit[iUnitIndex] + " " + strReturn;
				else
					strReturn = str + " " + strUnit[iUnitIndex];
			}
			iUnitIndex++;
		}
		if (bNegative)
			strReturn = "âm " + strReturn;
		return strReturn;
	}

	// //////////////////////////////////////////////////////
	/**
	 * Pronounce a number to vietnamese
	 * 
	 * @param bUnit
	 *            unit part
	 * @param bTen
	 *            tens part
	 * @param bHundred
	 *            hundred part
	 *            if hundred part = 0
	 * @return String contain sound of number
	 */
	// //////////////////////////////////////////////////////
	private static String pronounceVietnameseNumber(byte bUnit, byte bTen, byte bHundred, boolean bMax) {
		// Return zero
		if (bUnit == 0 && bTen == 0 && bHundred == 0)
			return "";

		String strUnitSuffix[] = new String[] { "", "một", "hai", "ba", "tư", "lăm", "sáu", "bảy", "tám", "chín" };
		String strUnitTen[] = new String[] { "", "mư�?i một", "mư�?i hai", "mư�?i ba", "mư�?i bốn", "mư�?i lăm",
				"mư�?i sáu", "mư�?i bảy", "mư�?i tám", "mư�?i chín" };
		String strUnit[] = new String[] { "", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };
		String strTenFirst[] = new String[] { "", "mư�?i một", "hai mươi mốt", "ba mươi mốt", "bốn mươi mốt",
				"năm mươi mốt", "sáu mươi mốt", "bảy mươi mốt", "tám mươi mốt", "chín mươi mốt" };
		String strTen[] = new String[] { "", "mư�?i", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi",
				"bảy mươi", "tám mươi", "chín mươi" };
		String strHundred[] = new String[] { "không trăm", "một trăm", "hai trăm", "ba trăm", "bốn trăm", "năm trăm",
				"sáu trăm", "bảy trăm", "tám trăm", "chín trăm" };
		String strReturn = "";

		if (bMax || bHundred > 0)
			strReturn = strHundred[bHundred];
		if (bTen > 0) {
			if (strReturn.length() > 0)
				strReturn += " ";
			if (bUnit > 0) {
				if (bTen == 1)
					strReturn += strUnitTen[bUnit];
				else {
					if (bUnit == 1)
						strReturn += strTenFirst[bTen];
					else
						strReturn += strTen[bTen] + " " + strUnitSuffix[bUnit];
				}
			} else
				strReturn += strTen[bTen];
		} else {
			if (bUnit > 0) {
				if (strReturn.length() > 0)
					strReturn += " linh " + strUnit[bUnit];
				else
					strReturn = strUnit[bUnit];
			}
		}
		return strReturn;
	}

	// //////////////////////////////////////////////////////
	/**
	 * @param str
	 *            String
	 * @param iAlignment
	 *            int
	 * @param iLength
	 *            int
	 * @return String
	 */
	// //////////////////////////////////////////////////////
	public static String align(String str, int iAlignment, int iLength) {
		if (str == null)
			return null;
		if (str.length() > iLength)
			return str.substring(0, iLength);
		StringBuffer buf = new StringBuffer();
		if (iAlignment == ALIGN_CENTER) {
			int iFirstLength = (iLength - str.length()) / 2;
			for (int iIndex = 0; iIndex < iFirstLength; iIndex++)
				buf.append(" ");
			buf.append(str);
			for (int iIndex = str.length() + iFirstLength; iIndex < iLength; iIndex++)
				buf.append(" ");
		} else if (iAlignment == ALIGN_RIGHT) {
			iLength = iLength - str.length();
			for (int iIndex = 0; iIndex < iLength; iIndex++)
				buf.append(" ");
			buf.append(str);
		} else {
			buf.append(str);
			for (int iIndex = str.length(); iIndex < iLength; iIndex++)
				buf.append(" ");
		}
		return buf.toString();
	}

	public static int compareVietnameseString(String o1, String o2) {
		return compareString(o1, o2, new Locale("vi"));
	}

	public static boolean stringIsNullOrEmty(String str) {
		if (str == null)
			return true;
		else {
			if (str.trim().length() <= 0)
				return true;
		}
		return false;
	}

	public static boolean stringIsNullOrEmty(Object str) {
		if (str == null)
			return true;
		else {
			if (str.toString().trim().length() <= 0)
				return true;
		}
		return false;
	}

	public static int compareString(String strObj1, String strObj2, Locale locale) {
		final String DELIMITERS = "\\p{Cntrl}\\s\\p{Punct}\u0080-\u00BF\u2000-\uFFFF";
		Collator primary = null;
		Collator secondary = null;
		if (primary == null) {
			primary = Collator.getInstance(locale);
			secondary = (Collator) primary.clone();
			secondary.setStrength(Collator.SECONDARY);
		}

		int result;
		// String o1 = str1;
		// String o2 = str;
		String[] s1 = (" " + strObj1).split("[" + DELIMITERS + "]+");
		String[] s2 = (" " + strObj2).split("[" + DELIMITERS + "]+");
		for (int i = 1; i < s1.length && i < s2.length; i++) {
			result = secondary.compare(s1[i], s2[i]);
			if (result != 0) {
				return result;
			}
		}

		if (s1.length > s2.length) {
			return 1;
		} else if (s1.length < s2.length) {
			return -1;
		}

		for (int i = 1; i < s1.length; i++) {
			result = primary.compare(s1[i], s2[i]);
			if (result != 0) {
				return result;
			}

		}

		s1 = (strObj1 + " ").split("[^" + DELIMITERS + "]+");
		s2 = (strObj2 + " ").split("[^" + DELIMITERS + "]+");

		for (int i = 1; i < s1.length - 1 && i < s2.length - 1; i++) {
			result = primary.compare(s1[i], s2[i]);
			if (result != 0) {
				return result;
			}
		}

		result = primary.compare(s1[0], s2[0]);

		if (result != 0) {
			return result;
		}

		return primary.compare(strObj1, strObj2);
	}

	public static ArrayList<String> stringToArrayList(String strData, String chSplit) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String[] arr = strData.split(chSplit);
			for (String string : arr) {
				list.add(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Long stringHMSToMillis(String strHMS) {
		if (stringIsNullOrEmty(strHMS))
			return null;

		String[] arrStr = strHMS.split(":");
		if (arrStr.length == 3) {
			int h = Integer.parseInt(arrStr[0]);
			int m = Integer.parseInt(arrStr[1]);
			int s = Integer.parseInt(arrStr[2]);
			long mH = h * (60 * (60 * 1000));
			long mM = m * (60 * 1000);
			long mS = s * 1000;
			return (mH + mM + mS);
		} else
			return null;
	}

	public static List<String> strCodeToList(String str) {
		List<String> list = new ArrayList<String>();
		String[] arrStr = str.split(",");
		for (String string : arrStr) {
			list.add(string);
		}
		return list;
	}

	public static String convertListToString(List<Object> list) {
		String data = "";
		if (list != null) {
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					if (i == list.size() - 1) {
						data += "'" + list.get(i) + "'";
						// data += list.get(i);
					} else {
						data += "'" + list.get(i) + "',";
						// data += list.get(i)+",";
					}
				}
			}
		}
		return data;
	}

	public static String listToString(List<String> list, String strSeparator) {
		String str = null;
		for (Object string : list) {
			if (str == null) {
				if (string instanceof BigDecimal) {
					str = String.valueOf(string);
				} else {
					str = string.toString();
				}
			} else {
				if (string instanceof BigDecimal) {
					str += strSeparator + String.valueOf(string);
				} else {
					str += strSeparator + string.toString();
				}
			}
		}
		return str;
	}

	public static String listToString(List<String> list, String strSeparator, String strChar) {
		String str = null;
		for (String string : list) {
			if (str == null) {
				str = strChar + string + strChar;
			} else {
				str += strSeparator + strChar + string + strChar;
			}
		}
		return str;
	}

	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static boolean isNumericLong(String str) {
		try {
			Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	/**
	 * sondt18 convert "FIELD_NAME" to "fieldName"
	 *
	 * @param name
	 * @return
	 */
	public static String genName(String name) {
		try {
			StringBuilder nameStr = new StringBuilder(name.toLowerCase());
			if (nameStr.indexOf("_") == 0) {
				nameStr.deleteCharAt(0);
			}
			while (nameStr.indexOf("_") >= 0) {
				int i = nameStr.indexOf("_") + 1;
				if (i < nameStr.length()) {
					nameStr.setCharAt(i, Character.toUpperCase(nameStr.charAt(i)));
				}
				nameStr.deleteCharAt(i - 1);
			}
			return nameStr.toString().replaceAll("_", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	public static String getParamSQLWithLike(String param){
		if (StringUtil.stringIsNullOrEmty(param)){
			return "";
		}
		return "%" + param.trim() + "%";
	}

	public static boolean checkValidPath(String path){
		String regularExpression = "((/)*[a-zA-Z0-9_-]+(/)*)+";
		Pattern pattern = Pattern.compile(regularExpression);
		return Pattern.matches(regularExpression,path);
	}
	public static String extractDirectoryPath(String path){
		int index = path.lastIndexOf("/");
		return path.substring(0, index);
	}
	public static String extractFileName(String path){
		int index = path.lastIndexOf("/");
		return path.substring(index);
	}
}
