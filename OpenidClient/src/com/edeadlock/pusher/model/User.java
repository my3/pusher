package com.edeadlock.pusher.model;

import java.util.UUID;

public class User {
	private UUID userId;
	private String email, openidIdentity;
	private String creationDate, modificationDate;
	
	public User(String openidIdentity) {
		this.userId = UUID.randomUUID();
		this.openidIdentity = openidIdentity;
	}
	
	public UUID getUserId() {
		return this.userId;
	}
	
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpenidIdentity() {
		return openidIdentity;
	}

	public void setOpenidIdentity(String openidIdentity) {
		this.openidIdentity = openidIdentity;
	}
	
	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}
}
