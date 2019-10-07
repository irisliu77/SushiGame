package sushigame.model;

import java.util.ArrayList;
import java.util.List;

import comp401.sushi.Plate;
import comp401.sushi.Sushi;

class BeltImpl implements ChefsBelt {

	private TimedPlate[] belt;
	private int rotation_count;
	private Customer[] customers;
	private List<BeltObserver> belt_observers;
	
	public BeltImpl(int size) {
		if (size < 1) {
			throw new IllegalArgumentException("Belt size must be greater than zero.");
		}

		belt = new TimedPlate[size];
		customers = new Customer[size];
		rotation_count = 0;
		belt_observers = new ArrayList<BeltObserver>();
	}

	@Override
	public int getRotationCount() {
		return rotation_count;
	}

	@Override
	public int getSize() {
		return belt.length;
	}
	
	@Override
	public Customer getCustomerAtPosition(int position) {
		return customers[normalizePosition(position)];
	}

	@Override
	public int setPlateNearestToPosition(Plate plate, int position) throws BeltFullException {
		for (int i=0; i<getSize(); i++) {
			try {
				setPlateAtPosition(plate, position);
				return normalizePosition(position);
			} catch (BeltPlateException e) {
				position += 1;
			}
		}
		throw new BeltFullException(this);
	}


	@Override
	public void rotate() {
		TimedPlate last_plate = belt[getSize()-1];
		for (int i=getSize()-1; i>0; i--) {
			belt[i] = belt[i-1];
		}
		belt[0] = last_plate;
		rotation_count++;

		notifyBeltObservers(new RotateEvent());
		
		for (int i=0; i<getSize(); i++) {
			if (plateAtPositionIsSpoiled(i)) {
				Plate spoiled_plate = removePlateAtPosition(i);
				notifyBeltObservers(new PlateSpoiledEvent(spoiled_plate, i));
			}
		}
		
		for (int i=0; i<getSize(); i++) {
			if (customers[i] != null) {
				Plate plate = getPlateAtPosition(i);
				if (plate != null) {
					if (customers[i].consumesPlate(plate)) {
						removePlateAtPosition(i);
						notifyBeltObservers(new PlateConsumedEvent(plate, i));			
					}
				}
			}
		}

	}

	@Override
	public int getAgeOfPlateAtPosition(int position) {
		position = normalizePosition(position);
		if (belt[position] == null) {
			return -1;
		} else {
			return getRotationCount() - belt[position].getInceptDate();
		}
	}

	@Override
	public int findPlate(Plate plate) {
		if (plate == null) {
			return -1;
		}
		
		for (int i=0; i<getSize(); i++) {
			if (getPlateAtPosition(i) == plate) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void registerBeltObserver(BeltObserver o) {
		belt_observers.add(o);
	}
	
	@Override
	public void unregisterBeltObserver(BeltObserver o) {
		belt_observers.remove(o);
	}
	
	private void notifyBeltObservers(BeltEvent event) {
		for (BeltObserver o : belt_observers) {
			o.handleBeltEvent(event);
		}
	}

	@Override
	public Plate getPlateAtPosition(int position) {
		position = normalizePosition(position);
		if (belt[position] != null) {
			return belt[position].getOriginal();
		} else {
			return null;
		}
	}

	private Plate removePlateAtPosition(int position) {
		Plate plate = getPlateAtPosition(position);
		clearPlateAtPosition(position);
		return plate;
	}

	void setCustomerAtPosition(Customer c, int position) {
		customers[normalizePosition(position)] = c;
	}
	
	private void setPlateAtPosition(Plate plate, int position) throws BeltPlateException {
		position = normalizePosition(position);

		if (plate == null) {
			throw new IllegalArgumentException("Plate is null");
		}

		if (belt[position] != null) {
			throw new BeltPlateException(position, plate, this);
		}
		belt[position] = new TimedPlateImpl(plate, getRotationCount());
		notifyBeltObservers(new PlatePlacedEvent(plate, position));
	}

	private void clearPlateAtPosition(int position) {
		belt[normalizePosition(position)] = null;
	}
	
	private boolean plateAtPositionIsSpoiled(int pos) {
		pos = normalizePosition(pos);
		TimedPlate plate = belt[pos];
		
		if (plate == null) {
			return false;
		}
		
		Sushi sushi = plate.getContents();
		if (sushi == null) {
			return false;
		}
		
		int age = getAgeOfPlateAtPosition(pos);
		
		if (sushi.getIsVegetarian()) {
			return (age >= 3 * getSize());
		}
		
		if (!sushi.getHasShellfish()) {
			return (age >= 2 * getSize());
		}
		
		return (age >= getSize());		
	}
	
	private int normalizePosition(int position) {
		int normalized_position = position%getSize();

		if (position < 0) {
			normalized_position += getSize();
		}

		return normalized_position;
	}




}
