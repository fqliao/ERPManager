package com.xidian.model.order;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderId {

	private IntegerProperty id;
	private StringProperty  createtime;
	private IntegerProperty counter;

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public int getCounter() {
		return counter.get();
	}
	public IntegerProperty counterProperty(){
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = new SimpleIntegerProperty(counter);
	}
	public StringProperty createtimeProperty(){
		return createtime;
	}
	public String getCreatetime() {
		return createtime.get();
	}
	public void setCreatetime(String createtime){
		this.createtime = new SimpleStringProperty(createtime);
	}

}
