package com.xidian.model.balance;

import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**账号余额变更实体类
 * @author lfq
 *
 */
public class UpdateBalance {

	private IntegerProperty id;
	private StringProperty auid;
	private IntegerProperty preBalance;
	private IntegerProperty updateBalance;
	private IntegerProperty posBalance;
	private ObjectProperty<LocalDateTime> updateTime;
	private StringProperty updateReason;
	private StringProperty  customerName;
	private StringProperty  rank;

	public UpdateBalance() {
		super();
	}

	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public String getAuid() {
		return auid.get();
	}
	public StringProperty auidProperty(){
		return auid;
	}
	public int getPreBalance() {
		return preBalance.get();
	}
	public IntegerProperty preBalanceProperty(){
		return preBalance;
	}
	public int getUpdateBalance() {
		return updateBalance.get();
	}
	public IntegerProperty updateBalanceProperty(){
		return updateBalance;
	}
	public int getPosBalance() {
		return posBalance.get();
	}
	public IntegerProperty posBalanceProperty(){
		return posBalance;
	}
	public String getUpdateReason() {
		return updateReason.get();
	}
	public StringProperty updateReasonProperty(){
		return updateReason;
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public void setAuid(String auid) {
		this.auid= new SimpleStringProperty(auid);
	}
	public void setPreBalance(int preBalance) {
		this.preBalance = new SimpleIntegerProperty(preBalance);
	}
	public void setUpdateBalance(int updateBalance) {
		this.updateBalance = new SimpleIntegerProperty(updateBalance);
	}
	public void setPosBalance(int posBalance) {
		this.posBalance = new SimpleIntegerProperty(posBalance);
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason= new SimpleStringProperty(updateReason);
	}
    public LocalDateTime getUpdateTime() {
        return updateTime.get();
    }
    public ObjectProperty<LocalDateTime> updateTimeProperty() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = new SimpleObjectProperty<LocalDateTime>(updateTime);
    }
	public String getCustomerName() {
		return customerName.get();
	}
	public StringProperty customerNameProperty(){
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = new SimpleStringProperty(customerName);
	}
	public String getRank() {
		return rank.get();
	}
	public StringProperty rankProperty(){
		return rank;
	}
	public void setRank(String rank) {
		this.rank = new SimpleStringProperty(rank);
	}

}
