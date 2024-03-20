package com.urielsoft.cids.management.common.mybatis;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-05
 */
public final class MyBatisResultHelper {

	/**
	 * 영향받은 Row 개수가 필수 영향 개수와 다를 경우 Exception 발생하도록 함
	 *
	 * @param affectedRowsCount         영향 받은 Row 수
	 * @param requiredAffectedRowsCount 영향 받아야 할 Row 수
	 * @throws DataAccessException
	 */
	public static void checkAffectedRow(int affectedRowsCount, int requiredAffectedRowsCount) throws DataAccessException {
		if (affectedRowsCount != requiredAffectedRowsCount) {
			throw new IncorrectUpdateSemanticsDataAccessException("No data updated");
		}
	}
}
