package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Model.GameEngine;
import Controller.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
	
	private JPanel Board;
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private GameEngine gameEngine;
	private JButton[][] gridGUI;
	private Model.Board gameBoard;
	private StatusBar statusBar;
	private ImageIcon Assasin, Mage, Scout, Soldier, Support, Tank;
	private TurnController turnController;
	String p1Name;
	String p2Name;

	public MainFrame(String title, Model.Board board, GameEngine gameEngine, TurnController turnController)
	{
		super(title);
		this.gameEngine = gameEngine;
		this.gameBoard = board;
		this.turnController = turnController;
		Initialise();
		setIcons();
		add(gui);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(750, 700);
		centreWindow(this);
		setPlayers();
		setVisible(true);
		this.statusBar = new StatusBar(turnController, p1Name, p2Name);
		getContentPane().add(statusBar, java.awt.BorderLayout.SOUTH);
		statusBar.update();
	}

	
	//takes input of players and passes them to gameEngine for status bar and win screen
	private void setPlayers() {
		p1Name = JOptionPane.showInputDialog("Enter Player 1 Name:");
		p2Name = JOptionPane.showInputDialog("Enter Player 2 Name:");
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
	   	for (int i = 0; i < gridGUI.length; i++)
	   	{
            for (int j = 0; j < gridGUI[i].length; j++)
            {
            	gridGUI[i][j].setIcon(null);
            }
	   	}
        gridGUI[0][5].setIcon(Assasin);
        gridGUI[1][4].setIcon(Assasin);
        gridGUI[1][6].setIcon(Mage);
        gridGUI[2][3].setIcon(Mage);
        gridGUI[2][7].setIcon(Scout);
        gridGUI[3][2].setIcon(Scout);
        
        gridGUI[10][5].setIcon(Soldier);
        gridGUI[9][4].setIcon(Soldier);
        gridGUI[9][6].setIcon(Support);
        gridGUI[8][3].setIcon(Support);
        gridGUI[8][7].setIcon(Tank);
        gridGUI[7][2].setIcon(Tank);
        
		
	}
	
	private final void createImages()
	{
		Assasin = createImageIcon("images/Monster1.png","Assasin");
		Mage = createImageIcon("images/Monster2.png","Mage");
		Scout = createImageIcon("images/Monster3.png","Scout");
	       
		Soldier = createImageIcon("images/Monster4.png","Soldier");
		Support = createImageIcon("images/PoisonedHero2.png","Support");
		Tank = createImageIcon("images/PoisonedHero3.png","Tank");
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
	 
	 public void Initialise()
	 {
		 int width = gameBoard.getWidth();

		 int max=10, mid=5, min=0;

		 //Initialising GUI
		 //Creading grid
	        gridGUI = new JButton[width][width];
			gui.setBorder(new EmptyBorder(4,4,4,4));
			JToolBar toolbar = new JToolBar();
			toolbar.setFloatable(false);
			gui.add(toolbar, BorderLayout.PAGE_START);
			
			 Board = new JPanel(new GridLayout(width, width));
			 Board.setBorder(new LineBorder(Color.BLACK));
			 gui.add(Board);
			    
			    //fills in the board panel with chess gridGUI
			    Insets buttonMargin = new Insets(0,0,0,0);
			    ImageIcon icon = new ImageIcon(
	                    new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
			    
			    
		        for (int y = 0; y < gridGUI.length; y++)
		        {
					for (int j = 0; j < gridGUI[y].length; j++) {
						JButton b = new JButton();
						b.setMargin(buttonMargin);

						b.setIcon(icon);
						b.setOpaque(false);
						b.setContentAreaFilled(false);
						b.setBorderPainted(false);
						gridGUI[j][y] = b;
						//adds action listener for square interaction
					}
				}



    		/* Initialising normal squares of the diamond block.
			 * upperRow initialises the rows 1 to 5
			 * and lowerRow initialises 6 to 9
			 *
			 * The initialisation starts at row 1 and row 9
			 * and creating squares at columns 4 to 6 in those rows.
			 * After every loop, upperRow increments and lowerRow decrements by 1
			 * and the column width increases on both sides by 1.
			 * 
			 * Jbuttons are created and given correct properties
			 */
    		for (int upperRow=1,lowerRow=9,low=4,high=6;upperRow<lowerRow;upperRow++,lowerRow--,low--,high++)
    		{
    			for(int i=low; i<=high;i++)
    			{
    				//For middle row
					if (upperRow==(lowerRow-2))
					{
						JButton button = new JButton();
						button.addActionListener(new SquareActionListener(gameBoard, upperRow+1, i, gameEngine, this, turnController));
						button.setBackground(Color.DARK_GRAY);
						setButtonProperties(button);

						gridGUI[upperRow+1][i] = button;

						//debug
						System.out.println("line 226: GUI for Squares created for : s0: " + (upperRow+1) + ", " + i);
					}

					JButton button = new JButton();
					button.addActionListener(new SquareActionListener(gameBoard, upperRow, i, gameEngine, this, turnController));
					button.setBackground(Color.DARK_GRAY);
					setButtonProperties(button);

					gridGUI[upperRow][i] = button;


					JButton button2 = new JButton();
					button2.addActionListener(new SquareActionListener(gameBoard, lowerRow, i, gameEngine, this, turnController));
					button2.setBackground(Color.DARK_GRAY);
					setButtonProperties(button2);

					gridGUI[lowerRow][i] = button2;

					//debug
					System.out.println("line 245: GUI for Squares created for : s1: " + upperRow + ", " + i + " and s2: " + lowerRow + ", " + i);
    			}
    		}
    		
    		//individually creating corner squares so they can be assigned different colour
	        gridGUI[max][mid] = new JButton();
	        gridGUI[max][mid].addActionListener(new SquareActionListener(gameBoard, max, mid, gameEngine, this, turnController));
            gridGUI[max][mid].setBackground(Color.GREEN);
            setButtonProperties(gridGUI[max][mid]);

            gridGUI[min][mid] = new JButton();
	        gridGUI[min][mid].addActionListener(new SquareActionListener(gameBoard, min, mid, gameEngine, this, turnController));
            gridGUI[min][mid].setBackground(Color.GREEN);
            setButtonProperties(gridGUI[min][mid]);

            gridGUI[mid][min] = new JButton();
	        gridGUI[mid][min].addActionListener(new SquareActionListener(gameBoard, mid, min, gameEngine, this, turnController));
            gridGUI[mid][min].setBackground(Color.GREEN);
    		setButtonProperties(gridGUI[mid][min]);
            
            gridGUI[mid][max] = new JButton();
	        gridGUI[mid][max].addActionListener(new SquareActionListener(gameBoard, mid, max, gameEngine, this, turnController));
            gridGUI[mid][max].setBackground(Color.GREEN);
            setButtonProperties(gridGUI[mid][max]);
				
    		//adding buttons to view
		        for(int i = 0;i<width;i++)
		        {
		        	for (int j= 0; j<width;j++)
		        	{
		        		Board.add(gridGUI[i][j]);
		        	}
		        }
	 }
	 
	//reflects a move made on the board
	   public void movePiece(int pieceX, int pieceY, int moveX, int moveY)
	   {
		   gridGUI[moveX][moveY].setIcon(gridGUI[pieceX][pieceY].getIcon());
		   gridGUI[pieceX][pieceY].setIcon(null);
	   }
	 //simple helper method to set buttons not a part of board
	   public void setButtonProperties(JButton button)
	   {
		   button.setOpaque(true);
           button.setContentAreaFilled(true);
           button.setBorderPainted(true);
	   }
	   
	   //updates individual gui components according to board
	   public void updateComponents()
	   {
		   statusBar.update();
		   checkIcons();
		   int playerWins = gameBoard.checkWinConditions();
		   if(playerWins == 1)
		   {
			   displayWin(playerWins);
		   }
		   else if(playerWins == 2)
		   {
			  displayWin(playerWins);
		   }
	   }
	   
	 //checks for dead pieces and updates gui
	   public void checkIcons()
	   {
		   int dim = gameBoard.getWidth();
		   for(int i = 0;i<dim;i++)
	        {
	        	for (int j= 0; j<dim;j++)
	        	{
	        		if(gameBoard.getSquare(i, j)!=null)
	        		{
	        			if(gameBoard.getSquare(i, j).getPiece() == null)
	        		{
	        			gridGUI[i][j].setIcon(null);
	        		}
	        		}
	        	}
	        }
		   
	   }
	   
	   private static void displayWin(int player)
	    {
		   //creates a simple win alert and closes game
		   String winningMessage = "Congratulations player " + player + " you win!";
	       int close = JOptionPane.showConfirmDialog(null, winningMessage, "Game Over", JOptionPane.DEFAULT_OPTION);
	       if (close == 0)
	       {
	    	   System.exit(0);
	       }
	    }
	   
	   // a simple testing method for help when debugging, labels piece squares with their set position and
	   // number of player they are associated with
	   private void squarePlacementTest()
	   {
		   for (int i = 0; i < gridGUI.length-1; i++)
			{
	            for (int j = 0; j < gridGUI[i].length-1; j++)
	            {
	            	if(gameBoard.getSquare(i, j) != null)
	            	{
	            		if(gameBoard.getSquare(i, j).getPiece()!=null)
	            		{
	            			gridGUI[i][j].setText(i+ " " + j +" " + gameBoard.getSquarePiece(i, j).getPLAYER());
	            		}
	            	}
	            	else
	            	{
	            		gridGUI[i][j].setText(i+ " " + j);
	            	}
	            }
			}
	   }
}
