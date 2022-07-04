package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;

import General.Constants;
import Helper.ResultSetConverter;

public class DbConnector {
    
    public static JSONArray getData(String sql) throws JSONException{
      
        JSONArray jsonArray = null;

        String dbName   = Constants.DEV_DB_NAME;
        String userName = Constants.DEV_DB_USER;
        String password = Constants.DEV_DB_PASS;
        String hostname = Constants.DEV_DB_HOST;
        String port     = Constants.DEV_DB_PORT;
        
        String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;
        
		try{
           
            Connection con = DriverManager.getConnection(jdbcUrl, userName, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            jsonArray = ResultSetConverter.convertToJsonArry(rs);
         }
        catch(SQLException err){
            System.out.println("Error in DB Connector: " + err.getMessage());
		}

        return jsonArray;
    }
}
