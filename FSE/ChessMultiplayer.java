//Chess Multiplayer game
//Upkar Chana

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.util.ArrayList;
import java.awt.MouseInfo;
import java.io.*;
import java.net.*;

public class ChessMultiplayer extends JFrame {
 
	public ChessMultiplayer() {
    	super("Chess");
       	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
       	GamePanel game = new GamePanel();
       	add(game);
       	pack();
       	setVisible(true);
	}

    public static void main(String[] arguments) {
    	GamePanel game = new GamePanel();
     	Chess frame = new Chess();  
     	game.connectToServer();		//connects to game server
		game.setUpPlayers();		//gives players their number
    }
}

class GamePanel extends JPanel implements ActionListener, MouseListener{
	Timer myTimer;
	private String mode = "play"; 
 	private int fw = 480, fh = 480;    	//frame width and height
 	private int click = 1;				//how many times the user clicks
 	private int sx, sy, dx, dy;			//coordinate of first click and coordinate of second click
 	private int playerID;				//id of player
 	private int otherPlayer;			//id of enemy
 	private String [][] boardArray = {	//array where all changes are made
	     {"rB1","kB1","bB1","qB ","KB ","bB2","kB2","rB2"},
	     {"pB1","pB2","pB3","pB4","pB5","pB6","pB7","pB8"},
	     {"---","---","---","---","---","---","---","---"},
	     {"---","---","---","---","---","---","---","---"},
	     {"---","---","---","---","---","---","---","---"},
	     {"---","---","---","---","---","---","---","---"},
	     {"pW1","pW2","pW3","pW4","pW5","pW6","pW7","pW8"},
	     {"rW1","kW1","bW1","qW ","KW ","bW2","kW2","rW2"}};;
	Pawn Bpawn1, Bpawn2, Bpawn3, Bpawn4, Bpawn5, Bpawn6, Bpawn7, Bpawn8, Wpawn1, Wpawn2, Wpawn3, Wpawn4, Wpawn5, Wpawn6, Wpawn7, Wpawn8;	//pawns
 	Knight Bknight1, Bknight2, Wknight1, Wknight2;		//knights
 	Rook Brook1, Brook2, Wrook1, Wrook2;				//rooks
 	Bishop Bbishop1, Bbishop2, Wbishop1, Wbishop2;		//bishops
 	Queen Bqueen, Wqueen;								//queens
 	King Bking, Wking;									//kings
 	Font fnt;
 	private ClientSideConnection csc;
 
  	public Image load(String n){
    	return new ImageIcon(n).getImage();
  	}
  
  	public GamePanel() {
	    myTimer = new Timer(20, this);
	    setPreferredSize(new Dimension(fw, fh));
	    Bpawn1 = new Pawn(0,1,boardArray,"B","W","down");
	    Bpawn2 = new Pawn(1,1,boardArray,"B","W","down");
	    Bpawn3 = new Pawn(2,1,boardArray,"B","W","down");
	    Bpawn4 = new Pawn(3,1,boardArray,"B","W","down");
	    Bpawn5 = new Pawn(4,1,boardArray,"B","W","down");
	    Bpawn6 = new Pawn(5,1,boardArray,"B","W","down");
	    Bpawn7 = new Pawn(6,1,boardArray,"B","W","down");
	    Bpawn8 = new Pawn(7,1,boardArray,"B","W","down");
	    Bknight1 = new Knight(1,0,boardArray,"B");
	    Bknight2 = new Knight(6,0,boardArray,"B");
	    Brook1 = new Rook(0,0,boardArray,"B","W");
	    Brook2 = new Rook(7,0,boardArray,"B","W");
	    Bbishop1 = new Bishop(2,0,boardArray,"B","W");
	    Bbishop2 = new Bishop(5,0,boardArray,"B","W");
	    Bqueen = new Queen(3,0,boardArray,"B","W");
	    Bking = new King(4,0,boardArray,"B");
	    Wpawn1 = new Pawn(0,6,boardArray,"W","B","up");
	    Wpawn2 = new Pawn(1,6,boardArray,"W","B","up");
	    Wpawn3 = new Pawn(2,6,boardArray,"W","B","up");
	    Wpawn4 = new Pawn(3,6,boardArray,"W","B","up");
	    Wpawn5 = new Pawn(4,6,boardArray,"W","B","up");
	    Wpawn6 = new Pawn(5,6,boardArray,"W","B","up");
	    Wpawn7 = new Pawn(6,6,boardArray,"W","B","up");
	    Wpawn8 = new Pawn(7,6,boardArray,"W","B","up");
	    Wknight1 = new Knight(1,7,boardArray,"W");
	    Wknight2 = new Knight(6,7,boardArray,"W");
	    Wrook1 = new Rook(0,7,boardArray,"W","B");
	    Wrook2 = new Rook(7,7,boardArray,"W","B");
	    Wbishop1 = new Bishop(2,7,boardArray,"W","B");
	    Wbishop2 = new Bishop(5,7,boardArray,"W","B");
	    Wqueen = new Queen(3,7,boardArray,"W","B");
	    Wking = new King(4,7,boardArray,"W");
	    fnt = new Font("Comic Sans", Font.BOLD, 50);
	    addMouseListener(this);
  	}
    
 // This triggers when the Panel is loaded. I can't get keyboard focus
 // until the panel is loaded.
  @Override
    public void addNotify() {
    	super.addNotify();
       	setFocusable(true);
        requestFocus();
        myTimer.start();
    }

  @Override
    public void paint(Graphics g){  
    	Point mouse = MouseInfo.getPointerInfo().getLocation();
     	Point offset = getLocationOnScreen();
     	int mx = mouse.x - offset.x;
     	int my = mouse.y - offset.y;
     	Image Board = load("Board.png");
     	Image whitePieces [] = new Image[6];		//player 1's pieces
   		Image blackPieces [] = new Image[6];		//player 2's pieces
     	for(int i = 1; i <= 6; i += 1) {			//loads images from arrays
      		whitePieces[i - 1] = load("piece" + i + ".png");
     	}
     	for(int i = 7; i <= 12; i += 1) {
       		blackPieces[i - 7] = load("piece" + i + ".png");
     	}
     	if(mode == "play") {	
      		g.drawImage(Board, 0, 0, null);
      		for(int i = 0; i < 8; i += 1) {		//loops through each index of boardArray, and draws images according to what the string is
       			for(int j = 0; j < 8; j += 1) {
        			if(boardArray[i][j].contains("KB")) {
         				g.drawImage(blackPieces[0],j * 60,i * 60, null);
        			}
        			else if(boardArray[i][j].contains("kB")) {
         				g.drawImage(blackPieces[1],j * 60,i * 60, null);
        			}
        			else if(boardArray[i][j].contains("pB")) {
         				g.drawImage(blackPieces[2],j * 60,i * 60, null);
        			}
        			else if(boardArray[i][j].contains("rB")) {
         				g.drawImage(blackPieces[3],j * 60,i * 60, null);
        			}
        			else if(boardArray[i][j].contains("qB")) {
         				g.drawImage(blackPieces[4],j * 60,i * 60, null);
        			}
        			else if(boardArray[i][j].contains("bB")) {
         				g.drawImage(blackPieces[5],j * 60,i * 60, null);
        			}
        			else if(boardArray[i][j].contains("KW")) {
         				g.drawImage(whitePieces[0],j * 60,i * 60, null);
        			}
			        else if(boardArray[i][j].contains("kW")) {
			         	g.drawImage(whitePieces[1],j * 60,i * 60, null);
			        }
			        else if(boardArray[i][j].contains("pW")) {
			         	g.drawImage(whitePieces[2],j * 60,i * 60, null);
			        }
			        else if(boardArray[i][j].contains("rW")) {
			         	g.drawImage(whitePieces[3],j * 60,i * 60, null);
			        }
			        else if(boardArray[i][j].contains("qW")) {
			         	g.drawImage(whitePieces[4],j * 60,i * 60, null);
			        }
			        else if(boardArray[i][j].contains("bW")) {
			         	g.drawImage(whitePieces[5],j * 60,i * 60, null);
			        }
       			}
      		}
     	}  
    }
 
  @Override
    public void mouseClicked(MouseEvent e){}

  @Override
    public void mouseEntered(MouseEvent e){}

  @Override
    public void mouseExited(MouseEvent e){}

  @Override
    public void mousePressed(MouseEvent e){
    	Pawn [] blackPawns= {Bpawn1, Bpawn2, Bpawn3, Bpawn4, Bpawn5, Bpawn6, Bpawn7, Bpawn8};
	 	Pawn [] whitePawns= {Wpawn1, Wpawn2, Wpawn3, Wpawn4, Wpawn5, Wpawn6, Wpawn7, Wpawn8};
	 	Knight [] blackKnights = {Bknight1, Bknight2};
	 	Knight [] whiteKnights = {Wknight1, Wknight2};
	 	Rook [] blackRooks = {Brook1, Brook2};
	 	Rook [] whiteRooks = {Wrook1, Wrook2};
	 	Bishop [] blackBishops = {Bbishop1, Bbishop2};
	 	Bishop [] whiteBishops = {Wbishop1, Wbishop2};
	    Point mouse = MouseInfo.getPointerInfo().getLocation();
	    Point offset = getLocationOnScreen();
	    int mx = mouse.x - offset.x;
	    int my = mouse.y - offset.y;
	    if(click == 1) {		//players first click
	    	sx = mx / 60;		//turns points on the gui into a point in boardArray
	      	sy = my / 60;
	    	click = 2;
    	}
    	else if(click == 2) {
      		dx = mx / 60;
      		dy = my / 60;
	      	/*
	      	System.out.print(sx);
	      	System.out.print(",");
	      	System.out.print(sy);
		    System.out.print("\t");
		    System.out.print(dx);
		    System.out.print(",");
		    System.out.print(dy);
		    System.out.println("\t");
		    */
      		if(boardArray[sy][sx].contains("W") && playerID == 1) {		//movement for white pieces
      			if(boardArray[sy][sx].contains("p")) {
      				int index = (Integer.parseInt(boardArray[sy][sx].replaceAll("[^0-9]", ""))) - 1;		//turns the number in the string in boardArray into an integer to be used as an index
      				whitePawns[index].move(dx,dy);
      				if(whitePawns[index].getValid()) {		//if the move is valid, it will change boardArray
      					boardArray[dy][dx] = boardArray[sy][sx];
      					boardArray[sy][sx] = "---";
      					updateTurn();
      				}
      			}
      			else if(boardArray[sy][sx].contains("k")) {
      				int index = (Integer.parseInt(boardArray[sy][sx].replaceAll("[^0-9]", ""))) - 1;
      				whiteKnights[index].move(dx,dy);
      				if(whiteKnights[index].getValid()) {
      					boardArray[dy][dx] = boardArray[sy][sx];
      					boardArray[sy][sx] = "---";
      					updateTurn();
      				}
      			}
      			else if(boardArray[sy][sx].contains("r")) {
      				int index = (Integer.parseInt(boardArray[sy][sx].replaceAll("[^0-9]", ""))) - 1;
      				whiteRooks[index].move(dx,dy);
      				if(whiteRooks[index].getValid()) {
      					boardArray[dy][dx] = boardArray[sy][sx];
      					boardArray[sy][sx] = "---";
      					updateTurn();
      				}
      				System.out.println(whiteRooks[index].getValid());
      			}
      			else if(boardArray[sy][sx].contains("b")) {
      				int index = (Integer.parseInt(boardArray[sy][sx].replaceAll("[^0-9]", ""))) - 1;
      				whiteBishops[index].move(dx,dy);
      				if(whiteBishops[index].getValid()) {
      					boardArray[dy][dx] = boardArray[sy][sx];
      					boardArray[sy][sx] = "---";
      					updateTurn();
      				}
      			}
		      	else if(boardArray[sy][sx].contains("K")) {
		      		Wking.move(dx,dy);
		      		if(Wking.getValid()) {
		      			boardArray[dy][dx] = boardArray[sy][sx];
		      			boardArray[sy][sx] = "---";
		      			updateTurn();
		      		}
		      	}
		      	else if(boardArray[sy][sx].contains("q")) {
		      		Wqueen.move(dx,dy);
		      		if(Wqueen.getValid()) {
		      			boardArray[dy][dx] = boardArray[sy][sx];
		      			boardArray[sy][sx] = "---";
		      			updateTurn();
		      		}
		      	}
      		}
      		else if(boardArray[sy][sx].contains("B") && playerID == 2) {		//movement for black pieces
		      	if(boardArray[sy][sx].contains("p")) {
		      		int index = (Integer.parseInt(boardArray[sy][sx].replaceAll("[^0-9]", ""))) - 1;
		      		blackPawns[index].move(dx,dy);
		      		if(blackPawns[index].getValid()) {
		      			boardArray[dy][dx] = boardArray[sy][sx];
		      			boardArray[sy][sx] = "---";
		      			updateTurn();
		      		}
		      	}
		      	else if(boardArray[sy][sx].contains("k")) {
		      		int index = (Integer.parseInt(boardArray[sy][sx].replaceAll("[^0-9]", ""))) - 1;
		      		blackKnights[index].move(dx,dy);
		      		if(blackKnights[index].getValid()) {
		      			boardArray[dy][dx] = boardArray[sy][sx];
		      			boardArray[sy][sx] = "---";
		      			updateTurn();
		      		}
		      	}
		      	else if(boardArray[sy][sx].contains("r")) {
		      		int index = (Integer.parseInt(boardArray[sy][sx].replaceAll("[^0-9]", ""))) - 1;
		      		blackRooks[index].move(dx,dy);
		      		if(blackRooks[index].getValid()) {
		      			boardArray[dy][dx] = boardArray[sy][sx];
		      			boardArray[sy][sx] = "---";
		      			updateTurn();
		      		}
		      	}
		      	else if(boardArray[sy][sx].contains("b")) {
		      		int index = (Integer.parseInt(boardArray[sy][sx].replaceAll("[^0-9]", ""))) - 1;
		      		blackBishops[index].move(dx,dy);
		      		if(blackBishops[index].getValid()) {
		      			boardArray[dy][dx] = boardArray[sy][sx];
		      			boardArray[sy][sx] = "---";
		      			updateTurn();
		      		}
		      	}
		      	else if(boardArray[sy][sx].contains("K")) {
		      		Bking.move(dx,dy);
		      		if(Bking.getValid()) {
		      			boardArray[dy][dx] = boardArray[sy][sx];
		      			boardArray[sy][sx] = "---";
		      			updateTurn();
		      		}
		      	}
		      	else if(boardArray[sy][sx].contains("q")) {
		      		Bqueen.move(dx,dy);
		      		if(Bqueen.getValid()) {
		      			boardArray[dy][dx] = boardArray[sy][sx];
		      			boardArray[sy][sx] = "---";
		      			updateTurn();
		      		}
		      	}
		    }
      		click = 1;
      		sx = 0;
      		sy = 0;
      		dx = 0;
      		dy = 0;
    	}
    	if(click == 1) {		//prints boardArray
    		for(int i = 0; i < 8; i += 1) {
     	 		for(int j = 0; j < 8; j += 1) {
      				System.out.print(boardArray[i][j]);
      			}
      			System.out.println();
    		}
    		checkLoss(Bking,"B","W");
    		checkLoss(Wking,"W","B");
    	}
  	}
  	
  	public boolean checkLoss(King king, String colour, String enemy) {
	  	int x = king.getX();
	  	int y = king.getY();
	  	int count = 0;
	  	for(int i = x; i <= 0; i -= 1) {
  			if(boardArray[y][i].contains(colour)) {
  				break;
  			}
  			else if(boardArray[y][i].contains(enemy) && (boardArray[y][i].contains("r") || boardArray[y][i].contains("q"))) {
  				count += 1;
  				break;
  			}
  		}
  		for(int i = x; i >= 7; i += 1) {
  			if(boardArray[y][i].contains(colour)) {
  				break;
  			}
  			else if(boardArray[y][i].contains(enemy) && (boardArray[y][i].contains("r") || boardArray[y][i].contains("q"))) {
  				count += 1;
  				break;
  			}
  		}
  		for(int i = x; i <= 0; i -= 1) {
  			if(boardArray[i][i].contains(colour)) {
	  			break;
  			}
  			else if(boardArray[i][i].contains(enemy) && (boardArray[i][i].contains("b") || boardArray[i][i].contains("q"))) {
  				count += 1;
  				break;
  			}
  		}
  		for(int i = x; i >= 7; i += 1) {
  			if(boardArray[i][i].contains(colour)) {
  				break;
  			}
  			else if(boardArray[i][i].contains(enemy) && (boardArray[i][i].contains("b") || boardArray[i][i].contains("q"))) {
  				count += 1;
  				break;
  			}
  		}
  		if((boardArray[x + 1][y + 1].contains("p") && boardArray[x + 1][y + 1].contains(enemy)) || (boardArray[x - 1][y + 1].contains("p") && boardArray[x - 1][y + 1].contains(enemy)) || (boardArray[x + 1][y - 1].contains("p") && boardArray[x + 1][y - 1].contains(enemy)) || (boardArray[x + 1][y - 1].contains("p") && boardArray[x + 1][y - 1].contains(enemy))){
  			count += 1;	
  		}
  		if(boardArray[x + 2][y + 1].contains("k" + enemy) || boardArray[x + 1][y + 2].contains("k" + enemy) || boardArray[x - 2][y + 1].contains("k" + enemy) || boardArray[x + 2][y - 1].contains("k" + enemy) || boardArray[x - 2][y - 1].contains("k" + enemy) || boardArray[x - 1][y + 2].contains("k" + enemy) || boardArray[x + 1][y - 2].contains("k" + enemy) || boardArray[x - 1][y - 2].contains("k" + enemy)) {
  			count += 1;
  		}
  		if(count == 6) {
  			return true;
  			System.out.println(enemy + " won");
  		}
  		else {
  			return false;
  		}
  	}
  
  	public void setUpPlayers() {		//gives player numbers
    	if (playerID == 1) {
    		System.out.println("You are player #1. You go first");
    		otherPlayer = 2;
    	}
    	else {
    		System.out.println("You are player #2. Wait for your turn");
    		otherPlayer = 1;
    	}
    }
  
	public void connectToServer () {	//connects to ChessServer
    	csc = new ClientSideConnection();
    }
    
    public void updateTurn() {
    	String n = csc.receiveBoard();		//recieves board from server in the form of a string
    	int y = 0;
    	int x = 0;
    	for(int i = 0; i < (n.length()) / 3 - 3; i += 1) {		//updates boardArray
    		boardArray[y][x] = n.substring(0,2);
    		n = n.substring(0,2);
    		x += 1;
    		if(x == 7) {
    			y += 1;
    			x = 0;
    		}
    	}
    	boardArray[7][7] = n;
    }
  
  	private class ClientSideConnection {
    	private Socket socket;
    	private DataInputStream dataIn;
    	private DataOutputStream dataOut;
    	
    	public ClientSideConnection() {
    		System.out.println("----Client----");
    		try {
    			socket = new Socket("localhost",51734);		//creates socket with ip localhost and port
    			dataIn = new DataInputStream(socket.getInputStream());
    			dataOut = new DataOutputStream(socket.getOutputStream());
    			playerID = dataIn.readInt();
    			System.out.println("Connected to server as player #" + playerID + ".");
    		} 
    		catch (IOException ex) {
    			System.out.println("IO Exception from CSC constructor");
    		}
    	}
    	
    	public void sendBoard(String n) {	//sends boardArray to be used by the other player
    		try {
    			dataOut.writeChars(n);
    			dataOut.flush();
    		}
    		catch (IOException ex) {
    			System.out.println("IOException from sendButtonNum() CSC");
    		}
    	}
    	
    	public String receiveBoard() {		//recieves boardArray
    		String n = "";
    		try {
    			n = dataIn.readLine();
    		}
    		catch (IOException ex){
    			System.out.println("IOException from receiveButtonNum() CSC");
    		}
    		return n;
    	}
    	
    	public void closeConnection() {		//disconnects from ChessServer
    		try {
    			socket.close();
    			System.out.println("---CONNCETION TERMINATED---");
    		}
    		catch (IOException ex) {
    			System.out.println("IOException on closeConnection() CSC");
    		}
    	}
    }
    
  @Override
    public void mouseReleased(MouseEvent e){}
  
  @Override
  	public void actionPerformed(ActionEvent evt){
   		repaint();   // Asks the JVM to indirectly call paint
  	}
}