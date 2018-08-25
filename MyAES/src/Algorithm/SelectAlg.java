package Algorithm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class SelectAlg {
	public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}

	public static boolean Isipv4(String ipv4){  
		if(ipv4==null || ipv4.length()==0){  
			return false;//字符串为空或者空串  
		}  
		String[] parts=ipv4.split("\\.");//因为java doc里已经说明, split的参数是reg, 即正则表达式, 如果用"|"分割, 则需使用"\\|"  
		if(parts.length!=4){  
			return false;//分割开的数组根本就不是4个数字  
		}  
		for(int i=0;i<parts.length;i++){  
			try{  
				int n=Integer.parseInt(parts[i]);  
				if(n<0 || n>255){  
					return false;//数字不在正确范围内  
				}  
			}catch (NumberFormatException e) {  
				return false;//转换数字不正确  
			}  
		}  
		return true;  
	}

	public boolean test(String ip,int port){
		Socket socket;
		try {
			socket = new Socket();
			SocketAddress socAddress = new InetSocketAddress(ip, port); 
			socket.connect(socAddress, 5000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		//socket.close();
		return true;
	}
}
