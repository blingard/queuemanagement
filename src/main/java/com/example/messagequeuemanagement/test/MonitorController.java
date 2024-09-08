package com.example.messagequeuemanagement.test;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MonitorController {

    private final  MonitorService monitorService;

    @GetMapping("/monitors")
    public String[] listMonitors() {
        return monitorService.getConnectedMonitors();
    }
}
