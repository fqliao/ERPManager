package com.xidian.model.evaluate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**考核订单实体类
 * @author lfq
 *
 */
public class EvaluateOrder {

	private IntegerProperty id;
	private StringProperty  auId;
	private StringProperty  orderId;
	private StringProperty qEvaluate; //是否90天考核
	private StringProperty yEvaluate; //是否年度奖励

	public String getOrderId() {
		return orderId.get();
	}
	public StringProperty orderIdProperty(){
		return orderId;
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
	public String getQEvaluate() {
		return qEvaluate.get();
	}
	public StringProperty qEvaluateProperty(){
		return qEvaluate;
	}
	public String getyEvaluate() {
		return yEvaluate.get();
	}
	public StringProperty yEvaluateProperty(){
		return yEvaluate;
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public void setAuId(String auId) {
		this.auId= new SimpleStringProperty(auId);
	}
	public void setOrderId(String orderId) {
		this.orderId = new SimpleStringProperty(orderId);
	}
	public void setQEvaluate(String qEvaluate) {
		this.qEvaluate = new SimpleStringProperty(qEvaluate);
	}
	public void setYEvaluate(String yEvaluate) {
		this.yEvaluate = new SimpleStringProperty(yEvaluate);
	}
}
