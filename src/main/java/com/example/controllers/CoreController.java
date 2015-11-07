package com.example.controllers;

import com.example.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreController {

    @RequestMapping("/status")
    public int getServerStatus() {
        return Api.STATUS_OK;
    }
}
