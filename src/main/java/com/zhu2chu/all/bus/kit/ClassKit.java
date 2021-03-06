package com.zhu2chu.all.bus.kit;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.PathKit;

public class ClassKit {

	private static final Logger log = LoggerFactory.getLogger(ClassKit.class);

	public static <T> List<Class<T>> scanSubClass(Class<T> pclazz) {
		return scanSubClass(pclazz, false);
	}

	public static <T> List<Class<T>> scanSubClass(Class<T> pclazz, boolean mustbeCanNewInstance) {
		if (pclazz == null) {
		    if (log.isTraceEnabled()) {
		        log.trace("scanClass: parent class is null");
		    }
			return null;
		}

		List<File> classFileList = new ArrayList<File>();
		scanClass(classFileList, PathKit.getRootClassPath());

		List<Class<T>> classList = new ArrayList<Class<T>>();
		for (File file : classFileList) {

			int start = PathKit.getRootClassPath().length();
			int end = file.toString().length() - 6; // 6 == ".class".length();

			String classFile = file.toString().substring(start + 1, end);
			Class<T> clazz = classForName(classFile.replace(File.separator, "."));

			if (clazz != null && pclazz.isAssignableFrom(clazz)) {
				if (mustbeCanNewInstance) {
					if (clazz.isInterface())
						continue;

					if (Modifier.isAbstract(clazz.getModifiers()))
						continue;
				}
				classList.add(clazz);
			}
		}

		File jarsDir = new File(PathKit.getWebRootPath() + "/WEB-INF/lib");
		if (jarsDir.exists() && jarsDir.isDirectory()) {
			File[] jarFiles = jarsDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					String name = pathname.getName().toLowerCase();
					return name.endsWith(".jar");
				}
			});

			if (jarFiles != null && jarFiles.length > 0) {
				for (File f : jarFiles) {
					classList.addAll(scanSubClass(pclazz, f, mustbeCanNewInstance));
				}
			}
		}

		return classList;
	}

	public static <T> List<Class<T>> scanSubClass(Class<T> pclazz, File f, boolean mustbeCanNewInstance) {
		if (pclazz == null) {
		    if (log.isWarnEnabled()) {
		        log.warn("scanClass: parent clazz is null");
		    }
			return null;
		}

		JarFile jarFile = null;

		try {
			jarFile = new JarFile(f);
			List<Class<T>> classList = new ArrayList<Class<T>>();
			Enumeration<JarEntry> entries = jarFile.entries();

			while (entries.hasMoreElements()) {
				JarEntry jarEntry = entries.nextElement();
				String entryName = jarEntry.getName();
				if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
					String className = entryName.replace("/", ".").substring(0, entryName.length() - 6);
					Class<T> clazz = classForName(className);
					if (clazz != null && pclazz.isAssignableFrom(clazz)) {
						if (mustbeCanNewInstance) {
							if (clazz.isInterface())
								continue;

							if (Modifier.isAbstract(clazz.getModifiers()))
								continue;
						}
						classList.add(clazz);
					}
				}
			}

			return classList;

		} catch (IOException e1) {
		} finally {
			if (jarFile != null)
				try {
					jarFile.close();
				} catch (IOException e) {
				}
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> classForName(String className) {
		Class<T> clazz = null;
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			clazz = (Class<T>) Class.forName(className, false, cl);
		} catch (Throwable e) {
		    if (log.isTraceEnabled()) {
		        log.trace("classForName is error，className:" + className);
		    }
		}
		return clazz;
	}

	private static void scanClass(List<File> fileList, String path) {
		File files[] = new File(path).listFiles();
		if (null == files || files.length == 0)
			return;
		for (File file : files) {
			if (file.isDirectory()) {
				scanClass(fileList, file.getAbsolutePath());
			} else if (file.getName().endsWith(".class")) {
				fileList.add(file);
			}
		}
	}

	/**
	 * 取得ClassLoader，依次获取
	 * @return
	 */
	public static ClassLoader getClassLoader() {
        ClassLoader classLoader = getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassKit.class.getClassLoader();
            if(null == classLoader){
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }

	/**
     * @return 当前线程的class loader
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获得资源的URL
     * 
     * @param resource 资源（相对Classpath的路径）
     * @return 资源URL
     */
    public static URL getURL(String resource) {
        return ClassKit.getClassLoader().getResource(resource);
    }

    /**
     * 获取ClassPathURL
     * @return
     */
    public static URL getClassPathURL() {
        return getURL("");
    }

    /**
     * 获得ClassPath
     * 
     * @return ClassPath
     */
    public static String getClassPath() {
        try {
            String path = getClassPathURL().toURI().getPath();
            return new File(path).getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
