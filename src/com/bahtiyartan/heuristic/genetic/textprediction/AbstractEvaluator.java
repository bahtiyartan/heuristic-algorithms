package com.bahtiyartan.heuristic.genetic.textprediction;

import java.util.Comparator;

/**
 * To allow implementing different types of Evaluators.
 * 
 * @author BAHTIYAR
 */
public abstract class AbstractEvaluator implements Comparator<Individual> {

	protected RandomCharGenerator Randomizer;
	protected String targetString;

	public AbstractEvaluator(String pTargetString) {
		targetString = pTargetString;
		Randomizer = new RandomCharGenerator();
	}

	public abstract void evaluate(Individual pIndividual);

	public abstract void mutate(Individual pIndividual);

	protected abstract Individual crossover(Individual pParent1, Individual pParent2);

}
