package com.nordcodes.testassignment.linkshortener.controller;

import com.nordcodes.testassignment.linkshortener.entity.Link;
import com.nordcodes.testassignment.linkshortener.entity.LinkRegisterRequest;
import com.nordcodes.testassignment.linkshortener.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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

    @GetMapping("redirect/{link}")
    public ResponseEntity<Void> redirect(@PathVariable String link) {
        final String fullLink = linksService.loadFullLink(link);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(fullLink)).build();
    }

    @DeleteMapping("delete/{link}")
    public void deleteLink(@PathVariable String link) {
        linksService.deleteLink(link);
    }
}
