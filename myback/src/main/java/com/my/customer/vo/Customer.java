package com.my.customer.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Customer {
	private String id;
	private String pwd;
	private String name;
}
