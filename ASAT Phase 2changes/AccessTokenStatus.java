package com.ibm.asat.admin.response;

import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenStatus {
	private boolean TokenActiveStatus;
	
	

	public AccessTokenStatus() {
		super();
		
	}

	public AccessTokenStatus(boolean tokenActiveStatus) {
		super();
		TokenActiveStatus = tokenActiveStatus;
	}

	public boolean getTokenActiveStatus() {
		return TokenActiveStatus;
	}

	@JsonProperty("active")
	public void setTokenActiveStatus(boolean tokenActiveStatus) {
		TokenActiveStatus = tokenActiveStatus;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AccessTokenStatus)) {
			return false;
		}
		final AccessTokenStatus that = (AccessTokenStatus) obj;

		return new EqualsBuilder().append(this.TokenActiveStatus, that.TokenActiveStatus)
				.isEquals();
	}



	@Override
	public int hashCode() {
		return Objects.hash(TokenActiveStatus);
	}



	@Override
	public String toString() {
		return "AccessTokenStatus [accessToken=" + TokenActiveStatus + "]";
	}
	
}
