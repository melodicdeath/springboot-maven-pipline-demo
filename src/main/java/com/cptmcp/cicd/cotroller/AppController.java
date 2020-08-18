package com.cptmcp.cicd.cotroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppController {
    /**
     * 当前版本
     */
    @Value("${app.version}")
    private String version;
    /**
     * 打包时间
     */
    @Value("${app.build.time}")
    private String buildTime;

    @GetMapping
    public String getVersion() {
        return String.format("%s %s",version,buildTime);
    }
}
