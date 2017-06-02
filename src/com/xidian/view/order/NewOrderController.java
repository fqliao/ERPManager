package com.xidian.view.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.order.Order;
import com.xidian.model.order.OrderId;
import com.xidian.model.rank.Rank;
import com.xidian.model.updateinfo.UpdateInfo;
import com.xidian.model.address.Address;
import com.xidian.model.balance.Balance;
import com.xidian.model.customer.Customer;
import com.xidian.model.evaluate.EvaluateOrder;
import com.xidian.util.LocalDateTimeUtil;
import com.xidian.util.MessageUtil;
import com.xidian.util.MybatisUtils;
import com.xidian.util.OrderValicateUtil;
import com.xidian.util.SingletonData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

/**新建客户订单控制器
 * @author gwh
 *
 */
public class NewOrderController {

	@FXML
	private TextField auIdField;

	@FXML
	private Label codeLabel;

	@FXML
	private Label isAuidLabel;

	@FXML
	private Label productNumLabel;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TextField deliveryNameField;

	@FXML
	private TextField orderIdField;

	@FXML
	private TextField wayBillNumberField;

	@FXML
	private ComboBox<String> productIdBox;

	@FXML
	private TextField productNumField;

	@FXML
	private Label totalLabel;

	@FXML
	private ComboBox<String> productPriceBox;

	@FXML
	private ComboBox<String> expressBox;

	@FXML
	private ComboBox<String> selectedExpressBox;

	@FXML
	private TextField receiverNameField;

	@FXML
	private TextField receiverPhoneField;

	@FXML
	private TextArea receiverAddressField;

	@FXML
	private TableView<Address> addressTable;

	@FXML
	private TableColumn<Address, String> auidColumn;

	@FXML
	private TableColumn<Address, String> receiverNameColumn;

	@FXML
	private TableColumn<Address, String> receiverAddressColumn;

	@FXML
	private TableColumn<Address, String> receiverPhoneColumn;

	@FXML
	private AnchorPane editAnchorPane;

//	@FXML
//	private TextField startTimeField;//测试时间

	private MainApp mainApp;
	private Order order;
	private Customer customer;
	private SqlSession sqlSession;
	private List<Address> addressByAuid;
	private ObservableList<Address> addressData = FXCollections.observableArrayList();
	private NewOrderController newOrderController;
	private Stage queryAddressStage;
	private int price;
	private int total;
	private int productNum;
	private int productNumChange;
	private String createtime;
	private int counter;
//	List<Rank> ranks = new ArrayList<>();
	List<Rank> ranksAll = new ArrayList<>();

	public NewOrderController() {

	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setNewOrderController( NewOrderController newOrderController)
    {
    	this.newOrderController = newOrderController;
    }
	public void setQueryAddressStage(Stage stage) {
		this.queryAddressStage = stage;

	}
	@FXML
	private void initialize()
	{
		if(deliveryNameField != null)
		{
			deliveryNameField.setText(SingletonData.getSingletonData().getManagerUser().getReallyname());
		}

		if(productIdBox != null)
		{
			productIdBox.getItems().removeAll(productIdBox.getItems());
			productIdBox.getItems().addAll("药王茶");
			productIdBox.getSelectionModel().select("药王茶");
		}

		if(expressBox != null)
		{
			expressBox.getItems().removeAll(expressBox.getItems());
			expressBox.getItems().addAll("中国邮政","申通快递","中通快递","圆通快递","韵达快递","天天快递","汇通快递");
			expressBox.getSelectionModel().select("中国邮政");
		}

		if(auIdField != null)
		{
			//对auid监听事件
			auIdField.textProperty().addListener(new ChangeListener<String>()
			{

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					setFieldData(newValue);

				}
			});
		}

		try {
			sqlSession  = MybatisUtils.getSqlSession(true);
			//对代理类型进行监听
			if(rankBox != null)
			{
				rankBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if(newValue != null){
							List<String> productType;
							productType = sqlSession.selectList("com.xidian.model.rank.RankXml.getProductTypeByRank",newValue);
							productIdBox.getItems().removeAll(productIdBox.getItems());
							productIdBox.getItems().addAll(productType);
							productIdBox.getSelectionModel().select(productType.get(0));
						}
						else{
							rankBox.getItems().removeAll(productPriceBox.getItems());
						}

					}
				});
			}

			//对产品类型进行监听
			if(productIdBox != null)
			{
				productIdBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
						if(newValue != null){
							//由代理级别与产品确定代理等级区间
//							ranksAll.clear();
							ranksAll = sqlSession.selectList("com.xidian.model.rank.RankXml.getAllRankByProductType",newValue);

							productPriceBox.getItems().removeAll(productPriceBox.getItems());
							Rank rankInOrder = new Rank();
							rankInOrder.setRank(rankBox.getSelectionModel().getSelectedItem());
							rankInOrder.setProducttype(newValue);
							Rank productSelect = sqlSession.selectOne("com.xidian.model.rank.RankXml.getProductPriceByRankAndProductType",rankInOrder);
							productPriceBox.getItems().add(String.valueOf(productSelect.getProductPrice()));
							productPriceBox.getSelectionModel().select(String.valueOf(productSelect.getProductPrice()));

							//确定代理产品数量区间
							productNumChange = productSelect.getProductNum();
							productNumField.setPromptText("提货数量至少为"+ productSelect.getProductNum());

							//变更产品后，将输入的产品数量清空
							productNumField.setText("");
							totalLabel.setText("");
							productNumLabel.setText("");

						}
						else{
							productPriceBox.getItems().removeAll(productPriceBox.getItems());
						}

					}
				});
			}

			if(productNumField != null)
			{
				//对productNum设置监听事件
				productNumField.textProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,  String newValue) {
						if(customer != null)
						{
							caculate(newValue);
						}

					}
				});
			}
		} catch (Exception e) {
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
//		finally
//		{
//			sqlSession.close();
//		}



	}

	/**定义列的点击事件类
	 * @author lfq
	 *
	 */
	private class AddressStringCellFactory implements Callback<TableColumn<Address, String>, TableCell<Address, String>> {

	    @Override
	    public TableCell<Address, String> call(TableColumn<Address, String> param) {
	        TextFieldTableCell<Address, String> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	Address selectedAddress = addressTable.getSelectionModel().getSelectedItem();
	            	if(selectedAddress != null)
	            	{
	            		setAddress(selectedAddress);

	            	}
	            }
	        });
	        return cell;
	    }

	}

	@FXML
	private void addExpress()
	{
		String selectedExpress = expressBox.getSelectionModel().getSelectedItem();
		if("".equals(selectedExpress) || selectedExpress == null)
		{
			MessageUtil.alertInfo("请选择快递公司");
			return;
		}
		selectedExpressBox.getItems().add(selectedExpress);
		selectedExpressBox.getSelectionModel().select(selectedExpress);
	}

	@FXML
	private void substractExpress()
	{
		String selectedExpress = selectedExpressBox.getSelectionModel().getSelectedItem();
		if(selectedExpress == null)
		{
			MessageUtil.alertInfo("请选择快递公司");
			return;
		}
		selectedExpressBox.getItems().remove(selectedExpress);
		selectedExpressBox.getSelectionModel().selectFirst();
	}

	//根据选择的地址设置地址
	private void setAddress(Address address)
	{
		newOrderController.receiverNameField.setText(address.getReceiverName());
		newOrderController.receiverPhoneField.setText(address.getReceiverPhone());
		newOrderController.receiverAddressField.setText(address.getReceiverAddress());
		queryAddressStage.close();
	}

	public void showAddress()
	{

		// 表中放数据
		addressData.addAll(mainApp.getAddress());
		addressTable.setItems(addressData);

		// 设置显示过滤列的菜单按钮
		addressTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		auidColumn.setStyle("-fx-alignment: CENTER;");
		receiverNameColumn.setStyle("-fx-alignment: CENTER;");
		receiverAddressColumn.setStyle("-fx-alignment: CENTER;");
		receiverPhoneColumn.setStyle("-fx-alignment: CENTER;");

		// 将数据放入表中的列
		auidColumn.setCellValueFactory(cellData -> cellData.getValue().auidProperty());
		receiverNameColumn.setCellValueFactory(cellData -> cellData.getValue().receiverNameProperty());
		receiverAddressColumn.setCellValueFactory(cellData -> cellData.getValue().receiverAddressProperty());
		receiverPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().receiverPhoneProperty());

		//设置每一列的双击事件
		AddressStringCellFactory addressCellFactory = new AddressStringCellFactory();
		auidColumn.setCellFactory(addressCellFactory);
		receiverNameColumn.setCellFactory(addressCellFactory);
		receiverAddressColumn.setCellFactory(addressCellFactory);
		receiverPhoneColumn.setCellFactory(addressCellFactory);
	}

	//通过auid查询客户信息并设置
	private void setFieldData(String auid) {

		try {
			sqlSession = MybatisUtils.getSqlSession(true);
			// 通过授权号查询客户信息
			if (!"".equals(auid.trim()))
			{
				customer = sqlSession.selectOne("com.xidian.CustomerXml.getCustomerByAuidToOrder", auid);
				if(customer != null)
				{
					isAuidLabel.setText("");

					//设置串码
					codeLabel.setText(customer.getCode());

					//设置级别
					String rank = customer.getRank();
					rankBox.getItems().removeAll(rankBox.getItems());
					rankBox.getItems().add(rank);
					rankBox.getSelectionModel().select(rank);

					//查询收货地址
					addressByAuid = sqlSession.selectList("com.xidian.model.address.AddressXml.getAddressByAuid", auid);
					addressData.addAll(addressByAuid);
					if(addressByAuid.size() != 0)
					{
						//默认第一个收货地址
						Address address = addressByAuid.get(0);
						receiverNameField.setText(address.getReceiverName());
						receiverPhoneField.setText(address.getReceiverPhone());
						receiverAddressField.setText(address.getReceiverAddress());
					}
					//设置订单号
	                createtime = LocalDate.now().toString().replaceAll("-", "");
	                OrderId orderId = sqlSession.selectOne("com.xidian.model.order.OrderIdXml.getCounterByCreateTime",createtime);
	                if(orderId != null){
	                	 counter = orderId.getCounter()+1;
	                	String orderIdCounterString;
	                	if(counter <10){
	                		 orderIdCounterString = "-000"+String.valueOf(counter);
	                	}
	                	else if(counter <100 && counter >9){
	                		 orderIdCounterString = "-00"+String.valueOf(counter);
	                	}
	                	else if(counter <1000 && counter >99){
	                		 orderIdCounterString = "-0"+String.valueOf(counter);
	                	}
	                	else if(counter <10000 && counter >999){
	                		 orderIdCounterString = "-"+String.valueOf(counter);
	                	}
	                	else{
	                		orderIdCounterString = "-"+String.valueOf(counter);
	                	}
	                	orderIdField.setText("YW"+createtime+orderIdCounterString);
	                }
	                else{
	                	sqlSession.insert("com.xidian.model.order.OrderIdXml.addOrderId", createtime);
	                	orderIdField.setText("YW"+createtime+"-0001");
	                }

				}
				else
				{
					//授权号不存在
					isAuidLabel.setText("授权号不存在！");
					codeLabel.setText("");
					rankBox.getItems().removeAll(rankBox.getItems());
					orderIdField.setText("");
					wayBillNumberField.setText("");
					productNumField.setText("");
					productPriceBox.getItems().removeAll(productPriceBox.getItems());
					receiverNameField.setText("");
					receiverPhoneField.setText("");
					receiverAddressField.setText("");
					totalLabel.setText("");
				}
			}

		} catch (Exception e) {
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
//		finally
//		{
//			sqlSession.close();
//		}

	}

	//计算金额
	private void caculate(String numString)
	{

		productNumLabel.setText("");
		try
		{
			productNum = Integer.parseInt(numString);
		}
		catch (NumberFormatException e)
		{
			productNumLabel.setText("产品数量必须输入整数，请重新输入！");
			productNumField.setText("");
			totalLabel.setText("");
			return;
		}

		if(productNum > 1000000)
		{
			productNumLabel.setText("产品数量过大，请重新输入！");
			totalLabel.setText("");
			return;
		}
		else if(productNum < productNumChange)
		{
			productNumLabel.setText("产品数量不能少于"+productNumChange+"，请重新输入！");
			totalLabel.setText("");
			return;
		}
		else
		{

			if(productNum != productNumChange)
			{
				//确定数量在哪一个等级
				Rank newRrank = new Rank();
				newRrank.setProductNum(productNum);
				ranksAll.add(newRrank);
				Collections.sort(ranksAll);
				Rank rank = ranksAll.get(ranksAll.indexOf(newRrank)-1);
				ranksAll.remove(newRrank);

				//如果不同等级，则升级
				if(!rank.getRank().equals(rankBox.getSelectionModel().getSelectedItem()))
				{
					boolean result = MessageUtil.alertConfirm("确认升级", "确认升级到\""+rank.getRank()+"\"吗？");
					if(result)
					{
						//先保存所选产品
						String productTypeSelected = productIdBox.getSelectionModel().getSelectedItem();

						rankBox.getItems().removeAll(rankBox.getItems());
						rankBox.getItems().add(rank.getRank());
						rankBox.getSelectionModel().selectFirst();

						productIdBox.getSelectionModel().select(productTypeSelected);
						productNumField.setText(String.valueOf(productNum));

					}
					else
					{
						productNumField.setText(productNumChange+"");
						return;
					}
				}
			}
		}

		price = Integer.parseInt(productPriceBox.getSelectionModel().getSelectedItem());
		total = productNum * price;
		totalLabel.setText(total + "");

	}

	/**
	 * 查询地址
	 */
	@FXML
	private void handleQueryAddress()
	{
		if(customer == null)
		{
			MessageUtil.alertInfo("请输正确的授权号!");
			return;
		}
		else
		{
			mainApp.showQueryAddress(null, addressByAuid, newOrderController);
		}
	}

	/**
	 * 新建订单
	 */
	@FXML
	private void handleSaveOrder()
	{
		if(customer == null)
		{
			MessageUtil.alertInfo("请输入正确授权号！");
			return;
		}
		if(!"".equals(productNumLabel.getText()))
		{
			MessageUtil.alertInfo("请输入合适的提货数量！");
			return;
		}
		order = new Order();

		//首先验证输入数据
		order.setAuId(auIdField.getText());

		//正式
		order.setOrderId(orderIdField.getText());

		order.setProductPrice(Integer.parseInt(productPriceBox.getSelectionModel().getSelectedItem()));
		order.setReceiverName(receiverNameField.getText());
		order.setReceiverPhone(receiverPhoneField.getText());
		order.setReceiverAddress(receiverAddressField.getText());
		String newRank = rankBox.getSelectionModel().getSelectedItem();
		order.setRank(newRank);
		order.setDeliveryName(deliveryNameField.getText());
		List<String> selectedItem = selectedExpressBox.getItems();
		if("".equals(wayBillNumberField.getText().trim()))
		{
			MessageUtil.alertInfo("请输入运单号");
			return;
		}
		String[] wayBillNumbers = wayBillNumberField.getText().replace(",", "，").split("，");
		if(wayBillNumbers.length != selectedItem.size())
		{
			MessageUtil.alertInfo("快递公司和运单号要一一对应");
			return;
		}
		order.setExpress(selectedItem.toString());
		order.setWayBillNumber(Arrays.toString(wayBillNumbers));

		String productNum = productNumField.getText();

		if(OrderValicateUtil.isInputValid(order,productNum))
		{
			try {
				sqlSession = mainApp.getSqlSession(false);
				String productId = productIdBox.getSelectionModel().getSelectedItem();
				order.setProductId(productId);
				LocalDateTime startTime = LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now()));
				order.setDeliveryTime(startTime);

				StringBuilder message = new StringBuilder();
				message.append("\n授权号：" + order.getAuId());
				message.append("\n收件人：" + order.getReceiverName());
				message.append("\n订单号：" + order.getOrderId());
				message.append("\n快递公司：" + order.getExpress());
				message.append("\n运单号：" + order.getWayBillNumber());
				message.append("\n产品数量(盒)：" + order.getProductNum());
				int totalSum = Integer.parseInt(totalLabel.getText());
				message.append("\n产品合计(元)：" + totalSum);

				if(!MessageUtil.alertConfirm("请确认订单信息", message.toString()))
				{
					return;
				}
				order.setProductSum(totalSum);

				int addOrderResult = 0;
				int addOrderidResult = 0;
				int updateBalanceResult = 0;
				boolean flag = true;
				String infoMessage = "";

				try {

					//插入订单表
					addOrderResult = sqlSession.insert("com.xidian.model.order.OrderXml.addOrder", order);

					//如果升级，更改升级的信息
					String oldRank = customer.getRank();
					LocalDateTime now = LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now()));
					if(!oldRank.equals(newRank))
					{

						customer.setAuid(order.getAuId());
						customer.setRank(newRank);
						customer.setState("注册");
						customer.setCreateTime(now);
						sqlSession.update("com.xidian.CustomerXml.updateCustomerOfState", customer);

						UpdateInfo updateInfo = new UpdateInfo();
						updateInfo.setAuid(order.getAuId());
						updateInfo.setState("注册");
						updateInfo.setRank(newRank);
						updateInfo.setUpdateReason(productId+"提货量为："+ productNum +"，由"+oldRank+"升级为"+newRank);
						updateInfo.setUpdateTime(now);

						sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);
					}

					//插入货款表
					Balance balance = new Balance();
					balance.setAuid(order.getAuId());
					balance.setBalance(totalSum);

					updateBalanceResult = sqlSession.update("com.xidian.BalanceXml.updateBalance", balance);

					//设置订单号的计数器
					OrderId orderId = new OrderId();
					orderId.setCreatetime(createtime);
					orderId.setCounter(counter);
					addOrderidResult = sqlSession.update("com.xidian.model.order.OrderIdXml.updateOrderIdCounter",orderId);

					sqlSession.commit();//提交事务

				}
				catch (Exception e)
				{
					e.printStackTrace();
					infoMessage = "保存失败！";
					flag = false;
				}

				if (addOrderResult == 1 && addOrderidResult == 1  && updateBalanceResult == 1 && flag)// 保存成功后清空表单数据
				{
					infoMessage = "保存成功！";
					customer = null;
					auIdField.setText("");
					codeLabel.setText("");
					rankBox.getItems().removeAll(rankBox.getItems());
					orderIdField.setText("");
					wayBillNumberField.setText("");
					productNumField.setText("");
					productPriceBox.getItems().removeAll(productPriceBox.getItems());
					rankBox.getItems().removeAll(rankBox.getItems());
					expressBox.getSelectionModel().select("中国邮政");
					selectedExpressBox.getItems().removeAll(selectedExpressBox.getItems());
					receiverNameField.setText("");
					receiverPhoneField.setText("");
					receiverAddressField.setText("");
					totalLabel.setText("");
					productNumLabel.setText("");
					productNumField.setPromptText("");

				}
				else
				{
					infoMessage = "保存失败！";
				}
				MessageUtil.alertInfo(infoMessage);
			} catch (Exception e1) {
				MessageUtil.alertInfo("请检查您是否有网络！");
			}
			finally
			{
				sqlSession.close();
			}

		}

	}


}
