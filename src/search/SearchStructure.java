package search;

import java.util.Comparator;

class Square {
    private int x, y;
    private boolean ableToVisit;
    private String path;

    Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.ableToVisit = true;
    }

    void close() {
        ableToVisit = false;
    }

    void reOpen() { ableToVisit = true; }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    boolean getAbleToVisit() {
        return ableToVisit;
    }

    boolean equals(Square sq) {
        return this.x == sq.x && this.y == sq.y;
    }

    String getPath() {
        return path;
    }
    void setPath(String path) {
        this.path = path;
    }
}

class ListMember {
    private Square child;
    private double dist;

    ListMember(Square child, Square parent, double dist, String c) {
        this.child = child;
        this.dist = dist;
        if(parent == null) child.setPath("");
        else child.setPath(parent.getPath() + c);
    }

    double getDist(){
        return dist;
    }

    Square getChild() {
        return child;
    }
}

class ListComparator implements Comparator {
    private static final double delta = 1e-6;
    @Override
    public int compare(Object obj1, Object obj2) {

        ListMember member1 = (ListMember) obj1;
        ListMember member2 = (ListMember) obj2;
        double d1 = member1.getDist(), d2 = member2.getDist();

        if(d1 - d2 > delta) return 1;
        else if(d2 - d1 > delta) return -1;
        else return 0;
    }
}