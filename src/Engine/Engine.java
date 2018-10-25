package Engine;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import org.json.JSONObject;

/**
 * @author Jubal Brooks Martin
 * @date 9/27/2018
 * @version 1.0
 * 
 * TO-DO
 * Implement GUI.
 * Draw graph for Astral Rune.
 * Adapt this to work for any spell, get spell information from OSRS Wiki.
 * Possibly break this class up because it has a lot of var and methods.
 */
public class Engine implements intEngine {
	
	public static final String PLAYER_NAME = "trades 5 gp";
	public static final int CRAFT_EXP = 4;
	public static final int MAGIC_EXP = 83;
	public static final String OSRS_GE_API = "http://services.runescape.com/m=itemdb_oldschool/api/catalogue/items.json?category=1&alpha=";
	public static final String OSRS_HISCORES_URL = "https://secure.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
	
	private int strung;
	private int unStrung;
	private int astral;
	private int plannedCasts;
	private int castsToLvl;
	private int[] values;
	private int playerMagicLvl;
	private int playerMagicExp;
	
	public int getplayerMagicLvl() {
		return playerMagicLvl;
	}

	public void setplayerMagicLvl(int playerMagicLvl) {
		this.playerMagicLvl = playerMagicLvl;
	}

	public int getplayerMagicExp() {
		return playerMagicExp;
	}

	public void setplayerMagicExp(int playerMagicExp) {
		this.playerMagicExp = playerMagicExp;
	}

	public void setStrung(int strung) {
		this.strung = strung;
	}

	public void setUnStrung(int unStrung) {
		this.unStrung = unStrung;
	}

	public void setAstral(int astral) {
		this.astral = astral;
	}

	public int getPlannedCasts() {
		return plannedCasts;
	}

	public void setPlannedCasts(int plannedCasts) {
		this.plannedCasts = plannedCasts;
	}

	public int[] getValues() {
		return values;
	}
	
	public int getCastsToLvl() {
		return castsToLvl;
	}

	public void setCastsToLvl(int castsToLvl) {
		this.castsToLvl = castsToLvl;
	}

	public void setValues(int[] values) {
		this.values = values;
	}
	
	public int getAstral() {
		return astral;
	}
	
	public int getUnStrung() {
		return unStrung;
	}
	
	public int getStrung() {
		return strung;
	}
	
	public static void main(String[] args) {
		Engine engine = new Engine();
		try {
			System.out.println(engine.run());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	/* 
	 * Sets the costs for Strung Gold Amulets, Unstrung Amulets and Astral Runes.
	 * 	and the planned number of casts, (this should be optional).
	 */
	public Engine() {
		try {
			populatePlayerInfo(PLAYER_NAME);
			setAstral(populateCost("gold amulet (u)"));
			setStrung(populateCost("gold amulet"));
			setUnStrung(populateCost("astral rune"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method populates the player's magic level and experience.
	 * 
	 * Builds a HiScores link with a given player name in standard UTF-8 format.
     * The URL returns all a player's levels, in the format \d,\d,\d - Rank,Level,Experience 
	 * A space separates each triplet.
	 * A Scanner is used to read the URL output.
	 * Magic is the 7th listed spell so the loop eats the first six.
	 * The triplet for magic is split by commas, then read from an array and stored.
	 * 
	 * @param playerName Right now this is static, but in the future it should be retrieved from UI.
	 * @throws IOException If our URL is malformed, most likely the playername is invalid.
	 */
	private void populatePlayerInfo(String playerName) throws IOException {
	    String s = OSRS_HISCORES_URL;
	    int magic = 7;
	    String[] temp;
	    s += URLEncoder.encode(playerName, "UTF-8");
	    URL url = new URL(s);
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    int i = 0;
	    while(i<magic)
	      {
	    	scan.next();
	    	i++;
	      }
	    str = scan.next();
	    scan.close();
	    temp = str.split(",");
	    setplayerMagicLvl(Integer.parseInt(temp[1]));
	    setplayerMagicExp(Integer.parseInt(temp[2]));
	}
	

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
	    String s = OSRS_GE_API;
	    s += URLEncoder.encode(item, "UTF-8");
	    URL url = new URL(s);
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext())
	    	str += scan.nextLine();
	    scan.close();
	    JSONObject obj = new JSONObject(str);
	    int price = obj.getJSONArray("items").getJSONObject(0).getJSONObject("current").getInt("price");
	    return price; 
	}

	/**
	 * Calculate the exp and number of casts needed to get to the next magic level
	 * 
	 * Throws an exception if the player's magic level or exp is not present.
	 * If we know do not know exp, but do know magic level, we use expFromLevel() to set player exp.
	 * If we know exp and not level, we use lvlFromExp to set player magic level.
	 * 
	 * Once we know exp and level, we use expFromLevel for level+1 and find the difference between
	 * 	that and current exp.
	 * We use that number, divided by magic exp per spell cast, 
	 * 	to determine the number of casts to next level and call setPlannedCasts()
	 * 
	 * @param playerMagicLvl current magic lvl
	 * @param playerMagicExp current magic exp
	 * @return the amount of experience to next Magic level.
	 * @throws Exception if there are no values for magic exp or level.
	 */
	public int expToNextLvl() throws Exception {
		if(getplayerMagicExp() == 0)
		{
			if(getplayerMagicLvl() != 0)
			{
				setplayerMagicExp(expFromLevel(getplayerMagicLvl()));
			}
			else
			{
				throw new Exception("Please provide experience in Magic or its level.");
			}
		}
		else 
		{
			if(getplayerMagicLvl() == 0)
			{
				setplayerMagicLvl(lvlFromExp((int)playerMagicExp));
			}
		}
		int res = expFromLevel(getplayerMagicLvl() + 1) - getplayerMagicExp();
		setPlannedCasts(res/MAGIC_EXP);
		return res;		
	}

	/**
	 * The experience for each level is determined by the formula:
	 * sum from 2 to level of: (level - 1) + 300 * 2 ^ ((level-1)/7))
	 * divided by 4 and rounded down.
	 * 
	 * expFormula is called for the summation
	 * 
	 * @param level to find experience for
	 * @return experience for given level
	 */
	public int expFromLevel(int level) {
			int exp = expFormula(level, 0);
			exp = (int) Math.floor(exp)/4;
			return exp;
	}
	
	/**
	 * Helper method for expFromLevel()
	 * Recursively calculates Sigma [2 to x] of [(x-1) + 300 * 2 ^ ((x-1)/7)]
	 * @param level recursive counter
	 * @param sum Used to contain result.
	 * @return Top half of experience formula
	 */
	private int expFormula(int level, int sum) {
	    if (level==1) {
	    	return sum;
	    }
		sum += (int) Math.floor((level - 1) + 300 * Math.pow(2, (float)(level-1)/7));
		return expFormula(level - 1, sum);
	}
	
	/**
	 * https://www.reddit.com/r/2007scape/comments/3idn2b/exp_to_lvl_formula/ 
	 * 
	 * @param exp to find level for
	 * @return the level for given exp
	 */
	public int lvlFromExp(int exp) {
		int index;
        for (index = 0; index < 120; index++) {
            if (expFromLevel(index + 1) > exp)
                break;
        }
        return index;
	}
	

	/**
	 * Calculate the ratio of 1 gp to experience.
	 * @return that ratio.
	 */
	public double costPerExp() {
		double expRate = (computeCost() / MAGIC_EXP);	
		return expRate;
	}

	/**
	 * Calculates the total exp gained from a certain number of casts.
	 * 
	 * TO-DO Perhaps move number of casts to be a parameter.
	 * @return An array that contains magic exp and crafting exp.
	 */
	public int[] computeExp() {
		int[] z = new int[2];
		z[0] = MAGIC_EXP * getPlannedCasts();
		z[1] = CRAFT_EXP * getPlannedCasts();
		return z;
	}
	
	/**
	 * Uses populated costs for runes and materials and assumes you sell the strung amulets back for market cost.
	 * 
	 * TO-DO: Figure out how much first down arrow lowers price in GE or
	 * 		  find a good number to subtract from market price so strung amulet sells instantly.
	 * 
	 * @return the cost per magic spell cast.
	 */
	public int computeCost() {	
		int costPerCast = getUnStrung() - getStrung() + (getAstral() * 2);	
		return costPerCast;
	}
	
	/**
	 * Should be called on an Engine object to perform computation.
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String run() throws Exception {
		NumberFormat fm = NumberFormat.getInstance(Locale.US);
		double nLvl = expToNextLvl();
		int cost = computeCost();
		double ratio = costPerExp();
		StringBuilder sb = new StringBuilder(1024);
				sb.append("Currently ");
				sb.append("level " + getplayerMagicLvl()); sb.append(" with " + fm.format(getplayerMagicExp()) + "exp \n");
				sb.append("Level " + (getplayerMagicLvl() + 1) + " in " + getPlannedCasts() + " casts or "+ fm.format((int)nLvl) + "exp \n");
				sb.append("Cost to next level: " + fm.format(cost * getPlannedCasts()) + "gp\n"); 
				sb.append("Cost per cast: " + fm.format(cost) + "gp\n");
				sb.append("1gp per " + (int)ratio + "exp\n");
		return  sb.toString();
	}
	
}
