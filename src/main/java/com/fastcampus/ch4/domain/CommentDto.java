package com.fastcampus.ch4.domain;


import java.util.Date;
import java.util.Objects;

//**4.10 클래스 전체
public class CommentDto {

  //필드 선언부, comment 테이블 보고 필드 선언
  private Integer cno;
  private Integer bno;
  private Integer pcno;
  private String comment;
  private String commenter;
  private Date reg_date;
  private Date up_date;

  //기본 생성자
  public CommentDto(){}
  //필드 주입 생성자 - AutoIncrement(cno) 및 자동 생성 컬럼(reg_date, up_date)은 생성자 제외
  public CommentDto(Integer bno, Integer pcno, String comment, String commenter) {
    this.bno = bno;
    this.pcno = pcno;
    this.comment = comment;
    this.commenter = commenter;
  }

  //getters&setters & toString() 모든 필드에 대해 생성
  public Integer getCno() {
    return cno;
  }

  public void setCno(Integer cno) {
    this.cno = cno;
  }

  public Integer getBno() {
    return bno;
  }

  public void setBno(Integer bno) {
    this.bno = bno;
  }

  public Integer getPcno() {
    return pcno;
  }

  public void setPcno(Integer pcno) {
    this.pcno = pcno;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getCommenter() {
    return commenter;
  }

  public void setCommenter(String commenter) {
    this.commenter = commenter;
  }

  public Date getReg_date() {
    return reg_date;
  }

  public void setReg_date(Date reg_date) {
    this.reg_date = reg_date;
  }

  public Date getUp_date() {
    return up_date;
  }

  public void setUp_date(Date up_date) {
    this.up_date = up_date;
  }

  @Override
  public String toString() {
    return "CommentDto{" +
        "cno=" + cno +
        ", bno=" + bno +
        ", pcno=" + pcno +
        ", comment='" + comment + '\'' +
        ", commenter='" + commenter + '\'' +
        ", reg_date=" + reg_date +
        ", up_date=" + up_date +
        '}';
  }

  //equals() & hashCode() 생성 시 날짜형 2개는 제외

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CommentDto that = (CommentDto) o;
    return Objects.equals(cno, that.cno) && Objects.equals(bno, that.bno) && Objects.equals(pcno, that.pcno) && Objects.equals(comment, that.comment) && Objects.equals(commenter, that.commenter) && Objects.equals(reg_date, that.reg_date) && Objects.equals(up_date, that.up_date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cno, bno, pcno, comment, commenter, reg_date, up_date);
  }
}
