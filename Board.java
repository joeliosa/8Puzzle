import com.sun.jdi.connect.Connector;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private int m;
    private int twodimlen;
    private int[] blocks;
    private int [][] input;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        input = tiles;
        blocks = new int[tiles.length * tiles.length];
        m = blocks.length;
        //creating 1D version of tiles
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles.length; j++){
                blocks[(i * tiles.length) + j] = tiles[i][j];
            }
        }
        twodimlen = Math.toIntExact(Math.round(Math.sqrt(blocks.length)));
    }

    // string representation of this board
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(twodimlen + "\n");
        for (int k = 0; k < m;) {
            for (int t = 0; t < twodimlen; t++) {
                s.append(String.format("%2d ", blocks[k]));
                k++;
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension(){
        return twodimlen;
    }

    private int zeroIndex(){
        int zeronumber = 0;
        for (int h = 0; h < m; h++)
            if (blocks[h] == 0) {
                zeronumber = h;
                break;
            }
        return zeronumber;
    }

    private void swap(int first, int second){

    }

    // number of tiles out of place
    public int hamming(){
        int outofplace = 0;
        for (int k = 0; k < m; k++)
            if (blocks[k] != k + 1 && blocks[k] != 0) outofplace++;
        return outofplace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int manhattandist = 0;
        for (int y = 0; y < blocks.length; y++){
            if (blocks[y] != 0) {
                double a = Math.floor(Double.valueOf((blocks[y] - 1) / twodimlen));
                double b = Math.floor(Double.valueOf(y / twodimlen));
                long firstnum = Math.round(Math.abs(a - b));
                int firstnumber = Math.toIntExact(firstnum);
                int secondnumber = Math.abs(((blocks[y] - 1) % twodimlen) - (y % twodimlen));
                int difference = firstnumber + secondnumber;
                manhattandist += difference;
            }
        }
        return manhattandist;
    }

    // is this board the goal board?
    public boolean isGoal(){
        boolean goal = true;
        for (int i = 0; i < m - 1;)
            if (blocks[i] == i + 1) i++;
            else {
                goal = false;
                break;
            }
        return goal;
    }

    // does this board equal y?
    public boolean equals(Object y){
        //if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        return Arrays.equals(this.blocks, that.blocks); //but does this compare each element of the boards, as a for loop would?
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        Stack<Board> neighs = new Stack<>();
        if (zeroIndex() + 1 <= m - 1) { // switch for BLOCK TO RIGHT
            Board copy = new Board(input); // copy of board we're working with
            int firstvar = copy.blocks[zeroIndex() + 1];
            int secondvar = copy.blocks[zeroIndex()];
            copy.blocks[zeroIndex()] = firstvar;
            copy.blocks[zeroIndex() + 1] = secondvar;
            neighs.push(copy);
        }
        if (zeroIndex() - 1 >= 0) { // BLOCK TO LEFT
            Board copy = new Board(input);
            int firstvar = copy.blocks[zeroIndex() - 1];
            int secondvar = copy.blocks[zeroIndex()];
            copy.blocks[zeroIndex()] = firstvar;
            copy.blocks[zeroIndex() - 1] = secondvar;
            neighs.push(copy);
        }
        if (zeroIndex() + twodimlen <= m - 1) { // BLOCK BELOW
            Board copy = new Board(input);
            int firstvar = copy.blocks[zeroIndex() + twodimlen];
            int secondvar = copy.blocks[zeroIndex()];
            copy.blocks[zeroIndex()] = firstvar;
            copy.blocks[zeroIndex() + twodimlen] = secondvar;
            neighs.push(copy);
        }
        if (zeroIndex() - twodimlen >= 0) { // BLOCK ABOVE
            Board copy = new Board(input);
            int firstvar = copy.blocks[zeroIndex() - twodimlen];
            int secondvar = copy.blocks[zeroIndex()];
            copy.blocks[zeroIndex()] = firstvar;
            copy.blocks[zeroIndex() - twodimlen] = secondvar;
            neighs.push(copy);
        }
        return neighs;
    }

    // a board that is obtained by exchanging any pair of tiles. just returns single one of these. 
    public Board twin(){
        Random first = new Random();
        int firstint = first.nextInt(m);
        while (firstint == zeroIndex()){
            Random newnumber = new Random();
            firstint = newnumber.nextInt(m);
        }
        Random second = new Random();
        int secondint = second.nextInt(m);
        while (secondint == zeroIndex() || secondint == firstint){
            Random newnumbah = new Random();
            secondint = newnumbah.nextInt(m);
        }
        Board twinboard = new Board(input);
        int firsttobeswitched = twinboard.blocks[firstint];
        int sectobeswitched = twinboard.blocks[secondint];
        twinboard.blocks[firstint] = sectobeswitched;
        twinboard.blocks[secondint] = firsttobeswitched;
        return twinboard;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int [][] benny = new int[3][3];
        benny[0][0] = 1;
        benny[0][1] = 4;
        benny[0][2] = 3;
        benny[1][0] = 5;
        benny[1][1] = 7;
        benny[1][2] = 8;
        benny[2][0] = 2;
        benny[2][1] = 0;
        benny[2][2] = 6;
        Board sampleboard = new Board(benny);
        //System.out.println(sampleboard.toString()); //works
        System.out.println(sampleboard.twin());
    }
}
