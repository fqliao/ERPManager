package com.xidian.view.product;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.product.Product;
import com.xidian.util.MessageUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EditProductTypeController {

	@FXML
	private TableView<Product> productTable;

	@FXML
	private TableColumn<Product, String> producttypeColumn;

	@FXML
	private TextField newProductTypeField;

	@FXML
	private TextField modifyProductTypeField;

	@FXML
	private AnchorPane editAnchorPane;


	private Stage editStage;

	private MainApp mainApp;

	private EditProductTypeController editProductTypeController;

	private ObservableList<Product> productData = FXCollections.observableArrayList();

	public EditProductTypeController(){

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

	@FXML
	private void initialize() {

	}

	public void setData()
	{
		// 清空表中的数据
		productTable.getItems().clear();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		List<Product> products;

		products = sqlSession.selectList("com.xidian.model.product.ProductXml.getProductAll");

		productData.addAll(products);

		//表中放数据
		productTable.setItems(productData);

		//设置显示过滤列的菜单按钮
		productTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		producttypeColumn.setStyle( "-fx-alignment: CENTER;");

		// 将数据放入表中的列
		producttypeColumn.setCellValueFactory(cellData -> cellData.getValue().producttypeProperty());

		//设置每一列的双击事件
		productStringCellFactory productStringCellFactory = new productStringCellFactory();
		producttypeColumn.setCellFactory(productStringCellFactory);
	}
	/**
	 * 定义列的点击事件
	 */
	private class productStringCellFactory implements Callback<TableColumn<Product, String>, TableCell<Product, String>> {

	    @Override
	    public TableCell<Product, String> call(TableColumn<Product, String> param) {
	        TextFieldTableCell<Product, String> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
	            	if(selectedProduct != null)
	            	{
	            		mainApp.showModifyAndDeleteProduct(selectedProduct, editProductTypeController);
	            	}
	            }
	        });
	        return cell;
	    }

	}

	/**
	 * 定义产品类型增加事件
	 */
	@FXML
	private void handleAddProductType(){
		mainApp.showAddProduct(this);
	}

	@FXML
	private void addProductType(){
	String newProduct = newProductTypeField.getText();

	if("".equals(newProduct.trim())){
		MessageUtil.alertInfo("请输入新的产品名！");
		return;
	}

	//检测产品名是否重复
	SqlSession sqlSession = mainApp.getSqlSession(true);
	List<Product> products;
	boolean flag = true;
	products = sqlSession.selectList("com.xidian.model.product.ProductXml.getProductAll");
	for(Product eachProduct : products){
		if(eachProduct.getProducttype().equals(newProduct.trim())){
			flag = false;
		}
	}
	if(!flag){
		MessageUtil.alertInfo("该产品名已经存在！");
		return;
	}

	//增加新的产品
	int addProductResult = sqlSession.insert("com.xidian.model.product.ProductXml.addProductType",newProduct);
    if(addProductResult == 1){
       MessageUtil.alertInfo("保存成功！");
       editProductTypeController.setData();
       editStage.close();
    }
	}

}
