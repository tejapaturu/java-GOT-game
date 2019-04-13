package Model;

public class Scout extends Piece
{
	private static final int MAXMOVE = 4;
	private static final int RANGE = 4;
	private static final int DAMAGE = 1;
	private static final String ID = "Scout";

	public Scout(int player)
	{
		super(3, MAXMOVE, RANGE, DAMAGE, ID, player);
	}
	
	public void Special()
	{
		//scout is able to move to any square on the map instantly
	}
}
