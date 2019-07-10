package kr.or.bit.utils;

public class Pager {
  private final int articlesOnPage = 10;
  private final int pageButtons = 5;
  private int currentPage;
  private int startPage;
  private int endPage;
  private int totalPages;
  private boolean prev;
  private boolean next;
  private int prevPage;
  private int nextPage;
  // private int totalArticles;
  private int start;
  private int end;

  public Pager(int currentPage, int totalArticles) {
    this.currentPage = currentPage;
    this.start = (currentPage - 1) * articlesOnPage;
    this.end = currentPage * articlesOnPage;
    int q = totalArticles / articlesOnPage;
    int r = totalArticles % articlesOnPage;
    this.totalPages = (r == 0 && totalArticles != 0) ? q : (q + 1);
    if (currentPage <= pageButtons) {
      this.startPage = 1;
    } else if (currentPage % pageButtons == 0) {
      this.startPage = currentPage - (pageButtons - 1);
    } else {
      this.startPage = (currentPage / pageButtons) * 5 + 1;
    }

    if (totalPages <= pageButtons) {
      this.endPage = totalPages;
    } else if (currentPage % pageButtons == 0) {
      this.endPage = currentPage;
    } else if ((currentPage / pageButtons + 1) * 5 < totalPages) {
      this.endPage = (currentPage / pageButtons + 1) * 5;
    } else {
      this.endPage = totalPages;
    }

    this.prev = (currentPage <= pageButtons) ? false : true;
    this.next = ((currentPage - 1) / pageButtons + 1) * 5 < totalPages ? true : false;
    this.prevPage = (currentPage % 5 == 0) ? (currentPage / 5 - 1) * 5 : (currentPage / 5) * 5;
    this.nextPage = (currentPage % 5 == 0) ? (currentPage / 5) * 5 + 1 : (currentPage / 5 + 1) * 5 + 1;
  }

  public int getPrevPage() {
    return prevPage;
  }

  public void setPrevPage(int prevPage) {
    this.prevPage = prevPage;
  }

  public int getNextPage() {
    return nextPage;
  }

  public void setNextPage(int nextPage) {
    this.nextPage = nextPage;
  }

  public int getStartPage() {
    return startPage;
  }

  public void setStartPage(int startPage) {
    this.startPage = startPage;
  }

  public int getEndPage() {
    return endPage;
  }

  public void setEndPage(int endPage) {
    this.endPage = endPage;
  }

  public int getArticlesOnPage() {
    return articlesOnPage;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getStart() {
    return start;
  }

  public void setStart(int start) {
    this.start = start;
  }

  public int getEnd() {
    return end;
  }

  public void setEnd(int end) {
    this.end = end;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getPageButtons() {
    return pageButtons;
  }

  public boolean isPrev() {
    return prev;
  }

  public void setPrev(boolean prev) {
    this.prev = prev;
  }

  public boolean isNext() {
    return next;
  }

  public void setNext(boolean next) {
    this.next = next;
  }
}
