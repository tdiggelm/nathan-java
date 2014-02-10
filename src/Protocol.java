/*
 * Copyright (c) 2014 Thomas Diggelmann, ai-one inc.
 * All rights reserved.
 */

import java.io.IOException;
import java.util.ArrayList;

public final class Protocol {
	public static Object recv_obj(NathanInputStream in) throws IOException, NathanException {
		String line = in.readLine();
		char cb = line.charAt(0);
		
		switch(cb) {
		case '@': {
			int len = Integer.parseInt(line.substring(1));
			byte[] read = new byte[len];
			int offset = 0;
			while (offset < len) {
				int size = in.read(read, offset, (len - offset));
				if (size == -1)
				    throw new IOException("Server connection is already closed.");
					offset += size;
			}
			in.readByte(); in.readByte();
			return new String(read);
		}
		case '*': {
			int len = Integer.parseInt(line.substring(1));
			ArrayList<Object> arr = new ArrayList<Object>(len);
			for (int i = 0; i < len; i++) arr.add(recv_obj(in));
			return arr;
		}
		case '+': {
			return new NathanStatus(line.substring(1));
		}
		case '-': {
			throw new NathanException(line.substring(1));
		}
		case ':': {
			return Integer.parseInt(line.substring(1));
		}
		default: {
			throw new IOException("received incorrect protocol.");
		}
		}
	}
}
