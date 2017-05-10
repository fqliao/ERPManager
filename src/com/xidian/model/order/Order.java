package com.xidian.model.order;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**客户订单实体类
 * @author gwh
 *
 */
public class Order {

	private IntegerProperty id;
	private StringProperty  auId;
	private StringProperty  code;
	private StringProperty  rank;
	private StringProperty  deliveryName;
	private StringProperty  orderId;
	private StringProperty  express;
	private StringProperty  wayBillNumber;
	private StringProperty  productId;
	private IntegerProperty productNum;
	private IntegerProperty  productPrice;
	private IntegerProperty  productSum;
	private StringProperty  receiverName;
	private StringProperty  receiverPhone;
	private StringProperty  receiverAddress;
	private ObjectProperty<LocalDateTime> deliveryTime;

	private LocalDate startDate;
	private LocalDate endDate;

	public LocalDate getStartDate() {
		return startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Order() {

	}
	public int getId() {
		return id.get();
	}
	public IntegerProperty idProperty(){
		return id;
	}
	public String getAuId() {
		return auId.get();
	}
	public StringProperty auIdProperty(){
		return auId;
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
	public String getDeliveryName() {
		return deliveryName.get();
	}
	public StringProperty deliveryNameProperty(){
		return deliveryName;
	}
	public String getOrderId() {
		return orderId.get();
	}
	public StringProperty orderIdProperty(){
		return orderId;
	}
	public String getExpress() {
		return express.get();
	}
	public StringProperty expressProperty(){
		return express;
	}
	public String getWayBillNumber() {
		return wayBillNumber.get();
	}
	public StringProperty wayBillNumberProperty(){
		return wayBillNumber;
	}
	public String getProductId() {
		return productId.get();
	}
	public StringProperty productIdProperty(){
		return productId;
	}
	public int getProductNum() {
		return productNum.get();
	}
	public IntegerProperty productNumProperty(){
		return productNum;
	}
	public int getProductSum() {
		return productSum.get();
	}
	public IntegerProperty productSumProperty(){
		return productSum;
	}
	public int getProductPrice() {
		return productPrice.get();
	}
	public IntegerProperty productPriceProperty(){
		return productPrice;
	}
	public String getReceiverName() {
		return receiverName.get();
	}
	public StringProperty receiverNameProperty(){
		return receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone.get();
	}
	public StringProperty receiverPhoneProperty(){
		return receiverPhone;
	}
	public String getReceiverAddress() {
		return receiverAddress.get();
	}
	public StringProperty receiverAddressProperty(){
		return receiverAddress;
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public void setAuId(String auId) {
		this.auId = new SimpleStringProperty(auId);
	}
	public void setCode(String code) {
		this.code = new SimpleStringProperty(code);
	}
	public void setExpress(String express) {
		this.express = new SimpleStringProperty(express);
	}
	public void setRank(String rank) {
		this.rank = new SimpleStringProperty(rank);
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = new SimpleStringProperty(deliveryName);
	}
	public void setOrderId(String orderId) {
		this.orderId = new SimpleStringProperty(orderId);
	}
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = new SimpleStringProperty(wayBillNumber);
	}
	public void setProductId(String productId) {
		this.productId = new SimpleStringProperty(productId);
	}
	public void setProductNum(int productNum) {
		this.productNum = new SimpleIntegerProperty(productNum);
	}
	public void setProductSum(int productSum) {
		this.productSum = new SimpleIntegerProperty(productSum);
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = new SimpleIntegerProperty(productPrice);
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = new SimpleStringProperty(receiverName);
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = new SimpleStringProperty(receiverPhone);
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = new SimpleStringProperty(receiverAddress);
	}
    public LocalDateTime getDeliveryTime() {
        return deliveryTime.get();
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = new SimpleObjectProperty<LocalDateTime>(deliveryTime);
    }

    public ObjectProperty<LocalDateTime> deliveryTimeProperty() {
        return deliveryTime;
    }




}
