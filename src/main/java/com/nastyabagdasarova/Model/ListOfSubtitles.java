package com.nastyabagdasarova.Model;

import java.util.ArrayList;

public class ListOfSubtitles {

	private int id_of_course_or_video;
	private int type_demo0_course1;
	
	
	public ListOfSubtitles(int id_of_course_or_video, int type_demo0_course1, ArrayList<Subtitle> subtitles) {
		super();
		this.id_of_course_or_video = id_of_course_or_video;
		this.type_demo0_course1 = type_demo0_course1;
		this.subtitles = subtitles;
	}

	
	@SuppressWarnings("unused")
	private ListOfSubtitles() {
		
	}
	
	
	private ArrayList<Subtitle> subtitles;


	public int getId_of_course_or_video() {
		return id_of_course_or_video;
	}
	public void setId_of_course_or_video(int id_of_course_or_video) {
		this.id_of_course_or_video = id_of_course_or_video;
	}
	public int getType_demo0_course1() {
		return type_demo0_course1;
	}

	public void setType_demo0_course1(int type_demo0_course1) {
		this.type_demo0_course1 = type_demo0_course1;
	}


	public ArrayList<Subtitle> getSubtitles() {
		return subtitles;
	}


	public void setSubtitles(ArrayList<Subtitle> subtitles) {
		this.subtitles = subtitles;
	}
	
	
	
}
