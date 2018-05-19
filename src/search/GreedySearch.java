package search;
import test.Map;

public class GreedySearch extends Search {

    public GreedySearch(int initCapacity) {
        super(initCapacity);
        System.out.println("***** Greedy Search *****");
    }

    private void addToOpenList(Square square, String str) {
        if(!square.getAbleToVisit()) return;
        openList.add(new ListMember(square, presentSquare, heuristicFunc(square.getX(), square.getY()), str));
    }

    @Override
    void childrenProcess() {
        int x = presentSquare.getX(), y = presentSquare.getY();

        if(x < Map.W - 1) addToOpenList(sq[x + 1][y], "R");

        if(y < Map.H - 1) addToOpenList(sq[x][y + 1], "D");

        if(0 < x) addToOpenList(sq[x - 1][y], "L");

        if(0 < y) addToOpenList(sq[x][y - 1], "U");
    }
}