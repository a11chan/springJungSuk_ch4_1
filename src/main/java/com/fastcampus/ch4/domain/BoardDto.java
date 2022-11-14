package com.fastcampus.ch4.domain;

import java.util.Date;
import java.util.Objects;

//이하 클래스와 필드 선언
public class BoardDto {
  private Integer bno; //G,S
  private String title; //G,S
  private String content; //G,S
  private String writer; //G,S
  private int view_cnt; //G,S
  private int comment_cnt; //G,S
  private Date reg_date; //G,S

  //기본 생성자 선언
  public BoardDto() {}

  // title, content, writer를 외부에서 받아 내부 필드를 초기화하는 생성자 선언
  public BoardDto(String title, String content, String writer) {
    this.title = title;
    this.content = content;
    this.writer = writer;
  }

  public Integer getBno() {
    return bno;
  }

  public void setBno(Integer bno) {
    this.bno = bno;
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

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public int getView_cnt() {
    return view_cnt;
  }

  public void setView_cnt(int view_cnt) {
    this.view_cnt = view_cnt;
  }

  public int getComment_cnt() {
    return comment_cnt;
  }

  public void setComment_cnt(int comment_cnt) {
    this.comment_cnt = comment_cnt;
  }

  public Date getReg_date() {
    return reg_date;
  }

  public void setReg_date(Date reg_date) {
    this.reg_date = reg_date;
  }

  // toString() 생성 - bno, title, content, writer, view_cnt, comment_cnt, reg_date

  @Override
  public String toString() {
    return "BoardDto{" +
        "bno=" + bno +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", writer='" + writer + '\'' +
        ", view_cnt=" + view_cnt +
        ", comment_cnt=" + comment_cnt +
        ", reg_date=" + reg_date +
        '}';
  }

  //equals() 생성 - bno, title, content, writer
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BoardDto boardDto = (BoardDto) o;
    return Objects.equals(bno, boardDto.bno) && Objects.equals(title, boardDto.title) && Objects.equals(content, boardDto.content) && Objects.equals(writer, boardDto.writer);
  }

  //hashCode() 생성 - bno, title, content, writer
  @Override
  public int hashCode() {
    return Objects.hash(bno, title, content, writer);
  }

}
