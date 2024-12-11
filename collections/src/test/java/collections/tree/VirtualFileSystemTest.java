package collections.tree;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VirtualFileSystemTest {
    private VirtualFileSystem<String, byte[]> vfs;

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
                "/b/d/f/e.ext\n";
        assertEquals(expected, this.vfs.toString());
    }

    @Test
    public void zip_unzip() throws Exception {
        this.vfs.zip("test.zip");
        assertEquals(this.vfs.toString(), VirtualFileSystem.unzip(new File("test.zip")).toString());
    }

    @Test
    public void get() {
        assertEquals("01234567890123456789", new String(VirtualFileSystem.find(this.vfs, "/a/a.ext").getElement(), StandardCharsets.UTF_8));
    }

    @Test
    public void remove() {
        VirtualFileSystem.find(this.vfs, "/b/d/f/e.ext").remove();
        String expected = "\n" +
                "/a\n" +
                "/a/a.ext\n" +
                "/a/b.ext\n" +
                "/b\n" +
                "/b/c\n" +
                "/b/c/c.ext\n" +
                "/b/d\n" +
                "/b/d/f\n";
        assertEquals(expected, this.vfs.toString());
    }

    @Test
    public void children() {
        final var it = VirtualFileSystem.find(this.vfs, "/a").children().iterator();
        assertTrue(it.hasNext());
        assertEquals("01234567890123456789", new String(it.next().getElement(), StandardCharsets.UTF_8));
        assertTrue(it.hasNext());
        assertEquals("1", new String(it.next().getElement(), StandardCharsets.UTF_8));
    }

    @Test
    public void root() {
        assertEquals("", VirtualFileSystem.find(this.vfs, "/").getFilename().toString());
    }

    @Test
    public void parent() {
        assertEquals(VirtualFileSystem.find(this.vfs, "/b/d/f/"), VirtualFileSystem.find(this.vfs, "/b/d/f/e.ext").parent());
        assertEquals(VirtualFileSystem.find(this.vfs, "/b/d/f"), VirtualFileSystem.find(this.vfs, "/b/d/f/e.ext").parent());
    }

    @Test
    public void add() {
        VirtualFileSystem.find(this.vfs, "/b/c").addChild(new VirtualFileSystem<>("o.ext", "Hola Mundo".getBytes(StandardCharsets.UTF_8)));
        VirtualFileSystem.find(this.vfs, "/b/d/f").addChild(new VirtualFileSystem<>("o.ext", "Hola Mundo".getBytes(StandardCharsets.UTF_8)));
        String expected = "\n" +
                "/a\n" +
                "/a/a.ext\n" +
                "/a/b.ext\n" +
                "/b\n" +
                "/b/c\n" +
                "/b/c/o.ext\n" +
                "/b/c/c.ext\n" +
                "/b/d\n" +
                "/b/d/f\n" +
                "/b/d/f/o.ext\n" +
                "/b/d/f/e.ext\n";
        assertEquals(expected, this.vfs.toString());
    }
}
