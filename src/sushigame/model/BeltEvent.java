package sushigame.model;

abstract public class BeltEvent {
	public enum EventType {PLATE_PLACED, PLATE_CONSUMED, PLATE_SPOILED, ROTATE}

	private BeltEvent.EventType type;

	public BeltEvent(EventType type) {
		this.type = type;
	}
	
	public BeltEvent.EventType getType() {
		return type;
	}
}
