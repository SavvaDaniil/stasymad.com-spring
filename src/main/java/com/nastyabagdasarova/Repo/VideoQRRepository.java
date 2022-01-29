package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.nastyabagdasarova.Model.VideoQR;

@Repository
public interface VideoQRRepository extends CrudRepository<VideoQR, Integer> {
	public VideoQR findById(int id);
}
