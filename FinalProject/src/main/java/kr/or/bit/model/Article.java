package kr.or.bit.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Article {
  private int id;
  private String title;
  private String content;
  private Timestamp time;
  private LocalDateTime timeLocal;
  private Date updated_time;
  private LocalDate updatedTime;
  private int view_count;
  private int original_id;
  private int level;
  private int enable;
  private String username;
  private int board_id;
  private Member writer;
  private List<String> tags;
  private ArticleOption option;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public LocalDateTime getTimeLocal() {
    return timeLocal;
  }

  public void setTimeLocal(LocalDateTime timeLocal) {
    this.timeLocal = timeLocal;
  }

  public Date getUpdated_time() {
    return updated_time;
  }

  public void setUpdated_time(Date updated_time) {
    this.updated_time = updated_time;
  }

  public LocalDate getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(LocalDate updatedTime) {
    this.updatedTime = updatedTime;
  }

  public int getView_count() {
    return view_count;
  }

  public void setView_count(int view_count) {
    this.view_count = view_count;
  }

  public int getOriginal_id() {
    return original_id;
  }

  public void setOriginal_id(int original_id) {
    this.original_id = original_id;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getEnable() {
    return enable;
  }

  public void setEnable(int enable) {
    this.enable = enable;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getBoard_id() {
    return board_id;
  }

  public void setBoard_id(int board_id) {
    this.board_id = board_id;
  }

  public Member getWriter() {
    return writer;
  }

  public void setWriter(Member writer) {
    this.writer = writer;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public ArticleOption getOption() {
    return option;
  }

  public void setOption(ArticleOption option) {
    this.option = option;
  }

  @Override
  public String toString() {
    return "Article [id=" + id + ", title=" + title + ", content=" + content + ", time=" + time + ", timeLocal="
        + timeLocal + ", updated_time=" + updated_time + ", updatedTime=" + updatedTime + ", view_count=" + view_count
        + ", original_id=" + original_id + ", level=" + level + ", enable=" + enable + ", username=" + username
        + ", board_id=" + board_id + ", writer=" + writer + ", tags=" + tags + ", option=" + option + "]";
  }
}
