package ai;

public class Node {
	
	Node parent;
	public int col;
	public int row;
	int gCost, hCost, fCost;
	public boolean solid, open, checked;
	
	public Node(int col, int row) {
		this.col = col;
		this.row = row;
	}

}
