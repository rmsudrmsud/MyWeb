package com.my.exception;

public class FindException extends Exception {

	public FindException() {
		super(); //부모생성자호출

	}

	public FindException(String message) {
		super(message);
		
	}
}
