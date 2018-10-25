package Engine;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sql.DataSource;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;
import org.apache.ddlutils.model.Table;

import java.sql.*;

/**
 * http://db.apache.org/ddlutils/api-usage.html
 * http://db.apache.org/ddlutils/api-usage.html#The+model
 * 
 * Turbine XML format
 * 
 * @author Jubal Martin
 *
 */

public class SpellDB {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/SpellDB";

	// Database credentials
	static final String USER = "admin";
	static final String PASS = "password";

	public static void main(String[] args) throws SQLException {
		SpellDB spell = new SpellDB();
		Database db = spell.readDatabaseFromXML("D:\\eclipse_workspace\\StringAmulet\\database\\SpellDB.xml");
		final BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(JDBC_DRIVER);
        ds.setUrl(DB_URL);
		ds.setUsername(USER);
		ds.setPassword(PASS);
	}
	        

	public Database readDatabaseFromXML(String fileName) {
		return new DatabaseIO().read(fileName);
	}

	public void insertData(DataSource dataSource, Database database, int id, String name, int experience,
			int requirement) {
		Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
		DynaBean spell = database.createDynaBeanFor("spell", false);
		spell.set("id", id);
		spell.set("name", name);
		spell.set("experience", experience);
		spell.set("requirement", requirement);
		platform.insert(database, spell);
	}

	public void readSpells(DataSource dataSource, Database database) {
		Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
		ArrayList<String> params = new ArrayList<String>();

		params.add("example spell");

		Iterator it = platform.query(database, "select * from spell where id = ?", params,
				new Table[] { database.findTable("spell_info") });

		while (it.hasNext()) {
			DynaBean spell = (DynaBean) it.next();
			System.out.println(spell.get("title"));
		}
	}
}
