package de.phbeinternational.budgetbook.dto;

import lombok.Data;

//@Data
public class AuthenticationRequest {

	private String email;
	private String password;
	
	public AuthenticationRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
    //=================
    // Builder
    //=================
    public static class AuthenticationRequestBuilder {
    	private String email;
    	private String password;

        public AuthenticationRequestBuilder email(String email) {
    		this.email = email;
            return this;
        }
        public AuthenticationRequestBuilder password(String password) {
        	this.password = password;
        	return this;
        }

        public AuthenticationRequest build() {
            return new AuthenticationRequest(email, password);
        }
    }
    
    public static AuthenticationRequestBuilder builder() {
        return new AuthenticationRequestBuilder();
    }

    
    //=================
    // Getter /Setter
    //=================
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
