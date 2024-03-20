package com.urielsoft.cids.management.biz.common.utils;

import org.springframework.util.Assert;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-11
 */
public final class DateTimeUtils {

	private static final String DEFAULT_ORIGN_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 기본 원본 일시 포팻을 변환
	 *
	 * @param originDateTime
	 * @param toFormat
	 * @return
	 */
	public static String convertDateTimeFormatFromOriginFormat(String originDateTime, String toFormat) {
		Assert.hasText(originDateTime, "Origin DateTime Data required.");

		int decimalIndex = originDateTime.indexOf('.');
		if (decimalIndex != -1) {
			String milliSeconds = originDateTime.substring(decimalIndex + 1);
			DecimalFormat decimalFormat = new DecimalFormat("000");
			String formattedMilliseconds = decimalFormat.format(Integer.parseInt(milliSeconds));
			originDateTime = originDateTime.substring(0, decimalIndex + 1) + formattedMilliseconds;
		} else {
			originDateTime = originDateTime + ".000";
		}

		LocalDateTime dateTime = LocalDateTime.parse(originDateTime, DateTimeFormatter.ofPattern(DEFAULT_ORIGN_DATETIME_FORMAT));

		return dateTime.format(DateTimeFormatter.ofPattern(toFormat));
	}

	public static String convertDateTimeFormatFromOriginFormat(String originDateTime) {
		Assert.hasText(originDateTime, "Origin DateTime Data required.");

		int decimalIndex = originDateTime.indexOf('.');
		if (decimalIndex != -1) {
			String milliSeconds = originDateTime.substring(decimalIndex + 1);
			DecimalFormat decimalFormat = new DecimalFormat("000");
			String formattedMilliseconds = decimalFormat.format(Integer.parseInt(milliSeconds));
			originDateTime = originDateTime.substring(0, decimalIndex + 1) + formattedMilliseconds;
		} else {
			originDateTime = originDateTime + ".000";
		}

		LocalDateTime dateTime = LocalDateTime.parse(originDateTime, DateTimeFormatter.ofPattern(DEFAULT_ORIGN_DATETIME_FORMAT));

		return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_ORIGN_DATETIME_FORMAT));
	}
}
