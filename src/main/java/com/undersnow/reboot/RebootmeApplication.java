package com.undersnow.reboot;

import java.util.ArrayList;

import java.util.List;

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
	
	List<String> articles =  new ArrayList<String>();
	
	@PostMapping("populate")
	public String pop(
			
		@RequestBody	String story) {
		if(!story.isEmpty())
		articles.add(story); 
		return ""+articles.size(); 
	}
	
	
	@GetMapping("articles/{fromIndex}")
	public List<String> pop(@PathVariable Integer fromIndex) {
		 if(articles.size()>fromIndex) return articles.subList(fromIndex, articles.size()); 
		 return new ArrayList<String>(); 
	}
	
	@DeleteMapping("articles/{index}")
	public Boolean del(@PathVariable Integer index) {
		  try {
			articles.remove(index);
			return true; 
		} catch (Exception e) {
			return false ;
		}
	}
	
	
}
