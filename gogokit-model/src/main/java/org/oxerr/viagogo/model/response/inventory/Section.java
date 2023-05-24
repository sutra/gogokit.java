package org.oxerr.viagogo.model.response.inventory;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Section implements Serializable {

	private static final long serialVersionUID = 2023021301L;

	private String name;

	private Boolean freeTextRow;

	private Boolean queueNumberRow;

	private List<String> rows;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getFreeTextRow() {
		return freeTextRow;
	}

	public void setFreeTextRow(Boolean freeTextRow) {
		this.freeTextRow = freeTextRow;
	}

	public Boolean getQueueNumberRow() {
		return queueNumberRow;
	}

	public void setQueueNumberRow(Boolean queueNumberRow) {
		this.queueNumberRow = queueNumberRow;
	}

	public List<String> getRows() {
		return rows;
	}

	public void setRows(List<String> rows) {
		this.rows = rows;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Section rhs = (Section) obj;
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

}
