package com.undersnow.reboot;

import java.util.ArrayList;

import java.util.List;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RebootmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RebootmeApplication.class, args);
	}

}


@RestController
class MainController {
	 
	DB db = null;
	@PostMapping("populate")
	public String pop( @RequestBody	String story) {

		int size =0;
		if(!story.isEmpty()) 
		{
			HTreeMap<String,String> map = (HTreeMap<String, String>)db().hashMap("stories").createOrOpen();
			map.put(""+map.size(), story);
			size=map.size();
			map.close();
			
		}
		return ""+size; 
	}
	
	
	@GetMapping("articles/{fromIndex}")
	public List<String> pop(@PathVariable Integer fromIndex) {
		List<String> strings = new ArrayList<String>();
		fromIndex-=7;
		HTreeMap<String,String> map = (HTreeMap<String, String>)db().hashMap("stories").createOrOpen();
		for (int i =fromIndex; i < map.size(); i++) {
		 
			strings.add(map.get(""+i));
		}
		map.close(); 
		 return strings; 
	}
	
	@DeleteMapping("articles/{index}")
	public Boolean del(@PathVariable Integer index) {
		index-=7;
		  try {
			  HTreeMap<String,String> map = (HTreeMap<String, String>)db().hashMap("stories").createOrOpen();
				map.remove(""+index);
				map.close();
			return true; 
		} catch (Exception e) {
			return false ;
		}
	}

	@DeleteMapping("clear")
	public Boolean clearAll( ) {
		  try {
			  HTreeMap<String,String> map = (HTreeMap<String, String>)db().hashMap("stories").createOrOpen();
				map.clear();;
				map.close();
			return true; 
		} catch (Exception e) {
			return false ;
		}
	}

	private DB db() {
		if(db==null || db.isClosed())
		db = DBMaker.fileDB("file.db").make();
		return db;
	}
	
	
}
