package com.bahtiyartan.heuristic.antcolony.visualshortestpath.core;

import java.util.Hashtable;
import java.util.Vector;

public class Ant {

	Map map;

	private Vector<Point> path = new Vector<Point>();
	public Hashtable<Point, String> pathht = new Hashtable<Point, String>();

	public Ant(Map pMap, int n) {
		this.map = pMap;
		path.addElement(map.start);
		pathht.put(map.start, "");

	}

	public Point getCurrentPosition() {
		return path.lastElement();
	}

	public void updateFeromon() {

		double totalFeromon = (double) map.Height * map.Width * map.Width;

		double pathsize = Math.pow((double) path.size(), 2);

		double increment = totalFeromon / pathsize;

		for (Point p : path) {

			Double d = map.feromon.get(p);
			if (d == null) {
				d = 100.0;
			}

			d = d + increment;

			map.feromon.put(p, d);
		}
	}

	public void moveTo(Point p) {
		path.add(p);
		pathht.put(p, "");
	}
}
