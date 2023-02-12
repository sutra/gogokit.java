package org.oxerr.viagogo.model.response.inventory;

import java.io.Serializable;
import java.util.List;

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

}
