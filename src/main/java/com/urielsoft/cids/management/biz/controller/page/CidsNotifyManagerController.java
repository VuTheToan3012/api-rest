package com.urielsoft.cids.management.biz.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller()
@RequestMapping("/notify-manager")
public class CidsNotifyManagerController {

    @GetMapping
    public String notifyManagerPage() throws Exception{
        return "/notify-manager";
    }
}
