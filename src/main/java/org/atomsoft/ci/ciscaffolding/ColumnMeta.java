package org.atomsoft.ci.ciscaffolding;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ColumnMeta {
	private String table;
	private String name;
	private String comment;
	private String inputType;
	private String extra;
	private String html;
	private boolean pkAble;
    static final Log logger = LogFactory.getLog(ColumnMeta.class);
	public ColumnMeta(String name, String comment) {
		super();
		this.name = name;
		comment = StringUtils.trim(comment);
		String[] mtas = comment.split(",");
		int len = mtas.length;
		Document doc = Jsoup.parse("");
		Element input = null;
		this.comment = mtas[0];
		switch (len) {
		case 1:// 对应主键注释，例如 ：编号
		{
			// value="${r"<?php echo isset($id)?$"}${meta.name} ${r":'';?>"} "/>
			this.inputType = "hidden";
			input = doc.createElement("input");
			input.attr("id", name).attr("type", inputType).attr("name", name)
					.val(parseValue(name));
		
			this.setHtml(input.outerHtml());
			break;
		}
		case 2: {
			this.inputType = mtas[1];
			if ("ckeditor".equalsIgnoreCase(inputType))
				this.setHtml(this.parseText(inputType, name, null));
			else {
				input = this.parseInput(inputType, name);				
				this.setHtml(input.outerHtml());
			}
			break;
		}
		case 3: {
			inputType = mtas[1];
			String extra = mtas[2];
			if ("select".equalsIgnoreCase(inputType)) {

				this.setHtml(this.parseText(inputType, name, extra));
			} else {
				input = this.parseInput(inputType, name);
				input.addClass(extra);
				this.setHtml(input.outerHtml());
			}
			break;
		}
		default:;

		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getInputType() {
		return inputType;
	}

	public String getExtra() {
		return extra;
	}

	Element parseInput(String type, String name) {
		Document doc = Jsoup.parse("");
	
		Element input = null;
	    
		switch (StringUtils.trim(type).toLowerCase()) {
		case "text": {
			input = doc.createElement("input");
			input.attr("name", name).attr("id", name).attr("type", "text")
					.val(parseValue(name));
			break;
		}
		case "checkbox": {

			input = doc.createElement("input");
			input.attr("type", "checkbox").attr("id", name).attr("name", name)
					.val(parseValue(name));

			break;
		}
		case "textarea": {
			input = doc.createElement("textarea");
			input.attr("id", name).attr("name", name).text(parseValue(name));
			break;

		}
		case "file": {
			input = doc.createElement("input");
			input.attr("id", name).attr("type", "file").attr("name", name);
			break;
		}
		case "password": {
			input = doc.createElement("input");
			input.attr("name", name).attr("id", name).attr("type", "password")
					.val(parseValue(name));
			break;
		}
		case "datepicker":{			
		       
			input = doc.createElement("input");
			input.attr("type","text")
			.attr("name",name)
			.attr("id",name)
			.attr("data-date-format","yyyy-mm-dd")
			.attr("readOnly","true")
			.addClass("datepicker")
			.val(parseValue(name));
			break;
		
			
		}
		}

		return input;

	}

	String parseText(String type, String name, String table) {
		if ("ckeditor".equalsIgnoreCase(type)) {
			return "<?php echo $my_editor;?>";
		} else if ("select".equalsIgnoreCase(type)) {
			return "<?= form_dropdown(\"" + name + "\",isset($" + table + "s)?$"+table+"s:array(),isset($" + name + ")?$"+name+":\"\")?>";
		} else {
			throw new java.lang.IllegalArgumentException("类型不符合:" + type);
		}
	}

	String parseValue(String name) {
		return String.format("<?=val($%s)?>",name);
	}

	public String getHtml() {
		return html.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public boolean isPkAble() {
		return pkAble;
	}

	public void setPkAble(boolean pkAble) {
		this.pkAble = pkAble;
	}

}
