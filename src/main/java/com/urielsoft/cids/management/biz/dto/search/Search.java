package com.urielsoft.cids.management.biz.dto.search;

import lombok.Getter;
import lombok.Setter;

/**
 * Usage History Pagination Search
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-28
 */
public class Search {
    @Setter
    @Getter
    private String value;

    /**
     * regex
     */
    @Getter
    @Setter
    private String regex;
}
