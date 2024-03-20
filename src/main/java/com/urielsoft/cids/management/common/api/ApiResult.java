package com.urielsoft.cids.management.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-10
 */
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {

	@Getter
	@Setter
	private boolean success;

	@Getter
	@Setter
	private T data;

	public ApiResult(boolean isSuccess) {
		this.success = isSuccess;
	}
}
