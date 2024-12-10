
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *
 * @author mvail, Daylen Mathews
 */
public class CircuitBoard {

    /**
     * current contents of the board
     */
    private char[][] board;
    /**
     * location of row,col for '1'
     */
    private Point startingPoint;
    /**
     * location of row,col for '2'
     */
    private Point endingPoint;

    //constants you may find useful
    private final int ROWS; //initialized in constructor
    private final int COLS; //initialized in constructor
    private static final char OPEN = 'O';	//capital 'o', an open position
    private static final char CLOSED = 'X';//a blocked position
    private static final char TRACE = 'T';	//part of the trace connecting 1 to 2
    private static final char START = '1';	//the starting component
    private static final char END = '2';	//the ending component
    private final String ALLOWED_CHARS = "OXT12"; //useful for validating with indexOf

    /**
     * Construct a CircuitBoard from a given board input file, where the first
     * line contains the number of rows and columns as ints and each subsequent
     * line is one row of characters representing the contents of that position.
     * Valid characters are as follows: 'O' an open position 'X' an occupied,
     * unavailable position '1' first of two components needing to be connected
     * '2' second of two components needing to be connected 'T' is not expected
     * in input files - represents part of the trace connecting components 1 and
     * 2 in the solution
     *
     * @param filename file containing a grid of characters
     * @throws FileNotFoundException if Scanner cannot open or read the file
     * @throws InvalidFileFormatException for any file formatting or content
     * issue
     */
    public CircuitBoard(String filename) throws FileNotFoundException {
        Scanner fileScan = new Scanner(new File(filename));

        //TODO: parse the given file to populate the char[][]
        // throw FileNotFoundException if Scanner cannot read the file
        // throw InvalidFileFormatException if any issues are encountered while parsing the file
        //replace with initialization statements using values from file
        String firstLine = fileScan.nextLine();
        Scanner firstLineScan = new Scanner(firstLine);

        if (!firstLineScan.hasNextInt()) {
            throw new InvalidFileFormatException(filename + " first value is not an integer.");
        }
        ROWS = firstLineScan.nextInt();
        if (!firstLineScan.hasNextInt()) {
            throw new InvalidFileFormatException(filename + " second value is not an integer.");
        }
        COLS = firstLineScan.nextInt();
        board = new char[ROWS][COLS];

        int oneCount = 0;
        int twoCount = 0;

        for (int i = 0; i < ROWS; i++) { // i rows
            if (!fileScan.hasNext()) {
                firstLineScan.close();
                fileScan.close();
                throw new InvalidFileFormatException(
                        String.format("%s: row %d does not contain %d rows.", filename, i, ROWS));
            }

            String line = fileScan.nextLine();
            Scanner lineScanner = new Scanner(line);
            for (int k = 0; k < COLS; k++) {
                if (!lineScanner.hasNext()) {
                    lineScanner.close();
                    fileScan.close();
                    throw new InvalidFileFormatException(
                            String.format("%s: row %d does not contain %d columns.", filename, i, COLS));
                }

                String columnValue = lineScanner.next();
                {
                    if (columnValue.length() != 1) {
                        lineScanner.close();
                        fileScan.close();
                        throw new InvalidFileFormatException(String
                                .format("%s: row %d column %d contains more than one character.", filename, i, k));
                    }
                    char colVal = columnValue.charAt(0);
                    if (colVal == START) {
                        if (startingPoint != null) {
                            fileScan.close();
                            lineScanner.close();
                            throw new InvalidFileFormatException(filename + " contains more than one start point.");
                        }
                        startingPoint = new Point(i, k);
                        oneCount++;
                    } else if (colVal == END) {
                        if (endingPoint != null) {
                            fileScan.close();
                            lineScanner.close();
                            throw new InvalidFileFormatException(filename + " contains more than one end point.");
                        }
                        endingPoint = new Point(i, k);
                        twoCount++;
                    }
                    if (ALLOWED_CHARS.indexOf(colVal) == -1) {
                        fileScan.close();
                        lineScanner.close();
                        throw new InvalidFileFormatException(filename + " contains invalid characters.");

                    } else {
                        board[i][k] = colVal;
                    }
                }
            }
            if (lineScanner.hasNext()) {
                lineScanner.close();
                fileScan.close();
                throw new InvalidFileFormatException(filename + " contains more than " + COLS + " columns.");
            }
            lineScanner.close(); // close scanner
        }
        if (oneCount == 0 || twoCount == 0) {
            firstLineScan.close();
            fileScan.close();
            throw new InvalidFileFormatException(filename + " does not contain a start or end point.");
        }
        if (fileScan.hasNext()) {
            firstLineScan.close();
            fileScan.close();
            throw new InvalidFileFormatException(filename + " contains more than " + ROWS + " rows.");
        }
        firstLineScan.close();
        fileScan.close();
    }

    public void tracePath(CircuitBoard board, List<Point> path) {
        for (Point p : path) {
            board.makeTrace(p.x, p.y);  // marks the trace as 'T' in the CircuitBoard
        }
    }

    /**
     * Copy constructor - duplicates original board
     *
     * @param original board to copy
     */
    public CircuitBoard(CircuitBoard original) {
        board = original.getBoard();
        startingPoint = new Point(original.startingPoint);
        endingPoint = new Point(original.endingPoint);
        ROWS = original.numRows();
        COLS = original.numCols();
    }

    /**
     * Utility method for copy constructor
     *
     * @return copy of board array
     */
    private char[][] getBoard() {
        char[][] copy = new char[board.length][board[0].length];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                copy[row][col] = board[row][col];
            }
        }
        return copy;
    }

    /**
     * Return the char at board position x,y
     *
     * @param row row coordinate
     * @param col col coordinate
     * @return char at row, col
     */
    public char charAt(int row, int col) {
        return board[row][col];
    }

    /**
     * Return whether given board position is open
     *
     * @param row
     * @param col
     * @return true if position at (row, col) is open
     */
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
            return false;
        }
        return board[row][col] == OPEN;
    }

    /**
     * Set given position to be a 'T'
     *
     * @param row
     * @param col
     * @throws OccupiedPositionException if given position is not open
     */
    public void makeTrace(int row, int col) {
        if (isOpen(row, col)) {
            board[row][col] = TRACE;
        } else {
            throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
        }
    }

    /**
     * @return starting Point(row,col)
     */
    public Point getStartingPoint() {
        return new Point(startingPoint);
    }

    /**
     * @return ending Point(row,col)
     */
    public Point getEndingPoint() {
        return new Point(endingPoint);
    }

    /**
     * @return number of rows in this CircuitBoard
     */
    public int numRows() {
        return ROWS;
    }

    /**
     * @return number of columns in this CircuitBoard
     */
    public int numCols() {
        return COLS;
    }

    /**
     * Get the start point as row, col coordinates.
     */
    public boolean isStart(int row, int col) {
        return startingPoint.x == row && startingPoint.y == col;
    }

    /**
     * Get the end point as row, col coordinates.
     */
    public boolean isEnd(int row, int col) {
        return endingPoint.x == row && endingPoint.y == col;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                str.append(board[row][col] + " ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
// class CircuitBoard
