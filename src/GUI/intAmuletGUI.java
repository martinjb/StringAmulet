package GUI;
/**
	It's true that if you start your GUI with a builder, 
	you are pretty much committed to using that builder throughout the whole life of the GUI. 
	This is sometimes acceptable, sometimes not -Marko
 * 
 *
 */

public interface intAmuletGUI {
	public void createFrame();
	public void handleListeners();
	public void createPricePanel();
	public void createResultPanel();
	public void show();
}
