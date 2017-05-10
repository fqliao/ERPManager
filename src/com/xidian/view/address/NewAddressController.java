package com.xidian.view.address;


import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.address.Address;
import com.xidian.model.customer.Customer;
import com.xidian.util.MessageUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * 保存收货地址的控制器
 *
 * @author lfq
 *
 */
public class NewAddressController {

	@FXML
	private TextField auidField;

	@FXML
	private TextField receiverNameField;

	@FXML
	private TextArea receiverAddressField;

	@FXML
	private TextField receiverPhoneField;

	// @FXML
	// private AnchorPane editAnchorPane;
	//
	private MainApp mainApp;
	private Address address;

	public NewAddressController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

	}

	/**
	 * 新建客户
	 */
	@FXML
	private void handleSaveAddress() {

		String message = "";
		String phone = receiverPhoneField.getText();
		String auid = auidField.getText();
		String receiverName = receiverNameField.getText();
		String receiverAddress = receiverAddressField.getText();
		boolean flag = true; //用户输入是否正确标志位

		if(auid.equals("")){
			flag = false;
			message += "请输入授权号！\n";
		}
		else{
			SqlSession sqlSession = mainApp.getSqlSession(true);
			Customer customerByAuid = sqlSession.selectOne("com.xidian.CustomerXml.getCustomerByAuid", auid);
			if(customerByAuid == null){
				flag = false;
				message += "该授权号不存在！\n";

			}
		}

		if(receiverName.equals("")){
			flag = false;
			message += "请输入收件人姓名！\n";
		}

		if(receiverAddress.equals("")){
			flag = false;
			message += "请输入收件人地址！\n";
		}

		if (phone != null && phone.length() != 0) {
			String fixedRegExp = "(\\d{3,4}\\-)\\d{8}";
			String mobileRegExp = "((13|14|15|17|18))\\d{9}";
			if ((phone.matches(fixedRegExp) || phone.matches(mobileRegExp))) {

			} else {
				flag = false;
				message += "电话号码输入有误！\n";
			}
		} else {
			flag = false;
			message += "请输入电话号码！\n";
		}

		if(flag)
		{
			address = new Address();
			address.setAuid(auidField.getText());
			address.setReceiverName(receiverNameField.getText());
			address.setReceiverAddress(receiverAddressField.getText());
			address.setReceiverPhone(phone);

			SqlSession sqlSession = mainApp.getSqlSession(true);// 非自动提交，可用于事务

			int addAddressResult = 0;

			try {
				addAddressResult = sqlSession.insert("com.xidian.model.address.AddressXml.addAddress", address);
			} finally {
				sqlSession.close();
				if (addAddressResult == 1) {
					message += "保存成功!\n";
					auidField.setText("");
					receiverNameField.setText("");
					receiverAddressField.setText("");
					receiverPhoneField.setText("");
				} else {
					message += "保存失敗!\n";
				}
			}
		}
		MessageUtil.alertInfo(message);

	}

}
