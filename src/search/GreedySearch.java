package search;

import java.util.Collections;

public class GreedySearch extends Search {

    public GreedySearch(int initCapacity) {
        super(initCapacity);
        System.out.println("***** Greedy Search *****");
    }

    @Override
    void childrenProcess() {
        Collections.shuffle(typeList);
        for(int index = 0; index < 4; ++index) {
            forceToAddToOpenList(typeList.get(index));
        }
    }

    @Override
    double cost(Square parent, Square child) {
        return heuristicFunc(child.getX(), child.getY());
    }
}