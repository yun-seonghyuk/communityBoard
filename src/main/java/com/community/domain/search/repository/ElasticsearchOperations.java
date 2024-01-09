package com.community.domain.search.repository;

import com.community.domain.search.model.PostDocument;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.DocumentOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchOperations;

public interface ElasticsearchOperations extends DocumentOperations, SearchOperations {
    SearchHits<PostDocument> search(MatchAllQueryBuilder matchAllQueryBuilder, Class<PostDocument> postDocumentClass, PageRequest of);
}
