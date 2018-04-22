import java.io.*;
import java.net.*;
import java.util.Scanner;
public class client_java_tcp {

protected static String server_IP ;

	public static void main(String argv[]) throws Exception {

		int i;
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
		try
		{
			Socket clientEnd = new Socket(server_IP, n);
			PrintWriter toServer = new PrintWriter(clientEnd.getOutputStream(), true);
			BufferedReader fromServer = new BufferedReader(new
					InputStreamReader(clientEnd.getInputStream()));
			BufferedReader fromUser = new BufferedReader
					(new InputStreamReader(System.in));
			System.out.print("Enter expression: ");
			String a1 = fromUser.readLine();
			//send it to server
			toServer.println(a1);
			System.out.println("Sent to server: " + a1);//retrieve result
			//a = fromServer.readLine();
			//System.out.println("Received from server: " + a);
			//close the socket
			//System.out.println("Result: "+a);
			//int i;
			//int p = Integer.parseInt(a);
			//for(i=1;i<=p;i++)
			//{
			//  System.out.println("Socket Programming");
			//}
			//toClient.println(a);
			String result1 = fromServer.readLine();
			server_java_tcp go = new server_java_tcp();
			String a = go.brackets(a1);
			if (new String(a).equals(result1))
			{
				System.out.println(result1);
				for(int z=0;z<Integer.parseInt(result1);z++)
				{
					System.out.println(fromServer.readLine());
				}
				clientEnd.close();
			}
			else{
				System.out.println("Could not fetch result. Terminating.");
				System.exit(1);
			}
			//if (clientEnd == null)
			//{
			//  System.out.println("Could not connect to server. Terminating.");
			//}
			//System.out.println("Connected to localhost at port 6789");
			//get streams
			//get an integer from user
		}
		catch(Exception ex)
		{
			System.out.println("Could not connect to server. Terminating.");
			System.exit(1);
		}
	}
}
