package Engine;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class SpellGet {

	public static final String SPELL_NAME = "monkaS";
	public static final String OSRS_WIKI_PHP = "https://oldschoolrunescape.wikia.com/api.php?action=parse&format=json&prop=wikitext&page=List_of_spells";

	/**
	 * https://oldschoolrunescape.wikia.com/wiki/List_of_spells
	 * https://oldschoolrunescape.wikia.com/api.php?action=parse&prop=wikitext&section=0&format=xml&page=
	 * |exp = 25\n| JAVA REGEX FORM
	 * 
	 * https://www.mediawiki.org/wiki/API:Main_page
	 * 
	 * exp=25\n
	 */

	/**
	 * Populates the cost from the OSRS Grand Exchange for a specific item.
	 * 
	 * We interpret a JSON response from the OSRS GE API with an org.json library
	 * included by using Maven.
	 * 
	 * @param item
	 *            **MUST BE ALL LOWERCASE** name of Runescape item
	 * @return the cost of item
	 * @throws IOException
	 *             if our URL is malformed due to invalid item name
	 */

	private int populateCost(String item) throws Exception {

		String s = OSRS_WIKI_PHP;
		s += URLEncoder.encode(item, "UTF-8");
		URL url = new URL(OSRS_WIKI_PHP);
		Scanner scan = new Scanner(url.openStream());
		String str = new String();

		while (scan.hasNext())
		{
			str += scan.nextLine();
			str += "\n";
		}
		scan.close();
		System.out.println(str);
		
		return 0;
	}

	public int collect(String str) {
		int firstIndex = str.indexOf("exp=");
		int offset = 4;
		String result = null;
		boolean flag = true;
		if (Character.isDigit(str.charAt(firstIndex + offset))) {
			System.out.println(str.charAt(firstIndex + offset));
			result = String.valueOf(str.charAt(firstIndex + offset));
		} else
			return -1;
		if (Character.isDigit(str.charAt(firstIndex + offset + 1))) {
			System.out.println(str.charAt(firstIndex + offset + 1));
			result += String.valueOf(str.charAt(firstIndex + offset + 1));
		} else
			return Integer.parseInt(result);
		if (Character.isDigit(str.charAt(firstIndex + offset + 2))) {
			System.out.println(str.charAt(firstIndex + offset + 2));
			result += String.valueOf(str.charAt(firstIndex + offset + 2));
		} else
			return Integer.parseInt(result);
		if (Character.isDigit(str.charAt(firstIndex + offset + 3))) {
			System.out.println(str.charAt(firstIndex + offset + 3));
			result += String.valueOf(str.charAt(firstIndex + offset + 3));
		} else
			return Integer.parseInt(result);
		return Integer.parseInt(result);
	}

	public static void main(String[] args) throws Exception {
		SpellGet sp = new SpellGet();
		sp.populateCost("remove");
	}

}
