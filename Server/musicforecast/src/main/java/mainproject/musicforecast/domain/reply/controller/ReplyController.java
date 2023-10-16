//package mainproject.musicforecast.domain.reply.controller;
//
//import mainproject.musicforecast.domain.reply.entity.Reply;
//import mainproject.musicforecast.domain.reply.service.ReplyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/replies")
//public class ReplyController {
//    @Autowired
//    private ReplyService replyService;
//
//    @GetMapping("/{commentId}")
//    public ResponseEntity<List<Reply>> getRepliesByCommentId(@PathVariable Long commentId) {
//            List<Reply> replies = replyService.getRepliesByCommentId(commentId);
//            return ResponseEntity.ok(replies);
//    }
//
//    @PostMapping
//    public ResponseEntity<Reply> saveReply(@RequestBody Reply reply) {
//            Reply savedReply = replyService.saveReply(reply);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedReply);
//    }
//
//}
