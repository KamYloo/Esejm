package pb.wi.mmw.e_sejm.service;

import pb.wi.mmw.e_sejm.dto.CommentDto;
import pb.wi.mmw.e_sejm.dto.request.CommentRequest;
import pb.wi.mmw.e_sejm.dto.request.CommentUpdateRequest;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentRequest comment, String username);
    CommentDto updateComment(Long id, CommentUpdateRequest comment, String username);
    List<CommentDto> getRepliesToComment(Long parentCommentId);
    List<CommentDto> getByAuthor(String author);
    List<CommentDto> getByPost(Long id);
    void deleteComment(Long id, String username);
    void upvotePost(Long id);
    void downvotePost(Long id);
}
