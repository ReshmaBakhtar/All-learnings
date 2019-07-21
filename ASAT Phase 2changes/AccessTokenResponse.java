package com.ibm.asat.admin.response;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {

	private String accessToken;
	private String idToken;
	private String tokenType;
	private String expiryTime;
	
	
	
	public AccessTokenResponse() {
		super();
		
	}



	public AccessTokenResponse(String accessToken, String idToken, String tokenType, String expiryTime) {
		super();
		this.accessToken = accessToken;
		this.idToken = idToken;
		this.tokenType = tokenType;
		this.expiryTime = expiryTime;
	}



	public String getAccessToken() {
		return accessToken;
	}


	@JsonProperty("access_token")
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}



	public String getIdToken() {
		return idToken;
	}


	@JsonProperty("id_token")
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}



	public String getTokenType() {
		return tokenType;
	}


	@JsonProperty("token_type")
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}



	public String getExpiryTime() {
		return expiryTime;
	}


	@JsonProperty("expires_in")
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}



	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AccessTokenResponse)) {
			return false;
		}
		final AccessTokenResponse that = (AccessTokenResponse) obj;

		return new EqualsBuilder().append(this.accessToken, that.accessToken)
				.append(this.idToken, that.idToken)
				.append(this.tokenType, that.tokenType)
				.append(this.expiryTime, that.expiryTime).isEquals();
	}



	@Override
	public int hashCode() {
		return Objects.hash(accessToken, idToken, tokenType, expiryTime);
	}



	@Override
	public String toString() {
		return "OAuthAccessTokenResponse [accessToken=" + accessToken + ", idToken=" + idToken + ", tokenType="
				+ tokenType + ", " + "expiresIn=" + expiryTime + "]";
	}
	
	
	
	
	
}
