/*
 * Copyright (c) 2014 Thomas Diggelmann, ai-one inc.
 * All rights reserved.
 */

import java.io.PrintWriter;
import java.net.Socket;

public class TestNathan {
	
    public static Socket sock;
    public static PrintWriter out;
    public static NathanInputStream in;

	public static void main(String[] args) {
        String hostName = "192.168.2.3";
        int portNumber = 3000;

        try {
            sock = new Socket(hostName, portNumber);
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new NathanInputStream(sock.getInputStream());
            
            // run it n-times to test parser/sync
            int trials = 100;
            
            // run the tests
            while(trials-- > 0) {
                // test exception => return NathanException
                try {
                	out.println("BLA");
                	System.out.println(Protocol.recv_obj(in));
                } catch (NathanException nex) {
                	System.out.println(nex.getMessage());
                }
            	
                // test cadd => return int
                out.println("CADD 'ウィキペディア日本語版' 'ウィキペディア日本語版'");
                System.out.println(Protocol.recv_obj(in));
                
                // test keys => should return unicode chars
                out.println("KEYS");
                System.out.println(Protocol.recv_obj(in));
                
                // test ping => returns NathanStatus
                out.println("PING");
                System.out.println(Protocol.recv_obj(in));
                
                // test echo => returns utf-8 string
                out.println("ECHO 'hello'");
                System.out.println(Protocol.recv_obj(in));
                
                // test echo => returns utf-8 string
                out.println("ECHO 'ウィキペディア日本語版'");
                System.out.println(Protocol.recv_obj(in));	
                
                out.println("CADD '123456789' 'the' 'basic' 'elements' 'of' 'ai-one BrainDocs'");
                System.out.println(Protocol.recv_obj(in));
                out.println("CADD '123456789' 'ai-one Braindocs'");
                System.out.println(Protocol.recv_obj(in));
                out.println("CADD '123456789' 'is' '박경랑' '지금' '서울에' '산다' 'form' 'of' 'unstructured' 'text'");
                System.out.println(Protocol.recv_obj(in));
                out.println("CADD '123456789' 'in' '박경랑' 'our' 'technology' 'works' 'best' 'with' 'semantically-rich' " +
                		"'content' 'written' 'in' 'your' 'business' 'vernacular'");
                System.out.println(Protocol.recv_obj(in));
                out.println("CADD '123456789' 'our' '박경랑' '감기에' '걸렸지만' '학교에' '간다' 'chinese' 'klingon' 'etc'");
                System.out.println(Protocol.recv_obj(in));
                out.println("CADD '123456789' 'we' '박경랑으로' 'support' 'the' 'import' 'of' 'documents' 'from' 'Microsoft'" +
                		"'Word' 'Adobe' 'pdf' 'and' 'plain' 'text' 'files' 'we' 'are'" +
                		"'working' 'on' 'developing' 'connectors' 'for' 'various' 'databases' 'email' 'repositories'" +
                		"'and' 'e-discovery' 'platforms'");
                System.out.println(Protocol.recv_obj(in));
                
                out.println("KEYW '123456789'");
                System.out.println(Protocol.recv_obj(in));
            }
            
            System.out.println("done");
            
        } catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
	}
}
