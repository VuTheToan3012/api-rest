package com.urielsoft.cids.management.common.message;

import com.urielsoft.cids.management.common.annotation.Db1Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-08-25
 */
@Db1Mapper
@Repository
public interface LocaleMessageMapper {

	/**
	 * Select All Locale Message Menu List
	 *
	 * @return
	 */
	public List<LocaleMessage> selectAllLocaleMessageListMenu();

	/**
	 * Select All Locale Message List
	 *
	 * @return
	 */
	public List<LocaleMessage> selectAllLocaleMessageList();
}
