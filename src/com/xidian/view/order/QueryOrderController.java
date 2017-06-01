package com.xidian.view.order;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.order.Order;
import com.xidian.util.DateUtil;
import com.xidian.util.LocalDateTimeUtil;
import com.xidian.util.MessageUtil;
import com.xidian.util.MybatisUtils;
import com.xidian.util.SingletonData;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**订单查询控制器
 * @author gwh
 *
 */
public class QueryOrderController {

	@FXML
	private TextField orderIdField;

	@FXML
	private TextField auIdField;

	@FXML
	private TableView<Order> orderTable;

	@FXML
	private TableColumn<Order, Integer> idColumn;

	@FXML
	private TableColumn<Order, String> orderIdColumn;

	@FXML
	private TableColumn<Order, String> wayBillNumberColumn;

	@FXML
	private TableColumn<Order, String> expressColumn;

	@FXML
	private TableColumn<Order, String> rankColumn;

	@FXML
	private TableColumn<Order, String> auIdColumn;

	@FXML
	private TableColumn<Order, String> codeColumn;

	@FXML
	private TableColumn<Order, LocalDateTime> deliveryTimeColumn;

	@FXML
	private TableColumn<Order, String> deliveryNameColumn;

	@FXML
	private TableColumn<Order, String> productIdColumn;

	@FXML
	private TableColumn<Order, Integer> productNumColumn;

	@FXML
	private TableColumn<Order, Integer> productPriceColumn;

	@FXML
	private TableColumn<Order, Integer> productSumColumn;

	@FXML
	private TableColumn<Order, String> receiverNameColumn;

	@FXML
	private TableColumn<Order, String> receiverPhoneColumn;

	@FXML
	private TableColumn<Order, String> receiverAddressColumn;

	//显示订单属性
	@FXML
	private TextField order2IdField;

	@FXML
	private TextField rankField;

	@FXML
	private TextField auId2Field;

	@FXML
	private Label codeLabel;

	@FXML
	private Label productSumLabel;

	@FXML
	private TextField wayBillNumberField;

	@FXML
	private TextField deliveryTimeField;

	@FXML
	private TextField deliveryNameField;

	@FXML
	private ComboBox<String> deliveryNameBox;

	@FXML
	private TextField expressField;

	@FXML
	private TextField productIdField;

	@FXML
	private TextField productNumField;

	@FXML
	private TextField productPriceField;

	@FXML
	private TextField receiverNameField;

	@FXML
	private TextField receiverPhoneField;

	@FXML
	private TextField receiverAddressField;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	private MainApp mainApp;

	private Order order;

	private Stage editStage;

	private ObservableList<Order> orderData = FXCollections.observableArrayList();

	public QueryOrderController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage editStage) {
		this.editStage = editStage;
	}

	@FXML
	private void initialize()
	{
		//设置默认发件人为登录用户
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisUtils.getSqlSession(true);
			List<String> reallyNames = sqlSession.selectList("com.xidian.ManagerUserXml.getManagerUserAllofReallyName");
			if(deliveryNameBox != null)
			{
				deliveryNameBox.getItems().addAll(reallyNames);
				deliveryNameBox.getSelectionModel().select(SingletonData.getSingletonData().getManagerUser().getReallyname());
			}
		} catch (Exception e) {
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
		finally
		{
			sqlSession.close();
		}
		//设置默认起始时间和结束时间
		LocalDate now = LocalDate.now();
		if(startDatePicker != null)
		{
			startDatePicker.setValue(now.minusMonths(1));
		}
		if(endDatePicker != null)
		{
			endDatePicker.setValue(now.plusDays(1));
		}
	}

	/**
	 * 设置订单信息
	 * @param Order
	 */
	public void setOrder(Order order)
	{
		//设置订单信息到显示表单
		this.order = order;

		order2IdField.setText(order.getOrderId());

		rankField.setText(order.getRank());

		auId2Field.setText(order.getAuId());

		codeLabel.setText(order.getCode());

		productSumLabel.setText(order.getProductSum()+"");

		wayBillNumberField.setText(order.getWayBillNumber());

		expressField.setText(order.getExpress());

		deliveryTimeField.setText(LocalDateTimeUtil.format(order.getDeliveryTime()));

		deliveryNameField.setText(order.getDeliveryName());

		productIdField.setText(order.getProductId());

		productNumField.setText(String.valueOf(order.getProductNum()));

		productPriceField.setText(String.valueOf(order.getProductPrice()));

		receiverNameField.setText(order.getReceiverName());

		receiverPhoneField.setText(order.getReceiverPhone());

		receiverAddressField.setText(order.getReceiverAddress());

	}

	/**定义列的点击事件类
	 * @author gwh
	 *
	 */
	private class OrderIntegerCellFactory implements Callback<TableColumn<Order, Integer>, TableCell<Order, Integer>> {

	    @Override
	    public TableCell<Order, Integer> call(TableColumn<Order, Integer> param) {
	        TextFieldTableCell<Order, Integer> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
	            	if(selectedOrder != null)
	            	{
	            		mainApp.showOrderInfo(selectedOrder);

	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class OrderStringCellFactory implements Callback<TableColumn<Order, String>, TableCell<Order, String>> {

		@Override
		public TableCell<Order, String> call(TableColumn<Order, String> param) {
			TextFieldTableCell<Order, String> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
	            	if(selectedOrder != null)
	            	{
	            		mainApp.showOrderInfo(selectedOrder);

	            	}
				}
			});
			return cell;
		}
	}
	private class OrderLocalDateTimeCellFactory implements Callback<TableColumn<Order, LocalDateTime>, TableCell<Order, LocalDateTime>> {

		@Override
		public TableCell<Order, LocalDateTime> call(TableColumn<Order, LocalDateTime> param) {
			TextFieldTableCell<Order, LocalDateTime> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
	            	Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
	            	if(selectedOrder != null)
	            	{
	            		mainApp.showOrderInfo(selectedOrder);

	            	}
				}
			});
			return cell;
		}
	}


	/**
	 * 查询订单信息
	 */
	@FXML
	private void handleQueryOrder() {
		// 清空表中的数据
		orderTable.getItems().clear();

		order = new Order();

		String deliveryName = deliveryNameBox.getSelectionModel().getSelectedItem().trim();
		String receiverName = receiverNameField.getText().trim();
		String orderId = orderIdField.getText().trim();
		String auId = auIdField.getText().trim();
		LocalDate startDate = startDatePicker.getValue();
		LocalDate endDate = endDatePicker.getValue();
		if(!"".equals(deliveryName))
		{
			order.setDeliveryName("%"+deliveryName+"%");
		}
		else
		{
			order.setDeliveryName(deliveryName);
		}
		if(!"".equals(receiverName))
		{
			order.setReceiverName("%"+receiverName+"%");
		}
		else
		{
			order.setReceiverName(receiverName);
		}
		if(!"".equals(orderId))
		{
			order.setOrderId("%"+orderId+"%");
		}
		else
		{
			order.setOrderId(orderId);
		}
		if(!"".equals(auId))
		{
			order.setAuId("%"+auId+"%");
		}
		else
		{
			order.setAuId(auId);
		}
		if(startDate != null)
		{
			order.setStartDate(startDate);
		}
		if(endDate != null)
		{
			order.setEndDate(endDate);
		}


		SqlSession sqlSession = null;
		try {
			sqlSession = mainApp.getSqlSession(true);
			// 通过订单号、授权号查询客户信息
			List<Order> orders;
			orders = sqlSession.selectList("com.xidian.model.order.OrderXml.getOrder", order);
			orderData.addAll(orders);
			sqlSession.close();
			//表中放数据
			orderTable.setItems(orderData);

			//设置显示过滤列的菜单按钮
			orderTable.setTableMenuButtonVisible(true);

			// 设置列中的文本居中
			orderIdColumn.setStyle( "-fx-alignment: CENTER;");
			rankColumn.setStyle( "-fx-alignment: CENTER;");
			auIdColumn.setStyle( "-fx-alignment: CENTER;");
			codeColumn.setStyle( "-fx-alignment: CENTER;");
			wayBillNumberColumn.setStyle( "-fx-alignment: CENTER;");
			expressColumn.setStyle( "-fx-alignment: CENTER;");
			deliveryTimeColumn.setStyle( "-fx-alignment: CENTER;");
			deliveryNameColumn.setStyle( "-fx-alignment: CENTER;");
			productIdColumn.setStyle( "-fx-alignment: CENTER;");
			productNumColumn.setStyle( "-fx-alignment: CENTER;");
			productPriceColumn.setStyle( "-fx-alignment: CENTER;");
			productSumColumn.setStyle( "-fx-alignment: CENTER;");
			receiverNameColumn.setStyle( "-fx-alignment: CENTER;");
			receiverPhoneColumn.setStyle( "-fx-alignment: CENTER;");
			receiverAddressColumn.setStyle( "-fx-alignment: CENTER;");

			// 将数据放入表中的列
			orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
			rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
			auIdColumn.setCellValueFactory(cellData -> cellData.getValue().auIdProperty());
			codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
			wayBillNumberColumn.setCellValueFactory(cellData -> cellData.getValue().wayBillNumberProperty());
			expressColumn.setCellValueFactory(cellData -> cellData.getValue().expressProperty());
			deliveryTimeColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryTimeProperty());
			deliveryNameColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryNameProperty());
			productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
			productNumColumn.setCellValueFactory(cellData -> cellData.getValue().productNumProperty().asObject());
			productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
			productSumColumn.setCellValueFactory(cellData -> cellData.getValue().productSumProperty().asObject());
			receiverNameColumn.setCellValueFactory(cellData -> cellData.getValue().receiverNameProperty());
			receiverPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().receiverPhoneProperty());
			receiverAddressColumn.setCellValueFactory(cellData -> cellData.getValue().receiverAddressProperty());

			//设置每一列的双击事件
			orderIdColumn.setCellFactory(new OrderStringCellFactory());
			rankColumn.setCellFactory(new OrderStringCellFactory());
			auIdColumn.setCellFactory(new OrderStringCellFactory());
			codeColumn.setCellFactory(new OrderStringCellFactory());
			wayBillNumberColumn.setCellFactory(new OrderStringCellFactory());
			expressColumn.setCellFactory(new OrderStringCellFactory());
			deliveryTimeColumn.setCellFactory(new OrderLocalDateTimeCellFactory());
			deliveryNameColumn.setCellFactory(new OrderStringCellFactory());
			productIdColumn.setCellFactory(new OrderStringCellFactory());
			productNumColumn.setCellFactory(new OrderIntegerCellFactory());
			productPriceColumn.setCellFactory(new OrderIntegerCellFactory());
			productSumColumn.setCellFactory(new OrderIntegerCellFactory());
			receiverNameColumn.setCellFactory(new OrderStringCellFactory());
			receiverPhoneColumn.setCellFactory(new OrderStringCellFactory());
			receiverAddressColumn.setCellFactory(new OrderStringCellFactory());
		} catch (Exception e) {
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
		finally
		{
			sqlSession.close();
		}


	}

	/**
	 * 查询订单信息
	 */
	@FXML
	private void handleExportOrder()
	{
		int dataSize = orderData.size();

		if(dataSize == 0)
		{
			MessageUtil.alertInfo("没有可导出的订单，请先查询订单！");
			return;
		}
		//得到导出的文件路径
		//导出人姓名
		String exportUserName = SingletonData.getSingletonData().getManagerUser().getReallyname();
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel文件(*.xls)", "*.xls");
		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
		fileChooser.setInitialDirectory(desktopDir);
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialFileName("订单-"+exportUserName+LocalDate.now());
		Stage s = new Stage();
		File file = fileChooser.showSaveDialog(s);
		if(file == null) return;
//		String exportFilePath = file.getAbsolutePath();

		//列头数据
		String[] titles = {"授权号","串码","代理级别","订单号","快递公司","运单号","产品类型","产品数量(盒)","产品价格(元)","产品合计(元)","发件人","收件人","收件人电话","收件地址","发件时间"};

//		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
//		String desktopPath = desktopDir.getAbsolutePath();
		//创建Excel文件
//		File file = new File(desktopPath+"/订单信息"+LocalDate.now()+".xls");
		try {
			//创建文件
			file.createNewFile();
			//创建工作簿
			WritableWorkbook workbook = Workbook.createWorkbook(file);
			//创建表
			WritableSheet sheet = workbook.createSheet("订单信息", 0);

			WritableCellFormat headerFormat = new WritableCellFormat();
            //水平居中对齐
            headerFormat.setAlignment(Alignment.CENTRE);
            //竖直方向居中对齐
            headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

			jxl.write.Label label = null;
			jxl.write.Number number = null;
			//循环添加头行标题数据
			for (int i = 0; i < titles.length; i++)
			{
				label = new jxl.write.Label(i, 0, titles[i], headerFormat);
				sheet.setColumnView(i,titles[i].getBytes().length);
				sheet.addCell(label);
			}
			//循环添加其他行数据
			int auidLen = titles[0].length()+4;
			int codeLen = titles[1].length()+4;
			int rankLen = titles[2].length()+4;
			int orderidLen = titles[3].length()+4;
			int expressLen = titles[4].length()+4;
			int wayBillNumberLen = titles[5].length()+4;
			int productidLen = titles[6].length()+4;
			int productnumLen = titles[7].length()+4;
			int productpriceLen = titles[8].length()+4;
			int productsumLen = titles[9].length()+4;
			int deliverynameLen = titles[10].length()+4;
			int receivernameLen = titles[11].length()+4;
			int receiverphoneLen = titles[12].length()+4;
			int receiveraddressLen = titles[13].length()+4;
			int deliverytimeLen = titles[14].length()+4;

			for (int i = 1, j = 0; i < dataSize+1 && j < dataSize; i++, j++)
			{
				label = new jxl.write.Label(0, i, orderData.get(j).getAuId(), headerFormat);
				if(auidLen < orderData.get(j).getAuId().getBytes().length+4)
				{
					auidLen = orderData.get(j).getAuId().getBytes().length+4;
				}
				sheet.setColumnView(0,auidLen);
				sheet.addCell(label);

				label = new jxl.write.Label(1, i, orderData.get(j).getCode(), headerFormat);
				if(codeLen < orderData.get(j).getCode().getBytes().length+4)
				{
					codeLen = orderData.get(j).getCode().getBytes().length+4;
				}
				sheet.setColumnView(1,codeLen);
				sheet.addCell(label);

				label = new jxl.write.Label(2, i, orderData.get(j).getRank(), headerFormat);
				if(rankLen < orderData.get(j).getRank().getBytes().length+4)
				{
					rankLen = orderData.get(j).getRank().getBytes().length+4;
				}
				sheet.setColumnView(2,rankLen);
				sheet.addCell(label);

				label = new jxl.write.Label(3, i, orderData.get(j).getOrderId(), headerFormat);
				if(orderidLen < orderData.get(j).getOrderId().getBytes().length+4)
				{
					orderidLen = orderData.get(j).getOrderId().getBytes().length+4;
				}
				sheet.setColumnView(3,orderidLen);
				sheet.addCell(label);

				label = new jxl.write.Label(4, i, orderData.get(j).getExpress(), headerFormat);
				if(expressLen < orderData.get(j).getExpress().getBytes().length-4)
				{
					expressLen = orderData.get(j).getExpress().getBytes().length-4;
				}
				sheet.setColumnView(4,expressLen);
				sheet.addCell(label);

				label = new jxl.write.Label(5, i, orderData.get(j).getWayBillNumber(), headerFormat);
				if(wayBillNumberLen < orderData.get(j).getWayBillNumber().getBytes().length+4)
				{
					wayBillNumberLen = orderData.get(j).getWayBillNumber().getBytes().length+4;
				}
				sheet.setColumnView(5,wayBillNumberLen);
				sheet.addCell(label);

				label = new jxl.write.Label(6, i, orderData.get(j).getProductId(), headerFormat);
				if(productidLen < orderData.get(j).getProductId().getBytes().length+4)
				{
					productidLen = orderData.get(j).getProductId().getBytes().length+4;
				}
				sheet.setColumnView(6,productidLen);
				sheet.addCell(label);

				number = new jxl.write.Number(7, i, orderData.get(j).getProductNum(), headerFormat);
				if(productnumLen < (orderData.get(j).getProductNum()+"").getBytes().length+8)
				{
					productnumLen = (orderData.get(j).getProductNum()+"").getBytes().length+8;
				}
				sheet.setColumnView(7,productnumLen);
				sheet.addCell(number);

				number = new jxl.write.Number(8, i, orderData.get(j).getProductPrice(), headerFormat);
				if(productpriceLen < (orderData.get(j).getProductPrice()+"").getBytes().length+8)
				{
					productpriceLen = (orderData.get(j).getProductPrice()+"").getBytes().length+8;
				}
				sheet.setColumnView(8,productpriceLen);
				sheet.addCell(number);

				number = new jxl.write.Number(9, i, orderData.get(j).getProductSum(), headerFormat);
				if(productsumLen < (orderData.get(j).getProductSum()+"").getBytes().length+8)
				{
					productsumLen = (orderData.get(j).getProductSum()+"").getBytes().length+8;
				}
				sheet.setColumnView(9,productsumLen);
				sheet.addCell(number);

				label = new jxl.write.Label(10, i, orderData.get(j).getDeliveryName(), headerFormat);
				if(deliverynameLen < orderData.get(j).getDeliveryName().getBytes().length+4)
				{
					deliverynameLen = orderData.get(j).getDeliveryName().getBytes().length+4;
				}
				sheet.setColumnView(10,deliverynameLen);
				sheet.addCell(label);

				label = new jxl.write.Label(11, i, orderData.get(j).getReceiverName(), headerFormat);
				if(receivernameLen < orderData.get(j).getReceiverName().getBytes().length+4)
				{
					receivernameLen = orderData.get(j).getReceiverName().getBytes().length+4;
				}
				sheet.setColumnView(11,codeLen);
				sheet.addCell(label);

				label = new jxl.write.Label(12, i, orderData.get(j).getReceiverPhone(), headerFormat);
				if(receiverphoneLen < orderData.get(j).getReceiverPhone().getBytes().length+4)
				{
					receiverphoneLen = orderData.get(j).getReceiverPhone().getBytes().length+4;
				}
				sheet.setColumnView(12,receiverphoneLen);
				sheet.addCell(label);

				label = new jxl.write.Label(13, i, orderData.get(j).getReceiverAddress(), headerFormat);
				if(receiveraddressLen < orderData.get(j).getReceiverAddress().getBytes().length+4)
				{
					receiveraddressLen = orderData.get(j).getReceiverAddress().getBytes().length+4;
				}
				sheet.setColumnView(13,receiveraddressLen);
				sheet.addCell(label);

				label = new jxl.write.Label(14, i, LocalDateTimeUtil.format(orderData.get(j).getDeliveryTime()), headerFormat);
				if(deliverytimeLen < LocalDateTimeUtil.format(orderData.get(j).getDeliveryTime()).getBytes().length)
				{
					deliverytimeLen = LocalDateTimeUtil.format(orderData.get(j).getDeliveryTime()).getBytes().length;
				}
				sheet.setColumnView(14,deliverytimeLen);
				sheet.addCell(label);
			}

			String exportUserNameTitle = "导出人：";
			label = new jxl.write.Label(0, dataSize+2, exportUserNameTitle, headerFormat);
			if(auidLen < exportUserNameTitle.getBytes().length+4)
			{
				auidLen = exportUserNameTitle.getBytes().length+4;
			}
			sheet.setColumnView(0,auidLen);
			sheet.addCell(label);

			//导出人姓名
			label = new jxl.write.Label(1, dataSize+2, exportUserName, headerFormat);
			if(auidLen < exportUserName.getBytes().length)
			{
				auidLen = exportUserName.getBytes().length;
			}
			sheet.setColumnView(0,auidLen);
			sheet.addCell(label);

			//导出时间段
			String exportTimeTitle = "导出时间段：";
			label = new jxl.write.Label(0, dataSize+3, exportTimeTitle, headerFormat);
			if(auidLen < exportTimeTitle.getBytes().length)
			{
				auidLen = exportTimeTitle.getBytes().length;
			}
			sheet.setColumnView(0,auidLen);
			sheet.addCell(label);

			//导出时间段
			String exportStartTime = "未选择";
			if(startDatePicker.getValue() != null)
			{
				exportStartTime = DateUtil.format(startDatePicker.getValue());
			}
			label = new jxl.write.Label(1, dataSize+3, exportStartTime, headerFormat);
			if(auidLen < exportStartTime.getBytes().length)
			{
				auidLen = exportStartTime.getBytes().length;
			}
			sheet.setColumnView(1,auidLen);
			sheet.addCell(label);

			String exportEndTime = "未选择";
			if(endDatePicker.getValue() != null)
			{
				exportEndTime = DateUtil.format(endDatePicker.getValue());
			}
			label = new jxl.write.Label(2, dataSize+3, exportEndTime, headerFormat);
			if(auidLen < exportEndTime.getBytes().length)
			{
				auidLen = exportEndTime.getBytes().length;
			}
			sheet.setColumnView(2,auidLen);
			sheet.addCell(label);

			//导出产品数量合计
			String totalNumTitle = "产品数量合计(盒)：";
			label = new jxl.write.Label(0, dataSize+4, totalNumTitle, headerFormat);
			if(auidLen < totalNumTitle.getBytes().length)
			{
				auidLen = totalNumTitle.getBytes().length;
			}
			sheet.setColumnView(0,auidLen);
			sheet.addCell(label);

			int totalNum = 0;
			int totalSum = 0;
			for (Order order : orderData)
			{
				totalNum += order.getProductNum();
				totalSum += order.getProductSum();
			}

			number = new jxl.write.Number(1, dataSize+4, totalNum, headerFormat);
			if(codeLen < (totalNum+"").getBytes().length)
			{
				codeLen = (totalNum+"").getBytes().length;
			}
			sheet.setColumnView(1,codeLen);
			sheet.addCell(number);

			//导出产品货款合计
			String totalSumTitle = "产品货款合计(元)：";
			label = new jxl.write.Label(0, dataSize+5, totalSumTitle, headerFormat);
			if(auidLen < totalSumTitle.getBytes().length)
			{
				auidLen = totalSumTitle.getBytes().length;
			}
			sheet.setColumnView(0,auidLen);
			sheet.addCell(label);

			number = new jxl.write.Number(1, dataSize+5, totalSum, headerFormat);
			if(codeLen < (totalSum+"").getBytes().length)
			{
				codeLen = (totalSum+"").getBytes().length;
			}
			sheet.setColumnView(1,codeLen);
			sheet.addCell(number);

			//写入工作簿
			workbook.write();
			//关闭
			workbook.close();

			MessageUtil.alertInfo("导出成功！");
		} catch (Exception e) {
			MessageUtil.alertInfo("请关闭旧的导出订单Excel文件，再进行导出");
		}
	}


}
