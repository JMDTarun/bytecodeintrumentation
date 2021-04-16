package org.javabenchmark.instrumentation;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.txw2.annotation.XmlCDATA;

@XmlRootElement(name = "logConfiguration")
public class LogConfiguration {

	private String file;
	private String level;
	private String pattern;
	private String logPattern;
	private int maxLines;
	private long minLogTime;

	public String getLogPattern() {
		return logPattern;
	}

	@XmlElement(name = "logPattern")
	@XmlCDATA
	public void setLogPattern(String logPattern) {
		this.logPattern = logPattern;
	}

	public int getMaxLines() {
		return maxLines;
	}

	@XmlElement(name = "maxLines")
	public void setMaxLines(int maxLines) {
		this.maxLines = maxLines;
	}

	public long getMinLogTime() {
		return minLogTime;
	}

	@XmlElement(name = "minLogTime")
	public void setMinLogTime(long minLogTime) {
		this.minLogTime = minLogTime;
	}

	public String getFile() {
		return file;
	}

	@XmlAttribute(name = "file")
	public void setFile(String file) {
		this.file = file;
	}

	public String getLevel() {
		return level;
	}

	@XmlAttribute(name = "level")
	public void setLevel(String level) {
		this.level = level;
	}

	public String getPattern() {
		return pattern;
	}

	@XmlAttribute(name = "pattern")
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
