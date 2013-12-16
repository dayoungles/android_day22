package com.example.mydiary;

public class Article {
	private String articleNumber;
	private String title;
	private String writer;
	private String id;
	private String content;
	private String writedate;
	private String imgName;
	
	public Article (String articleNumber, String title, String writer, String id, String content, String writedate, String imgName){
		this.articleNumber = articleNumber;
		this.title = title;
		this.writer = writer;
		this.id = id;
		this.content = content;
		this.writedate = writedate;
		this.imgName = imgName;
		
	}

	public String getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWritedate() {
		return writedate;
	}

	public void setWritedate(String writedate) {
		this.writedate = writedate;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}
