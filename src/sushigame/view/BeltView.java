 package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import comp401.sushi.IngredientPortion;
import comp401.sushi.Plate;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;

public class BeltView extends JPanel implements BeltObserver {

	private Belt belt;
	private JLabel[] belt_labels;
	private PlateViewWidget[] plateView;

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		//将beltview界面分成两列，左边用于显示基本信息，右边用于放置info按钮
		setLayout(new GridLayout(1, 2));
		
		//左边的panel
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(belt.getSize(), 1));
		add(left);
		//在每个grid里加入一个label，用于显示基本信息
		belt_labels = new JLabel[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			JLabel plabel = new JLabel("");
			plabel.setMinimumSize(new Dimension(300, 20));
			plabel.setPreferredSize(new Dimension(300, 20));
			plabel.setOpaque(true);
			plabel.setBackground(Color.GRAY);
			left.add(plabel);
			belt_labels[i] = plabel;
		}
		
		//右边的panel
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(belt.getSize(), 1));
		add(right);
		//在右panel的每个grid中加入一个plate view widget，即一个info button
		plateView= new PlateViewWidget[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			PlateViewWidget plateInfo = new PlateViewWidget();
			right.add(plateInfo);
			plateView[i] = plateInfo;
		}
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {	
		refresh();
	}

	private void refresh() {
		//更改左边每个label的信息
		for (int i=0; i<belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			JLabel plabel = belt_labels[i];

			if (p == null) {
				plabel.setText("");
				plabel.setBackground(Color.GRAY);
			} else {
				plabel.setText(p.toString());
				switch (p.getColor()) {
				case RED:
					plabel.setBackground(Color.RED); break;
				case GREEN:
					plabel.setBackground(Color.GREEN); break;
				case BLUE:
					plabel.setBackground(Color.BLUE); break;
				case GOLD:
					plabel.setBackground(Color.YELLOW); break;
				}
			}
		}
	
		//更改info button弹出框中的信息
		for (int i=0; i<belt.getSize(); i++) {
			//确定现在的plate 和 相对应需要修改的plateinfo
			Plate p = belt.getPlateAtPosition(i);
			PlateViewWidget plateInfo = plateView[i];

			//若位置为i时没有plate，则使info button无效
			if (p == null) {
				plateInfo.getInfo().setEnabled(false);
				plateInfo.setBackground(Color.GRAY);
				
			} else {//若在i处有plate，首先使info button变回有效按钮，然后更改四类信息
				plateInfo.getInfo().setEnabled(true);
				
				//关于是哪种sushi的信息更新
				String type = p.getContents().getName();
				if(type.contains("Roll")) {
					IngredientPortion[] ins = p.getContents().getIngredients().clone();
					for(IngredientPortion ing:ins) {
						type += " " + ing.getName();
						type += " " + Math.round(ing.getAmount() * 100.0) / 100.0;
					}
				}
				
				//将更新信息传送至info页面的label
				plateInfo.getColorL().setText(p.getColor().toString());
				plateInfo.getTypeL().setText(type);
				plateInfo.getChefL().setText(p.getChef().getName());
				plateInfo.getAgeL().setText("" + belt.getAgeOfPlateAtPosition(i));
			}
		}
	}
}
