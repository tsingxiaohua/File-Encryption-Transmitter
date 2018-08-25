package Algorithm;

import java.io.DataInputStream;  
import java.io.File;  
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;  
import java.net.ServerSocket;  
import java.net.Socket;
import java.security.spec.ECFieldF2m;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;  

/** 
 * 文件传输Server端<br> 
 * 功能说明： 
 * 
 * @author 大智若愚的小懂 
 * @Date 2016年09月01日 
 * @version 1.0 
 */  
public class FileTransferServer extends ServerSocket {  

	//private static final int SERVER_PORT = 8899; // 服务端端口  

	private static DecimalFormat df = null; 

	Socket socket ; 

	static {  
		// 设置数字格式，保留一位有效小数  
		df = new DecimalFormat("#0.0");  
		df.setRoundingMode(RoundingMode.HALF_UP);  
		df.setMinimumFractionDigits(1);  
		df.setMaximumFractionDigits(1);  
	}  

	public FileTransferServer(int port) throws IOException { 	
		super(port);  //port是服务器接收文件端口
	}  

	/** 
	 * 使用线程处理每个客户端传输的文件
	 * @throws Exception 
	 */  
	public void load() throws Exception {  
		while (true) {  
			// server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的  
			socket = this.accept(); 
			/** 
			 * 我们的服务端处理客户端的连接请求是同步进行的， 每次接收到来自客户端的连接请求后， 
			 * 都要先跟当前的客户端通信完之后才能再处理下一个连接请求。 这在并发比较多的情况下会严重影响程序的性能， 
			 * 为此，我们可以把它改为如下这种异步处理与客户端通信的方式 
			 */  
			// 每接收到一个Socket就建立一个新的线程来处理它  
			new Thread(new Task(socket)).start();  
		}  
	}  

	/** 
	 * 处理客户端传输过来的文件线程类 
	 */  
	class Task implements Runnable {

		private Socket socket;  

		private DataInputStream dis;  

		private FileOutputStream fos;  

		public Task(Socket socket) {  
			this.socket = socket;  
		}  

		@Override  
		public void run() {  
			try {
				if(JOptionPane.showConfirmDialog(new JFrame().getContentPane(), "是否接收数据？", "提示",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)!=0){
					JOptionPane.showMessageDialog(new JFrame().getContentPane(), "取消本次数据接收！", "提示",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				dis = new DataInputStream(socket.getInputStream());  
				String fileName = null;
				// 文件名和长度  
				try{
					fileName = dis.readUTF();  
				}catch(Exception e){

				}
				long fileLength = dis.readLong();  
				File directory = new File("C:\\result");  
				if(!directory.exists()) {  
					directory.mkdir();  
				}  
				File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);  
				fos = new FileOutputStream(file);  

				// 开始接收文件  
				byte[] bytes = new byte[1024];  
				int length = 0;  
				while((length = dis.read(bytes, 0, bytes.length)) != -1) {  
					fos.write(bytes, 0, length);  
					fos.flush();  
				}  
				JOptionPane.showMessageDialog(null, "本次数据接收成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ========");  
			} catch (Exception e) {  
				//e.printStackTrace();	
			} finally {  
				try {  
					if(fos != null)  
						fos.close();  
					if(dis != null)  
						dis.close();  
					socket.close();  
				} catch (Exception e) {
					e.printStackTrace();
				}  
			}  
		}  
	}  

	/** 
	 * 格式化文件大小 
	 * @param length 
	 * @return 
	 */
	private String getFormatFileSize(long length) {  
		double size = ((double) length) / (1 << 30);  
		if(size >= 1) {  
			return df.format(size) + "GB";  
		}  
		size = ((double) length) / (1 << 20);  
		if(size >= 1) {  
			return df.format(size) + "MB";  
		}  
		size = ((double) length) / (1 << 10);  
		if(size >= 1) {  
			return df.format(size) + "KB";  
		}  
		return length + "B";  
	}  

	/** 
	 * 入口 
	 * @param args 
	 */  
	/*public static void main(String[] args) {  
		try {  
			FileTransferServer server = new FileTransferServer(); // 启动服务端  
			server.load();
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	} */
}  
