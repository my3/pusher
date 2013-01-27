package com.edeadlock.pusher.model;

import java.util.UUID;

public class Device {
	private String deviceId;
	private String userId;
	private DeviceType type;
	
	
	public Device(String deviceId, DeviceType type) {
		this.deviceId = deviceId;
		this.type = type;
	}
	
	public Device(DeviceType type) {
		this.deviceId = UUID.randomUUID().toString();
		this.type = type;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public DeviceType getDeviceType() {
		return type;
	}

	public void setDeviceType(DeviceType type) {
		this.type = type;
	}
	
}
