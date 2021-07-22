# CIS110-finalproject
/**********************************************************************
 * Project- SUDOKU 
 **********************************************************************/
 
 Name: Sara Kenefick
 PennKey: sarak24
 
 **********************************************************************
 
 INSTRUCTIONS:
 1. Run java SudokuGrid with a command line argument of the name of the file 
    containing the initial input table of the sudoku puzzle.
 2. Click anywhere to begin.
 3. Hover over any square and type a number 1-6 to enter/change the value.
     a. Inital values from the input table cannot be changed. These numbers are
        bolded and slightly darker.
     b. If the mouse is not within the boundaries of the grid or the key typed is
        not a number 1-6, nothing will happen.
     c. Highlighted rows/columns/boxes indicate problem areas (repeated numbers).
 4. A running total of the number of moves is kept in the bottom left corner.
 5. Press the red "RESET" button in the bottom right corner to reset the puzzle to 
    the initial input table values. This will also reset the moves to 0. 
 6. When the puzzle is solved, a message will pop up and show the total number of
    moves it took to solve it. 
 
 **********************************************************************
 
 Additional features:
 1. RESET button- resets the puzzle to the initial input table values.
     a. Helpful if the board has become too messy with highlighted problem areas.
 2. Moves counter- counts the number of moves it takes to solve the puzzle. 
     a. Be careful! Even if you type a number that matches the number already in 
        that box, the number of moves will increment by one. It will also increment
        if you try to change an immutable number.
     b. Typing any key other than a number 1-6 or typing a key when the mouse is 
        outside the boundaries of the grid will NOT increase the # of moves.
 3. Winning message- in addition to a congratulatory message, it also prints the 
    the total number of moves.
 
 **********************************************************************
 
 Required files (3): 
 1. BoxValue.java- represents each square of the 6x6 sudoku puzzle. Each square has  
    a value and can either be mutable/immutable. The value can be changed as long 
    as the input argument is a digit 1-6, and the mutability property can be made 
    false. Initially filled-in squares are made immutable. Also, each value can
    be drawn at the appropriate position on the grid. 
 2. Box.java- represents one of the six 2x3 boxes of the sudoku puzzle. Box objects 
    are 2D arrays of BoxValue objects and have only one field (BoxValue[][] box).
    The drawArray() method draws the values of all the BoxValue elements and is 
    called in the drawArray() method of SudokuGrid on each box. A box can be checked
    for repeat numbers and can be colored if it does. 
 3. SudokuGrid.java- represents the entire 6x6 puzzle and is responsible for running
    the program using the filename from the command line argument. After reading in
    the file (and making sure the file is formatted correctly), it creates a 
    SudokuGrid object that represents the sudoku board in real time. It can handle 
    updating values, highlighting rows/columns/boxes, and resetting the board. 
    In addition to its Box[][] grid field, it also has a int moves field to tally 
    the total number of moves it takes to solve the puzzle. At he end when the 
    puzzle is solved, a winning message is displayed. 
 
 ***Each file also contains a description of its purpose/function at the top***
 
 **********************************************************************
