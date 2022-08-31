package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit2 {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit2() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit2(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		//create an array of 3 levels for trains, bus & locations and initialize zeroth node 
    	TNode list[] = new TNode[3];
        list[0] = new TNode(0);
        list[1] = new TNode(0);
        list[2] = new TNode(0);
        //initialize trainZero
        trainZero = list[0];
        //initialize each level
        makePath(list[0],trainStations);
        makePath(list[1],busStops);
        makePath(list[2],locations);
        //connect paths
        connectPaths(list);
	}
	
	
	//this takes an array of stops and creates a linked list and add to node
    private static void makePath (TNode node,int[] stops) {
    	for (int i=0; i < stops.length; i++) {
    		TNode newNode = new TNode(stops[i]);
    		node.setNext(newNode);
    		node = node.getNext();
    	} 		
    }
    
    
    //given an array of layered linked lists connect them
    private static void connectPaths (TNode[] node) {
    	//initialize zero nodes
    	int depth = node.length-1;
    	for (int i = 0; i < depth; i++) {
    		node[i].setDown( node[i+1]);
    	}
    	//go through last lecvel
    	while (node[depth].getNext() !=null) {
    		node[depth] = node[depth].getNext();
    		//check if parents next stop is same as current level, current stop
    		//and if parents next stop is current level, current stop then connect parents next stop to cuurent level, current stop
    		//once this connection is made check one level till level zero is reached
    		int curDepth = depth;
    		while (curDepth > 0 && node[curDepth-1].getNext() != null && node[curDepth-1].getNext().getLocation() == node[curDepth].getLocation()) {
    				node[curDepth-1].getNext().setDown(node[curDepth]);
    				node[curDepth-1] = node[curDepth-1].getNext();
    				curDepth--;
    		}
    	}
    }

	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
        TNode train = trainZero;
        while (train.getNext() != null && train.getNext().getLocation() < station) {
            train = train.getNext();
        }
        if (train.getNext() != null && train.getNext().getLocation() == station)
            train.setNext(train.getNext().getNext());
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
        TNode curBus = trainZero.getDown();
        
        // this while loop will stop at either last busStop or oneBusStop before new bus stop
        while (curBus.getNext() != null && curBus.getNext().getLocation() < busStop) {
            curBus = curBus.getNext();
        }

        // check if walking location is available
        TNode curWalk = curBus.getDown();
        while (curWalk != null && curWalk.getLocation()  < busStop)
        	curWalk = curWalk.getNext();
        if (curWalk == null || curWalk.getLocation() != busStop)
        	return;
        
        
        // add bus        
        TNode newBus = new TNode(busStop);
        newBus.setDown(curWalk);        
        newBus.setNext(curBus.getNext());
        curBus.setNext(newBus);   
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
        ArrayList<TNode> bestList = new ArrayList<TNode>();
        TNode current = trainZero;
        // TNode next = current.getNext();

        int curDepth = 0;
        int maxDepth = getDepth(trainZero);

        while (current != null && current.getLocation() <= destination) {
            bestList.add(current);
            while (curDepth < maxDepth && (current.getNext() == null || current.getNext().getLocation() > destination)) {
                current = current.getDown();
                bestList.add(current);
                curDepth++;
            }
            current = current.getNext();
        }

        return bestList;
	}

	
    private static int getDepth(TNode node) {
    	int depth=0;
    	while(node.getDown() != null) {
    		depth++;
    		node = node.getDown();
    	}
        return depth;
    }
    
    
	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		TNode curRoot = trainZero;
		int depth = getDepth(curRoot);
		//create an array of linked lists for each level
		TNode[] newStack = new TNode[depth + 1];
		for (int i = 0; i <= depth; i++) {
			//initialize each level from corresponding level of current path
			newStack[i] = duplicatePath(curRoot);
			curRoot = curRoot.getDown();
		}
		TNode newRoot = newStack[0];
		connectPaths(newStack);
		return newRoot;
	}

	//duplicate linked list
	private static TNode duplicatePath(TNode node) {
		TNode rootNode = new TNode(node.getLocation());
		TNode curNode = rootNode;
		node = node.getNext();
		while (node != null) {
			TNode newNode = new TNode(node.getLocation());
			curNode.setNext(newNode);
			curNode = newNode;
			node = node.getNext();
		}
		return rootNode;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
        TNode bus = trainZero.getDown();
        TNode walk = bus.getDown();
        TNode scooter = new TNode(0);
        makePath(scooter,scooterStops);
        bus.setDown(scooter);
        scooter.setDown(walk);
       	while (walk.getNext() !=null) {
    		walk = walk.getNext();
    		if (scooter.getNext() != null && scooter.getNext().getLocation() == walk.getLocation() ) {
    			scooter.getNext().setDown(walk);
    			scooter = scooter.getNext();
    		}
    		if (bus.getNext() != null && scooter != null &&  bus.getNext().getLocation() == scooter.getLocation() ) {
    			bus.getNext().setDown(scooter);
    			bus = bus.getNext();
    		}
    	}
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
