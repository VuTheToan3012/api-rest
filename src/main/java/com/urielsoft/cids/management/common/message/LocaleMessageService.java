package com.urielsoft.cids.management.common.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-08-27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LocaleMessageService {

	private final LocaleMessageMapper localeMessageMapper;

	private final ApplicationContext applicationContext;

	/**
	 * Get All Locale Message Menu
	 *
	 * @return
	 */
	@Cacheable(value = "localeMessageMenuCache")
	public List<LocaleMessage> getAllLocaleMessageMenuList() {
		return this.localeMessageMapper.selectAllLocaleMessageListMenu();
	}

	/**
	 * Get All Locale Message Item
	 *
	 * @return
	 */
	@Cacheable(value = "localeMessageItemCache")
	public List<LocaleMessage> getAllLocaleMessageItemList() {
		return this.localeMessageMapper.selectAllLocaleMessageList();
	}

	/**
	 * Get All Locale Message List
	 *
	 * @return
	 */
	@Cacheable(value = "localeMessageListCache")
	public List<LocaleMessage> getAllLocaleMessageList() {
		List<LocaleMessage> menuLocaleList = this.getAllLocaleMessageMenuList();
		List<LocaleMessage> itemLocaleList = this.getAllLocaleMessageItemList();
		List<LocaleMessage> allLocaleMessageList = Stream.concat(menuLocaleList.stream(), itemLocaleList.stream()).collect(Collectors.toList());
		return allLocaleMessageList;
	}


	/**
	 * Refresh All Locale Message List
	 */
	@CacheEvict(value = "localeMessageListCache")
	public void refreshLocaleMessageList() {
		log.info("System Locale Message is Refreshed.");
	}

	/**
	 * Refresh All Locale Message Menu List
	 */
	@CacheEvict(value = "localeMessageMenuCache")
	public void refreshLocaleMessageMenuList() {
		log.info("System Locale Message is Refreshed.");
	}

	/**
	 * Refresh All Locale Message Item List
	 */
	@CacheEvict(value = "localeMessageItemCache")
	public void refreshAllLocaleMessageItemList() {
		log.info("System Locale Message is Refreshed.");
	}

	/**
	 * Get all locale message map for i18next
	 *
	 * @return
	 */
	public Map<String, Map<String, Map<String, String>>> getAllLocaleMessageForI18Next() {
		Map<String, Map<String, Map<String, String>>> resultMap = new HashMap<>();

		// cacheable 메소드의 self-invocation을 회피하기 위해 자신의 Bean을 application context를 통해서 가져옴
		LocaleMessageService localeMessageService = this.applicationContext.getBean(LocaleMessageService.class);

		// List -> Map 구조로 바꾸면서 MsgLocale을 기준으로 그룹핑
		List<LocaleMessage> allLocaleMessageList = localeMessageService.getAllLocaleMessageList();

		Map<String, List<LocaleMessage>> localeMessageGroupMap = allLocaleMessageList.stream().collect(Collectors.groupingBy(LocaleMessage::getMsgLocaleByLowerCase, Collectors.toList()));
		for (String localeKey : localeMessageGroupMap.keySet()) {
			if (localeMessageGroupMap != null && localeKey != null && localeMessageGroupMap.containsKey(localeKey)) {
				Map<String, Map<String, String>> translateMap = new HashMap<>();
				List<LocaleMessage> localeMessageMap = localeMessageGroupMap.get(localeKey);
				if (localeMessageMap != null) {
					Map<String, String> translationMap = localeMessageMap.stream()
							.filter(localeMessage -> localeMessage.getMsgKey() != null && localeMessage.getMsgContent() != null)
							.collect(Collectors.toMap(LocaleMessage::getMsgKey, LocaleMessage::getMsgContent));

					translateMap.put("translation", translationMap);
					resultMap.put(localeKey, translateMap);
				}
			}
		}

		return resultMap;
	}
}
