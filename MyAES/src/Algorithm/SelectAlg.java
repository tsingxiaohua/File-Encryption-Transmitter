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
			return false;//�ַ���Ϊ�ջ��߿մ�  
		}  
		String[] parts=ipv4.split("\\.");//��Ϊjava doc���Ѿ�˵��, split�Ĳ�����reg, ��������ʽ, �����"|"�ָ�, ����ʹ��"\\|"  
		if(parts.length!=4){  
			return false;//�ָ����������Ͳ���4������  
		}  
		for(int i=0;i<parts.length;i++){  
			try{  
				int n=Integer.parseInt(parts[i]);  
				if(n<0 || n>255){  
					return false;//���ֲ�����ȷ��Χ��  
				}  
			}catch (NumberFormatException e) {  
				return false;//ת�����ֲ���ȷ  
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
