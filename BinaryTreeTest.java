package binarytree;

import org.junit.Before;

import java.util.SortedSet;

import static org.junit.Assert.*;

public class BinaryTreeTest {

    private BinaryTree<Integer> tree;

    @Before
    public void TreeTest() {
        tree = new BinaryTree<>();
        tree.add(5);
        tree.add(3);
        tree.add(7);
        tree.add(1);
        tree.add(4);
        tree.add(6);
        tree.add(10);
        tree.add(15);
        tree.add(9);
    }

    @org.junit.Test
    public void subSet() throws Exception {

        SortedSet<Integer> set;
        set = tree.subSet(5, 10);
        assertEquals(true, set.contains(5));
        assertEquals(true, set.contains(6));
        assertEquals(true, set.contains(7));
        assertEquals(true, set.contains(9));
        assertEquals(false, set.contains(1));
        assertEquals(false, set.contains(3));
        assertEquals(false, set.contains(4));
        assertEquals(false, set.contains(10));
        assertEquals(false, set.contains(15));

    }

    @org.junit.Test
    public void headSet() throws Exception {

        SortedSet<Integer> set;
        set = tree.headSet(7);
        assertEquals(true, set.contains(1));
        assertEquals(true, set.contains(3));
        assertEquals(true, set.contains(4));
        assertEquals(true, set.contains(5));
        assertEquals(true, set.contains(6));
        assertEquals(false, set.contains(7));
        assertEquals(false, set.contains(9));
        assertEquals(false, set.contains(10));
        assertEquals(false, set.contains(15));

    }

    @org.junit.Test
    public void tailSet() throws Exception {

        SortedSet<Integer> set;
        set = tree.tailSet(5);
        assertEquals(true, set.contains(5));
        assertEquals(true, set.contains(6));
        assertEquals(true, set.contains(7));
        assertEquals(true, set.contains(10));
        assertEquals(true, set.contains(9));
        assertEquals(true, set.contains(15));
        assertEquals(false, set.contains(1));
        assertEquals(false, set.contains(3));
        assertEquals(false, set.contains(4));

    }

}