package com.DigitalHeroes.Assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.DigitalHeroes.Assignment.dto.AuditRequest;
import com.DigitalHeroes.Assignment.dto.AuditResponse;
import com.DigitalHeroes.Assignment.service.AuditService;
@CrossOrigin(origins = {
	    "http://localhost:5173",
	    "https://sunny-lamington-795431.netlify.app"
	})
@RestController
@RequestMapping("/api")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/home")
    public String home() {
        return "Page Pulse Backend is Running";
    }

    @PostMapping("/audit")
    public AuditResponse audit(@RequestBody AuditRequest request) {

        return auditService.audit(request.getUrl());

    }
}