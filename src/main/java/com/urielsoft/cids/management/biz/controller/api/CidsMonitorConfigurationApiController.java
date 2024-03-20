package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.SearchCidsMonitorConfigurationFindLocAndTyDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsMonitorConfigurationDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsMonitorConfigurationDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CidsMonitorConfigurationFindLocAndTyParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateCidsMonitorConfigurationParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.CidsMonitorConfigurationDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.CidsMonitorConfigurationListItemResultDTO;
import com.urielsoft.cids.management.biz.service.CidsMonitorConfigurationService;
import com.urielsoft.cids.management.biz.service.UserManagementService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Cids Monitor Configuration CONTROLLER
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-08-05
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/cids-monitor-configuration")
public class CidsMonitorConfigurationApiController {
    /**
     * Cids Monitor Configuration Service
     */
    private final CidsMonitorConfigurationService cidsMonitorConfigurationService;

    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;


    /**
     * Get All List Cids Monitor Configuration
     *
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/list")
    public ResponseEntity<ApiResult> listCidsMonitorConfigurationData() throws Exception {
        // Get All Monitor Information
        List<ViewCidsMonitorConfigurationDTO> dbList = this.cidsMonitorConfigurationService.listAllCidsMonitorConfigurationData();
        // Use dbList to set Data for resultList
        List<CidsMonitorConfigurationListItemResultDTO> resultList = CollectionUtils.emptyIfNull(dbList).stream()
                .map(dbInfo -> {
                    var cidsMonitorConfigurationListItemResultDTO = new CidsMonitorConfigurationListItemResultDTO();

                    cidsMonitorConfigurationListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
                    cidsMonitorConfigurationListItemResultDTO.setWkTyNm(dbInfo.getWkTyNm());
                    cidsMonitorConfigurationListItemResultDTO.setWkTySeqNo(dbInfo.getWkTySeqNo());
                    cidsMonitorConfigurationListItemResultDTO.setLocNm(dbInfo.getLocNm());
                    cidsMonitorConfigurationListItemResultDTO.setLocTySeqNo(dbInfo.getLocTySeqNo());
                    cidsMonitorConfigurationListItemResultDTO.setTmplatSeqNo(dbInfo.getTmplatSeqNo());
                    cidsMonitorConfigurationListItemResultDTO.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());
                    cidsMonitorConfigurationListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));

                    cidsMonitorConfigurationListItemResultDTO.setTmplatNm(dbInfo.getTmplatNm());
                    cidsMonitorConfigurationListItemResultDTO.setUserNm(dbInfo.getUserNm());

                    return cidsMonitorConfigurationListItemResultDTO;
                }).collect(Collectors.toList());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultList);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Get Monitor Manager By cidsMonitorManagerSeq
     *
     * @param cidsMonitorManagerSeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{cidsMonitorManagerSeq}")
    public ResponseEntity<ApiResult> detailCidsMonitorConfigurationData(@PathVariable("cidsMonitorManagerSeq") int cidsMonitorManagerSeq) throws Exception {
        // Get User Information By id
        ViewCidsMonitorConfigurationDTO dbInfo = this.cidsMonitorConfigurationService.getCidsMonitorConfigurationDataBySeq(cidsMonitorManagerSeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new CidsMonitorConfigurationDetailResultDTO();

        resultInfo.setLocTySeqNo(dbInfo.getLocTySeqNo());
        resultInfo.setWkTyNm(dbInfo.getWkTyNm());
        resultInfo.setWkTySeqNo(dbInfo.getWkTySeqNo());
        resultInfo.setLocNm(dbInfo.getLocNm());
        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setTmplatSeqNo(dbInfo.getTmplatSeqNo());
        resultInfo.setMdfyUserSeqNo(dbInfo.getMdfyUserSeqNo());
        resultInfo.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getMdfyDt()));

        resultInfo.setTmplatNm(dbInfo.getTmplatNm());
        resultInfo.setUserNm(dbInfo.getUserNm());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Update template Monitor Manager Information
     *
     * @param updateCidsMonitorConfigurationParamDto
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseEntity<ApiResult> modifyCidsMonitorConfigurationData(@RequestBody @Validated UpdateCidsMonitorConfigurationParamDTO updateCidsMonitorConfigurationParamDto) throws Exception {

        var updateCidsMonitorConfigurationDto = new UpdateCidsMonitorConfigurationDTO();

        updateCidsMonitorConfigurationDto.setSeqNo(updateCidsMonitorConfigurationParamDto.getSeqNo());
        updateCidsMonitorConfigurationDto.setTmplatSeqNo(updateCidsMonitorConfigurationParamDto.getTmplatSeqNo());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();
        updateCidsMonitorConfigurationDto.setMdfyUserSeqNo(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.cidsMonitorConfigurationService.modifyCidsMonitorConfigurationData(updateCidsMonitorConfigurationDto);

        var apiResult = new ApiResult(true);
        apiResult.setData(updateCidsMonitorConfigurationDto);

        return ResponseEntity.ok(new ApiResult(true));
    }


    /**
     * Find Cids Monitor Monitoring Manager Data
     *
     * @param cidsMonitorConfigurationFindLocAndTyParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/find-temp-by-loc-and-type")
    public ResponseEntity<ApiResult> findTemplateCidsMonitorConfigurationByTypeAndLocation(@RequestBody @Validated CidsMonitorConfigurationFindLocAndTyParamDTO cidsMonitorConfigurationFindLocAndTyParamDTO) throws DataAccessException {
        SearchCidsMonitorConfigurationFindLocAndTyDTO searchCidsMonitorConfigurationFindLocAndTyDTO = new SearchCidsMonitorConfigurationFindLocAndTyDTO();

        searchCidsMonitorConfigurationFindLocAndTyDTO.setLocTySeqNo(cidsMonitorConfigurationFindLocAndTyParamDTO.getLocTySeqNo());
        searchCidsMonitorConfigurationFindLocAndTyDTO.setWkTySeqNo(cidsMonitorConfigurationFindLocAndTyParamDTO.getWkTySeqNo());

        ViewCidsMonitorConfigurationDTO dbInfo = this.cidsMonitorConfigurationService.findTemplateByTypeAndLocationData(searchCidsMonitorConfigurationFindLocAndTyDTO);

        var apiResult = new ApiResult(true);
        apiResult.setData(dbInfo.getTmplatSeqNo());

        return ResponseEntity.ok(apiResult);
    }

}
