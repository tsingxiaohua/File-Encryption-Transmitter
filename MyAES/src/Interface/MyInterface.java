package Interface;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import Algorithm.AES;
import Algorithm.FileTransferClient;
import Algorithm.FileTransferServer;
import Algorithm.SelectAlg;

import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.JTextField.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.channels.SelectableChannel;
import java.io.*;


public class MyInterface extends JFrame{
	TitledBorder titledBorder1;
	TitledBorder titledBorder2;
	JRadioButton encry = new JRadioButton();
	JRadioButton decry = new JRadioButton();
	JLabel label_2 = new JLabel();
	JButton sourcefile = new JButton();
	JButton aimfile = new JButton();
	JTextField sourcepath = new JTextField();
	JTextField aimpath = new JTextField();
	JComboBox groups = new JComboBox();
	JComboBox lengths = new JComboBox();
	JLabel label_5 = new JLabel();
	JLabel label_4 = new JLabel();
	JLabel label_3 = new JLabel();
	JLabel label_6 = new JLabel();
	final JLabel label_7 = new JLabel("\u670D\u52A1\u5668ip\u5730\u5740\uFF1A");
	final JLabel label_8 = new JLabel("\u670D\u52A1\u5668\u7AEF\u53E3\uFF1A");
	JPasswordField passwd = new JPasswordField();
	JButton jButtonEnsure = new JButton();
	final JCheckBox localhost = new JCheckBox("\u672C\u5730\u6A21\u5F0F");
	JRadioButton client = new JRadioButton("   \u5BA2\u6237\u7AEF");
	JRadioButton server = new JRadioButton("   \u670D\u52A1\u5668");
	FileTransferClient fileTransferClient;
	FileTransferServer fileTransferServer;

	AES myAES = new AES();
	String encryfile,savefile,Epassword,safile;
	String PathName=null;
	String DirFileName=null;

	String filename,savefilename;
	String exname="";
	String temp="";
	String password="";

	String sNb,sNr;
	int Nb,Nr;
	private JTextField ip;
	private JTextField port;
	private final JPanel panel_1 = new JPanel();
	private final JPanel panel = new JPanel();
	private final JTextField txtaes = new JTextField();
	private final JPanel panel_2 = new JPanel();
	private final JLabel label_1 = new JLabel("\u8F6F\u4EF6\u529F\u80FD\uFF1A");



	public MyInterface() {
		setResizable(false);
		txtaes.setFont(new Font("Dialog", Font.BOLD, 20));
		txtaes.setText("\u672C\u8F6F\u4EF6\u4F7F\u7528AES\u6587\u4EF6\u52A0\u5BC6\uFF0C\u53EF\u5B9E\u73B0\u672C\u5730\u548C\u4F20\u8F93\u52A0\u5BC6");
		txtaes.setBackground(Color.LIGHT_GRAY);
		txtaes.setHorizontalAlignment(SwingConstants.CENTER);
		txtaes.setEditable(false);
		txtaes.setBounds(14, 12, 550, 35);
		txtaes.setColumns(10);
		try {
			display();
			action();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void display() throws Exception {
		/*try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/


		int WIDTH = 480; 
		int HEIGHT = 350; 


		titledBorder1 = new TitledBorder("");
		titledBorder2 = new TitledBorder("");
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("\u6587\u4EF6\u52A0\u5BC6\u4F20\u8F93\u5668");
		encry.setFont(new Font("Dialog", Font.PLAIN, 16));
		encry.setText("   ����");
		encry.setBounds(new Rectangle(134, 101, 96, 25));
		encry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				label_7.setEnabled(true);
				label_8.setEnabled(true);
				ip.setEnabled(true);
				port.setEnabled(true);
				localhost.setSelected(false);
				localhost.setEnabled(true);
			}
		});

		label_2.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_2.setText("\u6267\u884C\u64CD\u4F5C\uFF1A");
		label_2.setBounds(new Rectangle(26, 99, 86, 28));
		sourcefile.setBounds(new Rectangle(21, 191, 98, 29));
		sourcefile.setFont(new Font("Dialog", Font.PLAIN, 16));
		sourcefile.setText("Դ�ļ�");
		aimfile.setEnabled(false);
		aimfile.setBounds(new Rectangle(21, 237, 98, 29));
		aimfile.setFont(new Font("Dialog", Font.PLAIN, 16));
		aimfile.setText("Ŀ���ļ�");
		sourcepath.setHorizontalAlignment(SwingConstants.CENTER);
		sourcepath.setEditable(false);
		sourcepath.setFont(new java.awt.Font("Dialog", 0, 12));
		sourcepath.setText("");
		sourcepath.setBounds(new Rectangle(133, 192, 423, 26));
		aimpath.setHorizontalAlignment(SwingConstants.CENTER);
		aimpath.setEditable(false);
		aimpath.setFont(new java.awt.Font("Dialog", 0, 12));
		aimpath.setText("");
		aimpath.setBounds(new Rectangle(133, 238, 423, 26));
		lengths.setFont(new Font("Dialog", Font.PLAIN, 15));
		lengths.setBounds(new Rectangle(372, 135, 98, 28));
		label_3.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_3.setText("\u52A0\u5BC6\u5F3A\u5EA6\uFF1A");
		label_3.setBounds(new Rectangle(27, 141, 85, 17));
		label_6.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_6.setText("�������룺");
		label_6.setBounds(new Rectangle(29, 284, 86, 28));
		passwd.setHorizontalAlignment(SwingConstants.CENTER);
		passwd.setFont(new java.awt.Font("Dialog", 0, 12));
		passwd.setText("");
		passwd.setBounds(new Rectangle(133, 281, 423, 30));
		jButtonEnsure.setBounds(new Rectangle(21, 373, 535, 35));
		jButtonEnsure.setFont(new Font("Dialog", Font.PLAIN, 16));
		jButtonEnsure.setText("\u8FD0\u884C");
		this.getContentPane().add(label_2, null);
		this.getContentPane().add(label_3, null);
		this.getContentPane().add(sourcefile, null);
		this.getContentPane().add(sourcepath, null);
		this.getContentPane().add(aimfile, null);
		this.getContentPane().add(aimpath, null);
		this.getContentPane().add(passwd, null);
		this.getContentPane().add(lengths, null);
		lengths.addItem("128");
		lengths.addItem("192");
		lengths.addItem("256");
		this.getContentPane().add(label_6, null);
		this.getContentPane().add(jButtonEnsure, null);

		ButtonGroup group = new ButtonGroup();
		group.add(encry);
		encry.setSelected(true);
		this.getContentPane().add(encry, null);


		label_7.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_7.setBounds(21, 328, 113, 26);
		getContentPane().add(label_7);

		ip = new JTextField();
		ip.setHorizontalAlignment(SwingConstants.CENTER);
		ip.setBounds(133, 326, 120, 28);
		getContentPane().add(ip);
		ip.setColumns(10);

		port = new JTextField();
		port.setHorizontalAlignment(SwingConstants.CENTER);
		port.setBounds(368, 325, 70, 28);
		getContentPane().add(port);
		port.setColumns(10);


		label_8.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_8.setBounds(267, 331, 98, 18);
		getContentPane().add(label_8);


		localhost.setFont(new Font("����", Font.PLAIN, 16));
		localhost.setBounds(455, 326, 101, 27);
		getContentPane().add(localhost);
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(14, 54, 550, 120);

		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		ButtonGroup group1 = new ButtonGroup();

		client.setFont(new Font("Dialog", Font.PLAIN, 16));
		client.setBounds(120, 10, 96, 27);
		client.setSelected(true);


		client.addActionListener(new ActionListener(){ //���嵥ѡ�򴥷��¼�

			public void actionPerformed(ActionEvent e){


				JOptionPane.showMessageDialog(null,"�ͻ���ģʽ������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				localhost.setSelected(false);
				label_2.setEnabled(true);
				label_3.setEnabled(true);
				label_4.setEnabled(true);
				label_5.setEnabled(true);
				label_6.setEnabled(true);
				label_7.setEnabled(true);
				label_8.setEnabled(true);
				sourcefile.setEnabled(true);
				//aimfile.setEnabled(true);
				passwd.setEnabled(true);
				groups.setEnabled(true);
				lengths.setEnabled(true);
				ip.setEnabled(true);
				port.setEnabled(true);
				encry.setEnabled(true);
				encry.setSelected(true);
				decry.setEnabled(true);
				localhost.setEnabled(true);//���ؼ��ܵĻ�����Ҫip�Ͷ˿ںͱ��ؼ��ܹ���
			}
		});

		group1.add(client);


		server.setFont(new Font("Dialog", Font.PLAIN, 16));
		group1.add(server);


		server.addActionListener(new ActionListener(){ //���嵥ѡ�򴥷��¼�

			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,"������ģʽ������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				label_2.setEnabled(false);
				label_3.setEnabled(false);
				label_4.setEnabled(false);
				label_5.setEnabled(false);
				label_6.setEnabled(false);
				label_7.setEnabled(false);
				label_8.setEnabled(true);
				groups.setEnabled(false);
				lengths.setEnabled(false);
				passwd.setEnabled(false);
				sourcefile.setEnabled(false);
				//aimfile.setEnabled(false);
				ip.setEnabled(false);
				port.setEnabled(true);
				encry.setEnabled(false);
				decry.setEnabled(false);
				encry.setEnabled(false);
				localhost.setEnabled(false);//���ؼ��ܵĻ�����Ҫip�ͼ��ܹ���,ֻ��Ҫ�˿�
			}
		});

		panel_1.add(client);
		label_1.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_1.setBounds(13, 12, 80, 23);

		panel_1.add(label_1);
		decry.setBounds(360, 46, 96, 25);
		panel_1.add(decry);
		decry.setFont(new Font("Dialog", Font.PLAIN, 16));
		decry.setText("   ����");
		group.add(decry);

		decry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				label_7.setEnabled(false);
				label_8.setEnabled(false);
				ip.setEnabled(false);
				port.setEnabled(false);
				localhost.setSelected(true);
				localhost.setEnabled(false);
			}
		});

		groups.setBounds(119, 81, 97, 28);
		panel_1.add(groups);
		groups.setFont(new Font("Dialog", Font.PLAIN, 15));
		groups.addItem("128");
		groups.addItem("192");
		groups.addItem("256");
		label_4.setBounds(232, 82, 70, 23);
		panel_1.add(label_4);
		label_4.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_4.setText("�����С");
		label_5.setBounds(464, 82, 72, 23);
		panel_1.add(label_5);
		label_5.setFont(new Font("Dialog", Font.PLAIN, 16));
		label_5.setText("��Կ����");
		server.setBounds(360, 11, 96, 27);

		panel_1.add(server);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(14, 182, 550, 180);

		getContentPane().add(panel);

		getContentPane().add(txtaes);
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(14, 368, 550, 43);

		getContentPane().add(panel_2);
		localhost.addActionListener(new ActionListener(){ //���帴ѡ�򴥷��¼�

			public void actionPerformed(ActionEvent e){
				if(localhost.isSelected()){
					JOptionPane.showMessageDialog(null, "����ģʽ������", "��ʾ", JOptionPane.INFORMATION_MESSAGE );
					label_7.setEnabled(false);
					label_8.setEnabled(false);
					ip.setEnabled(false);
					port.setEnabled(false);   //���ؼ��ܵĻ�����Ҫip�Ͷ˿�
				}
				else{
					JOptionPane.showMessageDialog(null, "����ģʽ�رգ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE );
					label_7.setEnabled(true);
					label_8.setEnabled(true);
					ip.setEnabled(true);
					port.setEnabled(true);   //������ܵĻ�����Ҫip�Ͷ˿�
				}
			}
		});


		this.setSize(584,453);
		Toolkit tk = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = tk.getScreenSize(); 
		this.setLocation((screenSize.width - WIDTH)/2, (screenSize.height - HEIGHT)/2); 
	}

	public void action(){
		sourcefile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				FileDialog fg=new FileDialog(MyInterface.this,"ѡ��Ҫ�������ļ�",FileDialog.LOAD);
				fg.show();

				DirFileName=fg.getFile();
				filename=fg.getDirectory()+fg.getFile();
				sourcepath.setText(filename);
				//	jTFtfile.setText(filename+".aes");

				if(encry.isSelected()){
					temp=filename.substring(filename.length()-4);
					aimpath.setText(filename+".aes");
				}	 
				else if(decry.isSelected())
				{
					aimpath.setText(filename.substring(0,filename.length()-4));
				}
				if(fg.getFile()==null){  //���ѡ���ļ�ʧ�ܣ���Դ�ļ�·���ÿ�
					sourcepath.setText("");
					aimpath.setText("");
				}
			}

		});

		aimfile.addActionListener(new ActionListener(){//�����ļ�

			public void actionPerformed(ActionEvent e){

				FileDialog fgs=new FileDialog(MyInterface.this,"����Ϊ",FileDialog.SAVE);

				fgs.show();
				savefilename=fgs.getDirectory()+fgs.getFile();
				aimpath.setText(savefilename+".aes");
				if(fgs.getFile()==null){  //���ѡ���ļ�ʧ�ܣ���Ŀ���ļ�·���ÿ�
					aimpath.setText("");
				}
			}
		});

		/* 	 	jCBNb.addActionListener(new ActionListener(){        //�������ǿ��
  	 		public void actionPerformed(ActionEvent e){
  	 				sNb=(String)jCBNb.getSelectedItem();
  	 				 Nb=Integer.parseInt(sNb);  
  	 				 System.out.print(Nb); 


  	 			}  	 		
  	 		});

  	 	 jCBNk.addActionListener(new ActionListener(){        //������Կ����
  	 		public void actionPerformed(ActionEvent e){
  	 				sNr=(String)jCBNb.getSelectedItem();
  	 				Nr=Integer.parseInt(sNr);	

  	 			}  	 		
  	 		});
		 */

		jButtonEnsure.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				sNb=(String)groups.getSelectedItem();
				Nb=Integer.parseInt(sNb)/32;         //��ȡ���ķ��鳤��

				sNr=(String)groups.getSelectedItem();//�����Կ����
				Nr=Integer.parseInt(sNr)/32;
				password=passwd.getText();  //��ȡ����
				encryfile=sourcepath.getText();       //�����ļ�·��    
				safile=aimpath.getText();          //�ļ�����·��
				if(client.isSelected()){
					if(DirFileName==null){
						JOptionPane.showMessageDialog(null, "Դ�ļ�����Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE );
						return;
					}
					if(passwd.getText().equals("")){
						JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE );
						return;
					}
					if(groups.getSelectedItem().toString()=="128" && passwd.getText().length()<8){
						JOptionPane.showMessageDialog(null, "���볤�Ȳ��ܵ���8λ��", "����", JOptionPane.ERROR_MESSAGE );
						return;
					}
					if(groups.getSelectedItem().toString()=="192" && passwd.getText().length()<10){
						JOptionPane.showMessageDialog(null, "���볤�Ȳ��ܵ���10λ��", "����", JOptionPane.ERROR_MESSAGE );
						return;
					}
					if(groups.getSelectedItem().toString()=="256" && passwd.getText().length()<12){
						JOptionPane.showMessageDialog(null, "���볤�Ȳ��ܵ���12λ��", "����", JOptionPane.ERROR_MESSAGE );
						return;
					}
					final SelectAlg selectAlg = new SelectAlg();

					if(localhost.isSelected()){
						new Thread(){
							public void run(){
								//���ؼ���

								if(encry.isSelected()){
									if(aimpath.getText().endsWith(".aes") && (sourcepath.getText().endsWith(".txt") 
											|| sourcepath.getText().endsWith(".doc") 
											|| sourcepath.getText().endsWith(".docx") 
											|| sourcepath.getText().endsWith(".zip") 
											|| sourcepath.getText().endsWith(".rar")
											|| sourcepath.getText().endsWith(".jpg")
											|| sourcepath.getText().endsWith(".png"))){
										try{
											myAES.AES_Encrypt(encryfile,safile,password,Nb,Nr);
											File f=new File(encryfile);
											f.delete();
											JOptionPane.showMessageDialog(null,"���ܳɹ���","��ʾ",JOptionPane.INFORMATION_MESSAGE);
											sourcepath.setText("");
											aimpath.setText("");  //Դ�ļ���Ŀ���ļ�·���ÿ�
											passwd.setText(""); //��д�������ÿ�
										}
										catch(IOException a){
											JOptionPane.showMessageDialog(null,"Դ�ļ������ڣ�","����",JOptionPane.ERROR_MESSAGE);
											return; 	 			
										}
									}
									else{
										JOptionPane.showMessageDialog(null,"�ļ���ʽ����","����",JOptionPane.ERROR_MESSAGE);
										return;
									}
								}    
								else if(decry.isSelected()){//����
									new Thread(){
										public void run() {
											if(sourcepath.getText().endsWith(".aes") && (aimpath.getText().endsWith(".txt") 
													|| aimpath.getText().endsWith(".doc") 
													|| aimpath.getText().endsWith(".docx") 
													|| aimpath.getText().endsWith(".zip") 
													|| aimpath.getText().endsWith(".rar")
													|| aimpath.getText().endsWith(".jpg")
													|| aimpath.getText().endsWith(".png"))){
												try{
													myAES.AES_DeEncrypt(encryfile,safile,password,Nb,Nr);
													File f=new File(encryfile);
													f.delete();
													JOptionPane.showMessageDialog( null,"���ܳɹ���","��ʾ",JOptionPane.INFORMATION_MESSAGE);
												}
												catch(IOException a){
													JOptionPane.showMessageDialog(null,"Դ�ļ������ڣ�","����",JOptionPane.ERROR_MESSAGE);
													return;
												}
											}
											else {
												JOptionPane.showMessageDialog(null,"�ļ���ʽ����","����",JOptionPane.ERROR_MESSAGE);
												return;
											}


										}
									}.start();
								}

							}
						}.start();
					}

					else{
						new Thread(){
							public void run(){
								//���ܺ��䵽��������
								if(!sourcepath.getText().equals("")){
									if(aimpath.getText().endsWith(".aes") && (sourcepath.getText().endsWith(".txt") 
											|| sourcepath.getText().endsWith(".doc") 
											|| sourcepath.getText().endsWith(".docx") 
											|| sourcepath.getText().endsWith(".zip") 
											|| sourcepath.getText().endsWith(".rar")
											|| sourcepath.getText().endsWith(".jpg")
											|| sourcepath.getText().endsWith(".png"))){
										if(ip.getText().equals("") && !localhost.isSelected()){ //����
											JOptionPane.showMessageDialog(null, "������IP����Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE );
											return;
										}
										if(port.getText().equals("")){
											JOptionPane.showMessageDialog(null, "�������˿ڲ���Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE );
											return;
										}
										if(!selectAlg.Isipv4(ip.getText()) && !localhost.isSelected()){
											JOptionPane.showMessageDialog(null, "������IP��ַ��ʽ����", "����", JOptionPane.ERROR_MESSAGE );
											return;
										}
										if(selectAlg.isNumeric(port.getText())){
											if(port.getText().length()>5 || port.getText().length()<4){
												JOptionPane.showMessageDialog(null, "�������˿�ֵֻ����1024~65535", "����", JOptionPane.ERROR_MESSAGE );
												return;
											}
											else {
												if((Integer.parseInt(port.getText())>65535 || Integer.parseInt(port.getText())<1024 )){
													JOptionPane.showMessageDialog(null, "�������˿�ֵֻ����1024~65535", "����", JOptionPane.ERROR_MESSAGE );
													return;
												}
											}
										}
										else{
											JOptionPane.showMessageDialog(null, "�������˿�ֵ���Ϸ�", "����", JOptionPane.ERROR_MESSAGE );
											return;
										}
										if(port.getText().equals("") && !localhost.isSelected()){
											JOptionPane.showMessageDialog(null, "�������˿ڲ���Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE );
											return;
										}
									//	if(!selectAlg.test(ip.getText(),Integer.parseInt(port.getText()))){
									//		JOptionPane.showMessageDialog(null,"���ӷ�����ʧ�ܣ�","����",JOptionPane.ERROR_MESSAGE);
										//	return;
										//}
										try{
											myAES.AES_Encrypt(encryfile,safile,password,Nb,Nr);
											File f=new File(encryfile);
											f.delete();
											final File file1 =new File(safile);
											new Thread(){
												public void run() {
													try {
														fileTransferClient = new FileTransferClient(ip.getText(),Integer.parseInt(port.getText()));
														try {
															fileTransferClient.sendFile(file1);

														} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
															System.out.println("1");

														} // �����ļ�
													} catch (NumberFormatException e) {
														// TODO Auto-generated catch block
														//System.out.println("2");
														e.printStackTrace();
													} catch (UnknownHostException e1) {
														// TODO Auto-generated catch block
														//System.out.println("3");
														e1.printStackTrace();
													} catch (IOException e1) {
														// TODO Auto-generated catch block
														JOptionPane.showMessageDialog(null, "���ӷ�����ʧ�ܣ�", "����", JOptionPane.ERROR_MESSAGE );
														//e1.printStackTrace();
														return;
													}

													JOptionPane.showMessageDialog(null,"���ܴ���ɹ���","��ʾ",JOptionPane.INFORMATION_MESSAGE);
													sourcepath.setText("");
													aimpath.setText("");  //Դ�ļ���Ŀ���ļ�·���ÿ�
													passwd.setText(""); //��д�������ÿ�
												}
											}.start();
										}
										catch(IOException a){
											JOptionPane.showMessageDialog(null,"Դ�ļ������ڣ�","����",JOptionPane.ERROR_MESSAGE); 	
											return;
										}
									}
									else{
										JOptionPane.showMessageDialog(null,"�ļ���ʽ����","����",JOptionPane.ERROR_MESSAGE);
										return;
									}
								}
								else {
									JOptionPane.showMessageDialog(null,"Դ�ļ�����Ϊ�գ�","����",JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
						}.start();
					}
				}

				else if(server.isSelected()){  //�������˽����ļ�
					if(port.getText().equals("")){
						JOptionPane.showMessageDialog(null, "�������˿ڲ���Ϊ�գ�", "����", JOptionPane.ERROR_MESSAGE );
						return;
					}
					SelectAlg selectAlg = new SelectAlg();
					if(selectAlg.isNumeric(port.getText())){
						if(port.getText().length()>5 || port.getText().length()<4){
							JOptionPane.showMessageDialog(null, "�������˿�ֵֻ����1024~65535", "����", JOptionPane.ERROR_MESSAGE );
							return;
						}
						else {
							if((Integer.parseInt(port.getText())>65535 || Integer.parseInt(port.getText())<1024 )){
								JOptionPane.showMessageDialog(null, "�������˿�ֵֻ����1024~65535", "����", JOptionPane.ERROR_MESSAGE );
								return;
							}
						}
					}
					else{
						JOptionPane.showMessageDialog(null, "�������˿���ֵ���Ϸ�", "����", JOptionPane.ERROR_MESSAGE );
						return;
					}

					Thread thread = new Thread(){
						public void run(){
							int count=0;
							try { 
								try {
									//if(count)
									fileTransferServer = new FileTransferServer(Integer.parseInt(port.getText())); // ���������  
									JOptionPane.showMessageDialog(null, "��������ʼ���У����������ݣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE );
									/*if(JOptionPane.showConfirmDialog(new JFrame().getContentPane(), "�����������ݣ�", "��ʾ",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)!=0){
										System.out.println("11111111111");
										JOptionPane.showConfirmDialog(new JFrame().getContentPane(), "�ô�����δ���գ�", "��ʾ",JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
										return;
									}*/
									fileTransferServer.load();
									count++;
								} catch (Exception e) {  
									JOptionPane.showMessageDialog(null, "�ö˿����з�������ʹ�ã�", "����", JOptionPane.ERROR_MESSAGE );
									//return;
								}

							} catch (Exception e1) {  
								e1.printStackTrace();

							}
						}
					};
					thread.start();
				}
			}


		});

	}

	public static void main(String args[]){

		MyInterface fp=new MyInterface();
		fp.show();
		//JOptionPane.showMessageDialog(null,"�ͻ���ģʽ������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
	}
}