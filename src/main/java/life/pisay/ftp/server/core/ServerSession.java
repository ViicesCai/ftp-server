package life.pisay.ftp.server.core;

import life.pisay.ftp.server.utils.OSUtils;

/**
 * 服务会话：存储服务产生的数据
 *
 * @author Viices Cai
 * @time 2021/11/18
 */
public class ServerSession {

	/**
	 * 系统类型
	 */
	private String systemType;

	/**
	 * 数据传输构造器：用于分发数据传输的端口
	 */
	private final DataTransferGenerator dataTransferGenerator;
	
    public ServerSession(DataTransferGenerator dataTransferGenerator) {
        super();
        systemType = OSUtils.getSystemName();
        this.dataTransferGenerator = dataTransferGenerator;
    }
    
	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public DataTransferGenerator getDataTransferGenerator() {
		return dataTransferGenerator;
	}
}
