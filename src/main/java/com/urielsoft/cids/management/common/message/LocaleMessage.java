package com.urielsoft.cids.management.common.message;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-08-25
 */
public class LocaleMessage implements Serializable {

	@Getter
	@Setter
	private String msgKey;

	@Getter
	@Setter
	private String msgLocale;

	@Getter
	@Setter
	private String msgContent;

	public String getMsgLocaleByLowerCase() {
		return StringUtils.lowerCase(this.msgLocale);
	}
}
