package pb.wi.mmw.e_sejm.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pb.wi.mmw.e_sejm.dto.CommentDto;
import pb.wi.mmw.e_sejm.dto.request.CommentRequest;
import pb.wi.mmw.e_sejm.dto.request.CommentUpdateRequest;
import pb.wi.mmw.e_sejm.entity.CommentEntity;
import pb.wi.mmw.e_sejm.entity.PostEntity;
import pb.wi.mmw.e_sejm.entity.UserEntity;
import pb.wi.mmw.e_sejm.handler.BusinessErrorCodes;
import pb.wi.mmw.e_sejm.handler.CustomException;
import pb.wi.mmw.e_sejm.mappers.CommentMapper;
import pb.wi.mmw.e_sejm.repository.CommentRepository;
import pb.wi.mmw.e_sejm.repository.PostRepository;
import pb.wi.mmw.e_sejm.repository.RolesRepository;
import pb.wi.mmw.e_sejm.repository.UserRepository;
import pb.wi.mmw.e_sejm.service.CommentService;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RolesRepository rolesRepository;
    private final CommentMapper mapper;

    @Transactional
    @Override
    public CommentDto createComment(CommentRequest req, String username) {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        PostEntity post = postRepository.findById(req.getPostId()).orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_POST_ID));
        CommentEntity parentComment = null;
        if (req.getParentCommentId() != null) {
            parentComment = commentRepository.findById(req.getParentCommentId())
                    .orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_COMMENT_ID));
        }
        CommentEntity commentEntity = new CommentEntity().builder()
                .content(req.getContent())
                .author(user)
                .post(post)
                .parent(parentComment)
                .build();
        CommentEntity saveComment = commentRepository.save(commentEntity);

        CommentEntity currentParent = parentComment;
        while (currentParent != null) {
            currentParent.setCommentCount(currentParent.getCommentCount() + 1);
            commentRepository.save(currentParent);
            currentParent = currentParent.getParent();
        }

        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);
        return mapper.mapTo(saveComment);
    }

    @Override
    public CommentDto updateComment(Long id, CommentUpdateRequest req, String username) {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_COMMENT_ID));
        if(isAuthenticated(comment, username)){
            comment.setContent(req.getContent());
            commentRepository.save(comment);
            return mapper.mapTo(comment);
        }else{
            throw new CustomException(BusinessErrorCodes.INCORRECT_PERMISSIONS);
        }

    }

    @Transactional
    @Override
    public void deleteComment(Long id, String username) {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_COMMENT_ID));
        if(isAuthenticated(comment, username)){
            CommentEntity parentComment = comment.getParent();
            PostEntity post = comment.getPost();
            int nestedComments = comment.getCommentCount() + 1;
            if(parentComment != null){
                parentComment.setCommentCount( parentComment.getCommentCount() - nestedComments );
                commentRepository.save(parentComment);
            }
            post.setCommentsCount( post.getCommentsCount() - nestedComments );
            postRepository.save(post);
            commentRepository.deleteById(id);
        }else{
            throw new CustomException(BusinessErrorCodes.INCORRECT_PERMISSIONS);
        }
    }

    public List<CommentDto> getRepliesToComment(Long parentCommentId) {
        return commentRepository.findByParentId(parentCommentId).stream().map(mapper::mapTo).toList();
    }

    @Override
    public List<CommentDto> getByAuthor(String author) {
        UserEntity authorEntity = userRepository.findByNickName(author).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return commentRepository.findByAuthor(authorEntity).stream().map(mapper::mapTo).toList();
    }

    @Override
    public List<CommentDto> getByPost(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_POST_ID));
        return commentRepository.findByPost(postEntity).stream().filter(c -> c.getParent() == null).map(mapper::mapTo).toList();
    }

    @Override
    public void upvotePost(Long id) {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_COMMENT_ID));
        comment.setScore( comment.getScore() + 1 );
        commentRepository.save(comment);
    }

    @Override
    public void downvotePost(Long id) {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new CustomException(BusinessErrorCodes.NO_COMMENT_ID));
        comment.setScore( comment.getScore() - 1 );
        commentRepository.save(comment);
    }

    private boolean isAuthenticated(CommentEntity comment, String username) {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if( comment.getAuthor().equals(userEntity) || userEntity.getRoles().contains(rolesRepository.findByName("MODERATOR").orElseThrow(
                () -> new CustomException(BusinessErrorCodes.NO_ROLE))) ) {
            return true;
        }
        return false;
    }
}
