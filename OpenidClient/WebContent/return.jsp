<%@page import="com.edeadlock.openid.DiscoverEndpoint"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.edeadlock.pusher.storage.DynamoDBStore" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Devices</title>
 </head>
 <body>
 	<a href="logout.jsp">Logout</a>
	<p>
		<% 
			if(session.getAttribute("email") == null) {
				String email = DiscoverEndpoint.verify(request);
				String userId = DynamoDBStore.createUserIfNotExists(session.getAttribute("openid.identity").toString(), email);
				session.setAttribute("userId", userId);
				session.setAttribute("email", email);
				out.write("User ID: "+userId+" Url: "+session.getAttribute("openid.identity"));
			}
			else {
				String userId = DynamoDBStore.createUserIfNotExists(
						session.getAttribute("openid.identity").toString(),
						session.getAttribute("email").toString());
				session.setAttribute("userId", userId);
				%>
				Email : <%= session.getAttribute("email") %>
				Devices: <%= 
					DynamoDBStore.getUserDevices(session.getAttribute("email").toString()) 
				%>
				<%
			}
			
		%>
	</p>
 </body>
</html>