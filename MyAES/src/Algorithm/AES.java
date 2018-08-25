package Algorithm;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class AES extends AESMap {

	public long AES_Encrypt(String OpenPath,String SavePath,String m_Key,int Nb,int Nk)
			throws IOException
	{

		//�Զ����ƶ��ķ�ʽ��Ҫ���ܵ��ļ���
		//�Զ�����д�ķ�ʽ�򿪱������ĵ��ļ���   
		FileInputStream fp1 = new FileInputStream(OpenPath);
		FileOutputStream fp2 = new FileOutputStream(SavePath,true); 
		int Length = fp1.available();//�õ�Ҫ���ܵ��ļ��ĳ��ȣ���λbit��
		if(Length==0)return 0;
		int  leave = Length%(4*Nb);                         //��ʣ����ֿ���ֽ�����
		long rounds = Length/(4*Nb);                        //�õ�����ļ���������
		if(leave!=0)rounds++;     
		long copy_rounds = rounds;

		byte[] state = new byte[4*8]; //��Ϊ����ʱ���Ҫ���ܵ����Ŀ飬�ֽ��������飻
		byte[] copy = new byte[4*8];               //�������ж̿鴦��ʱ�Ļ�������
		int Nr=GetRounds(Nb,Nk);      //�õ����ܵ�������NbΪ��Կ����������� 
		KeyExpansion(m_Key,Nb,Nk,Nr); //���ɸ�������Կ��

		if(copy_rounds==1&&rounds==1)
		{

			if(leave==0) fp1.read(state,0,4*Nb);//���ĵĳ���ǡ�õ��ڷ��鳤�ȣ�
			else
			{
				fp1.read(state,0,leave);//���ĵĳ���С�ڰ˸��ַ���ÿ���ַ�һ�ֽڣ�
				for(int i=leave;i<4*Nb;i++)
					state[i]=0;             //�����ÿո��룻
			}

			state = Transform(ByteToChar(state),Nb,Nr);                   //���ܱ任��  
			fp2.write(state,0,4*Nb);//�����ܺ�����Ŀ�д��Ŀ���ļ���
			rounds--; 
		}
		else if(copy_rounds>1&&leave!=0)//������ĵĳ��ȴ��ڷ��鳤�����ַ������Ƿ��鳤�ȵ�������              
		{                               //ʱ����Ҫ���ж̿鴦��
			fp1.read(state,0,4*Nb);
			state = Transform(ByteToChar(state),Nb,Nr);//�ȼ�����ǰ���һ�飻
			fp2.write(state,0,leave);//������������ô�������ǰ�沿���ַ������ļ���
			int j=0;
			for(int i=leave;i<4*Nb;i++)
				copy[j++]=state[i];//�����ȼ��ܵ����ĵĺ󲿷ַ��ڶ̿�ǰ�棬����Ϊ���鳤�ȼ�ȥleave��
			fp1.read(copy,j,leave);//��ʣ�������ô������ķ��ں��棨�ǵڶ������Ŀ��е����ģ������һ�������������Ŀ飻
			copy = Transform(ByteToChar(copy),Nb,Nr);
			fp2.write(copy,0,4*Nb);
			rounds-=2;//��ͼ�����2���ˣ�ʵ����һ��ࣻ
		}
		while(rounds>0)//���´���������Ƿ�����������������
		{          
			fp1.read(state,0,4*Nb);
			state = Transform(ByteToChar(state),Nb,Nr);
			fp2.write(state,0,4*Nb);
			rounds--;
		}    
		fp1.close();//�ر�Դ�ļ���Ŀ���ļ���
		fp2.close();
		return ((copy_rounds-1)*4*Nb+leave);//�����ļ����ȣ�
	}

	public long AES_DeEncrypt(String OpenPath,String SavePath,String  m_Key, int Nb, int Nk)
			throws IOException
	{
		//�Զ����ƶ��ķ�ʽ��Ҫ�����ܵ��ļ���
		//�Զ�����д�ķ�ʽ�򿪱���⿪�������ļ���   
		FileInputStream fp1= new FileInputStream(OpenPath);
		FileOutputStream fp2= new FileOutputStream(SavePath,true); 
		int Length = fp1.available();//�õ�Ҫ���ܵ��ļ��ĳ��ȣ�
		if(Length==0)return 0;
		int  leave=Length%(4*Nb);//��ʣ����ֿ���ֽ�����
		long rounds=Length/(4*Nb);//�õ�����Ľ���������
		if(leave!=0)rounds++;     
		long copy_rounds=rounds;


		byte []state = new byte[4*8];              //����ʱ������Ŀ飻
		int Nr = GetRounds(Nb,Nk);      //�õ�����ʱѭ��������
		KeyExpansion(m_Key,Nb,Nk,Nr); //���ɸ�������Կ
		byte[] copy = new byte[32];

		if(leave!=0)//��Ҫ���ж̿鴦��
		{
			fp1.read(copy,0,leave);//�Ȱ������������ַ���������飻
			fp1.read(state,0,4*Nb);//��ȡ�����ŵ�һ�����Ŀ飻
			state = ReTransform(ByteToChar(state),Nb,Nr);          //���ܣ�
			int j=0;
			for(int i=leave;i<4*Nb;i++)        //�ѽ��ܺ������ǰ���ֺ�ǰ�������������һ�����һ�飬
				copy[i]=state[j++];            //һ����ܣ�
			copy = ReTransform(ByteToChar(copy),Nb,Nr);
			//�����ܺ������д��Ŀ���ļ���
			fp2.write(copy,0,4*Nb);
			fp2.write(state,j,leave);//������������д��Ŀ���ļ���
			rounds-=2;                         //�Ѿ���������ֽ������Լ�����
		}
		while(rounds>0)//�Ժ����Ƿ��鳤�ȵ������������Ŀ���ܣ�
		{
			fp1.read(state,0,4*Nb);//��ȡ���Ŀ飻
			copy = ReTransform(ByteToChar(state),Nb,Nr);          //���ܱ任��
			fp2.write(copy,0,4*Nb);//�����ܺ������д��Ŀ���ļ���
			rounds--;                           //������һ��
		}
		fp1.close();//�ر�Դ�ļ���Ŀ���ļ���
		fp2.close();
		return ((copy_rounds-1)*4*Nb+leave);//�����ļ�����
	}

	public void RotWord(char[]A)
	{
		char temp;
		temp=A[0];
		A[0] = A[1];
		A[1] = A[2];
		A[2] = A[3];
		A[3] = temp;
	}

	public void SubWord(char []A)
	{
		for(int i=0;i<4;i++)
			A[i]=S_BOX[A[i]]; 
	}

	public int GetRounds(int Nb, int Nk)
	{    
		switch(Nb)
		{
		case 4:switch(Nk)
		{
		case 4:return 10;
		case 6:return 12;
		case 8:return 14;
		default:return 0;
		}
		case 6:switch(Nk)
		{
		case 4:
		case 6:return 12;
		case 8:return 14;
		default:return 0;
		}
		case 8:switch(Nk)
		{
		case 4:
		case 6:
		case 8:return 14;
		default:return 0;
		}
		default:return 0;
		}
	}

	public void KeyExpansion(String m_Key,int Nb, int Nk, int Nr)
	{
		int i=0;
		for(;i<4;i++)
			for(int j=0;j<Nk;j++){
				key[i*Nk+j]=m_Key.charAt(i+j);
			}
		i=0;
		while(i<Nk)
		{
			w[i*4]=key[i*4];//��һ�еĵ������Կ�ַ���
			w[i*4+1]=key[i*4+1];
			w[i*4+2]=key[i*4+2];
			w[i*4+3]=key[i*4+3];
			i++;
		}
		i=Nk;
		while(i<Nb*(Nr+1))
		{
			char []temp = new char[4];
			temp[0]=w[(i-1)*4+0];temp[1]=w[(i-1)*4+1];
			temp[2]=w[(i-1)*4+2];temp[3]=w[(i-1)*4+3];
			if((i%Nk)==0)
			{
				RotWord(temp);
				SubWord(temp);
				for(int j=0;j<4;j++)
					temp[j]^=Rcon[((i-1)/Nk)+j];//�볣��Rcon���
			}
			else if(Nk==8&&i%Nk==4)
				SubWord(temp);
			w[i*4+0] = (char)(w[(i-Nk)*4+0]^temp[0]);
			w[i*4+1] = (char)(w[(i-Nk)*4+1]^temp[1]);
			w[i*4+2] = (char)(w[(i-Nk)*4+2]^temp[2]);
			w[i*4+3] = (char)(w[(i-Nk)*4+3]^temp[3]);
			i++;
		}    
	}

	public void SubChar(char []state,int Nb)
	{ 
		for(int i=0;i<4*Nb;i++)    
			state[i]=S_BOX[state[i]%256];

	}

	public void ShiftRows(char []state, int Nb)
	{
		char[]  t = new char [8];
		for( int r=0;r<4;r++)
		{
			for(int c=0;c<Nb;c++)t[c]= state[Nb*r+(r+c)%Nb];
			for(int c=0;c<Nb;c++)
				state[Nb*r+c]=t[c];
		}
	}

	public void  MixColumns(char[]state, int Nb)
	{
		int [] t = new int[4];
		for( int c=0;c<Nb;c++)
		{
			for(int r=0;r<4;r++)t[r] = state[Nb*r+c];
			for(int r=0;r<4;r++)
			{
				state[Nb*r+c] = (char)(Ffmul(0x02,t[r])^Ffmul(0x03,t[(r+1)%4])
						^t[(r+2)%4]^t[(r+3)%4]);
			}
		}
	}

	public int Ffmul(int A, int B)
	{

		//�������;
		if(A==0||B==0)return 0;
		A = Log[A];
		B = Log[B];
		A =(A+B)%0xff;
		//�鷴������;
		A = Log_1[A];
		return A;
	}

	public void AddRoundKey(char[]state,  int Nb,int round)
	{
		for(int c=0;c<Nb;c++,round++)
			for(int r=0;r<4;r++)
				state[r*Nb+c] = (char)(state[r*Nb+c]^w[round*4+r]);
	}

	public byte[] Transform(char[]state, int Nb,int Nr)
	{
		int round=1; 
		AddRoundKey(state,Nb,0);
		for(;round<Nr;round++)
		{
			SubChar(state,Nb);
			ShiftRows(state,Nb);
			MixColumns(state,Nb);
			AddRoundKey(state,Nb,round*Nb);
		}
		SubChar(state,Nb);
		ShiftRows(state,Nb);
		AddRoundKey(state,Nb,round*Nb);
		return CharToByte(state);
	}

	public byte[] ReTransform(char []state, int Nb,int Nr)
	{
		AddRoundKey(state, Nb,Nr*Nb);
		for(int round=Nr-1;round>=1;round--)
		{
			InvShiftRows(state,Nb);
			InvSubint(state,Nb);
			AddRoundKey(state,Nb,round*Nb);
			InvMixColumns(state,Nb);
		}
		InvShiftRows(state,Nb);
		InvSubint(state,Nb);
		AddRoundKey(state,Nb,0);
		return CharToByte(state);
	}


	public void InvSubint(char []state, int Nb)
	{
		for(int i=0;i<4*Nb;i++)
			state[i] = S_BOX_1[state[i]%256];
	}

	public void InvShiftRows(char[]state, int Nb)
	{
		char [] t = new char[8];
		for( int r=0;r<4;r++)
		{
			for(int c=0;c<Nb;c++)
				t[(c+r)%Nb] = state[r*Nb+c];
			for(int c=0;c<Nb;c++)
				state[r*Nb+c]=t[c];
		}    
	} 

	public void InvMixColumns(char []state, int Nb)
	{
		char  []t = new char[4];
		for( int c=0;c<Nb;c++)
		{
			for(int r=0;r<4;r++)t[r] = state[Nb*r+c];
			for(int r=0;r<4;r++)
			{
				state[Nb*r+c] = (char)(Ffmul(0x0e,t[r])^Ffmul(0x0b,t[(r+1)%4])
						^Ffmul(0x0d,t[(r+2)%4])^Ffmul(0x09,t[(r+3)%4]));
			}
		}
	}

	public static char[] ByteToChar(byte[] data)
	{
		char []A = new char[data.length];
		for(int i = 0;i<data.length;i++)
			A[i] = (char)data[i];
		return A;
	}
	/**
	 * This method is used to transform a array from char type to byte type.
	 * @param data a char type array.
	 * @return a byte type array.
	 */
	public static byte[]CharToByte(char[]data)
	{
		byte[] A = new byte[data.length];
		for(int i = 0;i<data.length;i++)
			A[i] = (byte)data[i];
		return A;
	}
}


