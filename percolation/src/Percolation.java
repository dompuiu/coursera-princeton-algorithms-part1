public class Percolation {
    private WeightedQuickUnionUF unionFind, auxUnionFind;
    private int unionSize;

    private boolean[] grid;
    private int dimension;

    public Percolation(int N) {
        if (N < 1) {
            throw new IllegalArgumentException(
                "Grid width must be greater than zero"
            );
        }

        dimension = N;
        grid = createGrid(dimension * dimension);

        unionSize = dimension * dimension;
        unionFind = new WeightedQuickUnionUF(unionSize + 2);
        auxUnionFind = new WeightedQuickUnionUF(unionSize + 1);
    }

    public void open(int i, int j) {
        int index = getIndex(i, j);
        checkIndex(i, j);

        grid[index] = true;

        uniteWithVirtualTop(i, j);
        uniteWithTop(i, j);
        uniteWithRight(i, j);
        uniteWithBottom(i, j);
        uniteWithLeft(i, j);
        uniteWithVirtualBottom(i, j);
    }

    public boolean isOpen(int i, int j) {
        int index = getIndex(i, j);
        checkIndex(i, j);

        return grid[index];
    }

    public boolean percolates() {
        return unionFind.connected(unionSize, unionSize + 1);
    }

    public boolean isFull(int i, int j) {
        int index = getIndex(i, j);
        checkIndex(i, j);

        return auxUnionFind.connected(unionSize, index);
    }

    private boolean[] createGrid(int size) {
        boolean[] g = new boolean[size];

        for (int i = 0; i < size; i++) {
            g[i] = false;
        }

        return g;
    }

    private int getIndex(int i, int j) {
        return (i - 1) * dimension + j - 1;
    }

    private void uniteWithVirtualTop(int i, int j) {
        int index = getIndex(i, j);
        if (firstRowIndex(index)) {
            unionFind.union(unionSize, index);
            auxUnionFind.union(unionSize, index);
        }
    }

    private void uniteWithVirtualBottom(int i, int j) {
        int index = getIndex(i, j);
        if (lastRowIndex(index)) {
            unionFind.union(unionSize + 1, index);
        }
    }

    private void uniteWithTop(int i, int j) {
        int index = getIndex(i, j);
        if (firstRowIndex(index)) {
            return;
        }

        if (isOpen(i - 1, j)) {
            int topIndex = getIndex(i - 1, j);
            unionFind.union(index, topIndex);
            auxUnionFind.union(index, topIndex);
        }
    }

    private void uniteWithRight(int i, int j) {
        int index = getIndex(i, j);
        if (lastColumnIndex(index)) {
            return;
        }


        if (isOpen(i, j + 1)) {
            int rightIndex = getIndex(i, j + 1);
            unionFind.union(index, rightIndex);
            auxUnionFind.union(index, rightIndex);
        }
    }

    private void uniteWithBottom(int i, int j) {
        int index = getIndex(i, j);
        if (lastRowIndex(index)) {
            return;
        }

        if (isOpen(i + 1, j)) {
            int bottomIndex = getIndex(i + 1, j);
            unionFind.union(index, bottomIndex);
            auxUnionFind.union(index, bottomIndex);
        }

    }

    private void uniteWithLeft(int i, int j) {
        int index = getIndex(i, j);
        if (firstColumnIndex(index)) {
            return;
        }

        if (isOpen(i, j - 1)) {
            int leftIndex = getIndex(i, j - 1);
            unionFind.union(index, leftIndex);
            auxUnionFind.union(index, leftIndex);
        }
    }

    private boolean firstRowIndex(int index) {
        return index < dimension;
    }

    private boolean lastRowIndex(int index) {
        return index >= unionSize - dimension;
    }

    private boolean lastColumnIndex(int index) {
        return dimension == 1 || (index % dimension) == dimension - 1;
    }

    private boolean firstColumnIndex(int index) {
        return dimension == 1 || (index % dimension) == 0;
    }

    private void checkIndex(int i, int j) {
        if (i < 1 || j < 1 || i > dimension || j > dimension) {
            throw new IndexOutOfBoundsException(
                "Indices are not valid"
            );
        }
    }
}