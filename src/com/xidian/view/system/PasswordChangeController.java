package com.xidian.view.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.system.ManagerUser;
import com.xidian.util.EncryptBySaltUtil;
import com.xidian.util.MessageUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class PasswordChangeController {
	@FXML
	private TextField oldPasswordField;

	@FXML
	private TextField newPasswordField;

	@FXML
	private TextField confirmPasswordField;

	private MainApp mainApp;

	public PasswordChangeController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

	}

	/**
	 * 密码修改按钮监听函数
	 */
	@FXML
	private void handleNewPassword() {

		// 获取用户输入
		String oldPassword = oldPasswordField.getText();
		String newPsaaword = newPasswordField.getText();
		String confirmPassword = confirmPasswordField.getText();

		if ("".equals(oldPassword.trim()) || "".equals(newPsaaword.trim()) || "".equals(confirmPassword)) {
			MessageUtil.alertInfo("请在输入框中输入密码！");
			return;
		}
		if (!newPsaaword.equals(confirmPassword)) {
			MessageUtil.alertInfo("两次输入新密码不一致！");
			return;
		}
		// 读取文件获得登陆用户名
		String username = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(new File("username.txt"));
			bufferedReader = new BufferedReader(fileReader);
			username = bufferedReader.readLine();
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		SqlSession sqlSession = null;
		String message = "";
		try {
			sqlSession = mainApp.getSqlSession(true);
			ManagerUser managerUser = sqlSession.selectOne("com.xidian.ManagerUserXml.getManagerUser",
					username);
			if (EncryptBySaltUtil.verify(oldPassword, managerUser.getPassword())) {
				managerUser.setPassword(EncryptBySaltUtil.getEncryptPassword(newPsaaword));
				int result = sqlSession.update("com.xidian.ManagerUserXml.updateManagerUser", managerUser);
				if (result == 1) {
					message = "修改成功！";
					oldPasswordField.setText("");
					newPasswordField.setText("");
					confirmPasswordField.setText("");
				} else {
					message = "修改失败！";
				}

			}
			else {
				message = "旧密码错误，请重新输入！";
				MessageUtil.alertInfo(message);;
				return;
			}
		} catch (Exception e) {
			message = "修改失败！";
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		MessageUtil.alertInfo(message);
	}
}
