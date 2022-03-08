package com.nordcodes.testassignment.linkshortener.controller;

import com.nordcodes.testassignment.linkshortener.entity.LinkRegistrationRequest;
import com.nordcodes.testassignment.linkshortener.entity.Stats;
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
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("register")
    public String registerLink(@RequestBody LinkRegistrationRequest linkRegistrationRequest) {
        return linksService.registerLink(linkRegistrationRequest);
    }

    @GetMapping("redirect/{link}")
    public ResponseEntity<Void> redirect(@PathVariable String link, @RequestParam(name = "id") long userId) {
        final String fullLink = linksService.loadFullLink(link, userId);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(fullLink)).build();
    }

    @DeleteMapping("delete/{link}")
    public void deleteLink(@PathVariable String link) {
        linksService.deleteLink(link);
    }

    @GetMapping("stats/all")
    public List<Stats> loadStatsTopN(@RequestBody(required = false) Integer count) {
        return linksService.loadStatsAll(count);
    }

    @GetMapping("stats/unique")
    public List<Stats> loadStatsUniqueTopN(@RequestBody(required = false) Integer count) {
        return linksService.loadStatsUnique(count);
    }
}
