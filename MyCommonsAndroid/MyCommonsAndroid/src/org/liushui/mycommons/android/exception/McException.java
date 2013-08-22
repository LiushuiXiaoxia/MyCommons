package org.liushui.mycommons.android.exception;

/**
 * Title: McException.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013-5-8<br>
 * Version:v1.0
 */
public class McException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public McException() {
		super();
	}

	public McException(String message) {
		super(message);
	}
}