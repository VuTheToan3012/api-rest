package com.urielsoft.cids.management.biz.dto.api.result;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User Deatil Data Result
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-19
 */

public class UserManagementDetailResultDTO {
	/**
	 * seqNo
	 */
	@Getter
	@Setter
	@NotNull
	private Integer seqNo;

	/**
	 * userNm
	 */
	@Getter
	@Setter
	@Nullable
	@Size( max = 50)
	private String userNm;

	/**
	 * userId
	 */
	@Getter
	@Setter
	@Nullable
	@Size( max = 50)
	private String userId;

	/**
	 * userPw
	 */
	@Getter
	@Setter
	@Nullable
	private String userPw;

	/**
	 * authSeqNo
	 */
	@Getter
	@Setter
	@NotNull
	private Integer authSeqNo;

	/**
	 * regDt
	 */
	@Getter
	@Setter
	@NotNull
	private String regDt;

	/**
	 * mdfyDt
	 */
	@Getter
	@Setter
	@NotNull
	private String mdfyDt;

	/**
	 * authTyNm
	 */
	@Getter
	@Setter
	@NotNull
	private String authTyNm;

	/**
	 * mdfyUserSeqNo
	 */
	@Getter
	@Setter
	@NotNull
	private Integer mdfyUserSeqNo;
}
