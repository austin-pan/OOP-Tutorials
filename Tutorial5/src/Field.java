
public class Field {
	private int value;
	private final boolean initial;
	
	public Field() {
		value = 0;
		initial = false;
	}
	
	public Field(int value, boolean initial) {
		this.value = value;
		this.initial = initial;
	}
	
	public boolean isInitial() {
		return initial;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		String tail = "";
		if(initial) {
			tail = "'";
		}
		
		return value + tail;
	}
}
