package kr.or.bit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.bit.model.Article;
import kr.or.bit.utils.Pager;

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

  void insertTroubleShootingArticle(@Param("article") Article article, @Param("group_id") int group_id);

  void updateTroubleShootingArticle(Article article);

  void insertReplyArticle(Article article);

  void updateArticle(Article article);

  void deleteArticle(int id);

  List<Article> selectAllArticleByBoardId(@Param("board_id") int board_id);

  List<Article> selectFirstArticlesByBoardId(@Param("board_id") int board_id);

  List<Article> selectFirstArticlesToCopyByBoardId(int board_id);

  List<Article> selectNextArticlesToCopyByBoardId(@Param("board_id") int board_id, @Param("id") int id);

  List<Article> selectAllPagingArticlesByBoardId(@Param("board_id") int board_id, @Param("pager") Pager pager);

  List<Article> selectAllPagingArticlesByViewCount(@Param("board_id") int board_id, @Param("pager") Pager pager);

  List<Article> selectAllPagingArticlesByWrite(@Param("board_id") int board_id, @Param("pager") Pager pager);

  Article selectOneArticle(@Param("id") int id);

  void insertVote(@Param("id") int id, @Param("username") String username);

  void deleteVote(@Param("id") int id, @Param("username") String username);

  int countVote(@Param("id") int id);

  List<Article> selectArticlesOnNextPage(@Param("board_id") int board_id, @Param("article_id") int article_id);

  int selectVote(@Param("id") int articleId, @Param("username") String username);

  void updateEable(int id);

  List<Article> selectArticlesByPage(@Param("board_id") int board_id, @Param("start") int start, @Param("end") int end);

  List<Article> selectArticlesSorted(@Param("board_id") int board_id, @Param("start") int start, @Param("end") int end);

  List<Article> selectArticlesBySearchWord(@Param("board_id") int board_id, @Param("start") int start,
      @Param("end") int end, @Param("criteria") String criteria, @Param("search") String search);

  List<Article> selectArticlesByComment(@Param("board_id") int board_id, @Param("start") int start,
      @Param("end") int end, @Param("criteria") String criteria, @Param("search") String search);

  List<Article> selectHomeworkReplies(int article_id);

  List<Article> selectArticlesForClassMain(int course_id);

  List<Article> selectAllIssuesOpened(@Param("group_id") int group_id, @Param("criteria") String criteria,
      @Param("word") String word);

  List<Article> selectAllIssuesClosed(@Param("group_id") int group_id, @Param("criteria") String criteria,
      @Param("word") String word);

  List<Article> selectIssuesOpenedByPage(@Param("group_id") int group_id, @Param("pager") Pager pager,
      @Param("criteria") String criteria, @Param("word") String word);

  List<Article> selectIssuesClosedByPage(@Param("group_id") int group_id, @Param("pager") Pager pager,
      @Param("criteria") String criteria, @Param("word") String word);

  void updateArticleViewCount(Article article);

  void updateArticleLevel(Article article);

  List<Article> selectRecentlyCommentedArticle();

  Integer selectMaxLevel(Article article);

  Integer selectMaxLevelBySibling(Article article);

  List<Article> selectAllArticleByUsername(String username);

  List<Article> selectSearchTitleByBoardId(@Param("board_id") int board_id, @Param("title") String title);

  List<Article> selectEnableArticleByUsername(String username);

  Article selectRecentHomework(String username);

  List<Article> selectRecentStackbyCourse(int courseid);

  Article selectRecentQnabyBoardId(int Board_id);
}
