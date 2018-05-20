package search;

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