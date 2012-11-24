package org.atomsoft.ci.ciscaffolding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.atomsoft.ci.ciscaffolding.tpl.MyTemplates;
import org.junit.Test;
import org.springframework.util.StringUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AppTest {
	static final Log logger = LogFactory.getLog(AppTest.class);
	static final Map<String, String> extMap = new HashMap<String, String>() {
		{
			put("controllers", "php");
			put("models", "php");
			put("sytles", "css");
			put("scripts", "js");
			put("views", "php");
		}
	};

	// @Test
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
	public void testDB() throws IOException, TemplateException {

		DBTool dbtool = new DBTool();
		Map<String, String> tables = dbtool.getTables();
		for (Map.Entry<String, String> table : tables.entrySet()) {

			String tableName = table.getKey();
			Map<String, ColumnMeta> attrs = dbtool.getColumnsMetas(table
					.getKey());

			if ("artitle".equals(tableName)) {
				this.generateFile(tableName, table.getValue(), attrs, ".php",
						"Controller", "controllers", "application/controllers",
						true,false);
				this.generateFile(tableName, table.getValue(), attrs, ".php",
						"Model", "models", "application/models", true,false);
				this.generateFile(tableName, table.getValue(), attrs, ".js",
						"edit", "scripts", "resources/scripts/" + tableName,
						false,true);
				this.generateFile(tableName, table.getValue(), attrs, ".js",
						"list", "scripts", "resources/scripts/" + tableName,
						false,true);
				this.generateFile(tableName, table.getValue(), attrs, ".css",
						"style", "styles", "resources/styles/" + tableName,
						false,true);
				this.generateFile(tableName, table.getValue(), attrs, ".php",
						"editNew", "views", "application/views/" + tableName,
						false,true);
				this.generateFile(tableName, table.getValue(), attrs, ".php",
						"list", "views", "application/views/" + tableName,
						false,true);
				break;
			}
		}
	}

	void generateFile(String table, String label,
			Map<String, ColumnMeta> attrs, String type, String tempName,
			String relclassPath, String relFilePath, boolean caplize,boolean tempAsName)
			throws IOException, TemplateException {
		String base = "";
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
		String path = base + File.separator + relFilePath + File.separator
				+ (caplize?StringUtils.capitalize(tempAsName?tempName:table):(tempAsName?tempName:table)) + type;
		File file = new File(path);
		// OutputStream outs = new FileOutputStream(file);
		logger.info(file.getAbsoluteFile());
		// Writer out = new OutputStreamWriter(outs);
		// temp.process(root, out);
		// out.flush();
	}

	// @Test
	public void testJcl() throws MalformedURLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		URL url = new URL(
				"jar:file:G:/maven/repo/com/google/code/gson/gson/2.2.1/gson-2.2.1.jar!/");
		@SuppressWarnings("resource")
		URLClassLoader uc = new URLClassLoader(new URL[] { url });
		Class<?> cls = uc.loadClass("com.google.gson.Gson");
		Object obj = cls.newInstance();
		logger.info(obj);
	}

}
