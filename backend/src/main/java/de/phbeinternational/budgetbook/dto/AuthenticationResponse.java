package de.phbeinternational.budgetbook.dto;


//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class AuthenticationResponse {
	private String token;
	
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    //=================
    // Builder
    //=================
	public static class AuthenticationResponseBuilder{
		private String token;
		
		public AuthenticationResponseBuilder token(String token) {
			this.token = token;
			return this;
		}
		
        public AuthenticationResponse build() {
            return new AuthenticationResponse(token);
        }
	}
	
    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    //=================
    // Getter /Setter
    //=================
    public String getToken() {
        return token;
    }

}
