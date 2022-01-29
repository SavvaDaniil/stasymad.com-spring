package com.nastyabagdasarova.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "course")
public class Course {

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
	protected Integer id;
	 
	protected String name;
	protected String description;
	protected int kind;
	protected int price;
	protected int fake_price;
	protected int days;
	protected int status;
	protected int order_in_list;
	protected int beginner;
	protected int intermediate;
	private int original_inst1_youtube2;
	protected String instagram;
	protected String youtube;
	

	@Transient
	protected int alreadyBuyed;
	
	@Transient
	protected Back back;

	@Transient
	protected String posterSrc;

	@Transient
	protected String posterOriginal;

	@Transient
	protected String srcOriginal;

	@Transient
	protected String posterDemo;

	@Transient
	protected String srcDemo;
	
	@Transient
	private Demo demo;

	@Transient
	protected ListOfSubtitles queueOfSubtitleDemo;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
		

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}

	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getFake_price() {
		return fake_price;
	}
	public void setFake_price(int fake_price) {
		this.fake_price = fake_price;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getOrder_in_list() {
		return order_in_list;
	}
	public void setOrder_in_list(int order_in_list) {
		this.order_in_list = order_in_list;
	}
	public int getBeginner() {
		return beginner;
	}
	public void setBeginner(int beginner) {
		this.beginner = beginner;
	}
	public int getIntermediate() {
		return intermediate;
	}
	public void setIntermediate(int intermediate) {
		this.intermediate = intermediate;
	}

	public int getAlreadyBuyed() {
		return alreadyBuyed;
	}
	public void setAlreadyBuyed(int alreadyBuyed) {
		this.alreadyBuyed = alreadyBuyed;
	}
	public Back getBack() {
		return back;
	}
	public void setBack(Back back) {
		this.back = back;
	}
	public String getPosterSrc() {
		return posterSrc;
	}
	public void setPosterSrc(String posterSrc) {
		this.posterSrc = posterSrc;
	}

	public int getOriginal_inst1_youtube2() {
		return original_inst1_youtube2;
	}

	public void setOriginal_inst1_youtube2(int original_inst1_youtube2) {
		this.original_inst1_youtube2 = original_inst1_youtube2;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	public String getPosterOriginal() {
		return posterOriginal;
	}
	public void setPosterOriginal(String posterOriginal) {
		this.posterOriginal = posterOriginal;
	}
	public String getPosterDemo() {
		return posterDemo;
	}
	public void setPosterDemo(String posterDemo) {
		this.posterDemo = posterDemo;
	}
	public String getSrcOriginal() {
		return srcOriginal;
	}
	public void setSrcOriginal(String srcOriginal) {
		this.srcOriginal = srcOriginal;
	}
	public String getSrcDemo() {
		return srcDemo;
	}
	public void setSrcDemo(String srcDemo) {
		this.srcDemo = srcDemo;
	}
	public ListOfSubtitles getQueueOfSubtitleDemo() {
		return queueOfSubtitleDemo;
	}
	public void setQueueOfSubtitleDemo(ListOfSubtitles queueOfSubtitleDemo) {
		this.queueOfSubtitleDemo = queueOfSubtitleDemo;
	}
	public Demo getDemo() {
		return demo;
	}
	public void setDemo(Demo demo) {
		this.demo = demo;
	}
	
}
