package Engine;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.beanutils.DynaBean;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.io.DatabaseIO;
import org.apache.ddlutils.model.Database;

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

	public void writeDatabaseToXML(Database db, String fileName) {
		new DatabaseIO().write(db, fileName);
	}

	public Database readDatabaseFromXML(String fileName) {
		return new DatabaseIO().read(fileName);
	}

	public void insertData(DataSource dataSource, Database database) {
		Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);

		// "author" is a table of the model
		DynaBean author = database.createDynaBeanFor("author", false);

		// "name" and "whatever" are columns of table "author"
		author.set("name", "James");
		author.set("whatever", new Integer(1234));

		platform.insert(database, author);
	}
	
	public void dumpBooks(DataSource dataSource, Database database) {
		Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
		ArrayList params = new ArrayList();

		params.add("Some title");

		Iterator it = platform.query(database, "select * from book where title = ?", params,
				new Table[] { database.findTable("book") });

		while (it.hasNext()) {
			DynaBean book = (DynaBean) it.next();

			System.out.println(book.get("title"));
		}
	}
}
