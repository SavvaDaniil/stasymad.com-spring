package com.nastyabagdasarova.Interface;

import com.nastyabagdasarova.Model.Subtitle;

public interface ISubtitleFactory {
	public Subtitle createOrReturnNull(int id_of_video_or_course, int type_demo0_course1, String label, String srclang, String kind,
			String language);
}
