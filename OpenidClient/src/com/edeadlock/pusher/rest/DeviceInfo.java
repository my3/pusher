package com.edeadlock.pusher.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.edeadlock.pusher.storage.DynamoDBStore;

@Path("/device")
public class DeviceInfo {
	
	
	@GET
	@Path("/{user}")
	public String getDevices(@PathParam("user") String userId) {
		new DynamoDBStore();
		return DynamoDBStore.getUserDevices(userId).toString();
	}

}
