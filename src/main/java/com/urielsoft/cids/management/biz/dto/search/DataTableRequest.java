package com.urielsoft.cids.management.biz.dto.search;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

/**
 * Usage History Pagination Data Table Request
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-07-28
 */
public class DataTableRequest {
    /**
     * draw
     */
    @Setter
    @Getter
    private Integer draw;

    /**
     * Usage History Pagination Column
     */
    @Getter
    @Setter
    private List<Columns> columns;

    /**
     * Usage History Pagination Order
     */
    @Getter
    @Setter
    private List<Order> order;

    /**
     * start
     */
    @Getter
    @Setter
    private Integer start;

    /**
     * length
     */
    @Getter
    @Setter
    private Integer length;

    /**
     * empty
     */
    @Getter
    @Setter
    private String empty;

}
