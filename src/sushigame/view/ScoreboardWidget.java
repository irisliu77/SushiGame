package sushigame.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver, ActionListener {

	private SushiGameModel game_model;
	private JLabel display;
	private JRadioButton[] comparator;
	private ButtonGroup comp;
	private String order;
	
	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);
		
		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardHTML());
		
		JPanel choice = new JPanel();
		choice.setLayout(new GridLayout(3,1));
		add(choice, BorderLayout.NORTH);
		
		comparator = new JRadioButton[3];
		comp = new ButtonGroup();
		
		comparator[0] = new JRadioButton("balance order");
		comparator[0].setActionCommand("by balance");
		comparator[0].addActionListener(this);
		
		comparator[1] = new JRadioButton("food sold order");
		comparator[1].setActionCommand("by food sold");
		comparator[1].addActionListener(this);
		
		comparator[2] = new JRadioButton("spoiled food order");
		comparator[2].setActionCommand("by spoiled food");
		comparator[2].addActionListener(this);
		
		for(JRadioButton button:comparator) {
			comp.add(button);
			choice.add(button);
		}
	}

	private String makeScoreboardHTML() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";
		sb_html += order;
		return sb_html;
	}
	

	public void refresh() {
		display.setText(makeScoreboardHTML());		
	}
	
	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Chef[] opponent_chefs= game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length+1];
		chefs[0] = game_model.getPlayerChef();
		for (int i=1; i<chefs.length; i++) {
			chefs[i] = opponent_chefs[i-1];
		}
		
			if(comparator[0].isSelected()) {
				order = "";
				Arrays.sort(chefs, new HighToLowBalanceComparator());
				for (Chef c : chefs) {
					order = order + c.getName() + " ($" + Math.round(c.getBalance()*100.0)/100.0 + ") <br>";
				}
				refresh();
			}
			
			if(comparator[1].isSelected()) {
				order = "";
				Arrays.sort(chefs, new HighToLowFoodSoldComparator());
				for (Chef c : chefs) {
					order = order + c.getName() + " (" + Math.round(c.getFood()*100.0)/100.0 + " ounces) <br>";
				}
				refresh();
			}
			
			if(comparator[2].isSelected()) {
				order ="";
				Arrays.sort(chefs, new LowToHighFoodSpoiledComparator());
				for (Chef c : chefs) {
					order = order + c.getName() + " (" + Math.round(c.getSpoiledFood()*100.0)/100.0 + " ounces) <br>";
				}
				refresh();
			}

	}

}
