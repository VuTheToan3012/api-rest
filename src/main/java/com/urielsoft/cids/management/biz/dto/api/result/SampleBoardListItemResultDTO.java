package com.urielsoft.cids.management.biz.dto.api.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-11
 */

public class SampleBoardListItemResultDTO {

	/**
	 * Board Sequence
	 */
	@Getter
	@Setter
	private Integer boardSeq;

	/**
	 * Title
	 */
	@Getter
	@Setter
	private String title;

	/**
	 * Create User ID
	 */
	@Getter
	@Setter
	private String createUserId;

	/**
	 * Create User Name
	 */
	@Getter
	@Setter
	private String createUserName;

	/**
	 * Created Date Time
	 */
	@Getter
	@Setter
	private String createDt;
}
