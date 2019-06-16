
public class Cavalier extends Piece {
	private String nom = "Cavalier";
	public Cavalier(boolean estNoir) {
		super(estNoir);
		// TODO Auto-generated constructor stub
	}

	public Cavalier(Cavalier p) {
		super(p);
	}
	
	@Override
	public boolean mouvementValide(int xPos, int yPos, int xDes, int yDes) {
		// TODO Auto-generated method stub
		return (Math.abs(xPos - xDes) == 2 && Math.abs(yPos - yDes) == 1) || (Math.abs(xPos - xDes) == 1 && Math.abs(yPos - yDes) == 2) ;
	}
	public String getNom() {
		return this.nom;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		if(this.estNoir()) {return "\u265E ";}
		else{return "\u2658 ";}
		
//		if(this.estNoir()) {return "CN";}
//		else{return "CB";}
	}

}
