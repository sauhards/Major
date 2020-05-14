package com.login.loginsignup.controller;


import com.login.loginsignup.model.User;
import com.login.loginsignup.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {

	@Autowired
	MainService mainService;

	@GetMapping("/login")
	public String login(@RequestParam String id, @RequestParam String password)/* throws UsernameNotFoundException*/ {

		String resp =  mainService.login(id, password);

		return resp;
	}


	@PostMapping("/signup")
	public Object signup(@RequestBody User user){

		Object obj = mainService.signup(user);
		return obj;
	}

	@PostMapping("/add/amount")
	public User addAmount(@RequestParam String id, @RequestParam double amount){
		return mainService.addAmount(id, amount);
	}

	@GetMapping("/getUserById")
	public User getUser(@RequestParam String id){
		return mainService.getUser(id);
	}
}
