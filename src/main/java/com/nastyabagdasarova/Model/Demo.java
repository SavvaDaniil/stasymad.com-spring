package com.nastyabagdasarova.Model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "demo")
public class Demo {
	
	public Demo() {
		this.basePath = "public/demo";
	}
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name="id")
	private Integer id;
	
	private int id_of_course;
	private String hash;
	private boolean status;
	
	@Column(name = "date_of_add")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_add;
	

	@Transient
	private String basePath;

	@Transient
	protected ArrayList<Subtitle> listOfSubtitleDemo;
	
	
	
	@Transient
	private String srcVideo;
	
	@Transient
	private String srcPoster;
	
	
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
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getDate_of_add() {
		return date_of_add;
	}
	public void setDate_of_add(Date date_of_add) {
		this.date_of_add = date_of_add;
	}
	public String getBasePath() {
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public ArrayList<Subtitle> getListOfSubtitleDemo() {
		return listOfSubtitleDemo;
	}
	public void setListOfSubtitleDemo(ArrayList<Subtitle> listOfSubtitleDemo) {
		this.listOfSubtitleDemo = listOfSubtitleDemo;
	}
	public String getSrcVideo() {
		return srcVideo;
	}
	public void setSrcVideo(String srcVideo) {
		this.srcVideo = srcVideo;
	}
	public String getSrcPoster() {
		return srcPoster;
	}
	public void setSrcPoster(String srcPoster) {
		this.srcPoster = srcPoster;
	}
	
	
}
