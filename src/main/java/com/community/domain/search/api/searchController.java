package com.community.domain.search.api;

import com.community.domain.search.application.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class searchController {

    private final SearchService searchService;

    @GetMapping("/posts/search")
    public ResponseEntity<?> searchPost(
            @RequestParam(value = "keyword") String keyword) {
        return ResponseEntity.ok().body(searchService.searchPost(keyword));
    }
}
