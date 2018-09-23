package Engine;

public class Engine implements intEngine {
	
	public static final int CRAFT_EXP = 4;
	public static final int MAGIC_EXP = 83;
	
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

	public int getUnStrung() {
		return unStrung;
	}

	public void setUnStrung(int unStrung) {
		this.unStrung = unStrung;
	}

	public int getStrung() {
		return strung;
	}

	public int getAstral() {
		return astral;
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
	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.supplyValues();
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
	 */

	public Engine() {
		int resultValues[] = new int[5];		
		setStrung(90);
		setUnStrung(130);
		setAstral(200);
		setPlannedCasts(27);
		setValues(resultValues);
	}

	@Override
	public int[] supplyValues() {
		return values;	
	}

	
	/**
	 * 
	 * @param playerMagicLvl current magic lvl
	 * @param playerMagicExp or current magic exp
	 * @return 
	 * @throws Exception user entered no values
	 */
	public int nextLevel() throws Exception {
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
		else {
			setplayerMagicExp((int)playerMagicExp);
			setplayerMagicLvl(lvlFromExp((int)playerMagicExp));	
		}
		return expFromLevel(getplayerMagicLvl() + 1) - getplayerMagicExp();		
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
	/**
	 * The experience for each level is determined by the formula:
	 * 
	 * 	sum from 1 to level of:
	 *  level + 300 * 2 ^ (level/7)
	 * 	divided by 4 and rounded down.
	 *  
	 *  
	 *  
	 */
	public int expFromLevel(int level) {
			int exp = expFormula(level);
			exp = (int) Math.floor(exp)/4;
			setplayerMagicExp(exp);
			return exp;
	}
	
	private int expFormula(int level) {
	    int exp;
		exp = (int) Math.floor((level + 300 * Math.pow(2, (float)(level)/7)));
		if(level != 0)
		{
			exp += expFromLevel(level - 1);
		}
		return exp;
	}
	
	@Override
	public double costPerExp() {
		double expRate = ((computeExp()[0] + computeExp()[1]) / computeCost()) * 100;	
		return expRate;
	}

	@Override
	public int[] computeExp() {
		int[] z = new int[2];
		z[0] = MAGIC_EXP * getPlannedCasts();
		z[1] = CRAFT_EXP * getPlannedCasts();
		return z;
	}
	
	@Override
	public int computeCost() {	
		int costPerCast = getUnStrung() - getStrung() + getAstral();
		return costPerCast * getPlannedCasts();
	}
	
	@Override
	public String computeResults() {
		int mExp = 0;
		int cExp = 1;
		int cost = 2;
		int res[] = new int[3];
		
		computeExp();
		computeCost();
		
		double ratio = costPerExp();
		double nLvl = 5;
		
		try {
		 nLvl = nextLevel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//StringBuilder
		return "exp: " + getplayerMagicExp() + "lvl: " + getplayerMagicLvl() + "exp to lvl" + nLvl + " Casts:" + getPlannedCasts() + "Cost Per:" + res[2] + "Magic Exp:" + res[0] + "Ratio:" + ratio + "\n";
	}
	
	
}
