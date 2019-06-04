
public class Tour extends Piece {
	private String nom = "Tour";
	public Tour(boolean estNoir) {
		super(estNoir);
		// TODO Auto-generated constructor stub
	}
	
	public Tour(Tour p) {
		super(p);
	}

	public boolean mouvementValide(int xPos, int yPos, int xDes, int yDes) {
		// TODO Auto-generated method stub
		return !((xPos != xDes) && (yPos != yDes)); //XOR
	}
	public String getNom() {
		return this.nom;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		/*
		if(this.estNoir()) {return "\u265C";}
		else{return "\u2656";}
		*/
		if(this.estNoir()) {return "TN";}
		else{return "TB";}
	}

}
