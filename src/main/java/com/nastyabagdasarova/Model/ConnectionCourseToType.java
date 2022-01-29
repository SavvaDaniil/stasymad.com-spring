package com.nastyabagdasarova.Model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "connection_course_to_type")
public class ConnectionCourseToType {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(
		    strategy= GenerationType.AUTO,
		    generator="native"
	)
	@GenericGenerator(
	    name = "native",
	    strategy = "native"
	)
	@Column(name="id")
	private Integer id;
	
	private int id_of_course;

	private int id_of_type;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getId_of_course() {
		return id_of_course;
	}
	public void setId_of_course(int id_of_course) {
		this.id_of_course = id_of_course;
	}
	public int getId_of_type() {
		return id_of_type;
	}
	public void setId_of_type(int id_of_type) {
		this.id_of_type = id_of_type;
	}
	
	
}
