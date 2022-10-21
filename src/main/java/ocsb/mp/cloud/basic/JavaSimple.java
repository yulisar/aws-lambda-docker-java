package ocsb.mp.cloud.basic;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class JavaSimple implements RequestHandler<Map<String,String>, Boolean> {

	private static final Logger log = LogManager.getLogger(JavaSimple.class);

	public static void main(String[] args) {
		log.info("JavaSimple TEST......");
//	   
	}

	@Override
	public Boolean handleRequest(Map<String,String> event, Context context) {


		log.info("inf: JavaSimple TEST......");


//		log.info("url: {}", urldb);
//		log.info("port: {}", port);
//
//		Properties props = new Properties();
//		props.setProperty("options", "-c search_path=test,public,pg_catalog -c statement_timeout=90000");
//
//		String jdbcUrl = "jdbc:postgresql://" + urldb + "/" + dbId;
//		String username = "postgres";
//		String pass = "Pass1234!!";
//
//		log.info("Getting Connection to DB..");
//		int timeout = 500;
//
//		try {
//			log.info("Time Out: {}" , timeout);
//			DriverManager.setLoginTimeout(timeout);
//			Connection conn = DriverManager.getConnection(jdbcUrl, username, pass);
//
//			if (conn != null) {
//				log.info("Connected to the database!");
//				Statement stmt = null;
//				stmt = conn.createStatement();
//				String sql = "SELECT datname FROM pg_database; ";
//				ResultSet rs = stmt.executeQuery(sql);
//				while (rs.next()) {
//					String db = rs.getString("datname");
//					System.out.println(db);
//				}
//				rs.close();
//				stmt.executeUpdate(sql);
//				stmt.close();
//				conn.close();
//			} else {
//				log.info("Failed to make connection!");
//			}
//
//		} catch (SQLException e) {
//			log.info(e.getSQLState() + "--" + e.getMessage());
//		} catch (Exception e) {
//			log.info(e.toString());
//		}

//		

		return false;
	}



}
