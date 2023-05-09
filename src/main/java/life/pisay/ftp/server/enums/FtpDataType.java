package life.pisay.ftp.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * FTP 数据类型
 *
 * @author Viices Cai
 * @time 2021/11/5
 */
@Getter
@AllArgsConstructor
public enum FtpDataType {

    /**
     * ASCII码类型数据
     */
    ASCII('a'),

    /**
     * 二进制类型数据
     */
    BINARY('i');

    /**
     * 编码
     */
    private final char code;

    /**
     * 根据编码获取数据类型
     *
     * @param code 编码
     * @return FTP 数据类型
     */
    public static FtpDataType getValue(char code) {
        code = Character.toLowerCase(code);

        for (FtpDataType dataType : FtpDataType.values()) {
            if (dataType.getCode() == code) {

                return dataType;
            }
        }

        throw new IllegalArgumentException("Unknown data type : " + code);
    }
}
