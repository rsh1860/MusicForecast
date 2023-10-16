package mainproject.musicforecast.domain.comment.dto;

import lombok.AllArgsConstructor;
import mainproject.musicforecast.domain.post.entity.Post;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
public class CommentPostDto {
    @NotBlank(message = "글을 작성해주세요.")
    private String text;
    private Long postId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Post getPost() {
        Post post = new Post();
        post.setPostId(postId);
        return post;
    }
}
