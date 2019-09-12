import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private MinPQ minqueue;
    private int movesmade;
    public static class SearchNode implements Comparable<SearchNode>{
        private Board mainboard;
        private int numbermoves;
        private Board previousboard;

        SearchNode(Board first) {
            this.mainboard = first;
            this.numbermoves = 0;
            this.previousboard = null;
        }

        public int getPriority() { return mainboard.manhattan() + numbermoves;}

        public int compareTo(SearchNode s){ return this.getPriority() - s.getPriority(); }
    }
    SearchNode mainnode;

    /* patch of code to try if current comparator goes awry
    class CloseCompare implements Comparator<SearchNode> {
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.getPriority() < o2.getPriority()) return -1;
            if (o1.getPriority() > o2.getPriority()) return 1;
            else return 0;
        }
    } */

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("null argument to constructor");
        movesmade = 0;
        mainnode = new SearchNode(initial); // board/node we are going to be finding neighbors for
        Board goalboard = null;
        while (!mainnode.mainboard.isGoal()) {
            if (movesmade > 3) break;
            System.out.println("moves made: " + movesmade);
            System.out.println(mainnode.mainboard.toString());
            minqueue = new MinPQ();
            Iterator itr = mainnode.mainboard.neighbors().iterator();
            while (itr.hasNext()) { // add neighbors of board in question to PQ + defining neighbors' features
                Object element = itr.next();
                Board newboard = (Board) element;
                SearchNode newnode = new SearchNode(newboard);
                System.out.println(movesmade + " " + "neighbor: ");
                System.out.println(newnode.mainboard);
                newnode.previousboard = initial;
                minqueue.insert(newnode);
            }
            SearchNode toBeCompared = (SearchNode) minqueue.delMin();
            if (toBeCompared.mainboard.isGoal()) {
                goalboard = toBeCompared.mainboard;
                break;
            }
            else {
                mainnode = new SearchNode(toBeCompared.mainboard); // this board in question has tiles of original board but different block order. that might be problem.
                movesmade++;
            }
        }
        System.out.println(goalboard.toString());
        /*
        System.out.println("mainnode after 1 round: ");
        System.out.println(mainnode.mainboard.toString()); //works
        System.out.println("mainnode after modification: ");
        Iterator itr = mainnode.mainboard.neighbors().iterator();
        while (itr.hasNext()) { // add neighbors of board in question to PQ + defining neighbors' features
            Object element = itr.next();
            Board newboard = (Board) element;
            SearchNode newnode = new SearchNode(newboard);
            System.out.println("neighbor: ");
            System.out.println(newnode.mainboard);
            newnode.previousboard = initial;
            minqueue.insert(newnode);
        }
        SearchNode secondcompared = (SearchNode) minqueue.delMin();
        System.out.println("priority neighbor: " + secondcompared.mainboard); */
    }

    // min number of moves to solve initial board
    public int moves() {
        return movesmade;
    }

    /*
    // is the initial board solvable? (see below)
    public boolean isSolvable()

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
    }
    */
    // test client (see below)
    public static void main(String[] args) {
        int [][] benny = new int[3][3];
        benny[0][0] = 0;
        benny[0][1] = 1;
        benny[0][2] = 3;
        benny[1][0] = 4;
        benny[1][1] = 2;
        benny[1][2] = 5;
        benny[2][0] = 7;
        benny[2][1] = 8;
        benny[2][2] = 6;
        Board sampleboard = new Board(benny);
        Solver rufus = new Solver(sampleboard);
    }
}