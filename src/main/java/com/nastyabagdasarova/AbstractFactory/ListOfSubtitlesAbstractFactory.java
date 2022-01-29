package com.nastyabagdasarova.AbstractFactory;

import java.util.ArrayList;

import com.nastyabagdasarova.Factory.SubtitleFactory;
import com.nastyabagdasarova.Interface.IQueueOfSubtitleAbstractFactory;
import com.nastyabagdasarova.Model.ListOfSubtitles;
import com.nastyabagdasarova.Model.Subtitle;

public class ListOfSubtitlesAbstractFactory implements IQueueOfSubtitleAbstractFactory {

	@Override
	public ListOfSubtitles create(int id_of_course_or_video, int type_demo0_course1) {
		
		ArrayList<Subtitle> subtitles = new ArrayList<Subtitle>();
		SubtitleFactory subtitleFactory = new SubtitleFactory();
		Subtitle subtitle;
		
		subtitle = subtitleFactory.createOrReturnNull(id_of_course_or_video, type_demo0_course1, "English", "english", "captions", "English");
		if(subtitle != null) {
			subtitles.add(subtitle);
		}
		subtitle = subtitleFactory.createOrReturnNull(id_of_course_or_video, type_demo0_course1, "中文", "china", "captions", "中文");
		if(subtitle != null) {
			subtitles.add(subtitle);
		}
		subtitle = subtitleFactory.createOrReturnNull(id_of_course_or_video, type_demo0_course1, "Español", "spain", "captions", "Español");
		if(subtitle != null) {
			subtitles.add(subtitle);
		}
		subtitle = subtitleFactory.createOrReturnNull(id_of_course_or_video, type_demo0_course1, "Deutsche", "deutch", "captions", "Deutsche");
		if(subtitle != null) {
			subtitles.add(subtitle);
		}
		subtitle = subtitleFactory.createOrReturnNull(id_of_course_or_video, type_demo0_course1, "Magyar", "hungarian", "captions", "Magyar");
		if(subtitle != null) {
			subtitles.add(subtitle);
		}
		
		subtitle = null;
		subtitleFactory = null;
		System.gc();
		
		return new ListOfSubtitles(id_of_course_or_video, type_demo0_course1, subtitles);
	}

}
