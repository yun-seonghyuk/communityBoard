package com.community.domain.search.repository;

import com.community.domain.search.model.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDocumentRepository extends ElasticsearchRepository<PostDocument, Long> {


}
