package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;

/*
 *
 * @date: 2019. 6. 21.
 *
 * @author: 이힘찬 
 *
 * @description: ArticleDao
 *
 */
public interface ArticleDao {
  void insertArticle(Article article);

  void updateArticle(Article article);

  void deleteArticle(int id);

  List<Article> selectAllArticleByBoardId(int board_id);

  Article selectOneArticle(@Param("id") int id);

  void insertVote(@Param("id") int id, @Param("username") String username);

  void deleteVote(@Param("id") int id, @Param("username") String username);

  int countVote(@Param("id") int id);

  int getMostRecentArticleId();

  List<Article> selectArticlesOnNextPage(int article_id);

  int selectVote(@Param("id") int articleId, @Param("username") String username);

  void updateEable(int id);

  List<Article> selectArticlesByPage(@Param("board_id") int board_id, @Param("page") int page);
}
