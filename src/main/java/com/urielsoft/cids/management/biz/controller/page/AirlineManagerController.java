package com.urielsoft.cids.management.biz.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for AirlineManager Module
 * @author trungNV ( trungnv.bks@gmail.com )
 * @since 2023-07/20
 */
@Controller
@RequestMapping("/airline-manager")
public class AirlineManagerController {

    /**
     * Airline management page
     *
     */
    @GetMapping
    public String airlineManagementPage () throws Exception {
        return "/airline-manager/airline-manager";
    }
}
