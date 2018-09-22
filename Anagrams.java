/**
 * Anagrams.java
 * @author Bobby Georgiou
 * Date: Apr 2018
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Anagrams {
	final Integer[] primes;
	Map<Character, Integer> letterTable;
	Map<Long, ArrayList<String>> anagramTable;
	
	public Anagrams() {
		primes = new Integer[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
		letterTable = new HashMap<Character, Integer>();
		anagramTable = new HashMap<Long, ArrayList<String>>();
		buildLetterTable();
	}
	
	/**
	 * Builds the hash map letterTable by assigning each letter to a prime integer
	 */
	private void buildLetterTable() {
		char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		for (int i = 0; i < letters.length; i++) {
			letterTable.put(letters[i], primes[i]);
		}
	}
	
	/**
	 * Computes the hash code of a string and adds the string to anagramTable
	 * @param s
	 */
	private void addWord(String s) {
		Long sHash = myHashCode(s);
		ArrayList<String> curStrList;
		if (anagramTable.get(sHash) != null) {
			curStrList = anagramTable.get(sHash);
		} else {
			curStrList = new ArrayList<String>();
		}
		curStrList.add(s);
		anagramTable.put(sHash, curStrList);
	}
	
	/**
	 * Computes the hash code of a string using letterTable values
	 * @param s
	 * @return Returns the hash code of String s
	 */
	private Long myHashCode(String s) {
		s = s.replaceAll("\\s", "");
		long pendingHash = 1;
		for (Character c : s.toCharArray()) {
			pendingHash *= letterTable.get(c);
		}
		Long finalHash = new Long(pendingHash);
		return finalHash;
	}
	
	/**
	 * Receives file and builds hash map anagramTable based on each line of the file
	 * @param s
	 * @throws IOException
	 */
	public void processFile(String s) throws IOException {
		FileInputStream fstream = new FileInputStream(s);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			this.addWord(strLine);
		}
		br.close();
	}
	
	/**
	 * Gets the entries in hash map anagramTable that have the greatest number of anagrams
	 * @return Returns the entries in hash map anagramTable that have the greatest number of anagrams
	 */
	private ArrayList<Map.Entry<Long, ArrayList<String>>> getMaxEntries() {
		ArrayList<Map.Entry<Long, ArrayList<String>>> pendingEntries = new ArrayList<Map.Entry<Long, ArrayList<String>>>();
		int maxArrSize = 0;
		for (Map.Entry<Long, ArrayList<String>> entry : anagramTable.entrySet()) {
			if (entry.getValue().size() > maxArrSize) {
				maxArrSize = entry.getValue().size();
			}
		}
		for (Map.Entry<Long, ArrayList<String>> entry : anagramTable.entrySet()) {
			if (entry.getValue().size() == maxArrSize) {
				pendingEntries.add(entry);
			}
		}
		return pendingEntries;
	}
	
	public static void main(String[] args) {
		Anagrams a = new Anagrams();
		final long startTime = System.nanoTime();
		try {
			a.processFile("words_alpha.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = a.getMaxEntries();
		final long estimatedTime = System.nanoTime() - startTime;
		final double seconds = ((double) estimatedTime / 1000000000);
		System.out.println("Time: " + seconds);
		System.out.println("List of max anagrams: " + maxEntries);
	}
}
