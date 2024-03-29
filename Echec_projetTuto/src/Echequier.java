import java.util.Scanner;

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
							if(!newEchequier.check(estNoir)) {
								return false;
							}
						}
					}
				}
			}
		}
		afficher();
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
				int i = yPos+1;
				int j = xPos+1;
				while(i<yDes || j<xPos) {
					if(echequier[i][j].getPiece() != null) {
						return false;
					}
					i++;
					j++;
				}
			}
			else { //en haut a droite
				int i = yPos-1;
				int j = xPos+1;
				while(i>yDes || j<xPos) {
					if(echequier[i][j].getPiece() != null) {
						return false;
					}
					i--;
					j++;
				}
			}
		}
		
		else if (xDes < xPos) { // en bas a gauche
			if(yDes > yPos) {
				int i = yPos+1;
				int j = xPos-1;
				while(i<yDes || j>xPos) {
					if(echequier[i][j].getPiece() != null) {
						return false;
					}
					i++;
					j--;
				}
			}
			else { // en haut a gauche
				int i = yPos-1;
				int j = xPos-1;
				while(i>yDes || j>xPos) {
					if(echequier[i][j].getPiece() != null) {
						return false;
					}
					i--;
					j--;
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
	
	public void promotion(boolean tourNoir) {
		boolean pion = false;
		int x, y;
		x=0;
		y=0;
		for(int i = 0; i < 8; i++) {
			if(tourNoir) {
				if(echequier[7][i].getPiece() instanceof Pion) {
					pion = true;
					x=i;
					y=7;
					break;
				}
			}
			else {
				if(echequier[0][i].getPiece() instanceof Pion) {
					pion = true;
					x=i;
					y=0;
					break;
				}
			}
		}
		if(pion) {
			afficher();
			Scanner inputkb = new Scanner(System.in);
			System.out.println("choisir la piece souhaitee");
			System.out.println("[1]Dame");
			System.out.println("[2]Tour");
			System.out.println("[3]Fou");
			System.out.println("[4]Cavalier");
			int n = -1;
			while(n<1 || n >4) n = inputkb.nextInt();
			if(tourNoir) {
				switch(n) {
				case 1: this.echequier[y][x].setPiece(new Dame(tourNoir));break;
				case 2: this.echequier[y][x].setPiece(new Tour(tourNoir));break;
				case 3: this.echequier[y][x].setPiece(new Fou(tourNoir));break;
				case 4: this.echequier[y][x].setPiece(new Cavalier(tourNoir));break;
				}
			}
			else {
				switch(n) {
				case 1: this.echequier[y][x].setPiece(new Dame(tourNoir));break;
				case 2: this.echequier[y][x].setPiece(new Tour(tourNoir));break;
				case 3: this.echequier[y][x].setPiece(new Fou(tourNoir));break;
				case 4: this.echequier[y][x].setPiece(new Cavalier(tourNoir));break;
				}
			}
		}
	}
	
	
	public void afficher() {
		String s = "\n    ";
		
		s+="A  B  C  D  E  F  G  H\n";
		for(int i = 0; i<8; i++) {
			s += (8-i) + "  ";
			for(int j = 0; j<8; j++) {
				if (echequier[i][j].getPiece() != null){
					s += echequier[i][j].getPiece().toString()+"\u2009\u2009";
					}
//				else {
//					s+= "__ ";
//				}
				else if ((i+j)%2 == 1) {
			          s+="\u2591";
			          s+="\u2591";
			        } 
				else {
			          s+="\u2593";
			          s+="\u2593";
			   }
				
			}
			s += "  " + (8-i) + "\n";
		}
		s+="    A  B  C  D  E  F  G  H\n";
		
		System.out.println(s);
	
	}

}
