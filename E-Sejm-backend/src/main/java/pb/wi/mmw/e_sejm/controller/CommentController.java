package pb.wi.mmw.e_sejm.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.CommentDto;
import pb.wi.mmw.e_sejm.dto.request.CommentRequest;
import pb.wi.mmw.e_sejm.dto.request.CommentUpdateRequest;
import pb.wi.mmw.e_sejm.service.CommentService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDto> create(@RequestBody CommentRequest commentRequest, Principal principal) {
        return new ResponseEntity<>(commentService.createComment(commentRequest, principal.getName()), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId , @RequestBody CommentUpdateRequest commentRequest, Principal principal) {
        return new ResponseEntity<>(commentService.updateComment(commentId, commentRequest, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        commentService.deleteComment(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<List<CommentDto>> getReplies(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getRepliesToComment(id), HttpStatus.OK);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentDto>> getByAuthor(@PathVariable String username) {
        return new ResponseEntity<>(commentService.getByAuthor(username), HttpStatus.OK);
    }

    @GetMapping("/by-post/{id}")
    public ResponseEntity<List<CommentDto>> getByPost(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getByPost(id), HttpStatus.OK);
    }

    @PostMapping("/{commentId}/upvote")
    public ResponseEntity<?> upvotePost(@PathVariable Long commentId) {
        commentService.upvotePost(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{commentId}/downvote")
    public ResponseEntity<?> downvotePost(@PathVariable Long commentId) {
        commentService.downvotePost(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
