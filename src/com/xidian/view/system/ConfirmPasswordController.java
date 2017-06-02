package com.xidian.view.system;


import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

import javax.swing.filechooser.FileSystemView;

import org.apache.ibatis.session.SqlSession;

import com.xidian.MainApp;
import com.xidian.model.system.ManagerUser;
import com.xidian.util.EncryptBySaltUtil;
import com.xidian.util.MessageUtil;
import com.xidian.util.SingletonData;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ConfirmPasswordController{

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField sqlFileField;

	@FXML
	private Label passwordLabel;

	@FXML
	private Label infoLabel;

	@FXML
	private Label resultLabel;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Button okButton;



	private Stage stage;
	private MainApp mainApp;
	private  static  String BACKUP_DIR;
	private  static  String HOSTNAME;
	private  static  String DB_USER;
	private  static  String DB_PWD;
	private  static  String DB_NAME;

	public ConfirmPasswordController() {

	}

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void initialize() {
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
	}

	/**
	 * 密码修改按钮监听函数
	 */
	@FXML
	private void handleSelectSqlFile() {

		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel文件(*.sql)", "*.sql");
//		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
		fileChooser.setInitialDirectory(new File(BACKUP_DIR));
		fileChooser.getExtensionFilters().add(extFilter);
		Stage s = new Stage();
		File file = fileChooser.showOpenDialog(s);
		if(file == null) return;
		sqlFileField.setText(file.getAbsolutePath());
	}
	/**
	 * 密码修改按钮监听函数
	 */
	@FXML
	private void handleRestoreDB() {
		// 获取用户输入
		String password = passwordField.getText().trim();
		// 读取文件获得登陆用户名
		String username = SingletonData.getSingletonData().getManagerUser().getUsername();

		SqlSession sqlSession = null;

		try {
			sqlSession = mainApp.getSqlSession(true);
			ManagerUser managerUser = sqlSession.selectOne("com.xidian.ManagerUserXml.getManagerUser",username);
			if (EncryptBySaltUtil.verify(password, managerUser.getPassword()))
			{
				passwordLabel.setText("");
				//恢复数据库
				if(!MessageUtil.alertConfirm("提示", "确认恢复数据库吗"))
				{
					return;
				}
				else
				{

					Task<String> task = new Task<String>() {

						@Override
						protected String call() throws Exception {
							int result = 0;
							String message = "";
							try {
								result = restore();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(result == 0)
							{
								message = "恢复数据库成功！";

							}
							else if(result == 100)
							{
								message = "前一天备份文件不存在，请选择备份文件！";
							}
							else
							{
								message = "恢复数据库失败！";
							}

							return message;
						}
					};
					progressBar.visibleProperty().bind(task.runningProperty());
					infoLabel.visibleProperty().bind(task.runningProperty());
					resultLabel.textProperty().bind(task.valueProperty());
//					okButton.visibleProperty().bind(task.runningProperty());
					Thread thread = new Thread(task);
					thread.start();
				}

			}
			else {
				passwordLabel.setText("密码错误，请重新输入！");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}

	}

    /**
     * 还原数据库
     * @param sql 要还的SQL文件
     * @throws Exception
     */
    private int restore() throws Exception {
    	String targetFile =  BACKUP_DIR + "manager-backup-"+LocalDate.now().minusDays(1).toString() + ".sql";  // SQL文件路径
    	if(!"".equals(sqlFileField.getText()))
    	{
    		targetFile = sqlFileField.getText();
    	}
    	File file = new File(targetFile);
    	if(!file.exists())
    	{
    		return 100;
    	}
        String str= "mysql -h " + HOSTNAME +" -u" + DB_USER+" -p" + DB_PWD+" "+DB_NAME +" < "+targetFile;;	//将 mysql命令的语句赋值给str
        Process process = Runtime.getRuntime().exec("cmd /c"+str);//调用exce()函数
        return process.waitFor();
    }

}
