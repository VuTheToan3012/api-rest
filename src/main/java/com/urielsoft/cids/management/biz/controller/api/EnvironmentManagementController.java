package com.urielsoft.cids.management.biz.controller.api;


import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.UpdateEnvironmentManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewEnvironmentManagementDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateEnvironmentManagementParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.EnvironmentManagementDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.EnvironmentManagementListItemResultDTO;
import com.urielsoft.cids.management.biz.service.EnvironmentManagementService;
import com.urielsoft.cids.management.common.api.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Environment Management Controller
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-12-22
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/environment-management")
public class EnvironmentManagementController {
    /**
     * Environment Management Controller
     */
    private final EnvironmentManagementService environmentManagementService;

    /**
     * Get Menu Data List
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ApiResult> listEnvironmentManagementData() throws Exception {
        // Get All Environment Management
        List<ViewEnvironmentManagementDTO> dbList = this.environmentManagementService.listAllEnvironmentManagementData();
        // Use dbList to set Data for resultList
        List<EnvironmentManagementListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var environmentManagementListItemResultDTO = new EnvironmentManagementListItemResultDTO();

                    environmentManagementListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    environmentManagementListItemResultDTO.setEnvCtgry(dbInfo.getEnvCtgry());
                    environmentManagementListItemResultDTO.setEnvPropNm(dbInfo.getEnvPropNm());
                    environmentManagementListItemResultDTO.setEnvPropVal(dbInfo.getEnvPropVal());
                    environmentManagementListItemResultDTO.setEnvPropValTy(dbInfo.getEnvPropValTy());
                    environmentManagementListItemResultDTO.setNote(dbInfo.getNote());
                    environmentManagementListItemResultDTO.setWkTySeqNo(dbInfo.getWkTySeqNo());
                    environmentManagementListItemResultDTO.setLocTySeqNo(dbInfo.getLocTySeqNo());
                    environmentManagementListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
                    environmentManagementListItemResultDTO.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());
                    environmentManagementListItemResultDTO.setUserNm(dbInfo.getUserNm());

                    return environmentManagementListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Detail Environment Management Data
     *
     * @param seqNo
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{seqNo}")
    public ResponseEntity<ApiResult> detailEnvironmentManagementData(@PathVariable("seqNo") Integer seqNo) throws Exception {
        ViewEnvironmentManagementDTO dbInfo = this.environmentManagementService.getEnvironmentManagementDataBySeqNo(seqNo);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new EnvironmentManagementDetailResultDTO();

        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setEnvCtgry(dbInfo.getEnvCtgry());
        resultInfo.setEnvPropNm(dbInfo.getEnvPropNm());
        resultInfo.setEnvPropVal(dbInfo.getEnvPropVal());
        resultInfo.setEnvPropValTy(dbInfo.getEnvPropValTy());
        resultInfo.setNote(dbInfo.getNote());
        resultInfo.setWkTySeqNo(dbInfo.getWkTySeqNo());
        resultInfo.setLocTySeqNo(dbInfo.getLocTySeqNo());
        resultInfo.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));
        resultInfo.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());
        resultInfo.setUserNm(dbInfo.getUserNm());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Update Environment Management Param DTO
     *
     * @param updateEnvironmentManagementParamDTO
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseEntity<ApiResult> modifyEnvironmentManagementData(@RequestBody @Validated UpdateEnvironmentManagementParamDTO updateEnvironmentManagementParamDTO) throws Exception {
        var updateEnvironmentManagementDTO = new UpdateEnvironmentManagementDTO();

        updateEnvironmentManagementDTO.setSeqNo(updateEnvironmentManagementParamDTO.getSeqNo());
        updateEnvironmentManagementDTO.setEnvPropVal(updateEnvironmentManagementParamDTO.getEnvPropVal());
        updateEnvironmentManagementDTO.setNote(updateEnvironmentManagementParamDTO.getNote());

        this.environmentManagementService.modifyEnvironmentManagementData(updateEnvironmentManagementDTO);

        var apiResult = new ApiResult(true);
        apiResult.setData(updateEnvironmentManagementDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }
}
