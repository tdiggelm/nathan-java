import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TestNathan {
	
    public static Socket sock;
    public static PrintWriter out;
    public static NathanInputStream in;

	public static Object recv_obj() throws IOException, NathanException {
		String line = in.readLine();
		char cb = line.charAt(0);
		
		switch(cb) {
		case '@': {
			int len = Integer.parseInt(line.substring(1));
			byte [] data = new byte[len];
			in.read(data, 0, len);
			in.readByte(); in.readByte();
			return new String(data);
		}
		case '*': {
			int len = Integer.parseInt(line.substring(1));
			ArrayList<Object> arr = new ArrayList<Object>(len);
			for (int i = 0; i < len; i++) arr.add(recv_obj());
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
	
	public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 3002;

        try {
            sock = new Socket(hostName, portNumber);
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new NathanInputStream(sock.getInputStream());
            
            // run it n-times to test parser/sync
            int trials = 1;
            
            // run the tests
            while(trials-- > 0) {
                // test exception => return NathanException
                try {
                	out.println("BLA");
                	System.out.println(recv_obj());
                } catch (NathanException nex) {
                	System.out.println(nex.getMessage());
                }
            	
                // test cadd => return int
                out.println("CADD 'ウィキペディア日本語版' 'ウィキペディア日本語版'");
                System.out.println(recv_obj());
                
                // test keys => should return unicode chars
                out.println("KEYS");
                System.out.println(recv_obj());
                
                // test ping => returns NathanStatus
                out.println("PING");
                System.out.println(recv_obj());
                
                // test echo => returns utf-8 string
                out.println("ECHO 'hello'");
                System.out.println(recv_obj());
                
                // test echo => returns utf-8 string
                out.println("ECHO 'ウィキペディア日本語版'");
                System.out.println(recv_obj());	
                
                
                out.println("CADD '123456789' 'the' 'basic' 'elements' 'of' 'ai-one BrainDocs'");
                System.out.println(recv_obj());
                out.println("CADD '123456789' 'ai-one Braindocs'");
                System.out.println(recv_obj());
                out.println("CADD '123456789' 'is' '박경랑' '지금' '서울에' '산다' 'form' 'of' 'unstructured' 'text'");
                System.out.println(recv_obj());
                out.println("CADD '123456789' 'in' '박경랑' 'our' 'technology' 'works' 'best' 'with' 'semantically-rich' " +
                		"'content' 'written' 'in' 'your' 'business' 'vernacular'");
                System.out.println(recv_obj());
                out.println("CADD '123456789' 'our' '박경랑' '감기에' '걸렸지만' '학교에' '간다' 'chinese' 'klingon' 'etc'");
                System.out.println(recv_obj());
                out.println("CADD '123456789' 'we' '박경랑으로' 'support' 'the' 'import' 'of' 'documents' 'from' 'Microsoft'" +
                		"'Word' 'Adobe' 'pdf' 'and' 'plain' 'text' 'files' 'we' 'are'" +
                		"'working' 'on' 'developing' 'connectors' 'for' 'various' 'databases' 'email' 'repositories'" +
                		"'and' 'e-discovery' 'platforms'");
                System.out.println(recv_obj());
                
                out.println("KEYW '123456789'");
                System.out.println(recv_obj());
            }
            
        } catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
	}

}
