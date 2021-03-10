package ch.hslu.ad.sw03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// source:https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram

public class BTreePrinterTest {

    private static Knoten<Integer> test1() {
        Knoten<Integer> root = new Knoten<Integer>(2);
        Knoten<Integer> n11 = new Knoten<Integer>(7);
        Knoten<Integer> n12 = new Knoten<Integer>(5);
        Knoten<Integer> n21 = new Knoten<Integer>(2);
        Knoten<Integer> n22 = new Knoten<Integer>(6);
        Knoten<Integer> n23 = new Knoten<Integer>(3);
        Knoten<Integer> n24 = new Knoten<Integer>(6);
        Knoten<Integer> n31 = new Knoten<Integer>(5);
        Knoten<Integer> n32 = new Knoten<Integer>(8);
        Knoten<Integer> n33 = new Knoten<Integer>(4);
        Knoten<Integer> n34 = new Knoten<Integer>(5);
        Knoten<Integer> n35 = new Knoten<Integer>(8);
        Knoten<Integer> n36 = new Knoten<Integer>(4);
        Knoten<Integer> n37 = new Knoten<Integer>(5);
        Knoten<Integer> n38 = new Knoten<Integer>(8);

        root.links = n11;
        root.rechts = n12;

        n11.links = n21;
        n11.rechts = n22;
        n12.links = n23;
        n12.rechts = n24;

        n21.links = n31;
        n21.rechts = n32;
        n22.links = n33;
        n22.rechts = n34;
        n23.links = n35;
        n23.rechts = n36;
        n24.links = n37;
        n24.rechts = n38;

        return root;
    }

    private static Knoten<Integer> test2() {
        Knoten<Integer> root = new Knoten<Integer>(2);
        Knoten<Integer> n11 = new Knoten<Integer>(7);
        Knoten<Integer> n12 = new Knoten<Integer>(5);
        Knoten<Integer> n21 = new Knoten<Integer>(2);
        Knoten<Integer> n22 = new Knoten<Integer>(6);
        Knoten<Integer> n23 = new Knoten<Integer>(9);
        Knoten<Integer> n31 = new Knoten<Integer>(5);
        Knoten<Integer> n32 = new Knoten<Integer>(8);
        Knoten<Integer> n33 = new Knoten<Integer>(4);

        root.links = n11;
        root.rechts = n12;

        n11.links = n21;
        n11.rechts = n22;

        n12.rechts = n23;
        n22.links = n31;
        n22.rechts = n32;

        n23.links = n33;

        return root;
    }

    public static void main(String[] args) {

        BTreePrinter.printKnoten(test1());
        BTreePrinter.printKnoten(test2());

    }
}

class Knoten<T extends Comparable<?>> {
    Knoten<T> links, rechts;
    T data;

    public Knoten(T data) {
        this.data = data;
    }
}

class BTreePrinter {

    public static <T extends Comparable<?>> void printKnoten(Knoten<T> root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printKnotenInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printKnotenInternal(List<Knoten<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<Knoten<T>> newKnotens = new ArrayList<Knoten<T>>();
        for (Knoten<T> node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newKnotens.add(node.links);
                newKnotens.add(node.rechts);
            } else {
                newKnotens.add(null);
                newKnotens.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).links != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).rechts != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printKnotenInternal(newKnotens, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(Knoten<T> node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.links), BTreePrinter.maxLevel(node.rechts)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}