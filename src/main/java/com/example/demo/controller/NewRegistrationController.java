package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import com.example.demo.model.NewRegist;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NewRegistrationController {
	public void execute(NewRegist newRegist) {
		NewRegistractDAO dao = new NewRegistractDAO();
		dao.create(newRegist);
	}
}
