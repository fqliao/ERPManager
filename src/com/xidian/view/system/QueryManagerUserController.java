package com.xidian.view.system;


import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.system.ManagerUser;
import com.xidian.util.MessageUtil;
import com.xidian.util.MybatisUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**查询，修改,删除收件地址控制器
 * @author lfq
 *
 */
public class QueryManagerUserController {

	@FXML
	private TextField usernameBox;

	@FXML
	private TextField reallynameBox;

	@FXML
	private TableView<ManagerUser> managerUserTable;

	@FXML
	private TableColumn<ManagerUser, String> usernameColumn;

	@FXML
	private TableColumn<ManagerUser, String> reallynameColumn;

	@FXML
	private TableColumn<ManagerUser, String> typeuserColumn;

	@FXML
	private AnchorPane editAnchorPane;

	private MainApp mainApp;

	private ObservableList<ManagerUser> managerUserData = FXCollections.observableArrayList();

	private QueryManagerUserController queryManagerUserController;

	public QueryManagerUserController() {

	}

	public void setQueryManagerUserController(QueryManagerUserController queryManagerUserController){
		this.queryManagerUserController = queryManagerUserController;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public TableView<ManagerUser> getmanagerUserTable()
	{
		return managerUserTable;
	}

	@FXML
	private void initialize() {
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisUtils.getSqlSession(true);
		}
		catch (Exception e)
		{
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
		finally
		{
			sqlSession.close();
		}

	}

	/**定义列的点击事件类
	 * @author lfq
	 *
	 */
	private class ManagerUserStringCellFactory implements Callback<TableColumn<ManagerUser, String>, TableCell<ManagerUser, String>> {

	    @Override
	    public TableCell<ManagerUser, String> call(TableColumn<ManagerUser, String> param) {
	        TextFieldTableCell<ManagerUser, String> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	ManagerUser managerUser = managerUserTable.getSelectionModel().getSelectedItem();
	            	if(managerUser != null)
	            	{
	            		mainApp.showEditManagerUser(managerUser, queryManagerUserController);
	            	}
	            }
	        });
	        return cell;
	    }

	}

	/**
	 * 查询管理用户信息
	 */
	@FXML
	private void handleQueryManagerUser() {
		// 清空表中的数据
		managerUserTable.getItems().clear();

		String username = usernameBox.getText();
		String reallyname = reallynameBox.getText();

		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisUtils.getSqlSession(true);

			List<ManagerUser> managerUsers;
			ManagerUser user = new ManagerUser();
			if(username == null)
			{
				user.setUsername("");
			}
			else
			{
				user.setUsername("%"+username+"%");
			}
			if(reallyname == null)
			{
				user.setReallyname("");
			}
			else
			{
				user.setReallyname("%"+reallyname+"%");
			}
			managerUsers = sqlSession.selectList("com.xidian.ManagerUserXml.getManagerUsers", user);
			managerUserData.addAll(managerUsers);
			//表中放数据
			managerUserTable.setItems(managerUserData);

			//设置显示过滤列的菜单按钮
			managerUserTable.setTableMenuButtonVisible(true);

			// 设置列中的文本居中
			usernameColumn.setStyle( "-fx-alignment: CENTER;");
			reallynameColumn.setStyle( "-fx-alignment: CENTER;");
			typeuserColumn.setStyle( "-fx-alignment: CENTER;");

			// 将数据放入表中的列
			usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
			reallynameColumn.setCellValueFactory(cellData -> cellData.getValue().reallynameProperty());
			typeuserColumn.setCellValueFactory(cellData -> cellData.getValue().typeuserProperty());

			//设置每一列的双击事件
			ManagerUserStringCellFactory managerUserStringCellFactory = new ManagerUserStringCellFactory();
			usernameColumn.setCellFactory(managerUserStringCellFactory);
			reallynameColumn.setCellFactory(managerUserStringCellFactory);
			typeuserColumn.setCellFactory(managerUserStringCellFactory);
		}
		catch (Exception e)
		{
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
		finally
		{
			sqlSession.close();
		}

	}

}
