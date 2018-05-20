package search;
import java.util.Comparator;

class ListComparator implements Comparator {
    @Override
    public int compare(Object obj1, Object obj2) {
        ListMember member1 = (ListMember) obj1;
        ListMember member2 = (ListMember) obj2;
        double d1 = member1.getCost(), d2 = member2.getCost();
        return Double.compare(d1, d2);
    }
}