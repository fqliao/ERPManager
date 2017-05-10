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
import com.xidian.util.MybatisUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewManagerUserController {

	@FXML
	private TextField usernameField;

	@FXML
	private Label usernameLabel;

	@FXML
	private TextField reallynameField;

	@FXML
	private TextField passwordField;

	@FXML
	private ComboBox<String> typeuserBox;

	private MainApp mainApp;

	public NewManagerUserController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

		typeuserBox.getItems().removeAll(typeuserBox.getItems());
		typeuserBox.getItems().addAll("普通用户", "超级用户");
		typeuserBox.getSelectionModel().select("普通用户");

		if(usernameField != null)
		{
			//对auid监听事件
			usernameField.textProperty().addListener(new ChangeListener<String>()
			{

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldUsername, String newUsername) {
					// 查询用户名
					if (!"".equals(newUsername.trim()))
					{
						SqlSession sqlSession = MybatisUtils.getSqlSession(true);
						ManagerUser managerUser = sqlSession.selectOne("com.xidian.ManagerUserXml.getManagerUser", newUsername.trim());
						if(managerUser != null)
						{
							usernameLabel.setText("用户名已存在！");
						}
						else
						{
							usernameLabel.setText("");
						}
					}

				}
			});
		}
	}

	/**
	 * 密码修改按钮监听函数
	 */
	@FXML
	private void handleSaveManagerUser() {

		// 获取用户输入
		String username = usernameField.getText();
		String reallyname = reallynameField.getText();
		String password = passwordField.getText();
		String typeuser = typeuserBox.getSelectionModel().getSelectedItem();

		if ("".equals(username.trim())) {
			MessageUtil.alertInfo("请在输入框中输入用户名！");
			return;
		}
		if ("".equals(reallyname.trim())) {
			MessageUtil.alertInfo("请在输入框中输入姓名！");
			return;
		}
		if ("".equals(password.trim())) {
			MessageUtil.alertInfo("请在输入框中输入密码！");
			return;
		}
		if(!"".equals(usernameLabel.getText()))
		{
			MessageUtil.alertInfo("用户名已存在！");
			return;
		}
		SqlSession sqlSession = null;
		String message = "";
		try {
			String encryptPassword = EncryptBySaltUtil.getEncryptPassword(password);
			ManagerUser managerUser = new ManagerUser(username, reallyname, encryptPassword, typeuser);
			sqlSession = mainApp.getSqlSession(true);
			int result = sqlSession.insert("com.xidian.ManagerUserXml.addManagerUser", managerUser);
			if (result == 1) {
				message = "注册成功！";
				usernameField.setText("");
				reallynameField.setText("");
				passwordField.setText("");
				typeuserBox.getSelectionModel().select("普通用户");
			} else {
				message = "注册失败！";
			}
		} catch (Exception e) {
			message = "注册失败！";
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		MessageUtil.alertInfo(message);
	}
}
