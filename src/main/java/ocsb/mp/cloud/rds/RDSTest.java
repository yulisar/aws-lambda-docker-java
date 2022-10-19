package ocsb.mp.cloud.rds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class RDSTest implements RequestHandler<S3Event, Boolean> {
	
	public static void main(String[] args) {

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
