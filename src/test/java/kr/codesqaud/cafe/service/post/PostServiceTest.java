package kr.codesqaud.cafe.service.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;
import kr.codesqaud.cafe.dto.member.SignUpRequestDto;
import kr.codesqaud.cafe.dto.post.PostResponseDto;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.repository.post.PostRepository;
import kr.codesqaud.cafe.service.member.MemberService;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    MemberService memberService;

    @Test
    void save() {
        String memberId = memberService.signUp(basicMemberData());
        PostWriteRequest postWriteRequest = basicPostWriteData(memberId);
        UUID postId = UUID.fromString("1d5bf5d5-5a5c-4a57-9f05-49d2c9bcf357");

        //when
        UUID savedPostId = postService.save(postWriteRequest, postId);

        //then
        assertNotNull(savedPostId);
    }

    @Test
    void findById() {
        String memberId = memberService.signUp(basicMemberData());
        PostWriteRequest postWriteRequest = basicPostWriteData(memberId);
        UUID postId = UUID.fromString("1d5bf5d5-5a5c-4a57-9f05-49d2c9bcf357");
        UUID savedPostId = postService.save(postWriteRequest, postId);

        //when
        PostResponseDto result = postService.findById(savedPostId);

        //then
        assertNotNull(result);
        assertEquals(postWriteRequest.getTitle(), result.getTitle());
        assertEquals(postWriteRequest.getContent(), result.getContent());
        assertEquals(postWriteRequest.getWriterId(), result.getWriteId());
        assertNotNull(result.getWriteDate());
    }

//    @Test
//    void findAllPosts() {
//        //given
//        String memberId = memberService.signUp(basicMemberData());
//        UUID post1Id = UUID.fromString("1d5bf5d5-5a5c-4a57-9f05-49d2c9bcf357");
//        UUID post2Id = UUID.fromString("2d5bf5d5-5a5c-4a57-9f05-49d2c9bcf357");
//
//        List<PostWriteRequest> expectedPosts = Arrays.asList(
//                basicPostWriteData(memberId),
//                dummyPostWriteData(memberId)
//        );
//
//        postService.save(expectedPosts.get(0),post1Id);
//        postService.save(expectedPosts.get(1),post2Id);
//
//        //then
//        assertEquals(postService.findAllWriters(), hasSize(2));
//    }

//    @Test
//    void findAllWriters() {
//        SignUpRequestDto member1 = basicMemberData();
//        SignUpRequestDto member2 = dummyMemberData();
//
//        String member1Id = memberService.signUp(member1);
//        String member2Id = memberService.signUp(member2);
//
//        PostResponseDto post1 = basicPostData(member1Id);
//        PostResponseDto post2 = dummyPostData(member2Id);
//
//        postService.save(post1,UUID.randomUUID());
//        List<PostResponseDto> postResponseDtoList = Arrays.asList(post1,post2);
//
//        //when
//        List<PostResponseDto> resultList = postService.findAllWriters();
//
//        //then
//        assertThat(resultList).isNotNull();
//        assertThat(resultList).hasSize(postResponseDtoList.size());
//        assertThat(resultList).isEqualTo(postResponseDtoList);
//
//    }

//    @Test
//    void writerInfo() {
//    }


    private SignUpRequestDto basicMemberData() {
        String email = "test@test.com";
        String password = "testtest";
        String nickName = "chacha";
        LocalDateTime localDateTime = LocalDateTime.now();
        return new SignUpRequestDto(email, password, nickName, localDateTime);
    }

    private SignUpRequestDto dummyMemberData() {
        String email = "dummy@dummy.com";
        String password = "dummydummy";
        String nickName = "피오니";
        LocalDateTime localDateTime = LocalDateTime.now();
        return new SignUpRequestDto(email, password, nickName, localDateTime);
    }

    private PostResponseDto basicPostData(String memberId) {
        UUID postId = UUID.randomUUID();
        String title = "피에스타";
        String content = "내맘에 태양을 꾹 삼킨채 영원토록 뜨겁게 지지않을께";
        return new PostResponseDto(postId,title,content,memberId,LocalDateTime.now(),0L);
    }

    private PostResponseDto dummyPostData(String memberId) {
        UUID postId = UUID.randomUUID();
        String title = "파노라마";
        String content = "깊은 어둠속 빛나는 별처럼 우린 어디서든 서로 알아볼 수 있어";
        return new PostResponseDto(postId,title,content,memberId,LocalDateTime.now(),0L);
    }

    private PostWriteRequest basicPostWriteData(String memberId) {
        UUID postId = UUID.randomUUID();
        String title = "피에스타";
        String content = "내맘에 태양을 꾹 삼킨채 영원토록 뜨겁게 지지않을께";
        return new PostWriteRequest(title,content,memberId,LocalDateTime.now());
    }

    private PostWriteRequest dummyPostWriteData(String memberId) {
        UUID postId = UUID.randomUUID();
        String title = "파노라마";
        String content = "깊은 어둠속 빛나는 별처럼 우린 어디서든 서로 알아볼 수 있어";
        return new PostWriteRequest(title,content,memberId,LocalDateTime.now());
    }
}