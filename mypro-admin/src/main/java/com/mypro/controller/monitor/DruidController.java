package com.mypro.controller.monitor;

import com.mypro.common.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * druid监控页面控制器
 * @author houhaotong
 */
@Controller
public class DruidController extends BaseController {

    @GetMapping("/monitor/data")
    public String index(){
        return redirect("/druid/index");
    }
}
