package com.my.exception;

public class AddException extends Exception {

	public AddException() {
		super(); //부모생성자호출

	}

	public AddException(String message) {
		super(message);
		
	}
}
