package com.xidian.view.system;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.system.ManagerUser;
import com.xidian.util.EncryptBySaltUtil;
import com.xidian.util.MessageUtil;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**查询，修改,删除收件地址控制器
 * @author lfq
 *
 */
public class EditManagerUserController {

	//修改级别信息

	@FXML
	private TextField usernameField;

	@FXML
	private TextField reallynameField;

	@FXML
	private ComboBox<String> typeuserBox;

	@FXML
	private AnchorPane editAnchorPane;

	private MainApp mainApp;

	private ManagerUser managerUser;

	private Stage addStage;

	private QueryManagerUserController queryManagerUserController;

	public EditManagerUserController() {

	}

	public void setQueryManagerUserController(QueryManagerUserController queryManagerUserController){
		this.queryManagerUserController = queryManagerUserController;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage addStage) {
		this.addStage = addStage;
	}

	public void setManagerUser(ManagerUser managerUser)
	{
		//设置客户数据到修改表单
		this.managerUser = managerUser;
		usernameField.setText(managerUser.getUsername());
		reallynameField.setText(managerUser.getReallyname());
		typeuserBox.getItems().add(managerUser.getTypeuser());
		typeuserBox.getSelectionModel().select(managerUser.getTypeuser());

	}

	@FXML
	private void initialize() {

	}

	/**
	 * 修改级别信息
	 */
	@FXML
	private void handleResetPassword()
	{

		if(!MessageUtil.alertConfirm("提示", "确认重置密码吗"))
		{
			return;
		}
		SqlSession sqlSession = null;
		String message = "";
		try {
			sqlSession = mainApp.getSqlSession(true);
			managerUser.setPassword(EncryptBySaltUtil.getEncryptPassword("123456"));
			int result = sqlSession.update("com.xidian.ManagerUserXml.updateManagerUserByReset", managerUser);
			if(result == 1)
			{
				message = "重置密码成功！重置后密码为123456";
			}
			else
			{
				message = "重置密码失败！";
			}
		} catch (Exception e) {
			message = "重置密码失败！";
			e.printStackTrace();
		}
		finally
		{
			sqlSession.close();
		}
		MessageUtil.alertInfo(message);
		addStage.close();

	}

	@FXML
	private void handleDeleteManagerUser()
	{

		if(!MessageUtil.alertConfirm("提示", "确认注销用户吗"))
		{
			return;
		}
		SqlSession sqlSession = null;
		String message = "";
		try {
			sqlSession = mainApp.getSqlSession(true);
			int result = sqlSession.delete("com.xidian.ManagerUserXml.deleteManagerUser", managerUser.getUsername());
			if(result == 1)
			{
				message = "注销用户成功！";
				queryManagerUserController.getmanagerUserTable().getItems().remove(managerUser);
			}
			else
			{
				message = "注销用户失败！";
			}
		} catch (Exception e) {
			message = "注销用户失败！";
			e.printStackTrace();
		}
		finally
		{
			sqlSession.close();
		}
		MessageUtil.alertInfo(message);
		addStage.close();
	}

}
