package com.community.domain.search.application;

import com.community.domain.search.model.PostDocument;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;


//    public Page<PostDocument> searchAllPosts(int page, int size) {
//        SearchHits<PostDocument> searchHits = elasticsearchOperations.search(
//                QueryBuilders.matchAllQuery(),
//                PostDocument.class,
//                PageRequest.of(page, size)
//        );
//
//        return searchHits.getPage();
//    }

    public List<PostDocument> searchAllPosts() {
        SearchHits<PostDocument> searchHits = elasticsearchOperations.search(
                (Query) QueryBuilders.matchAllQuery(),
                PostDocument.class);

        return searchHits.get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<PostDocument> searchAllPost2s() {
        SearchHits<PostDocument> searchHits = elasticsearchOperations.search(
                (Query) QueryBuilders.matchAllQuery(),
                PostDocument.class);

        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
