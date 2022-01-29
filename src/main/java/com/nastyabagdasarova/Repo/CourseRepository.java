package com.nastyabagdasarova.Repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nastyabagdasarova.Model.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {
	public Course findById(int id);
}
