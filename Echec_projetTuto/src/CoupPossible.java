import java.util.*;

public class CoupPossible {
	private Echequier source;
	private ArrayList<Coup> listCoup;
	boolean estNoir;
	public CoupPossible(Echequier echequier, boolean estNoir) {
		this.source = echequier;
		this.estNoir = estNoir;
		this.listCoup = new ArrayList<Coup>();
		// TODO Auto-generated constructor stub
	}
	
	public void CalculeCoupPossible() {
		for(int yPos=0; yPos<8;yPos++) {
			for(int xPos=0;xPos<8;xPos++) {
				for(int yDes=0;yDes<8;yDes++) {
					for(int xDes=0;xDes<8;xDes++) {
						if(source.parcoursValide(xPos,yPos,xDes,yDes)&& source.pieceNoir(xPos, yPos) == estNoir) {
							Echequier newEchequier = new Echequier(source);
							String p = newEchequier.deplacerPiece(xPos, yPos, xDes, yDes);
							Coup c = new Coup(newEchequier,p,estNoir);
							this.listCoup.add(c);
						}
					}
				}
			}
		}
	}
	
	public Echequier bestMove() {
		int pointMax=0;
		for(int i = 0; i<listCoup.size();i++) {
			if(listCoup.get(i).getPoint() > listCoup.get(pointMax).getPoint()) {
				pointMax = i;
			}
		}
		return listCoup.get(pointMax).getEchequier();
	}
	
	public void afficherCoupPossible() {
		for(int i = 0; i<listCoup.size();i++) {
			listCoup.get(i).afficher();;
		}
	}
	
	public int nombreCoup() {
		return this.listCoup.size();
	}
	
	public static void main(String[] args) {
		/*
		Echequier echequier = new Echequier();
		echequier.initialise();
		CoupPossible c = new CoupPossible(echequier,false);
		c.CalculeCoupPossible();
		c.afficherCoupPossible();
		System.out.println(c.nombreCoup());
		*/
	}
}
