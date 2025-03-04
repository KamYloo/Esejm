package pb.wi.mmw.e_sejm.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pb.wi.mmw.e_sejm.dto.PostDto;
import pb.wi.mmw.e_sejm.dto.request.PostRequestDto;
import pb.wi.mmw.e_sejm.service.PostService;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/categories")
    public ResponseEntity<Page<PostDto>> getPostsByCategories(
            @RequestParam List<String> categories,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostDto> posts = postService.getPostsByCategories(categories, page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/date-asc")
    public ResponseEntity<Page<PostDto>> getPostsByDateASC(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostDto> posts = postService.getPostsByDateASC(page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/date-desc")
    public ResponseEntity<Page<PostDto>> getPostsByDateDESC(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostDto> posts = postService.getPostsByDateDESC(page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/score")
    public ResponseEntity<Page<PostDto>> getPostsByScore(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostDto> posts = postService.getPostsByScore(page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<Page<PostDto>> getPostsByCommentCount(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostDto> posts = postService.getPostsByCommentCount(page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Page<PostDto>> getPostsByUser(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostDto> posts = postService.getPostsByUser(username, page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<PostDto> getPostByTitle(@PathVariable String title) {
        PostDto post = postService.getPostByTitle(title);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody PostRequestDto dto, Principal principal) {
        PostDto post = postService.createPost(dto, principal.getName());
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto dto, Principal principal) {
        PostDto updatedPost = postService.updatePost(id, dto, principal.getName());
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Principal principal) {
        postService.deletePost(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{postId}/upvote")
    public ResponseEntity<?> upvotePost(@PathVariable Long postId) {
        postService.upvotePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{postId}/downvote")
    public ResponseEntity<?> downvotePost(@PathVariable Long postId) {
        postService.downvotePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
