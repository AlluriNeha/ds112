package art;

/*
 * This class is used to test the Collage class methods.
 * 
 * @author Ana Paula Centeno
 */ 

public class Driver2 {
        
    public static void main (String[] args) {


    	Collage collage = new Collage ("Ariel.jpeg");
    	System.out.println("Test1: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Ariel.jpeg",200,5);
    	System.out.println("Test2: " +collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Ariel.jpeg");
    	collage.replaceTile ("Flo.jpeg",0,0);
    	System.out.println("Test3a: " +collage.getCollagePicture().get(0,151));
    	
    
    	collage = new Collage ("Ariel.jpeg");
    	collage.replaceTile ("Baloo.jpeg",3,3);
    	System.out.println("Test3a: "+collage.getCollagePicture().get(0,1));
    	
    	
    	collage = new Collage ("Ariel.jpeg",200,3);
    	collage.replaceTile ("Flo.jpeg",2,2);
    	System.out.println("Test3b: " + collage.getCollagePicture().get(0,1));
    	
    	
    	collage = new Collage ("Baloo.jpeg",200,3);
    	collage.replaceTile ("Flo.jpeg",1,1);
    	System.out.println("Test3b: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Lilo.jpeg",200,3);
    	collage.replaceTile ("Flo.jpeg",2,0);
    	System.out.println("Test3b: " + collage.getCollagePicture().get(0,1));
    	
    	
    	
    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("red", 0, 0);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));
    	

    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("blue", 0, 1);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));


    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("green", 1, 2);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));

    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("red", 1, 3);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("red", 1, 3);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("blue", 2, 0);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("green", 2, 2);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));
    	
    	
    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("red", 3, 0);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("blue", 3, 1);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Baloo.jpeg");
    	collage.colorizeTile("green", 3, 3);
    	System.out.println("Test5a: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Ploc.jpeg",150,3);
    	collage.colorizeTile("red", 0, 1);
    	System.out.println("Test5b: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Ploc.jpeg",150,3);
    	collage.colorizeTile("blue", 1, 1);
    	System.out.println("Test5b: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Ploc.jpeg",150,3);
    	collage.colorizeTile("green", 2, 1);
    	System.out.println("Test5b: " + collage.getCollagePicture().get(0,1));
    	
    	
    	collage = new Collage ("Lilo.jpeg");
    	collage.grayscaleTile(0,0);
    	System.out.println("Test6a: " + collage.getCollagePicture().get(0,1));
    	
    	collage = new Collage ("Lilo.jpeg");
    	collage.grayscaleTile(1,1);
    	System.out.println("Test6a: " + collage.getCollagePicture().get(0,1));

    	collage = new Collage ("Lilo.jpeg");
    	collage.grayscaleTile(3,3);
    	System.out.println("Test6a: " + collage.getCollagePicture().get(0,1));


    	collage = new Collage ("Lilo.jpeg",200,4);
    	collage.grayscaleTile(3,0);
    	System.out.println("Test6b: " + collage.getCollagePicture().get(0,1));


    	collage = new Collage ("Lilo.jpeg",200,3);
    	collage.grayscaleTile(1,1);
    	System.out.println("Test6b: " + collage.getCollagePicture().get(0,1));

    	collage = new Collage ("Lilo.jpeg",200,3);
    	collage.grayscaleTile(1,0);
    	System.out.println("Test6b: " + collage.getCollagePicture().get(0,1));

    }

}
