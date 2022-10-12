package ocsb.mp;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class App {
	
private static final Logger log = LogManager.getLogger(App.class);
	
	private static String AWS_ACCESS_KEY_ID = "ASIA56733ODMPNYZEU43";
	private static String AWS_SECRET_ACCESS_KEY= "+Y7EJ1uRryDTa3OvCp8ap8gmfiKu38vbGhSX0KAX";
	private static String AWS_SESSION_TOKEN = "IQoJb3JpZ2luX2VjEMH//////////wEaDmFwLXNvdXRoZWFzdC0xIkcwRQIgeXLg1cXqpwZFRpHcBLqw9wRxxLi2QKc48KJgsLsjXioCIQCBHfeAGArUyg0SpabPV3eIZkdY6ZKGo4sbQaBlvA90tyqhAwiK//////////8BEAIaDDk1OTkxNjUwMzI1NiIMGs3nqOJf3ac/DQQ0KvUCi0aYN4MgomLUToQQNeL6BOrI4kq2iJ4BmAkyTRtWqP5PUJcUd3vVYK1l/dZ9cwPZVqiQj9xCFyHqYPr++FUZuTOzr5mFKiyHeg4IqWEnW1EaBfli87tL3oczx4t3E+UnPa/9gh/FU/EGiOZm5xzcoluRn9QdGqhiRHlU6ri2VAUQ3cmc242OPvdHMcGfkZC4yP0tP54ShDNoysRKrsaixschDGv/cxoOKtxlWFy+hR1I2NxkL3Zfb/ETm4ceQodSSKtW6kanBuekUdjlWSWOmrzEoCGzgRtWznjrBkpgTzbkLnIveW12tYPgB+gQIO4yFkKcAOStNXOS0VgQwDBavF0RN7HWd9Al71CvauT7AUSmLv7s2rPtM2pNxDNB0aHpnHcspdfVuj1n2K8T6XmVPymctgV4LgQ5DoewQg7ZhdUzvER58oCnopTyt50cmYEn4XeHhN6WhImrZGpcGPxtqpLyQSBVz4o0fZxPO+iVraL6wXk1YzDxiZqaBjqmARZyEYQy0n6USXSRgaIBYbadLPksFyuQM4w6dL9qr7kOFYKvUYyD6DrxJK20tTbIK5+/2zagrpPij0d7r7Ep99OVoGCWuYbad/GL2MhAtFTMxYpH5Iu/eDwU3nE6gwqJM7XN7RtWqJGcPP+U4HShun5bEU0WUocoKxeqw7tq6pi/z0B9WfdjDOMN5LYq9mBv+wdxU/6+Js0q4uM0PbbmR6qqemVVesM=";
	
	private static BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
			AWS_ACCESS_KEY_ID ,//session_creds.getAccessKeyId(),
			AWS_SECRET_ACCESS_KEY,   //session_creds.getSecretAccessKey(),
			AWS_SESSION_TOKEN   //session_creds.getSessionToken()
	);

	private static AmazonS3 s3client = AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
			.withRegion(Regions.AP_SOUTHEAST_1)
			.build();
	
	private static AWSS3Service awsService = new AWSS3Service(s3client);
	

	public static void main(String[] args) throws IOException {
		
		String bucketName = "mportal-ocbcsg-rawfiledump";
		String srcKey = "CCOCMPA220801";
		File localDestinationFile = new File("C:\\Users\\yulisar\\Documents\\mportal\\Cloud\\sampleFiles\\CCOCMPA220801-fromS3.txt");
		
		String newBucketName = "new-mportal-ocbcsg-rawfiledump";
		
		File srcfile = new File("C:\\Users\\yulisar\\Documents\\mportal\\Cloud\\sampleFiles\\hello.txt");
		String dstKey = "Document/hello.txt";
		
		String destinationBucketName = "yul-bucket-test2";
		String destinationCopyKey = "Document/hello2.txt";
		
		String objkeyArr[] = {
		"Document/hello1.txt", 
		"Document/hello2.png"
		};
		
		log.info("*****************************************************");
		log.info("01.List Buckets and download Object inside the bucket");
		listBuckets();
		listObjects(bucketName);
		downloadObject(bucketName, srcKey, localDestinationFile);
		
		log.info("*****************************************************");
		log.info("02. Create bucket and delete the bucket");
		createBucket(newBucketName);
		listBuckets();
		listObjects(newBucketName);
		deleteObject(newBucketName, destinationCopyKey);
		deleteBucket(newBucketName);
		listBuckets();
		
		log.info("*****************************************************");
		log.info("03. Put Object inside a bucket");
		listObjects(bucketName);
		putObject(bucketName, dstKey, srcfile);
		listObjects(bucketName);
		
		log.info("*****************************************************");
		log.info("04. Create new bucket, copy object from a bucket to the new bucket, then download object from the new bucket.");
		createBucket(newBucketName);
		listObjects(newBucketName);
		copyObject(bucketName, dstKey, bucketName, destinationCopyKey);
		copyObject(bucketName, dstKey, newBucketName, destinationCopyKey);
		listObjects(bucketName);
		listObjects(newBucketName);
		downloadObject(bucketName, destinationCopyKey, localDestinationFile);
		log.info("*****************************************************");
		log.info("05. Delete Object in a bucket ");
		deleteObject(bucketName, dstKey);
		listObjects(bucketName);
		deleteMultipleObject(bucketName, objkeyArr);
		log.info("*****************************************************");
		log.info("*****************************************************");
	}

	private static void deleteBucket(String bucketName) {
		log.info("=================================");
		log.info("Delete Bucket: " + bucketName);
		log.info("Checking if bucket already exist.");
		if (awsService.doesBucketExist(bucketName)) {
			log.info("Bucket name is Exist." + " Delete the bucket..");
	        //listing objects
			awsService.deleteBucket(bucketName);
		} else {
			log.info("The bucket not exist..");
		}
		
	}

	private static void deleteMultipleObject(String bucketName, String[] objkeyArr) {
		log.info("=================================");
		log.info("Delete some objects in Bucket: " + bucketName + ", with key: " );
		for (String string : objkeyArr) {
			log.info("objKey: " + string);
		}
		DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketName).withKeys(objkeyArr);
		awsService.deleteObjects(delObjReq);
	}

	private static void copyObject(String bucketName, String key, String destinationBucketName, String destinationKey) {
		log.info("=================================");
		log.info("Copy Object in Bucket: " + bucketName + ", with key: " + key );
        awsService.copyObject(bucketName, key, destinationBucketName, destinationKey);
	}

	private static void downloadObject(String bucketName, String key, File targetFile) {
			log.info("=================================");
			log.info("Download Object in Bucket : " + bucketName + ", with key: " + key );
	        S3Object s3object = awsService.getObject(bucketName, key);
	        S3ObjectInputStream inputStream = s3object.getObjectContent();
	        try {
				FileUtils.copyInputStreamToFile(inputStream, targetFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private static void deleteObject(String bucketName, String key) {
		log.info("=================================");
		log.info("Deleting Object in Bucket: " + bucketName + ", with key: " + key );
        awsService.deleteObject(bucketName, key);
	}

	private static void putObject(String bucketName, String key, File file) {
		log.info("=================================");
		log.info("Put Object to bucket: " + bucketName + ", with key: " + key );
		awsService.putObject(bucketName, key, file);
	
	}

	private static void listObjects(String bucketName) {
		log.info("=================================");
        log.info("List Bucket Objects: " + bucketName);
        ObjectListing objectListing = awsService.listObjects(bucketName);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            log.info(os.getKey());
        }
	}

	private static void listBuckets() {
		log.info("=================================");
		log.info("List existing buckets.");
		for (Bucket s : awsService.listBuckets()) {
			log.info(s.getName());
		}	
	}

	private static void createBucket(String bucketName) {
		log.info("=================================");
		log.info("Checking if bucket already exist.");
		if (awsService.doesBucketExist(bucketName)) {
			log.info("Bucket name is already Exist." + " Listing the bucket..");
	        //listing objects
			log.info("List Bucket: " + bucketName);
	        ObjectListing objectListing = awsService.listObjects(bucketName);
	        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
	        	log.info(os.getKey());
	        }

		} else {
			log.info("The bucket name is available for creating new one.");
			log.info("Creating a bucket..");
			awsService.createBucket(bucketName);
			
		}
		
	}

}
