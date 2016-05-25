package pkg8.puzzle;

public class StateOrder implements Comparable {

    /**
     * Constructs a state order with a given priority and description.
     *
     * @param aPriority the priority of state
     * @param aDescription the description of state
     */
    private int priority;

    private String description;
    private int nv, ord;
    // nv is level or distance of node from initial used
    // for breaking tie in same priority
    // ord is pre-given value of each of the four movements of the blank
    //used for breaking tie in both same level and same priority

    public StateOrder(int aPriority, String aDescription, int nv, int ord) {

        priority = aPriority;

        description = aDescription;
        this.nv = nv;
        this.ord = ord;

    }

    @Override
    public String toString() {

        return description;

    }

    public int compareTo(Object otherObject) {

        StateOrder other = (StateOrder) otherObject;

        if (priority < other.priority) {
            return -1;
        } else if (priority == other.priority) {
            if (nv < other.nv) {
                return -1;
            } else if (nv == other.nv) {
                if (ord < other.ord) {
                    return -1;
                } else if (ord == other.ord) {

                    return 0;
                } else {
                    return 1;

                }

            } else {
                return 1;

            }

        } else {
            return 1;
        }

    }

}
