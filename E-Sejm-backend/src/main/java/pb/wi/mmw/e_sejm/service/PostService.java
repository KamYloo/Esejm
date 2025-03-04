package pb.wi.mmw.e_sejm.service;

import org.springframework.data.domain.Page;
import pb.wi.mmw.e_sejm.dto.request.PostRequestDto;
import pb.wi.mmw.e_sejm.dto.PostDto;

import java.util.List;

public interface PostService {
    Page<PostDto> getPostsByCategories(List<String> categories, int page, int size);
    Page<PostDto> getPostsByDateASC(int page, int size);
    Page<PostDto> getPostsByDateDESC(int page, int size);
    Page<PostDto> getPostsByScore(int page, int size);
    Page<PostDto> getPostsByCommentCount(int page, int size);
    Page<PostDto> getPostsByUser(String username, int page, int size);
    PostDto getPostById(Long id);
    PostDto getPostByTitle(String title);
    PostDto createPost(PostRequestDto postRequestDto, String username);
    PostDto updatePost(Long id, PostRequestDto postRequestDto, String username);
    void deletePost(Long id, String username);
    void upvotePost(Long id);
    void downvotePost(Long id);
}
