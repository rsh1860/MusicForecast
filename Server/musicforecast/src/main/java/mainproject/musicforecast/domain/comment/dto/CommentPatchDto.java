package mainproject.musicforecast.domain.comment.dto;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
public class CommentPatchDto {
    private Long commentId;
    @NotBlank(message = "글을 작성해주세요.")
    private String text;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}