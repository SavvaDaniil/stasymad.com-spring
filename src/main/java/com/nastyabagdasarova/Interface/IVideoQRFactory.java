package com.nastyabagdasarova.Interface;

import com.nastyabagdasarova.Model.User;
import com.nastyabagdasarova.Model.UserQRCode;
import com.nastyabagdasarova.Model.Video;
import com.nastyabagdasarova.Model.VideoQR;

public interface IVideoQRFactory {
	VideoQR create(User user, Video video, UserQRCode userQRCode);
}
