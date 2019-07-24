package kr.or.bit.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MessageDao;
import kr.or.bit.dao.NotificationDao;
import kr.or.bit.model.Member;
import kr.or.bit.model.Notification;
import kr.or.bit.utils.Helper;
/*
 * 현재 접속한 사용자의 알림을 처리하는 Interceptor
 * */
public class CommonPageInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private SqlSession sqlSession;
  
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView mav) throws Exception {    
    MessageDao messageDao = sqlSession.getMapper(MessageDao.class);
    NotificationDao notificationDao = sqlSession.getMapper(NotificationDao.class);
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    
    String username = Helper.userName();
    
    int unreadMessages = messageDao.selectUnreadMessage(username).size();
    int unreadNotices = notificationDao.selectAllNewNotification(username).size();
    List<Notification> allNotices = notificationDao.selectAllNotification(username);
    
    Member user = memberDao.selectMemberByUsername(username);
    user.setPassword("");
    
    request.setAttribute("user", user);
    request.setAttribute("allNotices", allNotices);
    request.setAttribute("unreadMessages", unreadMessages);
    request.setAttribute("unreadNotices", unreadNotices);
  }
}
