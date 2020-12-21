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

public class RookTest {
	private int x, y;
   	private String colour, enemy;
   	private String [][] boardArray;
   	private boolean isValid = false;
  
   	public RookTest(int xx,int yy, String [][] array, String col, String enm) {
   		x = xx;
       	y = yy;
       	colour = col;
       	enemy = enm;
       	boardArray = array;
   	}
   
    public void move(int dx, int dy){
    	int count = 0;
    	if(boardArray[dy][dx].contains(colour)) {
    		isValid = false;
        	System.out.println("Inavlid move");
    	}
    	
     	else if(x == dx && y != dy) {
     		int a = (y > dy ? -1 : 1);
      		for(int i = y + a; i < dy; i += a) {
       			if(boardArray[i][x].contains(colour) || (boardArray[i][x].contains(enemy))) {
			        isValid = false;
			        System.out.println("Inavlid move");
			        break;
       			}
       			else {
       				count += 1;
       			}
      		}
      		if(count == Math.abs(y - dy)) {
      			isValid = true;
	        	x = dx;
    	    	y = dy;
      		}
		}
     
    	else if(x != dx && y == dy) {
    		int a = (x > dx ? -1 : 1);
    		for(int i = x + a; i < dx; i += a) {
       			if(boardArray[y][i].contains(colour) || boardArray[y][i].contains(enemy)) {
        			isValid = false;
        			System.out.println("Inavlid move");
        			break;
       			}
       			else {
	       			count += 1;
    	   		}
	      	}
    	  	if(count == Math.abs(x - dx)) {
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