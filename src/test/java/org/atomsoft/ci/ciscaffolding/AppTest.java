package org.atomsoft.ci.ciscaffolding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.util.StringUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AppTest {
	static final Log logger = LogFactory.getLog(AppTest.class);
	DBTool dbtool = new DBTool();
	String base = "g:/webroot/newtest/";
	@Test
	public void generateCI() throws IOException, TemplateException {

		
		Map<String, String> tables = dbtool.getTables();
		for (Map.Entry<String, String> table : tables.entrySet()) {

			String tableName  = table.getKey();
			String tableLabel = table.getValue();	
			generateMvc(tableName,tableLabel,dbtool.getColumnsMetas(tableName));
		}
	}
	
	
	//@Test
	public void generateOne() throws IOException, TemplateException{
		generateMvc("artitle","新闻",dbtool.getColumnsMetas("artitle"));
	}
	
	

	void generateMvc(String tableName,String tableLebel,Map<String, ColumnMeta> attrs ) throws IOException, TemplateException{
		this.generateFile(tableName, tableLebel, attrs, ".php",
				"Controller", "controllers", "application/controllers",
				true, false,null);
		this.generateFile(tableName, tableLebel, attrs, ".php",
				"Model", "models", "application/models", true, false,StringUtils.uncapitalize(tableName)+"_model");
		this.generateFile(tableName,tableLebel, attrs, ".js",
				"edit", "scripts", "resources/scripts/" + tableName,
				false, true,null);
		this.generateFile(tableName, tableLebel, attrs, ".js",
				"list", "scripts", "resources/scripts/" + tableName,
				false, true,null);
		this.generateFile(tableName,tableLebel, attrs, ".css",
				"style", "styles", "resources/styles/" + tableName,
				false, true,null);
		this.generateFile(tableName, tableLebel, attrs, ".php",
				"editNew", "views", "application/views/" + tableName,
				false, true,null);
		this.generateFile(tableName, tableLebel, attrs, ".php",
				"list", "views", "application/views/" + tableName,
				false, true,null);
		this.generateFile(tableName, tableLebel, attrs, ".php",
				"index", "views", "application/views/" + tableName,
				false, true, "index");
	}
	
	
	
	void generateFile(String table, String label,
			Map<String, ColumnMeta> attrs, String type, String tempName,
			String relclassPath, String relFilePath, boolean caplize,
			boolean tempAsName,String speicalName) throws IOException, TemplateException {
		
		Configuration cfg = new Configuration();
		ClassTemplateLoader loader = new ClassTemplateLoader(
				org.atomsoft.ci.ciscaffolding.tpl.MyTemplates.class,
				relclassPath);
		cfg.setTemplateLoader(loader);
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		Map<String, Object> root = new HashMap<String, Object>();
		// 在根中放入字符串"user"
		root.put("entityName", table);
		root.put("entityLabel", label);
		root.put("createDate", new Date().toString());
		root.put("attrs", attrs);
		Template temp = cfg.getTemplate(tempName + ".ftl");
		String path = base
				+ File.separator
				+ relFilePath
				+ File.separator
				+ 
				(speicalName==null?(
				(tempAsName ? tempName : table)
				):speicalName) + type;
		File file = new File(path);
		FileUtils.write(file, "");
		OutputStream outs = new FileOutputStream(file);
		logger.info(file.getAbsoluteFile());
		Writer out = new OutputStreamWriter(outs);
		temp.process(root, out);
		out.flush();
	}

	

}
