package com.urielsoft.cids.management.biz.dto.search;

import lombok.Getter;
import lombok.Setter;

/**
 * Usage History Pagination Column
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-28
 */
public class Columns {
    /**
     * data
     */
    @Setter
    @Getter
    private String data;

    /**
     * name
     */
    @Getter
    @Setter
    private String name;

    /**
     * searchable
     */
    @Getter
    @Setter
    private boolean searchable;

    /**
     * orderable
     */
    @Getter
    @Setter
    private boolean orderable;

    /**
     * Usage History Pagination Search
     */
    @Getter
    @Setter
    private Search search;
}
