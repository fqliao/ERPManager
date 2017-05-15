package com.xidian.model.order;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OrderId {

	private IntegerProperty id;
	private StringProperty  data;
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
	public StringProperty dataProperty(){
		return data;
	}
	public String getData() {
		return data.get();
	}
	public void setData(String data){
		this.data = new SimpleStringProperty(data);
	}

}
