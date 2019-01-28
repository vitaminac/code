package datastructure.user;

import junit.framework.AssertionFailedError;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class VirtualFileSystemTest {

    private final String INVALID_ID_MESSAGE = "Invalid ID.";
    private String path = "./Resources/DirectorioRaiz";
    private String path2 = "./Resources/DirectorioRaiz/SubdirectorioA";
    private VirtualFileSystem vfs;

    @Before
    public void setUp() {
        vfs = new VirtualFileSystem();
        vfs.loadFileSystem(path);
    }

    @After
    public void tearDown() {
        //Not required
    }

    @Test
    public void loadFileSystem() {
        String output = vfs.getFileSystem();
//        System.out.println(output);
        String expected = "0 DirectorioRaiz\n" +
                "1 \tSubdirectorioA\n" +
                "2 \t\tArchivoA.ext\n" +
                "3 \t\tArchivoB.ext\n" +
                "4 \tSubdirectorioB\n" +
                "5 \t\tSubdirectorioC\n" +
                "6 \t\t\tArchivoC.ext\n" +
                "7 \t\tSubdirectorioD\n" +
                "8 \t\tSubdirectorioE\n" +
                "9 \t\t\tSubdirectorioF\n" +
                "10 \t\t\t\tArchivoD.ext\n";
        assertEquals(expected, output);

        vfs.loadFileSystem(path2);
        output = vfs.getFileSystem();
        expected = "0 SubdirectorioA\n" +
                "1 \tArchivoA.ext\n" +
                "2 \tArchivoB.ext\n";
        assertEquals(expected, output);
    }


    @Test
    public void moveFileById() {
        vfs.moveFileById(7, 1);
        String output = vfs.getFileSystem();

        String expected = "0 DirectorioRaiz\n" +
                "1 \tSubdirectorioA\n" +
                "2 \t\tArchivoA.ext\n" +
                "3 \t\tArchivoB.ext\n" +
                "7 \t\tSubdirectorioD\n" +
                "4 \tSubdirectorioB\n" +
                "5 \t\tSubdirectorioC\n" +
                "6 \t\t\tArchivoC.ext\n" +
                "8 \t\tSubdirectorioE\n" +
                "9 \t\t\tSubdirectorioF\n" +
                "10 \t\t\t\tArchivoD.ext\n";
        assertEquals(expected, output);


        vfs.moveFileById(8, 4);
        output = vfs.getFileSystem();
        expected = "0 DirectorioRaiz\n" +
                "1 \tSubdirectorioA\n" +
                "2 \t\tArchivoA.ext\n" +
                "3 \t\tArchivoB.ext\n" +
                "7 \t\tSubdirectorioD\n" +
                "4 \tSubdirectorioB\n" +
                "5 \t\tSubdirectorioC\n" +
                "6 \t\t\tArchivoC.ext\n" +
                "8 \t\tSubdirectorioE\n" +
                "9 \t\t\tSubdirectorioF\n" +
                "10 \t\t\t\tArchivoD.ext\n";
        assertEquals(expected, output);

    }

    private <T extends Exception> T assertThrows(Class<T> exceptionClz, Runnable code) {
        try {
            code.run();
        } catch (Exception e) {
            if (e.getClass().equals(exceptionClz)) {
                return exceptionClz.cast(e);
            } else {
                throw new AssertionFailedError("expected exception " + exceptionClz.getName() + " and found " + e.getClass().getName());
            }
        }
        throw new AssertionFailedError("expected exception " + exceptionClz.getName());
    }

    @Test
    public void moveFileById_Exceptions() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> vfs.moveFileById(4, 9));
        assertEquals("A file can't be a subdirectory of itself.", exception.getMessage());

        exception = assertThrows(RuntimeException.class, () -> vfs.moveFileById(4, 2));
        assertEquals("Target can't be a file.", exception.getMessage());

        vfs.removeFileById(4);
        exception = assertThrows(RuntimeException.class, () -> vfs.moveFileById(4, 3));
        assertEquals(INVALID_ID_MESSAGE, exception.getMessage());
    }

    @Test
    public void removeFileById() {
        vfs.removeFileById(4);
        String output = vfs.getFileSystem();
        String expected = "0 DirectorioRaiz\n" +
                "1 \tSubdirectorioA\n" +
                "2 \t\tArchivoA.ext\n" +
                "3 \t\tArchivoB.ext\n";
        assertEquals(expected, output);

        Exception exception = assertThrows(RuntimeException.class, () -> vfs.removeFileById(4));
        assertEquals(INVALID_ID_MESSAGE, exception.getMessage());

        exception = assertThrows(RuntimeException.class, () -> vfs.removeFileById(10));
        assertEquals(INVALID_ID_MESSAGE, exception.getMessage());
    }

    @Test
    public void removeFileByID_Exceptions() {
        vfs.removeFileById(4);
        Exception exception = assertThrows(RuntimeException.class, () -> vfs.removeFileById(4));
        assertEquals(INVALID_ID_MESSAGE, exception.getMessage());
    }

    @Test
    public void findBySubstring() {
        Iterable<String> iterable = vfs.findBySubstring(0, "A");
        String output = iterable.toString();
        String expected = "[1\tSubdirectorioA, 2\tArchivoA.ext, 3\tArchivoB.ext, 6\tArchivoC.ext, 10\tArchivoD.ext]";
        assertEquals(expected, output);

        iterable = vfs.findBySubstring(0, ".ext");
        output = iterable.toString();
        expected = "[2\tArchivoA.ext, 3\tArchivoB.ext, 6\tArchivoC.ext, 10\tArchivoD.ext]";
        assertEquals(expected, output);

        iterable = vfs.findBySubstring(8, "");
        output = iterable.toString();
        expected = "[8\tSubdirectorioE, 9\tSubdirectorioF, 10\tArchivoD.ext]";
        assertEquals(expected, output);

        iterable = vfs.findBySubstring(8, "F");
        output = iterable.toString();
        expected = "[9\tSubdirectorioF]";
        assertEquals(expected, output);

        iterable = vfs.findBySubstring(4, "Z");
        output = iterable.toString();
        expected = "[]";
        assertEquals(expected, output);

    }

    @Test
    public void findBySubstring_Exception() {
        vfs.removeFileById(4);
        Exception exception = assertThrows(RuntimeException.class, () -> vfs.findBySubstring(4, "text"));
        assertEquals(INVALID_ID_MESSAGE, exception.getMessage());

        assertThrows(NullPointerException.class, () -> vfs.findBySubstring(0, null));
    }

    @Test
    public void findBySize() {
        Iterable<String> iterable = vfs.findBySize(0, 1, 19);
        String output = iterable.toString();
        String expected = "[3\tArchivoB.ext, 6\tArchivoC.ext]";
        assertEquals(expected, output);

        iterable = vfs.findBySize(0, 0, 1000);
        output = iterable.toString();
        expected = "[2\tArchivoA.ext, 3\tArchivoB.ext, 6\tArchivoC.ext, 10\tArchivoD.ext]";
        assertEquals(expected, output);

        iterable = vfs.findBySize(7, 0, 1000);
        output = iterable.toString();
        expected = "[]";
        assertEquals(expected, output);

        iterable = vfs.findBySize(0, 1, 1);
        output = iterable.toString();
        expected = "[3\tArchivoB.ext]";
        assertEquals(expected, output);
    }

    @Test
    public void findBySize_Exception() {

        Exception exception = assertThrows(RuntimeException.class, () -> vfs.findBySize(4, 2, 1));
        assertEquals("Invalid range.", exception.getMessage());

        vfs.removeFileById(4);
        exception = assertThrows(RuntimeException.class, () -> vfs.findBySize(4, 0, 10));
        assertEquals(INVALID_ID_MESSAGE, exception.getMessage());


    }

    @Test
    public void getFileVirtualPath() {
        String output, expected;
        output = vfs.getFileVirtualPath(0);
        expected = "vfs:/DirectorioRaiz";
        assertEquals(expected, output);

        output = vfs.getFileVirtualPath(6);
        assertEquals("vfs:/DirectorioRaiz/SubdirectorioB/SubdirectorioC/ArchivoC.ext", output);

        vfs.moveFileById(8, 5);
        output = vfs.getFileVirtualPath(10);
        expected = "vfs:/DirectorioRaiz/SubdirectorioB/SubdirectorioC/SubdirectorioE/SubdirectorioF/ArchivoD.ext";
        assertEquals(expected, output);

        vfs.moveFileById(9, 1);
        output = vfs.getFileVirtualPath(9);
        expected = "vfs:/DirectorioRaiz/SubdirectorioA/SubdirectorioF";
        assertEquals(expected, output);
    }

    @Test
    public void getFileVirtualPath_Exception() {
        vfs.removeFileById(4);
        Exception exception = assertThrows(RuntimeException.class, () -> vfs.getFileVirtualPath(4));
        assertEquals(INVALID_ID_MESSAGE, exception.getMessage());
    }

    @Test
    public void getFilePath() {
        String output, expected;
        output = vfs.getFilePath(0);
        expected = "./Resources/DirectorioRaiz";
        assertEquals(output, expected);

        output = vfs.getFilePath(3);
        expected = "./Resources/DirectorioRaiz/SubdirectorioA/ArchivoB.ext";
        assertEquals(expected, output);

        vfs.loadFileSystem(path2);
        output = vfs.getFilePath(0);
        expected = "./Resources/DirectorioRaiz/SubdirectorioA";
        assertEquals(expected, output);
    }

    @Test
    public void getFilePath_Exception() {
        vfs.removeFileById(4);
        Exception exception = assertThrows(RuntimeException.class, () -> vfs.getFilePath(4));
        assertEquals(INVALID_ID_MESSAGE, exception.getMessage());
    }
}
