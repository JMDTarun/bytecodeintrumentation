package org.javabenchmark.instrumentation;

public class Sleeping implements CustomInterface {
	public void randomSleep() throws InterruptedException {
		// randomly sleeps between 500ms and 1200s
		long randomSleepDuration = (long) (500 + Math.random() * 700);
		System.out.printf("Sleeping for %d ms ..\n", randomSleepDuration);
		Thread.sleep(randomSleepDuration);
	}

	public void customInterfaceTest() {
		try {
			System.out.println("Custom Interface.....");
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void customInterfaceTest1() {
		try {
			System.out.println("Custom Interface 1111111111111 .....");
			Thread.sleep(1100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void customInterfaceTest2() {
		try {
			System.out.println("Custom Interface 2222222222222222 .....");
			Thread.sleep(1200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}