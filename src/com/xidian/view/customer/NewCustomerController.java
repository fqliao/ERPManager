package com.xidian.view.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.customer.Customer;
import com.xidian.model.updateinfo.UpdateInfo;
import com.xidian.util.DataValicateUtil;
import com.xidian.util.LocalDateTimeUtil;
import com.xidian.util.MessageUtil;
import com.xidian.util.MybatisUtils;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**保存客户信息控制器
 * @author lfq
 *
 */
public class NewCustomerController {

	@FXML
	private TextField auidField;

	@FXML
	private TextField codeField;

	@FXML
	private Label isAuidLabel;

	@FXML
	private Label codeLabel;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private Label rankBoxLabel;

	@FXML
	private TextField customernameField;

	@FXML
	private ComboBox<String> sexBox;

	@FXML
	private TextField idcardField;

	@FXML
	private TextField addressField;

	@FXML
	private TextField phoneField;

	@FXML
	private TextField qqField;

	@FXML
	private TextField weixinField;

	@FXML
	private TextField createTimeField;

	@FXML
	private TextArea remarkField;

	@FXML
	private AnchorPane editAnchorPane;

	private MainApp mainApp;
	private Customer customer;

	public NewCustomerController() {

	}

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
	private void initialize()
	{
		SqlSession  sqlSession = MybatisUtils.getSqlSession(true);

		List<String> ranks = null;
		ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getRankOfRank");
		sqlSession.close();

		rankBox.getItems().removeAll(rankBox.getItems());

		rankBox.getItems().addAll(ranks);
		rankBox.getSelectionModel().selectFirst();

		sexBox.getItems().removeAll(sexBox.getItems());
		sexBox.getItems().addAll("男", "女");
		sexBox.getSelectionModel().select("男");

	}


	/**
	 * 新建客户
	 */
	@FXML
	private void handleSaveCustomer()
	{
		String auid = auidField.getText();
		if(auid == null || "".equals(auid.trim()))
		{
			isAuidLabel.setText("请输入授权号！");
			return;
		}
		SqlSession sqlSession = MybatisUtils.getSqlSession(false);
		customer = sqlSession.selectOne("com.xidian.CustomerXml.getCustomerByAuid", auid);
		if(customer != null)
		{
			isAuidLabel.setText("授权号已存在！");
			return;
		}
		else
		{
			isAuidLabel.setText("");
		}

		String code = codeField.getText();
		if(code == null || "".equals(code.trim()))
		{
			codeLabel.setText("请输入串码！");
			return;
		}

		customer = sqlSession.selectOne("com.xidian.CustomerXml.getCustomerByCode", code);
		if (customer != null) {
			codeLabel.setText("串码已存在!");
			return;
		}
		else
		{
			codeLabel.setText("");
		}

		customer = new Customer();

		//正式

		customer.setAuid(auid.trim());
		customer.setCode(codeField.getText().trim());
		customer.setRank(rankBox.getSelectionModel().getSelectedItem().trim());
		customer.setCustomerName(customernameField.getText().trim());
		customer.setSex(sexBox.getSelectionModel().getSelectedItem().trim());
		customer.setIdcard(idcardField.getText().trim());
		customer.setAddress(addressField.getText().trim());
		customer.setPhone(phoneField.getText().trim());
		customer.setQq(qqField.getText().trim());
		customer.setWeixin(weixinField.getText().trim());
		customer.setRemark(remarkField.getText().trim());

		//测试
//		customer.setIdcard("421281199002061256");
//		customer.setAddress("空中花园");
//		customer.setPhone("15896321458");
//		customer.setQq("89456324");
//		customer.setWeixin("kfuwnj123");

		LocalDateTime now = LocalDateTimeUtil.getNow();
		customer.setCreateTime(now);
		String message = "";
		if(DataValicateUtil.isInputValid(customer))
		{

			int addCustomerResult = 0;
			int addBalanceResult = 0;
			int addUpdateInfoResult = 0;
//			int addUpdateBalance = 0;
			try {

				//检测身份证是否被撤销资格
				String state = sqlSession.selectOne("com.xidian.CustomerXml.getIdcardOfState",customer.getIdcard());
				if("撤销".equals(state))
				{
					MessageUtil.alertInfo("省份证号"+customer.getIdcard()+"已被撤销授权资格！");
					return;
				}
				if("注册".equals(state))
				{
					MessageUtil.alertInfo("省份证号"+customer.getIdcard()+"已被注册！");
					return;
				}
				addCustomerResult = sqlSession.insert("com.xidian.CustomerXml.addCustomer", customer);

				addBalanceResult = sqlSession.insert("com.xidian.BalanceXml.addBalance", customer.getAuid());

				//封装更新客户信息
				UpdateInfo updateInfo = new UpdateInfo();
				updateInfo.setAuid(customer.getAuid());
				updateInfo.setRank(customer.getRank());
				updateInfo.setState("注册");
				updateInfo.setUpdateTime(now);
				updateInfo.setUpdateReason("注册");

				addUpdateInfoResult = sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);

//				UpdateBalance updateBalance = new UpdateBalance();
//				updateBalance.setAuid(customer.getAuid());
//				updateBalance.setRank(customer.getRank());
//				updateBalance.setPreBalance(0);
//				updateBalance.setUpdateBalance(0);
//				updateBalance.setPosBalance(0);
//				updateBalance.setUpdateTime(now);
//				updateBalance.setUpdateReason("注册");
//
//				addUpdateBalance = sqlSession.insert("com.xidian.UpdateBalanceXml.addUpdateBalance", updateBalance);

				sqlSession.commit();//提交事务

			}
			catch(Exception e)
			{
				e.printStackTrace();
				message = "保存失败！";
			}
			finally
			{
				sqlSession.close();
			}

			if (addCustomerResult == 1 && addBalanceResult == 1 && addUpdateInfoResult == 1)// 保存成功后清空表单数据
			{
				message = "保存成功！";

				auidField.setText("");
				codeField.setText("");
				rankBox.getSelectionModel().selectFirst();;
				customernameField.setText("");
				sexBox.getSelectionModel().select("男");
				idcardField.setText("");
				addressField.setText("");
				phoneField.setText("");
				qqField.setText("");
				weixinField.setText("");
				isAuidLabel.setText("");
				remarkField.setText("");
			}
			else
			{
				message = "保存失败！";
			}

			MessageUtil.alertInfo(message);

		}

	}


}
