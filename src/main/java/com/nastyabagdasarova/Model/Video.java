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

import com.nastyabagdasarova.Prototype.Copyable;

@Entity
@Table(name = "video")
public class Video implements Copyable {

	public Video() {
		this.basePath = "video";
	}
	
	
	public Video(Integer id, int id_of_course, String hash, int number, int status, Date date_of_add,
			Date date_of_refresh, ArrayList<Subtitle> listOfSubtitle, String basePath, String src, String srcOutput,
			int statusQRContent) {
		super();
		this.id = id;
		this.id_of_course = id_of_course;
		this.hash = hash;
		this.number = number;
		this.status = status;
		this.date_of_add = date_of_add;
		this.date_of_refresh = date_of_refresh;
		this.listOfSubtitle = listOfSubtitle;
		this.basePath = basePath;
		this.src = src;
		this.srcOutput = srcOutput;
		this.statusQRContent = statusQRContent;
	}


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
	private String hash;
	private int number;
	private int status;

	@Column(name = "date_of_add")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_add;
	
	@Column(name = "date_of_refresh")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_refresh;
	
	
	@Transient
	private ArrayList<Subtitle> listOfSubtitle;
	
	@Transient
	private String basePath;
	
	@Transient
	private String src;
	
	@Transient
	private String srcOutput;
	
	@Transient
	private int statusQRContent;
	

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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDate_of_add() {
		return date_of_add;
	}

	public void setDate_of_add(Date date_of_add) {
		this.date_of_add = date_of_add;
	}

	public Date getDate_of_refresh() {
		return date_of_refresh;
	}

	public void setDate_of_refresh(Date date_of_refresh) {
		this.date_of_refresh = date_of_refresh;
	}

	public ArrayList<Subtitle> getListOfSubtitle() {
		return listOfSubtitle;
	}

	public void setListOfSubtitle(ArrayList<Subtitle> listOfSubtitle) {
		this.listOfSubtitle = listOfSubtitle;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	

	public String getSrcOutput() {
		return srcOutput;
	}


	public void setSrcOutput(String srcOutput) {
		this.srcOutput = srcOutput;
	}


	public int getStatusQRContent() {
		return statusQRContent;
	}

	public void setStatusQRContent(int statusQRContent) {
		this.statusQRContent = statusQRContent;
	}

	@Override
	public Object copy() {
		Video copy = new Video(id, id_of_course, hash, number, status, date_of_add,
				date_of_refresh, listOfSubtitle, basePath, src, srcOutput,
				statusQRContent);
		return copy;
	}
	

	

	

	
	
}
