/**
 * Author: Sara Kenefick
 *
 * PennKey: sarak24
 *
 * Description: a class for each individual square of the sudoku puzzle. It's only
                field is the digit the square contains (integer). 
 *
 */

public class BoxValue {
    
    // fields
    private int value;
    private boolean isMutable;
    
    // constructor
    public BoxValue(int value) {
        this.value = value;
        this.isMutable = true;
        
    }
    
    // getters
    public int getValue() {
        return this.value;
    }
    
    public boolean getIsMutable() {
        return this.isMutable;
    }
    
    // setters
    public void setValue(int digit) {
        if (digit < 0 || digit > 6) {
            throw new IllegalArgumentException("Not a number between 1 and 6");
        }
        else if (this.getIsMutable() == false) {
            return;
        }
        else {
            this.value = digit;
        }
    }
    
    /**
    * Inputs: none
    * Outputs: void
    * Description: changes BoxValue's mutability property             
    */
    public void setToImmutable() {
        this.isMutable = false;
    }
    
    /**
    * Inputs: xPosition, yPosition (double)
    * Outputs: void
    * Description: gets value of BoxValue and draws it at the indicated position
    */
    public void drawDigit(double row, double col) {
        PennDraw.setFontSize(20);
        // if the value is not mutable, draw the inital value from input table
        if (this.getIsMutable() == false) {
            PennDraw.setPenColor(100, 100, 100);
            PennDraw.setFontBold();
            PennDraw.text(row, col, "" + this.getValue());
            return;
        }
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.setFontPlain();
        String stringDigit = integerToString(this.getValue());
        // draw nothing if the value is 0
        if (stringDigit.equals("0")) {
           PennDraw.text(row, col, " ");
        }
        // draw value (int) of the BoxValue object
        else {
           PennDraw.text(row, col, stringDigit);
        }
    }
    
    /**
    * Inputs: digit (int)
    * Outputs: String
    * Description: converts a digit (int) to a String representation              
    */
    private static String integerToString(int digit) {
        String stringDigit = "" + digit;
        return stringDigit;
    }
    
    /**
    * Inputs: none
    * Outputs: true/false (boolean)
    * Description: returns true if the value is zero            
    */
    public boolean isZero() {
        if (this.value == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
}