package com.xidian.view.rank;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.order.Order;
import com.xidian.model.rank.Rank;
import com.xidian.util.MessageUtil;
import com.xidian.util.MybatisUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
	private ComboBox<String> rankBox;

	@FXML
	private ComboBox<String> productTypeBox;

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
	private TableColumn<Rank, String> producttypeColumn;

	@FXML
	private TableColumn<Rank, LocalDateTime> createtimeColumn;

	@FXML
	private TableColumn<Rank, LocalDateTime> updatetimeColumn;

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
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisUtils.getSqlSession(true);
			//查询代理级别
			List<String> ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getRankOfRank");
			rankBox.getItems().removeAll(rankBox.getItems());
			rankBox.getItems().add("请选择");
			rankBox.getItems().addAll(ranks);
			rankBox.getSelectionModel().selectFirst();

			//查询产品类别
			List<String> productTypes = sqlSession.selectList("com.xidian.model.product.ProductXml.getProductNameAll");
			productTypeBox.getItems().removeAll(productTypeBox.getItems());
			productTypeBox.getItems().add("请选择");
			productTypeBox.getItems().addAll(productTypes);
			productTypeBox.getSelectionModel().selectFirst();
		}
		catch (Exception e)
		{
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
		finally
		{
			sqlSession.close();
		}

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
	private class RankLocalDateTimeCellFactory implements Callback<TableColumn<Rank, LocalDateTime>, TableCell<Rank, LocalDateTime>> {

		@Override
		public TableCell<Rank, LocalDateTime> call(TableColumn<Rank, LocalDateTime> param) {
			TextFieldTableCell<Rank, LocalDateTime> cell = new TextFieldTableCell<>();
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

		String rankString = rankBox.getSelectionModel().getSelectedItem().trim();
		String productTypeString = productTypeBox.getSelectionModel().getSelectedItem().trim();

		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisUtils.getSqlSession(true);
			Rank rank = new Rank();
			if(!"请选择".equals(rankString))
			{
				rank.setRank(rankString);
			}
			else
			{
				rank.setRank("");
			}
			if(!"请选择".equals(productTypeString))
			{
				rank.setProducttype(productTypeString);
			}
			else
			{
				rank.setProducttype("");
			}
			List<Rank> ranks;
			ranks = sqlSession.selectList("com.xidian.model.rank.RankXml.getRank", rank);
			rankData.addAll(ranks);

			//表中放数据
			rankTable.setItems(rankData);

			//设置显示过滤列的菜单按钮
			rankTable.setTableMenuButtonVisible(true);

			// 设置列中的文本居中
			rankColumn.setStyle( "-fx-alignment: CENTER;");
			productNumColumn.setStyle( "-fx-alignment: CENTER;");
			productPriceColumn.setStyle( "-fx-alignment: CENTER;");
			productSumColumn.setStyle("-fx-alignment: CENTER;");
			producttypeColumn.setStyle("-fx-alignment: CENTER;");
			createtimeColumn.setStyle("-fx-alignment: CENTER;");
			updatetimeColumn.setStyle("-fx-alignment: CENTER;");

			// 将数据放入表中的列
			rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty());
			productNumColumn.setCellValueFactory(cellData -> cellData.getValue().productNumProperty().asObject());
			productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
			productSumColumn.setCellValueFactory(cellData -> cellData.getValue().productSumProperty().asObject());
			producttypeColumn.setCellValueFactory(cellData -> cellData.getValue().producttypeProperty());
			createtimeColumn.setCellValueFactory(cellData -> cellData.getValue().createtimeProperty());
			updatetimeColumn.setCellValueFactory(cellData -> cellData.getValue().updatetimeProperty());

			//设置每一列的双击事件
			RankStringCellFactory rankStringCellFactory = new RankStringCellFactory();
			RankIntegerCellFactory rankIntegerCellFactory = new RankIntegerCellFactory();
			RankLocalDateTimeCellFactory rankLocalDateTimeCellFactory = new RankLocalDateTimeCellFactory();
			rankColumn.setCellFactory(rankStringCellFactory);
			productNumColumn.setCellFactory(rankIntegerCellFactory);
			productPriceColumn.setCellFactory(rankIntegerCellFactory);
			productSumColumn.setCellFactory(rankIntegerCellFactory);
			producttypeColumn.setCellFactory(rankStringCellFactory);
			createtimeColumn.setCellFactory(rankLocalDateTimeCellFactory);
			updatetimeColumn.setCellFactory(rankLocalDateTimeCellFactory);
		}
		catch (Exception e)
		{
			MessageUtil.alertInfo("请检查您是否有网络！");
		}
		finally
		{
			sqlSession.close();
		}

	}

}
