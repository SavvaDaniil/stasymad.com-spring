package com.nastyabagdasarova.Model;

public class Subtitle {

	private int id_of_video_or_course;
	private int type_demo0_course1;
	private String label;
	private String srclang;
	private String kind;
	private String language;
	private String src;

	
	public Subtitle(int id_of_video_or_course, int type_demo0_course1, String label, String srclang, String kind,
			String language, String src) {
		super();
		this.id_of_video_or_course = id_of_video_or_course;
		this.type_demo0_course1 = type_demo0_course1;
		this.label = label;
		this.srclang = srclang;
		this.kind = kind;
		this.language = language;
		this.src = src;
	}


	@SuppressWarnings("unused")
	private Subtitle() {
		
	}
	
	


	
	public int getId_of_video_or_course() {
		return id_of_video_or_course;
	}
	public void setId_of_video_or_course(int id_of_video_or_course) {
		this.id_of_video_or_course = id_of_video_or_course;
	}
	public int getType_demo0_course1() {
		return type_demo0_course1;
	}

	public void setType_demo0_course1(int type_demo0_course1) {
		this.type_demo0_course1 = type_demo0_course1;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getSrclang() {
		return srclang;
	}


	public void setSrclang(String srclang) {
		this.srclang = srclang;
	}


	public String getKind() {
		return kind;
	}


	public void setKind(String kind) {
		this.kind = kind;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getSrc() {
		return src;
	}


	public void setSrc(String src) {
		this.src = src;
	}
	
}
