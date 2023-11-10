package ai;

import java.util.ArrayList;

import main.Game;
import main.GameObject;

public class PathFinder {
	
	Game game;
	Node[][] node;
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;
	
	public PathFinder(Game game) {
		this.game = game;
		instantiateNodes();
	}
	
	public void instantiateNodes() {
		node = new Node[game.maxWorldCol][game.maxWorldRow];
		
		int col = 0;
		int row = 0;
		
		while (col < game.maxWorldCol && row < game.maxWorldRow) {
			
			node[col][row] = new Node(col, row);
			
			col++;
			
			if (col == game.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void resetNodes() {
		int col = 0;
		int row = 0;
		
		while (col < game.maxWorldCol && row < game.maxWorldRow) {
			
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;
			
			col++;
			
			if (col == game.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow, GameObject gameObject) {
		
		resetNodes();
		
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while (col < game.maxWorldCol && row < game.maxWorldRow) {
			
			// SET SOLID NODES
			int tileNum = game.tileM.mapTileNum[col][row];
			if (game.tileM.tile[tileNum].collision == true) {
				node[col][row].solid = true;
			}
			
			// CHECK INTERACTIVE TILES
//			for (int i = 0; i < game.iTile[1].length; i++) {
//				if (game.iTile[game.currentMap])		(MORE CODE FROM PATHFINDING TUTORIAL @ 12:11)
//			}
			
			// SET COST
			getCost(node[col][row]);
			
			col++;
			
			if (col == game.maxWorldCol) {
				col = 0;
				row++;
			}
		}
	}
	
	public void getCost(Node node) {
		
		// G cost
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		// H cost
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		// F cost
		node.fCost = node.gCost + node.hCost;
	}
	
	public boolean search() {
		
		while (goalReached == false && step < 500) {
			
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.checked = true;
			openList.remove(currentNode);
			
			// open the up node
			if (row - 1 >= 0) {
				openNode(node[col][row-1]);
			}
			// open the left node
			if (col - 1 >= 0) {
				openNode(node[col-1][row]);
			}
			// open the down node
			if (row + 1 < game.maxWorldRow) {
				openNode(node[col][row+1]);
			}
			// open the right node
			if (col + 1 < game.maxWorldCol) {
				openNode(node[col+1][row]);
			}
//			// open the upper left node
//			if (row - 1 >= 0 && col - 1 >= 0) {
//				openNode(node[col-1][row-1]);
//			}
//			// open the upper right node
//			if (row - 1 >= 0 && col + 1 < game.maxWorldCol) {
//				openNode(node[col+1][row-1]);
//			}
//			// open the bottom left node
//			if (row + 1 < game.maxWorldRow && col - 1 >= 0) {
//				openNode(node[col-1][row+1]);
//			}
//			// open the bottom right node
//			if (row + 1 < game.maxWorldRow && col + 1 < game.maxWorldCol) {
//				openNode(node[col+1][row+1]);
//			}
			
			// Find best node
			int bestNodeIndex = 0;
			int bestNodefCost = 999;
			
			for (int i = 0; i < openList.size(); i ++) {
				
				// check if fCost is better
				if (openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;
				}
				// if F cost is equal, check the g cost
				else if (openList.get(i).fCost == bestNodefCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			// end loop if openList is empty
			if (openList.isEmpty()) {
				break;
			}
			
			// after the loop, openList[bestNodeIndex] is the next step (= currentNode)
			currentNode = openList.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;
		}
		
		return goalReached;
	}
	
	public void openNode(Node node) {
		
		if (node.open == false && node.checked == false && node.solid == false) {
			
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}
	
	public void trackThePath() {
		
		Node current = goalNode;
		
		while (current != startNode) {
			
			pathList.add(0, current);
			current = current.parent;
		}
	}

}
