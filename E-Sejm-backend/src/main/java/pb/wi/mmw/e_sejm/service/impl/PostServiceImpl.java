package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pb.wi.mmw.e_sejm.dto.request.PostRequestDto;
import pb.wi.mmw.e_sejm.dto.PostDto;
import pb.wi.mmw.e_sejm.entity.CategoryEntity;
import pb.wi.mmw.e_sejm.entity.PostEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.Mapper;
import pb.wi.mmw.e_sejm.repository.CategoryRepository;
import pb.wi.mmw.e_sejm.repository.PostRepository;
import pb.wi.mmw.e_sejm.repository.RolesRepository;
import pb.wi.mmw.e_sejm.repository.UserRepository;
import pb.wi.mmw.e_sejm.service.PostService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final CategoryRepository categoryRepository;
    private final Mapper<PostEntity, PostDto> mapper;

    @Override
    public Page<PostDto> getPostsByCategories(List<String> categories, int page, int size) {
        Page<PostEntity> postEntity = postRepository.findByCategories(categories, PageRequest.of(page, size, Sort.by("title")));
        return postEntity.map(mapper::mapTo);
    }

    @Override
    public Page<PostDto> getPostsByDateASC(int page, int size) {
        Page<PostEntity> postEntity = postRepository.findPostsByDateASC(PageRequest.of(page, size, Sort.by("title")));
        return postEntity.map(mapper::mapTo);
    }

    @Override
    public Page<PostDto> getPostsByDateDESC(int page, int size) {
        Page<PostEntity> postEntity = postRepository.findPostsByDateDESC(PageRequest.of(page, size, Sort.by("title")));
        return postEntity.map(mapper::mapTo);
    }

    @Override
    public Page<PostDto> getPostsByScore(int page, int size) {
        Page<PostEntity> postEntity = postRepository.findPostsByScore(PageRequest.of(page, size, Sort.by("title")));
        return postEntity.map(mapper::mapTo);
    }

    @Override
    public Page<PostDto> getPostsByCommentCount(int page, int size) {
        Page<PostEntity> postEntity = postRepository.findPostsByCommentCount(PageRequest.of(page, size, Sort.by("title")));
        return postEntity.map(mapper::mapTo);
    }

    @Override
    public Page<PostDto> getPostsByUser(String username, int page, int size) {
        UserEntity userEntity = userRepository.findByNickName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Page<PostEntity> postEntity = postRepository.findByAuthor(userEntity, PageRequest.of(page, size, Sort.by("title")));
        return postEntity.map(mapper::mapTo);
    }

    @Override
    public PostDto getPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_POST_ID)
        );
        return mapper.mapTo(postEntity);
    }

    @Override
    public PostDto getPostByTitle(String title) {
        PostEntity postEntity = postRepository.findByTitle(title).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_POST_TITLE)
        );
        return mapper.mapTo(postEntity);
    }

    @Override
    public PostDto createPost(PostRequestDto dto, String username) {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PostEntity post = PostEntity.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(userEntity)
                .categories(fetchCategories(dto.getCategories()))
                .build();
        PostEntity postSaved = postRepository.save(post);
        return mapper.mapTo(postSaved);
    }

    @Override
    public PostDto updatePost(Long id, PostRequestDto dto, String username) {
            PostEntity postEntity = postRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_POST_ID)
        );
        if(isAuthenticated(postEntity, username)){
            postEntity.setTitle(dto.getTitle());
            postEntity.setContent(dto.getContent());
            postEntity.setCategories(fetchCategories(dto.getCategories()));
            PostEntity updatedPost = postRepository.save(postEntity);
            return mapper.mapTo(updatedPost);
        }else{
            throw new CustomException(BusinessErrorCodes.INCORRECT_PERMISSIONS);
        }
    }

    @Override
    public void deletePost(Long id,String username) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_POST_TITLE)
        );
        if(isAuthenticated(postEntity, username)) {
            postRepository.delete(postEntity);
        }else{
            throw new CustomException(BusinessErrorCodes.INCORRECT_PERMISSIONS);
        }
    }

    @Transactional
    @Override
    public void upvotePost(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_POST_ID));
        post.setScore(post.getScore() + 1);
        postRepository.save(post);
    }

    @Transactional
    @Override
    public void downvotePost(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_POST_ID));
        post.setScore(post.getScore() - 1);
        postRepository.save(post);
    }

    private List<CategoryEntity> fetchCategories(List<String> categories){
        return categories.stream()
                .map(c -> categoryRepository.findByName(c).orElseThrow(
                        () -> new CustomException(BusinessErrorCodes.NO_CATEGORY))
                ).collect(Collectors.toList());
    }

    private boolean isAuthenticated(PostEntity post, String username) {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if( post.getAuthor().equals(userEntity) || userEntity.getRoles().contains(rolesRepository.findByName("MODERATOR").orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_ROLE))) ) {
            return true;
        }
        return false;
    }
}
