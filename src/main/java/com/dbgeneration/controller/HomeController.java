package com.dbgeneration.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dbgeneration.constants.Constants;

@Controller
public class HomeController {

	 @GetMapping("/welcome")  
	public String welcome(Model model) throws SQLException {
		
		List<String> lstTypeOfDb = new ArrayList<>();
		lstTypeOfDb.add(Constants.MYSQL);
		lstTypeOfDb.add(Constants.POSTGRESQL);
		lstTypeOfDb.add(Constants.SQLSERVER);
		lstTypeOfDb.add(Constants.ORACLE);

		model.addAttribute("lstTypeOfDb", lstTypeOfDb);
		 
		return "welcome";
	}
}