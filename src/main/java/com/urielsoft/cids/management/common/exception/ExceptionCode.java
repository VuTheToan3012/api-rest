package com.urielsoft.cids.management.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum of Error Code
 *
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-10
 */
@AllArgsConstructor
public enum ExceptionCode {

	CMM_BIND_ERROR(HttpStatus.BAD_REQUEST, "CMM_001", "잘못된 인자값 입니다."),
	CMM_AUTH_ERROR(HttpStatus.UNAUTHORIZED, "CMM_002", "로그인 실패. 잘못된 아이디 또는 비밀번호 입니다."),
	CMM_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CMM_999", "시스템 내부 오류가 발생했습니다."),

	BIZ_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BIZ_001", "비즈니스 처리 중 오류가 발생했습니다."),
	BIZ_DUP_DATA(HttpStatus.BAD_REQUEST, "BIZ_002", "이미 등록된 데이터 입니다."),
	BIZ_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "BIZ_003", "등록된 데이터가 없습니다.");

	@Getter
	private HttpStatus httpStatus;

	@Getter
	private String code;

	@Getter
	private String name;
}
