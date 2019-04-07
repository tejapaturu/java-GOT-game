package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Controller.SquareActionListener;
import Model.GameEngine;
import Model.Square;
import Model.Board;
import Model.CornerSquare;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFrame extends JFrame {
	
	private JPanel Board;
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private GameEngine gameEngine;
	private JButton[][] squares;
	private Model.Board gameBoard;
	 public ImageIcon Assasin, Mage, Scout, Soldier, Support, Tank;

	public MainFrame(String title, Model.Board board, GameEngine gameEngine)
	{
		super(title);
		this.gameEngine = gameEngine;
		this.gameBoard = board;
		//initialise();
		newInit();
		setIcons();
		//createDiamond();
		add(gui);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(750, 700);
		centreWindow(this);
		setPlayers();
		setVisible(true);
		
	}

	public void initialise()
	{
		
			int maxWidth = gameBoard.getWidth();
	        int maxHeight = gameBoard.getHeight();

	        int mid;

	        if((maxHeight%2) == 0)
	            mid = maxHeight/2;
	        else
	            mid = (maxHeight+1)/2;
	     squares = new JButton[maxWidth][maxHeight];
	       
		gui.setBorder(new EmptyBorder(4,4,4,4));
		JToolBar toolbar = new JToolBar();
		JButton newGame = new JButton("New Game");
		toolbar.setFloatable(false);
		gui.add(toolbar, BorderLayout.PAGE_START);
		
		
		 Board = new JPanel(new GridLayout(maxWidth, maxHeight));
		    Board.setBorder(new LineBorder(Color.BLACK));
		    gui.add(Board);
		    
		    //fills in the board panel with chess squares
		    Insets buttonMargin = new Insets(0,0,0,0);
		    ImageIcon icon = new ImageIcon(
                    new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		    
		    
		    
		    
	        for (int i = 0; i < squares.length; i++) {
	            for (int j = 0; j < squares[i].length; j++) {
	                JButton b = new JButton();
	                b.setMargin(buttonMargin);
	                
	                b.setIcon(icon);
	                b.setOpaque(false);
		    		b.setContentAreaFilled(false);
		    		b.setBorderPainted(false);
	                squares[j][i] = b;
	                //adds action listener for square interaction
	            //    squares[j][i].addActionListener(new SquareActionListener(gameBoard, j, i, gameEngine, this));
	            }
	            
	        }
	        
	        //creating corner squares
	        squares[mid-1][0] = new JButton();
	        squares[mid-1][0].addActionListener(new SquareActionListener(gameBoard, mid - 1, 0, gameEngine, this));
            squares[mid-1][0].setBackground(Color.GREEN);
            
            squares[mid-1][10] = new JButton();
	        squares[mid-1][10].addActionListener(new SquareActionListener(gameBoard, mid - 1, 10, gameEngine, this));
            squares[mid-1][10].setBackground(Color.GREEN);
            
            squares[0][mid-1] = new JButton();
	        squares[0][mid-1].addActionListener(new SquareActionListener(gameBoard, 0, mid - 1, gameEngine, this));
            squares[0][mid-1].setBackground(Color.GREEN);
            
            squares[10][mid-1] = new JButton();
	        squares[10][mid-1].addActionListener(new SquareActionListener(gameBoard, 10, mid - 1, gameEngine, this));
            squares[10][mid-1].setBackground(Color.GREEN);
     
	        // Initialise mid row first;
	        for (int i=2; i < maxWidth; i++)
	        {
	            JButton b = new JButton();
	            b.setMargin(buttonMargin);
	            //uses a transparent icon to allow for a background to be used as colour
                //set to same size as pieces
	            
               // b.setIcon(icon);
                //adding squares to board and assigning action listener
                squares[mid-1][i-1] = b;
                squares[mid-1][i-1].addActionListener(new SquareActionListener(gameBoard, mid-1, i-1, gameEngine, this));
                squares[mid-1][i-1].setBackground(Color.WHITE);
	            //for simple testing
	            System.out.println("The square added to gui is" + mid + ", " + i);
	        }

	        for(int a=(maxHeight-1); a>mid; a--)
	        {
	            int x = maxHeight - a;
	            // for the rows 2 to 5
	            int b = (x+1);


	            for (int i = (mid-x); i < (mid+x + 1); i++)
	            {
	            	System.out.println("Squares created are : s1: " + (a-1) + ", " + (i-1) + " and s2: " + (b-1) + ", " + (i-1));
	        	    squares[a-1][i-1].addActionListener(new SquareActionListener(gameBoard, (a-1), (i-1), gameEngine, this));
	                squares[b-1][i-1].addActionListener(new SquareActionListener(gameBoard, (b-1), (i-1), gameEngine, this));
	                
	                squares[b-1][i-1].setBackground(Color.YELLOW);
	                squares[a-1][i-1].setBackground(Color.BLUE);
	                
	                squares[b-1][i-1].setOpaque(true);
	                squares[a-1][i-1].setOpaque(true);
	                
	                squares[b-1][i-1].setContentAreaFilled(true);
	                squares[a-1][i-1].setContentAreaFilled(true);
	                
	                squares[b-1][i-1].setBorderPainted(true);
	                squares[a-1][i-1].setBorderPainted(true);
	                
	                	    		
	            }
	            
	        }
	        for(int i =0;i<maxWidth;i++)
	        {
	        	for(int j =0;j<maxHeight;j++)
	        	{
	        		Board.add(squares[j][i]);
	        	}
	        }
	}

	//takes input of players and passes them to gameEngine for player object creation
	private void setPlayers() {
		String p1Name = JOptionPane.showInputDialog("Enter Player 1 Name:");
		gameEngine.setPlayer(p1Name, 1);

		String p2Name = JOptionPane.showInputDialog("Enter Player 2 Name:");
		gameEngine.setPlayer(p2Name, 2);

	}

	//ensures app starts in the middle of the screen
	public static void centreWindow(Window mainFrame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2);
		mainFrame.setLocation(x, y);
	}
	
	public void setIcons()
	{
		createImages();
	   	for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
            	squares[i][j].setIcon(null);
            }
            }
        squares[0][5].setIcon(Assasin);
        squares[1][4].setIcon(Assasin);
        squares[1][6].setIcon(Mage);
        squares[2][3].setIcon(Mage);
        squares[2][7].setIcon(Scout);
        squares[3][2].setIcon(Scout);
        
        squares[10][5].setIcon(Soldier);
        squares[9][4].setIcon(Soldier);
        squares[9][6].setIcon(Support);
        squares[8][3].setIcon(Support);
        squares[8][7].setIcon(Tank);
        squares[7][2].setIcon(Tank);
	}
	
	private final void createImages() {
	       Assasin = createImageIcon("images/Black_Rook.png","Assasin");
	       Mage = createImageIcon("images/Black_Bishop.png","Mage");
	       Scout = createImageIcon("images/Black_Knight.png","Scout");
	       
	       Soldier = createImageIcon("images/White_Rook.png","Soldier");
	       Support = createImageIcon("images/White_Bishop.png","Support");
	       Tank = createImageIcon("images/White_Knight.png","Tank");
	    }
	
	 protected ImageIcon createImageIcon(String path,String description)
	   {
		 	
		   java.net.URL imgURL = getClass().getResource(path);
		   if (imgURL != null) 
		   {
			   return new ImageIcon(imgURL, description);
		   } else 
		   {
			   System.err.println("Couldn't find file: " + path);
			   return null;
		   }
		   
		
	   }
	 
	 public void newInit()
	 {
		 int maxWidth = gameBoard.getWidth();
		 int maxHeight = gameBoard.getHeight();
		  int max = (gameBoard.getWidth() - 1);    // the maximum co-ordinate

	        int mid;                            //the middle co-ordinate
	        int min = 0;                        //the starting co-ordinate

	        if((max%2) == 0)
	            mid = max/2;
	        else
	            mid = (max+1)/2;
	        squares = new JButton[maxWidth][maxHeight];
		       
			gui.setBorder(new EmptyBorder(4,4,4,4));
			JToolBar toolbar = new JToolBar();
			JButton newGame = new JButton("New Game");
			toolbar.setFloatable(false);
			gui.add(toolbar, BorderLayout.PAGE_START);
			
			
			 Board = new JPanel(new GridLayout(maxWidth, maxHeight));
			    Board.setBorder(new LineBorder(Color.BLACK));
			    gui.add(Board);
			    
			    //fills in the board panel with chess squares
			    Insets buttonMargin = new Insets(0,0,0,0);
			    ImageIcon icon = new ImageIcon(
	                    new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
			    
			    
		        for (int y = 0; y < squares.length; y++) {
		            for (int j = 0; j < squares[y].length; j++) {
		                JButton b = new JButton();
		                b.setMargin(buttonMargin);
		                
		                b.setIcon(icon);
		                b.setOpaque(false);
			    		b.setContentAreaFilled(false);
			    		b.setBorderPainted(false);
		                squares[j][y] = b;
	                    Board.add(squares[j][y]);
		                //adds action listener for square interaction
		            }
	        
	        //creating corner squares
	        squares[max][mid] = new JButton();
	        squares[max][mid].addActionListener(new SquareActionListener(gameBoard, max, mid, gameEngine, this));
            squares[max][mid].setBackground(Color.GREEN);
            Board.add(squares[max][mid]);
          
            squares[min][mid] = new JButton();
	        squares[min][mid].addActionListener(new SquareActionListener(gameBoard, min, mid, gameEngine, this));
            squares[min][mid].setBackground(Color.GREEN);
            Board.add(squares[min][mid]);

            squares[mid][min] = new JButton();
	        squares[mid][min].addActionListener(new SquareActionListener(gameBoard, mid, min, gameEngine, this));
            squares[mid][min].setBackground(Color.GREEN);
            Board.add(squares[mid][min]);
            
            squares[mid][max] = new JButton();
	        squares[mid][max].addActionListener(new SquareActionListener(gameBoard, mid, max, gameEngine, this));
            squares[mid][max].setBackground(Color.GREEN);
            Board.add(squares[mid][max]);




	        //Initlising normal squares of the diamond block.
	        //a initialises the rows 1 to 5
	        // and b initialises 6 to 9
	        for (int a=1,b=9,low=4,high=6;a<=b;a++,b--,low--,high++)
	        {
	        		//adding to many buttons
	            for(int i=low; i<=high;i++)
	            {
	                if (a==b)
	                {
	                    JButton button = new JButton();
	                    button.addActionListener(new SquareActionListener(gameBoard, a, i, gameEngine, this));
		                button.setBackground(Color.GRAY);
		                button.setOpaque(true);
		                button.setContentAreaFilled(true);
		                button.setBorderPainted(true);
	                    
	                    squares[a][i] = button;
	                    Board.add(squares[a][i]);
	                }
	                else
	                {
	                    
	                    JButton button = new JButton();
	                    button.addActionListener(new SquareActionListener(gameBoard, a, i, gameEngine, this));
		                button.setBackground(Color.GRAY);
		                button.setOpaque(true);
		                button.setContentAreaFilled(true);
		                button.setBorderPainted(true);
	                    
	                    squares[a][i] = button;
	                    
	                    JButton button2 = new JButton();
	                    button2.addActionListener(new SquareActionListener(gameBoard, b, i, gameEngine, this));
		                button2.setBackground(Color.GRAY);
		                button2.setOpaque(true);
		                button2.setContentAreaFilled(true);
		                button2.setBorderPainted(true);
	                    
	                    squares[b][i] = button2;
	                    
	                    Board.add(squares[b][i]);
	                    Board.add(squares[a][i]);

	                }

	            }

	        }
	        

	    }
	 }
}
