package GUI;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JWindow;        

public class AmuletGUI {
	    private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame entryContainer = new JFrame("This is the Window Title");
	        entryContainer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	        
	        //JTextField textField = new JTextField("test");
	        //frame.getContentPane().add(textField);
	        JLabel lblPrices = new JLabel("Prices");
	        entryContainer.setLayout(new GridLayout(4, 3));
	        JLabel lblAstral = new JLabel("Astral");
	        JFormattedTextField priceAstral = new JFormattedTextField();
	        JLabel lblUnstrung = new JLabel("Unstrung");
	        JFormattedTextField priceUnstrung = new JFormattedTextField();
	        JLabel lblStrung = new JLabel("Strung");
	        JFormattedTextField priceStrung = new JFormattedTextField();
	        //ftf.validate();
	        priceAstral.setValue("Test"); 
	        
	        entryContainer.add(lblPrices);
	        entryContainer.add(new JLabel());
	        entryContainer.add(lblAstral);
	        entryContainer.add(priceAstral);
	        entryContainer.add(lblUnstrung);
	        entryContainer.add(priceUnstrung);
	        entryContainer.add(lblStrung);
	        entryContainer.add(priceStrung);
	        
	        entryContainer.add(lblResults);
	        entryContainer.add(new JLabel());
	        entryContainer.add(resultAstral);
	        entryContainer.add(lblmExp);
	        entryContainer.add(resultAstral);
	        entryContainer.add(lblcExp); //make header labels
	        entryContainer.add(resultAstral); //result Text field to create result need to be made
	        entryContainer.add(lblRequest);
	        entryContainer.add(checkRequest);
	        entryContainer.add(priceAstral);
	        entryContainer.add(lblUnstrung);
	        entryContainer.add(priceUnstrung);
	        entryContainer.add(lblStrung);
	        entryContainer.add(priceStrung);
	        
	        
	        //Display the window.
	        int windowWidth = 890;
	        int windowHeight = 500;
	        entryContainer.setBounds(50, 100, windowWidth, windowHeight);
	        entryContainer.pack();
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
