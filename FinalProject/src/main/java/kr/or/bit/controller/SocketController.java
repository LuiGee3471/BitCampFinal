package kr.or.bit.controller;

import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.model.Message;

@Controller
public class SocketController {
  @Autowired
  private SimpMessagingTemplate template;
  
  @Autowired
  private SimpUserRegistry registry;
  
  @Autowired
  private SqlSession sqlSession;
  
  @MessageMapping("/notification")
  @SendToUser("/queue/notification")
  public void sendNotice(Message message) {
    template.convertAndSend("/topic/noti/" + message.getId(), message);
  }
  
  @MessageMapping("/notice")
  public void sendAlarm(String hello) {
    System.out.println(hello);
    template.convertAndSendToUser("manager", "/queue/notice", "new notice");
  }
  
  @MessageMapping("/chat")
  public void sendMessage() {
    Set<SimpUser> set = registry.getUsers();
    
    for (SimpUser s : set) {
      MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
      System.out.println(memberDao.selectMemberByUsername(s.getName()).getName());
    }
    
    template.convertAndSend("/topic/chat/group/1", set.size());
  }
}
