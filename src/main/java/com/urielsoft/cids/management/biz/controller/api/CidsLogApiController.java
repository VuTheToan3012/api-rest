package com.urielsoft.cids.management.biz.controller.api;

import com.urielsoft.cids.management.biz.dto.CreateOperationLogDTO;
import com.urielsoft.cids.management.biz.dto.CreateProtocolLogDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CreateOperationLogParamDTO;
import com.urielsoft.cids.management.biz.dto.api.param.CreateProtocolLogParamDTO;
import com.urielsoft.cids.management.biz.service.UsageHistoryService;
import com.urielsoft.cids.management.biz.service.UserManagementService;
import com.urielsoft.cids.management.common.api.ApiResult;
import com.urielsoft.cids.management.common.security.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Cids Log Api Controller
 *
 * @author Chinhhd_bks (chinhhoangdinh05@gmail.com)
 * @version 1.0
 * @since 2023-10-17
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/cids-log")
public class CidsLogApiController {
    /**
     * Usage History Service
     */
    private final UsageHistoryService usageHistoryService;

    /**
     * User Management Service
     */
    private final UserManagementService userManagementService;

    /**
     * Register Operation Log
     * @param createOperationLogParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping("register-operation-log")
    public ResponseEntity<ApiResult> registerOperationLog(CreateOperationLogParamDTO createOperationLogParamDTO )throws Exception{
        CreateOperationLogDTO createOperationLogDTO =new CreateOperationLogDTO();

        createOperationLogDTO.setCidsLogLevelEnum(createOperationLogParamDTO.getCidsLogLevelEnum());
        createOperationLogDTO.setCidsLogMethod(createOperationLogParamDTO.getCidsLogMethod());
        createOperationLogDTO.setManipulation(createOperationLogParamDTO.getManipulation());
        createOperationLogDTO.setLogId(createOperationLogParamDTO.getLogId());

        this.usageHistoryService.createOperationLog(createOperationLogDTO.getLogId(), createOperationLogDTO.getCidsLogLevelEnum(), createOperationLogDTO.getCidsLogMethod(), createOperationLogDTO.getManipulation());

        return ResponseEntity.ok(new ApiResult(true));
    }

    /**
     * Register Protocol Log
     * @param createProtocolLogParamDTO
     * @return
     * @throws Exception
     */
    @PostMapping("register-protocol-log")
    public ResponseEntity<ApiResult> registerProtocolLog(CreateProtocolLogParamDTO createProtocolLogParamDTO ) throws Exception {
        // Get Loggin User Info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDetails loginUserDetails = (LoginUserDetails) authentication.getPrincipal();

        CreateProtocolLogDTO createProtocolLogDTO =new CreateProtocolLogDTO();

        createProtocolLogDTO.setProtocolName(createProtocolLogParamDTO.getProtocolName());
        createProtocolLogDTO.setResult(createProtocolLogParamDTO.getResult());
        createProtocolLogDTO.setResponseData(createProtocolLogParamDTO.getResponseData());
        createProtocolLogDTO.setRequestData(createProtocolLogParamDTO.getRequestData());
        createProtocolLogDTO.setLogId(createProtocolLogParamDTO.getLogId());
        createProtocolLogDTO.setOccrrncUserSeqNo(userManagementService.getUserManagementDataById(loginUserDetails.getUserId()).getSeqNo());

        this.usageHistoryService.createProtocolLog(createProtocolLogDTO.getLogId(), createProtocolLogDTO.getProtocolName(), createProtocolLogDTO.getResult(), createProtocolLogDTO.getRequestData(), createProtocolLogDTO.getResponseData(), createProtocolLogDTO.getOccrrncUserSeqNo());

        return ResponseEntity.ok(new ApiResult(true));
    }
}
