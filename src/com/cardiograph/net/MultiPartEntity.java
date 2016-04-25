package com.cardiograph.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;


/**
 * �ಿ Entity ʵ��
 * 
 * @author Michael
 *
 */

public class MultiPartEntity extends AbstractHttpEntity implements Cloneable {
	static private final String MULTIPART_CHARS = 
			"-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static private final String CRLF = "\r\n";
	static private final String EXTRA = "--";

	
	private String boundary;  //�ಿ�� ����
	private byte[] end_bytes;  //��β�ָ���
	private PrintStream printer;
	private ByteArrayOutputStream content;
	
	public MultiPartEntity()
	{
		super();

		//���� Body�ָ���
		long   t =       System.nanoTime() ^ System.currentTimeMillis();
		Random random  = new Random(t);	
		int    tabSize = MULTIPART_CHARS.length();
		StringBuffer sb= new StringBuffer();
		
		//����40λ���ಿ�ָ���
		sb.append("--------------------"); 
		for (int i=0;i< 20;i++)
		{
			sb.append(MULTIPART_CHARS.charAt(random.nextInt(tabSize)));
		}
		
		//�ϳɽ�β�ָ���
		end_bytes = ("--" + sb.toString() + "--\r\n").getBytes();
		
		//�ϳɶಿ�ָ���
		sb.append("\r\n");
		boundary=sb.toString();
		
		//Content ����
		this.contentType =  new BasicHeader("Content-Type" ,"multipart/form-data; boundary=" + boundary);
		
		//��������
		this.contentEncoding =  new BasicHeader("Content-Encoding ",HttpUtil.EncodingCharset);
		
		this.chunked = false;
		
		//��ʼ������
		content = new ByteArrayOutputStream();
		printer = new PrintStream(content);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		//�ṩ��㿽��֧��
		MultiPartEntity newobj = (MultiPartEntity) super.clone();
		
		newobj.content = new ByteArrayOutputStream();
		newobj.printer = new PrintStream(newobj.content);
		try {
			printer.write(this.content.toByteArray());
		} catch (IOException e) {
			//���� IO �쳣
		}
		
		return newobj;
	}

	@Override
	protected void finalize() throws Throwable {
		printer.close();
		super.finalize();
	}

	@Override
	public InputStream getContent() throws IOException, IllegalStateException {
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		writeTo(tmp);
		ByteArrayInputStream bais = new ByteArrayInputStream(tmp.toByteArray());
		tmp.close();
		return bais;
	}

	@Override
	public long getContentLength() {
		return content.size() + end_bytes.length;
	}

	@Override
	public boolean isRepeatable() {
		//�Ƿ�����ظ����� writeTo �� getContent
		return true;
	}

	@Override
	public boolean isStreaming() {
		//ָʾ�Ƿ����� Entity �����������һ��EOF ��ʾ����β��
		return false;
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		//���� content ��ֱ����ӽ�β�ָ�����ΪΪ�˿��Զ�ε��� 
		//getContent ��  writeTo ���Ե��ڸ÷�������ӽ�β��
		
		/*
		//�ֶ�����д������
		int block_size = 256;
		int total_bytes = content.size();
		byte[] content_bytes = content.toByteArray();
		
		for (int index = 0;index < total_bytes ;index += block_size)
		{
			if (block_size <= (total_bytes -index))
			{
				outstream.write(content_bytes,index,block_size);
			}
			else
			{
				outstream.write(content_bytes,index,total_bytes -index);
			}
		}
		*/
		outstream.write(content.toByteArray());
		outstream.write(end_bytes);
		outstream.flush();
	}

	/**
	 * �����ͨ�ֶ���
	 * @param name   �ֶ���
	 * @param value  �ֶ�ֵ
	 * @throws java.io.UnsupportedEncodingException
	 */
	public void addPart(String name,String value) throws UnsupportedEncodingException
	{
		printer.print("--"); //part ��ʼ
		printer.print(boundary);
		printer.print("Content-Disposition: form-data; name=\""); //������ͷ
		printer.print(name); //�ֶ���
		printer.print("\"\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\n"); 
		printer.print(URLEncoder.encode(value,HttpUtil.EncodingCharset)); //����
		printer.print("\r\n"); //part ����
		printer.flush();
	}
	
	/**
	 * ����ļ���
	 * @param name 		�ֶ���
	 * @param filename  �ļ���
	 * @param buffer    �ļ�����
	 * @throws java.io.IOException
	 */
	public void addPart(String name,String filename,byte[] buffer) throws IOException
	{
		printer.print("--"); //part ��ʼ
		printer.print(boundary);
		printer.print("Content-Disposition: form-data; name=\""); //������ͷ
		printer.print(name); //�ֶ���
		printer.print("\"; filename=\"");
		printer.print(filename); //�ļ�����
		printer.print("\"\r\nContent-Type: application/octet-stream\r\n\r\n"); 
		printer.write(buffer);//�ļ�����
		printer.print("\r\n"); //part ����
		printer.flush();
	}
	
}
