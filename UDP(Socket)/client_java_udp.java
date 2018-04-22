import java.net.*;
import java.util.Scanner;
import java.io.*;
public class client_java_udp {
public static void main(String args[]) throws Exception {

	Scanner reader = new Scanner(System.in);  // Reading from System.in
	System.out.print("Enter server name or IP address: ");
	String server_IP = reader.next();
	System.out.print("Enter port: ");
	int n = reader.nextInt();
	if((n<0) || (n>65535))
		{
			System.out.println("Invalid port number. Terminating.");
			System.exit(1);
		}
	
	try{
	byte[] rbuf = new byte[1024], sbuf = new byte[1024], rbuf1 = new byte[1024], rbuf2 = new byte[2048];
	BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
	DatagramSocket socket = new DatagramSocket();
	InetAddress addr = InetAddress.getByName(server_IP);
	
	//send length
	//length lines
		System.out.print("Enter Expression: ");
		Scanner reader1 = new Scanner(System.in);
		String pktlength1 = reader1.nextLine();
		String pktlength = Integer.toString(pktlength1.length());
		System.out.println("Length :- " +pktlength);
		byte[] spkt1 = pktlength.getBytes();
		DatagramPacket spktlength = new DatagramPacket(spkt1,spkt1.length,addr,n);
		socket.send(spktlength);
	
	//get an integer from user
		//System.out.print("Enter expression: ");
		//String data = fromUser.readLine();
		sbuf = pktlength1.getBytes();
		DatagramPacket spkt = new DatagramPacket(sbuf, sbuf.length, addr, n);
	//send it to server
		socket.send(spkt);
		//System.out.println("Sent to server: " + data);
	DatagramPacket rpkt = new DatagramPacket(rbuf, rbuf.length);
	//retrieve result
	socket.receive(rpkt);
	String data = new String(rpkt.getData(), 0, rpkt.getLength());
	//String a = new String(rpkt.getData());
	System.out.println("Received from server: " +data);
	
	DatagramPacket rpkt1 = new DatagramPacket(rbuf1, rbuf1.length);
	socket.receive(rpkt1);
	String data1 = new String(rpkt1.getData(), 0, rpkt1.getLength());
	System.out.println("Received from server: " +data1);
	
	DatagramPacket rpkt2 = new DatagramPacket(rbuf2, rbuf2.length);
	socket.receive(rpkt2);
	String data2 = new String(rpkt2.getData(), 0, rpkt2.getLength());
	System.out.println("Received from server: " +data2);
	//close the socket
	socket.close();
	}
	catch(Exception e)
	{
		System.out.println("Could not connect to server. Terminating.");
		System.exit(1);
	}
	
}
}