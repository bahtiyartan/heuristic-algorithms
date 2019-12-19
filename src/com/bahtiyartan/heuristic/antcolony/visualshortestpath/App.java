package com.bahtiyartan.heuristic.antcolony.visualshortestpath;

import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.Map;
import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.Obstacle;
import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.Point;

public class App {

	public static void main(String[] args) {

		int nWidth = 45;
		int nHeight = 16;

		Point start = new Point(0, 8);
		Point end = new Point(nWidth - 2, 10);

		Map iMap = new Map(nWidth, nHeight, start, end);

		Obstacle iObstacle = new Obstacle();

		iObstacle.addPoint(9, 3);
		iObstacle.addPoint(9, 4);
		iObstacle.addPoint(9, 5);
		iObstacle.addPoint(9, 6);

		//iObstacle.addPoint(7, 5);
		iObstacle.addPoint(7, 6);
		iObstacle.addPoint(7, 7);
		iObstacle.addPoint(7, 8);
		iObstacle.addPoint(7, 9);
		iObstacle.addPoint(7, 10);

		iObstacle.addPoint(9, 10);
		iObstacle.addPoint(9, 11);
		iMap.addObstacle(iObstacle);

		Frame iFrame = new Frame(iMap);
		iFrame.setVisible(true);
	}

}
