/**
 * Pledge: "I pledge my honor that I have abided by the Stevens Honor System."
 * @author Bobby Georgiou
 * CS 284 A | Homework 4 | 03-25-2018
 */

public class PairInt {
	private int x;
	private int y;

	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean equals(Object p) {
		PairInt pi_obj = (PairInt) p;
		return x == pi_obj.getX() && y == pi_obj.getY();
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public PairInt copy() {
		PairInt newP = new PairInt(x, y);
		return newP;
	}
}