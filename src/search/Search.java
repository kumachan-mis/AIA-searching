package search;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.PriorityQueue;
import java.util.Queue;
import test.Map;

abstract class Search {
    Square[][] sq = new Square[Map.W][Map.H];
    Square presentSquare;
    private Square start, goal;
    Queue<ListMember> openList;

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
        openList = new PriorityQueue<>(initCapacity, new ListComparator());
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

    void addParentToClosedList() {
        presentSquare.close();
    }

    abstract void childrenProcess();

    double heuristicFunc(int x, int y) {
        return (x - goal.getX())  * (x - goal.getX()) + (y - goal.getY()) * (y - goal.getY());
    }
}
