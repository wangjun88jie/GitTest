package db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class DBPoolConnection {
    static Logger log = Logger.getLogger(DBPoolConnection.class);
    private static DruidDataSource druidDataSource;
    static {
        Properties properties = loadPropertiesFile("DbConnectionService.properties");
        try {
            druidDataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(properties); //DruidDataSrouce工厂模式
            String ss = "";
        } catch (Exception e) {
            log.error("获取配置失败");
        }
    }
    private static DBPoolConnection ourInstance = new DBPoolConnection();

    public static DBPoolConnection getInstance() {
        return ourInstance;
    }

    private DBPoolConnection() {
    }

    public  DruidPooledConnection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }

    /**
     * 加载配置文件
     * @param filePath 配置文件名
     * @return Properties对象
     */
    private static Properties loadPropertiesFile(String filePath){
        String webRootPath = null;
        if(null==filePath || filePath.equals("")){
            throw new IllegalArgumentException("没有找到配置文件:"+filePath);
        }
        webRootPath = DBPoolConnection.class.getClassLoader().getResource("").getPath();
        //webRootPath = new File(webRootPath).getParent();
        InputStream inputStream = null;
        Properties properties = null;
        try {
            inputStream = new FileInputStream(new File(webRootPath+"\\"+filePath));
            properties = new Properties();
            properties.load(inputStream);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
