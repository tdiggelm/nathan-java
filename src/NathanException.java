/*
 * Copyright (c) 2014 Thomas Diggelmann, ai-one inc.
 * All rights reserved.
 */

public class NathanException extends Exception {
	private static final long serialVersionUID = 1L;
	public NathanException() { super(); }
	public NathanException(String message) { super(message); }
	public NathanException(String message, Throwable cause) { super(message, cause); }
	public NathanException(Throwable cause) { super(cause); }
}