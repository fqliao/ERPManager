package com.xidian.view.rank;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.rank.Rank;

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
import javafx.util.Callback;

/**查询，修改,删除收件地址控制器
 * @author lfq
 *
 */
public class QueryRankController {

	@FXML
	private TextField rankField;

	@FXML
	private TableView<Rank> rankTable;

	@FXML
	private TableColumn<Rank, String> rankColumn;

	@FXML
	private TableColumn<Rank, Integer> productNumColumn;

	@FXML
	private TableColumn<Rank, Integer> productPriceColumn;

	@FXML
	private TableColumn<Rank, Integer> productSumColumn;

	@FXML
	private AnchorPane editAnchorPane;

	private MainApp mainApp;

	private ObservableList<Rank> rankData = FXCollections.observableArrayList();

	private QueryRankController queryRankController;

	public QueryRankController() {

	}

	public void setQueryRankController(QueryRankController queryRankController){
		this.queryRankController = queryRankController;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public TableView<Rank> getRankTable()
	{
		return rankTable;
	}

	@FXML
	private void initialize() {

	}

	/**定义列的点击事件类
	 * @author lfq
	 *
	 */
	private class RankStringCellFactory implements Callback<TableColumn<Rank, String>, TableCell<Rank, String>> {

	    @Override
	    public TableCell<Rank, String> call(TableColumn<Rank, String> param) {
	        TextFieldTableCell<Rank, String> cell = new TextFieldTableCell<>();
	        cell.setOnMouseClicked((MouseEvent t) -> {
	            if (t.getClickCount() == 2) {
	            	Rank selectedRank = rankTable.getSelectionModel().getSelectedItem();
	            	if(selectedRank != null)
	            	{
	            		mainApp.showEditRank(selectedRank, queryRankController);
	            	}
	            }
	        });
	        return cell;
	    }

	}
	private class RankIntegerCellFactory implements Callback<TableColumn<Rank, Integer>, TableCell<Rank, Integer>> {

		@Override
		public TableCell<Rank, Integer> call(TableColumn<Rank, Integer> param) {
			TextFieldTableCell<Rank, Integer> cell = new TextFieldTableCell<>();
			cell.setOnMouseClicked((MouseEvent t) -> {
				if (t.getClickCount() == 2) {
					Rank selectedRank = rankTable.getSelectionModel().getSelectedItem();
					if(selectedRank != null)
					{
	            		mainApp.showEditRank(selectedRank, queryRankController);
					}
				}
			});
			return cell;
		}

	}

	/**
	 * 查询地址信息
	 */
	@FXML
	private void handleQueryRank() {
		// 清空表中的数据
		rankTable.getItems().clear();

		String rankString = rankField.getText();

		SqlSession sqlSession = mainApp.getSqlSession(true);

		List<Rank> ranks;
		// 通过授权号查询客户信息
		if (!"".equals(rankString.trim())) {
			ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getRank", "%"+rankString.trim()+"%");
			rankData.addAll(ranks);
		} else {
			//如果没有查询信息，则全部查询
			ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getAllRank");
			rankData.addAll(ranks);
		}

		//表中放数据
		rankTable.setItems(rankData);

		//设置显示过滤列的菜单按钮
		rankTable.setTableMenuButtonVisible(true);

		// 设置列中的文本居中
		rankColumn.setStyle( "-fx-alignment: CENTER;");
		productNumColumn.setStyle( "-fx-alignment: CENTER;");
		productPriceColumn.setStyle( "-fx-alignment: CENTER;");
		productSumColumn.setStyle("-fx-alignment: CENTER;");

		// 将数据放入表中的列
		rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
		productNumColumn.setCellValueFactory(cellData -> cellData.getValue().productNumProperty().asObject());
		productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
		productSumColumn.setCellValueFactory(cellData -> cellData.getValue().productSumProperty().asObject());

		//设置每一列的双击事件
		RankStringCellFactory rankStringCellFactory = new RankStringCellFactory();
		RankIntegerCellFactory rankIntegerCellFactory = new RankIntegerCellFactory();
		rankColumn.setCellFactory(rankStringCellFactory);
		productNumColumn.setCellFactory(rankIntegerCellFactory);
		productPriceColumn.setCellFactory(rankIntegerCellFactory);
		productSumColumn.setCellFactory(rankIntegerCellFactory);

	}

}
