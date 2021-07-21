/**
 * Author: Sara Kenefick
 *
 * PennKey: sarak24
 *
 * Description: a class to represent each one of the six boxes of a sudoku puzzle.
                The lines around each box are bolded. No box should contain two
                of the same number when the puzzle is solved. 
 *
 */

public class Box {
    
    // field
    private BoxValue[][] box;
    
    // constructor
    public Box(int[][] intArray, int row, int column) {
        int rowIndex = row;
        BoxValue[][] boxValueArray = new BoxValue[2][3]; 
        for (int i = 0; i < 2; i++) {
            int columnIndex = column;
            for (int j = 0; j < 3; j++) {
                BoxValue oneBoxValue = new BoxValue(intArray[rowIndex][columnIndex]);
                if (oneBoxValue.getValue() != 0) {
                    oneBoxValue.setToImmutable();
                }
                boxValueArray[i][j] = oneBoxValue;
                columnIndex++; 
            }
            rowIndex++;
        }
        this.box = boxValueArray;
    }
    // method overloading, constructor without input argument
    public Box() {
        this.box = null;
    }
    
    // getters
    public BoxValue[][] getBox() {
        return this.box;
    }
    
    /**
    * Inputs: horizontal offset x, vertical offset y (double)
    * Outputs: void
    * Description: draws the numbers of the BoxValue elements of the Box object
    */
    public void drawArray(double x, double y) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
               this.getBox()[i][j].drawDigit(j + x, i + y);
            }
            
        }
    }
    
    /**
    * Inputs: none
    * Outputs: true/false (boolean)
    * Description: returns true if the box contains 2 of the same number            
    */
    public boolean checkBox() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int compareToValue = this.getBox()[i][j].getValue();
                for (int k = 0; k < 3; k++) {
                    if (i == 0 && j != k && 
                       (compareToValue == this.getBox()[i][k].getValue() ||
                       compareToValue == this.getBox()[i + 1][k].getValue() ||
                       compareToValue == this.getBox()[i + 1][j].getValue()) &&
                       this.getBox()[i][j].getValue() != 0) {
                       return true;
                    }
                    if (i == 1 && j != k && 
                       (compareToValue == this.getBox()[i][k].getValue() ||
                       compareToValue == this.getBox()[i - 1][k].getValue() ||
                       compareToValue == this.getBox()[i - 1][j].getValue()) &&
                       this.getBox()[i][j].getValue() != 0) {
                       return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
    * Inputs: horizontal offset x, vertical offset y (int)
    * Outputs: void
    * Description: highlights the problem box green             
    */
    public void colorBox(int x, int y) {
        PennDraw.setPenColor(87, 255, 138, 100);
        PennDraw.filledRectangle(x + 1.5, y + 1, 1.5, 1);
    }
    
    /**
    * Inputs: none
    * Outputs: true/false (boolean)
    * Description: returns false if all squares of the box are filled in            
    */
    public boolean hasZero() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.getBox()[i][j].isZero()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    /* used for testing
    public void arrayToString() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(this.box[i][j].getValue());
            }
            System.out.println("");
        }
    }
    */
}