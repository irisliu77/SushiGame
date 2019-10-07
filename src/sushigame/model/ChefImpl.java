package sushigame.model;

import java.util.ArrayList;
import java.util.List;

import comp401.sushi.Plate;
import comp401.sushi.RedPlate;
import comp401.sushi.Sushi;
import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri.NigiriType;
import comp401.sushi.Plate.Color;
import comp401.sushi.Sashimi.SashimiType;

public class ChefImpl implements Chef, BeltObserver {

	private double balance;
	private List<HistoricalPlate> plate_history;
	private String name;
	private ChefsBelt belt;
	private boolean already_placed_this_rotation;
	private double food;
	private double spoiled_food;
	
	public ChefImpl(String name, double starting_balance, ChefsBelt belt) {
		this.name = name;
		this.balance = starting_balance;
		food = 0.0;
		spoiled_food = 0.0;
		this.belt = belt;
		belt.registerBeltObserver(this);
		already_placed_this_rotation = false;
		plate_history = new ArrayList<HistoricalPlate>();
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String n) {
		this.name = n;
	}

	@Override
	public HistoricalPlate[] getPlateHistory(int history_length) {
		if (history_length < 1 || (plate_history.size() == 0)) {
			return new HistoricalPlate[0];
		}

		if (history_length > plate_history.size()) {
			history_length = plate_history.size();
		}
		return plate_history.subList(plate_history.size()-history_length, plate_history.size()-1).toArray(new HistoricalPlate[history_length]);
	}

	@Override
	public HistoricalPlate[] getPlateHistory() {
		return getPlateHistory(plate_history.size());
	}

	@Override
	public double getBalance() {
		return balance;
	}
	
	//add method for getting the total amount of food
	@Override
	public double getFood() {
		return food;
	}

	//add method for getting the total amount of spoiled food
	@Override
	public double getSpoiledFood() {
		return spoiled_food;
	}

	@Override
	public void makeAndPlacePlate(Plate plate, int position) 
			throws InsufficientBalanceException, BeltFullException, AlreadyPlacedThisRotationException {

		if (already_placed_this_rotation) {
			throw new AlreadyPlacedThisRotationException();
		}
		
		if (plate.getContents().getCost() > balance) {
			throw new InsufficientBalanceException();
		}
		belt.setPlateNearestToPosition(plate, position);
		balance = balance - plate.getContents().getCost();
	
		already_placed_this_rotation = true;
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.PLATE_CONSUMED) {
			Plate plate = ((PlateEvent) e).getPlate();
			if (plate.getChef() == this) {
				balance += plate.getPrice();
				
				// if a plate is consumed, then the total amount of food should increase
				IngredientPortion[] a = plate.getContents().getIngredients().clone();
				for(IngredientPortion ing:a) {
					food = food + ing.getAmount();
				}
				
				Customer consumer = belt.getCustomerAtPosition(((PlateEvent) e).getPosition());
				plate_history.add(new HistoricalPlateImpl(plate, consumer));
			}
		} else if (e.getType() == BeltEvent.EventType.PLATE_SPOILED) {
			Plate plate = ((PlateEvent) e).getPlate();
			// if a plate is spoiled, then the total amount of spoiled food should increase
			if (plate.getChef() == this) {
				IngredientPortion[] a = plate.getContents().getIngredients().clone();
				for(IngredientPortion ing:a) {
					spoiled_food = spoiled_food + ing.getAmount();
				}
			}
			
			plate_history.add(new HistoricalPlateImpl(plate, null));
		} else if (e.getType() == BeltEvent.EventType.ROTATE) {
			already_placed_this_rotation = false;
		}
	}
	
	@Override
	public boolean alreadyPlacedThisRotation() {
		return already_placed_this_rotation;
	}


}
