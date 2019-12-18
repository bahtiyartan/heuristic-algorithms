package com.bahtiyartan.heuristic.genetic.textprediction;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This application is just a simple demo of genetic algorithms.
 * 
 * Application trys to find given test with an evolutionary approach.
 * 
 * It prints top five individuals on each iteration and final text and total iteration count while it finds result.
 * 
 * @author BAHTIYAR
 *
 */
public class App {

	public static void main(String[] args) {

		int nPopulationCount = 1000;
		String strTargetText = "helloworld";

		//temp variables
		int nIterationCount = 1;
		int nIndex = 0;

		//STEP: Read parameters from console
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("enter population count: ");
			nPopulationCount = Integer.parseInt(reader.readLine());

			System.out.print("enter final text: ");
			strTargetText = reader.readLine().trim().toLowerCase().toLowerCase().replace(" ", ""); // must be lowercase, no white space allowed
		} catch (Exception ex) {
			System.out.println("input error: " + ex.getClass());
			System.out.println("aborting...");
		}

		//STEP: Build Population
		Population iPopulation = Population.createRandom(nPopulationCount, strTargetText.length());
		
		//STEP: build Evaluator, its possible to implement new evaluators
		AbstractEvaluator iEvaluator = new Evaluator(strTargetText);

		//STEP: Evaluate
		while (iPopulation.getMaxScore() < strTargetText.length()) {
			nIndex++;
			nIterationCount--;

			if (nIterationCount == 0) { //ask for more iterations or abort
				System.out.print("abort(0) or run (x) more iterations: ");
				try {
					String strResult = reader.readLine();
					nIterationCount = Integer.parseInt(strResult);
					if (nIterationCount <= 0) {
						System.out.println("aborting...");
						break;
					}
				} catch (Exception ex) {
					System.out.println("run until find solution");
				}
			}

			System.out.println("\niteration: " + nIndex);
			System.out.println("-----------------------");

			//evaluate
			iPopulation.evaluatePopulation(iEvaluator);
			
			//print top 5 on each iteration
			iPopulation.printTop(5);

			System.out.println("");
		}

		System.out.println("Total Iterations : " + nIndex);
		iPopulation.printTop(1);

	}

}
