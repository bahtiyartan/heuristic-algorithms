package com.bahtiyartan.heuristic.genetic.textprediction;

import java.util.Collections;
import java.util.Vector;

public class Population {

	private Vector<Individual> itemList;

	public Population() {
		itemList = new Vector<Individual>();
	}

	/**
	 * Creates random individuals for a starting point
	 * 
	 * @param pCount
	 * @param pTextSize
	 * @return
	 */
	public static Population createRandom(int pCount, int pTextSize) {

		Population population = new Population();

		for (int i = 0; i < pCount; i++) {
			population.itemList.add(Individual.createRandom(pTextSize));
		}

		return population;

	}

	/**
	 * Prints top scored individuals
	 * 
	 * @param pCount
	 */
	public void printTop(int pCount) {
		for (int i = itemList.size() - 1; i > 0 && pCount > 0; i--) {
			itemList.get(i).printIndividual();
			pCount--;
		}
	}

	/**
	 * Prints all population
	 */
	public void printAllPopulation() {
		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).printIndividual();
		}
	}

	/**
	 * Evaluates for an iteartion.
	 * 
	 * Creates new individuals instead of worst %20. New individuals are created by
	 * crossover of best %20. And mutates worst %20-%60, using evaluator
	 * 
	 * 
	 * @param pEvaluator
	 */
	public void evaluatePopulation(AbstractEvaluator pEvaluator) {
		// TODO: as a strong option, this method can be moved to AbstractEvaluator as an abstract method

		int nBlockSize = (int) (itemList.size() * 0.20);

		// STEP 1: run fit function to calculate score for each item
		for (Individual item : itemList) {
			pEvaluator.evaluate(item);
		}

		// STEP 2: sort individuals due to score
		Collections.sort(itemList, pEvaluator);

		// STEP 3: kill worst %20 and crossover new %20 using the best %20
		for (int i = 0; i < nBlockSize; i++) {
			int parentIndex1 = itemList.size() - 1 - pEvaluator.Randomizer.createInt(nBlockSize);
			int parentIndex2 = itemList.size() - 1 - pEvaluator.Randomizer.createInt(nBlockSize);

			Individual child = pEvaluator.crossover(itemList.get(parentIndex1), itemList.get(parentIndex2));
			itemList.set(i, child);
		}

		// STEP 4: mutate worst %20-%60
		for (int i = nBlockSize; i < 3 * nBlockSize; i++) {
			pEvaluator.mutate(itemList.get(i));
		}

		// STEP 5: sort (just for a human readible printing)
		Collections.sort(itemList, pEvaluator);
	}

	/**
	 * Returns maximum score for the population
	 * 
	 * @return
	 */
	public double getMaxScore() {
		return itemList.lastElement().getScore();
	}
}
