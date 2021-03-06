package kr.or.bit.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.bit.dao.ArticleDao;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.CourseDao;
import kr.or.bit.dao.FilesDao;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dao.MypageDao;
import kr.or.bit.dao.ScrapDao;
import kr.or.bit.model.Article;
import kr.or.bit.model.Comment;
import kr.or.bit.model.Course;
import kr.or.bit.model.Files;
import kr.or.bit.model.Member;
import kr.or.bit.service.FileUploadService;
import kr.or.bit.service.MemberService;
import kr.or.bit.service.MypageService;
import kr.or.bit.utils.Helper;
import kr.or.bit.utils.Pager;

@Controller
@RequestMapping("/mypage")
public class MypageController {
  @Autowired
  private SqlSession sqlSession;
  @Autowired
  private MemberService service;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private FileUploadService fileUploadService;
  @Autowired
  private MypageService mypageService;

  /*
   * 마이페이지에 관련된 모든 CRUD를 포함한 Controller
   * 내 스크랩함, 내가 쓴 게시물과 댓글, 정보 수정
   * 
   */

  //메인 페이지 -> 내가 쓴 게시물
  @GetMapping("/home")
  public String mainPage(@RequestParam(defaultValue = "1") int page, String boardSearch, String criteria, Model model) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    MypageDao mypageDao = sqlSession.getMapper(MypageDao.class);
    String username = Helper.userName();
    List<Article> article1 = articleDao.selectAllArticleByUsername(username);
    // List<Article> article2 = mypageService.allArticleByUsername(username);
    List<Comment> comments = mypageDao.selectAllMyCommentByUsername(username);
    List<Article> article2 = null;
    Pager pager = null;
    if (boardSearch != null) {
      if (criteria.equals("titleOrContent")) {
        pager = new Pager(page, mypageDao.countMyArticleByTitleOrContent(boardSearch, username));
      } else {
        pager = new Pager(page, mypageDao.countMyArticleByTitle(boardSearch, username));
      }
      article2 = mypageService.selectMyArticlesByboardSearch(pager, boardSearch, criteria, username);
      model.addAttribute("boardSearch", boardSearch);
    } else {
      pager = new Pager(page, mypageDao.countAllMyArticle(username));
      article2 = mypageService.selectAllMyArticlesByUsername(pager, username);
    }

    Member user = memberDao.selectMemberByUsername(username);
    Course course = courseDao.selectCourse(user.getCourse_id());
    int completion = mypageService.coursePeriod(username);
    model.addAttribute("completion", completion);
    model.addAttribute("course", course);
    model.addAttribute("comments", comments);
    model.addAttribute("article1", article1);
    model.addAttribute("article2", article2);
    model.addAttribute("pager", pager);
    model.addAttribute("page", page);
    model.addAttribute("criteria", criteria);
    return "mypage/mypage";
  }

  //메인 페이지 -> 내가 쓴 댓글
  @GetMapping("/home/comments")
  public String commentsPage(Model model, String boardSearch, String criteria) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
    CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
    MypageDao mypageDao = sqlSession.getMapper(MypageDao.class);
    String username = Helper.userName();
    List<Article> article1 = articleDao.selectAllArticleByUsername(username);
    List<Comment> comments = mypageDao.selectAllMyCommentByUsername(username);
    if (boardSearch != null) {
      if (criteria.equals("Commentcontent")) {
        comments = mypageService.selectMyCommentsByboardSearch(boardSearch, criteria, username);
      }
    } else {
      comments = mypageDao.selectAllMyCommentByUsername(username);
    }
    for (Comment comment : comments) {
      comment.setTimeLocal(comment.getTime().toLocalDateTime());
    }
    Member user = memberDao.selectMemberByUsername(username);
    Course course = courseDao.selectCourse(user.getCourse_id());
    int completion = mypageService.coursePeriod(username);
    model.addAttribute("completion", completion);
    model.addAttribute("course", course);
    model.addAttribute("comments", comments);
    model.addAttribute("article1", article1);
    model.addAttribute("user", user);
    return "mypage/mypageComments";
  }

  //내가 쓴 게시물의 상세페이지
  @GetMapping("/home/content")
  public String getDetail(int article_id) {
    String URL = mypageService.selectOneArticleforMypage(article_id);
    return URL;
  }

  //스크랩의 메인 페이지
  @GetMapping("/scrap")
  public String getScrapList(Model model) {
    ScrapDao scrapDao = sqlSession.getMapper(ScrapDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    List<Article> scraps = scrapDao.selectAllScrap(Helper.userName());
    for (Article scrap : scraps) {
      scrap.setBoardtype(boardDao.selectBoardById(scrap.getBoard_id()).getBoardtype());
    }
    model.addAttribute("scraps", scraps);
    return "mypage/scrap/scrap";
  }

  //스크랩의 검색 기능
  @PostMapping("/scrap/search")
  public @ResponseBody List<Article> searchScraps(String word) {
    ScrapDao scrapDao = sqlSession.getMapper(ScrapDao.class);
    BoardDao boardDao = sqlSession.getMapper(BoardDao.class);
    List<Article> scraps = scrapDao.selectScrapByWord(Helper.userName(), word);
    for (Article scrap : scraps) {
      scrap.setBoardtype(boardDao.selectBoardById(scrap.getBoard_id()).getBoardtype());
    }
    return scraps;
  }

  //정보수정 메인페이지
  @GetMapping("")
  public String updateMember(Model model, Principal principal) {
    MemberDao memberDao = sqlSession.getMapper(MemberDao.class);
    String username = Helper.userName();
    Member user = memberDao.selectMemberByUsername(username);
    user.setPassword(user.getPassword());
    model.addAttribute("user", user);
    return "mypage/mypageForm";
  }

  //정보수정의 수정완료
  @PostMapping("")
  public String updateMember(Member member, MultipartFile files1, HttpServletRequest request)
      throws IllegalStateException, IOException {
    if (files1 != null) {
      FilesDao filesDao = sqlSession.getMapper(FilesDao.class);
      Files file = fileUploadService.uploadFile(files1, request);
      filesDao.insertFiles(file);
      member.setProfile_photo(file.getFilename());
      service.updateMemberOnlyFile(member);
    } else {
      member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
      service.updateMemberWithoutFile(member);
    }
    return "redirect:/mypage";
  }
}
