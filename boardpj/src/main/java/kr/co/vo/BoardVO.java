package kr.co.vo;

import java.util.Date;

public class BoardVO {
	
	/* VO는 DB에 있는 테이블 값을 java에서 객체로 다루기 위한것 
	   그렇기 때문에 데이터를 객체로 다루려면 게터랑 세터가 있어야함 */
	
	private int bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private int hit;    
	
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
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
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	@Override
	public String toString() {
		return "BoardVO [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer + ", regdate="
				+ regdate + ", hit=" + hit + "]";
	}
	
	
	
}
