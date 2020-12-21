/**
 * @(#)Rook.java
 *
 *
 * @author 
 * @version 1.00 2020/10/13
 */

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Rook {
	private int x, y;
   	private String colour, enemy;	//player and enemy colour
   	private String [][] boardArray;
   	private boolean isValid = false;
  
   	public Rook(int xx,int yy, String [][] array, String col, String enm) {
   		x = xx;
       	y = yy;
       	colour = col;
       	enemy = enm;
       	boardArray = array;
   	}
   
    public void move(int dx, int dy){
    	int count = 0;
     	if(x == dx && y > dy) {		//moving up
      		for(int i = dy; i < y; i += 1) {	//checks if path is blocked
       			if(boardArray[i][x].contains(colour) || (i == y - 1 && boardArray[i][x].contains(enemy))) {
			        isValid = false;
			        System.out.println("Inavlid move");
			        break;
       			}
       			else {
       				count += 1;
       			}
      		}
      		if(count == y - dy) {
      			isValid = true;
	        	x = dx;
    	    	y = dy;
      		}
		}
     
    	else if(x > dx && y == dy) {	//moving left
    		for(int i = dx; i < x; i += 1) {
       			if(boardArray[y][i].contains(colour) || boardArray[y][i].contains(enemy)) {
        			isValid = false;
        			System.out.println("Inavlid move");
        			break;
       			}
       			else {
	       			count += 1;
    	   		}
	      	}
    	  	if(count == x - dx) {
      			isValid = true;
        		x = dx;
        		y = dy;
      		}
		}
     
    	else if(x < dx && y == dy) {	//moving right
    		for(int i = x + 1; i < dx; i += 1) {
       			if(boardArray[y][i].contains(colour) || (i == x - 1 && boardArray[y][i].contains(enemy))) {
        			isValid = false;
        			System.out.println("Inavlid move");
        			break;
       			}
       			else {
       				count += 1;
       			}
      		}
      		if(count == dx - x) {
      			isValid = true;
        		x = dx;
        		y = dy;
      		}
		}
     
		else if(x == dx && y < dy) {	//moving down
    		for(int i = y + 1; i < dy; i += 1) {
	       		if(boardArray[i][x].contains(colour) || boardArray[i][x].contains(enemy)) {
    	    		isValid = false;
        			System.out.println("Inavlid move");
        			break;
       			}
       			else {
       				count += 1;
       			}
      		}	
      		if(count == dy - y) {
	      		isValid = true;
	        	x = dx;
	        	y = dy;
      		}
		}
    	else if(boardArray[dy][dx].contains(colour)) {	//if rook is attacking teammate
    		isValid = false;
        	System.out.println("Inavlid move");
    	}
    	else {
    		isValid = true;
    		x = dx;
        	y = dy;
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