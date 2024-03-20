package com.urielsoft.cids.management.biz.controller.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  Controller for the system management module.
 * @author trungNV (trungnv.bks@gmail.com)
 * @since 2023-07-18
 */
@Controller
@RequestMapping("system-manager")
public class SystemManagerController {

    /**
     * User management page
     * @return
     * @throws Exception
     */
    @GetMapping("/user-management")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String userManagement() throws Exception {
        return "/system-manager/user-management";
    }

    /**
     * Menu management page
     */
    @GetMapping("/menu-management")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String menuManagementPage() throws Exception {
        return "/system-manager/menu-management";
    }

    @GetMapping("/multilingual-management")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String multilingualManagementPage() throws Exception{
        return "/system-manager/multilingual-management";
    }

    @GetMapping("/environment-management")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String environmentManagementPage() throws Exception{
        return "/system-manager/environment-management";
    }
}
