package com.community.domain.search.application;

import com.community.domain.search.model.PostDocument;
import com.community.domain.search.repository.PostDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final PostDocumentRepository postDocumentRepository;

    public Page<PostDocument> searchPost(String keyword) {
      return postDocumentRepository.findByContent(keyword);
    }
}
