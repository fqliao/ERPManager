package com.xidian.model.customer;

import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**客户信息实体类
 * @author lfq
 *
 */
public class Customer {

	private final IntegerProperty id;
	private final StringProperty auid;
	private final StringProperty code;
	private final StringProperty rank;
	private final IntegerProperty productNum;
	private final IntegerProperty productPrice;
	private final StringProperty customerName;
	private final StringProperty sex;
	private final IntegerProperty age;
	private final StringProperty idcard;
	private final StringProperty address;
	private final StringProperty phone;
	private final StringProperty qq;
	private final StringProperty weixin;
	private final StringProperty state;
	private final IntegerProperty balance;
	private final StringProperty isUpgrade;
	private final ObjectProperty<LocalDateTime> createTime;

	public Customer() {
		this.id = new SimpleIntegerProperty();
		this.auid = new SimpleStringProperty();
		this.code = new SimpleStringProperty();
		this.rank = new SimpleStringProperty();
		this.customerName = new SimpleStringProperty();
		this.sex = new SimpleStringProperty();
		this.age = new SimpleIntegerProperty();
		this.idcard = new SimpleStringProperty();
		this.address = new SimpleStringProperty();
		this.phone = new SimpleStringProperty();
		this.qq = new SimpleStringProperty();
		this.weixin = new SimpleStringProperty();
		this.state = new SimpleStringProperty();
		this.balance = new SimpleIntegerProperty();
		this.isUpgrade = new SimpleStringProperty();
		this.createTime = new SimpleObjectProperty<LocalDateTime>();
		this.productNum = new SimpleIntegerProperty();
		this.productPrice = new SimpleIntegerProperty();
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
	public String getCode() {
		return code.get();
	}
	public StringProperty codeProperty(){
		return code;
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
	public String getSex() {
		return sex.get();
	}
	public StringProperty sexProperty(){
		return sex;
	}
	public int getAge() {
		return age.get();
	}
	public IntegerProperty ageProperty(){
		return age;
	}
	public String getIdcard() {
		return idcard.get();
	}
	public StringProperty idcardProperty(){
		return idcard;
	}
	public String getAddress() {
		return address.get();
	}
	public StringProperty addressProperty(){
		return address;
	}
	public String getPhone() {
		return phone.get();
	}
	public StringProperty phoneProperty(){
		return phone;
	}
	public String getQq() {
		return qq.get();
	}
	public StringProperty qqProperty(){
		return qq;
	}
	public String getWeixin() {
		return weixin.get();
	}
	public StringProperty weixinProperty(){
		return weixin;
	}
	public String getIsUpgrade() {
		return isUpgrade.get();
	}
	public StringProperty isUpgradeProperty(){
		return isUpgrade;
	}
	public int getBalance() {
		return balance.get();
	}
	public IntegerProperty balanceProperty(){
		return balance;
	}
	public String getState() {
		return state.get();
	}
	public StringProperty stateProperty(){
		return state;
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public void setAuid(String auid) {
		this.auid.set(auid);
	}
	public void setCode(String code) {
		this.code.set(code);
	}
	public void setRank(String rank) {
		this.rank.set(rank);
	}
	public void setCustomerName(String customerName) {
		this.customerName.set(customerName);
	}
	public void setSex(String sex) {
		this.sex.set(sex);
	}
	public void setAge(int age) {
		this.age.set(age);
	}
	public void setIdcard(String idcard) {
		this.idcard.set(idcard);
	}
	public void setAddress(String address) {
		this.address.set(address);
	}
	public void setPhone(String phone) {
		this.phone.set(phone);
	}
	public void setQq(String qq) {
		this.qq.set(qq);
	}
	public void setWeixin(String weixin) {
		this.weixin.set(weixin);
	}
	public void setIsUpgrade(String isUpgrade) {
		this.isUpgrade.set(isUpgrade);
	}
	public void setState(String state) {
		this.state.set(state);
	}
    public LocalDateTime getCreateTime() {
        return createTime.get();
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime.set(createTime);
    }

	public void setBalance(int balance) {
		this.balance.set(balance);
	}

    public ObjectProperty<LocalDateTime> createTimeProperty() {
        return createTime;
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



}
