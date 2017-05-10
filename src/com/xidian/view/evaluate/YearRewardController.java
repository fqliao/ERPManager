package com.xidian.view.evaluate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.balance.Balance;
import com.xidian.model.balance.UpdateBalance;
import com.xidian.model.evaluate.YEvaluateTime;
import com.xidian.util.LocalDateTimeUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**年度奖励控制器
 * @author gwh
 *
 */
public class YearRewardController {

	@FXML
	private TextField auIdField;

	@FXML
	private TextField nameField;

	@FXML
	private TableView<YEvaluateTime> rewardTable;

	@FXML
	private TableColumn<YEvaluateTime, Integer> idColumn;

	@FXML
	private TableColumn<YEvaluateTime, String> auIdColumn;

	@FXML
	private TableColumn<YEvaluateTime, String> nameColumn;

	@FXML
	private TableColumn<YEvaluateTime, LocalDateTime> ystartTimeColumn;

	@FXML
	private TableColumn<YEvaluateTime, LocalDateTime> yevaluatetimeColumn;

	@FXML
	private TableColumn<YEvaluateTime, String> isyevaluateColumn;

	@FXML
	private TableColumn<YEvaluateTime, Integer> yBalanceColumn;

	//弹窗显示需考核季度属性
	@FXML
	private TextField name2Field;

	@FXML
	private TextField auId2Field;

	@FXML
	private TextField ystartTimeField;

	@FXML
	private TextField yevaluatetimeField;

	@FXML
	private TextField xproductNumField;

	@FXML
	private TextField sproductNumField;

	@FXML
	private TextField xbalanceField;

	@FXML
	private TextField sbalanceField;

	@FXML
	private TextField balanceField;

	private MainApp mainApp;

	private YEvaluateTime yevaluate;

	private Stage editStage;

	private int qflag;// 1 未考核查询 2 已考核查询

	private ObservableList<YEvaluateTime> yevaluateData = FXCollections.observableArrayList();

	public YearRewardController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage editStage) {
		this.editStage = editStage;
	}

	@FXML
	private void initialize() {

	}

	/**
	 * 设置弹窗未年度奖励信息
	 * @param EvaluateTime
	 */
	public void setYevaluate(YEvaluateTime yevaluate)
	{
		//设置客户数据到修改表单
		this.yevaluate = yevaluate;

		name2Field.setText(yevaluate.getCustomerName());

		auId2Field.setText(yevaluate.getAuId());

		ystartTimeField.setText(LocalDateTimeUtil.format(yevaluate.getYstartTime()));

		yevaluatetimeField.setText(LocalDateTimeUtil.format(yevaluate.getyevaluatetime()));

	}

	/**
	 * 设置已年度奖励弹窗信息
	 * @param EvaluateTime
	 */
	public void setFYevaluate(YEvaluateTime yevaluate)
	{
		//设置客户数据到修改表单
		this.yevaluate = yevaluate;

		name2Field.setText(yevaluate.getCustomerName());

		auId2Field.setText(yevaluate.getAuId());

		ystartTimeField.setText(LocalDateTimeUtil.format(yevaluate.getYstartTime()));

		yevaluatetimeField.setText(LocalDateTimeUtil.format(yevaluate.getyevaluatetime()));

		xproductNumField.setText(String.valueOf(yevaluate.getXproductNum()));

		sproductNumField.setText(String.valueOf(yevaluate.getSproductNum()));

		xbalanceField.setText(String.valueOf(yevaluate.getXbalance()));

		sbalanceField.setText(String.valueOf(yevaluate.getSbalance()));

		balanceField.setText(String.valueOf(yevaluate.getBalance()));

	}

	/**定义未年度奖励列的点击事件类
	 * @author gwh
	 *
	 */
	private class YevaluateIntegerCellFactory implements Callback<TableColumn<YEvaluateTime, Integer>, TableCell<YEvaluateTime, Integer>> {

	    @Override
	    public TableCell<YEvaluateTime, Integer> call(TableColumn<YEvaluateTime, Integer> param) {
	        TextFieldTableCell<YEvaluateTime, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	yevaluate =  rewardTable.getSelectionModel().getSelectedItem();
	            	if(yevaluate != null)
	            	{
//	            		mainApp.showSelectYevaluate(yevaluate);
	            		yevaluateSelect(yevaluate);

	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class YevaluateStringCellFactory implements Callback<TableColumn<YEvaluateTime, String>, TableCell<YEvaluateTime, String>> {

		@Override
		public TableCell<YEvaluateTime, String> call(TableColumn<YEvaluateTime, String> param) {
			TextFieldTableCell<YEvaluateTime, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	yevaluate =  rewardTable.getSelectionModel().getSelectedItem();
	            	if(yevaluate != null)
	            	{
//	            		mainApp.showSelectYevaluate(yevaluate);
	            		yevaluateSelect(yevaluate);

	            	}
				}
			});
			return cell;
		}
	}
	private class YevaluateLocalDateCellFactory implements Callback<TableColumn<YEvaluateTime, LocalDateTime>, TableCell<YEvaluateTime, LocalDateTime>> {

		@Override
		public TableCell<YEvaluateTime, LocalDateTime> call(TableColumn<YEvaluateTime, LocalDateTime> param) {
			TextFieldTableCell<YEvaluateTime, LocalDateTime> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	yevaluate =  rewardTable.getSelectionModel().getSelectedItem();
	            	if(yevaluate != null)
	            	{
//	            		mainApp.showSelectYevaluate(yevaluate);
	            		yevaluateSelect(yevaluate);

	            	}
				}
			});
			return cell;
		}
	}

	/**定义已年度奖励列的点击事件类
	 * @author gwh
	 *
	 */
	private class FYevaluateIntegerCellFactory implements Callback<TableColumn<YEvaluateTime, Integer>, TableCell<YEvaluateTime, Integer>> {

	    @Override
	    public TableCell<YEvaluateTime, Integer> call(TableColumn<YEvaluateTime, Integer> param) {
	        TextFieldTableCell<YEvaluateTime, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	yevaluate =  rewardTable.getSelectionModel().getSelectedItem();
	            	if(yevaluate != null)
	            	{
	            		mainApp.showSelectFYevaluate(yevaluate);

	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class FYevaluateStringCellFactory implements Callback<TableColumn<YEvaluateTime, String>, TableCell<YEvaluateTime, String>> {

		@Override
		public TableCell<YEvaluateTime, String> call(TableColumn<YEvaluateTime, String> param) {
			TextFieldTableCell<YEvaluateTime, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	yevaluate =  rewardTable.getSelectionModel().getSelectedItem();
	            	if(yevaluate != null)
	            	{
	            		mainApp.showSelectFYevaluate(yevaluate);

	            	}
				}
			});
			return cell;
		}
	}
	private class FYevaluateLocalDateCellFactory implements Callback<TableColumn<YEvaluateTime, LocalDateTime>, TableCell<YEvaluateTime, LocalDateTime>> {

		@Override
		public TableCell<YEvaluateTime, LocalDateTime> call(TableColumn<YEvaluateTime, LocalDateTime> param) {
			TextFieldTableCell<YEvaluateTime, LocalDateTime> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	yevaluate =  rewardTable.getSelectionModel().getSelectedItem();
	            	if(yevaluate != null)
	            	{
	            		mainApp.showSelectFYevaluate(yevaluate);

	            	}
				}
			});
			return cell;
		}
	}


	/**
	 * 查询未年度奖励信息
	 */
	@FXML
	private void handleQueryYevaluate() {

		//设置查询标志位
		qflag = 1;

		// 清空表中的数据
		rewardTable.getItems().clear();

		yevaluate = new YEvaluateTime();
		String auId = auIdField.getText();
		String name = nameField.getText();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		// 通过授权号、姓名、代理类型查询年度奖励信息
		if (!"".equals(auId.trim())) {
			List<YEvaluateTime> yevaluateByAuid = sqlSession
					.selectList("com.xidian.model.evaluate.YEvaluateTimeXml.getYEvaluateByAuId", "%"+auId+"%");
			yevaluateData.addAll(yevaluateByAuid);
		}
		else
		{
			// 如果没有授权号，则查询姓名
			if (!"".equals(name.trim()))
			{
				List<YEvaluateTime> yevaluateByName = sqlSession
						.selectList("com.xidian.model.evaluate.YEvaluateTimeXml.getYEvaluateByName","%"+name+"%");
				yevaluateData.addAll(yevaluateByName);
			} else
			{
				// 如果授权号、姓名都没有，则查询全部
				List<YEvaluateTime> yevaluateByAll = sqlSession
						.selectList("com.xidian.model.evaluate.YEvaluateTimeXml.getYEvaluateByAll");
				yevaluateData.addAll(yevaluateByAll);

			}
		}
		if(yevaluateData.size() == 0)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText("");
			alert.setContentText("没有未考核记录!");
			alert.show();
			if (alert.isShowing()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alert.close();
			}
			return;
		}
		//表中放数据
		rewardTable.setItems(yevaluateData);

		//设置显示过滤列的菜单按钮
		rewardTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		nameColumn.setStyle( "-fx-alignment: CENTER;");
		auIdColumn.setStyle( "-fx-alignment: CENTER;");
		ystartTimeColumn.setStyle( "-fx-alignment: CENTER;");
		yevaluatetimeColumn.setStyle( "-fx-alignment: CENTER;");
		isyevaluateColumn.setStyle( "-fx-alignment: CENTER;");
		yBalanceColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
		auIdColumn.setCellValueFactory(cellData -> cellData.getValue().auIdProperty());
		ystartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().ystartTimeProperty());
		yevaluatetimeColumn.setCellValueFactory(cellData -> cellData.getValue().yevaluatetimeProperty());
		isyevaluateColumn.setCellValueFactory(cellData -> cellData.getValue().isyevaluateProperty());
		yBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
		yBalanceColumn.setVisible(false);

		//设置每一列的双击事件
		nameColumn.setCellFactory(new YevaluateStringCellFactory());
		auIdColumn.setCellFactory(new YevaluateStringCellFactory());
		ystartTimeColumn.setCellFactory(new YevaluateLocalDateCellFactory());
		yevaluatetimeColumn.setCellFactory(new YevaluateLocalDateCellFactory());
		isyevaluateColumn.setCellFactory(new YevaluateStringCellFactory());
		yBalanceColumn.setCellFactory(new YevaluateIntegerCellFactory());

	}

	/**
	 * 查询已年度奖励信息
	 */
	@FXML
	private void handleQueryFYevaluate() {

		//设置查询标志位
		qflag = 2;

		// 清空表中的数据
		rewardTable.getItems().clear();

		yevaluate = new YEvaluateTime();
		String auId = auIdField.getText();
		String name = nameField.getText();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		// 通过授权号、姓名、代理类型查询年度奖励信息
		if (!"".equals(auId.trim())) {
			List<YEvaluateTime> yevaluateByAuid = sqlSession
					.selectList("com.xidian.model.evaluate.YEvaluateTimeXml.getFYEvaluateByAuId", "%"+auId+"%");
			yevaluateData.addAll(yevaluateByAuid);
		} else {
			// 如果没有授权号，则查询姓名
			if (!"".equals(name.trim())) {
				List<YEvaluateTime> yevaluateByName = sqlSession
						.selectList("com.xidian.model.evaluate.YEvaluateTimeXml.getFYEvaluateByName", "%"+name+"%");
				yevaluateData.addAll(yevaluateByName);
			} else {
				// 如果授权号、姓名都没有，则查询查询全部
				List<YEvaluateTime> yevaluateByAll = sqlSession
						.selectList("com.xidian.model.evaluate.YEvaluateTimeXml.getFYEvaluateByAll");
				yevaluateData.addAll(yevaluateByAll);

			}
		}

		if(yevaluateData.size() == 0)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText("");
			alert.setContentText("没有已考核记录!");
			alert.show();
			if (alert.isShowing()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				alert.close();
			}
			return;
		}
		//表中放数据
		rewardTable.setItems(yevaluateData);

		//设置显示过滤列的菜单按钮
		rewardTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		nameColumn.setStyle( "-fx-alignment: CENTER;");
		auIdColumn.setStyle( "-fx-alignment: CENTER;");
		ystartTimeColumn.setStyle( "-fx-alignment: CENTER;");
		yevaluatetimeColumn.setStyle( "-fx-alignment: CENTER;");
		isyevaluateColumn.setStyle( "-fx-alignment: CENTER;");
		yBalanceColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
		auIdColumn.setCellValueFactory(cellData -> cellData.getValue().auIdProperty());
		ystartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().ystartTimeProperty());
		yevaluatetimeColumn.setCellValueFactory(cellData -> cellData.getValue().yevaluatetimeProperty());
		isyevaluateColumn.setCellValueFactory(cellData -> cellData.getValue().isyevaluateProperty());
		yBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
		yBalanceColumn.setVisible(true);

		//设置每一列的双击事件
		nameColumn.setCellFactory(new FYevaluateStringCellFactory());
		auIdColumn.setCellFactory(new FYevaluateStringCellFactory());
		ystartTimeColumn.setCellFactory(new FYevaluateLocalDateCellFactory());
		yevaluatetimeColumn.setCellFactory(new FYevaluateLocalDateCellFactory());
		isyevaluateColumn.setCellFactory(new FYevaluateStringCellFactory());
		yBalanceColumn.setCellFactory(new FYevaluateIntegerCellFactory());

	}

	/**
	 * 一键全部年度奖励
	 * @param 奖励对象数组：yevaluateData
	 */
	@FXML
	private void handleYEvaluateAll(){

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("提示");
		alert.setHeaderText("");
		if(yevaluateData.size() == 0 || qflag == 2)
		{
			alert.setContentText("请先查询未考核记录，再进行考核！");
			alert.showAndWait();
			return;
		}
		SqlSession sqlSession = mainApp.getSqlSession(false);
		int result = 0;

		Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		alertConfirm.setTitle("提示");
		alertConfirm.setHeaderText("");
		alertConfirm.setContentText("确认全部考核吗");
		Optional<ButtonType> key = alertConfirm.showAndWait();
		if(key.get() == ButtonType.CANCEL)
		{
			return;
		}
		try
		{
			for (YEvaluateTime yEvaluateTime : yevaluateData)
			{
				result = result + handleYEvaluate(sqlSession, yEvaluateTime);
			}
			if(result == yevaluateData.size())
			{
				alert.setContentText("年度全部考核成功");
				sqlSession.commit();//提交事务
				rewardTable.getItems().removeAll(rewardTable.getItems());
			}
			else
			{
				alert.setContentText("年度考核失败");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			alert.setContentText("年度考核失败！");

		}
		finally
		{
			sqlSession.close();
		}
		alert.show();
		if (alert.isShowing()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
		}
	}

    /**
     * 双击单个年度奖励
     */
	private void yevaluateSelect(YEvaluateTime yevaluate){

		Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		alertConfirm.setTitle("提示");
		alertConfirm.setHeaderText("");
		alertConfirm.setContentText("确认考核吗");
		Optional<ButtonType> key = alertConfirm.showAndWait();
		if(key.get() == ButtonType.CANCEL)
		{
			return;
		}

		SqlSession sqlSession = mainApp.getSqlSession(false);

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("年度考核结果");
		alert.setHeaderText("");
		try
		{
			if(handleYEvaluate(sqlSession, yevaluate) == 1)
			{
				alert.setContentText("年度考核成功");
				rewardTable.getItems().remove(yevaluate);
				sqlSession.commit();//提交事务
			}
			else
			{
				alert.setContentText("年度考核失败");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			alert.setContentText("年度考核失败！");

		}
		finally
		{
			sqlSession.close();
		}
		alert.show();
		if (alert.isShowing()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
		}
	}

	/**
	 * 单个年度奖励
	 */
	private int handleYEvaluate(SqlSession sqlSession, YEvaluateTime yevaluate){

		int xproductNum = 0; //县代提货量
		int sproductNum = 0; //省代提货量

		int xprice = 0;
		int sprice = 0;
		int xbalance = 0;
		int sbalance = 0;
		int balance = 0;
		int xspent = 0;
		int sspent = 0;
		int spent = 0;
		int profit = 0;

		boolean xflag = false;
		boolean sflag = false;
		boolean flag = false;

		int addBalanceResult = 0;
		int addUpdateBalanceResult = 0;
		int updateYEvaluateTimeResult = 0;
		int updateYevaluateResult = 0;

		try
		{
			List<YEvaluateTime> yEvaluateTimes = sqlSession.selectList("com.xidian.model.evaluate.YEvaluateTimeXml.getYevaluateNUM", yevaluate);
			for (YEvaluateTime yEvaluateTime : yEvaluateTimes)
			{
				if("县代".equals(yEvaluateTime.getRank()))
				{
					xproductNum = yEvaluateTime.getXproductNum();
					xflag = true;
				}
				if("省代".equals(yEvaluateTime.getRank()))
				{
					sproductNum = yEvaluateTime.getXproductNum();
					sflag = true;
				}

			}
			if(xflag)
			{ //县代奖励处理
				if(xproductNum >=1800 && xproductNum <= 3599)
				{
					//奖励10元每盒
					xprice = 10;
					xbalance = xprice * xproductNum;
				}
				else if(xproductNum >= 3600)
				{
					//奖励15元每盒
					xprice = 15;
					xbalance = xprice * xproductNum;
				}
				else
				{
					//不奖励
				}
				//县代花费
				xspent = xproductNum * 97;

			}
			if(sflag)
			{
				//省代奖励处理
				if(sproductNum >=6000 && sproductNum <= 8999)
				{
					//奖励4元每盒
					sprice = 4;
					sbalance = sprice * sproductNum;
				}
				else if(sproductNum >= 9000)
				{
					//奖励6元每盒
					sprice = 6;
					sbalance = sprice * sproductNum;
				}
				else
				{
					//不奖励
				}
				//县代花费
				sspent = sproductNum * 67;

			}
			//总花费
			spent = xspent + sspent;
			//总奖励
			balance = xbalance + sbalance;
			//总盈利
			profit = spent - balance;

			//更新相关表
			//更新年度考核时间表
			yevaluate.setXproductNum(xproductNum);
			yevaluate.setSproductNum(sproductNum);
			yevaluate.setXbalance(xbalance);
			yevaluate.setSbalance(sbalance);
			yevaluate.setBalance(balance);
			yevaluate.setXspent(xspent);
			yevaluate.setSspent(sspent);
			yevaluate.setSpent(spent);
			yevaluate.setProfit(profit);

			updateYEvaluateTimeResult = sqlSession.update("com.xidian.model.evaluate.YEvaluateTimeXml.updateYEvaluateTime", yevaluate);

			//更新考核订单表中
			updateYevaluateResult = sqlSession.update("com.xidian.model.evaluate.EvaluateOrderXml.updateYevaluate", yevaluate);

			if(balance != 0)//有返利，更新账号余额相关表
			{

				//先更新余额变更记录表
				UpdateBalance updateBalance = new UpdateBalance();
				updateBalance.setAuid(yevaluate.getAuId());
				updateBalance.setRank(yevaluate.getRank());
				//从账号余额表中查询变更前余额
				int preBalance = sqlSession.selectOne("com.xidian.BalanceXml.getBalanceByAuid", yevaluate.getAuId());
				updateBalance.setPreBalance(preBalance);
				updateBalance.setUpdateBalance(balance);
				updateBalance.setPosBalance(preBalance+balance);
				updateBalance.setUpdateTime(LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now())));
				updateBalance.setUpdateReason("年度奖励\n");
				if(xflag)
				{
					updateBalance.setUpdateReason(updateBalance.getUpdateReason()+"县代提货数量:"+xproductNum+"\n返利单价："+xprice+"元\n"+"返利金额："+ xbalance+"元");

				}
				if(sflag)
				{
					updateBalance.setUpdateReason(updateBalance.getUpdateReason()+"\n省代提货数量:"+sproductNum+"\n返利单价："+sprice+"元\n"+"返利金额："+ sbalance+"元");
				}

				addUpdateBalanceResult = sqlSession.insert("com.xidian.UpdateBalanceXml.addUpdateBalance", updateBalance);

				//更新账号余额表
				Balance balanceTable = new Balance();
				balanceTable.setAuid(yevaluate.getAuId());
				balanceTable.setBalance(balance);

				addBalanceResult = sqlSession.insert("com.xidian.BalanceXml.updateBalance", balanceTable);

				if(addBalanceResult == 0 && addUpdateBalanceResult == 0)
				{
					flag = true;
					return 0;
				}

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			flag = true;
			return 0;
		}
		if (updateYEvaluateTimeResult != 0 && updateYevaluateResult != 0 && !flag) {
			return 1;
		} else {
			return 0;
		}

	}

	/**
	 * 已年度奖励弹窗的确定按钮函数
	 */
	@FXML
	private void handleFEvaluateSelect(){
		editStage.close();
	}

}
