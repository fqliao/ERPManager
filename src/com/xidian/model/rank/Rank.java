package com.xidian.model.rank;

import java.time.LocalDateTime;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Rank implements Comparable<Rank>{

	private final IntegerProperty id;
	private final StringProperty rank;
	private final IntegerProperty productNum;
	private final IntegerProperty productPrice;
	private final IntegerProperty productSum;
	private final StringProperty producttype;
	private final ObjectProperty<LocalDateTime> createtime;
	private final ObjectProperty<LocalDateTime> updatetime;

	public Rank() {
		this.id = new SimpleIntegerProperty();
		this.rank = new SimpleStringProperty();
		this.productNum = new SimpleIntegerProperty();
		this.productPrice = new SimpleIntegerProperty();
		this.productSum = new SimpleIntegerProperty();
		this.producttype = new SimpleStringProperty();
		this.createtime = new SimpleObjectProperty<LocalDateTime>();
		this.updatetime = new SimpleObjectProperty<LocalDateTime>();
	}

	@Override
	public int compareTo(Rank otherRank) {
		if(this == otherRank)
		{
			return 0;
		}
		if(this.productNum.get() < otherRank.productNum.get())
		{
			return -1;
		}
		else if(this.productNum.get() == otherRank.productNum.get())
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}

	@Override
	public String toString() {
		return "Rank [rank=" + rank + ", productNum=" + productNum + ", productPrice=" + productPrice + "]";
	}

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public String getRank() {
		return rank.get();
	}
	public StringProperty rankProperty(){
		return rank;
	}
	public void setRank(String rank) {
		this.rank.set(rank);
	}
	public String getProducttype() {
		return producttype.get();
	}
	public StringProperty producttypeProperty(){
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype.set(producttype);
	}
	public int getProductNum() {
		return productNum.get();
	}
	public IntegerProperty productNumProperty(){
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum.set(productNum);
	}
	public int getProductPrice() {
		return productPrice.get();
	}
	public IntegerProperty productPriceProperty(){
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice.set(productPrice);
	}
	public int getProductSum() {
		return productSum.get();
	}
	public IntegerProperty productSumProperty(){
		return productSum;
	}
	public void setProductSum(int productSum) {
		this.productSum.set(productSum);
	}
	public LocalDateTime getUpdatetime() {
		return updatetime.get();
	}
	public void setUpdatetime(LocalDateTime updatetime) {
		this.updatetime.set(updatetime);
	}
    public ObjectProperty<LocalDateTime> updatetimeProperty() {
        return updatetime;
    }
    public LocalDateTime getCreatetime() {
    	return createtime.get();
    }
    public void setCreatetime(LocalDateTime Createtime) {
    	this.createtime.set(Createtime);
    }
    public ObjectProperty<LocalDateTime> createtimeProperty() {
    	return createtime;
    }


}
