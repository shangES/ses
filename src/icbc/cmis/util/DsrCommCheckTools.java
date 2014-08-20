package icbc.cmis.util;

import icbc.cmis.base.TranFailException;
import java.util.*;
import java.text.DecimalFormat;
import icbc.cmis.base.CmisConstance;
import java.io.UnsupportedEncodingException;

/**
 * <p>Title: DSR上送主机时的公用的校验工具</p>
 * <p>Description: 包括的功能有：</p>
 * <p>1 对日期、利率、金额等上送主机前的格式化</p>
 * <p>2 通讯区校验字段的计算</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: ICBC HZ</p>
 * @author zheng ze zhou
 * @version 1.0
 * modify zhengzezhou 20060802 增加了17位账号的校验位计算
 */

public class DsrCommCheckTools {
	/**
	 * 安全系数算法所用的变量
	 */
	private String v_ACCNO1;

	private String v_ACCNO2;

	private String v_AMOUNT1;

	private String v_AMOUNT2;

	private String v_AMOUNT3;

	private String v_TRXSQNB;

	private String v_SAFEPARA;

	/**
	 * 默认系统柜员密钥
	 */
	private static final String constTsfKey = "12345678";

	/**
	 * 加密算法常量因子数组
	 */
	private static final char[] constArray = { '3', '7', '0', '5', '6', '8',
			'9', '3' };

	public DsrCommCheckTools() {
	}

	/**
	 * 初始化参数：
	 * 1 存款帐号 accno1 17位
	 * 2 贷款帐号 accno2 17位
	 * 3 贷款金额 amount1 17位
	 * 4 交易流水号 tradesqno 8位
	 */
	public DsrCommCheckTools(String accno1, String accno2, double amount1,
			String tradesqno) {
		this.v_ACCNO1 = accno1;
		this.v_ACCNO2 = accno2;
		this.v_AMOUNT1 = DsrCommCheckTools.fmtElementContent(fmtAmount(String
				.valueOf(amount1)), 17, '0', 'L');
		this.v_AMOUNT2 = "00000000000000000";
		this.v_AMOUNT3 = "00000000000000000";
		this.v_TRXSQNB = DsrCommCheckTools.fmtElementContent(tradesqno, 8, '0',
				'L');
	}

	/**
	 * 初始化参数：
	 * 1 存款帐号 accno1 17位
	 * 2 交易流水号 tradesqno 8位
	 */
	public DsrCommCheckTools(String accno1, String tradesqno) {
		this.v_ACCNO1 = accno1;
		this.v_ACCNO2 = "00000000000000000";
		this.v_AMOUNT1 = "00000000000000000";
		this.v_AMOUNT2 = "00000000000000000";
		this.v_AMOUNT3 = "00000000000000000";
		this.v_TRXSQNB = DsrCommCheckTools.fmtElementContent(tradesqno, 8, '0',
				'L');
	}

	/**
	 * 初始化参数：
	 * 1 accno1 17位
	 * 2 accno2 17位
	 * 3 amount1 17位
	 * 4 amount2 17位
	 * 5 amount3 17位
	 * 4 tradesqno 8位
	 */
	public DsrCommCheckTools(String accno1, String accno2, double amount1,
			double amount2, double amount3, String tradesqno) {
		this.v_ACCNO1 = accno1;
		this.v_ACCNO2 = accno2;
		this.v_AMOUNT1 = DsrCommCheckTools.fmtElementContent(fmtAmount(String
				.valueOf(amount1)), 17, '0', 'L');
		this.v_AMOUNT2 = DsrCommCheckTools.fmtElementContent(fmtAmount(String
				.valueOf(amount2)), 17, '0', 'L');
		this.v_AMOUNT3 = DsrCommCheckTools.fmtElementContent(fmtAmount(String
				.valueOf(amount3)), 17, '0', 'L');
		this.v_TRXSQNB = DsrCommCheckTools.fmtElementContent(tradesqno, 8, '0',
				'L');
	}

	/**
	 * 初始化此类
	 */
	public void init(Vector v_para) {
	}

	/**
	 * 安全系数校验的算法
	 */
	public final String checkSafePara() throws Exception, TranFailException {
		try {
			/* ACCNO_NUM = ACCNO1_NUM @ ACCNO2_NUM */
			char[] accno1_bit = v_ACCNO1.substring(0, 17).toCharArray();
			char[] accno2_bit = v_ACCNO2.substring(0, 17).toCharArray();
			for (int i = 0; i < 17; i++) {
				int tmp1 = convertCharToInt(accno1_bit[i])
						+ convertCharToInt(accno2_bit[i]);
				accno1_bit[i] = getAddResultBit(tmp1);
			}
			String v_ACCNO_NUM = String.valueOf(accno1_bit);
			/* AMOUNT_NUM = AMOUNT1_NUM + AMOUNT2_NUM + AMOUNT3_NUM */
			char[] amount1_bit = v_AMOUNT1.substring(0, 17).toCharArray();
			char[] amount2_bit = v_AMOUNT2.substring(0, 17).toCharArray();
			char[] amount3_bit = v_AMOUNT3.substring(0, 17).toCharArray();
			for (int i = 0; i < 17; i++) {
				int tmp1 = convertCharToInt(amount1_bit[i])
						+ convertCharToInt(amount2_bit[i])
						+ convertCharToInt(amount3_bit[i]);
				amount1_bit[i] = getAddResultBit(tmp1);
			}
			String v_AMOUNT_NUM = String.valueOf(amount1_bit);
			/* K = KEY @ Z1 @ Z2 @ Y1 @ Y2 @ Y3 @ 37056893 */
			char[] key_bit = constTsfKey.substring(0, 8).toCharArray();
			char[] z1_bit = v_ACCNO_NUM.substring(0, 8).toCharArray();
			char[] mid_bit = v_ACCNO_NUM.substring(8, 9).toCharArray();
			char[] z2_bit = v_ACCNO_NUM.substring(9, 17).toCharArray();
			char[] y1_bit = v_AMOUNT_NUM.substring(1, 9).toCharArray();
			char[] y2_bit = v_AMOUNT_NUM.substring(9, 17).toCharArray();
			char[] y3_bit = v_TRXSQNB.substring(3, 8).toCharArray();

			char[] kk_bit = new char[8];
			int tmpkk;
			tmpkk = convertCharToInt(key_bit[0]) + convertCharToInt(z1_bit[0])
					+ convertCharToInt(z2_bit[0]) + convertCharToInt(y1_bit[0])
					+ convertCharToInt(y2_bit[0])
					+ convertCharToInt(constArray[0]);
			kk_bit[0] = getAddResultBit(tmpkk);
			tmpkk = convertCharToInt(key_bit[1]) + convertCharToInt(z1_bit[1])
					+ convertCharToInt(z2_bit[1]) + convertCharToInt(y1_bit[1])
					+ convertCharToInt(y2_bit[1])
					+ convertCharToInt(constArray[1]);
			kk_bit[1] = getAddResultBit(tmpkk);
			tmpkk = convertCharToInt(key_bit[2]) + convertCharToInt(z1_bit[2])
					+ convertCharToInt(z2_bit[2]) + convertCharToInt(y1_bit[2])
					+ convertCharToInt(y2_bit[2]) + convertCharToInt(y3_bit[0])
					+ convertCharToInt(constArray[2]);
			kk_bit[2] = getAddResultBit(tmpkk);
			tmpkk = convertCharToInt(key_bit[3]) + convertCharToInt(z1_bit[3])
					+ convertCharToInt(z2_bit[3]) + convertCharToInt(y1_bit[3])
					+ convertCharToInt(y2_bit[3]) + convertCharToInt(y3_bit[1])
					+ convertCharToInt(constArray[3]);
			kk_bit[3] = getAddResultBit(tmpkk);
			tmpkk = convertCharToInt(key_bit[4]) + convertCharToInt(z1_bit[4])
					+ convertCharToInt(z2_bit[4]) + convertCharToInt(y1_bit[4])
					+ convertCharToInt(y2_bit[4]) + convertCharToInt(y3_bit[2])
					+ convertCharToInt(constArray[4]);
			kk_bit[4] = getAddResultBit(tmpkk);
			tmpkk = convertCharToInt(key_bit[5]) + convertCharToInt(z1_bit[5])
					+ convertCharToInt(z2_bit[5]) + convertCharToInt(y1_bit[5])
					+ convertCharToInt(y2_bit[5]) + convertCharToInt(y3_bit[3])
					+ convertCharToInt(constArray[5]);
			kk_bit[5] = getAddResultBit(tmpkk);
			tmpkk = convertCharToInt(key_bit[6]) + convertCharToInt(z1_bit[6])
					+ convertCharToInt(z2_bit[6]) + convertCharToInt(y1_bit[6])
					+ convertCharToInt(y2_bit[6]) + convertCharToInt(y3_bit[4])
					+ convertCharToInt(constArray[6]);
			kk_bit[6] = getAddResultBit(tmpkk);
			tmpkk = convertCharToInt(key_bit[7]) + convertCharToInt(z1_bit[7])
					+ convertCharToInt(z2_bit[7]) + convertCharToInt(y1_bit[7])
					+ convertCharToInt(y2_bit[7])
					+ convertCharToInt(mid_bit[0])
					+ convertCharToInt(constArray[7]);
			kk_bit[7] = getAddResultBit(tmpkk);
			/* 扩散变换：K -> H */
			char[] hh_bit = new char[8];
			int tmphh;
			tmphh = convertCharToInt(kk_bit[7]) + convertCharToInt(kk_bit[0])
					+ convertCharToInt(kk_bit[1]);
			hh_bit[0] = getAddResultBit(tmphh);
			tmphh = convertCharToInt(kk_bit[0]) + convertCharToInt(kk_bit[1])
					+ convertCharToInt(kk_bit[2]);
			hh_bit[1] = getAddResultBit(tmphh);
			tmphh = convertCharToInt(kk_bit[1]) + convertCharToInt(kk_bit[2])
					+ convertCharToInt(kk_bit[3]);
			hh_bit[2] = getAddResultBit(tmphh);
			tmphh = convertCharToInt(kk_bit[2]) + convertCharToInt(kk_bit[3])
					+ convertCharToInt(kk_bit[4]);
			hh_bit[3] = getAddResultBit(tmphh);
			tmphh = convertCharToInt(kk_bit[3]) + convertCharToInt(kk_bit[4])
					+ convertCharToInt(kk_bit[5]);
			hh_bit[4] = getAddResultBit(tmphh);
			tmphh = convertCharToInt(kk_bit[4]) + convertCharToInt(kk_bit[5])
					+ convertCharToInt(kk_bit[6]);
			hh_bit[5] = getAddResultBit(tmphh);
			tmphh = convertCharToInt(kk_bit[5]) + convertCharToInt(kk_bit[6])
					+ convertCharToInt(kk_bit[7]);
			hh_bit[6] = getAddResultBit(tmphh);
			tmphh = convertCharToInt(kk_bit[6]) + convertCharToInt(kk_bit[7])
					+ convertCharToInt(kk_bit[0]);
			hh_bit[7] = getAddResultBit(tmphh);
			return String.valueOf(hh_bit);
		} catch (Exception ex) {
			throw new TranFailException("DsrCommCheckErr001",
					"icbc.cmis.util.DsrCommCheckTools", "计算安全码时出错", ex
							.toString());
		}
	}

	private static int convertCharToInt(char convertChar) {
		return Integer.parseInt(String.valueOf(convertChar));
	}

	private static char getAddResultBit(int numbit) {
		String tmpS = String.valueOf(numbit);
		return tmpS.toCharArray()[tmpS.length() - 1];
	}

	/**
	 * <p>对数据包的内容进行格式化</p>
	 * @param String OriginalString 元数据
	 * @param int TotalLength 字段定长
	 * @param char AttachedChar 要补上的字符，如空格或零
	 * @param char RorL 左补或右补 'R' 右补； 'L' 左补
	 * @return String 补齐长度后的字符串
	 */
	public static String fmtElementContent(String OriginalString,
			int TotalLength, char AttachedChar, char RorL) {
		if (OriginalString == null) {
			OriginalString = "";
		}
		int LofS = DsrCommCheckTools.getBytesLength(OriginalString); //原字符串长度
		String tempS = OriginalString;
		if (LofS >= TotalLength) { //如果原字符串大于最后长度
			try {
				return OriginalString.substring(0, TotalLength); //截取字段定长
			} catch (Exception ex) {
				return OriginalString;
			}
		} else {
			for (int i = 0; i < TotalLength - LofS; i++) {
				if (RorL == 'R') {
					tempS = tempS.concat(String.valueOf(AttachedChar));
				} else {
					tempS = String.valueOf(AttachedChar).concat(tempS);
				}
			}
			return tempS;
		}
	}

	/**
	 * <b>功能描述: </b>此函数用于计算进行格式化的字符转换成字节的字节数<br>
	 * <p>注：用于联机交易	</p>
	 * @see
	 * @param String 源String
	 * @return int 字节数
	 */
	public static int getBytesLength(String srcStr) {
		if (srcStr == null) {
			return 0;
		}
		String encoding = (String) CmisConstance.getParameterSettings().get(
				"dsrEndoding");
		if (encoding == null || encoding.trim().length() == 0) {
			encoding = "GBK";
		}
		try {
			return (srcStr.getBytes(encoding)).length;
		} catch (UnsupportedEncodingException ex) {
			return srcStr.length();
		}
	}

	/**
	 * <p>对金额数据进行格式化</p>
	 * <p>根据约定，保存在CM2002数据库中的金额数据带小数点后两位，但凡是送往主机的数字均不能带小数点；</p>
	 * <p>所以金额数据要乘100，即以分为单位，送往主机</p>
	 * @param String 规整前的金额数据
	 * @return String 规整后的金额数据
	 */
	public static String fmtAmount(String OriginalAmount) {
		if (OriginalAmount == null || OriginalAmount.equals("")) {
			OriginalAmount = "0";
		}
		java.text.DecimalFormat decimalFmt = new java.text.DecimalFormat();
		decimalFmt.applyPattern("#0.00#");
		double tmpD = 0;
		try {
			tmpD = Double.parseDouble(OriginalAmount);
		} catch (NumberFormatException ex) {
		}
		tmpD = tmpD * 100; //对金额数据乘以100，以便能消除小数
		String tmpS = decimalFmt.format(tmpD);
		return tmpS.substring(0, tmpS.indexOf("."));
	}

	/**
	 * 注：此方法已经过期，请改用fmtRateAll 2005-6-1
	 * <p>对CM2002中的利率（一般是最终利率）进行格式化，以便能上送主机 </p>
	 * <p>CM2002数据库中保存的利率带小数点，但送往主机时不能带小数点，所以 </p>
	 * <p>根据约定，利率数据首先乘以1000000，不足8位，前补0，再根据开关参数选择最前位补9或0 </p>
	 * <p>注：此函数能够正常处理的利率的范围 [0,100)  </p>
	 * @param String 规整前的利率数据
	 * @param char 是否前补9 - 'Y'或'9' 补9；'N'或'0' 补0
	 * @return String 规整后的利率数据(小数点后可能不止两位)
	 * @deprecated 2005-6-1
	 */
	public static String fmtRate(String OriginalRate, char Pitch9orNot) {
		if (OriginalRate == null || OriginalRate.equals("")) {
			OriginalRate = "0";
		}
		java.text.DecimalFormat decimalFmt = new java.text.DecimalFormat();
		decimalFmt.applyPattern("#0.00####");
		double tmpD = Double.parseDouble(OriginalRate);
		tmpD = tmpD * 1000000; //对利率数据乘以1000000
		String tmpS = decimalFmt.format(tmpD);
		tmpS = tmpS.substring(0, tmpS.indexOf(".")); //对利率数据进行规整
		int LofS = tmpS.length(); //利率数据规整后的长度
		if (Pitch9orNot == 'Y' || Pitch9orNot == '9') {
			for (int i = 0; i < 8 - LofS; i++) { //要补9的情况
				tmpS = "0" + tmpS;
			}
			tmpS = "9" + tmpS;
		} else {
			for (int i = 0; i < 9 - LofS; i++) { //全补0的情况
				tmpS = "0" + tmpS;
			}
		}
		return tmpS;
	}

	/**
	 * 注：此方法已经过期，请改用fmtRateAll 2005-6-1
	 * <p>对CM2002中的利率（一般是最终利率）进行格式化，以便能上送主机 For 主机直接记帐 2004-10-8 </p>
	 * <p>CM2002数据库中保存的利率带小数点，但送往主机时不能带小数点，所以 </p>
	 * <p>根据约定，利率数据首先乘以100000000，不足9位前补0。这个函数是在原来函数基础上改写的 </p>
	 * <p>注：此函数处理利率指不带百分号的利率 比如百分之十一，此处入口参数就是"0.11" </p>
	 * @param String 规整前的利率数据(小数点后可能不止两位)
	 * @return String 规整后的利率数据
	 * @deprecated 2005-6-1
	 */
	public static String fmtRateNew(String OriginalRate) {
		if (OriginalRate == null || OriginalRate.equals("")) {
			OriginalRate = "0";
		}
		java.text.DecimalFormat decimalFmt = new java.text.DecimalFormat();
		decimalFmt.applyPattern("#0.00####");
		double tmpD = Double.parseDouble(OriginalRate);
		tmpD = tmpD * 100000000; //对利率数据乘以一个整数
		String tmpS = decimalFmt.format(tmpD);
		tmpS = tmpS.substring(0, tmpS.indexOf(".")); //对利率数据进行规整
		int LofS = tmpS.length(); //利率数据规整后的长度

		for (int i = 0; i < 9 - LofS; i++) { //不足前补0
			tmpS = "0" + tmpS;
		}
		return tmpS;
	}

	/**
	 * <p>说明：利率及利率浮动率格式化函数</p>
	 * <p>用途：台帐中利率及利率浮动率的格式和主机不同，为了能让主机正确的识别，在上送主机时，需要调用该函数</p>
	 * <p>     对上送的利率及利率浮动率字段进行格式化</p>
	 * <p>调用时机：联机交易前</p>
	 * <p>需要调用的字段：台帐中与利率相关，又可能带有小数点的，比如以下字段：</p>
	 * <p>正常（展期）利率浮动率</p>
	 * <p>正常执行利率或逾期利率</p>
	 * <p>逾期利率浮动率</p>
	 * @param String OriginalRate 规整前的台帐数据格式
	 * @param String FormatType 0 浮动值（浮动档次） 1 浮动值（浮动点数） 2 利率
	 * @return String 规整后的主机数据格式
	 * @example:
	 * 1 如果要上送以浮动档次计算的正常利率浮动率，则OriginalRate送台帐格式浮动率，FormatType送0
	 *   调用：DsrCommCheckTools.fmtRateAll("10","0")
	 * 2 如果要上送利率值，则OriginalRate送台帐格式利率，FormatType送2
	 *   调用：DsrCommCheckTools.fmtRateAll("5.58","2")
	 *
	 * 以下实例经JUnit测试通过：
	 *     assertEquals("-999123456",DsrCommCheckTools.fmtRateAll("-99.123456","1"));
	 *     assertEquals("999123456",DsrCommCheckTools.fmtRateAll("99.123456","1"));
	 *     assertEquals("-900123456",DsrCommCheckTools.fmtRateAll("-0.123456","1"));
	 *     assertEquals("900123456",DsrCommCheckTools.fmtRateAll("0.123456","1"));
	 *     assertEquals("-901123456",DsrCommCheckTools.fmtRateAll("-1.123456","1"));
	 *     assertEquals("901123456",DsrCommCheckTools.fmtRateAll("1.123456","1"));
	 *
	 *     assertEquals("-099123456",DsrCommCheckTools.fmtRateAll("-99.123456","2"));
	 *     assertEquals("099123456",DsrCommCheckTools.fmtRateAll("99.123456","2"));
	 *     assertEquals("899123456",DsrCommCheckTools.fmtRateAll("899.123456","2"));
	 *     assertEquals("001123456",DsrCommCheckTools.fmtRateAll("1.123456","2"));
	 *     assertEquals("-001123456",DsrCommCheckTools.fmtRateAll("-1.123456","2"));
	 *     assertEquals("000123456",DsrCommCheckTools.fmtRateAll("0.123456","2"));
	 *
	 *     assertEquals("-099123456",DsrCommCheckTools.fmtRateAll("-99.123456","0"));
	 *     assertEquals("099123456",DsrCommCheckTools.fmtRateAll("99.123456","0"));
	 *     assertEquals("899123456",DsrCommCheckTools.fmtRateAll("899.123456","0"));
	 *     assertEquals("001123456",DsrCommCheckTools.fmtRateAll("1.123456","0"));
	 *     assertEquals("-001123456",DsrCommCheckTools.fmtRateAll("-1.123456","0"));
	 *     assertEquals("000123456",DsrCommCheckTools.fmtRateAll("0.123456","0"));
	 *
	 */
	public static String fmtRateAll(String OriginalRate, String FormatType) {
		//参数校验
		if (OriginalRate == null || OriginalRate.equals("")) {
			OriginalRate = "0";
		}
		//将字符型转换为数值型
		double tmpD;
		try {
			tmpD = Double.parseDouble(OriginalRate);
		} catch (NumberFormatException ex) {
			tmpD = 0;
		}
		//对利率数据乘以一个整数
		tmpD = tmpD * 1000000;
		//如果是浮动点数 则再加上或减去个9亿
		if (FormatType != null && FormatType.equals("1")) {
			if (tmpD >= 0) {
				tmpD = tmpD + 900000000;
			} else {
				tmpD = tmpD - 900000000;
			}
		}
		//格式化输出
		String fmtPattern = "000000000";
		return fmtDecimal(tmpD, fmtPattern);
	}

	public static String fmtDecimal(double originalDouble, String pattern) {
		java.text.DecimalFormat decimalFmt = new java.text.DecimalFormat();
		decimalFmt.applyPattern(pattern);
		String retString = decimalFmt.format(originalDouble);
		return retString;
	}

	/**
	 * 注：此方法已经过期，请改用fmtRateAll 2005-6-1
	 * <p> 格式化主机的浮动档次； </p>
	 * <p> 台帐页面输入域为：(-100,900) ； </p>
	 * 上送值逻辑：<br>
	 * 浮动档次值*1000000 不足的部分补0 <br>
	 * @param String 实际浮动点数
	 * @return String 上送主机浮动点数
	 * @author zhangjian
	 * @deprecated 2005-6-1
	 */
	public static String fmtFloatBaddish(String realdot) {
		double tmpd = 0;
		double retd = 0;
		java.text.DecimalFormat decimalFmt = new java.text.DecimalFormat();
		decimalFmt.applyPattern("#0.000000#");

		try {
			if (realdot == null) {
				realdot = "0";
			}
			tmpd = Double.parseDouble(realdot);
		} catch (NumberFormatException ex) {
		}
		tmpd = tmpd * 1000000;
		String tmpS = decimalFmt.format(tmpd);

		if (tmpd < 0) {
			tmpS = tmpS.substring(1, tmpS.indexOf(".")); //对利率数据进行规整
		} else {
			tmpS = tmpS.substring(0, tmpS.indexOf(".")); //对利率数据进行规整
		}

		if (tmpS.length() < 9) {
			int j = 9 - tmpS.length();

			for (int i = 0; i < j; i++) { //不足9位前补0
				tmpS = "0" + tmpS;
			}
		}
		if (tmpd < 0) { //如果是负数,则在前面补上一个"-"
			tmpS = "-" + tmpS;
		}

		return tmpS;
	}

	/**
	 * 注：此方法已经过期，请改用fmtRateAll 2005-6-1
	 * <p> 主机的浮动点数只能调整利率的万分位（包括10个万分位）根据开关选择乘以100000000,还是1000000)； </p>
	 * <p> 范围为：-0.0010 至 0.0010，也就是绝对值不大于0.0010 ； </p>
	 * 上送值逻辑：<br>
	 * IF 实际浮动点数 > 0 THEN <br>
	 *   上送主机浮动点数 = 实际浮动点数 * 100,000,000(1,000,000) + 900,000,000 <br>
	 * ELSE <br>
	 *   上送主机浮动点数 = 实际浮动点数 * 100,000,000(1,000,000) - 900,000,000 <br>
	 * END <br>
	 * @param String 实际浮动点数
	 * @param char 浮动点数分位制 'B'百分位制 'W'万分位制
	 * @return String 上送主机浮动点数
	 * @author yangguanrun
	 * @deprecated 2005-6-1
	 */
	public static String fmtFloatDot(String realdot, char format) {
		double tmpd = 0;
		double retd = 0;
		int yinzi = 0;
		yinzi = format == 'B' ? 1000000 : 100000000;
		try {
			if (realdot == null) {
				realdot = "0";
			}
			tmpd = Double.parseDouble(realdot);
		} catch (NumberFormatException ex) {
		}
		if (tmpd > 0) {
			retd = tmpd * yinzi + 900000000;
		} else if (tmpd == 0) {
		} else {
			retd = tmpd * yinzi - 900000000;
		}
		java.text.DecimalFormat decimalFmt = new java.text.DecimalFormat();
		decimalFmt.applyPattern("000000000");
		return String.valueOf(decimalFmt.format(retd));
	}

	/**
	 * <p>日期格式转换函数：从‘yyyymmdd’到‘yyyy-mm-dd’</p>
	 * <p>说明：在所有正式表中日期格式为‘yyyymmdd’，但是在准贷证表中所有日期格式为‘yyyy-mm-dd’</p>
	 * <p>无论是保存到数据库还是送往主机，均是这个格式。</p>
	 * @param 日期，格式‘yyyymmdd’
	 * @return 日期，格式‘yyyy-mm-dd’
	 */
	public static String formatDate(String date) {
		if (date == null || date.length() < 8) {
			date = "00000000";
		}
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		return year.trim() + "-" + month.trim() + "-" + day.trim();
	}

	/**
	 * 日期格式转换函数：从‘yyyymmdd’到‘yyyy-mm-dd’或‘yyyy.mm.dd’；分隔符可以设定 <p>
	 * 说明：在所有正式表中日期格式为‘yyyymmdd’，但是在准贷证表中日期格式为‘yyyy-mm-dd’；
	 * ‘到期日标志符’字段是‘yyyy.mm.dd’格式。<p>
	 * @param 日期，格式‘yyyymmdd’
	 * @return 日期，格式‘yyyy.mm.dd’或‘yyyy-mm-dd’
	 */
	public static String formatDate(String date, char splitChar) {
		if (date == null || date.length() < 8) {
			date = "00000000";
		}
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		return year.trim() + splitChar + month.trim() + splitChar + day.trim();
	}

	/**
	 * 日期格式逆转换函数：从‘yyyy-mm-dd’到‘yyyymmdd’
	 * 说明：对主机下传的日期格式为‘yyyy-mm-dd’，许可证表中存储的格式是‘yyyymmdd’
	 * @param 日期，格式‘yyyy-mm-dd’
	 * @return 日期，格式‘yyyymmdd’
	 */
	public static String trimDate(String hostDate) {
		if (hostDate == null || hostDate.length() != 10) {
			hostDate = "9999-12-31";
		}
		String year = hostDate.substring(0, 4);
		String month = hostDate.substring(5, 7);
		String day = hostDate.substring(8, 10);
		return year + month + day;
	}

	/**
	 * 时间格式逆转换函数：从‘HH:mm:ss’到‘HHmmss’
	 * 说明：对主机下传的时间格式为‘HH:mm:ss’，许可证表中存储的格式是‘HHmmss’
	 * @param 时间，格式‘HH:mm:ss’
	 * @return 时间，格式‘HHmmss’
	 */
	public static String trimTime(String hostTime) {
		if (hostTime == null || hostTime.length() != 8) {
			hostTime = "99:99:99";
		}
		String hour = hostTime.substring(0, 2);
		String minute = hostTime.substring(3, 5);
		String second = hostTime.substring(6, 8);
		return hour + minute + second;
	}

	/**
	 * 根据贷款账号的前17位计算校验码
	 * 说明：台帐数据库保存的账号为19位，如果是17位账号，需要追加校验位后保存
	 * @param 账号，未加校验位前的账号
	 * @return 校验码，2位
	 * 测试：12020202101540874 期望 13
	 *      02000260100000040 期望 84
	 */
	public static String accountVerifyCode(String AccNo17) {
		String retCode = "00";
		try {
			//账号不能为空
			if (AccNo17 == null) {
				return "";
			}
			//小于17位的账号不校验
			if (AccNo17.length() < 17)
				return "";
			//大于17位的账号不追加校验位
			if (AccNo17.length() > 17)
				return "";
			int num_so;
			int num_ob;
			num_so = Integer.parseInt(AccNo17.substring(0, 1)) * 11
					+ Integer.parseInt(AccNo17.substring(1, 2)) * 13
					+ Integer.parseInt(AccNo17.substring(2, 3)) * 17
					+ Integer.parseInt(AccNo17.substring(3, 4)) * 19
					+ Integer.parseInt(AccNo17.substring(4, 5)) * 23
					+ Integer.parseInt(AccNo17.substring(5, 6)) * 29
					+ Integer.parseInt(AccNo17.substring(6, 7)) * 31
					+ Integer.parseInt(AccNo17.substring(7, 8)) * 37
					+ Integer.parseInt(AccNo17.substring(8, 9)) * 41
					+ Integer.parseInt(AccNo17.substring(9, 10)) * 43
					+ Integer.parseInt(AccNo17.substring(10, 11)) * 47
					+ Integer.parseInt(AccNo17.substring(11, 12)) * 53
					+ Integer.parseInt(AccNo17.substring(12, 13)) * 59
					+ Integer.parseInt(AccNo17.substring(13, 14)) * 61
					+ Integer.parseInt(AccNo17.substring(14, 15)) * 67
					+ Integer.parseInt(AccNo17.substring(15, 16)) * 71
					+ Integer.parseInt(AccNo17.substring(16, 17)) * 73;
			num_ob = 97 - num_so % 97;
			retCode = DsrCommCheckTools.fmtElementContent(String
					.valueOf(num_ob), 2, '0', 'L');
		} catch (Exception ex) {			
		}
		return retCode;
	}

	public static void main(String[] args) throws Exception {
		DsrCommCheckTools dsrCommCheckTools1 = new DsrCommCheckTools(
				"02000260100150069", "02000260090150819", 1, "00000500");
		System.out.println(dsrCommCheckTools1.checkSafePara());
		System.out.println(DsrCommCheckTools.accountVerifyCode("02000260100000040"));
	}
}