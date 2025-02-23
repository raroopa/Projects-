import java.util.ArrayList;
import java.util.List;

public class TileState
{
    private int[][] board; // locations of tiles
    private TileState parent; // board which generated this one
    private int emptyR, emptyC; // location of empty tile

    public TileState(int[][] initial)
    {
        board = copy(initial);
        setEmptyLocation();
        parent = null;
    }

    // This is called a copy constructor.
    // It instantiates a new object that’s a copy
    // of the argument.
    public TileState(TileState toCopy)
    {
        this.board = copy(toCopy.board);
        this.parent = toCopy.parent;
        this.emptyC = toCopy.emptyC;
        this.emptyR = toCopy.emptyR;
    }

    // return a copy of the input array
    public int[][] copy(int[][] arr)
    {
        int[][] newArr = new int[arr.length][arr[0].length];

        for(int i = 0; i < arr.length; i++)
        {
            for(int j = 0; j < arr[0].length; j++)
            {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    // Warning!  Very slow!
    private void setEmptyLocation()
    {
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                if (board[r][c] == 0)
                {
                    this.emptyR = r;
                    this.emptyC = c;
                }
            }
        }
    }

    public void setParent(TileState p)
    {
        this.parent = p;
    }

    public boolean isGoal()
    {
        return isGoal(this.board);
    }

    public boolean isGoal(int[][] board)
    {
        int correctVal = 1;
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                // for lower right corner val
                if (correctVal == board.length*board[0].length) correctVal = 0;
                if (board[r][c] != correctVal) return false;
                correctVal++;
            }
        }
        return true;
    }

    // NOTE:  if you wish, you can make a getNextState() that returns a single next
    // state or null if there are no remaining next states.

    public List<TileState> getNextStates() {
        ArrayList<TileState> nextTileState = new ArrayList<>();

        if (isInBounds(emptyR+1,emptyC))
        {
            TileState nextState = copy();
            parent = this;
            this.board = nextState.board;
            moveTile(emptyR+1,emptyC);
            this.board = parent.board;
            nextTileState.add(nextState);
        }

        if (isInBounds(emptyR-1,emptyC))
        {
            TileState nextState = copy();
            parent = this;
            this.board = nextState.board;
            moveTile(emptyR+1,emptyC);
            this.board = parent.board;
            nextTileState.add(nextState);
        }

        if (isInBounds(emptyR,emptyC+1))
        {
            TileState nextState = copy();
            parent = this;
            this.board = nextState.board;
            moveTile(emptyR+1,emptyC);
            this.board = parent.board;
            nextTileState.add(nextState);
        }

        if (isInBounds(emptyR,emptyC-1))
        {
            TileState nextState = copy();
            parent = this;
            this.board = nextState.board;
            moveTile(emptyR+1,emptyC);
            this.board = parent.board;
            nextTileState.add(nextState);
        }

        return nextTileState;
    }

    // Move the tile at (r, c) into the blank square if it’s adjacent.
    public void moveTile(int r, int c)
    {
        if (!isAdjacent(r, c, emptyR, emptyC)) return;

        int temp = board[emptyR][emptyC];
        board[emptyR][emptyC] = board[r][c];
        board[r][c] = temp;
    }

    // Check if (r, c) is adjacent to (r2, c2)
    private boolean isAdjacent(int r, int c, int r2, int c2)
    {
        int rDiff = Math.abs(r - r2);
        int cDiff = Math.abs(c - c2);
        return rDiff + cDiff == 1;
    }

    // Return true if this objects represents the same state as other
    public boolean equals(TileState other)
    {
        for(int i = 0; i < other.getBoard().length; i++)
        {
            for(int j = 0; j < other.getBoard()[0].length; j++)
            {
                if(this.board[i][j] != other.getBoard()[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    // return a copy of the current TileState object (copy self)
    public TileState copy()
    {
        TileState tileState = new TileState(this);
        return tileState;
    }

    public String toString()
    {
        String out = "";
        for (int r = 0; r < board.length; r++)
        {
            for (int c = 0; c < board[r].length; c++)
            {
                out += "[ " + board[r][c] + "] ";
            }
            out += "\n";
        }
        return out;
    }

    private boolean isInBounds(int r, int c)
    {
        if (r < 0 || c < 0) return false;
        if (r >= board.length || c >= board[0].length) return false;
        return true;
    }

    public int[][] getBoard()
    {
        return board;
    }
}
