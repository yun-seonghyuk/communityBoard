package com.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE,
//        classes = PostDocumentRepository.class))
@SpringBootApplication
@EnableScheduling
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
