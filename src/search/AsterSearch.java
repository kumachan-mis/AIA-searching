package search;
import test.Map;
import java.util.Collections;

public class AsterSearch extends Search {

    public AsterSearch(int initCapacity) {
        super(initCapacity);
        System.out.println("***** A* Search *****");
    }

    private int childType(Square square) {
        int sx = square.getX(), sy = square.getY();
        int px =  presentSquare.getX(), py = presentSquare.getY();

        if(sx == px + 1 && sy == py) return 0;
        else if(sx == px && sy == py - 1) return 1;
        else if(sx == px - 1 && sy == py) return 2;
        else if(sx == px && sy == py + 1) return 3;
        return -1;
    }

    @Override
    void childrenProcess() {
        boolean[] isFound = {false, false, false, false};
        ListMember[] foundMember = new ListMember[4];

        for (ListMember open : openList) {
            int type = childType(open.getChild());
            if (type == -1) continue;
            isFound[type] = true;
            foundMember[type] = open;
        }

        Collections.shuffle(typeList);
        for(int index = 0; index < 4; ++index) {
            int type = typeList.get(index);

            if(isFound[type]) {
                replaceOpenList(foundMember[type], type);
                removeFromClosedList(foundMember[type], type);
            } else {
                forceToAddToOpenList(type);
            }
        }
    }

    private void replaceOpenList(ListMember open, int type) {
        Square child = open.getChild();

        if(!child.getAbleToVisit()) return;
        if (open.getCost() <= cost(presentSquare, child)) return;

        openList.remove(open);
        openList.add(new ListMember(child, presentSquare, cost(presentSquare, child), getPathSymbol(type)));
    }

    private void removeFromClosedList(ListMember open, int type) {
        Square child = open.getChild();
        int x = child.getX(), y = child.getY();

        if(Map.get(x, y) == '*' || child.getAbleToVisit()) return;
        if (open.getCost() <= cost(presentSquare, child)) return;

        child.reOpen();
        openList.add(new ListMember(child, presentSquare, cost(presentSquare, child), getPathSymbol(type)));
    }

    @Override
    double cost(Square parent, Square child) {
        return parent.getPath().length() + heuristicFunc(child.getX(), child.getY()) + 1.0;
    }

}