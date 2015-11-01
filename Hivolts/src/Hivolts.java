import java.applet.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Amy Luo
 *Sets the basic parameters of the game board
 */
public class Hivolts extends Applet implements KeyListener {
	final int RANDOM_FENCES = 20;
	final int RANDOM_MHOS = 12;
	final int BOARD_SIZE = 12;
	final int INNER_BOARD_SIZE = BOARD_SIZE - 2;
	final int BOARD_DIMENSION = 600;
	final int TEXT_DIMENSION = 200;
	final int UNIT_DIMENSION = BOARD_DIMENSION / BOARD_SIZE;
	final int SPACE = 8;
	final int PLAYER_EMOJI = 0x1F64A;
	final int PLAYER_WIN_EMOJI = 0x1F649;
	final int PLAYER_LOST_EMOJI = 0x1F648;
	final int MHOS_EMOJI = 0x1F47E;
	final int FENCE_ICON = 0x1F533;
			
	char[][] board = new char [BOARD_SIZE][BOARD_SIZE];
	int[][] randomList = new int [(int)Math.pow(INNER_BOARD_SIZE, 2)][2]; 
	int[] player = new int [2];
    int[][] mhos = new int[RANDOM_MHOS][2];

	/**
	 * Initializes the board size and background color.
	 * Also adds a KeyListener in order for the player to control the icon.
	 */
    public void init() {
		setBackground(Color.BLACK);
		this.setSize(BOARD_DIMENSION, BOARD_DIMENSION + TEXT_DIMENSION);
		initBoard();
		addKeyListener(this);
	}

	/**
	 * Initializes locations of the outside fences designated with the letter
	 * 'F' and the location of the player with the symbol '+'. 
	 */
	public void initBoard() {
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				if ((i > 0 && i < BOARD_SIZE - 1) 
					&& (j > 0 && j < BOARD_SIZE - 1)) {
					board[i][j] = ' ';
				} else {
					board[i][j] = 'F';
				}		
			}
		}
		prepareRandom();
		// the randomList is a 2D Array with 100(area of the inner square).
		// the player will take the 0th position of the randomized list as its
		// position
		player[0] = randomList[0][0];
		player[1] = randomList[0][1];
		board[player[0]][player[1]] = '+';
		initRandomFence();
		initRandomMhos();
	}
	
	/**
	 * The prepareRandom method initializes a randomList based on the size of
	 * the board without the outer ring of fences. 
	 */
	public void prepareRandom() {
		for(int i = 0; i < INNER_BOARD_SIZE; i++) {
			for(int j = 0; j < INNER_BOARD_SIZE; j++) {
				randomList[i * INNER_BOARD_SIZE + j][0] = i + 1;
				randomList[i * INNER_BOARD_SIZE + j][1] = j + 1;
			}
		}
		/*
		 * This adds random numbers created by the Math.random function into 
		 * the list 
		 */
		for(int i = 0; i < 33; i++) {
			int randomNumber = (int)(Math.random() * 100);
			int x = randomList[i][0];
			int y = randomList[i][1];
			randomList[i][0] = randomList[randomNumber][0];
			randomList[i][1] = randomList[randomNumber][1];
			randomList[randomNumber][0] = x;
			randomList[randomNumber][1] = y;
		}
	}
	
	/**
	 * Initializes the location of the 20 random fences with the Math.Random
	 * method and puts them in the 1 through 20th spot on the randomList.
	 */
	public void initRandomFence() {
		for(int i = 0; i < RANDOM_FENCES; i++) {
			int x = randomList[1 + i][0];
			int y = randomList[1 + i][1];
			board[x][y] = 'F';
		}	
	}
	
	/**
	 * Initializes the location of the 12 random Mhos with the Math.Random
	 * method and puts them in the 21 through 33rd spot on the randomList.
	 */
	public void initRandomMhos() {
		for(int i = 0; i < RANDOM_MHOS; i++) {
			int x = randomList[1 + RANDOM_FENCES + i][0];
			int y = randomList[1 + RANDOM_FENCES + i][1];
			board[x][y] = 'M';
			mhos[i][0] = x;
			mhos[i][1] = y;
		}
	}
	
	public void keyPressed( KeyEvent e ) { }
	public void keyReleased( KeyEvent e ) { }
	/**
	 * The KeyTyped class first gets the value of the key pressed by the player
	 * with the e.getKeyChar method. 
	 * Then multiple switch statements are used to change the location of the 
	 * player. 
	 * After the location is changed, the repaint method is used to show the 
	 * new location of the player on the output widow. 
	 * The location will then be consumed in preparation for the next key press.
	 */
	public void keyTyped( KeyEvent e ) {
		char c = e.getKeyChar();
		boolean moveMhos = true;
		// the game will restart if the player symbol can no longer be found on
		// the board and the 'r' key was pressed.
		if (board[player[0]][player[1]] != '+') {
			if (c == 'r') {
				initBoard();
				repaint();
			}
			e.consume();
			return;
		}
		/*
		 * The switch statements change the location of the player icon when 
		 * different keys are pressed.
		 * The Math.max and Math.min method ensure the new location will be in
		 * boundaries of the game board.
		 */
		if ( c != KeyEvent.CHAR_UNDEFINED ) {
			board [player[0]][player[1]] = ' ';
			
			switch (c) {
			// w moves the player up one spot
			case 'w': player[1] = Math.max(0, player[1] - 1);
					  break;
					   
		    // x moves the player down one spot
			case 'x': player[1] = Math.min(BOARD_SIZE - 1, player[1] + 1);
					  break;
			
			// a moves the player to the left one spot		  
			case 'a': player[0] = Math.max(0, player[0] - 1);
					  break;
			
			// d moves the player to the right one spot		  
			case 'd': player[0] = Math.min(BOARD_SIZE - 1, player[0] + 1);
			  		  break;
			  		  
			// q moves the player up and up and left one spot
			case 'q': player[0] = Math.max(0, player[0] - 1);
        			  player[1] = Math.max(0, player[1] - 1);
        			  break;
        	// e moves the player up and right one spot		  
			case 'e': player[0] = Math.min(BOARD_SIZE - 1, player[0] + 1);
        			  player[1] = Math.max(0, player[1] - 1);
        			  break;
        	
        	// z moves the player to the left and down one spot			  
			case 'z': player[0] = Math.max(0, player[0] - 1);
        			  player[1] = Math.min(BOARD_SIZE - 1, player[1] + 1);
        			  break;
        	
        	// c moves the player to the right and down one spot	
			case 'c': player[0] = Math.min(BOARD_SIZE - 1, player[0] + 1);
					  player[1] = Math.min(BOARD_SIZE - 1, player[1] + 1);
					  break;
			
			// s keeps the player in place and moves the Mhos	
			case 's': break;
			
			// j creates a random location and places the player there
			case 'j': moveMhos = false; 
					  for (;;) {
						player[0] = 1 + (int)(Math.random() * 10) % 9;
						player[1] = 1 + (int)(Math.random() * 10) % 9;
						if (board[player[0]][player[1]] != 'F')
							break;
					  }
					  
			// if the player does not press a key, the Mhos will not move
			default: moveMhos = false;
					 break;
			}
		    e.consume();
			
		    /*
		     * The playerLost method will be executed when the location of the 
		     * player coincides with that of a fence or of a Mho. If the player
		     * is in a safe spot, the program will proceed to move and draw the
		     * player.  
		     */
			if (board[player[0]][player[1]] == 'F' 
				|| board[player[0]][player[1]] == 'M') {
				playerLost();
			} else {
		        board[player[0]][player[1]] = '+';
			}
			if (moveMhos) moveMhos();
			repaint();
			
		}
	}
	/**
	 * This is the method used to move the Mhos. 
	 */
	public void moveMhos() {
		int liveMhos = 0;
		// initializes the number of Mhos
		for (int i = 0; i < RANDOM_MHOS; i++) {
			int x = mhos[i][0];
			int y = mhos[i][1];
			
			// if the Mho is already dead, then the for loop proceeds to the 
			// next Mho
			if (x == -1 && y == -1)
				continue;
			
			liveMhos++;
			
			// checks the position of the player relative to the Mho
			int xMove = player[0] > x ? 1 : -1;
            int yMove = player[1] > y ? 1 : -1;
            
			// if the player's position has already been replaced by a Mho, the 
            // player loses
			if (mhos[i][0] == player[0] && mhos[i][1] == player[1]) {
				playerLost();
				return;
			}
			
			 // this decides how the Mhos should move
		    if (x != player[0] && y != player[1]) {
		    	
		    	// if the next predicted location is empty or has the player in 
		    	// it, the Mho will move there
		    	if (board[x + xMove][y + yMove] == ' ' || 
		    		board[x + xMove][y + yMove] == '+') {
		    		moveMho(i, x + xMove, y + yMove);
		    	} else {
		    		
		    		// if the Mho is closer to the player on the vertical(y) 
		    		// direction, the Mho will move in the horizontal(x) 
		    		// direction first 
		    		if (Math.abs(player[0] - mhos[i][0]) 
		    			> Math.abs(player[1] - mhos[i][1]) 
		    			&& (board[x + xMove][y] == ' ' 
		    			|| board[x + xMove][y + yMove] == '+')) {
		    			moveMho(i, x + xMove, y);
		    		
		    		// if the Mho is closer to the player on the horizontal(x) 
		    		// direction, the Mho will move in the vertical(y) 
		    		//direction first 
		    		} else if (board[x][y + yMove] == ' ' 
		    				  || board[x + xMove][y + yMove] == '+') {
		    			moveMho(i, x, y + yMove);
		    		
		    		// if all of the above conditions cannot be met, the Mho 
		    		// will move onto an electric fence and die
		    		} else if (board[x + xMove][y + yMove] == 'F' 
		    				|| board[x + xMove][y] == 'F' 
		    				||board[x][y + yMove] == 'F') {
		    			killMho(i);
		    		}
		    	}
		    	
		    // if the Mho is directly to the right or to the left of the player
		    // it will head straight for the player
		    } else {
		    	if (x == player[0]) {
					y += yMove;
					
				// if the Mho is directly above or below the player
				// it will move straight up or down
				} else if (y == player[1]) {
					x += xMove;
			    }
		    	
		    	// if there is already a Mho in the new location, then the Mho 
		    	// will not move
				if (board[x][y] == 'M')
					continue;
				
				// if the Mho is on an electric fence, it will die
				if (board[x][y] == 'F') {
					killMho(i);
					
				// else the Mho will be moved to its new location
				} else {
					moveMho(i, x, y);
				}
		    }
		    
			// if the Mho is on the location of the player, the player will 
		    // lose the game
			if (mhos[i][0] == player[0] && mhos[i][1] == player[1]) {
				playerLost();
				return;
			}
		}
		
		// if there are no more live Mhos, then the player wins
	    if (liveMhos == 0) {
			playerWin();
		}
	}
	
	// move Mho index to new position x, y
	public void moveMho(int index, int x, int y) {
		board[mhos[index][0]][mhos[index][1]] = ' ';
		mhos[index][0] = x;
		mhos[index][1] = y;
		board[x][y] = 'M';
	}
	/**
	 * If the Mho was killed, the killMho method will set the position of that
	 * Mho to be empty and mark its location in the index as -1. 
	 * @param index the index of the current Mho
	 */
	public void killMho(int index) {
		board[mhos[index][0]][mhos[index][1]] = ' ';
		mhos[index][0] = mhos[index][1] = -1;
	}
	
	/**
	 * The paint method will draw out all the icons for the items according to 
	 * their allocated symbols.
	 */
	public void paint(Graphics g) {
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				switch (board[i][j]) {
				
				// if the value of the 2D array is 'F', the emoji allocated 
				// for the fence will be drawn
				case 'F': drawEmoji(g, i, j, FENCE_ICON);
						  break;
				
				// if the value of the 2D array is '+', the emoji allocated 
				// for the player will be drawn
				case '+': drawEmoji(g, i, j, PLAYER_EMOJI);
					      break;
				 
				// if the value of the 2D array is 'L', the emoji allocated 
				// for the losing player will be drawn
				case 'L': drawEmoji(g, i, j, PLAYER_LOST_EMOJI);
						  break;
				
				// if the value of the 2D array is 'W', the emoji allocated 
				// for the winning player will be drawn
				case 'W': drawEmoji(g, i, j, PLAYER_WIN_EMOJI);
				  		  break;
				
				// if the value of the 2D array is 'M', the emoji allocated 
				// for the Mhos will be drawn
				case 'M': drawEmoji(g, i, j, MHOS_EMOJI);
						  break;
				}
			}
		}
		// a string will be printed on the bottom of the screen to indicate 
		// that it's the player's turn or if the player won or lost the game
		String s = "Your turn to play";
		if (board[player[0]][player[1]] == 'L') {
			s = "Your lost, use 'r' to restart";
		} else if (board[player[0]][player[1]] == 'W') {
			s = "Your won, use 'r' to restart";
		}
		char[] chars = s.toCharArray();
		g.setColor(Color.WHITE);
		g.setFont(g.getFont().deriveFont((float)(UNIT_DIMENSION - SPACE)));
		g.drawChars(chars, 0, s.length(), UNIT_DIMENSION + SPACE, 
					BOARD_DIMENSION + UNIT_DIMENSION);
	}
	
	/**
	 * When the player loses, the symbol for the player will be replaced with 
	 * a 'L' that will draw the losing emoji as the player icon when the paint
	 * method is called. 
	 */
	public void playerLost() {
		board[player[0]][player[1]] = 'L';
	}
	
	/**
	 * When the player wins, the symbol for the player will be replaced with 
	 * a 'W' that will draw the winning emoji as the player icon when the paint 
	 * method is called.  
	 */
	public void playerWin() {
		board[player[0]][player[1]] = 'W';
	}
	
	/**
	 * This method converts the emojiCode and draws out the icon on the desired
	 * location
	 * @param g Graphics
	 * @param boardX X coordinate for the board
	 * @param boardY Y coordinate for the board
	 * @param emojiCode hex code for the emojis
	 */
	public void drawEmoji(Graphics g, int boardX, int boardY, int emojiCode) {
		int x = boardX * UNIT_DIMENSION + SPACE;
		int y = (boardY + 1) * UNIT_DIMENSION;
		g.setFont(g.getFont().deriveFont((float)(UNIT_DIMENSION - SPACE)));
		g.drawChars(Character.toChars(emojiCode), 0, 2, x, y);
	}
}