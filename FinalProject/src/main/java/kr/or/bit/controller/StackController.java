package kr.or.bit.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.StackDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.General;
import kr.or.bit.model.Tag;
import kr.or.bit.model.Member;
import kr.or.bit.service.ArticleInsertService;
import kr.or.bit.service.ArticleService;
import kr.or.bit.service.ArticleUpdateService;
import kr.or.bit.service.ArticleVoteService;
import kr.or.bit.service.BoardService;
import kr.or.bit.service.CommentService;
import kr.or.bit.service.TagService;
import kr.or.bit.utils.Helper;
import kr.or.bit.utils.Pager;

@Controller
@RequestMapping("/stack")
public class StackController {
  private final int STACK_BOARD_ID = 1;
  @Autowired
  private SqlSession sqlsession;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ArticleInsertService articleInsertService;
  @Autowired
  private ArticleVoteService articleVoteService;
  @Autowired
  private ArticleUpdateService articleUpdateService;
  @Autowired
  private TagService tagService;

  @GetMapping("")
  public String listPage(@RequestParam(defaultValue = "1") int page, String boardSearch, String criteria, Model model) throws Exception{
    StackDao stackDao = sqlsession.getMapper(StackDao.class);
    MemberDao memberDao = sqlsession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member member = memberDao.selectMemberByUsername(username);
    List<Article> stackList = null;
    Pager pager = null;
    if(boardSearch != null) {
      pager = new Pager(page, stackDao.countStackArticleBySearchWord(boardSearch));
      stackList = articleService.selectStackArticlesByboardSearch(pager,boardSearch,criteria);
      model.addAttribute("boardSearch", boardSearch);
    } else {
      pager = new Pager(page, stackDao.countAllStackArticle());
      stackList = articleService.selectAllStackArticles(pager);
    }
    model.addAttribute("stacklist", stackList);
    model.addAttribute("pager", pager);
    model.addAttribute("page", page);
    return "stack/home";
  }
  /*
   * //stack 메인으로 이동
   * 
   * @GetMapping("/home") public String getStackList(Model model) { List<Article>
   * article = articleService.selectAllArticle("qna",STACK_BOARD_ID);
   * model.addAttribute("stacklist",article);
   * 
   * return "stack/home"; }
   */

  // stack 게시물 상세보기 버튼
  @GetMapping("/content")
  public String GetStackContent(int id, Model model) {
    MemberDao memberDao = sqlsession.getMapper(MemberDao.class);
    Article article = articleService.selectOneArticle("qna",id);
    for(Comment c : article.getCommentlist()) {
      c.setWriter(memberDao.selectMemberByUsername(article.getUsername()));
    }
    model.addAttribute("stackcontent",article);
    
    return "stack/content";
  }

  // 글쓰기 폼 화면으로..
  @GetMapping("/write")
  public String writeStack(Model model) {
    StackDao stackdao = sqlsession.getMapper(StackDao.class);
    List<Tag> tags = stackdao.showTagList();
    model.addAttribute("tags", tags);
    return "stack/write";
  }

  // 글쓰기 버튼 누르기..
  @PostMapping("/write")
  public String writeOkStack(Article article, String tag) {
    List<String> tagList = new ArrayList<>();
    String[] splitStr = tag.split("#");
    for (int i = 1; i < splitStr.length; i++) {
      tagList.add(splitStr[i]);
    }
    List<Tag> tags = tagService.selectTagByName(tagList);
    
    
    article.setTags(tags);
    article.setUsername(Helper.userName());
    article.setBoard_id(STACK_BOARD_ID);
    articleInsertService.writeArticle(article);
    return "redirect:/stack";
  }

  @GetMapping("/edit")
  public String editStack(int id, Model model) {
    Article article = articleService.selectOneArticle("qna", id);
    model.addAttribute("article", article);
    return "stack/edit";
  }

  @PostMapping("/edit")
  public String editStackArticle(Article article) {
    article.setUsername(Helper.userName());
    article.setBoard_id(STACK_BOARD_ID);
    General general = new General();
    articleUpdateService.updateArticle(article);
    articleUpdateService.updateArticleOption(article.getId(), general);
    return "redirect:/stack";
  }

  @GetMapping("/delete")
  public String deleteStack(int id) {
    ArticleDao articleDao = sqlsession.getMapper(ArticleDao.class);
    articleDao.deleteArticle(id);
    return "redirect:/stack";
  }

  @PostMapping("/commentwrite")
  public String stackCommentWrite(int id, Comment comment) {
    MemberDao memberDao = sqlsession.getMapper(MemberDao.class);
    ArticleDao articleDao = sqlsession.getMapper(ArticleDao.class);
    comment.setUsername(Helper.userName());
    comment.setArticle_id(id);
    commentService.insertComment(comment);
    comment.setWriter(memberDao.selectMemberByUsername(articleDao.selectOneArticle(id).getWriter().getUsername()));
    return "redirect:/stack/content?id="+id;
  }

  @GetMapping("/commentdelete")
  public String stackCommentDelete(int id) {
    Comment comment = commentService.selectOnecomment(id);
    int article_id = comment.getArticle_id();
    commentService.deleteComment(id);
    return "redirect:/stack/content?id=" + article_id;
  }

  @GetMapping("/plusvote")
  public String stackplustVote(int id) {
    articleVoteService.insertVote(id, Helper.userName());
    return "redirect:/stack/content?id=" + id;
  }
}
