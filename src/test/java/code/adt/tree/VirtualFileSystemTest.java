package code.adt.tree;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class VirtualFileSystemTest {
    private VirtualFileSystem<byte[]> vfs;

    @Before
    public void setUp() throws Exception {
        this.vfs = VirtualFileSystem.loadFromFileSystem(new File(this.getClass().getResource("root").toURI()));
    }

    @Test
    public void loadFromFileSystem() {
        String expected = "\n" +
                "/a\n" +
                "/a/a.ext\n" +
                "/a/b.ext\n" +
                "/b\n" +
                "/b/c\n" +
                "/b/c/c.ext\n" +
                "/b/d\n" +
                "/b/d/f\n" +
                "/b/d/f/e.ext";
        assertEquals(expected, this.vfs.toString());
    }

    @Test
    public void zip_unzip() throws Exception {
        this.vfs.zip("test.zip");
        assertEquals(this.vfs.toString(), VirtualFileSystem.unzip(new File("test.zip")).toString());
    }
    
    @Test
    public void get() {
        assertEquals("01234567890123456789", new String(this.vfs.get("/a/a.ext").getElement(), StandardCharsets.UTF_8));
    }

    @Test
    public void remove() {
        this.vfs.remove(this.vfs.get("/b/d/f/e.ext"));
        String expected = "\n" +
                "/a\n" +
                "/a/a.ext\n" +
                "/a/b.ext\n" +
                "/b\n" +
                "/b/c\n" +
                "/b/c/c.ext\n" +
                "/b/d\n" +
                "/b/d/f";
        assertEquals(expected, this.vfs.toString());
    }

    @Test
    public void children() {
        final Iterator<VirtualFileSystem.LCRSTree<byte[]>> it = this.vfs.children(this.vfs.get("/a")).iterator();
        assertTrue(it.hasNext());
        assertEquals("01234567890123456789", new String(it.next().getElement(), StandardCharsets.UTF_8));
        assertTrue(it.hasNext());
        assertEquals("1", new String(it.next().getElement(), StandardCharsets.UTF_8));
    }

    @Test
    public void root() {
        assertNull(this.vfs.root().getElement());
        assertEquals("", this.vfs.getPath(this.vfs.root()));
    }

    @Test
    public void parent() {
        assertNull(this.vfs.parent(this.vfs.root()));
        assertEquals(this.vfs.get("/b/d/.e/"), this.vfs.parent(this.vfs.get("/b/d/.e/d.ext")));
        assertEquals(this.vfs.get("/b/d/.e"), this.vfs.parent(this.vfs.get("/b/d/.e/d.ext")));
    }

    @Test
    public void getPath() {
        assertEquals("/b/d/.e/d.ext", this.vfs.getPath(this.vfs.get("/b/d/.e/d.ext")));
    }

    @Test
    public void isInternal() {
        assertTrue(this.vfs.isInternal(this.vfs.root()));
        assertTrue(this.vfs.isInternal(this.vfs.get("/a")));
        assertFalse(this.vfs.isInternal(this.vfs.get("/a/a.ext")));
        assertFalse(this.vfs.isInternal(this.vfs.get("/a/b.ext")));
        assertTrue(this.vfs.isInternal(this.vfs.get("/b")));
        assertTrue(this.vfs.isInternal(this.vfs.get("/b/c")));
        assertFalse(this.vfs.isInternal(this.vfs.get("/b/c/c.ext")));
        assertTrue(this.vfs.isInternal(this.vfs.get("/b/d")));
        assertTrue(this.vfs.isInternal(this.vfs.get("/b/d/.e")));
        assertFalse(this.vfs.isInternal(this.vfs.get("/b/d/.e/d.ext")));
        assertTrue(this.vfs.isInternal(this.vfs.get("/b/d/f")));
        assertFalse(this.vfs.isInternal(this.vfs.get("/b/d/f/e.ext")));
    }

    @Test
    public void isLeaf() {
        assertFalse(this.vfs.isLeaf(this.vfs.root()));
        assertFalse(this.vfs.isLeaf(this.vfs.get("/a")));
        assertTrue(this.vfs.isLeaf(this.vfs.get("/a/a.ext")));
        assertTrue(this.vfs.isLeaf(this.vfs.get("/a/b.ext")));
        assertFalse(this.vfs.isLeaf(this.vfs.get("/b")));
        assertFalse(this.vfs.isLeaf(this.vfs.get("/b/c")));
        assertTrue(this.vfs.isLeaf(this.vfs.get("/b/c/c.ext")));
        assertFalse(this.vfs.isLeaf(this.vfs.get("/b/d")));
        assertFalse(this.vfs.isLeaf(this.vfs.get("/b/d/.e")));
        assertTrue(this.vfs.isLeaf(this.vfs.get("/b/d/.e/d.ext")));
        assertFalse(this.vfs.isLeaf(this.vfs.get("/b/d/f")));
        assertTrue(this.vfs.isLeaf(this.vfs.get("/b/d/f/e.ext")));
    }

    @Test
    public void add() {
        this.vfs.add(this.vfs.get("/b/d/.e"), "o.ext", "Hola Mundo".getBytes(StandardCharsets.UTF_8));
        this.vfs.add(this.vfs.get("/b/d/f"), "o.ext", "Hola Mundo".getBytes(StandardCharsets.UTF_8));
        String expected = "\n" +
                "/a\n" +
                "/a/a.ext\n" +
                "/a/b.ext\n" +
                "/b\n" +
                "/b/c\n" +
                "/b/c/c.ext\n" +
                "/b/d\n" +
                "/b/d/f\n" +
                "/b/d/f/e.ext\n" +
                "/b/d/f/o.ext";
        assertEquals(expected, this.vfs.toString());
    }
}
