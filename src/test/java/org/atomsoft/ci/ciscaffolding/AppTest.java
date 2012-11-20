package org.atomsoft.ci.ciscaffolding;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atomsoft.ci.ciscaffolding.tpl.MyTemplates;
import org.junit.Test;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;


public class AppTest {
	static final Log logger = LogFactory.getLog(AppTest.class);
	//@Test
	public void testTemplate() throws Exception {
		Configuration cfg = new Configuration();
		ClassTemplateLoader loader = new ClassTemplateLoader(MyTemplates.class,
				"");

		cfg.setTemplateLoader(loader);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Map<String, Object> root = new HashMap<String, Object>();
		// 在根中放入字符串"user"
		root.put("mytpl", "Big Joe");
		root.put("age", 14);
		Template temp = cfg.getTemplate("Controller.ftl");
		Writer out = new OutputStreamWriter(System.out);
		temp.process(root, out);
		out.flush();
	}
	
	@Test
	public void testDB(){
		DBTool dbtool = new DBTool();
		Collection<String> tables = dbtool.getTables();
	    for(String table :tables){
	    	logger.info(table);
	    	for(String field:dbtool.getColumns(table)){
	    		logger.info("\t"+field);
	    	}
	    }
	}
}
