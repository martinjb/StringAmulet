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
	public static final String OSRS_WIKI_PHP = "https://oldschoolrunescape.wikia.com/api.php?action=parse&prop=wikitext&section=0&format=json&page=";
	/**
	 * https://oldschoolrunescape.wikia.com/api.php?action=parse&prop=wikitext&section=0&format=json&page=Bones_to_Bananas
	 * |exp = 25\n|
	 * JAVA REGEX FORM
	 * 
	 * exp=25\n
	 */

	/**
	 * Populates the cost from the OSRS Grand Exchange for a specific item.
	 * 
	 * We interpret a JSON response from the OSRS GE API
	 * 	with an org.json library included by using Maven.
	 * 
	 * @param item **MUST BE ALL LOWERCASE** name of Runescape item
	 * @return the cost of item
	 * @throws IOException if our URL is malformed due to invalid item name
	 */
	
	private int populateCost(String item) throws Exception {
		
		String s = OSRS_WIKI_PHP;
	    s += URLEncoder.encode(item, "UTF-8");
	    URL url = new URL(s);
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext()) 
	    	str += scan.nextLine();
    		str += "\n";
	    scan.close();
    	System.out.println(str);
	    //JSONObject obj = new JSONObject(str);
	    //int price = obj.getJSONArray("items").getJSONObject(0).getJSONObject("current").getInt("price");
	    return 0; 
	}
	
	public static void main (String[] args) throws Exception {
		SpellGet sp = new SpellGet();
		sp.populateCost("bones to bananas");	
	}
	
}
