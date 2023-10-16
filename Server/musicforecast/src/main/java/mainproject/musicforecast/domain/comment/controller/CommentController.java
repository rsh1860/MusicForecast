package mainproject.musicforecast.domain.comment.controller;

import mainproject.musicforecast.domain.comment.dto.*;
import mainproject.musicforecast.domain.comment.entity.Comment;
import mainproject.musicforecast.domain.comment.mapper.CommentMapper;
import mainproject.musicforecast.domain.comment.service.CommentService;
import mainproject.musicforecast.domain.member.entity.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
@Validated
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentPostDto commentPostDto,
                                     @AuthenticationPrincipal Member user) {
        Comment comment = mapper.commentPostDtoToComment(commentPostDto);

        Comment response = commentService.createComment(comment, user);

        return new ResponseEntity<>(mapper.CommentToCommentResponseDto(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity patchComment(@PathVariable("commentId") @Positive long commentId,
                                      @Valid @RequestBody CommentPatchDto commentPatchDto,
                                      @AuthenticationPrincipal Member user) {
        commentPatchDto.setCommentId(commentId);
        Comment comment = commentService.updateComment(mapper.commentPatchDtoToComment(commentPatchDto), user);

        return new ResponseEntity<>(mapper.CommentToCommentResponseDto(comment), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity getComment(@PathVariable("commentId") @Positive long commentId) {
        Comment response = commentService.findComment(commentId);

        return new ResponseEntity<>(mapper.CommentToCommentResponseDto(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getComments() {
        List<Comment> comments = commentService.findComments();

        List<CommentResponseDto> response =
                comments.stream()
                        .map(comment -> mapper.CommentToCommentResponseDto(comment))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") @Positive long commentId,
                                       @AuthenticationPrincipal Member user) {

        commentService.deleteComment(commentId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
