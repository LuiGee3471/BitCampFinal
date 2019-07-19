package kr.or.bit.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.QnaDao;
import kr.or.bit.dao.TroubleShootingDao;
import kr.or.bit.dao.VideoDao;
import kr.or.bit.dao.ViewCountDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.ArticleOption;
import kr.or.bit.model.Video;
import kr.or.bit.utils.Helper;

@Service
public class ArticleUpdateService{
  @Autowired
  private SqlSession sqlSession;

  public void updateArticle(Article article) {
    ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
    articledao.updateArticle(article);
  }

  public void updateArticleOption(int articleid, ArticleOption option) {
    String optionname = option.getClass().getName().toLowerCase().trim().substring("kr.or.bit.model.".length());
    if (optionname.equals("homework")) {
    } else if (optionname.equals("general")) {
    } else if (optionname.equals("video")) {
      VideoDao videoDao = sqlSession.getMapper(VideoDao.class);
      Video video = (Video) option;
      video.setArticle_id(articleid);
      videoDao.updateVideo(video);
    }
  }

  public void updateChangeStatus(String optionname, int id) {
    switch (optionname.toLowerCase()) {
    case "troubleshooting":
      TroubleShootingDao troubleshootingdao = sqlSession.getMapper(TroubleShootingDao.class);
      //troubleshootingdao.updateTroubleShootingByIssueClosed(id);
      break;
    case "qna":
      QnaDao qnadao = sqlSession.getMapper(QnaDao.class);
      qnadao.updateQnaByAnswered(id);
      break;
    case "qnat":
      QnaDao qnadao1 = sqlSession.getMapper(QnaDao.class);
      qnadao1.updateQnaByTeacherAnswered(id);
      break;
    case "article":
      ArticleDao articledao = sqlSession.getMapper(ArticleDao.class);
      articledao.updateEable(id);
      break;
    default:
      break;
    }
  }
  public void viewCount(Article article) {
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    ViewCountDao viewCountDao = sqlSession.getMapper(ViewCountDao.class);
    
    if (viewCountDao.countExistViewCount(article.getId(), Helper.userName()) == 0) {
      viewCountDao.insertViewCount(article.getId(), Helper.userName());
      article.setView_count(viewCountDao.countViewCountByArticleId(article.getId()));
      articleDao.updateArticleViewCount(article);
    }
  }
}
