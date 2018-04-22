import java.math.*;
import java.net.*;
import java.util.*;

public class server_java_udp {
    ArrayList<String> contents;
    String item;
    server_java_udp check;
    protected static String server_IP ;

    public static void main(String args[]) throws Exception {

    	try {
    	      InetAddress iAddress = InetAddress.getLocalHost();
    	      server_IP = iAddress.getHostAddress();
    	      System.out.println("Server IP address : " +server_IP);
    	      	}
    	      catch (UnknownHostException e) {
    	      	}
        
    	
    	byte[] rbuf = new byte[1024], sbuf = new byte[1024], sbuf1 = new byte[1024], sbuf2 = new byte[1024], sbuf3 = new byte[2048];
        DatagramSocket socket = new DatagramSocket(6782);
        System.out.println("Server ready");
        //First Packet
        while(true)
        {
        DatagramPacket rpkt = new DatagramPacket(rbuf, rbuf.length);
        socket.receive(rpkt);
        byte[] b = rpkt.getData();
        String data = new String(b,0,b.length);
        System.out.println("Packet1 Length is : " + data);
        int a = Integer.parseInt(data.trim());
       // DatagramPacket spkt = new DatagramPacket(sbuf, sbuf.length, rpkt.getAddress(), rpkt.getPort());
       // socket.send(spkt);
        
      //  String data = new String(rpkt.getData(), 0, rpkt.getLength());
        DatagramPacket rpkt1 = new DatagramPacket(sbuf,sbuf.length);
        socket.receive(rpkt1);
        String data1 = new String(rpkt1.getData());
        
        byte[] test = rpkt1.getData();
        //String data1 = new String(test,test.length);
        System.out.println("String is " + data1);
        int pkt2length = rpkt1.getLength();
        String pktlength = Integer.toString(pkt2length);
        System.out.println("Packet2 Length is : " + pktlength);
        
        if(a == pkt2length);
        else
        {
        	System.out.println("Did not receive valid expression from client. Terminating.");
        	System.exit(1);
        	
        }
        String s="ACK";
        
        
        sbuf1 = String.valueOf(s).getBytes();
        DatagramPacket spkt1 = new DatagramPacket(sbuf1, sbuf1.length, rpkt1.getAddress(), rpkt1.getPort());
        socket.send(spkt1);
        
        //InetAddress addr = rpkt.getAddress();
        //int port = rpkt.getPort();
        //String ar = new String(rpkt.getData());
        //System.out.println("Value : " + ar);
        server_java_udp go = new server_java_udp();
        String a2 = go.brackets(data1);
        System.out.println("Result: "+a2);
        
        
        //sbuf = String.valueOf(a2).getBytes();
        
        sbuf2 = String.valueOf(a2).getBytes();
        DatagramPacket spkt2 = new DatagramPacket(sbuf2, sbuf2.length, rpkt1.getAddress(), rpkt1.getPort());
        socket.send(spkt2);
        
        String result = "";
        int i;
        int p = Integer.parseInt(a2);
        for(i=1;i<=p;i++)
        {
          result+= "Socket Programming \n";
        }
        
        
        sbuf3 = String.valueOf(result).getBytes();
        DatagramPacket spkt3 = new DatagramPacket(sbuf3, sbuf3.length, rpkt1.getAddress(), rpkt1.getPort());
        socket.send(spkt3);
        }   
          
    }

	String brackets(String s) {
		
		check = new server_java_udp();
        while(s.contains(Character.toString('('))||s.contains(Character.toString(')'))){
            for(int o=0; o<s.length();o++){
                try{                                                        //i there is not sign
                    if((s.charAt(o)==')' || Character.isDigit(s.charAt(o))) //between separate brackets
                            && s.charAt(o+1)=='('){                         //or number and bracket,
                        s=s.substring(0,o+1)+"*"+(s.substring(o+1));        //it treat it as
                    }                                                       //a multiplication
                }catch (Exception ignored){}                                //ignore out of range ex
                if(s.charAt(o)==')'){                                  //search for a closing bracket
                    for(int i=o; i>=0;i--){
                        if(s.charAt(i)=='('){                          //search for a opening bracket
                            String in = s.substring(i+1,o);
                            in = check.recognize(in);
                            s=s.substring(0,i)+in+s.substring(o+1);
                            i=o=0;
                        }
                    }
                }
            }
            if(s.contains(Character.toString('('))||s.contains(Character.toString(')'))||
                    s.contains(Character.toString('('))||s.contains(Character.toString(')'))){
                System.out.println("Error: incorrect brackets placement");
                return "Error: incorrect brackets placement";
            }
        }
        s=check.recognize(s);
        return s;
		// TODO Auto-generated method stub
		//return null;
	}

	 String recognize(String s) {
		PutIt putIt = new PutIt();
        contents = new ArrayList<String>();         //holds numbers and operators
        item = "";
        for(int i=s.length()-1;i>=0;i--){           //is scan String from right to left,
            if(Character.isDigit(s.charAt(i))){     //Strings are added to list, if scan finds
                item=s.charAt(i)+item;              //a operator, or beginning of String
                if(i==0){
                    putIt.put();
                }
            	}else{
                if(s.charAt(i)=='.'){
                    item=s.charAt(i)+item;
                }else if(s.charAt(i)=='-' && (i==0 || (!Character.isDigit(s.charAt(i-1))))){
                    item=s.charAt(i)+item;          //this part should recognize
                    putIt.put();                    //negative numbers
                }else{
                    putIt.put();                //it add already formed number and
                    item+=s.charAt(i);          //operators to list
                    putIt.put();                //as separate Strings
                if(s.charAt(i)=='|'){       //add empty String to list, before "|" sign,
                        item+=" ";          //to avoid removing of any meaningful String
                        putIt.put();        //in last part of result method
                    }
                }
            }
        }
        contents = putIt.result(contents, "^", "|");    //check Strings
        contents = putIt.result(contents, "*", "/");    //for chosen
        contents = putIt.result(contents, "+", "-");    //operators
        return contents.get(0);
		
		// TODO Auto-generated method stub
		//return null;
	}

	public class PutIt {
	
		public void put() {
			if(!item.equals("")){
	            contents.add(0,item);
	            item="";
	        }
			// TODO Auto-generated method stub
			
		}
		public ArrayList<String>result(ArrayList<String> arrayList, String op1, String op2){
	        int scale = 10;                              //controls BigDecimal decimal point accuracy
	        BigDecimal result = new BigDecimal(0);
	        for(int c = 0; c<arrayList.size();c++){
	            if(arrayList.get(c).equals(op1)|| arrayList.get(c).equals(op2)){
	                if(arrayList.get(c).equals("^")){
	                    result = new BigDecimal(arrayList.get(c-1)).pow(Integer.parseInt(arrayList.get(c+1)));
	                }else if(arrayList.get(c).equals("|")){
	                    result = new BigDecimal(Math.sqrt(Double.parseDouble(arrayList.get(c+1))));
	                }else if(arrayList.get(c).equals("*")){
	                    result = new BigDecimal(arrayList.get(c-1)).multiply
	                            (new BigDecimal(arrayList.get(c+1)));
	                }else if(arrayList.get(c).equals("/")){
	                    result = new BigDecimal(arrayList.get(c-1)).divide
	                            (new BigDecimal(arrayList.get(c+1)),scale,BigDecimal.ROUND_DOWN);
	                }else if(arrayList.get(c).equals("+")){
	                    result = new BigDecimal(arrayList.get(c-1)).add(new BigDecimal(arrayList.get(c+1)));
	                }else if(arrayList.get(c).equals("-")){
	                    result = new BigDecimal(arrayList.get(c-1)).subtract(new BigDecimal(arrayList.get(c+1)));
	                }
	                try{       //in a case of to "out of range" ex
	                    arrayList.set(c, (result.setScale(scale, RoundingMode.HALF_DOWN).
	                            stripTrailingZeros().toPlainString()));
	                    arrayList.remove(c + 1);            //it replace the operator with result
	                    arrayList.remove(c-1);              //and remove used numbers from list
	                }catch (Exception ignored){}
	            }else{
	                continue;
	            }
	            c=0;                     //loop reset, as arrayList changed size
	        }
	        return arrayList;
	    }
	}
}
