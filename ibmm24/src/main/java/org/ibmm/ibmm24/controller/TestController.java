package org.ibmm.ibmm24.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String testEndpoint() throws InterruptedException {
        Thread.sleep(2000); // Simulate a blocking operation
        boolean isVirtual = Thread.currentThread().isVirtual();
        return "Handled by thread: " + Thread.currentThread() + ", isVirtual: " + isVirtual;
    }
}
