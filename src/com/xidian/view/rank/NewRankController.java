package com.xidian.view.rank;


import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.rank.Rank;
import com.xidian.util.LocalDateTimeUtil;
import com.xidian.util.MessageUtil;
import com.xidian.util.MybatisUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * 保存收货地址的控制器
 *
 * @author lfq
 *
 */
public class NewRankController {

	@FXML
	private ComboBox<String> rankBox;

	@FXML
	private TextField productNumField;

	@FXML
	private TextField productPriceField;

	@FXML
	private ComboBox<String> producttypeBox;

	@FXML
	private TextField productSumField;

	// @FXML
	// private AnchorPane editAnchorPane;
	//
	private MainApp mainApp;
	private Rank rank;
	private String patternString = "^[1-9]*[1-9][0-9]*$";

	public NewRankController() {

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {

		SqlSession sqlSession = MybatisUtils.getSqlSession(true);
		List<String> productType;
		productType = sqlSession.selectList("com.xidian.model.product.ProductXml.getProductNameAll");
		producttypeBox.getItems().addAll(productType);
		producttypeBox.getSelectionModel().select(productType.get(0));

		List<String> ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getRankOfRank");
		rankBox.getItems().removeAll(rankBox.getItems());
		rankBox.getItems().addAll(ranks);
		rankBox.getSelectionModel().selectFirst();

		sqlSession.close();

		if(productNumField != null)
		{
			//对productNum设置监听事件
			productNumField.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldNum,  String newNum) {
					if(rank == null)
					{
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
					if(rank == null)
					{
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
	 * 新建客户
	 */
	@FXML
	private void handleSaveRank() {

		String rankString = rankBox.getSelectionModel().getSelectedItem().trim();
		String productNumString = productNumField.getText().trim();
		String productPriceString =productPriceField.getText().trim();
		String producttypeString = producttypeBox.getSelectionModel().getSelectedItem();

		Rank rankTest = new Rank();
		rankTest.setProducttype(producttypeString);
		rankTest.setRank(rankString);

		int productNum = 0;
		int productPrice = 0;


		boolean flag = true; //用户输入是否正确标志位
		SqlSession sqlSession = mainApp.getSqlSession(true);

		if("".equals(rankString)){
			flag = false;
			MessageUtil.alertInfo("请输入级别！");
			return;
		}
		else{

			int count = sqlSession.selectOne("com.xidian.model.rank.RankXml.getCountByProductAndRankname", rankTest);
			if(count != 0){
				flag = false;
				MessageUtil.alertInfo("该级别存在！");
				return;
			}
		}

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
			rank = new Rank();
			rank.setRank(rankString);
			rank.setProductNum(productNum);
			rank.setProductPrice(productPrice);
			int productSum = productNum * productPrice;
			rank.setProductSum(productSum);
			LocalDateTime now = LocalDateTimeUtil.getNow();
			rank.setCreatetime(now);
			rank.setUpdatetime(now);
			rank.setProducttype(producttypeString);

			int addRankResult = 0;
			String message = "";

			StringBuilder messageConfirm = new StringBuilder();
			messageConfirm.append("产品类别：" + producttypeString);
			messageConfirm.append("\n级别：" + rankString);
			messageConfirm.append("\n数量(元)：" + productNum);
			messageConfirm.append("\n单价(元)：" + productPrice);
			messageConfirm.append("\n货款(元)：" + productSum);
			if(!MessageUtil.alertConfirm("确认级别信息", messageConfirm.toString()))
			{
				return;
			}
			try
			{
				addRankResult = sqlSession.insert("com.xidian.model.rank.RankXml.addRank", rank);
			}
			catch(Exception e)
			{
				message = "保存失败!\n";
				e.printStackTrace();
			}
			finally {
				sqlSession.close();
			}
			if (addRankResult == 1)
			{
				message = "保存成功!";
				SqlSession sqlSession2 = MybatisUtils.getSqlSession(true);
				List<String> ranks = sqlSession2.selectList("com.xidian.model.rank.RankXml.getRankOfRank");
				sqlSession2.close();
				rankBox.getItems().removeAll(rankBox.getItems());
				rankBox.getItems().addAll(ranks);
				rankBox.getSelectionModel().selectFirst();
				productNumField.setText("");
				productPriceField.setText("");
				productSumField.setText("");
				rank = null;
			}
			else
			{
				message = "保存失敗!\n";
			}
			MessageUtil.alertInfo(message);
		}
	}

}
