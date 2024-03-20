package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.UpdateAdvertiseManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewAdvertiseManagementDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateAdvertiseManagementParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.AdvertiseManagementDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.AdvertiseManagementListItemResultDTO;
import com.urielsoft.cids.management.biz.service.AdvertiseManagementService;
import com.urielsoft.cids.management.biz.service.UserManagementService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Advertising Management CONTROLLER
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-08-04
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/advertising-management")
public class AdvertisingManagementApiController {
    /**
     * Advertise Management Service
     */
    private final AdvertiseManagementService advertiseManagementService;

    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;

    /**
     * Get All ListAdvertise Mng Data
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ApiResult> listAdvertiseManagementData() throws Exception {
        // Get All List Advertise Mng Data
        List<ViewAdvertiseManagementDTO> dbList = this.advertiseManagementService.listAllAdvertiseManagementData();
        // Use dbList to set Data for resultList
        List<AdvertiseManagementListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var advertiseMngListItemResultDTO = new AdvertiseManagementListItemResultDTO();

                    advertiseMngListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    advertiseMngListItemResultDTO.setStTm(dbInfo.getStTm());
                    advertiseMngListItemResultDTO.setEndTm(dbInfo.getEndTm());
                    advertiseMngListItemResultDTO.setUsageYn(dbInfo.getUsageYn());
                    advertiseMngListItemResultDTO.setFileNm(dbInfo.getFileNm());
                    advertiseMngListItemResultDTO.setPath(dbInfo.getPath());
                    advertiseMngListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));

                    return advertiseMngListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Get Advertise Mng By seq
     *
     * @param advertiseManagementSeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{advertiseManagementSeq}")
    public ResponseEntity<ApiResult> detailAdvertiseManagementData(@PathVariable("advertiseManagementSeq") int advertiseManagementSeq) throws Exception {
        // Get Advertise Mng Information By id
        ViewAdvertiseManagementDTO dbInfo = this.advertiseManagementService.getAdvertiseManagementDataBySeq(advertiseManagementSeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new AdvertiseManagementDetailResultDTO();
        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setStTm(dbInfo.getStTm());
        resultInfo.setEndTm(dbInfo.getEndTm());
        resultInfo.setUsageYn(dbInfo.getUsageYn());
        resultInfo.setFileNm(dbInfo.getFileNm());
        resultInfo.setPath(dbInfo.getPath());
        resultInfo.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * @param file
     * @param updateAdvertiseManagementParamDTO
     * @return
     * @throws Exception
     */
    @PutMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResult> modifyAdvertiseManagementData(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("updateAdvertiseManagementParamDTO") @Validated UpdateAdvertiseManagementParamDTO updateAdvertiseManagementParamDTO) throws Exception {

        var updateAdvertiseMngDTO = new UpdateAdvertiseManagementDTO();

        updateAdvertiseMngDTO.setEndTm(updateAdvertiseManagementParamDTO.getEndTm());
        updateAdvertiseMngDTO.setStTm(updateAdvertiseManagementParamDTO.getStTm());
        updateAdvertiseMngDTO.setUsageYn(updateAdvertiseManagementParamDTO.getUsageYn());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        updateAdvertiseMngDTO.setMdfyUserSeqNo(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        return this.advertiseManagementService.handleFileUpload(file, updateAdvertiseMngDTO);
    }
}
