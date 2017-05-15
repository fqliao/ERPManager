package com.xidian.view.customer;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.customer.Customer;
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
import javafx.util.Callback;

/**查询，修改控制器
 * @author lfq
 *
 */
public class QueryCustomerController {

	@FXML
	private TextField auidField; //授权号

	@FXML
	private TextField codeField; //串号

	@FXML
	private TextField customernameField; //姓名

	@FXML
	private TextField idcardField; //省份证号

	@FXML
	private ComboBox<String> rankBox; //级别

	@FXML
	private ComboBox<String> stateBox; //状态

	@FXML
	private TableView<Customer> customerTable;

	@FXML
	private TableColumn<Customer, Integer> idColumn;

	@FXML
	private TableColumn<Customer, String> auidColumn;

	@FXML
	private TableColumn<Customer, String> codeColumn;

	@FXML
	private TableColumn<Customer, String> rankColumn;

	@FXML
	private TableColumn<Customer, String> customernameColumn;

	@FXML
	private TableColumn<Customer, String> sexColumn;

	@FXML
	private TableColumn<Customer, Integer> ageColumn;

	@FXML
	private TableColumn<Customer, String> idcardColumn;

	@FXML
	private TableColumn<Customer, String> addressColumn;

	@FXML
	private TableColumn<Customer, String> phoneColumn;

	@FXML
	private TableColumn<Customer, String> qqColumn;

	@FXML
	private TableColumn<Customer, String> weixinColumn;

	@FXML
	private TableColumn<Customer, Integer> balanceColumn;

	@FXML
	private TableColumn<Customer, String> stateColumn;

	@FXML
	private TableColumn<Customer, String> remarkColumn;

//	@FXML
//	private TableColumn<Customer, String> isUpgradeColumn;


	private MainApp mainApp;

	private Customer customer;

	private ObservableList<Customer> customerData = FXCollections.observableArrayList();

	public QueryCustomerController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

		SqlSession sqlSession = null;
		List<String> ranks = null;
		try
		{
			sqlSession = MybatisUtils.getSqlSession(true);
			ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getRankOfRank");
		}
		catch (Exception e)
		{
			MessageUtil.alertInfo("数据库异常，重启系统");
			e.printStackTrace();
		}
		finally
		{
			sqlSession.close();
		}

		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().add("请选择");
		rankBox.getItems().addAll(ranks);
		rankBox.getSelectionModel().select("请选择");

		stateBox.getItems().removeAll(stateBox.getItems());
		stateBox.getItems().add("请选择");
		stateBox.getItems().addAll("注册","过期","撤销");
		stateBox.getSelectionModel().select("注册");
	}

	/**定义列的点击事件类
	 * @author lfq
	 *
	 */
	private class CustomerIntegerCellFactory implements Callback<TableColumn<Customer, Integer>, TableCell<Customer, Integer>> {

	    @Override
	    public TableCell<Customer, Integer> call(TableColumn<Customer, Integer> param) {
	        TextFieldTableCell<Customer, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
	            	if(selectedCustomer != null)
	            	{
	            		mainApp.showEditCustomer(selectedCustomer);
	            	}
				}
	        });
	        return cell;
	    }

	}
	private class CustomerStringCellFactory implements Callback<TableColumn<Customer, String>, TableCell<Customer, String>> {

		@Override
		public TableCell<Customer, String> call(TableColumn<Customer, String> param) {
			TextFieldTableCell<Customer, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
	            	if(selectedCustomer != null)
	            	{
	            		mainApp.showEditCustomer(selectedCustomer);
	            	}
				}
			});
			return cell;
		}
	}
	/**
	 * 查询客户信息
	 */
	@FXML
	private void handleQueryCustomer() {
		// 清空表中的数据
		customerTable.getItems().clear();

		customer = new Customer();
		String auid = auidField.getText().trim();
		String code = codeField.getText().trim();
		String customerName = customernameField.getText().trim();
		String idcard = idcardField.getText().trim();
		String rank = rankBox.getSelectionModel().getSelectedItem().trim();
		String state = stateBox.getSelectionModel().getSelectedItem().trim();

		if(!"".equals(auid))
		{
			customer.setAuid("%"+auid+"%");
		}
		else
		{
			customer.setAuid(auid);
		}
		if(!"".equals(code))
		{
			customer.setCode("%"+code+"%");
		}
		else
		{
			customer.setCode(code);
		}
		if(!"".equals(customerName))
		{
			customer.setCustomerName("%"+customerName+"%");
		}
		else
		{
			customer.setCustomerName(customerName);
		}
		if(!"".equals(idcard))
		{
			customer.setIdcard("%"+idcard+"%");
		}
		else
		{
			customer.setIdcard(idcard);
		}
		if(!"请选择".equals(rank))
		{
			customer.setRank(rank);
		}
		else
		{
			customer.setRank("");
		}
		if(!"请选择".equals(state))
		{
			customer.setState(state);
		}
		else
		{
			customer.setState("");
		}

		SqlSession sqlSession = mainApp.getSqlSession(true);

		List<Customer> customers = sqlSession.selectList("com.xidian.CustomerXml.getCustomers", customer);
		customerData.addAll(customers);
//		// 通过授权号查询客户信息
//		if (!"".equals(auid.trim())) {
//			Customer customerByAuid = sqlSession.selectOne("com.xidian.CustomerXml.getCustomerByAuid", "%"+auid+"%");
//			if(customerByAuid == null)
//			{
//				return;
//			}
//			customerData.add(customerByAuid);
//		} else {
//			// 如果没有查询信息，则全部查询
//			if (("".equals(customerName.trim())) && ("请选择".equals(rank))) {
//				List<Customer> customers = sqlSession.selectList("com.xidian.CustomerXml.getAllCustomer");
//				customerData.addAll(customers);
//			}
//			// 通过代理级别查询客户信息
//			if (("".equals(customerName.trim())) && (!"请选择".equals(rank))) {
//				List<Customer> customersByRank = sqlSession.selectList("com.xidian.CustomerXml.getCustomerByRank",
//						rank);
//				customerData.addAll(customersByRank);
//			}
//
//			// 通过客户姓名查询客户信息
//			if ((!"".equals(customerName.trim())) && ("请选择".equals(rank))) {
//				List<Customer> customersByName = sqlSession.selectList("com.xidian.CustomerXml.getCustomerByName",
//						"%"+customerName+"%");
//				customerData.addAll(customersByName);
//			}
//
//			// 通过代理级别和客户姓名查询客户信息
//			if ((!"".equals(customerName.trim())) && (!"请选择".equals(rank))) {
//				customer.setRank(rank);
//				customer.setCustomerName("%"+customerName+"%");
//				List<Customer> customersByRankAndName = sqlSession
//						.selectList("com.xidian.CustomerXml.getCustomerByRankAndName", customer);
//				customerData.addAll(customersByRankAndName);
//			}
//		}

		//表中放数据
		customerTable.setItems(customerData);

		//设置显示过滤列的菜单按钮
		customerTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		auidColumn.setStyle( "-fx-alignment: CENTER;");
		codeColumn.setStyle( "-fx-alignment: CENTER;");
		rankColumn.setStyle( "-fx-alignment: CENTER;");
		customernameColumn.setStyle( "-fx-alignment: CENTER;");
		sexColumn.setStyle( "-fx-alignment: CENTER;");
		ageColumn.setStyle( "-fx-alignment: CENTER;");
		idcardColumn.setStyle( "-fx-alignment: CENTER;");
		addressColumn.setStyle( "-fx-alignment: CENTER;");
		phoneColumn.setStyle( "-fx-alignment: CENTER;");
		qqColumn.setStyle( "-fx-alignment: CENTER;");
		weixinColumn.setStyle( "-fx-alignment: CENTER;");
		balanceColumn.setStyle("-fx-alignment: CENTER;");
		stateColumn.setStyle( "-fx-alignment: CENTER;");
		remarkColumn.setStyle( "-fx-alignment: CENTER;");
//		isUpgradeColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		auidColumn.setCellValueFactory(cellData -> cellData.getValue().auidProperty());
		codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
		rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		customernameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
		sexColumn.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
		ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
		idcardColumn.setCellValueFactory(cellData -> cellData.getValue().idcardProperty());
		addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		qqColumn.setCellValueFactory(cellData -> cellData.getValue().qqProperty());
		weixinColumn.setCellValueFactory(cellData -> cellData.getValue().weixinProperty());
		balanceColumn.setCellValueFactory(cellData -> cellData.getValue().balanceProperty().asObject());
		stateColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
		remarkColumn.setCellValueFactory(cellData -> cellData.getValue().remarkProperty());
//		isUpgradeColumn.setCellValueFactory(cellData -> cellData.getValue().isUpgradeProperty());

		//设置每一列的双击事件
		CustomerStringCellFactory stringCellFactory = new CustomerStringCellFactory();
		CustomerIntegerCellFactory intCellFactory = new CustomerIntegerCellFactory();
		auidColumn.setCellFactory(stringCellFactory);
		codeColumn.setCellFactory(stringCellFactory);
		rankColumn.setCellFactory(stringCellFactory);
		customernameColumn.setCellFactory(stringCellFactory);
		sexColumn.setCellFactory(stringCellFactory);
		ageColumn.setCellFactory(intCellFactory);
		idcardColumn.setCellFactory(stringCellFactory);
		addressColumn.setCellFactory(stringCellFactory);
		phoneColumn.setCellFactory(stringCellFactory);
		qqColumn.setCellFactory(stringCellFactory);
		weixinColumn.setCellFactory(stringCellFactory);
		balanceColumn.setCellFactory(intCellFactory);
		stateColumn.setCellFactory(stringCellFactory);
		remarkColumn.setCellFactory(stringCellFactory);
//		isUpgradeColumn.setCellFactory(stringCellFactory);

	}

}
