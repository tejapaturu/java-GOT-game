package Model;

public abstract class Piece
{
    private int health;
    private int maxMove;
    private int range;
    private int damage;
    private String id;
    private int player;
    
    public Piece(int health, int maxMove, int range, int damage, String id, int player)
    {
    	this.health = health;
        this.maxMove = maxMove;
        this.range = range;
        this.damage = damage;
        this.id = id;
       
        this.player = player;
    }


	public int getHealth()
    {
		return health;
	}

	public int getPlayer()
    {
        return player;
    }

	public void setHealth(int health)
    {
		this.health = health;
	}
	
	public void takeDamage(int dmg)
	{
		this.health -=dmg;
	}
	
	public int getDamage()
	{
		return this.damage;
	}

	public String getID()
    {
        return this.id;
    }

    public boolean move (Board board, int currentX, int currentY, int newX, int newY)
    {
        if(validateMove( board, currentX,  currentY,  newX,  newY))
        {
            board.setSquarePiece(currentX, currentY, null);
            board.setSquarePiece(newX, newY,this);

            return true;
        }

        else
            return false;
    }

    public boolean validateMove(Board board, int currentX, int currentY, int newX, int newY)
    {
        //Pre conditions
        //If the new position already has a character.
        if(board.getSquarePiece(newX, newY) != null)
        {
            //If new position has the character of same team.
            if(board.getSquarePiece(newX, newY).getPlayer() == this.getPlayer())
            {
                System.err.println("Cannot place two characters of same team on the same square.");
            }

            //If new position has the character of different team.
            else
            {
                System.err.println("Cannot place two characters of different team on the same square, attack and defeat enemy.");
            }

            return false;
        }

        //Checks if the move is out of range.
        if(countMoveLen(currentX, currentY, newX, newY) > range)
        {
            System.err.println("Move length is greater than the range of the character.");
            return false;
        }
        //If new position is not placeable.
        if(board.getSquare(newX, newY).getIsPlacebale() == false)
        {
            System.err.println("Can't place charcater on the square selected");
            return false;
        }

        //Add conditions for players moving on players
        //Add conditions for player of the characters of the same team to be placed on each other.
        return true; //test return statement
    }

    //Counts the movement length to check if it fits the character's range.
    private int countMoveLen(int currentX, int currentY, int newX, int newY)
    {
        int maxX, minX, xDiff;
        int maxY, minY, yDiff;


        if(currentX=>newX)
        {
            maxX = currentX;
            minX = newX;
        }
        else
        {
            maxX = newX;
            minX = currentX;
        }

        xDiff = maxX - minX;

        if(currentY=>newY)
        {
            maxY = currentY;
            minY = newY;
        }
        else
        {
            maxY = newY;
            minY = currentY;
        }

        yDiff = maxY - miny;

        int maxDiff;

        if(xDiff >= yDiff)
            maxDiff = xDiff;
        else
            maxDiff = yDiff;

        return maxDiff;

    }
}
