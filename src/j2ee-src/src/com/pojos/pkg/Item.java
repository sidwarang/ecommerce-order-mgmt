package com.pojos.pkg;

import java.util.ArrayList;
import java.util.List;

import org.jongo.marshall.jackson.oid.ObjectId;

public class Item {

	@ObjectId
	private String _id;
	private int pid;
	private String name;
	private String sdate;
	private String edate;
	private String image;
	private String price;
	private String director;
	private String length;
	private String releasedate;
	private String genre;
	private String rating;
	private String rated;
	private String format;
	private String cat;
	private String uid;
	private String description;
	private String type;
	private String color;
	private String size;
	private String fabric;
	private String screen;
	private String processor;
	private String clockspeed;
	private String batterylife;
	private String ram;
	private String newOrOld;	        
	
	public Item() {

	}

	public Item(int pid, String name, String sdate, String edate, String image, String price, String director, String length,
			String releasedate, String genre, String rating, String rated, String format, String cat, String uid, String description,
			String type, String color, String size, String fabric, String screen, String processor, String clockspeed, String batterylife, String ram, String newOrOld) {
		super();
		this.pid=pid;
		this.name=name;
		this.sdate=sdate;
		this.edate=edate;
		this.image=image;
		this.price=price;
		this.director=director;
		this.length=length;
		this.releasedate=releasedate;
		this.genre=genre; 
		this.rating=rating; 
		this.rated=rated; 
		this.format=format; 
		this.cat=cat; 
		this.uid=uid; 
		this.description=description;
		this.type=type; 
		this.color=color; 
		this.size=size; 
		this.fabric=fabric; 
		this.screen=screen; 
		this.processor=processor; 
		this.clockspeed=clockspeed; 
		this.batterylife=batterylife; 
		this.ram=ram;
		this.newOrOld=newOrOld;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setStartDate(String sdate) {
		this.sdate = sdate;
	}

	public String getStartDate() {
		return this.sdate;
	}

	public void setEndDate(String edate) {
		this.edate = edate;
	}

	public String getEndDate() {
		return this.edate;
	}

	public void setPId(int pid) {
		this.pid = pid;
	}

	public int getPId() {
		return this.pid;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return this.image;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return this.price;
	}
	
	public void setDirector(String director) {
		this.director=director;
	}

	public String getDirector() {
		return this.director;
	}

	public void setLength(String length) {
		this.length=length;	
	}

	public String getLength() {
		return this.length;
	}
	
	public void setReleasedate(String releasedate) {
		this.releasedate=releasedate;	
	}

	public String getReleasedate() {
		return this.releasedate;
	}
	
	public void setGenre(String genre) {
		this.genre=genre; 	
	}

	public String getGenre() {
		return this.genre;
	}
	
	public void setRating(String rating) {
		this.rating=rating; 	
	}

	public String getRating() {
		return this.rating;
	}
	
	public void setRated(String rated) {
		this.rated=rated; 	
	}

	public String getRated() {
		return this.rated;
	}
	
	public void setFormat(String format) {
		this.format=format; 	
	}

	public String getFormat() {
		return this.format;
	}
	
	public void setCat(String cat) {
		this.cat=cat; 	
	}

	public String getCat() {
		return this.cat;
	}
	
	public void setUid(String uid) {
		this.uid=uid; 
	}

	public String getUid() {
		return this.uid;
	}
	
	public void setDescription(String description) {
		this.description=description;	
	}

	public String getDescription() {
		return this.description;
	}
	
	public void setType(String type) {
		this.type=type; 
	}

	public String getType() {
		return this.type;
	}
	
	public void setColor(String color) {
		this.color=color; 
	}

	public String getColor() {
		return this.color;
	}
	
	public void setSize(String size) {
		this.size=size; 			
	}

	public String getSize() {
		return this.size;
	}
	
	public void setFabric(String fabric) {
		this.fabric=fabric; 				
	}

	public String getFabric() {
		return this.fabric;
	}
	
	public void setScreen(String screen) {
		this.screen=screen; 			
	}

	public String getScreen() {
		return this.screen;
	}
	
	public void setProcessor(String processor) {
		this.processor=processor; 			
	}

	public String getProcessor() {
		return this.processor;
	}
	
	public void setClock(String clockspeed) {
		this.clockspeed=clockspeed; 			
	}

	public String getClock() {
		return this.clockspeed;
	}
	
	public void setBattery(String batterylife) {
		this.batterylife=batterylife; 
	}

	public String getBattery() {
		return this.batterylife;
	}
	
	public void setRam(String ram) {
		this.ram=ram;		
	}

	public String getRam() {
		return this.ram;
	}
	
	public void setNewOrOld(String newOrOld) {
		this.newOrOld=newOrOld;		
	}

	public String getNewOrOld() {
		return this.newOrOld;
	}

	@Override
	public String toString() {
		return new StringBuffer(" director : ").append(this.director).append(" type : ").append(this.type).append(" screen : ").append(this.screen)
				.append(" length : ").append(this.length).append(" color : ").append(this.color).append(" processor : ").append(this.processor).append(" releasedate : ").append(this.releasedate)
				.append(" size : ").append(this.size).append(" clockspeed : ").append(this.clockspeed).append(" genre : ").append(this.genre).append(" fabric : ").append(this.fabric)
				.append(" rating : ").append(this.rating).append(" batterylife : ").append(this.batterylife).append(" rated : ").append(this.rated).append(" ram : ").append(this.ram)
				.append(" format : ").append(this.format).append(" newOrOld : ").append(this.newOrOld).append(" cat : ").append(this.cat).append(" uid : ").append(this.uid)
				.append(" description : ").append(this.description).toString();
	}
	
	public List<String> toList() {
		List<String> s = new ArrayList<String>();
		s.add("<tr><td align='left'>director</td><td align='left'> : "+this.director); s.add("</td></tr><tr><td align='left'>length</td><td align='left'> : </td><td>"+this.length); s.add("<tr><td align='left'>releasedate</td><td align='left'> : "+this.releasedate); s.add("<tr><td align='left'>genre</td><td align='left'> : "+this.genre)
		; s.add("<tr><td align='left'>rating</td><td align='left'> : "+this.rating); s.add("<tr><td align='left'>rated</td><td align='left'> : "+this.rated); s.add("<tr><td align='left'>format</td><td align='left'> : "+this.format); s.add("<tr><td align='left'>catagory</td><td align='left'> : "+this.cat)
		; s.add("<tr><td align='left'>type</td><td align='left'> : "+this.type); s.add("<tr><td align='left'>color</td><td align='left'> : "+this.color); s.add("<tr><td align='left'>size</td><td align='left'> : "+this.size); s.add("<tr><td align='left'>fabric</td><td align='left'> : "+this.fabric)
		; s.add("<tr><td align='left'>screen</td><td align='left'> : "+this.screen); s.add("<tr><td align='left'>processor</td><td align='left'> : "+this.processor); s.add("<tr><td align='left'>clockspeed</td><td align='left'> : "+this.clockspeed)
		; s.add("<tr><td align='left'>batterylife</td><td align='left'> : "+this.batterylife); s.add("<tr><td align='left'>ram</td><td align='left'> : "+this.ram); s.add("<tr><td align='left'>status</td><td align='left'> : "+this.newOrOld+"</td></tr>");
		return s;
		
		
	}

}
