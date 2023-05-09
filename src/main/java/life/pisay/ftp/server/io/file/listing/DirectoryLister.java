package life.pisay.ftp.server.io.file.listing;

import life.pisay.ftp.server.io.file.FileSystemView;
import life.pisay.ftp.server.io.file.FtpFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 目录列表
 *
 * @author Viices Cai
 * @time 2021/11/8
 */
public class DirectoryLister {

    public String listFiles(String fileName, FileSystemView fileSystemView, final FileFormatter formatter) {
        StringBuilder builder = new StringBuilder();

        List<? extends FtpFile> files = listFiles(fileSystemView, fileName);
        if (files != null) {
            builder.append(onList(files, formatter));
        }

        return builder.toString();
    }

    private List<? extends FtpFile> listFiles(FileSystemView fileSystemView, String file) {
        List<? extends FtpFile> files;

        FtpFile vFile = fileSystemView.getFile(file);

        if (vFile.isFile()) {
            List<FtpFile> vFiles = new ArrayList<>();
            vFiles.add(vFile);
            files = vFiles;

        } else {
            files = vFile.listFiles();
        }

        return files;
    }

    private String onList(final List<? extends FtpFile> files, final FileFormatter formatter) {
        StringBuilder builder = new StringBuilder();

        for (FtpFile file : files) {
            if (file == null) {
                continue;
            }

            builder.append(formatter.format(file));
        }

        return builder.toString();
    }
}
