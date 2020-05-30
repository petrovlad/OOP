package its.me.Vladik.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class DynamicClassLoader extends ClassLoader {
    private HashMap classesHash = new HashMap();
    public final String[] classPath;

    //
    public DynamicClassLoader(String[] classPath) {
        // Набор путей поиска - аналог переменной CLASSPATH
        this.classPath = classPath;
    }

    // переопределенный метод для загрузки
    protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class result = findClass(name);
        if (resolve)
            resolveClass(result);
        return result;
    }

    // переопределенный метод для поиска класса
    protected Class findClass(String name) throws ClassNotFoundException {
        // на случай кеширования
        //
        Class result = (Class)classesHash.get(name);
        if (result != null) {
             // System.out.println("% Class " + name + " found in cache");
            return result;
        }

        File f = findFile(name.replace('.','/'),".class");
         // System.out.println("% Class " + name + (f == null?"":" found in "+f));

        if (f == null) {
            // Обращаюсь к системному загрузчику в случае неудачи
            Class<?> res = findSystemClass(name);
            return res;
        }

        try {
            byte[] classBytes = loadFileAsBytes(f);
            result = defineClass(classBytes, 0, classBytes.length);
        }
        catch (IOException e) {
            throw new ClassNotFoundException("Cannot load class " + name + ": " + e);
        }
        catch (ClassFormatError e) {
            throw new ClassNotFoundException("Format of class file incorrect for class " + name + " : " + e);
        }
        classesHash.put(name,result);
        return result;
    }

    // получаю ссылку на ресурс
    protected java.net.URL findResource(String name) {
        File f = findFile(name, "");
        if (f == null)
            return null;
        try {
            return f.toURL();
        }
        catch(java.net.MalformedURLException e) {
            return null;
        }
    }

    // поиск файла в папке classPath с расширением extension
    private File findFile(String name, String extension) {
        File f;
        for (int k = 0; k < classPath.length; k++) {
            f = new File((new File(classPath[k])).getPath() + File.separatorChar + name.replace('/', File.separatorChar) + extension);
            if (f.exists())
                return f;
        }
        return null;
    }

    // загрузка байтов из файла
    public static byte[] loadFileAsBytes(File file) throws IOException {
        byte[] result = new byte[(int)file.length()];
        FileInputStream f = new FileInputStream(file);
        try {
            f.read(result,0,result.length);
        }
        finally {
            try {
                f.close();
            }
            catch (Exception e) {
            };
        }
        return result;
    }
}
