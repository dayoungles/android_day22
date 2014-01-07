package com.example.mydiary;

public class Article {
	private String articleNumber;
	private String title;
	private String user;
	private String id;
	private String content;
	//private String writedate;
	private String imgName;
	
	public Article (String id, String title, String writer, String contents, String file){
		this.id = id;
		this.title = title;
		this.user = writer;
		this.content = contents;
		//this.writedate = writedate;
		this.imgName = file;
		
	}

	public String getArticleNumber() {
		return id;
	}

	public void setArticleNumber(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return user;
	}

	public void setWriter(String writer) {
		this.user = writer;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public String getWritedate() {
//		return writedate;
//	}
//
//	public void setWritedate(String writedate) {
//		this.writedate = writedate;
//	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}
