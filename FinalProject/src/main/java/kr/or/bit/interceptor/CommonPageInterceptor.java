package kr.or.bit.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sun.deploy.uitoolkit.impl.fx.Utils;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.dao.NotificationDao;
import kr.or.bit.model.Member;
import kr.or.bit.model.Message;
import kr.or.bit.model.Notification;

public class CommonPageInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private SqlSession sqlSession;
  
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView mav) throws Exception {
    
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    
    List<Message> unreadMessages = messageDao.selectUnreadMessage(username);
    List<Notification> unreadNotices = notificationDao.selectAllNewNotification(username);
    
    int unreadMessage = unreadMessages.size();
    int unreadNotice = unreadNotices.size();
    
    request.setAttribute("user", memberDao.selectMemberByUsername(username));
    request.setAttribute("unreadMessage", unreadMessage);
    request.setAttribute("unreadNotice", unreadNotice);
  }
}
