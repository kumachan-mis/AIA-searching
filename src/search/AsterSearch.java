package search;
import test.Map;

public class AsterSearch extends Search {

    public AsterSearch(int initCapacity) {
        super(initCapacity);
        System.out.println("***** A* Search *****");
    }

    @Override
    public void addParentToClosedList() {
        super.addParentToClosedList();
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

        for(int type = 0; type < 4; ++type) {
            if(isFound[type]) {
                replaceOpenList(foundMember[type], type);
                removeFromClosedList(foundMember[type], type);
            } else {
                forceToAddToOpenList(type);
            }
        }
    }

    private String getPathSymbol(int type) {
        switch (type) {
            case 0: return "R";
            case 1: return "U";
            case 2: return "L";
            case 3: return "D";
            default: return "";
        }
    }

    private void replaceOpenList(ListMember open, int type) {
        Square child = open.getChild();

        if(!child.getAbleToVisit()) return;
        if (open.getDist() <= cost(presentSquare, child)) return;

        openList.remove(open);
        openList.add(new ListMember(child, presentSquare, cost(presentSquare, child), getPathSymbol(type)));
    }

    private void removeFromClosedList(ListMember open, int type) {
        Square child = open.getChild();
        int x = child.getX(), y = child.getY();

        if(Map.get(x, y) == '*' || child.getAbleToVisit()) return;
        if (open.getDist() <= cost(presentSquare, child)) return;

        child.reOpen();
        openList.add(new ListMember(child, presentSquare, cost(presentSquare, child), getPathSymbol(type)));
    }

    private void forceToAddToOpenList(int type) {
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

    private double cost(Square parent, Square child) {
        return parent.getPath().length() + heuristicFunc(child.getX(), child.getY()) + 1.0;
    }

}