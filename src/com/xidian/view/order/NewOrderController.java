package com.xidian.view.order;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.order.Order;
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
	List<Rank> ranks = new ArrayList<>();

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

		SqlSession sqlSession = null;
		try
		{
			sqlSession = MybatisUtils.getSqlSession(true);
			ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getAllRank");
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

				//设置产品价格
				productPriceBox.getItems().removeAll(productPriceBox.getItems());
				String productPrice = customer.getProductPrice()+"";
				productPriceBox.getItems().add(productPrice);
				productPriceBox.getSelectionModel().select(productPrice);

				productNumField.setPromptText("提货数量至少为"+ customer.getProductNum());
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
		sqlSession.close();
	}

	//计算金额
	private void caculate(String numString)
	{

		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().add(customer.getRank());
		rankBox.getSelectionModel().selectFirst();

		productPriceBox.getItems().removeAll(productPriceBox.getItems());
		productPriceBox.getItems().add(customer.getProductPrice()+"");
		productPriceBox.getSelectionModel().selectFirst();

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
		else if(productNum < customer.getProductNum())
		{
			productNumLabel.setText("产品数量不能少于"+customer.getProductNum()+"，请重新输入！");
			totalLabel.setText("");
			return;
		}
		else
		{

			if(productNum != customer.getProductNum())
			{
				int num = 0, dnum = 0;

				if("VIP".equals(customer.getRank()) || "特约".equals(customer.getRank()))
				{
					num = productNum % customer.getProductNum();
					if(num != 0)
					{
						dnum = customer.getProductNum() - num;
						productNumLabel.setText("产品数量为"+customer.getProductNum()+"的倍数，需再增加"+dnum+"盒或减少"+num+"盒");
						totalLabel.setText("");
						return;
					}
				}
				else
				{
					num = (productNum - (productNum / customer.getProductNum()) * customer.getProductNum()) % 24;
					if(num != 0)
					{	dnum = 24 - num;
						productNumLabel.setText("产品数量为本等级数加每件(24盒)的倍数，需再增加"+dnum+"盒或减少"+num+"盒");
						totalLabel.setText("");
						return;
					}
				}

				//确定数量在哪一个等级
				Rank newRrank = new Rank();
				newRrank.setProductNum(productNum);
				ranks.add(newRrank);
				Collections.sort(ranks);
				Rank rank = ranks.get(ranks.indexOf(newRrank)-1);
				ranks.remove(newRrank);

				//如果不同等级，则升级
				if(!rank.getRank().equals(rankBox.getSelectionModel().getSelectedItem()))
				{
					boolean result = MessageUtil.alertConfirm("确认升级", "确认升级到\""+rank.getRank()+"\"吗？");
					if(result)
					{
						rankBox.getItems().removeAll(rankBox.getItems());
						rankBox.getItems().add(rank.getRank());
						rankBox.getSelectionModel().selectFirst();

						productPriceBox.getItems().removeAll(productPriceBox.getItems());
						productPriceBox.getItems().add(rank.getProductPrice()+"");
						productPriceBox.getSelectionModel().selectFirst();
					}
					else
					{
						productNumField.setText(customer.getProductNum()+"");
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

		//测试
//		Random random = new Random();
//		order.setOrderId(random .nextInt(1000000000)+"");
//		order.setWayBillNumber(random .nextInt(1000000000)+"");

		order.setProductPrice(Integer.parseInt(productPriceBox.getSelectionModel().getSelectedItem()));
		order.setReceiverName(receiverNameField.getText());
		order.setReceiverPhone(receiverPhoneField.getText());
		order.setReceiverAddress(receiverAddressField.getText());
		String newRank = rankBox.getSelectionModel().getSelectedItem();
		order.setRank(newRank);
		order.setDeliveryName(deliveryNameField.getText());
		List<String> selectedItem = selectedExpressBox.getItems();
		String[] wayBillNumbers = wayBillNumberField.getText().split("\\|");
		if(wayBillNumbers.length != selectedItem.size())
		{
			MessageUtil.alertInfo("快递公司和运单号要一一对应");
			return;
		}
		order.setExpress(selectedItem.toString());
		order.setWayBillNumber(wayBillNumberField.getText());

		String productNum = productNumField.getText();

		if(OrderValicateUtil.isInputValid(order,productNum))
		{
			SqlSession sqlSession = mainApp.getSqlSession(false);//非自动提交，可用于事务

			order.setProductId(productIdBox.getSelectionModel().getSelectedItem());
			//正式使用
			LocalDateTime startTime = LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now()));
//			//测试
//			LocalDateTime startTime = LocalDateTimeUtil.parse(startTimeField.getText());
			order.setDeliveryTime(startTime);

			StringBuilder message = new StringBuilder();
			message.append("\n授权号：" + order.getAuId());
			message.append("\n收件人：" + order.getReceiverName());
			message.append("\n订单号：" + order.getOrderId());
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
			int addEvaluateOrderResult = 0;
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
					updateInfo.setUpdateReason("提货量为："+ productNum +"，由"+oldRank+"升级为"+newRank);
					updateInfo.setUpdateTime(now);

					sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);
				}

				//插入考核订单表
				EvaluateOrder evaluateOrder = new EvaluateOrder();
				evaluateOrder.setAuId(order.getAuId());
				evaluateOrder.setOrderId(order.getOrderId());

				addEvaluateOrderResult = sqlSession.insert("com.xidian.model.evaluate.EvaluateOrderXml.addEvaluateOrder", evaluateOrder);

				//插入货款表
				Balance balance = new Balance();
				balance.setAuid(order.getAuId());
				balance.setBalance(totalSum);

				updateBalanceResult = sqlSession.update("com.xidian.BalanceXml.updateBalance", balance);

				sqlSession.commit();//提交事务

			}
			catch (Exception e)
			{
				e.printStackTrace();
				infoMessage = "保存失败！";
				flag = false;
			}
			finally
			{
				sqlSession.close();
			}

			if (addOrderResult == 1 && addEvaluateOrderResult == 1  && updateBalanceResult == 1 && flag)// 保存成功后清空表单数据
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

		}

	}


}
