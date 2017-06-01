package com.xidian.view.balance;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.balance.UpdateBalance;
import com.xidian.util.LocalDateTimeUtil;
import com.xidian.util.MessageUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**余额变更控制器
 * @author gwh
 *
 */
public class CustomerBalanceUpdateController {

	@FXML
	private TextField auIdField;

	@FXML
	private TextField nameField;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TableView<UpdateBalance> balanceTable;

	@FXML
	private TableColumn<UpdateBalance, Integer> idColumn;

	@FXML
	private TableColumn<UpdateBalance, String> auIdColumn;

	@FXML
	private TableColumn<UpdateBalance, String> nameColumn;

	@FXML
	private TableColumn<UpdateBalance, String> rankColumn;

	@FXML
	private TableColumn<UpdateBalance, LocalDateTime> updateTimeColumn;

	@FXML
	private TableColumn<UpdateBalance, Integer> preBalanceColumn;

	@FXML
	private TableColumn<UpdateBalance, Integer> updateBalanceColumn;

	@FXML
	private TableColumn<UpdateBalance, Integer> posBalanceColumn;

	@FXML
	private TableColumn<UpdateBalance, String> updateReasonColumn;

	//弹窗用参数
	@FXML
	private TextField auId2Field;

	@FXML
	private TextField name2Field;

	@FXML
	private TextField rankField;

	@FXML
	private TextField updateTimeField;

	@FXML
	private TextField preBalanceField;

	@FXML
	private TextField updateBalanceField;

	@FXML
	private TextField posBalanceField;

	@FXML
	private TextArea updateReasonField;


	private MainApp mainApp;

	private UpdateBalance updateBalance;

	private Stage editStage;

	private ObservableList<UpdateBalance> UpdateBalanceData = FXCollections.observableArrayList();

	public CustomerBalanceUpdateController() {

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
	 * 设置弹窗余额更新信息
	 * @param UpdateBalance
	 */
	public void setBalanceWindows(UpdateBalance balance)
	{
		//设置客户数据到修改表单
		this.updateBalance = balance;

		name2Field.setText(updateBalance.getCustomerName());

		rankField.setText(updateBalance.getRank());

		auId2Field.setText(updateBalance.getAuid());

		updateTimeField.setText(LocalDateTimeUtil.format(updateBalance.getUpdateTime()));

		preBalanceField.setText(String.valueOf(updateBalance.getPreBalance()));

		updateBalanceField.setText(String.valueOf(updateBalance.getUpdateBalance()));

		posBalanceField.setText(String.valueOf(updateBalance.getPosBalance()));

		updateReasonField.setText(updateBalance.getUpdateReason());

	}

	/**定义余额更新列的点击事件类
	 * @author gwh
	 *
	 */
	private class balanceIntegerCellFactory implements Callback<TableColumn<UpdateBalance, Integer>, TableCell<UpdateBalance, Integer>> {

	    @Override
	    public TableCell<UpdateBalance, Integer> call(TableColumn<UpdateBalance, Integer> param) {
	        TextFieldTableCell<UpdateBalance, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	updateBalance =  balanceTable.getSelectionModel().getSelectedItem();
	            	if(updateBalance != null)
	            	{
	            		mainApp.showSelectBalance(updateBalance);

	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class balanceStringCellFactory implements Callback<TableColumn<UpdateBalance, String>, TableCell<UpdateBalance, String>> {

		@Override
		public TableCell<UpdateBalance, String> call(TableColumn<UpdateBalance, String> param) {
			TextFieldTableCell<UpdateBalance, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
					updateBalance =  balanceTable.getSelectionModel().getSelectedItem();
	            	if(updateBalance != null)
	            	{
	            		mainApp.showSelectBalance(updateBalance);

	            	}
				}
			});
			return cell;
		}
	}
	private class balanceLocalDateCellFactory implements Callback<TableColumn<UpdateBalance, LocalDateTime>, TableCell<UpdateBalance, LocalDateTime>> {

		@Override
		public TableCell<UpdateBalance, LocalDateTime> call(TableColumn<UpdateBalance, LocalDateTime> param) {
			TextFieldTableCell<UpdateBalance, LocalDateTime> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
					updateBalance =  balanceTable.getSelectionModel().getSelectedItem();
	            	if(updateBalance != null)
	            	{
	            		mainApp.showSelectBalance(updateBalance);

	            	}
				}
			});
			return cell;
		}
	}

	/**
	 * 查询余额更新信息
	 */
	@FXML
	private void handleUpdateBalance() {
		// 清空表中的数据
		balanceTable.getItems().clear();

		updateBalance = new UpdateBalance();
		String auId = auIdField.getText();
		String name = nameField.getText();
		String rank = rankBox.getSelectionModel().getSelectedItem();

		SqlSession sqlSession = null;
		try {
			sqlSession = mainApp.getSqlSession(true);
			// 通过授权号、姓名、代理类型查询余额更新信息
			if (!"".equals(auId.trim())) {
				List<UpdateBalance> balanceByAuid = sqlSession.selectList("com.xidian.UpdateBalanceXml.getBalanceByAuid", "%"+auId+"%");
				UpdateBalanceData.addAll(balanceByAuid);
			} else {
				// 如果没有授权号、级别，则查询姓名
				if (!"".equals(name.trim())&&"请选择".equals(rank.trim())) {
					List<UpdateBalance> balanceByName = sqlSession.selectList("com.xidian.UpdateBalanceXml.getBalanceByName", "%"+name+"%");
					UpdateBalanceData.addAll(balanceByName);
				}
				else{
				// 如果授权号、姓名都没有，则查询级别
				if (!"请选择".equals(rank.trim())&& "".equals(name.trim())) {
					List<UpdateBalance> balanceByRank = sqlSession.selectList("com.xidian.UpdateBalanceXml.getBalanceByRank",rank);
					UpdateBalanceData.addAll(balanceByRank);
				}
				//如果授权号、姓名、级别都没有，则查询全部
				if ("请选择".equals(rank.trim())&& "".equals(name.trim())) {
					List<UpdateBalance> balanceByAll = sqlSession.selectList("com.xidian.UpdateBalanceXml.getBalanceByAll");
					UpdateBalanceData.addAll(balanceByAll);
				}
				//姓名、级别组合查询
				if(!"请选择".equals(rank.trim())&& !"".equals(name.trim())){
					UpdateBalance queryUpdateBalance = new UpdateBalance();
					queryUpdateBalance.setCustomerName("%"+name+"%");
					queryUpdateBalance.setRank(rank);
					List<UpdateBalance> balanceByNameAndRank = sqlSession.selectList("com.xidian.UpdateBalanceXml.getBalanceByNameAndRank",queryUpdateBalance);
					UpdateBalanceData.addAll(balanceByNameAndRank);
				}

			}
			}

			//表中放数据
			balanceTable.setItems(UpdateBalanceData);

			//设置显示过滤列的菜单按钮
			balanceTable.setTableMenuButtonVisible(true);

			// 设置列中的文本居中
			nameColumn.setStyle( "-fx-alignment: CENTER;");
			rankColumn.setStyle( "-fx-alignment: CENTER;");
			auIdColumn.setStyle( "-fx-alignment: CENTER;");
			updateTimeColumn.setStyle( "-fx-alignment: CENTER;");
			preBalanceColumn.setStyle( "-fx-alignment: CENTER;");
			updateBalanceColumn.setStyle( "-fx-alignment: CENTER;");
			posBalanceColumn.setStyle( "-fx-alignment: CENTER;");
			updateReasonColumn.setStyle( "-fx-alignment: CENTER;");

			// 将数据放入表中的列
			nameColumn.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
			rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
			auIdColumn.setCellValueFactory(cellData -> cellData.getValue().auidProperty());
			updateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().updateTimeProperty());
			preBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().preBalanceProperty().asObject());
			updateBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().updateBalanceProperty().asObject());
			posBalanceColumn.setCellValueFactory(cellData -> cellData.getValue().posBalanceProperty().asObject());
			updateReasonColumn.setCellValueFactory(cellData -> cellData.getValue().updateReasonProperty());

			//设置每一列的双击事件
			nameColumn.setCellFactory(new balanceStringCellFactory());
			rankColumn.setCellFactory(new balanceStringCellFactory());
			auIdColumn.setCellFactory(new balanceStringCellFactory());
			updateTimeColumn.setCellFactory(new balanceLocalDateCellFactory());
			preBalanceColumn.setCellFactory(new balanceIntegerCellFactory());
			updateBalanceColumn.setCellFactory(new balanceIntegerCellFactory());
			posBalanceColumn.setCellFactory(new balanceIntegerCellFactory());
			updateReasonColumn.setCellFactory(new balanceStringCellFactory());
		} catch (Exception e) {
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
		finally
		{
			sqlSession.close();
		}
	}

	/**
	 * 余额更新弹窗的确定按钮函数
	 */
	@FXML
	private void handleBalanceSelect(){
		editStage.close();
	}
}
