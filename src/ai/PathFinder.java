package ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

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
		
		for (int col = 0; col < game.maxWorldCol; col++) {
	        for (int row = 0; row < game.maxWorldRow; row++) {
	            node[col][row] = new Node(col, row);
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
	
	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		
		resetNodes();
	    startNode = node[startCol][startRow];
	    goalNode = node[goalCol][goalRow];

	    for (int col = 0; col < game.maxWorldCol; col++) {
	        for (int row = 0; row < game.maxWorldRow; row++) {
	            int tileNum = game.tileM.mapTileNum[col][row];
	            node[col][row].solid = game.tileM.tile[tileNum].collision;
	            // Debugging: Log the solid status of nodes
//	            System.out.println("Node at [" + col + "," + row + "]  with tileNum: " + tileNum +  " is marked solid: " + node[col][row].solid);
	            getCost(node[col][row]);
	        }
	    }

	    openList.add(startNode);
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
//	    resetNodes();
	    PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost));
	    HashSet<Node> closedList = new HashSet<>();
	    openList.add(startNode);

	    while (!openList.isEmpty()) {
	        Node currentNode = openList.poll();
	        closedList.add(currentNode);

	        if (currentNode == goalNode) {
	            goalReached = true;
	            trackThePath();
	            return true;
	        }

	        for (Node neighbor : getNeighbors(currentNode)) {
	            if (closedList.contains(neighbor)) continue;

	            int newGCost = currentNode.gCost + distanceBetween(currentNode, neighbor);
	            if (newGCost < neighbor.gCost || !openList.contains(neighbor)) {
	                neighbor.gCost = newGCost;
	                neighbor.hCost = distanceBetween(neighbor, goalNode);
	                neighbor.fCost = neighbor.gCost + neighbor.hCost;
	                neighbor.parent = currentNode;

	                if (!openList.contains(neighbor)) openList.add(neighbor);
	            }
	        }
	    }
	    return false;  // Goal not reached
	}
	
	// Helper method to get the distance between two nodes
	private int distanceBetween(Node nodeA, Node nodeB) {
	    int distX = Math.abs(nodeA.col - nodeB.col);
	    int distY = Math.abs(nodeA.row - nodeB.row);
	    return distX + distY;  // Manhattan distance
	}
	
	// Helper method to get neighbors of a node
	private List<Node> getNeighbors(Node node) {
	    List<Node> neighbors = new ArrayList<>();

	    int[][] directions = {
	        {0, -1},  // up
	        {0, 1},   // down
	        {-1, 0},  // left
	        {1, 0},    // right
	        {1, -1},  // upper right
	        {1, 1},   // lower right
	        {-1, -1}, // upper left
	        {-1, 1}   // lower left
	    };

	    for (int[] direction : directions) {
	        int neighborCol = node.col + direction[0];
	        int neighborRow = node.row + direction[1];

	        // Check if the neighbor is within the grid bounds
	        if (neighborCol >= 0 && neighborCol < game.maxWorldCol &&
                neighborRow >= 0 && neighborRow < game.maxWorldRow) {
	        	int tileNum = game.tileM.mapTileNum[neighborCol][neighborRow];
	        	boolean isSolid = this.node[neighborCol][neighborRow].solid;
//	        	System.out.println("Neighbor at [" + neighborCol + "," + neighborRow + "] with tileNum: " + tileNum + " is solid: " + isSolid);
	            if (!isSolid) {
	                neighbors.add(this.node[neighborCol][neighborRow]);
	            }
            }
	    }

	    return neighbors;
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
			// Manual Path Verification: Log each node's details in the path
//	        System.out.println("Tracking path node at [" + current.col + "," + current.row + "] with solid status: " + current.solid);
			pathList.add(0, current);
			current = current.parent;
		}
	}

}
