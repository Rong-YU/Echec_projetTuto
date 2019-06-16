

public class Echequier {
	
	private Case echequier[][];
	public Echequier() {
		echequier = new Case[8][8];
		for(int i = 0; i<8;i++) {
			for(int j = 0; j<8;j++) {
				echequier[i][j] = new Case(j,i);
			}
		}
	}
	
	public Echequier(Echequier e) {
		this.echequier = new Case[8][8];
		for(int i = 0; i<8;i++) {
			for(int j = 0; j<8;j++) {
				this.echequier[i][j] = new Case(j,i,e.echequier[i][j].getPiece());
			}
		}
	}
	
	public void setPiece(int xPos, int yPos, Piece p) {
		this.echequier[yPos][xPos].setPiece(p);
	}
	
	public void initialise() {
		for(int ligne = 0; ligne<8;ligne++) {
			for(int colonne = 0; colonne<8;colonne++) {
				if(ligne == 0) {
					switch(colonne) {
					case 0:echequier[ligne][colonne].setPiece(new Tour(true));break;
					case 1:echequier[ligne][colonne].setPiece(new Cavalier(true));break;
					case 2:echequier[ligne][colonne].setPiece(new Fou(true));break;
					case 3:echequier[ligne][colonne].setPiece(new Dame(true));break;
					case 4:echequier[ligne][colonne].setPiece(new Roi(true));break;
					case 5:echequier[ligne][colonne].setPiece(new Fou(true));break;
					case 6:echequier[ligne][colonne].setPiece(new Cavalier(true));break;
					case 7:echequier[ligne][colonne].setPiece(new Tour(true));break;
					}
				}
				else if (ligne == 1) {
					echequier[ligne][colonne].setPiece(new Pion(true));
				}
				else if (ligne == 6) {
					echequier[ligne][colonne].setPiece(new Pion(false));
				}
				else if (ligne == 7) {
					switch(colonne) {
					case 0:echequier[ligne][colonne].setPiece(new Tour(false));break;
					case 1:echequier[ligne][colonne].setPiece(new Cavalier(false));break;
					case 2:echequier[ligne][colonne].setPiece(new Fou(false));break;
					case 3:echequier[ligne][colonne].setPiece(new Dame(false));break;
					case 4:echequier[ligne][colonne].setPiece(new Roi(false));break;
					case 5:echequier[ligne][colonne].setPiece(new Fou(false));break;
					case 6:echequier[ligne][colonne].setPiece(new Cavalier(false));break;
					case 7:echequier[ligne][colonne].setPiece(new Tour(false));break;
					}
					
				}
				else {
					echequier[ligne][colonne].setPiece(null);
				}
			}
		}
	}
	
	public Case[][] getEchequier() {
		return this.echequier;
	}
	
	public boolean pieceNoir(int xPos, int yPos) {
		return this.echequier[yPos][xPos].getPiece().estNoir();
	}
	
	public boolean caseLibre(int xPos, int yPos) {
		return this.echequier[yPos][xPos].getPiece() == null;
	}
	
	public Case positionRoi(boolean estNoir) {
		for(int i=0; i<8;i++) {
			for(int j=0;j<8;j++) {
				if(echequier[i][j].getPiece() != null && echequier[i][j].getPiece().getNom()=="Roi" && echequier[i][j].getPiece().estNoir() == estNoir) {
					return echequier[i][j];
				}
			}
		}
		return null;
	}
	
	public boolean roiMort(boolean estNoir) {
		Case roi = positionRoi(!estNoir);
		if(roi == null) {
			return true;
		}
		return false;
	}
	
	public boolean check(boolean estNoir) {
		Case roi = positionRoi(!estNoir);
		int xRoi = roi.getX();
		int yRoi = roi.getY();
		for(int i=0; i<8;i++) {
			for(int j=0;j<8;j++) {
				if(parcoursValide(j,i,xRoi,yRoi)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkMate(boolean estNoir) {
		for(int yPos=0; yPos<8;yPos++) {
			for(int xPos=0;xPos<8;xPos++) {
				for(int yDes=0;yDes<8;yDes++) {
					for(int xDes=0;xDes<8;xDes++) {
						if(parcoursValide(xPos,yPos,xDes,yDes)&& pieceNoir(xPos, yPos) == !estNoir) {
							Echequier newEchequier = new Echequier(this);
							newEchequier.deplacerPiece(xPos, yPos, xDes, yDes);
							newEchequier.afficher();
							if(!newEchequier.check(estNoir)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean parcoursValide(int xPos, int yPos, int xDes, int yDes) {
		if (echequier[yPos][xPos].getPiece() == null || !echequier[yPos][xPos].getPiece().mouvementValide(xPos, yPos, xDes, yDes)) {
			return false;
		}
		else if(echequier[yDes][xDes].getPiece() != null && echequier[yPos][xPos].getPiece().estNoir() == echequier[yDes][xDes].getPiece().estNoir()) {
			return false;
		}
		else if(echequier[yPos][xPos].getPiece().getNom() == "Pion" ) {
				if (xPos != xDes && (echequier[yDes][xDes].getPiece() == null || Math.abs(yDes - yPos) >1)) {
					return false;
				}
				else if(xPos == xDes && echequier[yDes][xDes].getPiece() != null) {
					return false;
				}
				
			
		}
		else if(Math.abs(xDes - xPos) <= 1 && Math.abs(yDes - yPos) <= 1 || this.echequier[yPos][xPos].getPiece().getNom() == "Cavalier") { // si la destination est la case a cote
			return true;
		}
		else if (xDes == xPos) {  //mouvement vers le bas
			if(yDes > yPos) {
				for(int i = yPos+1; i<yDes; i++) {
					if(echequier[i][xPos].getPiece() != null) { //1, 1, 1, 3
						return false;
					}
				}
			}
			else {
				for(int i = yPos-1; i>yDes; i--) {
					if(echequier[i][xPos].getPiece() != null) {
						return false;
					}
				}
			}
		}
		
		else if (yDes == yPos) { // mouvement vers a gauche
			if(xDes < xPos) {
				for(int i = xPos-1; i>xDes; i--) {
					if(echequier[yPos][i].getPiece() != null) {
						return false;
					}
				}
			}
			else {
				for(int i = xPos+1; i<xDes; i++) {
					if(echequier[yPos][i].getPiece() != null) {
						return false;
					}
				}
			}
		}
		
		else if(Math.abs(xPos - xDes) !=  Math.abs(yPos - yDes)) { // si le parcours n'est pas diagonale
			return false;
		}
		else if (xDes > xPos) {  //en bas a droite
			if(yDes > yPos) {
				for(int i = yPos+1; i<yDes; i++) {
					for(int j = xPos+1; j<xDes; j++) {
						if(echequier[i][j].getPiece() != null) {
							return false;
						}
					}
				}
			}
			else { //en haut a droite
				for(int i = yPos-1; i>yDes; i--) {
					for(int j = xPos+1; j<xDes; j++) {
						if(echequier[i][j].getPiece() != null) {
							return false;
						}
					}
				}
			}
		}
		
		else if (xDes < xPos) { // en bas a gauche
			if(yDes > yPos) {
				for(int i = yPos+1; i<yDes; i++) {
					for(int j = xPos-1; j>xDes; j--) {
						if(echequier[i][j].getPiece() != null) {
							return false;
						}
					}
				}
			}
			else { // en haut a gauche
				for(int i = yPos-1; i>yDes; i--) {
					for(int j = xPos-1; j>xDes; j--) {
						if(echequier[i][j].getPiece() != null) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public String deplacerPiece(int xPos, int yPos, int xDes, int yDes) {
		String deadPiece = "null";
		if(echequier[yDes][xDes].getPiece() != null) {
			deadPiece = echequier[yDes][xDes].getPiece().getNom();
		}
		this.echequier[yDes][xDes].setPiece(echequier[yPos][xPos].getPiece());
		this.echequier[yPos][xPos].setPiece(null);
		return deadPiece;
	}
//	private static final char light='\u2591';
//	  private static final char dark='\u2593';
	public void afficher() {
		String s = "\n     ";
		s+="[a] [b] [c] [d] [e] [f] [g] [h]\n";
		for(int i = 0; i<8; i++) {
			s += "[" + (8-i) + "] ";
			for(int j = 0; j<8; j++) {
				if (echequier[i][j].getPiece() != null){
					s += echequier[i][j].getPiece().toString()+" ";
					}
//				else {
//					s+= "__ ";
//				}
				else if ((i+j)%2 == 1) {
			          // print two light chars (light and dark are defined in the fields)
			          s+="\u2591";
			          s+="\u2591";
			        } else {
			          // else, print two dark chars
			          s+="\u2593";
			          s+="\u2593";
			        }
			}
			s += "[" + (8-i) + "]\n";
		}
		s+="     [a] [b] [c] [d] [e] [f] [g] [h]\n";
		
		System.out.println(s);
	
	}

}
