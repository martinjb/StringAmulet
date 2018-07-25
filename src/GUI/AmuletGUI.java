package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;        

public class AmuletGUI implements intAmuletGUI {
	    private static void createAndShowGUI() {
	        //Create and set up the window.
	    	float x = 0.0f;
	    	float y = 1.0f;
	    	float z = 0.4f;

	        JFrame entryContainer = new JFrame("This is the Window Title");
	        entryContainer.getContentPane().setBackground(new Color(x,y,z));
	        entryContainer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        entryContainer.setLayout(new GridLayout(2, 2));
	        
	        JPanel pricePanel = new JPanel(new FlowLayout());
	        JPanel resultPanel = new JPanel(new FlowLayout());	        
	        JPanel priceSPanel = new JPanel(new FlowLayout());
	        JPanel priceAPanel = new JPanel(new FlowLayout());
	        JPanel priceUPanel = new JPanel(new FlowLayout());	        
	        JPanel resultMPanel = new JPanel(new FlowLayout());
	        JPanel resultCPanel = new JPanel(new FlowLayout());
	        
	        pricePanel.setBorder(BorderFactory.createLineBorder(Color.black));
	        resultPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	        
	        
	        //JTextField textField = new JTextField("test");
	        //frame.getContentPane().add(textField);
	        JLabel lblPrices = new JLabel("Prices");
	        
	        JLabel lblAstral = new JLabel("Astral");
	        JFormattedTextField priceAstral = new JFormattedTextField();
	        
	        JLabel lblUnstrung = new JLabel("Unstrung");
	        JFormattedTextField priceUnstrung = new JFormattedTextField();
	        
	        JLabel lblStrung = new JLabel("Strung");
	        JFormattedTextField priceStrung = new JFormattedTextField();
	        
	        JLabel lblcExp = new JLabel("Crafting Experience");
	        JFormattedTextField resultCExp = new JFormattedTextField();
	        
	        JLabel lblmExp = new JLabel("Magic Experience");
	        JFormattedTextField resultMExp = new JFormattedTextField();
	    
	        priceAstral.setValue("pAstral"); 
	        priceUnstrung.setValue("pUnstrung"); 
	        priceStrung.setValue("pStrung"); 
	        
	        

	        
	        priceAPanel.add(lblAstral);
	        priceAPanel.add(priceAstral);        
	        priceUPanel.add(lblUnstrung);
	        priceUPanel.add(priceUnstrung);
	        priceSPanel.add(lblStrung);
	        priceSPanel.add(priceStrung);
	        
	        pricePanel.add(lblPrices);               
	        pricePanel.add(lblPrices);
	        pricePanel.add(priceAPanel);
	        pricePanel.add(priceUPanel);
	        pricePanel.add(priceSPanel);
	        
	        JLabel lblResults = new JLabel("Results");
	        resultPanel.add(lblResults);
	        
	        resultPanel.add(new JLabel());
	
			resultMPanel.add(lblmExp);
	        resultMPanel.add(resultMExp);
			resultCPanel.add(lblcExp); //make header labels
	        resultCPanel.add(resultCExp); //result Text field to create result need to be made
	        
	        resultPanel.add(resultCPanel);
	        resultPanel.add(resultMPanel);
	        
	        entryContainer.add(pricePanel);
	        entryContainer.add(resultPanel);
	        
	        //Display the window.
	        int windowWidth = 890;
	        int windowHeight = 500;
	        entryContainer.setBounds(50, 100, windowWidth, windowHeight);
	        //entryContainer.pack();
	        entryContainer.setVisible(true);
	    }

	    public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }

}
