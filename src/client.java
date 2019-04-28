import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class client {
    String cmd = "";//�ӱ�׼�����������ַ���������cmd��
	Socket s;//
	BufferedReader br;//��������
	DataOutputStream dos;//
	DataInputStream dis;
	public client (String serName){
		
		try {
				
			boolean flag = true;
			
			while(flag){
				
				s = new Socket(serName ,8888);
			//�ӱ�׼IO�л�����������
				System.out.println("���������(��֪����ʲô�Ͱ�'h'+'�س�'�鿴����)");
				 br = new BufferedReader(new InputStreamReader(System.in));//�����������ŵ�BufferedReader�����br��
			//	if ((cmd = br.readLine())!= null&&cmd.length()!=0)
				//	System.out.println("echo:"+cmd);
				cmd = br.readLine();//��br�����ݶ����ŵ�cmd��
				
			//��������
				DataOutputStream dos = new DataOutputStream(
						new BufferedOutputStream(s.getOutputStream()));//������������������,��get,put,cd,dir
				//���ַ���ת�����ֽ�����
				byte []buf = cmd.getBytes();//ʹ��ƽ̨Ĭ�ϵ��ַ������� String ����Ϊ�ֽ����У���������洢��һ���µ��ֽ������С� 
				//���أ�����ֽ�����
				
				
	
		//		dos.write(buf);
				dos.writeUTF(cmd);//��UTF���뽫һ���ַ���д���������������д������˵�������
				dos.flush();//��մ����������
				dos.close();//�ر������
				s.close();//�ر�socket
				
			
			//���ж���Ӧ�ò�����
			if(cmd.equals("get"))
				get(serName);
			else if(cmd.equals("put"))
				put(serName);
			else if(cmd.equals("cd"))
				cd (serName);
			else if(cmd.equals("dir"))
				dir(serName);
			else if(cmd.equals("h"))
				help();
			else if(cmd.equals("quit"))
				flag = false;
			
			
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
    		/*if(dis != null){
    			try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		if(dos != null){
    			try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}*/
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    		if(s != null){
    			try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		
    	}

		System.out.println("Bye");
		
	}
	
	
	

	public void help() {
		System.out.println("ljc`s cloud V0.1��˵�������ʹ��˵��");
		System.out.println("��������������������������������������������������������������������");
		System.out.println("cd���뵱ǰĿ¼����Ŀ¼����cd�ٻس���Ȼ�����뵱ǰĿ¼�µ��ļ���������(�����������·����)");
		System.out.println("��cd�ٻس�������'..'�ɷ�����һ������߷���E:\\bili");
		System.out.println("������ǰ��E:\\aaa, aaa�ļ�������bbb�ļ��У�����'bbb'���ɽ���E:\\aaa\\bbb��");
		System.out.println("��������������������������������������������������������������������");
		System.out.println("dir��ʾ��ǰĿ¼���dir�ٻس�����ʾ��ǰĿ¼��������(û�к�׺�����ļ���");
		System.out.println("mp4����Ƶ��xml�ǵ�Ļ�ļ�����Ļ�ļ�����ʹ��ר�ŵĲ�������ass����Ļ�ļ�.)");
		System.out.println("��������������������������������������������������������������������");
		System.out.println("get���ع��ܣ���get�ٻس���Ȼ������Ҫ�����ļ��ľ���·��(����dirȻ����ճ���ɡ�����)");
		System.out.println("������ǰ��E:\\aaa, Ҫ���ش�Ŀ¼�µ�bbb.flv.mp4�ļ�����������E:\\aaa\\bbb.flv.mp4");
		System.out.println("��������������������������������������������������������������������");
		System.out.println("put�ϴ����ܣ�������δʵ�֣������ڴ�V0.2�汾");
		System.out.println("��������������������������������������������������������������������");
		System.out.println("ljc������ά������л֧��~");
		
	}




	public void get(String serName){
		//����
		System.out.println("�������ع���ģ��:");
		System.out.println("�����������ļ��ľ���·��(����ճ����...)��");
		try{
			//�������ӣ��ӱ�׼�����л��Ҫ���ص��ļ���·������br
			Socket s = new Socket(serName,8888);
		br = new BufferedReader(new InputStreamReader(System.in));
	    String downFile = br.readLine();//��ȡһ���ı��С�ͨ�������ַ�֮һ������Ϊĳ������ֹ��
	                                    //���� ('\n')���س� ('\r') ��س���ֱ�Ӹ��Ż��С�
	    //���أ�
	    //�����������ݵ��ַ������������κ�����ֹ��������ѵ�����ĩβ���򷵻� null 
	    //��Ҫ���ص��ļ������������öԷ����յ�Ҫ���ص��ļ�������
	    dos = new DataOutputStream(
				new BufferedOutputStream(s.getOutputStream()));
		dos.writeUTF(downFile);
		dos.flush();
	    
		
		//�����ļ�
        dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    
    	int bufferSize = 8192;   
        // ������   
        byte[] buf = new byte[bufferSize];   
        long passedlen = 0;   
        
        long len = 0;   
        String savePath = "E:\\";
        // ��ȡ�ļ�����   ·�����ļ���
        savePath =  savePath+File.separator+dis.readUTF();   
        //�ڱ���·����һ��������
        DataOutputStream fileOut = new DataOutputStream(   
                new BufferedOutputStream( new FileOutputStream(savePath)));   
        // ��ȡ�ļ�����   
        len = dis.readLong();   

        System.out.println("�ļ��ĳ���Ϊ:" + len + "  B");   
        System.out.println("��ʼ�����ļ�!");   

        // ��ȡ�ļ�   
        while (true) {   
            int read = 0;   
            if (dis != null) {   
                read = dis.read(buf);   
            }   
            passedlen += read;   
            if (read == -1) {   
                break;   
            }   
            System.out.println("�ļ�����" + (100*passedlen/len) + "%");  
//            System.out.println("�ļ�������" + (passedlen * 100 / len) + "%");   
            fileOut.write(buf, 0, read);   
        }   
        System.out.println("������ɣ��ļ���Ϊ" + savePath);   
        fileOut.close();   
		
		
		
		
		}catch(IOException e ){
			
			
		}
			try{
				dis.close();
			dos.close();
			s.close();
		}catch(IOException e1 ){
			
		}
		
		
		
	}
	public void put(String serName){
		System.out.println("put");
		Socket s = null;
		try{
			s = new Socket (serName,8888);
			
		//�ӱ�׼����������Ҫ������ļ��ڱ��صĵ�·����
		br = new BufferedReader(new InputStreamReader(System.in));
		    String upFile = br.readLine();//����Ҫ�ϴ����ļ��ŵ�upFile
		
		  //�����ļ���
			   
			   dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));//����һ�����������
	    		
	    		File file = new File(upFile);//����File����upFile
	    		dos.writeUTF(file.getName());//  file.getname  ()  �����ɴ˳���·������ʾ���ļ���Ŀ¼�����ơ�
	    		//д�������
	    		dos.flush();//��������
	    		dos.writeLong(file.length());//�������д��һ��long���͵�����
	    		dos.flush();
	    		
	    		dis = new DataInputStream(new BufferedInputStream(new FileInputStream(upFile)));
	    		
	    		int BUFSIZE = 8192;
	    		byte [] buf = new byte[BUFSIZE];
	    		
	    		while(true){
	    			int read = 0;
	    			if(dis != null){
	    				read = dis.read(buf);
	    			}else{
	    				System.out.println("no file founded!");
	    				break;
	    			}
	    			if (read == -1){
	    				break;
	    			}
	    			dos.write(buf, 0, read);
	    		}
	    		dos.flush();
		}catch(IOException e){
			
		}finally{
			
			try {
				dis.close();
				dos.close();
			    s.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
	}
	public void cd(String serName){
		Scanner in = new Scanner(System.in);
		System.out.println("cd");
		try{
		Socket s = new Socket(serName,8888);//����
//		 br = new BufferedReader(new InputStreamReader(System.in));//
				String changedDir = in.nextLine();
				
			//��������
				DataOutputStream dos = new DataOutputStream(
						new BufferedOutputStream(s.getOutputStream()));
		//		byte []buf = changedDir.getBytes();
				
				dos.writeUTF(changedDir);
				dos.flush();
				dos.close();
				s.close();
				
				dir(serName);
				}catch(IOException e){
					
				}
	}
	public void dir (String serName){
		
		System.out.println("������Ŀ¼��");
		try {
			
			s = new Socket(serName,8888);//���ӷ�����
			dis = new DataInputStream(new BufferedInputStream(s
					.getInputStream()));//��������������

			int BUFSIZE = 8912;
			byte[] buf = new byte[BUFSIZE];
			System.out.println("chdn");
//			while (true) {
				
				int data = 0;//read����������ļ��ж�ȡ����������
				if (dis != null) {
					data= dis.read(buf);//������������ĩβ������-1
					String str = new String(buf);
					System.out.println(str);
				}//�����β�ִ������
//				else if (data== -1) {
//					System.out.println("����");
//					break;
//				}
//			}
		} catch (IOException e) {
			System.out.println("����");
		} finally {

			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("asdf");
		}

	}
	
	public static void main(String[] args) {
		new client("127.0.0.1");

	}

}
