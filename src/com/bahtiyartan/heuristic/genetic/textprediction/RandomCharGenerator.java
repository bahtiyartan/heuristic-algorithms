package com.bahtiyartan.heuristic.genetic.textprediction;

import java.util.Random;

@SuppressWarnings("serial")
public class RandomCharGenerator extends Random {

	public char createChar() {
		return (char) (this.nextInt('z' - 'a') + 'a');
	}

	public int createInt(int pMax) {
		return this.nextInt(pMax);
	}

}
