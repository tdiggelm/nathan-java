/*
 * Copyright (c) 2014 Thomas Diggelmann, ai-one inc.
 * All rights reserved.
 */

public class NathanStatus {
	String message;
	public NathanStatus(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return this.getMessage();
	}
}
