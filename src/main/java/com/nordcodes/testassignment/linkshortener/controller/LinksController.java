package com.nordcodes.testassignment.linkshortener.controller;

import com.nordcodes.testassignment.linkshortener.entity.Link;
import com.nordcodes.testassignment.linkshortener.entity.LinkRegisterRequest;
import com.nordcodes.testassignment.linkshortener.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinksController {

    private final LinksService linksService;

    @Autowired
    public LinksController(LinksService linksService) {
        this.linksService = linksService;
    }

    @GetMapping("all")
    public List<Link> loadAll() {
        return linksService.loadAll();
    }

    @PostMapping("register")
    public String registerLink(@RequestBody LinkRegisterRequest linkRegisterRequest) {
        return linksService.registerLink(linkRegisterRequest);
    }
}
