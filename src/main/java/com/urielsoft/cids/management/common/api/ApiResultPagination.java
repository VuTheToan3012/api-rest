package com.urielsoft.cids.management.common.api;

import com.urielsoft.cids.management.biz.dto.api.result.UsageHistoryListItemResultDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-12
 */
@NoArgsConstructor
@AllArgsConstructor
public class ApiResultPagination<T> extends ApiResult {
	@Getter
	@Setter
	private int draw;

	@Getter
	@Setter
	private int recordsTotal;

	@Getter
	@Setter
	private int recordsFiltered;
}
