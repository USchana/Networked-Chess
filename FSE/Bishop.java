/**
 * @(#)Bishop.java
 *
 *
 * @author 
 * @version 1.00 2020/10/13
 */

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Bishop {
	private int x, y;
   	private String colour, enemy;	//player and enemy colour
   	private String [][] boardArray;
   	private boolean isValid = true;
  
    public Bishop(int xx,int yy, String [][] array, String col, String enm) {
    	x = xx;
       	y = yy;
       	colour = col;
       	enemy = enm;
       	boardArray = array;
    }
   
    public void move(int dx, int dy){
    	int count = 0;
     	if(x > dx && y > dy && x - dx == y - dy) {	//moving up and left
      		int ddy = dy - 1;	//increments y value
      		for(int i = dx; i <= x; i += 1) {	//checking if path is blocked
       			ddy += 1;
       			if(boardArray[ddy][i].contains(colour) || boardArray[ddy][i].contains(enemy)) {
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
     	else if(x < dx && y > dy && dx - x == y - dy) {		//moving up and right
      		int ddy = dy - 1;
      		for(int i = x + 1; i <= dx; i += 1) {
       			ddy += 1;
       			if(boardArray[ddy][i].contains(colour)) {
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
     	else if(x > dx && y < dy && x - dx == dy - y) {		//moving down and left
      		int ddy = y - 1;
      		for(int i = dx; i <= x; i += 1) {
       			ddy += 1;
       			if(boardArray[ddy][i].contains(colour)) {
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
     	else if(x < dx && y < dy && dx - x == dy - y) {		//moving down and right
      		int ddy = y - 1;
      		for(int i = x + 1; i <= dx; i += 1) {
       			ddy += 1;
       			if(boardArray[ddy][i].contains(colour)) {
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
	    else if(boardArray[dy][dx].contains(colour)) {		//if bishop is attacking teammate
     		isValid = false;
        	System.out.println("Inavlid move");
     	}
	    else {
	    	isValid = false;
	        System.out.println("Inavlid move");
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