/**
 * @(#)Knight.java
 *
 *
 * @author 
 * @version 1.00 2020/10/13
 */

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Knight {
   	private int x, y;
   	private String colour;	//player colour
   	private String [][] boardArray;
   	private boolean isValid = true;
  
    public Knight(int xx,int yy, String [][] array, String col) {
      	x = xx;
       	y = yy;
       	colour = col;
       	boardArray = array;
    }
   
    public void move(int dx, int dy){
      	if(Math.abs(dx - x) == 2 && Math.abs(dy - y) == 1) {
       		if(boardArray[dy][dx].contains(colour)) {	//if knight is attacking teammate
         		isValid = false;
         		System.out.println("Inavlid move");
       		}
       		else {	//sets new values
         		x = dx;
         		y = dy;
       		}
      	}
     	else if(Math.abs(dx - x) == 1 && Math.abs(dy - y) == 2) {
      		if(boardArray[dy][dx].contains(colour)) {
         		isValid = false;
         		System.out.println("Inavlid move");
       		}
       		else {	//sets new values
       	 		isValid = true;
         		x = dx;
         		y = dy;
       		}
     	}  
      	else {
	        isValid = false;
      		System.out.println("Invalid move");
      	}
	}
    
    public int getX(){
     	return x;
    }

    public int getY(){
     	return y;
    }
    
    public boolean getValid() {
    	return isValid;
    }
}