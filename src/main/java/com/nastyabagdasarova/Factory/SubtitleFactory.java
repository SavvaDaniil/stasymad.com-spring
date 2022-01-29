package com.nastyabagdasarova.Factory;

import java.io.File;
import com.nastyabagdasarova.Interface.ISubtitleFactory;
import com.nastyabagdasarova.Model.Subtitle;

public class SubtitleFactory implements ISubtitleFactory {
	
	@Override
	public Subtitle createOrReturnNull(int id_of_video_or_course, int type_demo0_course1, String label, String srclang, String kind,
			String language) {
		
		if(language.equals("")) {
			return null;
		}
		
		String subPath;
		if(type_demo0_course1 == 0) {
			subPath = "public/track_demo";
		} else if(type_demo0_course1 == 1) {
			subPath = "private/track";
		} else {
			subPath = null;
			System.gc();
			return null;
		}

		File folderTracksOfVideo = new File(subPath+"/"+ id_of_video_or_course);
		if(!folderTracksOfVideo.exists()) {
			folderTracksOfVideo.mkdir();
		}
		
		
		String src = subPath + "/" + id_of_video_or_course + "/"+srclang+".vtt";
		File fileCheck = new File(src);
		if(!fileCheck.exists()) {
			System.out.println(subPath + "/" + id_of_video_or_course + "/"+srclang+".vtt" + " - Файл не найден");
			folderTracksOfVideo = null;
			subPath = null;
			fileCheck = null;
			System.gc();
			return null;
		}
		
		folderTracksOfVideo = null;
		subPath = null;
		fileCheck = null;
		System.gc();
		
		return new Subtitle(id_of_video_or_course, type_demo0_course1, label, srclang, kind,
				language, src);
	}
	
}
