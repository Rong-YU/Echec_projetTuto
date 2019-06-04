import java.util.ArrayList;

public class Ai {
	private Echequier echequier;
	private int depth;
	private boolean estNoir = true;
	private CoupPossible[] listCoupPossible;
	public Ai(Echequier e, int dept) {
		this.echequier = e;
		this.estNoir = estNoir;
		this.depth = dept;
		this.listCoupPossible = new CoupPossible[dept];
		// TODO Auto-generated constructor stub
	}
	public Echequier bestMove() {
		CoupPossible cp = new CoupPossible(echequier, estNoir);
		cp.CalculeCoupPossible();
		return cp.bestMove();
		
	}
	
	public void echequierUpdate(Echequier e) {
		this.echequier = e;
	}
	
	
}
