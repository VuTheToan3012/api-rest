package com.urielsoft.cids.management.biz.controller;

import com.urielsoft.cids.management.biz.dto.ViewMenuManagementDTO;
import com.urielsoft.cids.management.biz.dto.ViewMultilingualManagementDTO;
import com.urielsoft.cids.management.biz.service.MenuManagementService;
import com.urielsoft.cids.management.biz.service.MultilingualManagementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * Global Controller Advice
 *
 * @author TrungNv (Trungnv_bks@gmail.com)
 * @since 2023-08-23
 */

@ControllerAdvice
public class GlobalControllerAdvice {

    @Value("${websocket.url}")
    private String websocketUrl;

    public GlobalControllerAdvice(MenuManagementService menuManagementService,MultilingualManagementService multilingualManagementService) {
    }

    /**
     * Retrieves and adds configuration variable to the model.
     *
     * @param model the model object to add the variable value to
     * @throws Exception if there is an error retrieving the variable value
     */
    @ModelAttribute("ConfiguationValue")
    public void passConfigVariable(Model model) throws Exception {
        model.addAttribute("websocketUrl", websocketUrl);
    }
}
