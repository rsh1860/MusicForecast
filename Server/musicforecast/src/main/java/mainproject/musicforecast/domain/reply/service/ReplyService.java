//package mainproject.musicforecast.domain.reply.service;
//
//import mainproject.musicforecast.domain.reply.entity.Reply;
//import mainproject.musicforecast.domain.reply.repository.ReplyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ReplyService {
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    public List<Reply> getRepliesByCommentId(Long commentId) {
//        return replyRepository.findByCommentId(commentId);
//    }
//
//    public Reply saveReply(Reply reply) {
//        return replyRepository.save(reply);
//    }
//}
