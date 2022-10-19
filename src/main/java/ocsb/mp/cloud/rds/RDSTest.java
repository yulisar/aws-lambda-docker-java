package ocsb.mp.cloud.rds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class RDSTest implements RequestHandler<S3Event, Boolean> {
	
	private static final Logger log = LogManager.getLogger(RDSTest.class);

	
	public static void main(String[] args) {
		log.info("RDS TEST......");
		
//	    String url = "db-1.c7oacjbxyql0.ap-southeast-1.rds.amazonaws.com";
	    String url = "localhost";
	    int port = 5432;
	    String dbId = "";
	    
	    log.info("url"+ url);
	    log.info("port"+ port);
	    
	    try (Connection conn = DriverManager.getConnection(
	            "jdbc:postgresql://" + url + "/"+dbId, "postgres", "sapassword")) {

	        if (conn != null) {
	        	log.info("Connected to the database!");
	        	Statement stmt = null;
	        	stmt = conn.createStatement();
	        	String sql = "SELECT datname FROM pg_database; ";
	        	ResultSet rs = stmt.executeQuery(sql);
	        	while ( rs.next() ) {
	                String  db = rs.getString("datname");
	                System.out.println(db);
	             }
	             rs.close();
	        	stmt.executeUpdate(sql);
	            stmt.close();
	            conn.close();
	        } else {
	        	log.info("Failed to make connection!");
	        }

	    } catch (SQLException e) {
	    	log.info(e.getSQLState() +"--"+ e.getMessage());
	    } catch (Exception e) {
	    	log.info(e.toString());
	    }
	}

	@Override
	public Boolean handleRequest(S3Event event, Context context) {
		
		final LambdaLogger logger = context.getLogger();
		
		
	    String url = "db-1.c7oacjbxyql0.ap-southeast-1.rds.amazonaws.com";
	    int port = 5432;
	    
	    logger.log("url"+ url);
	    logger.log("port"+ port);
	    
	    try (Connection conn = DriverManager.getConnection(
	            "jdbc:postgresql://" + url + "/mp_dev", "postgres", "sapassword")) {

	        if (conn != null) {
	        	 logger.log("Connected to the database!");
	        } else {
	        	 logger.log("Failed to make connection!");
	        }

	    } catch (SQLException e) {
	    	 logger.log(e.getSQLState() +"--"+ e.getMessage());
	    } catch (Exception e) {
	    	logger.log(e.toString());
	    }
	    
	    
	    

		return false;
	}

}
