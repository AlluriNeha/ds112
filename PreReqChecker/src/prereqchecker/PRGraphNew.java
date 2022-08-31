package prereqchecker;

import java.util.ArrayList;
import java.util.HashMap;

public class PRGraphNew {
	
	
	private HashMap<String, Integer> cnToci;	
	private int numCourses;
	private  ArrayList<Integer>[] nodeArray;
	private  String[] ciTocn;
	
	
	public PRGraphNew(String file) {
		loadPreReqs(file);
	}
	
	public String[] getCourses() {
		return ciTocn;
	}

	public String getCourseName(int id) {
		return ciTocn[id];
	}

	public ArrayList<Integer> getPR(int id) {
		return ( ArrayList<Integer> )nodeArray[id];
	}

	
	public void loadPreReqs(String file) {
        StdIn.setFile(file);
		int count = StdIn.readInt();
		cnToci = new HashMap<String,Integer>();
		nodeArray = (ArrayList<Integer> [] )new Object[count];
		ciTocn = new String[count];
		for (int i=0;i<count;i++) {
			String course=StdIn.readString();
			cnToci.put(course,i);
			nodeArray[i] = new ArrayList<Integer>();
			ciTocn[i] = course;
		}
		
		
		count = StdIn.readInt();
		for (int i=0;i<count;i++) {
			String course=StdIn.readString();
			String preReq=StdIn.readString();
			int c = cnToci.get(course);
			int p = cnToci.get(preReq);
			((ArrayList<Integer>)nodeArray[c]).add(p);
		}
    }
	

	
}
