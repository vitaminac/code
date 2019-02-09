package code;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

class Utils {
    static class Pair {
        public File file;
        public String packageName;

        public Pair(File file, String packageName) {
            this.file = file;
            this.packageName = packageName;
        }
    }


    private static List<Pair> findResource(File directory, String packageName, String type) {
        List<Pair> resources = new ArrayList<>();
        if (!directory.exists()) {
            return resources;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    resources.addAll(findResource(file, packageName + "." + file.getName(), type));
                } else if (file.getName().endsWith("." + type)) {
                    resources.add(new Pair(file, packageName));
                }
            }
        }
        return resources;
    }

    private static List<Pair> getResources(String packageName, String type) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> enumeration = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            URL resource = enumeration.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Pair> resources = new ArrayList<>();
        for (File directory : dirs) {
            resources.addAll(findResource(directory, packageName, type));
        }
        return resources;
    }

    public static <T> List<Pair> getResources(Class<T> clazz, String type) throws Exception {
        return getResources(clazz.getPackage().getName(), type);
    }
}
