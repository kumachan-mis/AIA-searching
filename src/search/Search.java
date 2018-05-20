package search;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import test.Map;

abstract class Search {
    private Square[][] sq = new Square[Map.W][Map.H];
    Square presentSquare;
    private Square start, goal;
    Queue<ListMember> openList;
    ArrayList<Integer> typeList = new ArrayList<>();

    public void doSearch() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long startCpuTime, endCpuTime;

        startCpuTime = threadMXBean.getCurrentThreadCpuTime();

        initOpenList();
        while (true) {
            try {
                setPresentSquare();
            } catch (NullPointerException e) {
                System.out.println("Search Failed");
                return;
            }
            if (isEnd()) break;
            addParentToClosedList();
            childrenProcess();
        }

        endCpuTime = threadMXBean.getCurrentThreadCpuTime();

        System.out.println("CPU Time: " + (endCpuTime - startCpuTime));
        System.out.println();
    }

    Search(int initCapacity) {
        for(int y = 0; y < Map.H; ++y) {
            for(int x = 0; x < Map.W; ++x) {
                setSq(x, y);
            }
        }
        openList = new PriorityQueue<ListMember>(initCapacity, new ListComparator());

        for(int type = 0; type < 4; ++type) {
            typeList.add(type);
        }
    }

    private void setSq(int x, int y) {
        sq[x][y] = new Square(x, y);
        switch (Map.get(x, y)) {
            case '*':
                sq[x][y].close();
                break;
            case 'S':
                start = sq[x][y];
                break;
            case 'G':
                goal = sq[x][y];
                break;
        }
    }

    private void initOpenList() {
        openList.clear();
        openList.add(new ListMember(start, null, heuristicFunc(start.getX(), start.getY()), ""));
    }

    private void setPresentSquare() throws NullPointerException{
        presentSquare = openList.poll().getChild();
    }

    private boolean isEnd() {
        boolean isEnd = presentSquare.equals(goal);
        if(isEnd) System.out.println(presentSquare.getPath());
        return isEnd;
    }

    private void addParentToClosedList() {
        presentSquare.close();
    }

    abstract void childrenProcess();

    void forceToAddToOpenList(int type) {
        int x = presentSquare.getX(), y = presentSquare.getY();
        switch (type) {
            case 0:
                x++;
                break;
            case 1:
                y--;
                break;
            case 2:
                x--;
                break;
            case 3:
                y++;
                break;
        }
        if (x < 0 || Map.W <= x || y < 0 || Map.H <= y) return;

        Square child = sq[x][y];
        if (child.getAbleToVisit())
            openList.add(new ListMember(child, presentSquare, cost(presentSquare, child), getPathSymbol(type)));
    }

    String getPathSymbol(int type) {
        switch (type) {
            case 0: return "R";
            case 1: return "U";
            case 2: return "L";
            case 3: return "D";
            default: return "";
        }
    }

    double heuristicFunc(int x, int y) {
        return Math.sqrt(
                (x - goal.getX())  * (x - goal.getX())
                        + (y - goal.getY()) * (y - goal.getY())
        );
    }

    abstract double cost(Square parent, Square child);
}
