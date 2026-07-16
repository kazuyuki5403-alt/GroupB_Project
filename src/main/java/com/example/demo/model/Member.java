package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private String member_id;
	private String password;
	private String member_name;
	private String postal_code;
	private String address;
	private String phone_number;
	private String birth_date;
	private String email;
	private String payment_method;
	private String role;
}
