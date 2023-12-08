package com.salty.canvas.controller;

import com.salty.canvas.model.MonitorInfo;
import com.salty.canvas.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangcong
 * @description
 * @since 2023/12/07 16:02
 */
@RestController
public class MonitorController {
    @Autowired
    MonitorService monitorService;

    @GetMapping("monitor")
    public ResponseEntity<String> getMonitorInfo() {
        return ResponseEntity.ok(monitorService.getMonitorInfo().toString());
    }
}
