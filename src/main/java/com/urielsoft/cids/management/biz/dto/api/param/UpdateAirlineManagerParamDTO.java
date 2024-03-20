package com.urielsoft.cids.management.biz.dto.api.param;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Update Param Airline
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-20
 */


public class UpdateAirlineManagerParamDTO {

    /**
     * seqNo
     */
    @Getter
    @Setter
    @NotNull
    private Integer seqNo;

    /**
     * alNm ( airlineName )
     */
    @Getter
    @Setter
    @Size(max = 50)
    private String alNm;

    /**
     * alCode ( iata 2Letter )
     */
    @Getter
    @Setter
    @Size(max = 2)
    private String alCode;

    /**
     * alCode3 ( icao code)
     */
    @Getter
    @Setter
    @Size(max = 3)
    private String alCode3;

    /**
     * logoImg ( logo )
     */
    @Getter
    @Setter
    private String logoImg;


    /**
     * mdfyDt ( modify date )
     */
    @Getter
    @Setter
    private String mdfyDt;

    /**
     * IATA 3Digit
     */
    @Getter
    @Setter
    @Size(max = 3)
    private String alCodea;

    /**
     * usage yes or no
     */
    @Getter
    @Setter
    private Integer usageYn;
}
