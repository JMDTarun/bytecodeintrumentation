package org.javabenchmark.instrumentation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "configurations")
@XmlAccessorType(XmlAccessType.FIELD)
public class Configurations {

	private List<Configuration> configuration;
	private LogConfiguration logConfiguration;
	private DefaultCode defaultCode;

	public DefaultCode getDefaultCode() {
		return defaultCode;
	}

	public void setDefaultCode(DefaultCode defaultCode) {
		this.defaultCode = defaultCode;
	}

	public List<Configuration> getConfiguration() {
		return configuration;
	}

	public void setConfiguration(List<Configuration> configuration) {
		this.configuration = configuration;
	}

	public LogConfiguration getLogConfiguration() {
		return logConfiguration;
	}

	public void setLogConfiguration(LogConfiguration logConfiguration) {
		this.logConfiguration = logConfiguration;
	}

}
