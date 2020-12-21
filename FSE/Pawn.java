/**
 * @(#)Pawn.java
 *
 *
 * @author 
 * @version 1.00 2020/10/13
 */

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Pawn {
  	private int x, y;
   	private String colour, direction, enemy;
   	private String [][] boardArray;
   	private boolean isValid = true;
  
    public Pawn(int xx,int yy, String [][] array, String col, String enm, String dir) {
      	x = xx;
       	y = yy;
       	colour = col;
       	enemy = enm;
       	direction = dir;
       	boardArray = array;
    }
   
    public void move(int dx, int dy){
       	if((boardArray[dy][dx].contains(colour)) || (direction.equals("up") && dy > y) || (direction.equals("down") && y > dy)) {	//if pawn attacks teammate or tries to move in opposite direction
       		isValid = false;
         	System.out.println("Inavlid move");
       	}
       	else if(Math.abs(dy - y) == 2){		//allows pawn to move two spaces from starting position
       		System.out.println(Math.abs(dy - y));
       		if((y == 1 && boardArray[2][dx] == "---") || (y == 6 && boardArray[5][dx] == "---")) {	//checks if path is blocked
       			isValid = true;
        		x = dx;
        		y = dy;
       		}
       		else {
       			isValid = false;
         		System.out.println("Inavlid move");
       		}
       	}
       	else if(Math.abs(dy - y) == 1 && boardArray[dy][dx] == "---") {		//moves 1 up
       		isValid = true;
        	x = dx;
        	y = dy;
       	}
       	else if((Math.abs(x - dx) == 1)) {		//attacks diagonally
       		if(boardArray[dy][dx].contains(enemy) && Math.abs(dy - y) == 1) {
       			isValid = true;
        		x = dx;
        		y = dy;
       		}
       		else {
       			isValid = false;
         		System.out.println("Inavlid move");
       		}
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