package ar.com.instMessenger.enums;

public enum Estado implements Enumerable{
	ACTIVO(0), INACTIVO(1);
	
	private final int value;
	
	private Estado(int estado) {
		this.value = estado;
	}



	public String getName(){
		return this.name();
	}

	@Override
	public Object getValue() {
		return value;
	}
	public String toString() {
		return String.valueOf(value);
	}
}
