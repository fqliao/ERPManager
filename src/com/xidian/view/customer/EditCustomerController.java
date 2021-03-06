package com.xidian.view.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.model.customer.Customer;
import com.xidian.model.updateinfo.UpdateInfo;
import com.xidian.util.DataValicateUtil;
import com.xidian.util.LocalDateTimeUtil;
import com.xidian.util.MessageUtil;
import com.xidian.util.MybatisUtils;
import com.xidian.util.SingletonData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**修改控制器
 * @author lfq
 *
 */
public class EditCustomerController {

	@FXML
	private TextField auidField;

	@FXML
	private TextField codeField;

	@FXML
	private TextField customernameField;

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private CheckBox expiredBox;

	@FXML
	private CheckBox isRevoke;

	//修改客户信息属性
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

	@FXML
	private Label upgradeLabel;

	@FXML
	private Button editButton;

	private Customer customer;

	private Stage editStage;

	public EditCustomerController() {

	}

	public void setEditStage(Stage editStage) {
		this.editStage = editStage;
	}

	public void setCustomer(Customer customer)
	{
		//设置客户数据到修改表单
		this.customer = customer;
		rankBox.getItems().removeAll(rankBox.getItems());
		sexBox.getItems().removeAll(sexBox.getItems());
		if("过期".equals(customer.getState()) || "撤销".equals(customer.getState()) || "普通用户".equals(SingletonData.getSingletonData().getManagerUser().getTypeuser()))
		{
			editButton.setVisible(false);
			isRevoke.setVisible(false);
			expiredBox.setVisible(false);

			customernameField.setEditable(false);

			idcardField.setEditable(false);

			addressField.setEditable(false);

			phoneField.setEditable(false);

			qqField.setEditable(false);

			weixinField.setEditable(false);

			remarkField.setEditable(false);

			rankBox.getItems().add(customer.getRank());
			sexBox.getItems().add(customer.getSex());
		}
		else
		{
			SqlSession sqlSession = null;
			try {
				sqlSession = MybatisUtils.getSqlSession(true);
				List<String> ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getRankOfRank");
				sqlSession.close();

				rankBox.getItems().addAll(ranks);
				sexBox.getItems().addAll("男", "女");
			} catch (Exception e) {
				MessageUtil.alertInfo("请检查您是否有网络！");
			}
			finally
			{
				sqlSession.close();
			}
		}

		auidField.setText(customer.getAuid());
		codeField.setText(customer.getCode());
		rankBox.getSelectionModel().select(customer.getRank());
		customernameField.setText(customer.getCustomerName());
		sexBox.getSelectionModel().select(customer.getSex());
		idcardField.setText(customer.getIdcard());
		addressField.setText(customer.getAddress());
		phoneField.setText(customer.getPhone());
		qqField.setText(customer.getQq());
		weixinField.setText(customer.getWeixin());
		remarkField.setText(customer.getRemark());


	}

	@FXML
	private void initialize() {
		expiredBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			{
				if(newValue)
				{
					isRevoke.setDisable(true);
				}
				else
				{
					isRevoke.setDisable(false);
				}

			}
		});
		isRevoke.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
			{
				if(newValue)
				{
					expiredBox.setDisable(true);
				}
				else
				{
					expiredBox.setDisable(false);
				}

			}
		});
	}

	/**
	 * 修改客户信息
	 */
	@FXML
	private void handleEditCustomer()
	{

		// 首先验证输入数据
		String oldRank = customer.getRank();
		String selectedRank = rankBox.getSelectionModel().getSelectedItem();
		if(!selectedRank.equals(oldRank))
		{
			if(!MessageUtil.alertConfirm("提示", "确认变更级别吗？"))
			{
				return;
			}
			else
			{
				customer.setRank(selectedRank);
			}
		}
		if(expiredBox.isSelected())
		{
			if(!MessageUtil.alertConfirm("提示", "确认授权过期吗？"))
			{
				return;
			}
			else
			{
				customer.setState("过期");
			}
		}
		if(isRevoke.isSelected())
		{
			if(!MessageUtil.alertConfirm("提示", "确认资格撤销吗？"))
			{
				return;
			}
			else
			{
				customer.setState("撤销");
			}
		}

		customer.setCustomerName(customernameField.getText());
		customer.setSex(sexBox.getSelectionModel().getSelectedItem());
		customer.setIdcard(idcardField.getText());
		customer.setAddress(addressField.getText());
		customer.setPhone(phoneField.getText());
		customer.setQq(qqField.getText());
		customer.setWeixin(weixinField.getText());
		customer.setRemark(remarkField.getText());

		if (DataValicateUtil.isInputValid(customer)) {

			SqlSession sqlSession = null;
			try {
				sqlSession = MybatisUtils.getSqlSession(false);
				int result = 0;
				String message = "";
				try {
					result = sqlSession.update("com.xidian.CustomerXml.updateCustomer", customer);

					if(isRevoke.isSelected())
					{
						//更新信息变更表
						UpdateInfo updateInfo = new UpdateInfo();
						updateInfo.setAuid(customer.getAuid());
						updateInfo.setState("撤销");
						updateInfo.setRank(oldRank);
						updateInfo.setUpdateReason("撤销");
						LocalDateTime now = LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now()));
						updateInfo.setUpdateTime(now);

						sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);
					}

					if(expiredBox.isSelected())
					{
						//更新信息变更表
						UpdateInfo updateInfo = new UpdateInfo();
						updateInfo.setAuid(customer.getAuid());
						updateInfo.setState("过期");
						updateInfo.setRank(oldRank);
						updateInfo.setUpdateReason("过期");
						LocalDateTime now = LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now()));
						updateInfo.setUpdateTime(now);

						sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);
					}
					if(!selectedRank.equals(oldRank))
					{
						UpdateInfo updateInfo = new UpdateInfo();
						updateInfo.setAuid(customer.getAuid());
						updateInfo.setState("注册");
						updateInfo.setRank(oldRank);
						updateInfo.setUpdateReason("手动变更级别：由"+oldRank+"变为"+selectedRank);
						LocalDateTime now = LocalDateTimeUtil.parse(LocalDateTimeUtil.format(LocalDateTime.now()));
						updateInfo.setUpdateTime(now);

						sqlSession.insert("com.xidian.UpdateInfoXml.addUpdateInfo", updateInfo);
					}

					sqlSession.commit();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					message = "修改失败！";
				}
				finally{
					sqlSession.close();
				}

				if (result == 1)// 修改成功
				{
					message = "修改成功！";
				}
				else {
					message = "修改失败！";
				}
				MessageUtil.alertInfo(message);

				editStage.close();// 关闭修改客户信息界面
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
