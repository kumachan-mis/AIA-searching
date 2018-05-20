package search;

class ListMember {
    private Square child;
    private double cost;

    ListMember(Square child, Square parent, double cost, String str) {
        this.child = child;
        this.cost = cost;
        if(parent == null) child.setPath("");
        else child.setPath(parent.getPath() + str);
    }

    double getCost(){
        return cost;
    }

    Square getChild() {
        return child;
    }
}