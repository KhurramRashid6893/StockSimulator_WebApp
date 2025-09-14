// In src/main/java/com/stock/simulator/controller/ViewController.java

package com.stock.simulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login.html";
    }

    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "dashboard.html";
    }
}