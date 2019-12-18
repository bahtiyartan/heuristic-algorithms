package com.bahtiyartan.heuristic.genetic.textprediction;

public class Individual {

	private char[] text;

	private double score;

	public Individual(int pTextSize) {
		text = new char[pTextSize];
	}

	/**
	 * Creates an individual with random chars
	 * 
	 * @param pTextSize
	 * @return
	 */
	public static Individual createRandom(int pTextSize) {

		RandomCharGenerator iGenerator = new RandomCharGenerator();
		Individual instance = new Individual(pTextSize);

		for (int i = 0; i < pTextSize; i++) {
			instance.text[i] = iGenerator.createChar();
		}

		return instance;
	}

	public void printIndividual() {
		System.out.println(new String(text) + " -> " + score);
	}

	public void setScore(int pValue) {
		score = pValue;
	}

	public char charAt(int pIndex) {
		return text[pIndex];
	}

	public double getScore() {
		return score;
	}

	public void setChar(int pIndex, char pValue) {
		text[pIndex] = pValue;
	}

	public Individual cloneItem() {
		Individual child = new Individual(text.length);

		for (int i = 0; i < text.length; i++) {
			child.text[i] = text[i];
		}

		return child;
	}

}
