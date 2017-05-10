package com.xidian.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xidian.model.order.Order;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class OrderValicateUtil {

	public static boolean isInputValid(Order order,String num) {
		String errorMessage = "";

		if (order.getAuId() == null || order.getAuId().length() == 0) {
			errorMessage += "请输入授权号!\n";
		}
		if (order.getOrderId() == null || order.getOrderId().length() == 0) {
			errorMessage += "请输入订单号!\n";
		}
		if (order.getWayBillNumber() == null || order.getWayBillNumber().length() == 0) {
			errorMessage += "请输入运单号!\n";
		}

	    //判断用户输入的货物数量是否为整数,并赋值
		if(isInt(num)){
			order.setProductNum(Integer.parseInt(num));
		}else{
			errorMessage += "请输入产品数量!\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("输入数据验证");
			alert.setHeaderText("请在输入框中输入正确格式数据");
			alert.setContentText(errorMessage);

			alert.showAndWait();
			return false;
		}


	}


    public static boolean isInt (String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }
}