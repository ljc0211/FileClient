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
    String cmd = "";//从标准输入流接收字符串，放在cmd中
	Socket s;//
	BufferedReader br;//传输数据
	DataOutputStream dos;//
	DataInputStream dis;
	public client (String serName){
		
		try {
				
			boolean flag = true;
			
			while(flag){
				
				s = new Socket(serName ,8888);
			//从标准IO中获得输入的命令
				System.out.println("请输入命令：(不知道干什么就按'h'+'回车'查看帮助)");
				 br = new BufferedReader(new InputStreamReader(System.in));//将输入的命令放到BufferedReader类变量br中
			//	if ((cmd = br.readLine())!= null&&cmd.length()!=0)
				//	System.out.println("echo:"+cmd);
				cmd = br.readLine();//将br的内容读出放到cmd中
				
			//发送命令
				DataOutputStream dos = new DataOutputStream(
						new BufferedOutputStream(s.getOutputStream()));//向服务器发送相关命令,如get,put,cd,dir
				//将字符集转换成字节序列
				byte []buf = cmd.getBytes();//使用平台默认的字符集将此 String 解码为字节序列，并将结果存储到一个新的字节数组中。 
				//返回：结果字节数组
				
				
	
		//		dos.write(buf);
				dos.writeUTF(cmd);//用UTF编码将一个字符串写入基础输入流，即写到服务端的输入流
				dos.flush();//清空此数据输出流
				dos.close();//关闭输出流
				s.close();//关闭socket
				
			
			//进行对于应得操作：
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
		System.out.println("ljc`s cloud V0.1版菜单：命令使用说明");
		System.out.println("——————————————————————————————————");
		System.out.println("cd进入当前目录的子目录：先cd再回车，然后输入当前目录下的文件夹名就行(不必输入绝对路径！)");
		System.out.println("先cd再回车，输入'..'可返回上一级，最高返回E:\\bili");
		System.out.println("例：当前在E:\\aaa, aaa文件夹下有bbb文件夹，输入'bbb'，可进入E:\\aaa\\bbb。");
		System.out.println("——————————————————————————————————");
		System.out.println("dir显示当前目录项：先dir再回车，显示当前目录下所有项(没有后缀的是文件夹");
		System.out.println("mp4是视频，xml是弹幕文件，弹幕文件必须使用专门的播放器，ass是字幕文件.)");
		System.out.println("——————————————————————————————————");
		System.out.println("get下载功能：先get再回车，然后输入要下载文件的绝对路径(先用dir然后复制粘贴吧。。。)");
		System.out.println("例：当前在E:\\aaa, 要下载此目录下的bbb.flv.mp4文件，必须输入E:\\aaa\\bbb.flv.mp4");
		System.out.println("——————————————————————————————————");
		System.out.println("put上传功能：功能尚未实现，敬请期待V0.2版本");
		System.out.println("——————————————————————————————————");
		System.out.println("ljc将长期维护，感谢支持~");
		
	}




	public void get(String serName){
		//下载
		System.out.println("进入下载功能模块:");
		System.out.println("请输入下载文件的绝对路径(复制粘贴吧...)：");
		try{
			//建立连接：从标准输入中获得要下载的文件的路径放在br
			Socket s = new Socket(serName,8888);
		br = new BufferedReader(new InputStreamReader(System.in));
	    String downFile = br.readLine();//读取一个文本行。通过下列字符之一即可认为某行已终止：
	                                    //换行 ('\n')、回车 ('\r') 或回车后直接跟着换行。
	    //返回：
	    //包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null 
	    //发要下载的文件给服务器好让对方接收到要下载的文件的名字
	    dos = new DataOutputStream(
				new BufferedOutputStream(s.getOutputStream()));
		dos.writeUTF(downFile);
		dos.flush();
	    
		
		//下载文件
        dis = new DataInputStream(new BufferedInputStream(s.getInputStream()));
    
    	int bufferSize = 8192;   
        // 缓冲区   
        byte[] buf = new byte[bufferSize];   
        long passedlen = 0;   
        
        long len = 0;   
        String savePath = "E:\\";
        // 获取文件名称   路径加文件名
        savePath =  savePath+File.separator+dis.readUTF();   
        //在本地路径建一个数据流
        DataOutputStream fileOut = new DataOutputStream(   
                new BufferedOutputStream( new FileOutputStream(savePath)));   
        // 获取文件长度   
        len = dis.readLong();   

        System.out.println("文件的长度为:" + len + "  B");   
        System.out.println("开始接收文件!");   

        // 获取文件   
        while (true) {   
            int read = 0;   
            if (dis != null) {   
                read = dis.read(buf);   
            }   
            passedlen += read;   
            if (read == -1) {   
                break;   
            }   
            System.out.println("文件接收" + (100*passedlen/len) + "%");  
//            System.out.println("文件接收了" + (passedlen * 100 / len) + "%");   
            fileOut.write(buf, 0, read);   
        }   
        System.out.println("接收完成，文件存为" + savePath);   
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
			
		//从标准输入流输入要传输的文件在本地的的路径：
		br = new BufferedReader(new InputStreamReader(System.in));
		    String upFile = br.readLine();//将该要上传的文件放到upFile
		
		  //传输文件：
			   
			   dos = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));//建立一个输出流对象
	    		
	    		File file = new File(upFile);//定义File对象upFile
	    		dos.writeUTF(file.getName());//  file.getname  ()  返回由此抽象路径名表示的文件或目录的名称。
	    		//写到输出流
	    		dos.flush();//清空输出流
	    		dos.writeLong(file.length());//向输出流写入一个long类型的数据
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
		Socket s = new Socket(serName,8888);//连接
//		 br = new BufferedReader(new InputStreamReader(System.in));//
				String changedDir = in.nextLine();
				
			//发送命令
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
		
		System.out.println("以下是目录：");
		try {
			
			s = new Socket(serName,8888);//连接服务器
			dis = new DataInputStream(new BufferedInputStream(s
					.getInputStream()));//定义数据输入流

			int BUFSIZE = 8912;
			byte[] buf = new byte[BUFSIZE];
			System.out.println("chdn");
//			while (true) {
				
				int data = 0;//read用来保存从文件中读取过来的数据
				if (dis != null) {
					data= dis.read(buf);//遇到输入流的末尾，返回-1
					String str = new String(buf);
					System.out.println(str);
				}//读两次才执行跳出
//				else if (data== -1) {
//					System.out.println("跳出");
//					break;
//				}
//			}
		} catch (IOException e) {
			System.out.println("错了");
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
