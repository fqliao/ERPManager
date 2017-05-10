package com.xidian.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class MessageUtil {

	public static void alertInfo(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("提示");
		alert.setHeaderText("");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static boolean alertConfirm(String title, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(message);
		Optional<ButtonType> key = alert.showAndWait();
		if(key.get() == ButtonType.OK)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
}
