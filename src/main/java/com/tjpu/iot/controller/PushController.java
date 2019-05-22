package com.tjpu.iot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class PushController {

    @RequestMapping(value = "autoPush/{companyId}/{appId}", method = RequestMethod.POST, consumes = "application/json")
    public void autoPush(HttpServletRequest request, @PathVariable("companyId") String companyId, @PathVariable("appId") String appId) {
        log.info("收到推送");
    }
}
