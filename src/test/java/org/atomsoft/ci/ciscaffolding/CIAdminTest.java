package org.atomsoft.ci.ciscaffolding;

import static org.junit.Assert.*;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

public class CIAdminTest {

	static final Log logger = LogFactory.getLog(CIAdminTest.class);
	@Test
	public void test() {
		DBTool dbtool = new DBTool();
		Document doc = Jsoup.parse("<ul></ul");
		Element ul = doc.select("ul").first();
		ul.addClass("nav nav-tabs");
		
		Map<String, String> tables = dbtool.getTables();
		for(Map.Entry<String,String> entry : tables.entrySet()){
			
			Element li = ul.appendElement("li");
			Element a = li.appendElement("a");
			a.attr("href","#")
			.attr("data-toggle","tab")
			.attr("url", "/"+entry.getKey()+"/list")
			.addClass("tab-menuitem")
			.text(entry.getValue()+"管理");
			
		}
		logger.info(ul.outerHtml());
	}

}
