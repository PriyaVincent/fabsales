package com.store.payment.response.model;

import java.util.Set;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Response {

	private Set<ResponseField> response;

	private Set<ErrorField> errors;

	public Response() {
		super();
	}

	public Response(Set<ResponseField> responseFields) {
		super();
		this.response = responseFields;
	}

	public Response(Set<ResponseField> responseFields, Set<ErrorField> errors) {
		super();
		this.errors = errors;
	}

	public Set<ResponseField> getResponse() {
		return response;
	}

	public void setResponse(Set<ResponseField> response) {
		this.response = response;
	}

	public Set<ErrorField> getErrors() {
		return errors;
	}

	public void setErrors(Set<ErrorField> errors) {
		this.errors = errors;
	}

}
