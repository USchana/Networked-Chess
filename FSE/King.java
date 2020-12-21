/**
 * @(#)King.java
 *
 *
 * @author 
 * @version 1.00 2020/10/13
 */

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class King {
   	private int x, y;
   	private String colour;		//player colour
   	private String [][] boardArray;
   	private boolean isValid = true;
  
    public King(int xx,int yy, String [][] array, String col) {
      	x = xx;
       	y = yy;
       	colour = col;
       	boardArray = array;
    }
   
    public void move(int dx, int dy){
       	if(Math.abs(dx - x) > 1 || Math.abs(dy - y) > 1 || boardArray[dy][dx].contains(colour)) {		//if king moves more than 1 space, or attacks teammate
        	isValid = false;
         	System.out.println("Inavlid move");
      	}
       	else {	//sets new values
        	x = dx;
         	y = dy;
         	isValid = true;
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