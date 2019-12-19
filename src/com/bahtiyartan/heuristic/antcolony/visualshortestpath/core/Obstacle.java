package com.bahtiyartan.heuristic.antcolony.visualshortestpath.core;

import java.util.Vector;

public class Obstacle {
	public Vector<Point> points = new Vector<Point>();

	public void addPoint(int pX, int pY) {
		this.points.add(new Point(pX, pY));
	}
}
