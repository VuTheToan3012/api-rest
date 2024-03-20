package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.common.utils.DateTimeUtils;
import com.urielsoft.cids.management.biz.dto.CreateCidsNotifyManagerDTO;
import com.urielsoft.cids.management.biz.dto.SearchAndPaginationCidsNotifyManagerResultsDTO;
import com.urielsoft.cids.management.biz.dto.UpdateCidsNotifyManagerDTO;
import com.urielsoft.cids.management.biz.dto.ViewCidsNotifyManagerDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CreateCidsNotifyManagerParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.UpdateCidsNotifyManagerParamDTO;
import com.urielsoft.cids.management.biz.dto.api.result.CidsNotifyManagerDetailResultDTO;
import com.urielsoft.cids.management.biz.dto.api.result.CidsNotifyManagerListItemResultDTO;
import com.urielsoft.cids.management.biz.dto.search.DataTableRequest;
import com.urielsoft.cids.management.biz.service.CidsNotifyManagerService;
import com.urielsoft.cids.management.biz.service.UserManagementService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.api.ApiResultPagination;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Cids Notify Manager Controller
 *
 * @author Thangdt_bks (tdaotrong77@gmail.com)
 * @version 1.0
 * @since 2023-07-24
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/cids-notify-manager")
public class CidsNotifyManagerApiController {
    /**
     * Cids Notify Manager Service
     */
    private final CidsNotifyManagerService cidsNotifyManagerService;

    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;

    /**
     * Get Notify Manager By cidsNotifyManagerSeq
     *
     * @param cidsNotifyManagerSeq
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/detail/{cidsNotifyManagerSeq}")
    public ResponseEntity<ApiResult> detailCidsNotifyManagerData(@PathVariable("cidsNotifyManagerSeq") int cidsNotifyManagerSeq) throws Exception {
        // Get User Information By id
        ViewCidsNotifyManagerDTO dbInfo = this.cidsNotifyManagerService.getCidsNotifyManagerDataBySeq(cidsNotifyManagerSeq);
        //  Use dbInfo to set Data for resultInfo
        var resultInfo = new CidsNotifyManagerDetailResultDTO();
        resultInfo.setSeqNo(dbInfo.getSeqNo());
        resultInfo.setNotiBen(dbInfo.getNotiBen());
        resultInfo.setNotiEng(dbInfo.getNotiEng());
        resultInfo.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
        resultInfo.setMdfyDt(dbInfo.getMdfyDt());
        resultInfo.setMsgFwdYn(dbInfo.getMsgFwdYn());
        resultInfo.setMdfyUserSeqNO(dbInfo.getMdfyUserSeqNO());
        resultInfo.setWkTyNm(dbInfo.getWkTyNm());
        resultInfo.setWkTySeqNo(dbInfo.getWkTySeqNo());
        resultInfo.setUserNm(dbInfo.getUserNm());
        resultInfo.setLocTySeqNo(dbInfo.getLocTySeqNo());
        resultInfo.setLocNm(dbInfo.getLocNm());

        var apiResult = new ApiResult(true);
        apiResult.setData(resultInfo);

        return ResponseEntity.ok(apiResult);
    }

    /**
     * Create Cids Notify Manager Data
     *
     * @param createCidsMonitorManagerParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping
    public Object createCidsNotifyManagerData(@RequestBody @Validated CreateCidsNotifyManagerParamDTO createCidsMonitorManagerParamDTO) throws Exception {
        // Set createUserParamDTO information to createUserDTO
        var createCidsNotifyManagerDTO = new CreateCidsNotifyManagerDTO();

        createCidsNotifyManagerDTO.setWkTySeqNo(createCidsMonitorManagerParamDTO.getWkTySeqNo());
        createCidsNotifyManagerDTO.setNotiBen(createCidsMonitorManagerParamDTO.getNotiBen());
        createCidsNotifyManagerDTO.setNotiEng(createCidsMonitorManagerParamDTO.getNotiEng());
        createCidsNotifyManagerDTO.setLocTySeqNo(createCidsMonitorManagerParamDTO.getLocTySeqNo());
        //setMsgFwdYn : TRUE : 1(USE)
        createCidsNotifyManagerDTO.setMsgFwdYn(0);
        // Get Loggin User Info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        createCidsNotifyManagerDTO.setMdfyUserSeqNO(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.cidsNotifyManagerService.createCidsNotifyManagerData(createCidsNotifyManagerDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Update Notify Manager Information
     *
     * @param updateCidsNotifyManagerParamDTO
     * @return
     * @throws Exception
     */
    @PutMapping
    public ResponseEntity<ApiResult> modifyCidsNotifyManagerData(@RequestBody @Validated UpdateCidsNotifyManagerParamDTO updateCidsNotifyManagerParamDTO) throws Exception {

        var updateCidsNotifyManagerDTO = new UpdateCidsNotifyManagerDTO();

        updateCidsNotifyManagerDTO.setSeqNo(updateCidsNotifyManagerParamDTO.getSeqNo());
        updateCidsNotifyManagerDTO.setWkTySeqNo(updateCidsNotifyManagerParamDTO.getWkTySeqNo());
        updateCidsNotifyManagerDTO.setNotiBen(updateCidsNotifyManagerParamDTO.getNotiBen());
        updateCidsNotifyManagerDTO.setNotiEng(updateCidsNotifyManagerParamDTO.getNotiEng());
        updateCidsNotifyManagerDTO.setLocTySeqNo(updateCidsNotifyManagerParamDTO.getLocTySeqNo());
        //setMsgFwdYn : TRUE : 1(USE), FALSE : 0(NOT USE)
        updateCidsNotifyManagerDTO.setMsgFwdYn(updateCidsNotifyManagerParamDTO.getMsgFwdYn());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        updateCidsNotifyManagerDTO.setMdfyUserSeqNO(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.cidsNotifyManagerService.modifyCidsNotifyManagerData(updateCidsNotifyManagerDTO);

        var apiResult = new ApiResult(true);
        apiResult.setData(updateCidsNotifyManagerDTO);

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * List Pagination Cids Notify Manager Data
     *
     * @param dataTableRequest
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/list-pagination-search")
    public ResponseEntity<ApiResultPagination> listPaginationCidsNotifyManagerData(@RequestBody DataTableRequest dataTableRequest) throws Exception {

        // Call the service method to retrieve paginated and filtered data

        SearchAndPaginationCidsNotifyManagerResultsDTO filteredAndSearchData = this.cidsNotifyManagerService.getCidsNotifyManagerDataListPaginationAndSearch(dataTableRequest);

        List<ViewCidsNotifyManagerDTO> filteredData = filteredAndSearchData.getViewCidsNotifyManagerDTOS();

        // Convert the data to DTO format as required by the API response
        List<CidsNotifyManagerListItemResultDTO> resultList = filteredData.stream().map(dbInfo -> {

            var cidsNotifyManagerListItemResultDTO = new CidsNotifyManagerListItemResultDTO();
            cidsNotifyManagerListItemResultDTO.setSeqNo(dbInfo.getSeqNo());
            cidsNotifyManagerListItemResultDTO.setNotiBen(dbInfo.getNotiBen());
            cidsNotifyManagerListItemResultDTO.setNotiEng(dbInfo.getNotiEng());
            cidsNotifyManagerListItemResultDTO.setMdfyUserSeqNO(dbInfo.getMdfyUserSeqNO());
            cidsNotifyManagerListItemResultDTO.setMsgFwdYn(dbInfo.getMsgFwdYn());
            cidsNotifyManagerListItemResultDTO.setMdfyDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
            cidsNotifyManagerListItemResultDTO.setRegDt(DateTimeUtils.convertDateTimeFormatFromOriginFormat(dbInfo.getRegDt()));
            cidsNotifyManagerListItemResultDTO.setWkTyNm(dbInfo.getWkTyNm());
            cidsNotifyManagerListItemResultDTO.setWkTySeqNo(dbInfo.getWkTySeqNo());
            cidsNotifyManagerListItemResultDTO.setUserNm(dbInfo.getUserNm());
            cidsNotifyManagerListItemResultDTO.setLocTySeqNo(dbInfo.getLocTySeqNo());
            cidsNotifyManagerListItemResultDTO.setLocNm(dbInfo.getLocNm());

            return cidsNotifyManagerListItemResultDTO;
        }).collect(Collectors.toList());

        // Create the response object
        var apiResultPagination = new ApiResultPagination();

        apiResultPagination.setSuccess(true);
        apiResultPagination.setData(resultList);
        apiResultPagination.setDraw(dataTableRequest.getDraw());
        apiResultPagination.setRecordsTotal(cidsNotifyManagerService.listAllCidsNotifyManagerData().get(0).getCountFilterData());
        apiResultPagination.setRecordsFiltered(filteredAndSearchData.getCountFilterData());

        return ResponseEntity.ok(apiResultPagination);
    }
}
