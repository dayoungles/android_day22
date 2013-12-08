package com.example.mydiary;

public class ListData {
	private String text1;
	private String text2;
	private String imgName;
	
	ListData(String text1, String text2, String imgName){
		this.text1= text1;
		this.text2 = text2;
		this.imgName = imgName;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}
