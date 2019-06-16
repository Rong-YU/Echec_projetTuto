
public class Dame extends Piece {
	private String nom = "Dame";
	public Dame(boolean estNoir) {
		super(estNoir);
		// TODO Auto-generated constructor stub
	}
	
	public Dame(Dame p) {
		super(p);
	}
	
	@Override
	public boolean mouvementValide(int xPos, int yPos, int xDes, int yDes) {
		// TODO Auto-generated method stub
		return (!((xPos != xDes) && (yPos != yDes))) || ((Math.abs(xPos - xDes) ==  Math.abs(yPos - yDes)));
	}
	public String getNom() {
		return this.nom;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		if(this.estNoir()) {return "\u265B ";}
		else{return "\u2655 ";}
		
//		if(this.estNoir()) {return "DN";}
//		else{return "DB";}
	}

}
