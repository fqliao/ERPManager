package com.xidian.view.rank;

import java.time.LocalDateTime;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.rank.Rank;
import com.xidian.util.LocalDateTimeUtil;
import com.xidian.util.MessageUtil;
import com.xidian.util.SingletonData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**查询，修改,删除收件地址控制器
 * @author lfq
 *
 */
public class EditRankController {

	//修改级别信息

	@FXML
	private TextField rankField;

	@FXML
	private TextField productNumField;

	@FXML
	private TextField productPriceField;

	@FXML
	private TextField productSumField;

	@FXML
	private TextField producttypeField;

	@FXML
	private TextField createtimeField;

	@FXML
	private TextField updatetimeField;

	@FXML
	private AnchorPane editAnchorPane;

	@FXML
	private Button editRankButton;

	@FXML
	private Button deleteRankButton;

	private MainApp mainApp;

	private Rank rank;

	private Stage addStage;

	private QueryRankController queryRankController;

	private String patternString = "^[1-9]*[1-9][0-9]*$";

	public EditRankController() {

	}

	public void setQueryRankController(QueryRankController queryRankController){
		this.queryRankController = queryRankController;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage addStage) {
		this.addStage = addStage;
	}

	public void setRank(Rank rank)
	{
		//设置客户数据到修改表单
		this.rank = rank;
		rankField.setText(rank.getRank());
		producttypeField.setText(rank.getProducttype());
		createtimeField.setText(LocalDateTimeUtil.format(rank.getCreatetime()));
		updatetimeField.setText(LocalDateTimeUtil.format(rank.getUpdatetime()));
		productNumField.setText(rank.getProductNum()+"");
		productPriceField.setText(rank.getProductPrice()+"");
		productSumField.setText(rank.getProductSum()+"");

	}

	@FXML
	private void initialize() {

		if("普通用户".equals(SingletonData.getSingletonData().getManagerUser().getTypeuser()))
		{
			editRankButton.setVisible(false);
			deleteRankButton.setVisible(false);
			productNumField.setEditable(false);
			productPriceField.setEditable(false);
			productSumField.setEditable(false);
		}

		if(productNumField != null)
		{
			//对productNum设置监听事件
			productNumField.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldNum,  String newNum) {
					String num = newNum.trim();
					if(num.matches(patternString))
					{
						caculateSum1(num);
					}
					else
					{
						MessageUtil.alertInfo("数量必须为正整数！");
					}

				}

				private void caculateSum1(String num)
				{
					String price = productPriceField.getText().trim();
					if(price.matches(patternString))
					{
						productSumField.setText((Integer.parseInt(num) * Integer.parseInt(price) + ""));
					}

				}
			});
		}
		if(productPriceField != null)
		{
			//对productNum设置监听事件
			productPriceField.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldPrice,  String newPrice) {
					String price = newPrice.trim();
					if(price.matches(patternString))
					{
						caculateSum2(price);
					}
					else
					{
						MessageUtil.alertInfo("单价必须为正整数！");
					}

				}

				private void caculateSum2(String price)
				{
					String num = productNumField.getText().trim();
					if(num.matches(patternString))
					{
						productSumField.setText((Integer.parseInt(price) * Integer.parseInt(num) + ""));
					}

				}
			});
		}
	}

	/**
	 * 修改级别信息
	 */
	@FXML
	private void handleEditRank()
	{
		String rankString = rankField.getText().trim();
		String productNumString = productNumField.getText().trim();
		String productPriceString =productPriceField.getText().trim();
		String productTypeString = producttypeField.getText().trim();

		int productNum = 0;
		int productPrice = 0;


		boolean flag = true; //用户输入是否正确标志位
		SqlSession sqlSession = mainApp.getSqlSession(true);

		if("".equals(productNumString)){
			flag = false;
			MessageUtil.alertInfo("请输入数量！");
			return;
		}
		else
		{
			if(!productNumString.matches(patternString))
			{
				MessageUtil.alertInfo("数量必须为正整数！");
				return;
			}
			else
			{
				productNum = Integer.parseInt(productNumString);
			}
		}

		if("".equals(productPriceString)){
			flag = false;
			MessageUtil.alertInfo("请输入单价！");
			return;
		}
		else
		{
			if(!productPriceString.matches(patternString))
			{
				MessageUtil.alertInfo("单价必须为正整数！");
				return;
			}
			else
			{
				productPrice = Integer.parseInt(productPriceString);
			}
		}

		if(flag)
		{
			rank.setRank(rankString);
			rank.setProductNum(productNum);
			rank.setProductPrice(productPrice);
			int productSum = productNum * productPrice;
			rank.setProductSum(productSum);
			LocalDateTime now = LocalDateTimeUtil.getNow();
			rank.setUpdatetime(now);
			rank.setProducttype(productTypeString);

			int updateRankResult = 0;
			String message = "";

			StringBuilder messageConfirm = new StringBuilder();
			messageConfirm.append("级别：" + rankString);
			messageConfirm.append("\n数量(元)：" + productNum);
			messageConfirm.append("\n单价(元)：" + productPrice);
			messageConfirm.append("\n货款(元)：" + productSum);
			if(!MessageUtil.alertConfirm("确认级别信息", messageConfirm.toString()))
			{
				return;
			}
			try
			{
				updateRankResult = sqlSession.update("com.xidian.model.rank.RankXml.updateRank", rank);
			}
			catch(Exception e)
			{
				message = "修改失败!\n";
			}
			finally {
				sqlSession.close();
			}
			if (updateRankResult == 1)
			{
				message = "修改成功!";
			}
			else
			{
				message = "修改失敗!\n";
			}
			MessageUtil.alertInfo(message);
		}
		addStage.close();

	}

	@FXML
	private void handleDeleteRank()
	{



		if(!MessageUtil.alertConfirm("提示", "确认删除吗"))
		{
			return;
		}

		String message = "";

		SqlSession sqlSession = mainApp.getSqlSession(true);
		int deleteResult = 0;
		try
		{
			deleteResult = sqlSession.delete("com.xidian.model.rank.RankXml.deleteRank", rank);
		}
		catch(Exception e)
		{
			message = "删除失败！";
		}
		finally
		{
			sqlSession.close();
		}

		if(deleteResult != 0)
		{
			message = "删除成功！";
			queryRankController.getRankTable().getItems().remove(rank);

		}
		else
		{
			message = "删除失败！";
		}
		MessageUtil.alertInfo(message);
		addStage.close();
	}

}
