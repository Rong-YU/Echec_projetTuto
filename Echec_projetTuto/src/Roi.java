
public class Roi extends Piece {
	private String nom = "Roi";
	public Roi(boolean estNoir) {
		super(estNoir);
		// TODO Auto-generated constructor stub
	}
	
	public Roi(Roi p) {
		super(p);
	}

	@Override
	public boolean mouvementValide(int xPos, int yPos, int xDes, int yDes) {
		// TODO Auto-generated method stub
		return Math.abs(xDes - xPos) <= 1 && Math.abs(yDes - yPos) <= 1;
	}
	public String getNom() {
		return this.nom;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		if(this.estNoir()) {return "\u265A";}
		else{return "\u2654";}
		/*
		if(this.estNoir()) {return "RN";}
		else{return "RB";}
		*/
	}
}
