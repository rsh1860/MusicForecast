//package mainproject.musicforecast.domain.reply.entity;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import mainproject.musicforecast.domain.audit.Auditable;
//import mainproject.musicforecast.domain.comment.entity.Comment;
//import mainproject.musicforecast.domain.member.entity.Member;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class Reply extends Auditable {
//    @Column
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long replyId;
//
//    @ManyToOne
//    @JoinColumn(name = "MEMBER_ID")
//    private Member member;
//
//    @Column
//    private String text;
//
//    @ManyToOne
//    @JoinColumn(name = "comment_id")
//    private Comment comment;
//
//}