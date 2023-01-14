package com.my.product.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class Product {
	private String prodNo;
	private String prodName;
	private int prodPrice;
	private String prodDetail;
	@JsonFormat(timezone = "Asia/Seoul", pattern="yy-MM-dd")
	private Date prodMfDt;
}
