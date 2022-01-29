package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Video;

@Repository
public interface VideoRepository extends CrudRepository<Video, Integer> {
	Video findById(int id);
}
