package life.pisay.ftp.server.io.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * FTP 文件接口
 *
 * @author Viices Cai
 * @time 2021/11/2
 */
public interface FtpFile {

    /**
     * 获取绝对路径
     *
     * @return 路径分隔符为'/'的路径
     */
    String getAbsolutePath();

    /**
     * 获取文件名称
     *
     * @return 文件名称
     */
    String getName();

    /**
     * 是否隐藏
     *
     * @return true:隐藏
     */
    boolean isHidden();

    /**
     * 是否为目录
     *
     * @return true:目录
     */
    boolean isDirectory();

    /**
     * 是否为文件
     *
     * @return true:文件
     */
    boolean isFile();

    /**
     * 是否存在
     *
     * @return true:存在
     */
    boolean doesExist();

    /**
     * 是否可读
     *
     * @return true:可读
     */
    boolean isReadable();

    /**
     * 是否可写
     *
     * @return true:可写
     */
    boolean isWritable();

    /**
     * 是否可删除
     *
     * @return true:可删除
     */
    boolean isRemovable();

    /**
     * 获取所有者名称
     *
     * @return 文件所有者名称
     */
    String getOwnerName();

    /**
     * 获取所有者组名
     *
     * @return 文件所有者的组织名称
     */
    String getGroupName();

    /**
     * 获取链接数
     *
     * @return 文件链接数
     */
    Integer getLinkCount();

    /**
     * 获取最后修改时间(UTC 格式)
     *
     * @return 文件最后一次修改的时间戳
     */
    Long getLastModified();

    /**
     * 设置文件最后一次修改的时间戳
     *
     * @param time 文件最后一次修改的时间(ms)
     * @return 修改结果
     */
    boolean setLastModified(Long time);

    /**
     * 获取文件大小
     *
     * @return 文件大小(byte)
     */
    Long getSize();

    /**
     * 获取文件的物理位置或路径
     *
     * @return 文件的物理位置或路径
     */
    Object getPhysicalFile();

    /**
     * 创建目录
     *
     * @return true:创建成功
     */
    boolean mkdir();

    /**
     * 删除文件
     *
     * @return true:删除成功
     */
    boolean delete();

    /**
     * 移动文件
     *
     * @param des 目的目录
     * @return true:移动成功
     */
    boolean move(FtpFile des);

    /**
     * 列出文件
     * 如果不是目录或不存在，则返回 null
     * 文件必须按字母顺序返回
     * List必须是不可变的
     *
     * @return 文件列表
     */
    List<? extends FtpFile> listFiles();

    /**
     * 创建用于写入的输出流
     *
     * @param offset 从何处开始写入的字节数
     * @return 文件输出流
     * @throws IOException 如果文件不是随机可访问的，那么除了0之外的任何偏移量都会抛出异常
     */
    OutputStream createOutputStream(Long offset) throws IOException;

    /**
     * 创建用于读取的输入流
     *
     * @param offset 从何处开始读取的字节数
     * @return 文件输入流
     * @throws IOException 如果文件不是随机可访问的，那么除了0之外的任何偏移量都会抛出异常
     */
    InputStream createInputStream(Long offset) throws IOException;
}
