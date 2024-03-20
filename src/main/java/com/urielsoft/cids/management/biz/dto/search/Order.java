package com.urielsoft.cids.management.biz.dto.search;

import lombok.Getter;
import lombok.Setter;

/**
 * Usage History Pagination Order
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-28
 */
public class Order {
    /**
     * column
     */
    @Setter
    @Getter
    private String column;

    /**
     * dir
     */
    @Getter
    @Setter
    private String dir;
}
