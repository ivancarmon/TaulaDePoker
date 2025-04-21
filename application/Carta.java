package application;

import enumerados.Palo;

public class Carta {
	
	
	private Palo palo;
	private int punt;
	private String id;
	
	public Carta(Palo palo, int punt) {
		
		this.punt = punt;
		this.palo = palo;
		this.Generarid(punt, palo);
		
	}
	
	private String Generarid(int punt, Palo p) {
		
		char aux = p.name().charAt(0);
		
		return Character.toString(aux) + String.valueOf(punt);
		
	}
	
	
	
	
	
	
	
	
	
	public Palo getPalo() {
		return palo;
	}
	public void setPalo(Palo palo) {
		this.palo = palo;
	}
	public int getPunt() {
		return punt;
	}
	public void setPunt(int punt) {
		this.punt = punt;
	}

	@Override
	public String toString() {
		return id;
	}
	
	
	
	
}
