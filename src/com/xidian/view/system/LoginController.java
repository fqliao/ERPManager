package com.xidian.view.system;


import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.system.ManagerUser;
import com.xidian.util.EncryptBySaltUtil;
import com.xidian.util.SingletonData;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**管理用户登录控制器
 * @author lfq
 *
 */
public class LoginController {
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Label messageLabel;

	private MainApp mainApp;
	private ManagerUser managerUser;

	public LoginController() {

	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
	private void initialize()
	{
		passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                	handleLogin();
                }
            }
        });
	}

	/**
	 * 处理登录
	 */
	@FXML
	private void handleLogin()
	{
		messageLabel.setText("");
		String username = usernameField.getText();
		String password = passwordField.getText();
		SqlSession sqlSession = null;
		try {
			sqlSession = mainApp.getSqlSession(true);
			managerUser = sqlSession.selectOne("com.xidian.ManagerUserXml.getManagerUser", username);
			if(managerUser != null)
			{
				if(EncryptBySaltUtil.verify(password, managerUser.getPassword()))
				{
					//保存数据到单例类中
					SingletonData.getSingletonData().setManagerUser(managerUser);

					mainApp.showMainWindow();

				}
				else
				{
					messageLabel.setText("用户名或密码错误！");
				}
			}
			else
			{
				messageLabel.setText("用户名或密码错误！");
			}
		}
		catch (Exception e) {
			messageLabel.setText("登录超时，请检查您是否有网络！");
		}
		finally{
			sqlSession.close();
		}

	}


}
