package com.bahtiyartan.heuristic.antcolony.visualshortestpath.core;

import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class Map implements Runnable {

	public int Width;
	public int Height;
	public Point start;
	public Point end;

	public Vector<Ant> ants = new Vector<Ant>();

	public Vector<Obstacle> vObstacle = new Vector<Obstacle>();
	public Hashtable<String, String> obstacles = new Hashtable<String, String>();

	public Hashtable<Point, Double> feromon = new Hashtable<Point, Double>();

	private IMapUI ui;

	Thread colonythread = null;

	public int SuccessfulAnts = 0;

	Random rand;

	public Map(int pWidth, int pHeight, Point pStart, Point pEnd) {
		this.Width = pWidth;
		this.Height = pHeight;

		start = pStart;
		end = pEnd;

		rand = new Random();
	}

	public void addObstacle(Obstacle pObstacle) {
		for (Point p : pObstacle.points) {
			obstacles.put(p.getKey(), "");
		}
		this.vObstacle.add(pObstacle);
	}

	public void clear() {

		try {
			colonythread.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ants.clear();
	}

	public void setUI(IMapUI pUI) {
		this.ui = pUI;
	}

	public void addAnt(int pCount) {

		for (int i = 0; i < pCount; i++) {
			Point startPoint = start.clonePoint();
			startPoint.X = i % 5; //just for testing
			this.ants.addElement(new Ant(this, i));
		}

		this.ui.stateChanged();
	}

	public void run() {

		int nStep = 0;
		while (true) {
			nStep++;

			Vector<Ant> winnerants = new Vector<Ant>();
			for (Ant ant : ants) {

				Point current = ant.getCurrentPosition().clonePoint();

				Point next = this.calculateNext(ant, current);
				while (!isValidPoint(next)) {

					next = this.calculateNext(ant, current);
				}

				ant.moveTo(next);

				if (next.equals(end)) {
					winnerants.addElement(ant);
				}
			}

			for (Ant ant : winnerants) {
				ant.updateFeromon();
				ants.remove(ant);
				SuccessfulAnts++;
				System.out.println("successful ants : " + SuccessfulAnts);
			}

			addAnt(winnerants.size());

			if (SuccessfulAnts >= 10) {
				this.globalFeromonUpdate();
				SuccessfulAnts = 0;
				System.out.println("reduced");
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.antMoved();

		}

	}

	private void globalFeromonUpdate() {

		double factor = 0.75;

		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				Point p = new Point(i, j);
				Double d = this.feromon.get(p);
				if (d == null) {
					d = 100.0 * factor;
				} else {
					d = d * factor;
				}

				this.feromon.put(p, d);
			}
		}
	}

	private Point calculateNext(Ant ant, Point current) {

		Vector<Point> adjacentPoints = new Vector<Point>();

		adjacentPoints.add(current.getAdjacent(1, 0)); //right
		adjacentPoints.add(current.getAdjacent(-1, 0)); //left
		adjacentPoints.add(current.getAdjacent(0, 1)); //the cell below
		adjacentPoints.add(current.getAdjacent(0, -1)); //the cell below

		adjacentPoints.add(current.getAdjacent(-1, -1));
		adjacentPoints.add(current.getAdjacent(1, 1));
		adjacentPoints.add(current.getAdjacent(-1, 1));
		adjacentPoints.add(current.getAdjacent(1, -1));

		Random rand = new Random();
		int random = rand.nextInt(100) % 10;

		Vector<Double> probs = new Vector<Double>();

		if (random < 1) { //%10 random

			double totalscore = 0;
			for (int i = 0; i < adjacentPoints.size(); i++) {
				double cellscore = 10.0;
				totalscore += cellscore;
				probs.add(cellscore);
			}

			for (int i = 0; i < probs.size(); i++) {
				probs.set(i, probs.get(i) / totalscore);
			}

			return selectPoint(adjacentPoints, probs);
		} else {

			double totalferomon = 0;

			for (int i = 0; i < adjacentPoints.size(); i++) {
				double cellferomon = this.getCellFeromon(adjacentPoints.get(i));
				if(ant.pathht.containsKey(adjacentPoints.get(i))) {
					cellferomon = cellferomon * 0.0;
				}
				totalferomon += cellferomon;
				probs.add(cellferomon);
			}

			for (int i = 0; i < probs.size(); i++) {
				probs.set(i, probs.get(i) / totalferomon);
			}

			if (random < 9) {
				return selectPointMax(adjacentPoints, probs);
			} else {
				return selectPoint(adjacentPoints, probs);
			}

		}

	}

	public double getCellFeromon(Point p) {
		if (this.feromon.containsKey(p)) {
			return this.feromon.get(p);
		} else {
			return 0.1;
		}
	}

	public synchronized void antMoved() {
		this.ui.stateChanged();
	}

	public void init() {
		colonythread = new Thread(this);
		colonythread.start();
	}

	public static Point selectPoint(Vector<Point> points, Vector<Double> probs) {
		double p = Math.random();
		double cumulativeProbability = 0.0;
		for (int i = 0; i < points.size(); i++) {
			cumulativeProbability += probs.get(i);
			if (p <= cumulativeProbability) {
				return points.get(i);
			}
		}

		return null;
	}

	public static Point selectPointMax(Vector<Point> points, Vector<Double> probs) {

		double nMaxProb = 1.0 / points.size();
		nMaxProb = (double) Math.round(nMaxProb * 1000d) / 1000d;

		int MaxIndex = -1;

		for (int i = 0; i < points.size(); i++) {
			probs.set(i, (double) Math.round(probs.get(i) * 1000d) / 1000d);
			if (probs.get(i) > nMaxProb) {
				MaxIndex = i;
				nMaxProb = probs.get(i);
			}
		}

		if (MaxIndex == -1) {
			return selectPoint(points, probs);
		} else {
			return points.get(MaxIndex);
		}

	}

	public boolean isValidPoint(Point p) {
		return p != null && p.X >= 0 && p.Y >= 0 && p.X < this.Width && p.Y < this.Height && !obstacles.containsKey(p.getKey());
	}
}
