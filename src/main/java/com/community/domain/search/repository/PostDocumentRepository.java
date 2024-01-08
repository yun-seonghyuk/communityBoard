package com.community.domain.search.repository;

import com.community.domain.search.model.PostDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface PostDocumentRepository extends ElasticsearchRepository<PostDocument, Long> {
    Page<PostDocument> findByContent(String content);
}
