package org.oxerr.viagogo.model.response.sale;

import java.io.Serializable;

public class DeliveryMethod implements Serializable {

	private static final long serialVersionUID = 2023021801L;

	private Integer id;

	private String name;

	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
