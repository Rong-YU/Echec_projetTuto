import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Partie {
	private Echequier echequier;
	private boolean tourNoir;
	private boolean check;
	private boolean vsAI;
	private Ai ai;
	public Partie() {
		// TODO Auto-generated constructor stub
		echequier = new Echequier();
		tourNoir = false;
		check = false;
		vsAI = false;
		this.initialiser();
		this.jouer();
	}
	
	public void initialiser() {
		this.echequier.initialise();
	}
	
	public Echequier getEchequier() {
		return echequier;
	}
	public boolean getTourNoir() {
		return tourNoir;
	}
	
	public boolean saisieValide(String s) {
		int xPos, yPos, xDes, yDes;
		if(s.equals("m")) {
			return true;
		}
		if (s.length() == 4) {
			xPos = (int)s.charAt(0)-97;
			yPos = Math.abs((Character.getNumericValue(s.charAt(1)))-8);
			xDes = (int)s.charAt(2)-97;			yDes = Math.abs((Character.getNumericValue(s.charAt(3)))-8);
			if (xPos > 7 || xPos <0 || yPos > 7 || xPos <0 || xDes > 7 || xDes <0 || yDes > 7 || xDes <0) {
				System.out.println("coordonnee invalide!");
				return false;
				}
			
			else if(this.echequier.parcoursValide(xPos, yPos, xDes,yDes) && this.echequier.pieceNoir(xPos, yPos) == this.tourNoir) {
				Echequier newEchequier = new Echequier(this.echequier);
				newEchequier.deplacerPiece(xPos, yPos, xDes, yDes);
				if(this.check) {
					if(!newEchequier.check(!tourNoir)) {
						this.echequier.deplacerPiece(xPos,yPos,xDes,yDes);
						this.check = false;
						return true;
					}
					else {
						this.echequier.afficher();
						System.out.println("ECHEC! le roi est encore en danger! resaisir!!");
						return false;
					}
				}
				else {
					this.echequier.deplacerPiece(xPos,yPos,xDes,yDes);
					return true;
				}
			}
		}
		System.out.println("saisir le mouvement(ex: \"a2a4\") ou \"m\" pour le menu");
		return false;
	}
	public int menu() {
		Scanner input = new Scanner(System.in);
		int n = -1;
		while (n <0 || n >6) {
			
			System.out.println("saisir le numero d'action");
			System.out.println("[0] reprendre la partie en cours");
			System.out.println("[1] commencer une nouvelle partie");
			System.out.println("[2] charger une partie");
			System.out.println("[3] sauvegarder la partie en cours");
			System.out.println("[4] terminer le jeu");
			System.out.println("[5] AI");
			System.out.println("[6] demos");
			n=input.nextInt();
		}
		return n;
		
	}
	public void jouer() {
		while (true) {
			System.out.flush();
			this.echequier.afficher();
				if(this.tourNoir) {
					if(check) {
						System.out.println("ECHEC! le roi noir est en danger!");
					}
					System.out.println("C'est le tour du joueur noir");
				}
				else {
					if(check) {
						System.out.println("ECHEC! le roi blanc est en danger!");
					}
					System.out.println("C'est le tour du joueur blanc");
					}
				if((!vsAI && tourNoir) || !tourNoir) {
					Scanner input = new Scanner(System.in);
					String s = "";			
					while (!saisieValide(s)) {
							s = input.next();
							s = s.toLowerCase();
						}
					
					if(s.equals("m")) {
						int n = menu();
						if (n == 0) {
							continue;
						}
						else if (n == 1) {
							this.initialiser();
							continue;
						}
						else if (n == 2) {
							this.charger("save");
							continue;
						}
						else if (n == 3) {
							sauver("save");
							continue;
						}
						else if (n == 4) {
							break;
						}
						else if (n == 5) {
							vsAI = true;
							ai = new Ai(this.echequier, 1);
							continue;
						}
						else if(n==6) {
							demo();
							continue;
						}
					}
				}
				else{
					ai.echequierUpdate(this.echequier);
					this.echequier = ai.bestMove();
				}
				if(this.echequier.roiMort(tourNoir)) {
					if(tourNoir) {
						System.out.println("Le joueur noir gagne");
					}
					else {
						System.out.println("Le joueur blanc gagne");
					}
					break;
				}
				if(this.echequier.checkMate(tourNoir)) {
					if(tourNoir) {
						System.out.println("ECHEC ET MAT! Le joueur noir gagne");
					}
					else {
						System.out.println("ECHEC ET MAT! Le joueur blanc gagne");
					}
					break;
				}
				check = this.echequier.check(tourNoir);
				this.echequier.promotion(tourNoir);
	
			tourNoir = !tourNoir;
		}
	}
	
	public void charger(String nomFichier) {
		try {
		FileReader fr = new FileReader(new File(nomFichier));
	      BufferedReader br = new BufferedReader(fr);
	      String line = br.readLine();
	      this.tourNoir = Boolean.valueOf(line);
	      line = br.readLine();
	      this.check = Boolean.valueOf(line);
	      line = br.readLine();
	      String piece;
	      while(line != null){
	        StringTokenizer st = new StringTokenizer(line, "\t");
	        int xPos = Integer.parseInt(st.nextToken());
	        int yPos = Integer.parseInt(st.nextToken());
	        piece = st.nextToken();
	        switch(piece) {
	        case "null": this.echequier.setPiece(yPos, xPos, null);break;
	        case "PionB": this.echequier.setPiece(yPos, xPos, new Pion(false));break;
	        case "PionN": this.echequier.setPiece(yPos, xPos, new Pion(true));break;
	        case "TourB": this.echequier.setPiece(yPos, xPos, new Tour(false));break;
	        case "TourN": this.echequier.setPiece(yPos, xPos, new Tour(true));break;
	        case "DameB": this.echequier.setPiece(yPos, xPos, new Dame(false));break;
	        case "DameN": this.echequier.setPiece(yPos, xPos, new Dame(true));break;
	        case "CavalierB": this.echequier.setPiece(yPos, xPos, new Cavalier(false));break;
	        case "CavalierN": this.echequier.setPiece(yPos, xPos, new Cavalier(true));break;
	        case "FouB": this.echequier.setPiece(yPos, xPos, new Fou(false));break;
	        case "FouN": this.echequier.setPiece(yPos, xPos, new Fou(true));break;
	        case "RoiB": this.echequier.setPiece(yPos, xPos, new Roi(false));break;
	        case "RoiN": this.echequier.setPiece(yPos, xPos, new Roi(true));break;
	        }
	        line = br.readLine();
	      }
	      br.close();
	      fr.close();
	      }
	      catch(IOException e){
	      System.out.println(e);
	    }
}
	public void sauver(String nomFichier) {
		  try{
		      BufferedWriter bw = new BufferedWriter(new FileWriter(new File(nomFichier)));
		      bw.write(String.valueOf(this.tourNoir)+ "\n");
		      bw.write(String.valueOf(this.check)+ "\n");
		      for(int i = 0; i<8;i++){
		    	  for(int j = 0; j<8; j++) {
		    		  bw.write(String.valueOf(i)+"\t"+String.valueOf(j)+"\t"+echequier.getEchequier()[i][j].toString()+"\n");
		      
		    	  }
		      }
		      bw.close();
		    }catch(IOException e){
		      System.out.println(e);
		    }
		  }
	
	public void demo() {
		Scanner inputkb = new Scanner(System.in);
		System.out.println("liste de demos:");
		System.out.println("[1]Promotion");
		System.out.println("[2]Echec");
		System.out.println("[3]EchecEtMat");
		int n = -1;
		while(n<1 || n >3) n = inputkb.nextInt();
		switch(n) {
		case 1: this.charger("demo_promotion");
		case 2: this.charger("demo_echec");
		case 3: this.charger("demo_checkmate");
		}
	}
	
	public static void main(String[] args) {
		new Partie();
	}
}


