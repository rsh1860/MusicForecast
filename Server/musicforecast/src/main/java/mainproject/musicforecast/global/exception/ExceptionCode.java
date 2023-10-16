package mainproject.musicforecast.global.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_PERMISSION_DENIED(402, "Permission denied"),
    COMMENT_NOT_FOUND(404, "Comment Not Found"),
    POST_NOT_FOUND(404, "Post Not Found"),
    MEMBER_IS_EXIST(409, "Member Is Exist"),

    MEMBER_STATUS_DELETE(409, "Member Status Delete"),
    QUESTION_NOT_FOUND(404, "Question Not Found");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
