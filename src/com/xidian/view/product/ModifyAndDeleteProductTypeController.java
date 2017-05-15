package com.xidian.view.product;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.product.Product;
import com.xidian.util.MessageUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModifyAndDeleteProductTypeController {

	@FXML
	private TextField modifyProductTypeField;

	@FXML
	private AnchorPane editAnchorPane;

	private Stage editStage;

	private MainApp mainApp;

	private EditProductTypeController editProductTypeController;

	private Product prodectSelect;

	public void setProdectSelect(Product prodectSelect){
		this.prodectSelect = prodectSelect;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage editStage){
		this.editStage = editStage;
	}

	public void setEditProductTypeController(EditProductTypeController editProductTypeController){
		this.editProductTypeController = editProductTypeController;

	}

	/**
	 * 设置产品名
	 */
	public void setProductName(Product product){
		modifyProductTypeField.setText(product.getProducttype());
	}


	/**
	 * 修改产品名
	 */
	@FXML
	private void modifyProduct(){
		String modifyProduct = modifyProductTypeField.getText();

		//空检测
		if("".equals(modifyProduct.trim())){
			MessageUtil.alertInfo("请输入产品名！");
			return;
		}

		//检测是否未修改
		if(prodectSelect.getProducttype().equals(modifyProduct.trim())){
			MessageUtil.alertInfo("没有修改产品名！");
			return;
		}

		//检测产品名是否重复
		SqlSession sqlSession = mainApp.getSqlSession(true);
		List<Product> products;
		boolean flag = true;
		products = sqlSession.selectList("com.xidian.model.product.ProductXml.getProductAll");
		for(Product eachProduct : products){
			if(eachProduct.getProducttype().equals(modifyProduct.trim())&&!prodectSelect.getProducttype().equals(modifyProduct.trim())){
				flag = false;
			}
		}
		if(!flag){
			MessageUtil.alertInfo("该产品名已经存在！");
			return;
		}

		//修改产品名
		prodectSelect.setOldproducttype(prodectSelect.getProducttype());
		prodectSelect.setProducttype(modifyProduct);
		int modifyProductResult = sqlSession.update("com.xidian.model.product.ProductXml.updateProductType",prodectSelect);

		//修改相关的代理等级
		int modifyRankResult = sqlSession.update("com.xidian.model.rank.RankXml.updateRankByProduct",prodectSelect);

		if(modifyProductResult == 1){
	       MessageUtil.alertInfo("修改成功！");
//	       editProductTypeController.setData();
	       editStage.close();
	    }
	    else{
	    	MessageUtil.alertInfo("修改失败！");
	    	editStage.close();
	    }


	}

	/**
	 * 删除产品
	 */
	@FXML
	private void deleteProduct(){
		SqlSession sqlSession = mainApp.getSqlSession(true);
		int deleteProductResult = sqlSession.delete("com.xidian.model.product.ProductXml.deleteProductType",prodectSelect);

		//删除相关的代理等级
		int deleteRankResult = sqlSession.delete("com.xidian.model.rank.RankXml.deleteRankByProduct",prodectSelect);

		if(deleteProductResult == 1){
	       MessageUtil.alertInfo("删除成功！");
	       editProductTypeController.setData();
	       editStage.close();
	    }
	    else{
	    	MessageUtil.alertInfo("删除失败！");
	    	editStage.close();
	    }
	}

}
