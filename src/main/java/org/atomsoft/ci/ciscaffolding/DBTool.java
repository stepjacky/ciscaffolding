package org.atomsoft.ci.ciscaffolding;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Hello world!
 * 
 */
public class DBTool {
	static final Log logger = LogFactory.getLog(DBTool.class);
	static JdbcTemplate jdbcTemp;
	static {
		connect();
	}

	public static void connect() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/beshop");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		jdbcTemp = new JdbcTemplate(dataSource);
	}

	public Map<String, String> getTables() {
		String sql = "SHOW TABLE STATUS;";
		final Map<String, String> mp = new LinkedHashMap<>();
		jdbcTemp.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String cm = rs.getString(18);
				String[] ch  = cm.split(",");
				if(ch.length==1)mp.put(rs.getString(1), cm);
				return "";
			}

		});
		return mp;

	}

	public Map<String, ColumnMeta> getColumnsMetas(final String table) {
		String sql = String.format("SHOW FULL FIELDS FROM `%s`", table);
		final Map<String, ColumnMeta> mp = new java.util.LinkedHashMap<>();
		jdbcTemp.query(sql, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String name = rs.getString(1);				
				String comment = rs.getString(9);
				ColumnMeta cm = new ColumnMeta(name,comment);
				cm.setTable(table);
				mp.put(name, cm);
				return null;
			}

		});
		return mp;
	}
}
