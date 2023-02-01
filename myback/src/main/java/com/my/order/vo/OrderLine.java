package com.my.order.vo;
import com.my.product.vo.Product;

//주문상세정보
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString
public class OrderLine {
	private int orderNo;
	//private String orderProdNo;
	private Product orderP;
	private int orderQuantity;
}
