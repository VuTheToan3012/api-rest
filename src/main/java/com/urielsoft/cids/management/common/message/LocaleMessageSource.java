package com.urielsoft.cids.management.common.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-08-25
 */
@Slf4j
@RequiredArgsConstructor
@Component("messageSource")
public class LocaleMessageSource extends ReloadableResourceBundleMessageSource {

	private final LocaleMessageService localeMessageService;

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		List<LocaleMessage> allLocalMessageList = this.localeMessageService.getAllLocaleMessageList();
		Optional<LocaleMessage> localeMessage = CollectionUtils.emptyIfNull(allLocalMessageList).stream()
				.filter(e -> StringUtils.equalsIgnoreCase(e.getMsgLocale(), locale.getLanguage()) && StringUtils.equals(e.getMsgKey(), code))
				.findFirst();
		if (localeMessage.isPresent()) {
			return new MessageFormat(localeMessage.get().getMsgContent(), locale);
		} else {
			return super.resolveCode(code, locale);
		}
	}

	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		List<LocaleMessage> localeMessageList = this.localeMessageService.getAllLocaleMessageList();
		Optional<LocaleMessage> localeMessage = CollectionUtils.emptyIfNull(localeMessageList).stream()
				.filter(e -> StringUtils.equalsIgnoreCase(e.getMsgLocale(), locale.getLanguage()) && StringUtils.equals(e.getMsgKey(), code))
				.findFirst();
		if (localeMessage.isPresent()) {
			return localeMessage.get().getMsgContent();
		} else {
			return super.resolveCodeWithoutArguments(code, locale);
		}
	}
}
