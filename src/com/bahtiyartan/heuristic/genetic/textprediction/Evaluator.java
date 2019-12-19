package com.bahtiyartan.heuristic.genetic.textprediction;

public class Evaluator extends AbstractEvaluator {

	public Evaluator(String pTargetString) {
		super(pTargetString);
	}

	/**
	 * Fitness method, that evaluates individuals.
	 */
	public void evaluate(Individual pIndividual) {

		pIndividual.setScore(0);

		int nScore = 0;
		for (int i = 0; i < targetString.length(); i++) {
			char c = targetString.charAt(i);
			if (c == pIndividual.charAt(i)) {
				nScore++;
			}
		}

		pIndividual.setScore(nScore);
	}

	@Override
	public int compare(Individual o1, Individual o2) {
		if (o1.getScore() < o2.getScore()) {
			return -1;
		} else if (o1.getScore() > o2.getScore()) {
			return 1;
		} else {
			return 0;
		}
	}

	public void mutate(Individual o1) {
		// mutate random two char
		o1.setChar(Randomizer.createInt(targetString.length()), Randomizer.createChar());
		o1.setChar(Randomizer.createInt(targetString.length()), Randomizer.createChar());

	}

	public Individual crossover(Individual parent1, Individual parent2) {

		Individual child = parent1.cloneItem();

		child.setChar(Randomizer.createInt(targetString.length()), parent2.charAt(Randomizer.createInt(targetString.length())));
		child.setChar(Randomizer.createInt(targetString.length()), parent2.charAt(Randomizer.createInt(targetString.length())));
		child.setChar(Randomizer.createInt(targetString.length()), parent2.charAt(Randomizer.createInt(targetString.length())));

		return child;
	}

}
