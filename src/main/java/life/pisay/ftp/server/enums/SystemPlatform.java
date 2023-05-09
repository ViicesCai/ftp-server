package life.pisay.ftp.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统平台
 *
 * @author Viices Cai
 * @time 2021/11/18
 */
@Getter
@AllArgsConstructor
public enum SystemPlatform {

    ANY("Any"),
    LINUX("Linux"),
    MAC_OS("Mac OS"),
    MAC_OS_X("Mac OS X"),
    WINDOWS("Windows NT"),
    OS2("OS/2"),
    SOLARIS("Solaris"),
    SUNOS("SunOS"),
    MPEIX("MPE/iX"),
    HP_UX("HP_UX"),
    AIX("AIX"),
    OS390("OS/390"),
    FREEBSD("FreeBSD"),
    IRIX("Irix"),
    DIGITAL_UNIX("Digital Unix"),
    NETWARE_411("NetWare"),
    OSF1("OSF1"),
    OPENVMS("OpenVms"),
    OTHERS("Others");

    /**
     * 系统平台信息
     */
    private final String info;

    /**
     * 根据系统平台信息获取系统平台
     *
     * @param info 系统平台信息
     * @return 系统平台
     */
    public static SystemPlatform getValue(String info) {

        int splitIndex = info.indexOf(' ');
        info = info.substring(0, splitIndex);

        for (SystemPlatform platform : SystemPlatform.values()) {

            if (info.contains(platform.info)) {

                return platform;
            }
        }

        return OTHERS;
    }
}
