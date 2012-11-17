package org.atomsoft.ci.ciscaffolding;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.atomsoft.ci.ciscaffolding.tpl.MyTemplates;
import org.junit.Test;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;


public class AppTest {
	
	@Test
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
}
