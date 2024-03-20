package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;
/**
 * DTO for Create Multilingual Management param
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-01
 */
public class CreateMultilingualManagementParamDTO {
    /**
     * Item Name(English)
     */
    @Getter
    @Setter
    private String itemNmEng;

    /**
     * Item Name(Bengali)
     */
    @Getter
    @Setter
    private String itemNmBen;

    /**
     * Item Name(Korean)
     */
    @Getter
    @Setter
    private String itemNmKor;

    /**
     * tagId
     */
    @Getter
    @Setter
    @Size(max = 10)
    private String tagId;
}
