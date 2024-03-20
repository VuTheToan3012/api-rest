package com.urielsoft.cids.management.biz.dto.api.result;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class SampleAirlineListItemResultDTO {

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
     * regDt ( registration Date )
     */
    @Getter
    @Setter
    @NotNull
    private String regDt;

    /**
     * mdfyDt ( modify date )
     */
    @Getter
    @Setter
    @NotNull
    private String mdfyDt;

    /**
     * userId
     */
    @Getter
    @Setter
    private String userId;

    /**
     * IATA 3Digit
     */
    @Getter
    @Setter
    @Size(max = 2)
    private String alCodea;

    /**
     * usage yes or no
     */
    @Getter
    @Setter
    private Integer usageYn;


}
