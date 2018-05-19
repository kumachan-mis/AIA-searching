package test;
import search.AsterSearch;
import search.GreedySearch;

class Main {
    private static final int initCapacity = 1;

    public static void main(String[] args) {
        Map.read();
        Map.show();
        new GreedySearch(initCapacity).doSearch();
        new AsterSearch(initCapacity).doSearch();
    }
}