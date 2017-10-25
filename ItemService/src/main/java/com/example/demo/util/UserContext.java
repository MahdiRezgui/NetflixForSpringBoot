package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
	public static final String CORRELATION_ID="is_correlation";
	public static final String AUTH_TOKEN="Authorization";
	public static final String User_ID="is_user_id";
	
	private String correlationId=new String();
	private String authtoken = new String();
	private String userId=new String();
	
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getAuthtoken() {
		return authtoken;
	}
	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
