package com.utilities.pkg;
import com.mongodb.*;
import com.pojos.pkg.*;


public class Driver {
	
	public DBObject connectJongo(int id)
	{
		try
		{
			DB dB = (new MongoClient("localhost", 27017)).getDB("product-collection");
			DBCollection channelDBCollection = dB.getCollection("items");
			return find(channelDBCollection, id);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private DBObject find(DBCollection channelDBCollection, int id) {
		int pid = id;
		BasicDBObject query = new BasicDBObject();
		query.put("pid", pid);
		DBCursor cur = channelDBCollection.find(query);
		DBObject obj = cur.next();
		return obj;
		
	}
	
	public Item getItem(int id)
	{
		DBObject obj = connectJongo(id);
		String _id = null;
		int pid = (int) obj.get("pid");
		String name =(String) obj.get("name");
		String sdate = (String) obj.get("sdate");
		String edate = (String) obj.get("edate");
		String image = (String) obj.get("image");
		String price = (String) obj.get("price");
		String director = (String) obj.get("director");
		String length = (String) obj.get("length");
		String releasedate = (String) obj.get("releasedate");
		String genre = (String) obj.get("genre");
		String rating = (String) obj.get("rating");
		String rated = (String) obj.get("rated");
		String format = (String) obj.get("format");
		String cat = (String) obj.get("cat");
		String uid = (String) obj.get("uid");
		String description = (String) obj.get("description");
		String type = (String) obj.get("type");
		String color = (String) obj.get("color");
		String size = (String) obj.get("size");
		String fabric = (String) obj.get("fabric");
		String screen = (String) obj.get("screen");
		String processor = (String) obj.get("processor");
		String clockspeed = (String) obj.get("clockspeed");
		String batterylife = (String) obj.get("batterylife");
		String ram = (String) obj.get("ram");
		String newOrOld = (String) obj.get("newOrOld");
		Item itm = new Item(pid,name,  sdate,  edate,  image,  price,  director,  length,
			 releasedate,  genre,  rating,  rated,  format,  cat,  uid,  description,
			 type,  color,  size,  fabric,  screen,  processor,  clockspeed,  batterylife,  ram,  newOrOld);
		return itm;
		
	}
}
