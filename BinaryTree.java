package binarytree;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private static class Node<T> {

        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }

    }

    private Node<T> root = null;
    BinaryTree<T> parentSet = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        if(this.parentSet != null)
            this.parentSet.add(t);
        return true;
    }

    boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {}

        private Node<T> findNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    void addBranch(Node<T> vertex, BinaryTree<T> tree) {

        tree.add(vertex.value);
        if(vertex.left != null)
            addBranch(vertex.left, tree);
        if(vertex.right != null)
            addBranch(vertex.right, tree);

    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {

        return this.headSet(toElement).tailSet(fromElement);

    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {

        BinaryTree<T> newSet = new BinaryTree<>();
        Node<T> currentVertex = root;

        int checkFinal = 0;

        while(checkFinal == 0) {
            while (currentVertex.value.compareTo(toElement) >= 0) {

                if (currentVertex.left != null)
                    currentVertex = currentVertex.left;
                else
                    break;

            }
            while (currentVertex.value.compareTo(toElement) < 0) {

                newSet.add(currentVertex.value);
                if (currentVertex.left != null)
                    addBranch(currentVertex.left, newSet);
                if (currentVertex.right != null)
                    currentVertex = currentVertex.right;
                else {
                    checkFinal = 1;
                    break;
                }

            }
        }
        newSet.parentSet = this;

        return newSet;

    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {

        BinaryTree<T> newSet = new BinaryTree<>();
        Node<T> currentVertex = root;

        int checkFinal = 0;

        while(checkFinal == 0) {
            while (currentVertex.value.compareTo(fromElement) < 0) {

                if (currentVertex.right != null)
                    currentVertex = currentVertex.right;
                else {
                    checkFinal = 1;
                    break;
                }

            }
            while (currentVertex.value.compareTo(fromElement) >= 0) {

                newSet.add(currentVertex.value);
                if (currentVertex.right != null)
                    addBranch(currentVertex.right, newSet);
                if (currentVertex.left != null)
                    currentVertex = currentVertex.left;
                else
                    break;

            }
        }
        newSet.parentSet = this;

        return newSet;

    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
