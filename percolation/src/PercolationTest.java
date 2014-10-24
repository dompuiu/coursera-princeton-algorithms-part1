import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PercolationTest {
    @Test(expected=IllegalArgumentException.class)
    public void Constructor_WhenCalledWithSizeLowerThanOne_ThrowsException() {
        new Percolation(0);
    }

    @Test
    public void Constructor_WhenInitializingGridWithSizeOne_AllSitesAreBlocked() {
        Percolation p = new Percolation(1);

        assertFalse(p.isOpen(1, 1));
    }

    @Test
    public void Constructor_WhenInitializingGridWithSizeTwo_AllSitesAreBlocked() {
        Percolation p = new Percolation(2);

        assertFalse(p.isOpen(1, 1));
        assertFalse(p.isOpen(1, 2));
        assertFalse(p.isOpen(2, 1));
        assertFalse(p.isOpen(2, 2));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void Open_WhenCalledWithInvalidIndices_ThrowsException() {
        Percolation p = new Percolation(1);
        p.open(2, 1);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void Open_WhenCalledWithInvalidIndices2_ThrowsException() {
        Percolation p = new Percolation(10);
        p.open(6, 12);
    }

    @Test
    public void Open_WhenCalledWithValidIndices_OpensSite() {
        Percolation p = new Percolation(1);
        p.open(1, 1);

        assertTrue(p.isOpen(1, 1));
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void IsOpen_WhenCalledWithInvalidIndices_ThrowsException() {
        Percolation p = new Percolation(1);
        p.isOpen(2, 1);
    }

    @Test
    public void Percolates_ForGridWithSizeOneAndAllClosedSites_ReturnsFalse() {
        Percolation p = new Percolation(1);

        assertFalse(p.percolates());
    }

    @Test
    public void Percolates_ForGridWithSizeOneAndAllOpenSites_ReturnsFalse() {
        Percolation p = new Percolation(1);
        p.open(1, 1);

        assertTrue(p.percolates());
    }

    @Test
    public void Percolates_ForGridWithSizeTwoAndOneOpenSite_ReturnsFalse() {
        Percolation p = new Percolation(2);
        p.open(1, 1);

        assertFalse(p.percolates());
    }

    @Test
    public void Percolates_ForGridWithSizeTwoAndOneOpenSiteOnEachRow1_ReturnsTrue() {
        Percolation p = new Percolation(2);
        p.open(2, 1);
        p.open(1, 1);

        assertTrue(p.percolates());
    }

    @Test
    public void Percolates_ForGridWithSizeTwoAndOneOpenSiteOnEachRow2_ReturnsTrue() {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 1);

        assertTrue(p.percolates());
    }

    @Test
    public void Percolates_ForGridWithSizeThreeAndMultipleOpenSiteOnEachRow1_ReturnsTrue() {
        Percolation p = new Percolation(3);
        p.open(1, 3);
        p.open(2, 3);
        p.open(2, 2);
        p.open(3, 2);

        assertTrue(p.percolates());
    }

    @Test
    public void Percolates_ForGridWithSizeThreeAndMultipleOpenSiteOnEachRow2_ReturnsTrue() {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);

        assertTrue(p.percolates());
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void IsFull_WhenCalledWithInvalidIndices_ThrowsException() {
        Percolation p = new Percolation(1);
        p.isFull(2, 1);
    }

    @Test
    public void IsFull_ForGridWithSizeOneAndAllClosedSites_ReturnsFalse() {
        Percolation p = new Percolation(1);

        assertFalse(p.isFull(1, 1));
    }

    @Test
    public void IsFull_ForGridWithSizeOneAndAllOpenSites_ReturnsFalse() {
        Percolation p = new Percolation(1);
        p.open(1, 1);

        assertTrue(p.isFull(1, 1));
    }

    @Test
    public void IsFull_ForGridWithSizeTwoAndOneOpenSite_ReturnsFalse() {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 1);

        assertFalse(p.isFull(2, 2));
    }

    @Test
    public void IsFull_ForGridWithSizeThreeWithRandomOpenSites_ReturnsTrue() {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 1);

        p.open(3, 3);
        p.open(3, 2);

        p.open(2, 2);

        assertTrue(p.isFull(3, 3));
    }

    @Test
    public void IsFull_ForGridWithBackwash_ReturnsFalse() {
        Percolation p = new Percolation(3);
        p.open(3, 1);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);

        assertFalse(p.isFull(3, 1));
    }
}
