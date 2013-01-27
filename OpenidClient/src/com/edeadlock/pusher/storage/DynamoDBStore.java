package com.edeadlock.pusher.storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodb.model.AttributeValue;
import com.amazonaws.services.dynamodb.model.ComparisonOperator;
import com.amazonaws.services.dynamodb.model.Condition;
import com.amazonaws.services.dynamodb.model.PutItemRequest;
import com.amazonaws.services.dynamodb.model.PutItemResult;
import com.amazonaws.services.dynamodb.model.ScanRequest;
import com.amazonaws.services.dynamodb.model.ScanResult;
import com.edeadlock.pusher.model.Device;
import com.edeadlock.pusher.model.DeviceType;
import com.edeadlock.pusher.model.User;
import com.edeadlock.pusher.util.ConvertToItem;

public class DynamoDBStore {
	static AmazonDynamoDBClient dynamoDB;
	private static final String CONFIG_FILE = "AwsCredentials.properties";

	public DynamoDBStore() {
		try {
			init(CONFIG_FILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private static void init(String location) throws Exception {
        AWSCredentials credentials = new PropertiesCredentials(
        		DynamoDBStore.class.getResourceAsStream(location));
        dynamoDB = new AmazonDynamoDBClient(credentials);
    }
    
    public void addItem(String tableName, Map<String, AttributeValue> item) throws Exception {
    	try {
    		PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
    		PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
    	} catch(Exception e) {
    		throw new Exception(item+""+e.getMessage());
    	}
    }
    
    public ScanResult getItem(String tableName, Map<String, Condition> scanFilter) {
    	ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
        return dynamoDB.scan(scanRequest);
    }
    
    public static String createUserIfNotExists(String openidIdentity, String email) throws Exception {
    	DynamoDBStore store = new DynamoDBStore();
    	Map<String, Condition> scanFilter = new HashMap<String, Condition>();
        Condition condition = new Condition()
            .withComparisonOperator(ComparisonOperator.EQ.toString())
            .withAttributeValueList(new AttributeValue(openidIdentity));
        scanFilter.put("openid_identity", condition);
        //System.out.println(store.getItem("User", scanFilter));
    	ScanRequest scanRequest = new ScanRequest("User").withScanFilter(scanFilter);
        if(dynamoDB.scan(scanRequest).getCount()>0) {
        	ScanResult result = dynamoDB.scan(scanRequest);
        	List<Map<String, AttributeValue>> data = result.getItems();
        	Map<String, AttributeValue> user = data.get(0);
        	return user.get("user_id").toString();
        } 
        else {
        	User user = new User(openidIdentity);
    		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//dd/MM/yyyy
    	    Date now = new Date();
    	    String strDate = sdfDate.format(now);
    		user.setCreationDate(strDate);
    		user.setModificationDate(strDate);
    		user.setEmail(email);
    		store.addItem("User", ConvertToItem.getUserItem(user));
    		return user.getUserId().toString();
        }
    }
    
    public static List<Device> getUserDevices(String userId) {
    	System.out.println(userId);
    	Map<String, Condition> scanFilter = new HashMap<String, Condition>();
        Condition condition1 = new Condition()
            .withComparisonOperator(ComparisonOperator.EQ.toString())
            .withAttributeValueList(new AttributeValue(userId));
        scanFilter.put("user_id", condition1);
        ScanRequest scanRequest = new ScanRequest("Device").withScanFilter(scanFilter);
    	ScanResult result  = dynamoDB.scan(scanRequest);
    	List<Device> devices = new ArrayList<Device>();
    	ListIterator<Map<String, AttributeValue>> i = result.getItems().listIterator();
    	while(i.hasNext()) {
    		Map<String, AttributeValue> deviceRecord = (Map<String, AttributeValue>)i.next();
    		Device device = new Device(deviceRecord.get("device_id").toString(), 
    				DeviceType.valueOf(deviceRecord.get("device_type").getS()));
    		System.out.println(device.getDeviceType());
    		devices.add(device);
    	}
    	return devices;
    }
    
    public static Device getUserDevice(String userId, String deviceId) {
    	Map<String, Condition> scanFilter = new HashMap<String, Condition>();
        Condition condition1 = new Condition()
            .withComparisonOperator(ComparisonOperator.EQ.toString())
            .withAttributeValueList(new AttributeValue(userId));
        Condition condition2 = new Condition()
        .withComparisonOperator(ComparisonOperator.EQ.toString())
        .withAttributeValueList(new AttributeValue(deviceId));
        scanFilter.put("user_id", condition1);
        scanFilter.put("device_id", condition2);
        ScanRequest scanRequest = new ScanRequest("Device").withScanFilter(scanFilter);
    	ScanResult result  = dynamoDB.scan(scanRequest);
    	Map<String, AttributeValue> deviceRecord = (Map<String, AttributeValue>)result.getItems().listIterator().next();
    	Device device = new Device(deviceRecord.get("device_id").toString(), 
    				DeviceType.valueOf(deviceRecord.get("device_type").getS()));
    	return device;
    }
    
    public static void main(String args[]) {
    	new DynamoDBStore();
    	DynamoDBStore.getUserDevices("devendra_ayalasomayajula@yahoo.com");
    }
}
