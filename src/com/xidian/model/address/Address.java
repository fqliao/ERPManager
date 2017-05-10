package com.xidian.model.address;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**收货地址实体类
 * @author lfq
 *
 */
public class Address {

	private final IntegerProperty id;
	private final StringProperty auid;
	private final StringProperty receiverName;
	private final StringProperty receiverAddress;
	private final StringProperty receiverPhone;

	public Address() {
		super();
		this.id = new SimpleIntegerProperty();
		this.auid = new SimpleStringProperty();;
		this.receiverName = new SimpleStringProperty();
		this.receiverAddress = new SimpleStringProperty();
		this.receiverPhone = new SimpleStringProperty();
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
	public String getReceiverName() {
		return receiverName.get();
	}
	public StringProperty receiverNameProperty(){
		return receiverName;
	}
	public String getReceiverAddress() {
		return receiverAddress.get();
	}
	public StringProperty receiverAddressProperty(){
		return receiverAddress;
	}
	public String getReceiverPhone() {
		return receiverPhone.get();
	}
	public StringProperty receiverPhoneProperty(){
		return receiverPhone;
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public void setAuid(String auid) {
		this.auid.set(auid);
	}
	public void setReceiverName(String receiverName) {
		this.receiverName.set(receiverName);
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress.set(receiverAddress);
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone.set(receiverPhone);
	}
}
