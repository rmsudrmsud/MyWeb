package com.my.products.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Products {
	private String prodNo;
	private String prodName;
	private int prodPrice;
	private String prodDetail;
	@JsonFormat(timezone = "Asia/seoul", pattern="yy-MM-dd")
	private Date prodMfDt;
	
}
