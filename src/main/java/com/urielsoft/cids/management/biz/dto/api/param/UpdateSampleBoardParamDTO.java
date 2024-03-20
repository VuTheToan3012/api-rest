package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Hojun, Lee (hjlee@urielsoft.co.kr)
 * @version 1.0
 * @since 2023-07-11
 */

public class UpdateSampleBoardParamDTO {

    /**
     * Board Sequence
     */
    @Getter
    @Setter
    @NotNull
    private Integer boardSeq;

    /**
     * Title
     */
    @Getter
    @Setter
    @NotBlank
    private String title;

    /**
     * Content
     */
    @Getter
    @Setter
    @NotBlank
    private String content;
}
