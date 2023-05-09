package life.pisay.ftp.server.utils;

import java.io.File;
import java.util.StringTokenizer;

/**
 * 文件系统工具类
 * 
 * @author Viices Cai
 * @time 2021/12/15
 */
public class FileSystemUtils {
	
	/**
     * 默认根路径
     */
    private static final String DEFAULT_ROOT_PATH = "/";
	
	 /**
     * 统一文件路径的分隔符
     *
     * @param path 文件路径
     * @return 分隔符一致的文件路径
     */
    public static String generalSeparator(String path) {
        String normalPath = path.replace(File.separatorChar, '/');
        normalPath = normalPath.replace('\\', '/');

        return normalPath;
    }

    /**
     * 统一化文件路径
     *
     * @param path 文件路径
     * @param defaultPath 默认文件路径
     * @return 统一化的文件路径
     */
    public static String general(String path, String defaultPath) {
        if (path == null || path.trim().length() == 0) {
            path = defaultPath;
        }

        return generalSeparator(prependSlash(appendSlash(path)));
    }

    /**
     * 为文件路径末尾添加分隔符 '/'
     *
     * @param path 文件路径
     * @return 末尾带分隔符的文件路径
     */
    public static String appendSlash(String path) {
        if (path.charAt(path.length() - 1) != '/') {
            return path + '/';

        } else {
            return path;
        }
    }

    /**
     * 为文件路径首部添加分隔符 '/'
     *
     * @param path 文件路径
     * @return 首部带分隔符的文件路径
     */
    public static String prependSlash(String path) {
        if (path.charAt(0) != '/') {
            return path + '/';

        } else {
            return path;
        }
    }

    /**
     * 去除文件路径末尾的分隔符 '/'
     *
     * @param path 文件路径
     * @return 末尾不包含分隔符的文件路径
     */
    public static String trimTrailingSlash(String path) {
        if (path.charAt(path.length() - 1) == '/') {
            return path.substring(0, path.length() - 1);

        } else {
            return path;
        }
    }

    /**
     * 获取实际的文件名称
     *
     * @param rootDir 根目录
     * @param currDir 当前目录
     * @param fileName 文件名称
     * @return 实际的文件名
     */
    public static String getActualFileName(String rootDir, String currDir, String fileName) {
        String generalRootDir = generalSeparator(rootDir);
        generalRootDir = appendSlash(generalRootDir);

        String generalFileName = generalSeparator(fileName);

        String res;
        // 如果文件名是相对
        if (generalFileName.charAt(0) != '/') {
            String generalCurrDir = general(currDir, DEFAULT_ROOT_PATH);
            res = generalRootDir + generalCurrDir.substring(1);

        } else {
            res = generalRootDir;
        }

        // 去除路径末尾的分隔符
        res = trimTrailingSlash(res);

        // 按 '/' 分割字符串
        StringTokenizer tokenizer = new StringTokenizer(generalFileName, "/");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            // . : 当前目录
            if (".".equals(token)) {
                // 忽略

                // .. : 父级目录
            } else if ("..".equals(token)) {
                // 如果不是根，获取父级目录
                if (res.startsWith(generalRootDir)) {
                    int slashIndex = res.lastIndexOf('/');

                    if (slashIndex != -1) {
                        res = res.substring(0, slashIndex);
                    }
                }
                // ~ : 主目录，即：根目录
            } else if ("~".equals(token)) {
                res = trimTrailingSlash(generalRootDir);

            } else {
                res = res + '/' + token;
            }
        }

        // 判断是否需要在末尾添加 '/'
        if ((res.length()) + 1 == generalRootDir.length()) {
            res += '/';
        }

        // 确保不包含根目录
        if (!res.startsWith(generalRootDir)) {
            res = generalRootDir;
        }

        return res;
    }
	
	private FileSystemUtils() { }
}
