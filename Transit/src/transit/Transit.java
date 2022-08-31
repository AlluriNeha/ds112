package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
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
		TNode train = makeStops (trainStations);
		TNode bus = makeStops (busStops);
		TNode walk = makeStops (locations);
		connectPaths(train,bus);
		connectPaths(bus,walk);
		trainZero=train;
		return;
	}
	
	private static void connectPaths (TNode up, TNode down) {
		while (up != null && down != null) {
			if (up.getLocation() == down.getLocation()) 
				up.setDown(down);
			down = down.getNext();
			if (down != null && up.getNext() != null && up.getNext().getLocation() == down.getLocation()) 
				up=up.getNext();			
		}
	}

	private static TNode makeStops (int[] stops) {
		TNode root = new TNode(0);
		TNode curNode = root;
		for (int stop:stops) {
			TNode newNode=new TNode(stop);
			curNode.setNext(newNode);
			curNode = newNode;
		}
		return root;
	}
	
	private static TNode duplicateList(TNode orig) {
		TNode root = new TNode(orig.getLocation());
		TNode curNode = root;
		while ((orig=orig.getNext()) != null) {
			TNode newNode = new TNode (orig.getLocation());
			curNode.setNext(newNode);
			curNode = newNode;
		}
		return root;
	}

	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		TNode train = trainZero;  //the first node can't be removed as it will create dangling bus stops
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
		while (curBus.getNext() != null && curBus.getNext().getLocation() < busStop) {
			curBus = curBus.getNext();
		}
		if ((curBus.getLocation() == busStop) || (curBus.getNext() != null && curBus.getNext().getLocation() == busStop))
			return;
		TNode curWalk = curBus.getDown();
		while (curWalk != null && curWalk.getLocation() < busStop)
			curWalk = curWalk.getNext();
		if (curWalk == null || curWalk.getLocation() != busStop) 
			return;
		TNode newBus = new TNode(busStop,curBus.getNext(),curWalk);
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
		while (current != null && current.getLocation() <= destination) {
			bestList.add(current);
			if (current.getDown() != null && 
					(current.getNext() == null || current.getNext().getLocation() > destination)) 
				current = current.getDown();
			else 
				current = current.getNext();
		}
		return bestList;
	}
	
	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		TNode orig = trainZero;
		TNode root = duplicateList(orig);   
		//root = duplicate train
		TNode parentSet  = root; // need a pointer to return  						
		orig = orig.getDown();
		// parent = duplicate train, child = duplicate bus, connect parent, child
		// parent = duplicate bus, child = duplicate walk, connect parent, child

		while (orig != null ) {
			TNode childSet= duplicateList(orig);
			connectPaths (parentSet,childSet);
			parentSet = childSet;
			orig=orig.getDown();
		}
		return root;
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
		TNode scooter = makeStops (scooterStops);
		connectPaths(bus,scooter);
		connectPaths(scooter,walk);
	}

	/**
	 * Used by the driver to display the layered linked list.
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the getLocation(), then prepare for the arrow to the getNext()
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null)
					break;

				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation() + 1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++)
						StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null)
				break;
			StdOut.println();

			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation())
					downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr)
					StdOut.print("|");
				else
					StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen - 1; j++)
					StdOut.print(" ");

				if (horizPtr.getNext() == null)
					break;

				for (int i = horizPtr.getLocation() + 1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++)
							StdOut.print(" ");
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
				if (path.contains(horizPtr))
					StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++)
						StdOut.print(" ");
				}
				if (horizPtr.getNext() == null)
					break;

				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation() + 1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);

					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++)
						StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}

			if (vertPtr.getDown() == null)
				break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen - 1; j++)
					StdOut.print(" ");

				if (horizPtr.getNext() == null)
					break;

				for (int i = horizPtr.getLocation() + 1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++)
							StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
