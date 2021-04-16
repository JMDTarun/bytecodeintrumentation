package org.javabenchmark.instrumentation;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class CopyOfSleepingClassFileTransformer implements ClassFileTransformer {

	List<String> addedClasses = new ArrayList<String>();

	private static List<String> classes = new ArrayList<String>();
	// private static List<String> interfaces = new ArrayList<String>();
	private static Map<String, List<String>> interfaces = new HashMap<String, List<String>>();
	private static List<String> location = new ArrayList<String>();

	static {
		String agentConfiguration = System.getProperty("agent.Configuration");
		System.out.println(" @@@@@@@.......@@@@@@@@@ " + agentConfiguration);
		try {
			File file = new File(agentConfiguration);
			JAXBContext jaxbContext1 = JAXBContext.newInstance(Configurations.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
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
						interfaces.put(configuration.getInterfaceName(), configuration.getMethodName());
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(configurationsObj);

	}

	public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
			throws IllegalClassFormatException {

		byte[] byteCode = classfileBuffer;

		// try {
		// File file = new File("C:/Users/mmt5133/Desktop/test.log");
		//
		// // if file doesnt exists, then create it
		// if (!file.exists()) {
		// file.createNewFile();
		// }
		//
		// FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		// BufferedWriter bw = new BufferedWriter(fw);
		// bw.write(className);
		// bw.write("\n");
		// bw.close();
		//
		// System.out.println("Done");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		if (className.contains("org/javabenchmark/instrumentation") || className.contains("org/springframework/data/redis/core/DefaultHashOperations")
				|| className.contains("org/apache/solr/client/solrj/SolrServer")) {

			try {
				System.out.println("Loading Class...............$$$$$$$$$$$$$$$$$$$$$$$$$$################### " + className);
				System.out.println("Class Pool......");
				System.out.println(ClassPool.getDefault());
				System.out.println("Testing.....");
				// ClassPool cp = ClassPool.getDefault();
				ClassPool cp = new ClassPool(true);
				// ClassClassPath classPath = new
				// ClassClassPath(this.getClass());
				// System.out.println("Classpath$$$$$$$$ "+classPath.toString());
				// cp.insertClassPath(classPath);

				// cp.appendClassPath("C:/Users/mmt5133/.m2/repository/*");
				// cp.insertClassPath("C:/Users/mmt5133/.m2/repository/*");
				// cp.insertClassPath("C:/Users/mmt5133/.m2/repository/*");
				// cp.appendClassPath("C:/Users/mmt5133/.m2/repository/org/springframework/data/spring-data-redis/1.0.1.RELEASE/spring-data-redis-1.0.1.RELEASE.jar");
				// cp.appendClassPath("F:/Softwares/apache-tomcat-7.0.47/wtpwebapps/HotelsSOA/WEB-INF/lib/HotelsCoreEngine-1.2.jar");
				// cp.appendClassPath("C:/Users/mmt5133/.m2/repository/org/apache/solr/solr-solrj/3.5.0/solr-solrj-3.5.0-sources.jar");
				cp.appendPathList("C:/Users/mmt5133/.m2/repository/org/apache/solr/solr-solrj/3.5.0/solr-solrj-3.5.0-sources.jar;C:/Users/mmt5133/.m2/repository/org/springframework/data/spring-data-redis/1.0.1.RELEASE/spring-data-redis-1.0.1.RELEASE.jar;F:/Softwares/apache-tomcat-7.0.47/wtpwebapps/HotelsSOA/WEB-INF/lib/HotelsCoreEngine-1.2.jar");

				// cp.appendClassPath("C:/Users/mmt5133/.m2/repository/org/apache/solr/solr-solrj/3.5.0/solr-solrj-3.5.0-sources.jar");

				// setJarInClasspath("C:/Users/mmt5133/.m2/repository", cp);
				// System.out.println("Cp is................. "+cp);
				// System.out.println("Getting Class from pool.... "+cp.get("org.springframework.data.redis.core.RedisOperations"));
				// int index = className.indexOf("$");
				// if (index > -1) {
				// className = className.substring(0, index);
				// }
				CtClass cc = cp.get(className.replaceAll("/", "."));
				System.out.println("CtClass object..... " + cc);
				System.out.println("Class Loaded........$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ");
				CtClass[] interfaces = cc.getInterfaces();

				if (interfaces != null && interfaces.length > 0) {
					for (CtClass ctClass : interfaces) {
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + ctClass.getName());

						// Thread.currentThread().getStackTrace();
						//
						// ThreadGroup rootGroup =
						// Thread.currentThread().getThreadGroup();
						// ThreadGroup parent;
						// while ((parent = rootGroup.getParent()) != null) {
						// rootGroup = parent;
						// }
						//
						// listThreads(rootGroup, "");

						if (ctClass.getName().equals("org.javabenchmark.instrumentation.CustomInterface")
								|| ctClass.getName().equals("org/springframework/data/redis/core/HashOperations")
								|| ctClass.getName().equals("org.springframework.data.redis.core.HashOperations") || ctClass.getName().equals("com.mmt.solr.ISolrClient")
								|| ctClass.getName().equals("com/mmt/solr/ISolrClient") || ctClass.getName().equals("java.io.Serializable")
								|| ctClass.getName().equals("java/io/Serializable")) {
							addedClasses.add(ctClass.getName());
							// CtClass[] interfaces = cc.getInterfaces();
							// if (interfaces != null && interfaces.length > 0)
							// {
							// for (CtClass inte : interfaces) {
							// System.out.println("!!!!!@@@@@@ " +
							// inte.getName());
							// }
							// }
							System.out.println("Class can be assigned to Custom Iterface .............. " + className);
							// if (!addedClasses.contains(ctClass.getName())) {
							CtMethod[] methods = cc.getDeclaredMethods();

							if (methods != null && methods.length > 0) {
								System.out.println("Method Array Length ..... " + methods.length);
								for (CtMethod m : methods) {
									System.out.println("Method Name.................................... " + m.getName());
									System.out.println("Adding local variable.........................");
									m.addLocalVariable("elapsedTime", CtClass.longType);
									System.out.println("Added local variable.......................");
									System.out.println("Assigning local variable.................");
									m.insertBefore("elapsedTime = System.currentTimeMillis();");
									System.out.println("Assigned local variable.................");
									m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;" + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");
									m.insertAfter("{try{java.io.File file = new java.io.File(\"C:/Users/mmt5133/Desktop/test.log\");  if (!file.exists()) {file.createNewFile();} java.io.FileWriter fw = new java.io.FileWriter(file.getAbsoluteFile(), true); java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);bw.write(\"Method execution time is...... \");bw.write(String.valueOf(elapsedTime));bw.write(\"\\\n\");bw.close();System.out.println(\"Done\");java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace(); for(int i=0;i<stackTrace.length; i++) {System.out.println(stackTrace[i]);}} }");
								}
							}
							// }

						}
					}
				} else {

				}

				// System.out.println(cc.getInterfaces());
				// try {
				// System.out.println("Inside Try...................");
				// try {
				// System.out.println("Trying to load class");
				// // Class<?> loadClass = loader.loadClass(className);
				// // ClassLoader myClassLoader =
				// // ClassLoader.getSystemClassLoader();
				//
				// // Step 2: Define a class to be loaded.
				//
				// // String classNameToBeLoaded =
				// // "net.viralpatel.itext.pdf.DemoClass";
				// //Class c = Class.forName(className.replaceAll("/", "."));
				// System.out.println(classBeingRedefined);
				// // Step 3: Load the class
				//
				// // Class myClass = myClassLoader.loadClass(className);
				//
				// // System.out.println("@@@@@@@@@@@@@ "+
				// // myClass.getName());
				// System.out.println("Why????????????");
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				//
				// if (cc.getClass()
				// .isAssignableFrom(
				// org.javabenchmark.instrumentation.CustomInterface.class)) {
				// // CtClass[] interfaces = cc.getInterfaces();
				// // if (interfaces != null && interfaces.length > 0) {
				// // for (CtClass inte : interfaces) {
				// // System.out.println("!!!!!@@@@@@ " + inte.getName());
				// // }
				// // }
				// System.out
				// .println("Class can be assigned to Custom Iterface .............. "
				// + className);
				//
				// CtMethod[] methods = cc.getDeclaredMethods();
				//
				// if (methods != null && methods.length > 0) {
				// for (CtMethod m : methods) {
				// m.addLocalVariable("elapsedTime",
				// CtClass.longType);
				// m.insertBefore("elapsedTime = System.currentTimeMillis();");
				// m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
				// +
				// "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");
				// }
				// }
				//
				// }
				// } catch (Exception e) {
				// e.printStackTrace();
				// }

				// CtMethod m = cc.getDeclaredMethod("randomSleep");
				// m.addLocalVariable("elapsedTime", CtClass.longType);
				// m.insertBefore("elapsedTime = System.currentTimeMillis();");
				// m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
				// +
				// "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");
				byteCode = cc.toBytecode();
				cc.detach();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}

		return byteCode;
	}

	public static void listThreads(ThreadGroup group, String indent) {
		System.out.println(indent + "Group[" + group.getName() + ":" + group.getClass() + "]");
		int nt = group.activeCount();
		Thread[] threads = new Thread[nt * 2 + 10]; // nt is not accurate
		nt = group.enumerate(threads, false);

		// List every thread in the group
		for (int i = 0; i < nt; i++) {
			Thread t = threads[i];
			System.out.println(indent + "  Thread[" + t.getName() + ":" + t.getClass() + "]");

			StackTraceElement[] stackTrace = t.getStackTrace();
			for (StackTraceElement ste : stackTrace) {
				System.out.println(ste);
			}

		}

		// Recursively list all subgroups
		int ng = group.activeGroupCount();
		ThreadGroup[] groups = new ThreadGroup[ng * 2 + 10];
		ng = group.enumerate(groups, false);

		for (int i = 0; i < ng; i++) {
			listThreads(groups[i], indent + "  ");
		}
	}

	public static void setJarInClasspath(String path, ClassPool cp) throws NotFoundException {

		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {
			String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1, f.getName().length());
			if (f.isDirectory()) {
				setJarInClasspath(f.getAbsolutePath(), cp);
				if (extension.equals("jar")) {
					cp.appendClassPath(f.getAbsolutePath());
				}
				// System.out.println( "Dir:" + f.getAbsoluteFile() );
			} else {
				if (extension.equals("jar")) {
					cp.appendClassPath(f.getAbsolutePath());
				}
				// System.out.println( "File:" + f.getAbsoluteFile() );
			}
		}
	}

}
