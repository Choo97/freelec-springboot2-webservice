package com.jojoldu.book.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() throws Exception {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본분";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0, 0, 0);
        String title = "title";
        String content = "content";
        String author = "author";
        Posts savePosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author).build());
        //when
        Posts findPosts = postsRepository.findAll().get(0);


        //then
        System.out.println("=== createDate = " + findPosts.getCreatedDate() +
                ", modifiedDate = " + findPosts.getModifiedDate());

        assertThat(findPosts.getCreatedDate()).isAfter(now);
        assertThat(findPosts.getModifiedDate()).isAfter(now);
    }
}