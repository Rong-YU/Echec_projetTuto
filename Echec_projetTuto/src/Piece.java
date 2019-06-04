
public abstract class Piece {
	private boolean estNoir;
	
	public Piece(boolean estNoir) {
		this.estNoir = estNoir;
	}
	
	public Piece(Piece p) {
		this.estNoir = p.estNoir();
	}

	public boolean estNoir() {
		return this.estNoir;
	}
	
	public abstract boolean mouvementValide(int xPos, int yPos, int xDes, int yDes);
	
	public abstract String toString();

	public abstract String getNom();
}
