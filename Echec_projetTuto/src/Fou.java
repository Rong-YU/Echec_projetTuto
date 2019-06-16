
public class Fou extends Piece {
	private String nom = "Fou";
	public Fou(boolean estNoir) {
		super(estNoir);
	}
	
	public Fou(Fou p) {
		super(p);
	}
	
	public boolean mouvementValide(int xPos, int yPos, int xDes, int yDes) {
		
		return (Math.abs(xPos - xDes) ==  Math.abs(yPos - yDes));
	}
	public String getNom() {
		return this.nom;
	}
	public String toString() {
		/*
		if(this.estNoir()) {return "\u265D";}
		else{return "\u2657";}
		*/
		if(this.estNoir()) {return "FN";}
		else{return "FB";}
	}
	
}
