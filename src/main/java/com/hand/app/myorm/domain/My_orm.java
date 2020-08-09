package com.hand.app.myorm.domain;

import java.sql.*;
import java.util.*;

public class My_orm {

	private java.sql.Date birthday;
	private String name;
	private Integer id;
	private Integer age;


	public java.sql.Date getBirthday(){
		return birthday;
	}
	public String getName(){
		return name;
	}
	public Integer getId(){
		return id;
	}
	public Integer getAge(){
		return age;
	}
	public void setBirthday(java.sql.Date birthday){
		this.birthday=birthday;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setAge(Integer age){
		this.age=age;
	}
}
