package life.pisay.ftp.server.io.file.impl;

import life.pisay.ftp.server.io.file.FtpFile;
import life.pisay.ftp.server.user.User;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 本地 FTP 文件
 *
 * @author Viices Cai
 * @time 2021/11/8
 */
public class NativeFtpFile implements FtpFile {

    /**
     * 文件名
     */
    private final String fileName;

    /**
     * 文件实体
     */
    private final File file;

    /**
     * 用户
     */
    private final User user;

    /**
     * 文件拥有者信息
     */
    private final FileOwnerAttributeView ownerAttributeView;

    public NativeFtpFile(String fileName, File file, User user) {
        this.fileName = fileName;
        this.file = file;
        this.user = user;

        if (fileName == null) {
            throw new IllegalArgumentException("filename can not be null");
        }

        if (file == null) {
            throw new IllegalArgumentException("file can not be null");
        }

        if (fileName.length() == 0) {
            throw new IllegalArgumentException("fileName can not be empty");
        }

        if (fileName.charAt(0) != '/') {
            throw new IllegalArgumentException("fileName must be a absolut path");
        }

        Path path = Paths.get(file.toURI());

        this.ownerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);
    }

    @Override
    public String getAbsolutePath() {
        String fullName = fileName;

        int len = fullName.length();

        if ((len != 1) && fullName.charAt(len - 1) == '/') {
            fullName = fullName.substring(0, len - 1);
        }

        return fullName;
    }

    @Override
    public String getName() {

        if ("/".equals(fileName)) {
            return "/";
        }

        String shortName = fileName;
        int len = fileName.length();

        if (shortName.charAt(len - 1) == '/') {
            shortName = shortName.substring(0, len - 1);
        }

        int indexOfSlash = shortName.lastIndexOf('/');

        if (indexOfSlash != -1) {
            shortName = shortName.substring(indexOfSlash + 1);
        }

        return shortName;
    }

    @Override
    public boolean isHidden() {

        return file.isHidden();
    }

    @Override
    public boolean isDirectory() {

        return file.isDirectory();
    }

    @Override
    public boolean isFile() {

        return file.isFile();
    }

    @Override
    public boolean doesExist() {

        return file.exists();
    }

    @Override
    public boolean isReadable() {

        if (!file.exists()) {
            return false;
        }

        return file.canRead();
    }

    @Override
    public boolean isWritable() {

        if (!file.exists()) {
            return false;
        }

        return file.canWrite();
    }

    @Override
    public boolean isRemovable() {

        // 根目录不能被删除
        if ("/".equals(fileName)) {
            return false;
        }

        // 获取全称
        String fullName = this.getAbsolutePath();

        int indexOfSlash = fullName.lastIndexOf('/');

        String parentFullName;
        if (indexOfSlash == 0) {
            parentFullName = "/";

        } else {
            parentFullName = fullName.substring(0, indexOfSlash);
        }

        NativeFtpFile parentFile = new NativeFtpFile(parentFullName, file.getAbsoluteFile().getParentFile(), user);

        return parentFile.isWritable();
    }

    @Override
    public String getOwnerName() {

        try {
            return ownerAttributeView.getOwner().getName();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "user";
    }

    @Override
    public String getGroupName() {

        try {
            return FileSystems.getDefault()
                    .getUserPrincipalLookupService()
                    .lookupPrincipalByGroupName(this.getOwnerName())
                    .getName();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "group";
    }

    @Override
    public Integer getLinkCount() {

        return file.isDirectory() ? 3 : 1;
    }

    @Override
    public Long getLastModified() {

        return file.lastModified();
    }

    @Override
    public boolean setLastModified(Long time) {

        return file.setLastModified(time);
    }

    @Override
    public Long getSize() {

        return file.length();
    }

    @Override
    public Object getPhysicalFile() {

        return file;
    }

    @Override
    public boolean mkdir() {
        boolean res = false;

        if (isWritable()) {
            res = file.mkdir();
        }

        return res;
    }

    @Override
    public boolean delete() {
        boolean res = false;

        if (isWritable()) {
            res = file.delete();
        }

        return res;
    }

    @Override
    public boolean move(FtpFile des) {
        boolean res = false;

        if (des.isWritable() && des.isReadable()) {
            File desFile = ((NativeFtpFile) des).file;

            if (desFile.exists()) {
                res = false;

            } else {
                res = file.renameTo(desFile);
            }
        }

        return res;
    }

    @Override
    public List<? extends FtpFile> listFiles() {

        if (!file.isDirectory()) {
            return null;
        }

        File[] files = file.listFiles();
        if (files == null) {
            return null;
        }

        // 排序
        Arrays.sort(files, Comparator.comparing(File::getName));

        String vFile = getAbsolutePath();
        if (vFile.charAt(vFile.length() - 1) != '/') {
            vFile += '/';
        }

        FtpFile[] vFiles = new FtpFile[files.length];
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            String fileName = vFile + f.getName();

            vFiles[i] = new NativeFtpFile(fileName, f, user);
        }

        return Collections.unmodifiableList(Arrays.asList(vFiles));
    }

    @Override
    public OutputStream createOutputStream(Long offset) throws IOException {

        if (!isWritable()) {
            throw new IOException("Not write permission : " + fileName);
        }

        final RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.seek(offset);
        raf.setLength(offset);

        return new FileOutputStream(raf.getFD()) {

            @Override
            public void close() throws IOException {
                super.close();
                raf.close();
            }
        };
    }

    @Override
    public InputStream createInputStream(Long offset) throws IOException {

        if (!isReadable()) {
            throw new IOException("Not read permission : " + file.getName());
        }

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(offset);

        return new FileInputStream(raf.getFD()) {

            @Override
            public void close() throws IOException {
                super.close();
                raf.close();
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NativeFtpFile) {
            String thisCanonicalPath;
            String otherCanonicalPath;

            try {
                thisCanonicalPath = this.file.getCanonicalPath();
                otherCanonicalPath = ((NativeFtpFile) obj).file.getCanonicalPath();

            } catch (IOException e) {
                throw new RuntimeException("Failed to get the canonical path", e);
            }

            return thisCanonicalPath.equals(otherCanonicalPath);
        }

        return false;
    }

    @Override
    public int hashCode() {
        try {
            return file.getCanonicalPath().hashCode();

        } catch (IOException e) {

            return 0;
        }
    }
}
