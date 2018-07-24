package Engine;

public class Engine implements intEngine {
	public static final int craftExp = 4;
	public static final int magicExp = 83;
	
	public static final int STRUNG = 0;
	public static final int UNSTRUNG = 1;
	public static final int ASTRAL = 2;
	public static final int CASTS = 3;
	
	
	private int strung;
	private int unStrung;
	private int astral;
	private int plannedCasts;
	private int[] values;
	
	public void setValues(int[] values) {
		this.values = values;
	}

	public static void main(String[] args) {
		Engine engine = new Engine();
		engine.supplyValues();
		System.out.println(engine.computeResults());
	}
	
	public Engine() {
		
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


	@Override
	public int[] supplyValues() {	
		int providedValues[] = new int[4];
		
		providedValues[STRUNG] = 90;//getStrung();
		providedValues[UNSTRUNG] = 130;//getUnStrung();
		providedValues[ASTRAL] = 200;//getAstral();
		providedValues[CASTS] = 27;//getPlannedCasts() from SWING;
		setValues(providedValues);
		setPlannedCasts(providedValues[CASTS]);
		return getValues();
	}


	public int[] getValues() {
		return values;
	}

	@Override
	public int calculateCost(int[] x) {

		
		int diff = x[UNSTRUNG] - x[STRUNG];
		int runes = x[ASTRAL];
		
		return diff + runes;
	}

	@Override
	public int[] computeExp(int[] y) {
		int plannedCasts = 3;
		int mExp = 0;
		int cExp = 1;
		int totalExp = 2;
		
		int magic = magicExp * y[plannedCasts];
		int craft = craftExp * y[plannedCasts];
		int vals[] = new int[4];
		
		vals[mExp] = magic;
		vals[cExp] = craft;
		vals[totalExp] = magic + craft;
		
		return vals;
	}

	@Override
	public double costPerExp(int z[]) {
		int mExp = 0;
		int cExp = 1;
		int tcost = 2;
		
		int exp = z[mExp] + z[cExp];
		System.out.println(z[mExp]);
		int cost = z[tcost];
		System.out.println(z[tcost]);
		double expRate = ((double)cost/ (double)exp) * 100;
		
		return expRate;
	}

	@Override
	public String computeResults() {
		int mExp = 0;
		int cExp = 1;
		int cost = 2;
		int res[] = new int[3];
		
		//get magic exp for all casts
		res[mExp] = computeExp(getValues())[mExp];
		//get craft exp for all casts
		res[cExp] = computeExp(getValues())[cExp];
		//get cost for all casts
		res[cost] = calculateCost(getValues());
		
		//get ratio
		double ratio = costPerExp(res);
		
		return "Casts:" + getPlannedCasts() + "Cost Per:" + res[2] + "Magic Exp:" + res[0] + "Ratio:" + ratio + "\n";
	}
	
	
}
