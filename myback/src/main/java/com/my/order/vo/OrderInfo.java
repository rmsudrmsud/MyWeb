package com.my.order.vo;
//주문 기본정보
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class OrderInfo {
	private int orderNo;
	private String orderId;
	@JsonFormat(pattern = "yy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date orderDt;
	private List<OrderLine> lines;
}
