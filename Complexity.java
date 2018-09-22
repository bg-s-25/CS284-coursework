/**
 * Complexity.java
 * @author Bobby Georgiou
 * Date: Feb 2018
 */

public class Complexity {
	
	/**
	 * Method with complexity O(n^2)
	 * @param n
	 */
	public void method1(int n) {
		int counter = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.println("Operation " + counter);
				counter++;
			}
		}
	}
	
	/**
	 * Method with complexity O(n^3)
	 * @param n
	 */
	public void method4(int n) {
		int counter = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					System.out.println("Operation " + counter);
					counter++;
				}
			}
		}
	}
	
	/**
	 * Method with complexity O(log(n))
	 * @param n
	 */
	public void method2(int n) {
		int counter = 0;
		for (int i = 1; i < n; i *= 2) {
			System.out.println("Operation " + counter);
			counter++;
		}
	}
	
	/**
	 * Method with complexity O(nlog(n))
	 * @param n
	 */
	public void method3(int n) {
		int counter = 0;
		for (int i = 1; i < n; i *= 2) {
			for (int j = 0; j < n; j++) {
				System.out.println("Operation " + counter);
				counter++;	
			}
		}
	}
	
	/**
	 * Method with complexity O(log(log(n)))
	 * @param n
	 */
	public void method5(int n) {
		int counter = 0;
		for (int i = 2; i < n; i = (int) Math.pow(i, 2)) {
			System.out.println("Operation " + counter);
			counter++;	
		}
	}
	
	/**
	 * Method with complexity O(2^n)
	 * @param n
	 */
	int method6counter;
	public int method6(int n) {
		if (n <= 1) {
			return n;
		} else {
			System.out.println("Operation " + method6counter);
			method6counter++;
			return method6(n - 2) + method6(n - 1);
		}
	}
}
