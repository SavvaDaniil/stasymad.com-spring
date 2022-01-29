package com.nastyabagdasarova.Interface;

import com.nastyabagdasarova.Model.ListOfSubtitles;

public interface IQueueOfSubtitleAbstractFactory {
	public ListOfSubtitles create(int id_of_demo_or_video, int type_demo0_course1);
}
