package org.atomsoft.ci.ciscaffolding;

public class ColumnMeta {
	private String name;
	private String key;
	private String comment;
	private String type;
		
	public ColumnMeta(String name, String key, String comment,String type) {
		super();
		this.name = name;
		this.key = key;
		this.comment = comment;
		this.type = type;
	}

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
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean getTextable() {
		return 
				this.type.toLowerCase().indexOf("blob")!=-1 || 
				this.type.toLowerCase().indexOf("text")!=-1 ;
	}

	
	public boolean getDateable() {
		return 
				this.type.toLowerCase().indexOf("timestamp")!=-1 || 
				this.type.toLowerCase().indexOf("datetime")!=-1 || 
				this.type.toLowerCase().indexOf("date")!=-1 || 
				this.type.toLowerCase().indexOf("time")!=-1 || 
				this.type.toLowerCase().indexOf("year")!=-1;
	}

	

}
