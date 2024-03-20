package com.urielsoft.cids.management.common.exception;

import lombok.Getter;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-10
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 7627561576959107255L;
	
	@Getter
	private final ExceptionCode errorCode;

	@Getter
	private final String errorDescription;

	public BizException(final ExceptionCode errorCode, final String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorDescription = "";
	}

	public BizException(final ExceptionCode errorCode, final String errorMessage, final String errorDescription) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public BizException(final ExceptionCode errorCode) {
		super(errorCode.getName());
		this.errorCode = errorCode;
		this.errorDescription = "";
	}
}
