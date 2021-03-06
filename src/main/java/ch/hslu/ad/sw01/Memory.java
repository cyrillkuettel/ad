package ch.hslu.ad.sw01;

/**
 *
 * @author cyrill
 */
public abstract class Memory {

    private int Belegt = 0;
    private int Frei;

    public Memory(final int initialValue) {
        this.Frei = initialValue;
    }

    public Allocation malloc(final int allocatedMem) {    //allocate memory
        this.Belegt += allocatedMem;
        this.Frei -= allocatedMem;
        Allocation allocation = new Allocation(allocatedMem);
        return allocation;
    }

    public void free(final Allocation block) {
        final int allocatedMem = block.getSize();
        this.Frei += allocatedMem;
        this.Belegt -= allocatedMem;
        // Undo the allocation, set the pointer correctly. memory is free, 

        // probably we need a linked list of Available free memory blocks .
        // Idee von Andreas Gisler: anstatt Frei und Belegt attribute, da 2 Allocationen Objecte machen
        // Idee: Allocationen nach address sortieren, um aneinanderhängende Objecte zu finden
        // Idee: Allocationen nach size sortieren, um in O(n) Zeit zu sortieren. 
        // to insert a item in a sorted linked list, requires O(1) time
        // TODO: Improve. nextAddress should always point to the next available block. 
        Allocation.setNextAddress(Allocation.getNextAddress() - allocatedMem);
    }

    public int getBelegt() {
        return Belegt;
    }

    public int getFrei() {
        return Frei;
    }
}
