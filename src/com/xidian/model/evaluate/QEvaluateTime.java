package com.xidian.model.evaluate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDateTime;

/**考核时间实体类
 * @author lfq
 *
 */
public class QEvaluateTime {

	private IntegerProperty id;
	private StringProperty  auId;
	private StringProperty  rank;
	private StringProperty  customerName;
	private IntegerProperty price;
	private IntegerProperty productNum;
	private IntegerProperty balance; //名字不能取为qBalance，否则映射不上表中的字段！！！
	private ObjectProperty<LocalDateTime> qstartTime; //90天考核开始时间
	private ObjectProperty<LocalDateTime> qevaluatetime; //90考核时间
	private StringProperty isqevaluate;//是否90天考核

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public int getPrice() {
		return price.get();
	}
	public IntegerProperty priceProperty(){
		return price;
	}
	public int getProductNum() {
		return productNum.get();
	}
	public IntegerProperty productNumProperty(){
		return productNum;
	}
	public int getBalance() {
		return balance.get();
	}
	public IntegerProperty balanceProperty(){
		return balance;
	}

	public String getAuId() {
		return auId.get();
	}
	public StringProperty auIdProperty(){
		return auId;
	}
	public String getRank() {
		return rank.get();
	}
	public StringProperty rankProperty(){
		return rank;
	}
	public String getCustomerName() {
		return customerName.get();
	}
	public StringProperty customerNameProperty(){
		return customerName;
	}

	public String getIsqevaluate() {
		return isqevaluate.get();
	}
	public StringProperty isqevaluateProperty(){
		return isqevaluate;
	}


	public LocalDateTime getQstartTime() {
		return qstartTime.get();
	}
    public void setQstartTime(LocalDateTime qstartTime) {
        this.qstartTime = new SimpleObjectProperty<LocalDateTime>(qstartTime);
    }

    public ObjectProperty<LocalDateTime> qstartTimeProperty() {
        return qstartTime;
    }

    public LocalDateTime getQevaluatetime() {
    	return qevaluatetime.get();
    }
    public void setQevaluatetime(LocalDateTime qevaluatetime) {
    	this.qevaluatetime = new SimpleObjectProperty<LocalDateTime>(qevaluatetime);
    }

    public ObjectProperty<LocalDateTime> qevaluatetimeProperty() {
    	return qevaluatetime;
    }

	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public void setAuId(String auId) {
		this.auId= new SimpleStringProperty(auId);
	}
	public void setRank(String rank) {
		this.rank = new SimpleStringProperty(rank);
	}
	public void setCustomerName(String customerName) {
		this.customerName = new SimpleStringProperty(customerName);
	}
	public void setIsqevaluate(String isqevaluate) {
		this.isqevaluate = new SimpleStringProperty(isqevaluate);
	}
	public void setPrice(int price) {
		this.price = new SimpleIntegerProperty(price);
	}
	public void setProductNum(int productNum) {
		this.productNum = new SimpleIntegerProperty(productNum);
	}
	public void setBalance(int balance) {
		this.balance = new SimpleIntegerProperty(balance);
	}
	@Override
	public String toString() {
		return "QEvaluateTime [id=" + id + ", auId=" + auId + ", rank=" + rank + ", customerName=" + customerName
				+ ", qstartTime=" + qstartTime + ", qevaluatetime=" + qevaluatetime + ", isqevaluate=" + isqevaluate
				+ ", balance=" + balance + "]";
	}


}
