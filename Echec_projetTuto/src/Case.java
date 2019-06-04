
public class Case {
	private int x;
	private int y;
	private Piece p;
	
	public Case(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Case(int x, int y, Piece p) {
		this.x = x;
		this.y = y;
		this.p = p;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Piece getPiece() {
		return this.p;
	}
	
	public void setPiece(Piece p) {
		this.p = p;
	}
	
	public String toString() {
		
		if (this.p != null) {
			if(this.p.estNoir()) {
				return this.p.getNom()+"N";}
			else {
				return this.p.getNom()+"B";
			}
		}
		else {return "null";}
		
		
		//return this.p.getNom();
	}
}
