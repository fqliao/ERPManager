package com.xidian;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;

import com.xidian.model.address.Address;
import com.xidian.model.balance.UpdateBalance;
import com.xidian.model.customer.Customer;
import com.xidian.model.evaluate.QEvaluateTime;
import com.xidian.model.evaluate.YEvaluateTime;
import com.xidian.model.order.Order;
import com.xidian.model.product.Product;
import com.xidian.model.rank.Rank;
import com.xidian.model.system.ManagerUser;
import com.xidian.model.updateinfo.UpdateInfo;
import com.xidian.util.MybatisUtils;
import com.xidian.view.MainController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.xidian.view.address.NewAddressController;
import com.xidian.view.address.QueryAddressController;
import com.xidian.view.balance.CustomerBalanceUpdateController;
import com.xidian.view.customer.EditCustomerController;
import com.xidian.view.customer.NewCustomerController;
import com.xidian.view.customer.QueryCustomerController;
import com.xidian.view.order.NewOrderController;
import com.xidian.view.order.QueryOrderController;
import com.xidian.view.product.EditProductTypeController;
import com.xidian.view.product.ModifyAndDeleteProductTypeController;
import com.xidian.view.rank.EditRankController;
import com.xidian.view.rank.NewRankController;
import com.xidian.view.rank.QueryRankController;
import com.xidian.view.system.ConfirmPasswordController;
import com.xidian.view.system.EditManagerUserController;
import com.xidian.view.system.LoginController;
import com.xidian.view.system.NewManagerUserController;
import com.xidian.view.system.PasswordChangeController;
import com.xidian.view.system.QueryManagerUserController;
import com.xidian.view.updateinfo.QueryUpdateInfoController;

/**程序入口类，管理界面，控制器
 * @author lfq
 *
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private Stage mainStage;
	private AnchorPane loginView;
	private List<Address> address;

	private  static  String BACKUP_DIR;
	private  static  String HOSTNAME;
	private  static  String DB_USER;
	private  static  String DB_PWD;
	private  static  String DB_NAME;

	public MainApp() {
		super();
	}

	public List<Address> getAddress()
	{
		return this.address;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public SqlSession getSqlSession(boolean flag) throws Exception
	{
		return MybatisUtils.getSqlSession(flag);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("登录");
		 // Set the application icon.
	    this.primaryStage.getIcons().add(new Image("file:resources/images/person.png"));

	    //正式
	    showLoginView();

	    timerBackupDB();
	}


	 public static void timerBackupDB()
	    {
		    try {
				backupDB();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//	        Runnable runnable = new Runnable() {
//	            public void run() {
//	                try {
//						backupDB();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//	            }
//	        };
//	        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//	        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
//	        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.DAYS);
	    }

	    public static void backupDB() throws IOException, InterruptedException{

			Properties prop = new Properties();
			InputStream in = MainApp.class.getClassLoader().getResourceAsStream("db.properties");
			try {
				prop.load(in);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			BACKUP_DIR = prop.getProperty("backdir");
			HOSTNAME = prop.getProperty("hostname");
			DB_USER = prop.getProperty("username");
			DB_PWD = prop.getProperty("password");
			DB_NAME = prop.getProperty("dbname");

			deleteThreeDaysAgoFile(BACKUP_DIR); //删除前三条的备份数据

	      String savePath = BACKUP_DIR + "manager-backup-" + LocalDate.now().toString() + ".sql";
	      String[] execCMD = new String[] {"mysqldump", "-h" + HOSTNAME, "-u" + DB_USER, "-p" + DB_PWD, DB_NAME,
	              "-r" + savePath, "--skip-lock-tables"};
	      Runtime.getRuntime().exec(execCMD);
	  }

		private static void deleteThreeDaysAgoFile(String directorySql) {

	        File managerData = new File(directorySql);
	    	File[] listSqlFiles = null;
	    	if(managerData!=null){
	            if(managerData.isDirectory()){
	            	listSqlFiles = managerData.listFiles();
	            }
	        }
	    	if(listSqlFiles != null)
	    	{
	            for (File file : listSqlFiles)
	            {
	    			LocalDate now = LocalDate.now();
	    			String path = file.getPath();
	    			if(!(path.contains(now.toString()) ||
	    			   path.contains(now.minusDays(1).toString())||
	    			   path.contains(now.minusDays(2).toString())))
	    			{

	    			}
	    		}
	    	}
	    	else
	    	{
	    		managerData.mkdir();
	    	}
		}

	    /**
	     * 删除单个文件
	     *
	     * @param fileName
	     *            要删除的文件的文件名
	     * @return 单个文件删除成功返回true，否则返回false
	     */
	    public static boolean deleteFile(String fileName) {
	        File file = new File(fileName);
	        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	        if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	                System.out.println("删除单个文件" + fileName + "成功！");
	                return true;
	            } else {
	                System.out.println("删除单个文件" + fileName + "失败！");
	                return false;
	            }
	        } else {
	            System.out.println("删除单个文件失败：" + fileName + "不存在！");
	            return false;
	        }
	    }
	/**
	 * 显示登录界面
	 */
	public void showLoginView() {
	    try {
	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class
	                .getResource("view/system/Login.fxml"));
	        loginView = (AnchorPane) loader.load();

	        // Show the scene containing the root layout.
	        Scene scene = new Scene(loginView);
	        primaryStage.setScene(scene);

	        // Give the controller access to the main app.
	        LoginController loginController = loader.getController();
	        loginController.setMainApp(this);

	        primaryStage.show();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}

    public Stage getPrimaryStage() {
        return primaryStage;
    }

	/**
	 * 显示主界面
	 */
	public void showMainWindow() {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/Main.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			primaryStage.close();//关闭登录界面
			mainStage = new Stage();
			mainStage.setTitle("管理界面");
			mainStage.initModality(Modality.WINDOW_MODAL);
			mainStage.getIcons().add(new Image("file:resources/images/person.png"));

			Scene scene = new Scene(page);
			mainStage.setScene(scene);

			MainController mainController = loader.getController();
			mainController.setMainApp(this);

			mainStage.setMaximized(true);
			mainStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示新建客户信息界面
	 * @param anchorPaneContent
	 */
	public void showNewCustomer(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/customer/NewCustomer.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			NewCustomerController newCustomerController = loader.getController();
			newCustomerController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示查询客户信息界面
	 * @param anchorPaneContent
	 */
	public void showQueryCustomer(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/customer/QueryCustomer.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加查询内容
			anchorPaneContent.getChildren().add(page);

			QueryCustomerController queryCustomerController = loader.getController();
			queryCustomerController.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示查询客户信息界面
	 * @param anchorPaneContent
	 */
	public void showQueryUpdateInfo(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/updateinfo/QueryUpdateInfo.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加查询内容
			anchorPaneContent.getChildren().add(page);

			QueryUpdateInfoController queryUpdateInfoController = loader.getController();
			queryUpdateInfoController.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示修改客户信息界面
	 * @param customer
	 */
	public void showEditCustomer(Customer customer) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/customer/EditCustomer.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("修改客户信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			EditCustomerController editCustomerController = loader.getController();
			editCustomerController.setCustomer(customer);
			editCustomerController.setEditStage(editStage);

			editStage.showAndWait();

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showUpdateInfo(UpdateInfo selectedUpdateInfo) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/updateinfo/ShowUpdateInfo.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage addStage = new Stage();
			addStage.setTitle("变更客户信息");
			addStage.initModality(Modality.WINDOW_MODAL);
			addStage.initOwner(mainStage);
			addStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			addStage.setScene(scene);

			QueryUpdateInfoController queryUpdateInfoController = loader.getController();
			queryUpdateInfoController.setMainApp(this);
			queryUpdateInfoController.setUpdateInfo(selectedUpdateInfo);
			queryUpdateInfoController.setEditStage(addStage);

			addStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showNewAddress(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/address/NewAddress.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			NewAddressController newAddressController = loader.getController();
			newAddressController.setMainApp(this);
		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void showNewRank(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/rank/NewRank.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			NewRankController newRankController = loader.getController();
			newRankController.setMainApp(this);
		}
		catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void showQueryAddress(AnchorPane anchorPaneContent, List<Address> address, NewOrderController newOrderController)
	{
		try
		{

			if(anchorPaneContent != null)
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/address/QueryAddress.fxml"));
				AnchorPane page = (AnchorPane)loader.load();
				mainStage.setMaximized(true);
				//先移除面板中的内容
				anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
				//增加查询内容
				anchorPaneContent.getChildren().add(page);

				QueryAddressController queryAddressController = loader.getController();
				queryAddressController.setMainApp(this);
				queryAddressController.setQueryAddressController(queryAddressController);
			}
			else
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(MainApp.class.getResource("view/order/QueryAddressByAuid.fxml"));
				AnchorPane page = (AnchorPane)loader.load();
				this.address = address;
				Scene scene = new Scene(page);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initOwner(mainStage);
				stage.initModality(Modality.WINDOW_MODAL);

				NewOrderController newOrderController2 = loader.getController();
				newOrderController2.setMainApp(this);
				newOrderController2.setNewOrderController(newOrderController);
				newOrderController2.showAddress();
				newOrderController2.setQueryAddressStage(stage);
				stage.showAndWait();
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showQueryRank(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/rank/QueryRank.fxml"));
			AnchorPane page = (AnchorPane)loader.load();
			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加查询内容
			anchorPaneContent.getChildren().add(page);

			QueryRankController queryRankController = loader.getController();
			queryRankController.setQueryRankController(queryRankController);
			queryRankController.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showEditAddress(Address selectedAddress, QueryAddressController queryAddressController2)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/address/EditAddress.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("收件地址信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			QueryAddressController queryAddressController = loader.getController();
			queryAddressController.setMainApp(this);
			queryAddressController.setAddress(selectedAddress);
			queryAddressController.setQueryAddressController(queryAddressController2);
			queryAddressController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showEditRank(Rank rank, QueryRankController queryRankController)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/rank/EditRank.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("级别信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			EditRankController editRankController = loader.getController();
			editRankController.setMainApp(this);
			editRankController.setRank(rank);
			editRankController.setQueryRankController(queryRankController);
			editRankController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示新建订单界面
	 * @param anchorPaneContent
	 */
	public void showNewOrder(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/order/NewOrder.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			NewOrderController newOrderController = loader.getController();
			newOrderController.setMainApp(this);
			newOrderController.setNewOrderController(newOrderController);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示查询订单界面
	 * @param anchorPaneContent
	 */
	public void showQueryOrder(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/order/QueryOrder.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			QueryOrderController queryOrderController = loader.getController();
			queryOrderController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示单个详细订单信息界面
	 * @param order
	 */
	public void showOrderInfo(Order order) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/order/QueryOrderInfo.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("订单信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			QueryOrderController queryOrderController = loader.getController();
			queryOrderController.setMainApp(this);
			queryOrderController.setOrder(order);
			queryOrderController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示余额更新界面
	 * @param anchorPaneContent
	 */
	public void showBalanceUpdate(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/balance/CustomerBalanceUpdate.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			CustomerBalanceUpdateController balanceController = loader.getController();
			balanceController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**显示单个余额更新信息弹窗界面
	 * @param UpdateBalance
	 */
	public void showSelectBalance(UpdateBalance balance) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/balance/CustomerBalanceUpdateWindows.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("余额更新信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			CustomerBalanceUpdateController balanceController = loader.getController();
			balanceController.setMainApp(this);
			balanceController.setBalanceWindows(balance);
			balanceController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**显示修改密码界面
	 * @param anchorPaneContent
	 */
	public void showPasswordChange(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/system/PasswordChange.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			PasswordChangeController passwordController = loader.getController();
			passwordController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showNewManagerUser(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/system/NewManagerUser.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			NewManagerUserController newManagerUserController = loader.getController();
			newManagerUserController.setMainApp(this);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showEditManagerUser(ManagerUser managerUser, QueryManagerUserController queryManagerUserController)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/system/EditManagerUser.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("用户信息");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			EditManagerUserController editManagerUserController = loader.getController();
			editManagerUserController.setMainApp(this);
			editManagerUserController.setManagerUser(managerUser);
			editManagerUserController.setQueryManagerUserController(queryManagerUserController);
			editManagerUserController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showQueryManagerUser(AnchorPane anchorPaneContent)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/system/QueryManagerUser.fxml"));
			AnchorPane page = (AnchorPane)loader.load();
			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加查询内容
			anchorPaneContent.getChildren().add(page);

			QueryManagerUserController queryManagerUserController = loader.getController();
			queryManagerUserController.setQueryManagerUserController(queryManagerUserController);
			queryManagerUserController.setMainApp(this);

		}
		catch (IOException e) {
			e.printStackTrace();
		}


	}
	/**显示产品类型设置主界面
	 * @param anchorPaneContent
	 */
	public void showModifyAndDeleteProduct(Product productSelect, EditProductTypeController newProductController) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/product/ModifyAndDeleteProductType.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("修改删除产品");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			ModifyAndDeleteProductTypeController modifyAndDeleteProductController = loader.getController();
			modifyAndDeleteProductController.setMainApp(this);
			modifyAndDeleteProductController.setEditProductTypeController(newProductController);
			modifyAndDeleteProductController.setProdectSelect(productSelect);
			modifyAndDeleteProductController.setProductName(productSelect);
			modifyAndDeleteProductController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
     * 增添新的产品类型弹窗界面
     * @param editProductController
     */
	public void showAddProduct(EditProductTypeController newProductController)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/product/NewProductType.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage editStage = new Stage();
			editStage.setTitle("新增产品");
			editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(mainStage);
			editStage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			editStage.setScene(scene);

			EditProductTypeController editProductController = loader.getController();
			editProductController.setMainApp(this);
			editProductController.setEditProductTypeController(newProductController);
			editProductController.setEditStage(editStage);

			editStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**显示产品类型设置主界面
	 * @param anchorPaneContent
	 */
	public void showEditProducType(AnchorPane anchorPaneContent) {
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/product/EditProductType.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			mainStage.setMaximized(true);
			//先移除面板中的内容
			anchorPaneContent.getChildren().removeAll(anchorPaneContent.getChildren());
			//增加新建客户面板
			anchorPaneContent.getChildren().add(page);

			EditProductTypeController productController = loader.getController();
			productController.setMainApp(this);
			productController.setEditProductTypeController(productController);
			productController.setData();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 显示身份验证框
	 */
	public void showRestoreDB()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/system/ConfirmPassword.fxml"));
			AnchorPane page = (AnchorPane)loader.load();

			Stage stage = new Stage();
			stage.setTitle("恢复数据库");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(mainStage);
			stage.getIcons().add(new Image("file:resources/images/person.png"));
			Scene scene = new Scene(page);
			stage.setScene(scene);

			ConfirmPasswordController confirmPasswordController = loader.getController();
			confirmPasswordController.setMainApp(this);
			confirmPasswordController.setStage(stage);

			stage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
