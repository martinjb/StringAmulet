package Engine;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Engine implements intEngine {
	
	public static final int CRAFT_EXP = 4;
	public static final int MAGIC_EXP = 83;
	public static final String OSRS_GE_API = "http://services.runescape.com/m=itemdb_oldschool/api/catalogue/items.json?category=1&alpha=";
	public static final String OSRS_HISCORES_URL = "https://secure.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";
	
	private int strung;
	private int unStrung;
	private int astral;
	private int plannedCasts;
	private int castsToLvl;
	private int costPerCast;
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
		System.out.println(engine.computeResults());
	}	
	
	/* 
	 * Sets the costs for Strung Gold Amulets, Unstrung Amulets and Astral Runes.
	 * and the planned number of casts, (this should be optional).
	 * 
	 * It also initializes the result value array the should report,
	 * 0 - The number of casts to the next magic level.
	 * 1 - Total cost to next magic level.
	 * 2 - Total cost for planned number of casts.
	 * 3 - Magic experience gained from planned casts.
	 * 4 - Crafting experience gained from planned casts.
	 * 5 - Cost / Experience Ratio
	 * 6 - 
	 */

	public Engine() {
		int resultValues[] = new int[5];	
		//int inputPlannedCasts = 27;
		//int inputPlayerMagicLevel = 1;
		//int inputPlayerMagicExp = 83;
		//populatePlayerInfo();
		//populateCosts();
		//setPlannedCasts(inputPlannedCasts);
		setplayerMagicLvl(44);
		//setplayerMagicExp(inputPlayerMagicExp);
		setValues(resultValues);
	}

	private void populatePlayerInfo(String playerName) throws Exception {

	    String s = OSRS_HISCORES_URL;
	    s += URLEncoder.encode(playerName, "UTF-8");
	    URL url = new URL(s);
	    Scanner scan = new Scanner(url.openStream());
	    String str = new String();
	    while (scan.hasNext())
	        str += scan.nextLine();
	    scan.close();
	    //setplayerMagicExp(83);
	    System.out.println(str);
	}

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
	 * @param playerMagicLvl current magic lvl
	 * @param playerMagicExp or current magic exp
	 * @return 
	 * @throws Exception user entered no values
	 */
	public int expToNextLvl() throws Exception {
		if(getplayerMagicExp() == 0)
		{
			if(getplayerMagicLvl() != 0)
			{
				return expFromLevel(getplayerMagicLvl());
			}
			else
			{
				throw new Exception("Please provide experience in Magic or its level.");
			}
		}
		else {
			setplayerMagicExp((int)playerMagicExp);
			setplayerMagicLvl(lvlFromExp((int)playerMagicExp));	
		}
		int res = expFromLevel(getplayerMagicLvl() + 1) - getplayerMagicExp();
		setPlannedCasts(res/MAGIC_EXP);
		return res;		
	}

	/**
	 * The experience for each level is determined by the formula:
	 * 
	 * 	sum from 2 to level of:
	 *  (level - 1) + 300 * 2 ^ ((level-1)/7))
	 * 	divided by 4 and rounded down.
	 * 
	 * 
	 */
	public int expFromLevel(int level) {
			int exp = expFormula(level, 0);
			exp = (int) Math.floor(exp)/4;
			System.out.println("\n" + exp);
			setplayerMagicExp(exp);
			return exp;
	}
	/**
	 * This is a helper method for expFromLevel()
	 * Recursively calculates Sigma [2 to x] of [(x-1) + 300 * 2 ^ ((x-1)/7)]
	 * @param level How many recursive calls.
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
	
	/*
	 * https://www.reddit.com/r/2007scape/comments/3idn2b/exp_to_lvl_formula/ 
	 */
	public int lvlFromExp(int exp) {
		int index;
        for (index = 0; index < 120; index++) {
            if (expFromLevel(index + 1) > exp)
                break;
        }
        setplayerMagicLvl(index);
        return index;
	}
	
	
	public double costPerExp() {
		double expRate = ((computeExp()[0] + computeExp()[1]) / computeCost()) * 100;	
		return expRate;
	}


	public int[] computeExp() {
		int[] z = new int[2];
		z[0] = MAGIC_EXP * getPlannedCasts();
		z[1] = CRAFT_EXP * getPlannedCasts();
		return z;
	}
	

	public int computeCost() {	
		int costPerCast = getUnStrung() - getStrung() + getAstral();
		return 1;
		//return costPerCast * getPlannedCasts();
	}
	
	/* 
	 * ---Result array should report---
	 * 0 - The number of casts to the next magic level.
	 * 1 - Total cost to next magic level.
	 * 2 - Total cost for planned number of casts.
	 * 3 - Magic experience gained from planned casts.
	 * 4 - Crafting experience gained from planned casts.
	 * 5 - Cost / Experience Ratio
	 * 6 - Buffer
	 */
	public String computeResults() {
		int res[] = new int[6];
		computeExp();
		computeCost();
		try {
			populatePlayerInfo("Trades 5 gp");
			setAstral(populateCost("gold amulet (u)"));
			setStrung(populateCost("gold amulet"));
			setUnStrung(populateCost("astral rune"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		double ratio = costPerExp();
		double nLvl = 5;
		
		try {
		 nLvl = expToNextLvl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder(1024);
				sb.append("Currently ");
				sb.append("level " + getplayerMagicLvl()); sb.append(" with " + getplayerMagicExp() + " experience \n");
				sb.append("Level " + (getplayerMagicLvl() + 1) + " in " + getPlannedCasts() + " casts or "+ (int)nLvl + " experience \n");
				sb.append("Cost per: " + res[2] + "\n"); 
				sb.append("Ratio of experience to GP: " + ratio + "\n");
		return  sb.toString();
	}
	
	
}
