package sushigame.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import comp401.sushi.AvocadoPortion;
import comp401.sushi.CrabPortion;
import comp401.sushi.EelPortion;
import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri;
import comp401.sushi.Nigiri.NigiriType;
import comp401.sushi.RicePortion;
import comp401.sushi.Roll;
import comp401.sushi.SalmonPortion;
import comp401.sushi.Sashimi;
import comp401.sushi.Sashimi.SashimiType;
import comp401.sushi.SeaweedPortion;
import comp401.sushi.ShrimpPortion;
import comp401.sushi.Sushi;
import comp401.sushi.TunaPortion;

public class PlayerChefView extends JPanel implements ActionListener, ChangeListener {

	private List<ChefViewListener> listeners;
	private int belt_size;
	private JRadioButton[] type;
	private JRadioButton[] seafood;
	private JCheckBox[] ing;
	private JSlider[] num;
	private JRadioButton[] color;
	private JSlider gold;
	private JSlider position;
	private Sushi sushi;
	private int place_to_place;
	
	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//create the overall framework
		JPanel a1 = new JPanel();
		a1.setLayout(new GridLayout(1,3));
		add(a1);
		
		JPanel a2 = new JPanel();
		a2.setLayout(new GridLayout(1,5));
		add(a2);
		
		JPanel a3 = new JPanel();
		a3.setLayout(new GridLayout(8,2));
		add(a3);
		
		JPanel a4 = new JPanel();
		a4.setLayout(new GridLayout(1,4));
		add(a4);
		
		JPanel a5 = new JPanel();
		a5.setLayout(new BorderLayout());
		add(a5);
		
		JPanel a6 = new JPanel();
		a6.setLayout(new BorderLayout());
		add(a6);
		
		JPanel a7 = new JPanel();
		a7.setLayout(new BorderLayout());
		add(a7);
		
		//create radio button for 3 sushi types
		type = new JRadioButton[3];
		ButtonGroup type_group = new ButtonGroup();
		
		type[0] = new JRadioButton("sashimi");
		a1.add(type[0]);
		type[0].setActionCommand("sashimi");
		type[0].addActionListener(this);
		
		type[1] = new JRadioButton("nigiri");
		a1.add(type[1]);
		type[1].setActionCommand("nigiri");
		type[1].addActionListener(this);
		
		type[2] = new JRadioButton("roll");
		a1.add(type[2]);
		type[2].setActionCommand("roll");
		type[2].addActionListener(this);
		
		for(JRadioButton b: type) {
			type_group.add(b);
		}
		
		//create radio button for ingredients of either sashimi or nigiri
		seafood = new JRadioButton[5];
		ButtonGroup seafood_group = new ButtonGroup();
		
		seafood[0] = new JRadioButton("crab");
		seafood[0].setActionCommand("crab");
		seafood[0].addActionListener(this);
		
		seafood[1] = new JRadioButton("eel");
		seafood[1].setActionCommand("eel");
		seafood[1].addActionListener(this);
		
		seafood[2] = new JRadioButton("salmon");
		seafood[2].setActionCommand("salmon");
		seafood[2].addActionListener(this);
		
		seafood[3] = new JRadioButton("shrimp");
		seafood[3].setActionCommand("shrimp");
		seafood[3].addActionListener(this);
		
		seafood[4] = new JRadioButton("tuna");
		seafood[4].setActionCommand("tuna");
		seafood[4].addActionListener(this);
		
		for(JRadioButton b: seafood) {
			seafood_group.add(b);
			a2.add(b);
		}
		
		//create checkbox and slider for each ingredient in order to identify the type of the sushi
		ing = new JCheckBox[8];
		num = new JSlider[8];
		
		ing[0] = new JCheckBox("avocado");
		ing[0].setActionCommand("roll_avocado");
		ing[0].addActionListener(this);
		num[0] = new JSlider(JSlider.HORIZONTAL,
                0,150,1);
	
		ing[1]= new JCheckBox("crab");
		ing[1].setActionCommand("crab_avocado");
		ing[1].addActionListener(this);
		num[1] = new JSlider(JSlider.HORIZONTAL,
				0,150,1);
		
		ing[2] = new JCheckBox("eel");
		ing[2].setActionCommand("eel_avocado");
		ing[2].addActionListener(this);
		num[2] = new JSlider(JSlider.HORIZONTAL,
                0,150,1);

		ing[3] = new JCheckBox("rice");
		ing[3].setActionCommand("rice_avocado");
		ing[3].addActionListener(this);
		num[3] = new JSlider(JSlider.HORIZONTAL,
                0,150,1);
	
		ing[4] = new JCheckBox("salmon");
		ing[4].setActionCommand("salmon_avocado");
		ing[4].addActionListener(this);
		num[4] = new JSlider(JSlider.HORIZONTAL,
                0,150,10);
		
		ing[5] = new JCheckBox("seaweed");
		ing[5].setActionCommand("seaweed_avocado");
		ing[5].addActionListener(this);
		num[5] = new JSlider(JSlider.HORIZONTAL,
                0,150,1);
		
		ing[6] = new JCheckBox("shrimp");
		ing[6].setActionCommand("shrimp_avocado");
		ing[6].addActionListener(this);
		num[6] = new JSlider(JSlider.HORIZONTAL,
                0,150,1);
		
		ing[7] = new JCheckBox("tuna");
		ing[7].setActionCommand("tuna_avocado");
		ing[7].addActionListener(this);
		num[7] = new JSlider(JSlider.HORIZONTAL,
                0,150,1);

		for(int i = 0; i<8; i++) {
			a3.add(ing[i]);
			ing[i].setEnabled(true);
			a3.add(num[i]);
			num[i].addChangeListener(this);
			num[i].setMajorTickSpacing(30);
			num[i].setMinorTickSpacing(10);
			num[i].setPaintTicks(true);
			num[i].setPaintLabels(true);
		}
		
		//create a button group for choosing the plate color
		color = new JRadioButton[4];
		ButtonGroup plate_color = new ButtonGroup();
		
		color[0] = new JRadioButton("red");
		color[0].setActionCommand("red_plate");
		color[0].addActionListener(this);
		
		color[1] = new JRadioButton("green");
		color[1].setActionCommand("green_plate");
		color[1].addActionListener(this);
		
		color[2] = new JRadioButton("blue");
		color[2].setActionCommand("blue_plate");
		color[2].addActionListener(this);
		
		color[3] = new JRadioButton("gold");
		color[3].setActionCommand("gold_plate");
		color[3].addActionListener(this);
		
		for(JRadioButton team: color) {
			plate_color.add(team);
			a4.add(team);
		}
		
		JLabel gold_price = new JLabel("Choose the price for gold plate: ");
		a5.add(gold_price, BorderLayout.NORTH);
		
		//create the slider for the price of gold plate
		gold = new JSlider(JSlider.HORIZONTAL,
                500,1000,500);
		a5.add(gold, BorderLayout.SOUTH);
		gold.addChangeListener(this);
		gold.setMajorTickSpacing(50);
		gold.setPaintTicks(true);
		gold.setPaintLabels(true);
		
		JLabel pose = new JLabel("Choose the position to put the plate: ");
		a6.add(pose, BorderLayout.NORTH);
		
		//create a JSlider for choosing position from 1-20	
		position = new JSlider(JSlider.HORIZONTAL,
                0,19,0);
		a6.add(position, BorderLayout.SOUTH);
		position.addChangeListener(this);
		position.setMajorTickSpacing(1);
		position.setPaintTicks(true);
		position.setPaintLabels(true);
		position.setSnapToTicks(true);
		
		//create a JButton to make the user-wanted sushi at the specific position
		JButton make_button = new JButton("Make the Sushi at this position");
		make_button.setActionCommand("make the sushi");
		make_button.addActionListener(this);
		a7.add(make_button);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}
	
	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//if it is a sashimi
		if(type[0].isSelected()) {
			for(JCheckBox ins: ing) {
				ins.setEnabled(false);
			}
			for(JSlider text: num) {
				text.setEnabled(false);
			}
			
			for(JRadioButton ins: seafood) {
				ins.setEnabled(true);
			}
			
			color[3].setEnabled(false);
			gold.setEnabled(false);
			
			if(seafood[0].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.CRAB), place_to_place);                    
				}
			}
			if(seafood[1].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.EEL), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.EEL), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.EEL), place_to_place);                    
				}
			}
			if(seafood[2].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.SALMON), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.SALMON), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.SALMON), place_to_place);                    
				}
			}
			if(seafood[3].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.SHRIMP), place_to_place);                    
				}
			}
			if(seafood[4].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Sashimi(Sashimi.SashimiType.TUNA), place_to_place);                    
				}

			}
			//if nigiri is selected
		}else if(type[1].isSelected()) {
			for(JRadioButton ins: seafood) {
				ins.setEnabled(true);
			}
			
			for(JCheckBox ins: ing) {
				ins.setEnabled(false);
			}
			for(JSlider text: num) {
				text.setEnabled(false);
			}
			
			color[3].setEnabled(false);
			gold.setEnabled(false);
			
			if(seafood[0].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.CRAB), place_to_place);                    
				}
			}
			if(seafood[1].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.EEL), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.EEL), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.EEL), place_to_place);                    
				}
			}
			if(seafood[2].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.SALMON), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.SALMON), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.SALMON), place_to_place);                    
				}
			}
			if(seafood[3].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.SHRIMP), place_to_place);                    
				}
			}
			if(seafood[4].isSelected()) {
				if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeRedPlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), place_to_place);  
				}
				if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
					makeGreenPlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), place_to_place);
				}
				if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
					makeBluePlateRequest(new Nigiri(Nigiri.NigiriType.TUNA), place_to_place);                    
				}
			}
			// if it is a roll
		}else if(type[2].isSelected()) {
			for(JRadioButton ins: seafood) {
				ins.setEnabled(false);
			}
			
			for(JCheckBox ins: ing) {
				ins.setEnabled(true);
			}
			for(JSlider text: num) {
				text.setEnabled(true);
			}
			
			color[3].setEnabled(true);
			gold.setEnabled(true);
			
			//collect all the ingredients that are going to be used for making a roll
			IngredientPortion[] a = new IngredientPortion[8];
			if(ing[0].isSelected()) {
				a[0] = new AvocadoPortion(num[0].getValue()/100.00);
			}
			if(ing[1].isSelected()) {
				a[1] = new CrabPortion(num[1].getValue()/100.00);
			}
			if(ing[2].isSelected()) {
				a[2] = new EelPortion(num[2].getValue()/100.00);
			}
			if(ing[3].isSelected()) {
				a[3] = new RicePortion(num[3].getValue()/100.00);
			}
			if(ing[4].isSelected()) {
				a[4] = new SalmonPortion(num[4].getValue()/100.00);
			}
			if(ing[5].isSelected()) {
				a[5] = new SeaweedPortion(num[5].getValue()/100.00);
			}
			if(ing[6].isSelected()) {
				a[6] = new ShrimpPortion(num[6].getValue()/100.00);
			}
			if(ing[7].isSelected()) {
				a[7] = new TunaPortion(num[7].getValue()/100.00);
			}
			
			int null_num = 0;
			ArrayList<IngredientPortion> list = new ArrayList<IngredientPortion>();
			for(IngredientPortion ing:a) {
				if(ing!=null) {
					null_num++;
					list.add(ing);
				}
			}
			
			IngredientPortion[] final_list = new IngredientPortion[null_num];
			for(int i = 0; i<null_num; i++) {
				final_list[i] = list.get(i);
			}
			
			//make the roll
			if(color[0].isSelected()&&e.getActionCommand()=="make the sushi") {
				makeRedPlateRequest(new Roll("Random Roll", final_list), place_to_place);  
			}
			if(color[1].isSelected()&&e.getActionCommand()=="make the sushi") {
				makeGreenPlateRequest(new Roll("Random Roll", final_list), place_to_place);
			}
			if(color[2].isSelected()&&e.getActionCommand()=="make the sushi"){
				makeBluePlateRequest(new Roll("Random Roll", final_list), place_to_place);                    
			}
			if(color[3].isSelected()&&e.getActionCommand()=="make the sushi"){
				makeGoldPlateRequest(new Roll("Random Roll", final_list), place_to_place, gold.getValue()/100.00);
				
			}
		
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		place_to_place = position.getValue();
	}
}
