package com.nastyabagdasarova.Factory;

import com.nastyabagdasarova.Interface.IVideoFactory;
import com.nastyabagdasarova.Model.Video;

public class VideoFactory implements IVideoFactory {

	@Override
	public Video clone(Video video) {
		return (Video) video.copy();
	}

}
