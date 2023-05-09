package life.pisay.ftp.server.core.impl;

import life.pisay.ftp.server.core.DataConnection;
import life.pisay.ftp.server.core.DataConnectionFactory;
import life.pisay.ftp.server.core.FtpSession;
import life.pisay.ftp.server.enums.FtpDataType;
import life.pisay.ftp.server.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * IO 数据传输类
 *
 * @author Viices Cai
 * @time 2021/11/8
 */
public class IODataConnection implements DataConnection {

    private final Logger LOG = LoggerFactory.getLogger(IODataConnection.class);

    private static final byte[] EOL = System.getProperty("line.separator").getBytes();

    private final Socket socket;

    private final DataConnectionFactory factory;

    public IODataConnection(Socket socket, IODataConnectionFactory factory) {
        this.socket = socket;
        this.factory = factory;
    }

    /**
     * 从客户端接收数据
     *
     * @param session FTP 会话
     * @param out     输出流
     * @return 传输数据的长度
     * @throws IOException IO 异常
     */
    @Override
    public Long transferFromClient(FtpSession session, OutputStream out) throws IOException {

        InputStream in = getDataInputStream();

        try {
            return onTransfer(session, false, in, out);

        } finally {
            IOUtils.close(in);
        }
    }

    /**
     * 将数据传输到客户端
     *
     * @param session FTP 会话
     * @param in      输入流
     * @return 传输数据的长度
     * @throws IOException IO异常
     */
    @Override
    public Long transferToClient(FtpSession session, InputStream in) throws IOException {
        OutputStream out = getDataOutputStream();

        try {
            return onTransfer(session, true, in, out);

        } finally {
            IOUtils.close(out);
        }
    }

    /**
     * 传输字符串到客户端
     *
     * @param session FTP 会话
     * @param str     字符串
     * @throws IOException IO异常
     */
    @Override
    public void transferToClient(FtpSession session, String str) throws IOException {
        OutputStream out = getDataOutputStream();

        // 字符输入流
        Writer writer = null;

        try {
            writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            writer.write(str);

        } finally {
            if (writer != null) {
                writer.flush();
            }

            IOUtils.close(writer);
        }
    }

    /**
     * 传输逻辑
     *
     * @param session FTP 会话
     * @param isWrite 是否可写
     * @param in 输入流
     * @param out 输出流
     * @return 传输字节大小
     * @throws IOException IO 异常
     */
    private Long onTransfer(FtpSession session, Boolean isWrite,  InputStream in, OutputStream out) throws IOException {

        // 传输数据的大小
        long transferSize = 0L;

        // 是否为二进制
        boolean isAscii = session.getDataType() == FtpDataType.ASCII;

        // 缓冲区
        byte[] buffer = new byte[1024 * 8];

        BufferedInputStream bis;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(in);
            bos = new BufferedOutputStream(out);

            byte lastByte = 0;

            while (true) {
                int count = bis.read(buffer);

                if (count == -1) {
                    break;
                }

                if (isAscii) {
                    for (int i = 0; i < count; ++i) {
                        byte b = buffer[i];

                        if(isWrite) {
                            if (b == '\n' && lastByte != '\r') {
                                bos.write('\r');
                            }

                            bos.write(b);

                        } else {
                            if(b == '\n') {
                                // 读取的内容总是以 \r\n 为结尾
                                if (lastByte != '\r'){
                                    bos.write(EOL);
                                }

                            } else if(b == '\r') {
                                bos.write(EOL);

                            } else {
                                // 不是行尾，直接输出
                                bos.write(b);
                            }
                        }
                        // 用于结束符的比较
                        lastByte = b;
                    }

                } else {
                    bos.write(buffer, 0, count);
                }

                transferSize += count;
            }
        } catch (IOException e) {
            LOG.warn("Exception during data transfer, closing data connection socket", e);

        } finally {
            if (bos != null) {
                bos.flush();
            }
        }

        return transferSize;
    }

    /**
     * 获取数据输入流
     *
     * @return 数据输入流
     * @throws IOException IO 异常
     */
    private InputStream getDataInputStream() throws IOException {
        try {
            Socket dataSocket = socket;

            if (dataSocket == null) {
                throw new IOException("Cannot open data connection.");
            }

            return dataSocket.getInputStream();

        } catch (IOException e) {
            factory.close();
            throw e;
        }
    }

    /**
     * 获取数据输出流
     *
     * @return 数据输出流
     * @throws IOException IO 异常
     */
    private OutputStream getDataOutputStream() throws IOException {
        try {
            Socket dataSocket = socket;

            if (dataSocket == null) {
                throw new IOException("Cannot open data connection.");
            }

            return dataSocket.getOutputStream();

        } catch (IOException e) {
            factory.close();

            throw e;
        }
    }
}
