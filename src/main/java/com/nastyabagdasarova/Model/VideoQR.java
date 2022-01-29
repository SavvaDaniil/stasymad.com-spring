package com.nastyabagdasarova.Model;

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
@Table(name = "video_qr")
public class VideoQR implements Copyable {

	public VideoQR(int id_of_user, int id_of_video, UserQRCode userQRCode) {
		this.id_of_user = id_of_user;
		this.id_of_video = id_of_video;
		this.userQRCode = userQRCode;
		this.basePath = "private/videoqr";
	}
	
	

	public VideoQR(Integer id, int id_of_user, int id_of_video, int status, String hash, UserQRCode userQRCode,
			String basePath, String src, String srcOutput, String srcTSBase, Date date_of_add, Date date_of_refresh, Date date_of_start) {
		super();
		this.id = id;
		this.id_of_user = id_of_user;
		this.id_of_video = id_of_video;
		this.status = status;
		this.hash = hash;
		this.userQRCode = userQRCode;
		this.basePath = basePath;
		this.src = src;
		this.srcOutput = srcOutput;
		this.srcTSBase = srcTSBase;
		this.date_of_add = date_of_add;
		this.date_of_refresh = date_of_refresh;
		this.date_of_start = date_of_start;
	}



	public VideoQR() {
		this.basePath = "private/videoqr";
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
	
	private int id_of_user;
	private int id_of_video;
	private int status;
	private String hash;


	@Transient
	private UserQRCode userQRCode;
	
	@Transient
	public String basePath;
	
	@Transient
	public String src;
	
	@Transient
	public String srcOutput;
	
	@Transient
	public String srcTSBase;
	

	@Column(name = "date_of_add")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_add;
	
	@Column(name = "date_of_refresh")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_refresh;
	
	@Column(name = "date_of_start")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_start;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public int getId_of_user() {
		return id_of_user;
	}

	public void setId_of_user(int id_of_user) {
		this.id_of_user = id_of_user;
	}

	public int getId_of_video() {
		return id_of_video;
	}

	public void setId_of_video(int id_of_video) {
		this.id_of_video = id_of_video;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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
	

	public Date getDate_of_start() {
		return date_of_start;
	}



	public void setDate_of_start(Date date_of_start) {
		this.date_of_start = date_of_start;
	}



	public UserQRCode getUserQRCode() {
		return userQRCode;
	}

	public void setUserQRCode(UserQRCode userQRCode) {
		this.userQRCode = userQRCode;
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
	
	
	public String getSrcTSBase() {
		return srcTSBase;
	}



	public void setSrcTSBase(String srcTSBase) {
		this.srcTSBase = srcTSBase;
	}



	@Override
	public Object copy() {
		VideoQR copy = new VideoQR(id, id_of_user, id_of_video, status, hash, userQRCode,
				basePath, src, srcOutput, srcTSBase, date_of_add, date_of_refresh, date_of_start);
		return copy;
	}
	
	
	public String getSrcOutput() {
		return srcOutput;
	}

	public void setSrcOutput(String srcOutput) {
		this.srcOutput = srcOutput;
	}

	public void updateSrc() {
		this.src = this.basePath + "/" + this.id_of_user + "/" + this.id_of_video + "_" + this.hash + "/playlist.m3u8";
	}
	public void updateSrcOutput() {
		this.srcOutput = this.basePath + "/" + this.id_of_user + "/" + this.id_of_video + "_" + this.hash + "/output.mp4";
	}
	public void updateSrcTSBase() {
		this.srcTSBase = this.basePath + "/" + this.id_of_user + "/" + this.id_of_video + "_" + this.hash;
	}



	@Override
	public String toString() {
		return "VideoQR [id=" + id + ", id_of_user=" + id_of_user + ", id_of_video=" + id_of_video + ", status="
				+ status + ", hash=" + hash + ", userQRCode=" + userQRCode + ", basePath=" + basePath + ", src=" + src
				+ ", srcOutput=" + srcOutput + ", date_of_add=" + date_of_add + ", date_of_refresh=" + date_of_refresh
				+ ", date_of_start=" + date_of_start + "]";
	}

	
	
	
	
	
}
