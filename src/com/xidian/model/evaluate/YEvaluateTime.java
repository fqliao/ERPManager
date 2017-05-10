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
public class YEvaluateTime {

	private IntegerProperty id;
	private StringProperty  auId;
	private StringProperty  rank;
	private StringProperty  customerName;
	private ObjectProperty<LocalDateTime> ystartTime;
	private  ObjectProperty<LocalDateTime> yevaluatetime;
	private StringProperty isyevaluate;//是否年度考核
	private IntegerProperty xproductNum;
	private IntegerProperty sproductNum;
	private IntegerProperty xbalance;
	private IntegerProperty sbalance;
	private IntegerProperty balance;
	private IntegerProperty xspent;
	private IntegerProperty sspent;
	private IntegerProperty spent;
	private IntegerProperty profit;

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public IntegerProperty xproductNumProperty(){
		return xproductNum;
	}
	public int getXproductNum() {
		return xproductNum.get();
	}
	public IntegerProperty sproductNumProperty(){
		return sproductNum;
	}
	public int getSproductNum() {
		return sproductNum.get();
	}
	public IntegerProperty xbalanceProperty(){
		return xbalance;
	}
	public int getXbalance() {
		return xbalance.get();
	}
	public IntegerProperty sbalanceProperty(){
		return sbalance;
	}
	public int getSbalance() {
		return sbalance.get();
	}
	public IntegerProperty balanceProperty(){
		return balance;
	}
	public int getBalance() {
		return balance.get();
	}
	public IntegerProperty xspentProperty(){
		return xspent;
	}
	public int getXspent() {
		return xspent.get();
	}
	public IntegerProperty sspentProperty(){
		return sspent;
	}
	public int getSspent() {
		return sspent.get();
	}
	public IntegerProperty spentProperty(){
		return spent;
	}
	public int getSpent() {
		return spent.get();
	}
	public IntegerProperty profitProperty(){
		return profit;
	}
	public int getProfit() {
		return profit.get();
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


	public String getIsyevaluate() {
		return isyevaluate.get();
	}
	public StringProperty isyevaluateProperty(){
		return isyevaluate;
	}

    public void setYevaluatetime(LocalDateTime yevaluatetime) {
        this.yevaluatetime = new SimpleObjectProperty<LocalDateTime>(yevaluatetime);
    }

    public ObjectProperty<LocalDateTime> yevaluatetimeProperty() {
        return yevaluatetime;
    }
    public LocalDateTime getyevaluatetime() {
    	return yevaluatetime.get();
    }

    public LocalDateTime getYstartTime() {
    	return ystartTime.get();
    }
    public void setYstartTime(LocalDateTime ystartTime) {
    	this.ystartTime = new SimpleObjectProperty<LocalDateTime>(ystartTime);
    }

    public ObjectProperty<LocalDateTime> ystartTimeProperty() {
    	return ystartTime;
    }

	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	@Override
	public String toString() {
		return "YEvaluateTime [id=" + id + ", auId=" + auId + ", rank=" + rank + ", customerName=" + customerName
				+ ", ystartTime=" + ystartTime + ", yevaluatetime=" + yevaluatetime + ", isyevaluate=" + isyevaluate
				+ ", xproductNum=" + xproductNum + ", sproductNum=" + sproductNum + ", xbalance=" + xbalance
				+ ", sbalance=" + sbalance + ", balance=" + balance + ", xspent=" + xspent + ", sspent=" + sspent
				+ ", spent=" + spent + ", profit=" + profit + "]";
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
	public void setIsyevaluate(String isyevaluate) {
		this.isyevaluate = new SimpleStringProperty(isyevaluate);
	}
	public void setXproductNum(int xproductNum) {
		this.xproductNum = new SimpleIntegerProperty(xproductNum);
	}
	public void setSproductNum(int sproductNum) {
		this.sproductNum = new SimpleIntegerProperty(sproductNum);
	}
	public void setXbalance(int xbalance) {
		this.xbalance = new SimpleIntegerProperty(xbalance);
	}
	public void setSbalance(int sbalance) {
		this.sbalance = new SimpleIntegerProperty(sbalance);
	}
	public void setBalance(int balance) {
		this.balance = new SimpleIntegerProperty(balance);
	}
	public void setXspent(int xspent) {
		this.xspent = new SimpleIntegerProperty(xspent);
	}
	public void setSspent(int sspent) {
		this.sspent = new SimpleIntegerProperty(sspent);
	}
	public void setSpent(int spent) {
		this.spent = new SimpleIntegerProperty(spent);
	}
	public void setProfit(int profit) {
		this.profit = new SimpleIntegerProperty(profit);
	}

}
