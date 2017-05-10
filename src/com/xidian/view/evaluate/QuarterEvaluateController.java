package com.xidian.view.evaluate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.balance.Balance;
import com.xidian.model.balance.UpdateBalance;
import com.xidian.model.customer.Customer;
import com.xidian.model.evaluate.QEvaluateTime;
import com.xidian.model.rank.Rank;
import com.xidian.model.updateinfo.UpdateInfo;
import com.xidian.util.LocalDateTimeUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**90天考核控制器
 * @author gwh
 *
 */
public class QuarterEvaluateController {

	@FXML
	private TextField auIdField;

	@FXML
	private TextField nameField;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TableView<QEvaluateTime> evaluateTable;

	@FXML
	private TableColumn<QEvaluateTime, Integer> idColumn;

	@FXML
	private TableColumn<QEvaluateTime, String> auIdColumn;

	@FXML
	private TableColumn<QEvaluateTime, String> nameColumn;

	@FXML
	private TableColumn<QEvaluateTime, String> rankColumn;

	@FXML
	private TableColumn<QEvaluateTime, LocalDateTime> qstartTimeColumn;

	@FXML
	private TableColumn<QEvaluateTime, LocalDateTime> qevaluatetimeColumn;

	@FXML
	private TableColumn<QEvaluateTime, String> isqevaluateColumn;

	@FXML
	private TableColumn<QEvaluateTime, Integer> qBalanceColumn;

	//弹窗显示需考核季度属性
	@FXML
	private TextField name2Field;

	@FXML
	private TextField rankField;

	@FXML
	private TextField auId2Field;

	@FXML
	private TextField qstartTimeField;

	@FXML
	private TextField qevaluatetimeField;

	@FXML
	private TextField qevaluatenumField;

	@FXML
	private TextField qevaluatepriceField;

	@FXML
	private TextField qevaluatebalanceField;

	private MainApp mainApp;

	private QEvaluateTime qevaluate;

	private Stage editStage;

	private int qflag;// 1 未考核查询 2 已考核查询

	private ObservableList<QEvaluateTime> qevaluateData = FXCollections.observableArrayList();

	public QuarterEvaluateController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage editStage) {
		this.editStage = editStage;
	}

	@FXML
	private void initialize() {
		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll("请选择", "县代", "省代");
		rankBox.getSelectionModel().select("请选择");

	}

	/**
	 * 设置未考核90天弹窗信息
	 * @param EvaluateTime
	 */
	public void setQevaluate(QEvaluateTime qevaluate)
	{
		//设置客户数据到修改表单
		this.qevaluate = qevaluate;

		name2Field.setText(qevaluate.getCustomerName());

		rankField.setText(qevaluate.getRank());

		auId2Field.setText(qevaluate.getAuId());

		qstartTimeField.setText(LocalDateTimeUtil.format(qevaluate.getQstartTime()));

		qevaluatetimeField.setText(LocalDateTimeUtil.format(qevaluate.getQevaluatetime()));


	}

	/**
	 * 设置已考核90天弹窗信息
	 * @param EvaluateTime
	 */
	public void setFQevaluate(QEvaluateTime qevaluate)
	{
		//设置客户数据到修改表单
		this.qevaluate = qevaluate;

		name2Field.setText(qevaluate.getCustomerName());

		rankField.setText(qevaluate.getRank());

		auId2Field.setText(qevaluate.getAuId());

		qstartTimeField.setText(LocalDateTimeUtil.format(qevaluate.getQstartTime()));

		qevaluatetimeField.setText(LocalDateTimeUtil.format(qevaluate.getQevaluatetime()));

		qevaluatenumField.setText(String.valueOf(qevaluate.getProductNum()));

		qevaluatepriceField.setText(String.valueOf(qevaluate.getPrice()));

		qevaluatebalanceField.setText(String.valueOf(qevaluate.getBalance()));
	}

	/**定义未考核列的点击事件类
	 * @author gwh
	 *
	 */
	private class QevaluateIntegerCellFactory implements Callback<TableColumn<QEvaluateTime, Integer>, TableCell<QEvaluateTime, Integer>> {

	    @Override
	    public TableCell<QEvaluateTime, Integer> call(TableColumn<QEvaluateTime, Integer> param) {
	        TextFieldTableCell<QEvaluateTime, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	qevaluate =  evaluateTable.getSelectionModel().getSelectedItem();
	            	if(qevaluate != null)
	            	{
//	            		mainApp.showSelectQevaluate(qevaluate);
	            		evaluateSelect(qevaluate);

	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class QevaluateStringCellFactory implements Callback<TableColumn<QEvaluateTime, String>, TableCell<QEvaluateTime, String>> {

		@Override
		public TableCell<QEvaluateTime, String> call(TableColumn<QEvaluateTime, String> param) {
			TextFieldTableCell<QEvaluateTime, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
					qevaluate = evaluateTable.getSelectionModel().getSelectedItem();
	            	if(qevaluate != null)
	            	{
//	            		mainApp.showSelectQevaluate(qevaluate);
	            		evaluateSelect(qevaluate);
	            	}
				}
			});
			return cell;
		}
	}
	private class QevaluateLocalDateCellFactory implements Callback<TableColumn<QEvaluateTime, LocalDateTime>, TableCell<QEvaluateTime, LocalDateTime>> {

		@Override
		public TableCell<QEvaluateTime, LocalDateTime> call(TableColumn<QEvaluateTime, LocalDateTime> param) {
			TextFieldTableCell<QEvaluateTime, LocalDateTime> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
					qevaluate = evaluateTable.getSelectionModel().getSelectedItem();
	            	if(qevaluate != null)
	            	{
//	            		mainApp.showSelectQevaluate(qevaluate);
	            		evaluateSelect(qevaluate);

	            	}
				}
			});
			return cell;
		}
	}


	/**定义已考核列的点击事件类
	 * @author gwh
	 *
	 */
	private class FQevaluateIntegerCellFactory implements Callback<TableColumn<QEvaluateTime, Integer>, TableCell<QEvaluateTime, Integer>> {

	    @Override
	    public TableCell<QEvaluateTime, Integer> call(TableColumn<QEvaluateTime, Integer> param) {
	        TextFieldTableCell<QEvaluateTime, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	qevaluate =  evaluateTable.getSelectionModel().getSelectedItem();
	            	if(qevaluate != null)
	            	{
	            		mainApp.showSelectFinishQevaluate(qevaluate);

	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class FQevaluateStringCellFactory implements Callback<TableColumn<QEvaluateTime, String>, TableCell<QEvaluateTime, String>> {

		@Override
		public TableCell<QEvaluateTime, String> call(TableColumn<QEvaluateTime, String> param) {
			TextFieldTableCell<QEvaluateTime, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
					qevaluate = evaluateTable.getSelectionModel().getSelectedItem();
	            	if(qevaluate != null)
	            	{
	            		mainApp.showSelectFinishQevaluate(qevaluate);

	            	}
				}
			});
			return cell;
		}
	}
	private class FQevaluateLocalDateCellFactory implements Callback<TableColumn<QEvaluateTime, LocalDateTime>, TableCell<QEvaluateTime, LocalDateTime>> {

		@Override
		public TableCell<QEvaluateTime, LocalDateTime> call(TableColumn<QEvaluateTime, LocalDateTime> param) {
			TextFieldTableCell<QEvaluateTime, LocalDateTime> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
					qevaluate = evaluateTable.getSelectionModel().getSelectedItem();
	            	if(qevaluate != null)
	            	{
	            		mainApp.showSelectFinishQevaluate(qevaluate);

	            	}
				}
			});
			return cell;
		}
	}

	/**
	 * 查询90天未考核信息
	 */
	@FXML
	private void handleQueryQevaluate() {
		//设置查询标志位
		qflag = 1;

		// 清空表中的数据
		evaluateTable.getItems().clear();

		qevaluate = new QEvaluateTime();
		String auId = auIdField.getText();
		String name = nameField.getText();
		String rank = rankBox.getSelectionModel().getSelectedItem();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		// 通过授权号、姓名、代理类型查询90天考核信息
		if (!"".equals(auId.trim())) {
			List<QEvaluateTime> qevaluateByAuid = sqlSession
					.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getQevaluateByAuId2", "%"+auId+"%");
			qevaluateData.addAll(qevaluateByAuid);
		} else {
			// 如果没有授权号、级别，则查询姓名
			if (!"".equals(name.trim())&&"请选择".equals(rank.trim())) {
				List<QEvaluateTime> qevaluateByName = sqlSession
						.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getQevaluateByName", "%"+name+"%");
				qevaluateData.addAll(qevaluateByName);
			} else {
				// 如果授权号、姓名都没有，则查询级别
				if (!"请选择".equals(rank.trim())&&"".equals(name.trim())) {
					List<QEvaluateTime> qevaluateByRank = sqlSession
							.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getQevaluateByRank", rank);
					qevaluateData.addAll(qevaluateByRank);
				}
				// 如果授权号、姓名、级别都没有，则查询全部
				if ("请选择".equals(rank.trim())&&"".equals(name.trim())) {
					List<QEvaluateTime> qevaluateByAll = sqlSession
							.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getQevaluateByAll");
					qevaluateData.addAll(qevaluateByAll);
				}
				//姓名与级别进行组合查询
				if (!"请选择".equals(rank.trim())&&!"".equals(name.trim())) {
					QEvaluateTime queryEvaluateTime = new QEvaluateTime();
					queryEvaluateTime.setCustomerName("%"+name+"%");
					queryEvaluateTime.setRank(rank);
					List<QEvaluateTime> qevaluateByNameAndRank = sqlSession
							.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getQevaluateByRankAndName",queryEvaluateTime);
					qevaluateData.addAll(qevaluateByNameAndRank);
				}

			}
		}

		if(qevaluateData.size() == 0)
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
		evaluateTable.setItems(qevaluateData);

		//设置显示过滤列的菜单按钮
		evaluateTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		nameColumn.setStyle( "-fx-alignment: CENTER;");
		rankColumn.setStyle( "-fx-alignment: CENTER;");
		auIdColumn.setStyle( "-fx-alignment: CENTER;");
		qstartTimeColumn.setStyle( "-fx-alignment: CENTER;");
		qevaluatetimeColumn.setStyle( "-fx-alignment: CENTER;");
		isqevaluateColumn.setStyle( "-fx-alignment: CENTER;");
		qBalanceColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
		rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		auIdColumn.setCellValueFactory(cellData -> cellData.getValue().auIdProperty());
		qstartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().qstartTimeProperty());
		qevaluatetimeColumn.setCellValueFactory(cellData -> cellData.getValue().qevaluatetimeProperty());
		isqevaluateColumn.setCellValueFactory(cellData -> cellData.getValue().isqevaluateProperty());
		qBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
		qBalanceColumn.setVisible(false);

		//设置每一列的双击事件
		nameColumn.setCellFactory(new QevaluateStringCellFactory());
		rankColumn.setCellFactory(new QevaluateStringCellFactory());
		auIdColumn.setCellFactory(new QevaluateStringCellFactory());
		qstartTimeColumn.setCellFactory(new QevaluateLocalDateCellFactory());
		qevaluatetimeColumn.setCellFactory(new QevaluateLocalDateCellFactory());
		isqevaluateColumn.setCellFactory(new QevaluateStringCellFactory());
		qBalanceColumn.setCellFactory(new QevaluateIntegerCellFactory());

	}

	/**
	 * 查询90天已考核信息
	 */
	@FXML
	private void handleFQueryQevaluate() {
		//设置查询标志位
		qflag = 2;

		// 清空表中的数据
		evaluateTable.getItems().clear();

		qevaluate = new QEvaluateTime();
		String auId = auIdField.getText();
		String name = nameField.getText();
		String rank = rankBox.getSelectionModel().getSelectedItem();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		// 通过授权号、姓名、代理类型查询90天考核信息
		if (!"".equals(auId.trim())) {
			List<QEvaluateTime> qevaluateByAuid = sqlSession
					.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getFQevaluateByAuId", "%"+auId+"%");
			qevaluateData.addAll(qevaluateByAuid);
		} else {
			// 如果没有授权号、级别，则查询姓名
			if (!"".equals(name.trim())&&"请选择".equals(rank.trim())) {
				List<QEvaluateTime> qevaluateByName = sqlSession
						.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getFQevaluateByName", "%"+name+"%");
				qevaluateData.addAll(qevaluateByName);
			} else {
				// 如果授权号、姓名都没有，则查询级别
				if (!"请选择".equals(rank.trim())&&"".equals(name.trim())) {
					List<QEvaluateTime> qevaluateByRank = sqlSession
							.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getFQevaluateByRank",rank);
					qevaluateData.addAll(qevaluateByRank);
				}
				// 如果授权号、姓名、级别都没有，则查询全部
				if ("请选择".equals(rank.trim())&&"".equals(name.trim())) {
					List<QEvaluateTime> qevaluateByAll = sqlSession
							.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getFQevaluateByAll");
					qevaluateData.addAll(qevaluateByAll);
				}
				//姓名与级别进行组合查询
				if (!"请选择".equals(rank.trim())&&!"".equals(name.trim())) {
					QEvaluateTime queryEvaluateTime = new QEvaluateTime();
					queryEvaluateTime.setCustomerName("%"+name+"%");
					queryEvaluateTime.setRank(rank);
					List<QEvaluateTime> qevaluateByAll = sqlSession
							.selectList("com.xidian.model.evaluate.QEvaluateTimeXml.getFQevaluateByRankAndName",queryEvaluateTime);
					qevaluateData.addAll(qevaluateByAll);
				}

			}
		}

		if(qevaluateData.size() == 0)
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
		evaluateTable.setItems(qevaluateData);

		//设置显示过滤列的菜单按钮
		evaluateTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		nameColumn.setStyle( "-fx-alignment: CENTER;");
		rankColumn.setStyle( "-fx-alignment: CENTER;");
		auIdColumn.setStyle( "-fx-alignment: CENTER;");
		qstartTimeColumn.setStyle( "-fx-alignment: CENTER;");
		qevaluatetimeColumn.setStyle( "-fx-alignment: CENTER;");
		isqevaluateColumn.setStyle( "-fx-alignment: CENTER;");
		qBalanceColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
		rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		auIdColumn.setCellValueFactory(cellData -> cellData.getValue().auIdProperty());
		qstartTimeColumn.setCellValueFactory(cellData -> cellData.getValue().qstartTimeProperty());
		qevaluatetimeColumn.setCellValueFactory(cellData -> cellData.getValue().qevaluatetimeProperty());
		isqevaluateColumn.setCellValueFactory(cellData -> cellData.getValue().isqevaluateProperty());
		qBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
		qBalanceColumn.setVisible(true);

		//设置每一列的双击事件
		nameColumn.setCellFactory(new FQevaluateStringCellFactory());
		rankColumn.setCellFactory(new FQevaluateStringCellFactory());
		auIdColumn.setCellFactory(new FQevaluateStringCellFactory());
		qstartTimeColumn.setCellFactory(new FQevaluateLocalDateCellFactory());
		qevaluatetimeColumn.setCellFactory(new FQevaluateLocalDateCellFactory());
		isqevaluateColumn.setCellFactory(new FQevaluateStringCellFactory());
		qBalanceColumn.setCellFactory(new FQevaluateIntegerCellFactory());

	}

	/**
	 * 一键全部90天考核
	 * @param 考核对象数组：qevaluateData
	 */
	@FXML
	private void handleEvaluateAll(){

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("提示");
		alert.setHeaderText("");
		if(qevaluateData.size() == 0 || qflag == 2)
		{
			alert.setContentText("请先查询未考核记录，再进行考核！");
			alert.showAndWait();
			return;
		}

		Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
		alertConfirm.setTitle("提示");
		alertConfirm.setHeaderText("");
		alertConfirm.setContentText("确认全部考核吗");
		Optional<ButtonType> key = alertConfirm.showAndWait();
		if(key.get() == ButtonType.CANCEL)
		{
			return;
		}

		SqlSession sqlSession = mainApp.getSqlSession(false);
		int result = 0;
		try
		{

			for (QEvaluateTime qEvaluateTime : qevaluateData)
			{
				result = result + handleEvaluate(sqlSession, qEvaluateTime);
			}
			if(result == qevaluateData.size())
			{
				alert.setContentText("全部考核成功！");
				sqlSession.commit();
				evaluateTable.getItems().removeAll(evaluateTable.getItems());
			}
			else
			{
				alert.setContentText("考核失败！");
			}
		}
		catch (Exception e) {
			alert.setContentText("考核失败！");
			e.printStackTrace();
		}
		finally
		{
			sqlSession.close();
		}
		alert.setTitle("90天考核结果");
		alert.show();
		if(alert.isShowing())
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
		}

	}

    /**
     * 双击单个90天未考核
     */
	private void evaluateSelect(QEvaluateTime qevaluate)
	{
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
		int result = 0;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("90天考核结果");
		alert.setHeaderText("");
		try
		{
			result = handleEvaluate(sqlSession, qevaluate);
			if(result == 1)
			{
				alert.setContentText("考核成功！");
				evaluateTable.getItems().remove(qevaluate);
				sqlSession.commit();
			}
			else
			{
				alert.setContentText("考核失败！");
			}
		}
		catch (Exception e) {
			alert.setContentText("考核失败！");
			e.printStackTrace();
		}
		finally
		{
			sqlSession.close();
		}
		alert.show();
		if(alert.isShowing())
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			alert.close();
		}
	}


	/** 考核用户
	 * @param sqlSession
	 * @param qevaluate
	 * @return
	 */
	private int handleEvaluate(SqlSession sqlSession, QEvaluateTime qevaluate)
	{
		int result = 0;

		int price = 0;
		int productNum = 0;
		int bonus = 0;

		int addBalanceResult = 0;
		int addUpdateBalanceResult = 0;
		int updateQEvaluateTimeResult = 0;
		int updateQevaluateResult = 0;

		boolean xflag1 = false; //县代返利10元
		boolean xflag2 = false; //县代返利20元
		boolean sflag1 = false; //省代返利7元
		boolean sflag2 = false; //省代返利11元
		boolean flag = false;
		boolean flag2 = false;
		try
		{

			//先在季度内查询有没有订单
			int orderNum = sqlSession.selectOne("com.xidian.model.order.OrderXml.getOrderCountByAuid", qevaluate);
			if(orderNum == 0)
			{
				productNum = 0;
				flag2 = true;
			}
			else
			{
				productNum = sqlSession.selectOne("com.xidian.model.evaluate.QEvaluateTimeXml.getQevaluateProductNum", qevaluate);
			}

			//县代考核
			LocalDateTime now = LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now()));
			if("县代".equals(qevaluate.getRank()))
			{
				if(productNum < 90)
				{
					//撤销县代资格
					Customer customer = new Customer();
					customer.setAuid(qevaluate.getAuId());
					customer.setState("撤销");
					customer.setRank(qevaluate.getRank());
					customer.setCreateTime(now);
					sqlSession.update("com.xidian.CustomerXml.updateCustomerOfState", customer);

					UpdateInfo updateInfo = new UpdateInfo();
					updateInfo.setAuid(qevaluate.getAuId());
					updateInfo.setRank(qevaluate.getRank());
					updateInfo.setState("撤销");
					updateInfo.setUpdateReason("90天提货量为："+productNum+"，小于90，撤销县代资格！");
					updateInfo.setUpdateTime(now);

					sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);


					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("90天考核结果");
					alert.setHeaderText("");
					alert.setContentText("授权号："+qevaluate.getAuId()+"，姓名："+qevaluate.getCustomerName()+"\n90天提货量为："+productNum+"，小于90\n县代资格撤销成功！");
					alert.showAndWait();
				}
				else if(productNum >= 90 && productNum<= 299)
				{
					//返利10元每盒
					bonus = productNum * 10;
					xflag1 = true;
					price = 10;
				}
				else if(productNum >= 300)
				{
					//超过299返利20元每盒
					bonus = productNum * 20;
					if(productNum >= 900)
					{
						//可申请为省代 设置标志位，给出提示，标志数据存入一个表中
						Customer customer = new Customer();
						customer.setAuid(qevaluate.getAuId());
						customer.setIsUpgrade("可升级");
						customer.setCreateTime(qevaluate.getQevaluatetime());
						sqlSession.update("com.xidian.CustomerXml.updateCustomerIsUpgrade", customer);

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("90天考核结果");
						alert.setHeaderText("");
						alert.setContentText("恭喜！\n授权号："+qevaluate.getAuId()+"，姓名："+qevaluate.getCustomerName()+"\n90天提货量为："+productNum+"，大于等于900\n可申请升级为省代！");
						alert.showAndWait();
					}
					xflag2 = true;
					price = 20;
				}
				else
				{

				}
			}
			//省代考核
			else
			{
				if(productNum < 900)
				{
					//降级
					Customer customer = new Customer();
					customer.setAuid(qevaluate.getAuId());
					customer.setState("注册");
					customer.setIsUpgrade("可降级");
					customer.setCreateTime(qevaluate.getQevaluatetime());
					sqlSession.update("com.xidian.CustomerXml.updateCustomerIsUpgrade", customer);

					UpdateInfo updateInfo = new UpdateInfo();
					updateInfo.setAuid(qevaluate.getAuId());
					updateInfo.setRank("县代");
					updateInfo.setState("注册");
					updateInfo.setUpdateReason("90天提货量为："+productNum+"，小于900，降级为县代！");
					updateInfo.setUpdateTime(now);

					sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);

					qevaluate.setRank("县代");

					sqlSession.update("com.xidian.model.evaluate.QEvaluateTimeXml.updateQEvaluateTimeRank", qevaluate);

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("90天考核结果");
					alert.setHeaderText("");
					alert.setContentText("授权号："+qevaluate.getAuId()+"，姓名："+qevaluate.getCustomerName()+"\n90天提货量为："+productNum+"，小于900\n可降级为县代！");
					alert.showAndWait();
				}
				else if(productNum >= 1200 && productNum<= 2399)
				{
					//返利7元每盒  7元的数据考虑放入数据库，提供一个修改界面
					bonus = productNum * 7;
					sflag1 = true;
					price = 7;

				}
				else if(productNum >= 2400)
				{
					//超过2399返利11元每盒
					bonus = productNum * 11;
					sflag2 = true;
					price = 11;
				}
				else
				{
					//不返利，不降级
				}
			}

			//更新订单返利相关表
			//更新90天考核时间表
			qevaluate.setProductNum(productNum);
			qevaluate.setPrice(price);
			qevaluate.setBalance(bonus);
			updateQEvaluateTimeResult = sqlSession.update("com.xidian.model.evaluate.QEvaluateTimeXml.updateQEvaluateTime", qevaluate);

			//更新考核订单表中
			updateQevaluateResult = sqlSession.update("com.xidian.model.evaluate.EvaluateOrderXml.updateQevaluate", qevaluate);

			if(bonus != 0)//有返利，更新账号余额相关表
			{

				//先更新余额变更记录表
				UpdateBalance updateBalance = new UpdateBalance();
				updateBalance.setAuid(qevaluate.getAuId());
				updateBalance.setRank(qevaluate.getRank());
				//从账号余额表中查询变更前余额
				int preBalance = sqlSession.selectOne("com.xidian.BalanceXml.getBalanceByAuid", qevaluate.getAuId());
				updateBalance.setPreBalance(preBalance);
				updateBalance.setUpdateBalance(bonus);
				updateBalance.setPosBalance(preBalance+bonus);
				updateBalance.setUpdateTime(now);
				if(xflag1)
				{
					updateBalance.setUpdateReason("90天返利\n产品数量:"+productNum+"\n返利单价：10元\n"+"返利金额："+bonus+"元");

				}
				if(xflag2)
				{
					updateBalance.setUpdateReason("90天返利\n产品数量:"+productNum+"\n返利单价：20元\n"+"返利金额："+bonus+"元");
				}
				if(sflag1)
				{
					updateBalance.setUpdateReason("90天返利\n产品数量:"+productNum+"\n返利单价：7元\n"+"返利金额："+bonus+"元");

				}
				if(sflag2)
				{
					updateBalance.setUpdateReason("90天返利\n产品数量:"+productNum+"\n返利单价：11元\n"+"返利金额："+bonus+"元");
				}

				addUpdateBalanceResult = sqlSession.insert("com.xidian.UpdateBalanceXml.addUpdateBalance", updateBalance);

				//更新账号余额表
				Balance balance = new Balance();
				balance.setAuid(qevaluate.getAuId());
				balance.setBalance(bonus);

				addBalanceResult = sqlSession.insert("com.xidian.BalanceXml.updateBalance", balance);

				if(addBalanceResult == 0 && addUpdateBalanceResult == 0)
				{
					flag = true;
				}

			}

		}
		catch (Exception e)
		{	e.printStackTrace();
			flag = true;
		}

		if(updateQEvaluateTimeResult == 1 && ((updateQevaluateResult != 0 && !flag) || flag2))
		{
			//考核成功
			result = 1;

		}

		return result;
	}

	/**
	 * 已考核弹窗的确定按钮函数
	 */
	@FXML
	private void handleFEvaluateSelect(){
		editStage.close();
	}
}
