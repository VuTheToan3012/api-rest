package com.urielsoft.cids.management.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-10
 */
public class ApiError {

	/**
	 * 오류 코드
	 */
	@Getter
	@Setter
	private String code;

	/**
	 * 오류 코드명
	 */
	@Getter
	@Setter
	private String message;

	/**
	 * 오류 상세 내용
	 */
	@Getter
	@Setter
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String description;

	/**
	 * 바인딩 상세 오류 목록
	 */
	@Getter
	@Setter
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<BindErrorField> errors;

	private ApiError(final ExceptionCode exceptionCode) {
		this.code = exceptionCode.getCode();
		this.message = exceptionCode.getName();
	}

	private ApiError(final ExceptionCode exceptionCode, String description) {
		this.code = exceptionCode.getCode();
		this.message = exceptionCode.getName();
		this.description = description;
	}

	private ApiError(final ExceptionCode exceptionCode, List<BindErrorField> bindErrorFields) {
		this.code = exceptionCode.getCode();
		this.message = exceptionCode.getName();
		this.errors = bindErrorFields;
	}

	public static ApiError of(final ExceptionCode exceptionCode) {
		return new ApiError(exceptionCode);
	}

	public static ApiError of(final ExceptionCode exceptionCode, final String description) {
		return new ApiError(exceptionCode, description);
	}

	public static ApiError of(final ExceptionCode exceptionCode, final BindingResult bindingResult) {
		return new ApiError(exceptionCode, BindErrorField.of(bindingResult));
	}

	/**
	 * 바인딩 유효성 검증 실패 시 각 필드에 대한 상세 내용
	 */
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BindErrorField {

		/**
		 * 바인딩 유효성 검사 코드
		 */
		@Getter
		@Setter
		private String code;

		/**
		 * 바인딩 대상 필드
		 */
		@Getter
		@Setter
		private String field;


		/**
		 * 바인딩 유효성 검증 실패 메시지
		 */
		@Getter
		@Setter
		private String message;

		/**
		 * BindingResult를 BindErrorField List로 변환
		 *
		 * @param bindingResult
		 * @return
		 */
		private static List<BindErrorField> of(final BindingResult bindingResult) {
			return bindingResult.getFieldErrors().stream()
					.map(error -> new BindErrorField(error.getCode(), error.getField(), error.getDefaultMessage()))
					.collect(Collectors.toList());
		}
	}
}
