/**
 * Author: Sara Kenefick
 *
 * PennKey: sarak24
 *
 * Execution: java SudokuGrid [input sudoku table]
 * Command line argument [input sudoku table] should be a text file representing 
 * the initial puzzle where numbers are arranged in a 6x6 formation. Each number
 * will be an integer 1-6 and an empty square is denoted by a whitespace character.
 * If the file is formatted incorrectly, contains illegal inputs, or has two of 
 * the same number in a row/column/box, there will be an error message. The program
 * will accept a file that contains seven lines with a blank last line (new line 
 * after last row of table), but otherwise the file should contain exactly 6 lines.  
 * 
 * Description: a class that executes gameplay: reads in the initial sudoku board,
 * draws the grid and digits, updates the numbers based on user input, and 
 * highlights columns/rows/boxes with repeat numbers. When the puzzle is solved, 
 * the program stops and prints a "You Win" message. A SudokuGrid object is a 2D
 * array (3 x 2) of Box objects. 
 *
 */

public class SudokuGrid {
    
    // execute gameplay
    public static void main(String[] args) {
        String inputTable = args[0];
        drawGrid(inputTable);
    }
    
    // fields
    private Box[][] grid;
    private int moves;
    
    // constructor
    public SudokuGrid(int[][] intArray) {
        Box box1 = new Box(intArray, 0, 0);
        Box box2 = new Box(intArray, 0, 3);
        Box box3 = new Box(intArray, 2, 0);
        Box box4 = new Box(intArray, 2, 3);
        Box box5 = new Box(intArray, 4, 0);
        Box box6 = new Box(intArray, 4, 3);
        Box[][] grid = {{box1, box2}, {box3, box4}, {box5, box6}};
        
        this.grid = grid;
        this.moves = 0;
    }
    
    // getters
    public Box[][] getGrid() {
        return this.grid;
    }
    
    public int getMoves() {
        return this.moves;
    }
    
    /**
    * Inputs: None
    * Outputs: void
    * Description: adds 1 to moves
    */
    public void addOneMove() {
        this.moves++;
    }
    
    /**
    * Inputs: name of file with initial input table (String)
    * Outputs: 6x6 int array with input table values (int[][])
    * Description: reads in the filename from the command line argument and creates
                   a 2D array containing the values of the input table
    */
    public static int[][] readInFile(String inputTable) {
        In inStream = new In(inputTable);
        // check format of input file 
        In checkNumRow = new In(inputTable);
        String readAll = checkNumRow.readAll();
        // count how many new lines there are
        int newlineChar = 0;
        for (int i = 0; i < readAll.length(); i++) {
            char c = readAll.charAt(i);
            // character must be a digit 1-6, ' ', or '\n'
            if (c != '1' && c != '2' && c != '3' && c != '4' &&
                c != '5' && c != '6' && c != ' ' && c != '\n') {
                throw new IllegalArgumentException("Invalid character");
            }
            if (c == '\n') {
                newlineChar++;
            }
        }
        if (readAll.charAt(readAll.length() - 1) != '\n') {
            newlineChar++;
        } 
        // file should have 6 total lines (rows)
        if (newlineChar != 6) {
            throw new IllegalArgumentException("More than 6 rows");
        }
        In checkRow = new In(inputTable);
        for (int j = 0; j < newlineChar; j++) {
            String row = checkRow.readLine();
            // each row should have exactly 6 characters
            if (row.length() != 6) {
                throw new IllegalArgumentException("Row length is not 6");
            }
        } 
        
        // fill in values of 6 x 6 array using input file
        int[][] intArray = new int[6][6];
        for (int i = 0; i < 6; i++) {
            String line = inStream.readLine();
            for (int j = 0; j < line.length(); j++) {
                String oneChar = line.substring(j, j + 1);
                char c = oneChar.charAt(0);
                if (c == 32) {
                    intArray[i][j] = 0;
                }
                else {
                    intArray[i][j] = Integer.parseInt(oneChar);
                }
                
            }
        }
        
        // No row should have two of the same number
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int compareToValue = intArray[i][j];
                for (int k = 0; k < 6; k++) {
                    if (j != k && compareToValue == intArray[i][k] && 
                        intArray[i][j] != 0) {
                        throw new IllegalArgumentException("Row has repeat #s");
                     }  
                 }
                
            }
        }
        
        // No column should have two of the same number
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int compareToValue = intArray[j][i];
                for (int k = 0; k < 6; k++) {
                    if (j != k && compareToValue == intArray[k][i] && 
                        intArray[j][i] != 0) {
                        throw new IllegalArgumentException("Column has repeat #s");
                     }  
                 }
                
            }
        }
        
        // No box should have two of the same number
        for (int i = 0; i < 6; i += 2) {
            for (int j = 0; j < 3; j++) {
                int compareToValue = intArray[i][j];
                for (int k = 0; k < 3; k++) {
                    if (j != k && (compareToValue == intArray[i][k] || 
                        compareToValue == intArray[i + 1][k]) && 
                        intArray[i][j] != 0) {
                        throw new IllegalArgumentException("Box has repeat #s");
                        }
                    }
                }
        }
        for (int i = 0; i < 6; i += 2) {
            for (int j = 3; j < 6; j++) {
                int compareToValue = intArray[i][j];
                for (int k = 3; k < 6; k++) {
                    if (j != k && (compareToValue == intArray[i][k] || 
                        compareToValue == intArray[i + 1][k]) && 
                        intArray[i][j] != 0) {
                        throw new IllegalArgumentException("Box has repeat #s");
                        }
                    }
                }
        }
       
        return intArray;
    }
    
    
    /**
    * Inputs: name of file with initial input table (String)
    * Outputs: void
    * Description: calls readInFile() on the file with the initial input table and
                   creates a SudokuGrid object from the 2D int array. Draws the 
                   sudoku puzzle and animates the gameplay.
    */
    public static void drawGrid(String inputTable) {
        PennDraw.enableAnimation(30);
        int[][] sudokuGridIntArray = readInFile(inputTable);
        
        SudokuGrid gameBoard = new SudokuGrid(sudokuGridIntArray);
        
        // set scales to align with int array indices
        PennDraw.setXscale(-.5, 6.5);
        PennDraw.setYscale(6.5, -.5);
        
        
        
        while (gameBoard.isSolved() == false) {
            drawBackground();
            // draw numbers in correct positions
            gameBoard.drawArray();
            // keep track of the number of moves
            PennDraw.setFontSize(16);
            PennDraw.setPenColor(PennDraw.BLACK);
            PennDraw.text(0.2, 6.3, "Moves: " + gameBoard.getMoves());
            
            // check for and flag boxes with repeat numbers
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                    if (gameBoard.getGrid()[i][j].checkBox()) {
                        gameBoard.getGrid()[i][j].colorBox(j * 3, i * 2);
                    }
                }
            }
            
            // check for and flag rows with repeat numbers
            for (int i = 0; i < 3; i++) {
                if (gameBoard.checkRow1(i)) {
                    gameBoard.colorRow(i * 2);
                }
                if (gameBoard.checkRow2(i)) {
                    gameBoard.colorRow(i * 2 + 1);
                }
            }
            
            // check for and flag columns with repeat numbers
            for (int i = 0; i < 2; i++) {
                if (gameBoard.checkCol1(i)) {
                    gameBoard.colorCol(i * 3);
                }
                if (gameBoard.checkCol2(i)) {
                    gameBoard.colorCol(i * 3 + 1);
                } 
                if (gameBoard.checkCol3(i)) {
                    gameBoard.colorCol(i * 3 + 2);
                }
            }
            
            // check if user has typed a key
            if (PennDraw.hasNextKeyTyped()) {
                String s = "";
                char c = PennDraw.nextKeyTyped();
                s += c;
                // only if the key is a number 1-6, do the following
                if (c == '1' || c == '2' || c == '3' || 
                    c == '4' || c == '5' || c == '6') {
                    int digit = Integer.parseInt(s);
                    int xPosition = 0;
                    int yPosition = 0;
                    // if mouse is outside the borders of the board, do nothing
                    if (PennDraw.mouseX() < 0 || PennDraw.mouseX() > 6) {
                        xPosition = -1;
                    }
                    else {
                        xPosition = (int) (PennDraw.mouseX());
                    }
                    // if mouse is outside the borders of the board, do nothing
                    if (PennDraw.mouseY() < 0 || PennDraw.mouseY() > 6) {
                        yPosition = -1;
                    }
                    else {
                        yPosition = (int) (PennDraw.mouseY());
                    }
                    
                    // Box object used to update number depending on mouse position
                    Box findMouse = new Box();
                    
                    // row 1, column 1
                    if (xPosition >= 0 && xPosition < 3 && 
                        yPosition >= 0 && yPosition < 2) {
                        findMouse = gameBoard.getGrid()[0][0];
                        if (xPosition == 0 && yPosition == 0) {
                            findMouse.getBox()[0][0].setValue(digit);
                        }
                        else if (xPosition == 1 && yPosition == 0) {
                            findMouse.getBox()[0][1].setValue(digit);
                        }
                        else if (xPosition == 2 && yPosition == 0) {
                            findMouse.getBox()[0][2].setValue(digit);
                        }
                        else if (xPosition == 0 && yPosition == 1) {
                            findMouse.getBox()[1][0].setValue(digit);
                        }
                        else if (xPosition == 1 && yPosition == 1) {
                            findMouse.getBox()[1][1].setValue(digit);
                        }
                        else {
                            findMouse.getBox()[1][2].setValue(digit);
                        }
                        gameBoard.addOneMove();
                    }
                    // row 1, column 2
                    else if (xPosition >= 3 && xPosition < 6 && 
                        yPosition >= 0 && yPosition < 2) {
                        findMouse = gameBoard.getGrid()[0][1];
                        if (xPosition == 3 && yPosition == 0) {
                            findMouse.getBox()[0][0].setValue(digit);
                        }
                        else if (xPosition == 4 && yPosition == 0) {
                            findMouse.getBox()[0][1].setValue(digit);
                        }
                        else if (xPosition == 5 && yPosition == 0) {
                            findMouse.getBox()[0][2].setValue(digit);
                        }
                        else if (xPosition == 3 && yPosition == 1) {
                            findMouse.getBox()[1][0].setValue(digit);
                        }
                        else if (xPosition == 4 && yPosition == 1) {
                            findMouse.getBox()[1][1].setValue(digit);
                        }
                        else {
                            findMouse.getBox()[1][2].setValue(digit);
                        }
                        gameBoard.addOneMove();
                    }
                    // row 2, column 1
                    else if (xPosition >= 0 && xPosition < 3 && 
                        yPosition >= 2 && yPosition < 4) {
                        findMouse = gameBoard.getGrid()[1][0];
                        if (xPosition == 0 && yPosition == 2) {
                            findMouse.getBox()[0][0].setValue(digit);
                        }
                        else if (xPosition == 1 && yPosition == 2) {
                            findMouse.getBox()[0][1].setValue(digit);
                        }
                        else if (xPosition == 2 && yPosition == 2) {
                            findMouse.getBox()[0][2].setValue(digit);
                        }
                        else if (xPosition == 0 && yPosition == 3) {
                            findMouse.getBox()[1][0].setValue(digit);
                        }
                        else if (xPosition == 1 && yPosition == 3) {
                            findMouse.getBox()[1][1].setValue(digit);
                        }
                        else {
                            findMouse.getBox()[1][2].setValue(digit);
                        }
                        gameBoard.addOneMove();
                    }
                    // row 2, column 2
                    else if (xPosition >= 3 && xPosition < 6 && 
                        yPosition >= 2 && yPosition < 4) {
                        findMouse = gameBoard.getGrid()[1][1];
                        if (xPosition == 3 && yPosition == 2) {
                            findMouse.getBox()[0][0].setValue(digit);
                        }
                        else if (xPosition == 4 && yPosition == 2) {
                            findMouse.getBox()[0][1].setValue(digit);
                        }
                        else if (xPosition == 5 && yPosition == 2) {
                            findMouse.getBox()[0][2].setValue(digit);
                        }
                        else if (xPosition == 3 && yPosition == 3) {
                            findMouse.getBox()[1][0].setValue(digit);
                        }
                        else if (xPosition == 4 && yPosition == 3) {
                            findMouse.getBox()[1][1].setValue(digit);
                        }
                        else {
                            findMouse.getBox()[1][2].setValue(digit);
                        }
                        gameBoard.addOneMove();
                    }
                    // row 3, column 1
                    else if (xPosition >= 0 && xPosition < 3 && 
                        yPosition >= 4 && yPosition < 6) {
                        findMouse = gameBoard.getGrid()[2][0];
                        if (xPosition == 0 && yPosition == 4) {
                            findMouse.getBox()[0][0].setValue(digit);
                        }
                        else if (xPosition == 1 && yPosition == 4) {
                            findMouse.getBox()[0][1].setValue(digit);
                        }
                        else if (xPosition == 2 && yPosition == 4) {
                            findMouse.getBox()[0][2].setValue(digit);
                        }
                        else if (xPosition == 0 && yPosition == 5) {
                            findMouse.getBox()[1][0].setValue(digit);
                        }
                        else if (xPosition == 1 && yPosition == 5) {
                            findMouse.getBox()[1][1].setValue(digit);
                        }
                        else {
                            findMouse.getBox()[1][2].setValue(digit);
                        }
                        gameBoard.addOneMove();
                    }
                    // row 3, column 2
                    else if (xPosition >= 3 && xPosition < 6 && 
                        yPosition >= 4 && yPosition < 6) {
                        findMouse = gameBoard.getGrid()[2][1];
                        if (xPosition == 3 && yPosition == 4) {
                            findMouse.getBox()[0][0].setValue(digit);
                        }
                        else if (xPosition == 4 && yPosition == 4) {
                            findMouse.getBox()[0][1].setValue(digit);
                        }
                        else if (xPosition == 5 && yPosition == 4) {
                            findMouse.getBox()[0][2].setValue(digit);
                        }
                        else if (xPosition == 3 && yPosition == 5) {
                            findMouse.getBox()[1][0].setValue(digit);
                        }
                        else if (xPosition == 4 && yPosition == 5) {
                            findMouse.getBox()[1][1].setValue(digit);
                        }
                        else {
                            findMouse.getBox()[1][2].setValue(digit);
                        }
                        gameBoard.addOneMove();
                    }
                    // mouse is not within border of the grid
                    else {
                        continue;
                    }
                }
            }
            // Clicking reset will set board to initial input values
            if (PennDraw.mousePressed() && PennDraw.mouseX() > 5.6 && 
               PennDraw.mouseX() < 6.4 && PennDraw.mouseY() > 6.05 &&
               PennDraw.mouseY() < 6.45) {
                SudokuGrid initialBoard = new SudokuGrid(sudokuGridIntArray);
                gameBoard = initialBoard;
            }
            
            PennDraw.advance();    
        }
        
        PennDraw.disableAnimation();
        
        // final frame
        drawBackground();
        gameBoard.drawArray();
        PennDraw.setFontSize(16);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.text(0.2, 6.3, "Moves: " + gameBoard.getMoves());
        printWinningMessage();
        PennDraw.text(3, 3, "Total moves: " + gameBoard.getMoves());
        
    }
    
    /**
    * Inputs: None
    * Outputs: void
    * Description: draws background
    */
    public static void drawBackground() {
        // grey background
        PennDraw.setPenColor(164, 164, 164);
        PennDraw.filledRectangle(3, 3, 3.5, 3.5);
        // navy-colored board
        PennDraw.setPenColor(8, 18, 84);
        PennDraw.filledRectangle(3, 3, 3, 3);
        // white lines
        PennDraw.setPenColor(PennDraw.WHITE);
        for (int i = 0; i <= 6; i++) {
            // bold lines outlining boxes
            if (i == 0 || i == 3 || i == 6) {
                PennDraw.setPenRadius(.025);
            }
            else {
                PennDraw.setPenRadius(.01);
            }
            PennDraw.line(i, 0, i, 6);
        }
        for (int j = 0; j <= 6; j++) {
            // bold lines outlining boxes
            if (j == 0 || j == 2 || j == 4 || j == 6) {
                PennDraw.setPenRadius(.025);
            }
            else {
                PennDraw.setPenRadius(.01);
            }
            PennDraw.line(0, j, 6, j);
        }
        // Reset button
        PennDraw.setPenColor(255, 0, 0, 150);
        PennDraw.filledRectangle(6, 6.3, .4, .15);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.setFontSize(14);
        PennDraw.text(6, 6.3, "RESET");
    }
    
    /**
    * Inputs: None
    * Outputs: void
    * Description: prints the numbers contained in a SudokuGrid object by iterating
    * through each element, getting the integer at that index (value of BoxValue),
    * and using PennDraw.text() to draw it
    * NOTE: calls .drawArray() on each Box object in grid array
    */
    public void drawArray() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                this.getGrid()[i][j].drawArray(j * 3 + 0.5, i * 2 + 0.5);
            }
        }
    }
    
    /**
    * Inputs: row (int)
    * Outputs: true/false (boolean)
    * Description: creates an int array of the values in one row of the grid and
                   returns true if the row contains 2 of the same number
    * NOTE: used to check rows 1, 3, and 5               
    */
    public boolean checkRow1(int row) {
        int[] row1 = new int[6];
        for (int i = 0; i < 3; i++) {
            Box box = this.getGrid()[row][0];
            row1[i] = box.getBox()[0][i].getValue();
        }
        for (int i = 3; i < 6; i++) {
            Box box = this.getGrid()[row][1];
            row1[i] = box.getBox()[0][i - 3].getValue();
        }
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                if (row1[i] == row1[j] && row1[i] != 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * Inputs: row (int)
    * Outputs: true/false (boolean)
    * Description: creates an int array of the values in one row of the grid and
                   returns true if the row contains 2 of the same number
    * NOTE: used to check rows 2, 4, and 6               
    */
    public boolean checkRow2(int row) {
        int[] row2 = new int[6];
        for (int i = 0; i < 3; i++) {
            Box box = this.getGrid()[row][0];
            row2[i] = box.getBox()[1][i].getValue();
        }
        for (int i = 3; i < 6; i++) {
            Box box = this.getGrid()[row][1];
            row2[i] = box.getBox()[1][i - 3].getValue();
        }
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                if (row2[i] == row2[j] && row2[i] != 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * Inputs: row (int)
    * Outputs: void
    * Description: highlights the problem row pink             
    */
    public void colorRow(int row) {
        PennDraw.setPenColor(252, 95, 235, 100);
        PennDraw.filledRectangle(3, row + 0.5, 3, 0.5);
    }
    
    /**
    * Inputs: column (int)
    * Outputs: true/false (boolean)
    * Description: creates an int array of the values in one column of the grid and
                   returns true if the column contains 2 of the same number
    * NOTE: used to check columns 1 and 3              
    */
    public boolean checkCol1(int col) {
        int[] col1 = new int[6];
        for (int i = 0; i < 2; i++) {
            Box box = this.getGrid()[0][col];
            col1[i] = box.getBox()[i][0].getValue();
        }
        for (int i = 2; i < 4; i++) {
            Box box = this.getGrid()[1][col];
            col1[i] = box.getBox()[i - 2][0].getValue();
        }
        for (int i = 4; i < 6; i++) {
            Box box = this.getGrid()[2][col];
            col1[i] = box.getBox()[i - 4][0].getValue();
        }
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                if (col1[i] == col1[j] && col1[i] != 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * Inputs: column (int)
    * Outputs: true/false (boolean)
    * Description: creates an int array of the values in one column of the grid and
                   returns true if the column contains 2 of the same number
    * NOTE: used to check columns 2 and 4               
    */
    public boolean checkCol2(int col) {
        int[] col2 = new int[6];
        for (int i = 0; i < 2; i++) {
            Box box = this.getGrid()[0][col];
            col2[i] = box.getBox()[i][1].getValue();
        }
        for (int i = 2; i < 4; i++) {
            Box box = this.getGrid()[1][col];
            col2[i] = box.getBox()[i - 2][1].getValue();
        }
        for (int i = 4; i < 6; i++) {
            Box box = this.getGrid()[2][col];
            col2[i] = box.getBox()[i - 4][1].getValue();
        }
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                if (col2[i] == col2[j] && col2[i] != 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * Inputs: column (int)
    * Outputs: true/false (boolean)
    * Description: creates an int array of the values in one column of the grid and
                   returns true if the column contains 2 of the same number
    * NOTE: used to check columns 3 and 6               
    */
    public boolean checkCol3(int col) {
        int[] col3 = new int[6];
        for (int i = 0; i < 2; i++) {
            Box box = this.getGrid()[0][col];
            col3[i] = box.getBox()[i][2].getValue();
        }
        for (int i = 2; i < 4; i++) {
            Box box = this.getGrid()[1][col];
            col3[i] = box.getBox()[i - 2][2].getValue();
        }
        for (int i = 4; i < 6; i++) {
            Box box = this.getGrid()[2][col];
            col3[i] = box.getBox()[i - 4][2].getValue();
        }
        for (int i = 0; i < 6; i++) {
            for (int j = i + 1; j < 6; j++) {
                if (col3[i] == col3[j] && col3[i] != 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
    * Inputs: column (int)
    * Outputs: void
    * Description: highlights the problem column yellow             
    */
    public void colorCol(int col) {
        PennDraw.setPenColor(255, 217, 87, 100);
        PennDraw.filledRectangle(col + 0.5, 3, 0.5, 3);
    }
    
    /**
    * Inputs: none
    * Outputs: true/false (boolean)
    * Description: returns true if the puzzle is solved (no repeat numbers in any
                   row/column/box and all squares filled in)             
    */
    public boolean isSolved() {
        boolean filledIn = true;
        boolean columns = true;
        boolean rows = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (this.getGrid()[i][j].hasZero()) {
                    filledIn = false;
                }
            }
        }
        
        for (int i = 0; i < 3; i++) {
            if (this.checkRow1(i)) {
                rows = false;
            }
            if (this.checkRow2(i)) {
                rows = false;
            }
        }
        
        for (int i = 0; i < 2; i++) {
            if (this.checkCol1(i)) {
                columns = false;
            }
            if (this.checkCol2(i)) {
                columns = false;
            } 
            if (this.checkCol3(i)) {
                columns = false;
            }
        }
        return filledIn && columns && rows;
    }
    
    /**
    * Inputs: none
    * Outputs: void
    * Description: prints <<You Win>> after puzzle is solved            
    */
    public static void printWinningMessage() {
        PennDraw.setPenColor(108, 117, 137, 100);
        PennDraw.filledRectangle(3, 3, 3.5, 3.5);
        PennDraw.setFontSize(50);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.text(3, 2, "<<You Win>>");
        
    }
}