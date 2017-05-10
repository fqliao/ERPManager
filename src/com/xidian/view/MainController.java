package com.xidian.view;

import com.xidian.MainApp;
import com.xidian.util.SingletonData;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TitledPane;

/**
 * 管理界面控制器
 * @author lfq
 *
 */
public class MainController {

	@FXML
	private AnchorPane anchorPaneContent;

	private MainApp mainApp;

	@FXML
	private Button queryAndEditCustomerButton;

	@FXML
	private Button queryAndEditAddressButton;

	@FXML
	private Button newManagerUserButton;

	@FXML
	private Button queryAndEditManagerUserButton;

	@FXML
	private TitledPane rankTitledPane;

	@FXML
	private Button newRankButton;

	@FXML
	private Button queryRankButton;


	public MainController() {

	}

	@FXML
	private void initialize()
	{
		if("普通用户".equals(SingletonData.getSingletonData().getManagerUser().getTypeuser()))
		{
			queryAndEditCustomerButton.setText("查询客户");
			queryAndEditAddressButton.setText("查询收件地址");
			rankTitledPane.setText("级别查询");
			newRankButton.setVisible(false);
			queryRankButton.setText("查询级别");
			queryRankButton.setLayoutX(-7.0);
			queryRankButton.setLayoutY(3.0);
			newManagerUserButton.setVisible(false);
			queryAndEditManagerUserButton.setVisible(false);
		}
	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	/**
	 * 打开新建客户面板
	 */
	@FXML
	private void handleNewCustomer()
	{
		mainApp.showNewCustomer(anchorPaneContent);
	}

	/**
	 * 打开查询客户面板
	 */
	@FXML
	private void handleQueryCustomer()
	{
		mainApp.showQueryCustomer(anchorPaneContent);
	}

	/**
	 * 打开查询变更客户信息面板
	 */
	@FXML
	private void handleQueryUpdateInfo()
	{
		mainApp.showQueryUpdateInfo(anchorPaneContent);
	}

	/**
	 * 打开新建收件地址面板
	 */
	@FXML
	private void handleNewAddress()
	{
		mainApp.showNewAddress(anchorPaneContent);
	}

	/**
	 * 打开新建收件地址面板
	 */
	@FXML
	private void handleNewRank()
	{
		mainApp.showNewRank(anchorPaneContent);
	}
	/**
	 * 打开查询收件地址面板
	 */
	@FXML
	private void handleQueryAddress()
	{
		mainApp.showQueryAddress(anchorPaneContent, null, null);
	}
	/**
	 * 打开查询级别面板
	 */
	@FXML
	private void handleQueryRank()
	{
		mainApp.showQueryRank(anchorPaneContent);
	}
	/**
	 * 打开新建订单面板
	 */
	@FXML
	private void handleNewOrder()
	{
		mainApp.showNewOrder(anchorPaneContent);
	}

	/**
	 * 打开订单查询面板
	 */
	@FXML
	private void handleQueryOrder()
	{
		mainApp.showQueryOrder(anchorPaneContent);
	}

	/**
	 * 打开90天查询面板
	 */
	@FXML
	private void handleQevaluate()
	{
		mainApp.showQevaluate(anchorPaneContent);
	}

	/**
	 * 打开年度奖励查询面板
	 */
	@FXML
	private void handleYevaluate()
	{
		mainApp.showYevaluate(anchorPaneContent);
	}

	/**
	 * 打开余额更新查询面板
	 */
	@FXML
	private void handleBalanceUpdate()
	{
		mainApp.showBalanceUpdate(anchorPaneContent);
	}

	/**
	 * 打开修改密码面板
	 */
	@FXML
	private void handlePasswordChange()
	{
		mainApp.showPasswordChange(anchorPaneContent);
	}
	/**
	 * 打开新建用户面板
	 */
	@FXML
	private void handleNewManagerUser()
	{
		mainApp.showNewManagerUser(anchorPaneContent);
	}
	/**
	 * 打开查询用户面板
	 */
	@FXML
	private void handleQueryManagerUser()
	{
		mainApp.showQueryManagerUser(anchorPaneContent);
	}
}
