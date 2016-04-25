package com.cardiograph.model;

import java.util.List;
import java.util.Map;

import com.cardiograph.dao.UserDao;

import android.content.Context;

public class User {
	private Context context;
	private int id;
	private String code;
	private String pwd;
	private String name;
	
	public final static int ACTIVE=1;//¼¤»î×´Ì¬
	public final static int INACTIVE=0;//·Ç¼¤»î×´Ì¬
	
	/**
	 * ¼¤»î×´Ì¬
	 * ACTIVE ¼¤»î×´Ì¬
	 * INACTIVE ·Ç¼¤»î×´Ì¬
	 * */
	public int isState ; //¼¤»î×´Ì¬
	
	//ÓÃ»§id
	public String userId  ="";
	
	//µÇÂ¼ÓÃ»§Ãû
	public String userName="";
	
	//ÃÜÂë
	public String password;
	
	//µÇÂ¼ÁîÅÆ  
	public String token;
	
	public User() {
		super();
	}

	public User(Context context) {
		super();
		this.context = context;
	}
	
	public User(Context context, int id) {
		super();
		this.context = context;
		this.id = id;
	}

	public User(Context context, String code, String pwd)
	{
		this.context = context;
		this.code = code;
		this.pwd = pwd;
	}
	
	public User(Context context, String code, String pwd, String name) {
		super();
		this.context = context;
		this.code = code;
		this.pwd = pwd;
		this.name = name;
	}

	public User(Context context, int id, String name) {
		super();
		this.context = context;
	    this.id = id;
		this.name = name;
	}

	public User(Context context, String name) {
		super();
		this.context = context;
		this.name = name;
	}
	
	public Map<String, Object> login()
	{
		UserDao ud = new UserDao(this,context);
		return ud.login();
	}
	
	public boolean add()
	{
		UserDao ud = new UserDao(this,context);
		return ud.add();
	}
	
	public void update()
	{
		
	}
	
	public boolean delete()
	{
		UserDao ud = new UserDao(this,context);
		return ud.delete();
	}
	
	public User findUser(User us)
	{
		User user = null;
		return user;
	}
	
	public List<User> findUsers()
	{
		UserDao ud = new UserDao(this, context);
		return ud.findUsers();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
