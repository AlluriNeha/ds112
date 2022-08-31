package prereqchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class PRGraph {
	
	
	private HashMap <String, ArrayList<String>> prMap;
	
	public PRGraph(String file) {
		prMap = new HashMap <String, ArrayList<String>> ();
		loadPreReqs(file);
	}
	
	public  HashMap <String, ArrayList<String>> getPRMap() {
		return prMap;
	}

	public void createCourse(String course) {
		if (prMap.get(course) == null)
			prMap.put (course, new ArrayList<String>());

	}

	public void addPreReq(String course, String preReq) {
		prMap.get(course).add(preReq);
	}

	public ArrayList<String> getPreReq(String course) {
		return prMap.get(course);
	}
	
	public Set<String> getCourseList() {
		return prMap.keySet();
	}

	
	public void loadPreReqs(String file) {
        StdIn.setFile(file);
		int count = StdIn.readInt();
		for (int i=0;i<count;i++) {
			String course=StdIn.readString();
			createCourse(course);
		}
		count = StdIn.readInt();
		for (int i=0;i<count;i++) {
			String course=StdIn.readString();
			String preReq=StdIn.readString();
			addPreReq(course,preReq);
		}
    }
	
	
	/*
	 * Return all prereqs both direct and indirect for a given course
	 */	
	public HashSet<String> getAllPreReqs(String course) {
		ArrayList<String> courses = new ArrayList<>();
		courses.add(course);
		return getAllPreReqs(courses);
	}

	/*
	 * Return all preReqs both direct and indirect for a given set of courses
	 */
	public HashSet<String> getAllPreReqs(ArrayList<String> courses) {
		LinkedList<String> queue = new LinkedList<> ();
		HashSet<String> dependencies = new HashSet<>();
		for (String course:courses) {			
			queue.add(course);
			dependencies.add(course);
		}
		while (queue.size() > 0) {
			String course = queue.removeFirst();
			ArrayList<String> preReqs = prMap.get(course);
			for (String prereq:preReqs)
				if (!dependencies.add (prereq) ) 
					queue.addLast(prereq);
		}
		return dependencies;
	}

	/*
	 * Check if the prcourse can be added as a prereq to course
	 * Get list of all dependencies for prcourse  and check if course is already in prcourse dependencies
	 *  
	 */
	public boolean isValidPreReq(String course, String prcourse) {
		HashSet<String> dependencies = getAllPreReqs(prcourse);
		return !dependencies.contains(course);
	}
	
	

	
	public ArrayList<String> getEligibilityList(ArrayList<String> courses) {		
		HashSet<String> completedSet = getAllPreReqs(courses);
		ArrayList<String> eligbilities = new ArrayList<>();
		for (String course:prMap.keySet())
			if (!completedSet.contains(course) && isPreReqMet (course,completedSet)) 
					eligbilities.add(course);
		return eligbilities;
		
	}
	
	
	public ArrayList<String> getNeedToTake(String course, ArrayList<String> completed) {
		HashSet<String> completedSet = getAllPreReqs(completed);
		HashSet<String> dependencies = getAllPreReqs(course);
		ArrayList<String> needToTake = new ArrayList<String>();
		for (String prereq:dependencies)
			if (!prereq.equals(course) && !completedSet.contains(prereq))
				needToTake.add(prereq);
		return needToTake;		
	}


	public boolean isPreReqMet (String course,HashSet<String> completedSet ) {
		ArrayList<String> prList = prMap.get(course);
		for (String pr:prList)
			if (!completedSet.contains(pr))
				return false;
		return true;
	}
	public ArrayList<ArrayList<String>> createSchedule(String target, ArrayList<String> completed) {
		HashSet<String> completedSet = getAllPreReqs(completed);
		HashSet<String> targetPreReqs = getAllPreReqs(target);
		targetPreReqs.remove(target);		
		targetPreReqs.removeAll(completedSet);
		ArrayList<ArrayList<String>> scheduleList = new ArrayList<ArrayList<String>>(); 
		while (targetPreReqs.size() > 0) {
			ArrayList<String> schedule = new ArrayList<>();
			for (String prereq:targetPreReqs) {
				if (!completedSet.contains(prereq) && isPreReqMet(prereq,completedSet))
					schedule.add(prereq);
			}			
			if (schedule.size() > 0) {
				scheduleList.add(schedule);
				targetPreReqs.removeAll(schedule);
				completedSet.addAll(schedule);
			}
			
		}
		return scheduleList;
	}
	
}
