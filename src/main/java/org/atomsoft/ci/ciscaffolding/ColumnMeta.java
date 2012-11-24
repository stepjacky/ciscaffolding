package org.atomsoft.ci.ciscaffolding;

public class ColumnMeta {
	private String name;
	private String key;
	private String comment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ColumnMeta(String name, String key, String comment) {
		super();
		this.name = name;
		this.key = key;
		this.comment = comment;
	}

}
