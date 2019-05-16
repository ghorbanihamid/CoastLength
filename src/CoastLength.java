import java.io.*;
import java.util.Scanner;

/**
 * Created by Hamid Ghorbani on 8/27/15.
 */

public class CoastLength {

    static String fileName = "CoastLength.txt";
    static String fileData[];

    static final int water = 0;
    static final int land = 1;
    static final int sea = 2;

    private static int rowCount = 0;
    private static int colCount = 0;

    private int[][] squareList;

    CoastLength(int rowCount, int colCount, int[][] inputList) {

        this.rowCount = rowCount;
        this.colCount = colCount;
        squareList = new int[rowCount][colCount];
        this.squareList = inputList;
    }

    /*
     * Calculates Coast Length
     * For each Data in the squareList, if the square is water , don't have any coast line, so leave it
     * if the square is land, then we need to check borders of it, to see if that border is a coast line or not
     * if one border is water, then we need to see that border is lake or not.
     * if that border is lake, so it is not coast line.
     */
    public int calculateCoastLength() {

        int sumCoastLine = 0;
        for(int rowIndex = 0; rowIndex < rowCount; rowIndex++){
            for(int colIndex = 0; colIndex < colCount; colIndex++) {

                if(squareList[rowIndex][colIndex] == land) {  // then must check borders of the square

                    int leftColIndex = colIndex - 1;
                    // check left side
                    if( leftColIndex < 0)                // means left side is sea
                        sumCoastLine += 1;
                    else if(squareList[rowIndex][leftColIndex] == water && !isLake(rowIndex,leftColIndex))
                        sumCoastLine += 1;

                    // check right side
                    int rightColIndex = colIndex + 1;
                    if(rightColIndex > colCount)        // means right side is sea
                        sumCoastLine += 1;
                    else if(squareList[rowIndex][rightColIndex] == water && !isLake(rowIndex,rightColIndex))
                        sumCoastLine += 1;

                    // check upper side
                    int leftRowIndex = rowIndex - 1;
                    if(leftRowIndex < 0)               // means upper side is sea
                        sumCoastLine += 1;
                    else if(squareList[leftRowIndex][colIndex] == water && !isLake(leftRowIndex,colIndex))
                        sumCoastLine += 1;

                    // check bottom side
                    int rightRowIndex = rowIndex + 1;
                    if(rightRowIndex > rowCount)       // means bottom side is sea
                        sumCoastLine += 1;
                    else if(squareList[rightRowIndex][colIndex] == water && !isLake(rightRowIndex,colIndex))
                        sumCoastLine += 1;
                }
            }
        }
        return sumCoastLine;
    }

    /*
     * This function checks the borders of a square, to find it is lake or not
     * for being lake, all borders of that square must be land
     * if one side or more is water, then it is sea not lake.
     */
    private boolean isLake(int rowIndex, int colIndex) {

        if(squareList[rowIndex][colIndex] != water){       // the square must be water, if it's not, then not lake
            return false;
        }
        int leftSideResult   = checkLeftSideOfSquare(rowIndex, colIndex);
        if(leftSideResult == sea){
            return false;
        }
        int rightSideResult  = checkRightSideOfSquare(rowIndex, colIndex);
        if(rightSideResult == sea){
            return false;
        }

        int upperSideResult  = checkUpperSideOfSquare(rowIndex, colIndex);
        if(upperSideResult == sea){
            return false;
        }

        int bottomSideResult = checkBottomSideOfSquare(rowIndex, colIndex);
        if(bottomSideResult == sea){
            return false;
        }

        // if all four side a (square) water is land, then it is lake
        if(leftSideResult == land && rightSideResult == land && upperSideResult == land && bottomSideResult == land){
            return true;
        }
        return false;

    }

    /*
     * This recursive function checks left side of given Coordinate
     * It checks left side until to see land or see
     * Then the result is land or sea
     * if the result is sea it means the main square is not lake
     */
    private int checkLeftSideOfSquare(int rowIndex, int colIndex) {

        int minColIndex = 0;
        if(colIndex - 1 < minColIndex) {
            return sea;
        }
        if(squareList[rowIndex][colIndex - 1] == land) {
            return land;
        }
        return checkLeftSideOfSquare(rowIndex, colIndex - 1);
    }

    /*
    * This recursive function checks right side of given Coordinate
    * It checks right side until to see land or see
    * Then the result is land or sea
    * if the result is sea it means the main square is not lake
    */
    private int checkRightSideOfSquare(int rowIndex, int colIndex) {

        int maxColIndex = colCount - 1;
        if(colIndex + 1 > maxColIndex) {
            return sea;
        }
        if(squareList[rowIndex][colIndex + 1] == land) {
            return land;
        }
        return checkRightSideOfSquare(rowIndex, colIndex + 1);
    }

    /*
    * This recursive function checks upper side of given Coordinate
    * It checks upper side until to see land or see
    * Then the result is land or sea
    * if the result is sea it means the main square is not lake
    */
    private int checkUpperSideOfSquare(int rowIndex, int colIndex) {

        int minRowIndex = 0;
        if(rowIndex - 1 < minRowIndex) {
            return sea;
        }

        if(squareList[rowIndex - 1][colIndex] == land) {
            return land;
        }

        return checkUpperSideOfSquare(rowIndex - 1, colIndex);
    }

    /*
    * This recursive function checks bottom side of given Coordinate
    * It checks bottom side until to see land or see
    * Then the result is land or sea
    * if the result is sea it means the main square is not lake
    */
    private int checkBottomSideOfSquare(int rowIndex, int colIndex) {

        int maxRowIndex = rowCount - 1;

        if(rowIndex + 1 > maxRowIndex) {
            return sea;
        }

        if(squareList[rowIndex + 1][colIndex] == land) {
            return land;
        }
        return checkBottomSideOfSquare(rowIndex + 1, colIndex);
    }


    public static int[][] getDataFromScanner1() {

        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter $N$ and $M$ :\n");

        int rowCount = reader.nextInt();
        int colCount = reader.nextInt();

        if(rowCount < 1){
            System.out.println("$N$ error.\n");
            return null;
        }
        if(colCount < 1){
            System.out.println("$M$ error.\n");
            return null;
        }

        int[][] inputList = new int[rowCount][colCount];

        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < colCount; j++) {
                System.out.println("Please input data [" + (i + 1) + "][" + (j + 1) + "]");
                inputList[i][j] = reader.nextInt();
            }
            System.out.println("");
        }
        return inputList;
    }

    public static int[][] getDataFromScanner() {


        Scanner sc = new Scanner(System.in);
        String lineData = sc.nextLine();
        lineData = lineData.trim();
        int spacePos = lineData.indexOf(' ');
        rowCount = Integer.parseInt(lineData.substring(0,spacePos).trim());
        colCount = Integer.parseInt(lineData.substring(spacePos, lineData.length()).trim());
        if(rowCount < 1){
            return null;
        }
        if(colCount > 1000){
            return null;
        }
        String[] lineDataList = new String[rowCount];
        for(int i = 0; i < rowCount; i++){
            lineData = sc.nextLine();
            if(lineData.trim().length()!= colCount)
                return null;
            lineDataList[i] = lineData.trim();
        }
        int[][] inputList = new int[rowCount][colCount];
        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < colCount; j++) {
                lineData = lineDataList[i];
                if(j < lineData.length()){
                    inputList[i][j] = Character.getNumericValue(lineData.charAt(j));
                }
            }
        }
        return inputList;
    }

    /*
    * Reads data from files
    */
    static int[][] readDataFromFile() {

        FileReader fileReader = null;
        try {
            LineNumberReader lnr = new LineNumberReader(new FileReader(new File(fileName)));
            lnr.skip(Long.MAX_VALUE);
            int lineNumbers = lnr.getLineNumber() + 1;

            fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String lineContent;
            fileData = new String[lineNumbers];
            for ( int i = 0; i < lineNumbers; i++) {
                lineContent = bufferedReader.readLine();
                if(lineContent == null)
                    break;
                fileData[i] = lineContent;
            }
            String lineData = fileData[0];
            int spaceIndex = lineData.indexOf(" ");
            rowCount = Integer.parseInt(lineData.substring(0,spaceIndex).trim());
            colCount = Integer.parseInt(lineData.substring(spaceIndex, lineData.length()).trim());

            if(lineNumbers - 1 != rowCount){
                System.out.println("Invalid file Lines!");
                return null;
            }
            int[][] inputList = new int[rowCount][colCount];
            for(int i =0; i < rowCount; i++){
                lineData = fileData[i + 1];
                for(int j = 0; j < colCount; j++) {
                    inputList[i][j] = Character.getNumericValue(lineData.charAt(j));
                }
            }
            return inputList;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fileReader != null)
                    fileReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {

//        int[][] inputList = readDataFromFile();
        int[][] inputList = getDataFromScanner();
        if(inputList == null){
            return;
        }
        CoastLength cl = new CoastLength(rowCount,colCount,inputList);
        System.out.println(cl.calculateCoastLength());
    }

}
