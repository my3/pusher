package com.edeadlock.paisa.reminders;

import javax.ws.rs.GET;
import javax.ws.rs.Path;



@Path("/reminder")
public class ReminderStorage {

	@GET
	public String setReminders() {
		return "Success";
	}
	
	public static void main(String[] args) {

	}

}
