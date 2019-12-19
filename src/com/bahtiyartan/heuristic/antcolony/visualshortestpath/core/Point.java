package com.bahtiyartan.heuristic.antcolony.visualshortestpath.core;

public class Point {
	public int X;
	public int Y;

	public Point(int pX, int pY) {
		this.X = pX;
		this.Y = pY;
	}

	public Point clonePoint() {
		return new Point(X, Y);
	}
	
	public Point getAdjacent(int shiftx, int shifty) {
		return  new Point(X+shiftx, Y+shifty);
	}

	@Override
	public int hashCode() {
		return 10000 * X + Y;
	}

	public String getKey() {
		return new StringBuilder().append(X).append(",").append(Y).toString();
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return ((Point) obj).X == this.X && ((Point) obj).Y == this.Y;
		} catch (Exception e) {
			return false;
		}
	}
}
