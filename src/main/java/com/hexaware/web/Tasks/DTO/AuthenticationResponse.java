package com.hexaware.web.Tasks.DTO;


public class AuthenticationResponse {
	private String jwt;
    private int userId;
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + ", userId=" + userId + "]";
	}
    
}
