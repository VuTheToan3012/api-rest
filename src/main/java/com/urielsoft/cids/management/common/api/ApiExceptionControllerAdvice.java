package com.urielsoft.cids.management.common.api;

import com.urielsoft.cids.management.common.exception.BizException;
import com.urielsoft.cids.management.common.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-10
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionControllerAdvice {

	/**
	 * Validated Annotation으로 선언된 필드에 대해 유효성 검증 후 실패한 경우에 대한 API 결과 처리
	 *
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		log.error("======================= Method Argument Not Valid Occurred =======================");
		log.error(ExceptionUtils.getStackTrace(e));

		ApiResult<ApiError> apiResult = new ApiResult<>();
		apiResult.setSuccess(false);
		apiResult.setData(ApiError.of(ExceptionCode.CMM_BIND_ERROR, e.getBindingResult()));
		
		return new ResponseEntity<>(apiResult, ExceptionCode.CMM_BIND_ERROR.getHttpStatus());
	}

	/**
	 * BizException 발생에 대한 처리
	 *
	 * @param be
	 * @return
	 */
	@ExceptionHandler(BizException.class)
	protected ResponseEntity<ApiResult> handleBizException(final BizException be) {
		log.error("======================= Business Errror Occurred =======================");
		log.error(ExceptionUtils.getStackTrace(be));

		ApiResult<ApiError> apiResult = new ApiResult<>();
		apiResult.setSuccess(false);
		apiResult.setData(ApiError.of(be.getErrorCode(), be.getErrorDescription()));

		return new ResponseEntity<>(apiResult, be.getErrorCode().getHttpStatus());
	}

	/**
	 * 일반적인 Exception 발생에 대한 처리
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiResult> handleException(final Exception e) {
		log.error("======================= Errror Occurred =======================");
		log.error(ExceptionUtils.getStackTrace(e));

		ApiResult<ApiError> apiResult = new ApiResult<>();
		apiResult.setSuccess(false);
		apiResult.setData(ApiError.of(ExceptionCode.CMM_INTERNAL_ERROR, e.getMessage()));

		return new ResponseEntity<>(apiResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
