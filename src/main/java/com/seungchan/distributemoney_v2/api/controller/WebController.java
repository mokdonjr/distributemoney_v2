package com.seungchan.distributemoney_v2.api.controller;

import com.seungchan.distributemoney_v2.common.BaseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(hidden = true)
public class WebController extends BaseBean {

    @GetMapping("/apis")
    @ApiOperation(value = "API 문서", hidden = true)
    public String home() {
        return "redirect:/swagger-ui.html";
    }
}
