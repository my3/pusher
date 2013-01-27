package com.edeadlock.pusher.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodb.model.AttributeValue;
import com.edeadlock.pusher.model.Device;
import com.edeadlock.pusher.model.User;
import com.edeadlock.pusher.storage.DynamoDBStore;

public class ConvertToItem {
	public static Map<String, AttributeValue> getUserItem(User user) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("user_id", new AttributeValue(user.getUserId().toString()));
        item.put("email", new AttributeValue(user.getEmail()));
        item.put("openid_identity", new AttributeValue(user.getOpenidIdentity()));
        item.put("creation_date", new AttributeValue(user.getCreationDate()));
        item.put("modified_date", new AttributeValue(user.getModificationDate()));
        return item;
	}
	
	public static Map<String, AttributeValue> getDeviceItem(Device device) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("user_id", new AttributeValue(device.getUserId()));
        item.put("device_id", new AttributeValue(device.getDeviceId()));
        item.put("device_type", new AttributeValue(device.getDeviceType()+""));
        return item;
	}
	
	
	public static void main(String args[]) throws Exception {
		
		for(int i=111; i<10000; i++) {
			String user = DynamoDBStore.createUserIfNotExists("test"+i, "mythrikiran@zmail"+i+".com");
			System.out.println(i+". Created "+user);
			if(i % 10 == 0) {
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//dd/MM/yyyy
	    	    Date now = new Date();
	    	    String strDate = sdfDate.format(now);
	    	    System.out.println(strDate);
			}
				
		}
			
		
		/*
		DynamoDBStore store = new DynamoDBStore();
		
		User user = new User("Mythri", "mythrikiran123@yahoo.com", "mythrikiran123@yahoo.com", "4696443626");
		store.addItem("User", getUserItem(user));
		device.setUserId("mythrikiran@yahoo.com");
		Device device = new Device(UUID.randomUUID(), DeviceType.ANDROID);
		store.addItem("Device", getDeviceItem(device));
		
		
		// Scan items for movies with a year attribute greater than 1985
		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
        Condition condition = new Condition()
            .withComparisonOperator(ComparisonOperator.EQ.toString())
            .withAttributeValueList(new AttributeValue("mythrikiran@yahoo.com"));
        scanFilter.put("user_id", condition);
        System.out.println(store.getItem("Device", scanFilter));
        
        */
		
		
		
	}
}
