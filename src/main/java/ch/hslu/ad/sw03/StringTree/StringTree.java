package ch.hslu.ad.sw03.StringTree;

import ch.hslu.ad.sw03.SimpleTree.*;
import ch.hslu.ad.sw02.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author cyrill
 */
// is empty ,add search , prtin in order, delete of course
interface TreeInterface {

    boolean isEmpty();

    public void add(node node, String value);

    public void add(String value);

    void search(node node, String value);

    void search(String value);

    void printInOrder(node node);

}

public class StringTree implements TreeInterface {

    private static final Logger LOG = LogManager.getFormatterLogger(StringTree.class);
    protected node root;

    public StringTree(String value) {
        this.root = new node(value);
    }

    public StringTree() {
        this.root = new node();
    }

    public static void main(String[] args) {
        new StringTree();
    }

    @Override
    public void printInOrder(node node) {
        if (node == null) {
            return;
        }
        /* first print data of node */
        printInOrder(node.left);

        /* then print the data of node */
        System.out.print(node.value + " ");

        /* now recur on right child */
        printInOrder(node.right);
    }


    @Override
    public void search(node node, String value) {
        // what to do if element does not exist? 
        if (value.compareTo(node.value) < 0) {
            if (node.left.value == value) {
                System.out.println("found left Node: " + node.left);
            } else {
                search(node.left, value);
            }
        } else {
            if (node.right.value == value) {
                System.out.println("found right Node: " + node.right);
            } else {
                search(node.right, value);
            }
        }
    }

    @Override
    public void search(String value) {
        if (isEmpty()) {
            LOG.warn("Can't search a empty tree...");
        } else {
            search(root, value);
        }
    }

    @Override
    public void add(String value) {
        if (isEmpty()) {
            root = new node(value); // base case r
        } else {
            add(root, value);  //  descend one niveau
        }
    }

    @Override
    public void add(node node, String value) {

        if (value.compareTo(node.value) < 0) { //
            if (node.left == null) {
                node.left = new node(value);
            } else {
                add(node.left, value); // descend one niveau (Right side)
            }
        } else { // ---> value is bigger or equal to node.value
            if (node.right == null) {
                node.right = new node(value);
            } else { //right > right
                add(node.right, value); // descend one niveau (Right node)
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }

    public ArrayList<ArrayList<node>> getNodeListFromEachNiveau() {

// Inspiration from https://stackoverflow.com/questions/2241513/java-printing-a-binary-tree-using-level-order-in-a-specific-format
        ArrayList<ArrayList<node>> result = new ArrayList<ArrayList<node>>();

        Queue<node> currentLevel = new LinkedList<node>();
        Queue<node> nextLevel = new LinkedList<node>();

        currentLevel.add(root);

        while (!currentLevel.isEmpty() && !allZeroesInQueue(currentLevel)) {
            Iterator<node> iter = currentLevel.iterator();
            while (iter.hasNext()) {
                node currentNode = iter.next();
                prepareNextLevel(currentNode, nextLevel);
            }
            ArrayList<node> zwischenspeicher = new ArrayList<>(currentLevel);
            result.add(zwischenspeicher);

            currentLevel = nextLevel;
            nextLevel = new LinkedList<node>();
        }
        return result;
    }

    public boolean allZeroesInQueue(Queue<node> currentLevel) {
        return currentLevel.stream().allMatch(i -> i.empty == true);
    }

    public void prepareNextLevel(node currentNode, Queue<node> nextLevel) {
        if (currentNode.left != null) {
            nextLevel.add(currentNode.left);
        } else {
            nextLevel.add(new node("0", true)); // the boolean flag signals the node, that this Zero is in fact empty
        }

        if (currentNode.right != null) {
            nextLevel.add(currentNode.right);
        } else {
            nextLevel.add(new node("0", true));
        }
    }

    public void drawTree() {
        /*
         *      1      root
         *     / \
         *    2   3    Niveau-1
         *   /   / \
         *  4   5   6  Niveau-2
        
        https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
            
       a
      / \
     /   \
    /     \
   /       \
   b       c
  / \     / \
 /   \   /   \
 d   e   f   g
/ \ / \ / \ / \
h i j k l m n  o
         */

        ArrayList< ArrayList< node>> nodeListByNiveau = getNodeListFromEachNiveau();
//        System.out.println(nodeListByNiveau);

        int niveau = nodeListByNiveau.size();
        int firstBranchLength = getBranchLengthForNiveau(niveau);

        ArrayList<node> bottomList = nodeListByNiveau.get(niveau - 1);
        int middle = bottomList.size() * 2; // start in the middle
        ArrayList<node> firstList = nodeListByNiveau.get(0);
        node root = firstList.get(0); // get the root element

        for (ArrayList<node> arrayList : nodeListByNiveau) {
            int branchLen = getBranchLengthForNiveau(niveau);

            for (node node : arrayList) { // one level 
                String value;
                if (node.isEmpty()) {
                    value = " ";
                } else {
                    value = node.value.toString();
                }
                int offset = (int) Math.floor(arrayList.size() * 1.1); // 1.1 is fine tuning
                String whiteSpaceprevious = generateWhiteSpace((middle + 1) / offset);
                String whiteSpaceafter = generateWhiteSpace(middle / offset - 1);

                System.out.print(whiteSpaceprevious + node.value + whiteSpaceafter);

            }
            System.out.println();
            for (int i = 0; i < branchLen; i++) { // beschreibt, über wie viele Zeilen ein Ast gezeichent wird. 
                for (node node : arrayList) { // jeder Knotenpunkt muss beachtet werden. Von Dort aus zeichnen. 
                    // orientierung: whiteSpacePrevious und whitespace after. diese werte werden angepast mit jeder iteration der äusseren schliefe
                    //für jedes node:
                    // zeichne unterhalb einer einer nach links und einer nach rechts. (wenn != null)
                    //mit jeder iteration der inneren schleife:
                    // function: string replace:
                    // kopier die vorherige zeile. Dort jeweils 2 white space adden zwischen branches und den whitespace wegnehmen. 
                }
                System.out.println();
            }
            niveau--; // kapt'n niveau! Wir sinken!

        }

    }

    public int getBranchLengthForNiveau(final int niveau) {
        return (int) Math.pow(2, niveau - 1);
    }

    public String generateWhiteSpace(final int len) {
        return " ".repeat(len);
    }

}