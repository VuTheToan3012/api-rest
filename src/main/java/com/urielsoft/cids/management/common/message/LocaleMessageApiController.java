package com.urielsoft.cids.management.common.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-08-28
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class LocaleMessageApiController {

	private final LocaleMessageService localeMessageService;

	/**
	 * Get All Locale Menu Message
	 *
	 * @return
	 * @throws Exception
	 */
	@GetMapping(path = "/api/locale/all-locale-message")
	public ResponseEntity<Map<String, Map<String, Map<String, String>>>> getAllLocaleMessage() throws Exception {
		return ResponseEntity.ok(this.localeMessageService.getAllLocaleMessageForI18Next());
	}
}
