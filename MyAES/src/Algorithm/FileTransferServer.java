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
 * �ļ�����Server��<br> 
 * ����˵���� 
 * 
 * @author �������޵�С�� 
 * @Date 2016��09��01�� 
 * @version 1.0 
 */  
public class FileTransferServer extends ServerSocket {  

	//private static final int SERVER_PORT = 8899; // ����˶˿�  

	private static DecimalFormat df = null; 

	Socket socket ; 

	static {  
		// �������ָ�ʽ������һλ��ЧС��  
		df = new DecimalFormat("#0.0");  
		df.setRoundingMode(RoundingMode.HALF_UP);  
		df.setMinimumFractionDigits(1);  
		df.setMaximumFractionDigits(1);  
	}  

	public FileTransferServer(int port) throws IOException { 	
		super(port);  //port�Ƿ����������ļ��˿�
	}  

	/** 
	 * ʹ���̴߳���ÿ���ͻ��˴�����ļ�
	 * @throws Exception 
	 */  
	public void load() throws Exception {  
		while (true) {  
			// server���Խ�������Socket����������server��accept����������ʽ��  
			socket = this.accept(); 
			/** 
			 * ���ǵķ���˴���ͻ��˵�����������ͬ�����еģ� ÿ�ν��յ����Կͻ��˵���������� 
			 * ��Ҫ�ȸ���ǰ�Ŀͻ���ͨ����֮������ٴ�����һ���������� ���ڲ����Ƚ϶������»�����Ӱ���������ܣ� 
			 * Ϊ�ˣ����ǿ��԰�����Ϊ���������첽������ͻ���ͨ�ŵķ�ʽ 
			 */  
			// ÿ���յ�һ��Socket�ͽ���һ���µ��߳���������  
			new Thread(new Task(socket)).start();  
		}  
	}  

	/** 
	 * ����ͻ��˴���������ļ��߳��� 
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
				if(JOptionPane.showConfirmDialog(new JFrame().getContentPane(), "�Ƿ�������ݣ�", "��ʾ",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)!=0){
					JOptionPane.showMessageDialog(new JFrame().getContentPane(), "ȡ���������ݽ��գ�", "��ʾ",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				dis = new DataInputStream(socket.getInputStream());  
				String fileName = null;
				// �ļ����ͳ���  
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

				// ��ʼ�����ļ�  
				byte[] bytes = new byte[1024];  
				int length = 0;  
				while((length = dis.read(bytes, 0, bytes.length)) != -1) {  
					fos.write(bytes, 0, length);  
					fos.flush();  
				}  
				JOptionPane.showMessageDialog(null, "�������ݽ��ճɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("======== �ļ����ճɹ� [File Name��" + fileName + "] [Size��" + getFormatFileSize(fileLength) + "] ========");  
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
	 * ��ʽ���ļ���С 
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
	 * ��� 
	 * @param args 
	 */  
	/*public static void main(String[] args) {  
		try {  
			FileTransferServer server = new FileTransferServer(); // ���������  
			server.load();
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	} */
}  
