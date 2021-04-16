package org.javabenchmark.instrumentation;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class SleepingClassFileTransformer implements ClassFileTransformer {

	List<String> addedClasses = new ArrayList<String>();
	private static List<String> classes = new ArrayList<String>();
	private static Map<String, List<Method>> customInterfaceMap = new HashMap<String, List<Method>>();
	private static List<String> location = new ArrayList<String>();

	String def = "private static org.apache.log4j.Logger _logInstrumentation;";
	String ifLog = "if (_log.isLoggable(java.util.logging.Level.INFO))";
	private static String logFile = "defaultLogFile";
	private static String logFormat = "%m%n";
	private static String logFilePattern = "com.mmt";
	private static int maxLines = -1;
	private static long minLogTime = -1;
	private static String codeBefore = null;
	private static String codeAfter = null;
	// private static Pattern p = null;

	static {
		String agentConfiguration = System.getProperty("agent.Configuration");
		try {
			// BasicConfigurator.configure();
			File file = new File(agentConfiguration);
			JAXBContext jaxbContext = JAXBContext.newInstance(Configurations.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Configurations configurations = (Configurations) jaxbUnmarshaller.unmarshal(file);
			if (configurations != null && configurations.getConfiguration() != null && configurations.getConfiguration().size() > 0) {
				for (Configuration configuration : configurations.getConfiguration()) {
					if (configuration.getLocation() != null) {
						location.add(configuration.getLocation());
					}
					if (configuration.getClassName() != null) {
						classes.add(configuration.getClassName());
					}
					if (configuration.getInterfaceName() != null) {
						customInterfaceMap.put(configuration.getInterfaceName(), configuration.getMethod());
					}
				}
				if (configurations.getLogConfiguration() != null) {
					LogConfiguration logConfiguration = configurations.getLogConfiguration();
					if (logConfiguration.getFile() != null) {
						logFile = logConfiguration.getFile();
					}
					if (logConfiguration.getPattern() != null) {
						logFormat = logConfiguration.getPattern();
					}
					if (logConfiguration.getLogPattern() != null) {
						logFilePattern = logConfiguration.getLogPattern();
						// p =
						// Pattern.compile(logConfiguration.getLogPattern());
					}
					if (logConfiguration.getMaxLines() > 0) {
						maxLines = logConfiguration.getMaxLines();
					}
					if (logConfiguration.getMinLogTime() > 0) {
						minLogTime = logConfiguration.getMinLogTime();
					}
					if (configurations.getDefaultCode() != null) {
						if (configurations.getDefaultCode().getAfter() != null && configurations.getDefaultCode().getAfter().getCode() != null) {
							codeAfter = configurations.getDefaultCode().getAfter().getCode();
						}
						if (configurations.getDefaultCode().getBefore() != null && configurations.getDefaultCode().getBefore().getCode() != null) {
							codeBefore = configurations.getDefaultCode().getBefore().getCode();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
			throws IllegalClassFormatException {

		byte[] byteCode = classfileBuffer;

		if (classes != null && classes.size() > 0 && classes.contains(className)) {

			try {
				ClassPool cp = new ClassPool(true);
				if (location != null && location.size() > 0) {
					for (String str : location) {
						cp.appendClassPath(str);
					}
				}
				CtClass cc = null;
				String classNameStr = className.replaceAll("/", ".");
				try {
					cc = cp.get(classNameStr);
				} catch (Exception e) {
					Class.forName(classNameStr, true, ClassLoader.getSystemClassLoader());
					cc = cp.get(classNameStr);
				}
				// cc.addField(f);

				CtField field = CtField.make(def, cc);
				String getLogger = "org.apache.log4j.Logger.getLogger(\"instrumentedLogger\");";
				cc.addField(field, getLogger);

				CtField field1 = CtField.make("private static org.apache.log4j.PatternLayout _layout;", cc);
				cc.addField(field1, "new org.apache.log4j.PatternLayout(\"" + logFormat + "\");");

				CtField field2 = CtField.make("private static org.apache.log4j.RollingFileAppender _rfa;", cc);
				cc.addField(field2, "new org.apache.log4j.RollingFileAppender(_layout, \"" + logFile + "\");");

				if (logFilePattern != null) {
					CtField field3 = CtField.make("private static java.util.regex.Pattern _patternInstrumentation;", cc);
					cc.addField(field3, "java.util.regex.Pattern.compile(\"" + logFilePattern + "\"); ");
				}

				CtClass[] interfaces = cc.getInterfaces();

				if (interfaces != null && interfaces.length > 0) {
					for (CtClass ctClass : interfaces) {
						if (customInterfaceMap != null && customInterfaceMap.size() > 0
								&& (customInterfaceMap.containsKey(ctClass.getName()) || customInterfaceMap.containsKey(ctClass.getName().replaceAll("/", ".")))) {
							CtMethod[] methods = cc.getDeclaredMethods();

							if (methods != null && methods.length > 0) {
								for (CtMethod m : methods) {
									Map<String, Method> methodNameCodeMap = new HashMap<String, Method>();
									List<Method> methodsList = customInterfaceMap.get(ctClass.getName());
									if (methodsList != null && methodsList.size() > 0) {
										for (Method method : methodsList) {
											if (method.getName() != null) {
												methodNameCodeMap.put(method.getName(), method);
											}
										}

									}
									if (methodsList != null && methodsList.size() > 0) {
										if (methodNameCodeMap.containsKey(m.getName())) {
											m.addLocalVariable("maxLinesInstumentation", CtClass.intType);
											m.insertBefore("maxLinesInstumentation = " + maxLines + ";");

											m.addLocalVariable("minLogTimeInstumentation", CtClass.longType);
											m.insertBefore("minLogTimeInstumentation = " + minLogTime + ";");

											m.insertBefore("_logInstrumentation.addAppender(_rfa);");
											m.addLocalVariable("elapsedTime", CtClass.longType);
											m.insertBefore("elapsedTime = System.currentTimeMillis();");
											if (codeBefore != null) {
												m.insertBefore(codeBefore);
											}
											m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;}");
											m.insertAfter("_logInstrumentation.info(\"Elapsed Time ..... \"+elapsedTime);");
											// m.insertAfter("{java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace(); int k=0; for(int i=0;i<stackTrace.length; i++) { if(_patternInstrumentation != null) {java.util.regex.Matcher m = _patternInstrumentation.matcher(stackTrace[i].toString()); if(m.matches()) { System.out.println(\"caliong &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& \"+stackTrace[i]+\" i is \"+i); k++; _logInstrumentation.info(stackTrace[i]); } } else {_logInstrumentation.info(stackTrace[i]);} } }");

											if (codeAfter != null) {
												m.insertAfter(codeAfter);
											}

											Method method = methodNameCodeMap.get(m.getName());
											if (method.getCode() != null) {
												if (method.getCodeInject() != null) {
													if (method.getCodeInject().equals("after")) {
														m.insertAfter(method.getCode());
													} else {
														m.insertBefore(method.getCode());
													}
												} else {
													m.insertBefore(method.getCode());
												}
											}
										}
									} else {
										m.addLocalVariable("elapsedTime", CtClass.longType);
										m.insertBefore("elapsedTime = System.currentTimeMillis();");
										m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;}");
										m.insertAfter("_logInstrumentation.info(\"Elapsed Time ..... \"+elapsedTime);");
										// m.insertAfter("{java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace(); for(int i=0;i<stackTrace.length; i++) {_logInstrumentation.info(stackTrace[i]);} }");
									}
								}
							}
						}
					}
				} else {

				}
				byteCode = cc.toBytecode();
				cc.detach();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return byteCode;
	}

}
