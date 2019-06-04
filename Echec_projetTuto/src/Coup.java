
public class Coup {
	private Echequier echequier;
	private int point;
	private String pieceKilled;
	//int xPos, yPos, xDes, yDes;
	boolean coupNoir;
	public Coup(Echequier e, String pieceKilled, boolean coupNoir) {
		this.echequier = e;
		this.pieceKilled = pieceKilled;
		this.coupNoir=coupNoir;
		this.evaluerCoup();
		// TODO Auto-generated constructor stub
	}
	
	public void evaluerCoup() {
		
		switch(pieceKilled) {
			case "null": point = 0;break;
	        case "Pion": point = 10;break;
	        case "Tour": point = 50;break;
	        case "Dame": point = 90;break;
	        case "Cavalier": point = 30;break;
	        case "Fou": point = 30;break;
	        case "Roi": point = 999;break;
		}
		
		if(!coupNoir) {
			point = point*-1;
		}
	}
	public void afficher() {
		this.echequier.afficher();
		System.out.println(point);
	}
	public int getPoint() {
		return this.point;
	}
	public Echequier getEchequier() {
		return this.echequier;
	}
}
