
public class Pion extends Piece {
	private String nom = "Pion";
	public Pion(boolean estNoir) {
		super(estNoir);
		// TODO Auto-generated constructor stub
	}
	
	public Pion(Pion p) {
		super(p);
	}

	@Override
	public boolean mouvementValide(int xPos, int yPos, int xDes, int yDes) {
		// TODO Auto-generated method stub
		if (this.estNoir()) {
			if(yPos == 1) {
				return (yDes - yPos == 1 || yDes - yPos == 2);
			}
			else {
				return (yDes - yPos == 1) && (Math.abs(xDes - xPos) <=1);
			}
		
		}
		else {
			if(yPos == 6) {
				return (((yPos - yDes) == 1 )|| ((yPos - yDes) == 2));
			}
			else {
				return (yPos - yDes == 1) && (Math.abs(xDes - xPos) <=1);
			
			}
		}
	}
	public String getNom() {
		return this.nom;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		/*
		if(this.estNoir()) {return "\u265F ";}
		else{return "\u2659";}
		*/
		if(this.estNoir()) {return "PN";}
		else{return "PB";}
	}


}
