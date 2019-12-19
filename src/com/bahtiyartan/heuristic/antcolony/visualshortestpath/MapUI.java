package com.bahtiyartan.heuristic.antcolony.visualshortestpath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Enumeration;

import javax.swing.JPanel;

import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.Ant;
import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.Map;
import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.Obstacle;
import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.Point;

@SuppressWarnings("serial")
public class MapUI extends JPanel {

	public static final int Size = 50;
	public static final int AntSize = 6;
	public static final int AntGap = (Size - AntSize) / 2;

	Map map;

	public MapUI(Map pMap) {
		new BorderLayout();

		this.map = pMap;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		//draw feromon
		Enumeration<Point> e = map.feromon.keys();
		while (e.hasMoreElements()) {
			Point p = e.nextElement();
			int fValue = map.feromon.get(p).intValue() * 3;
			if(fValue > 255) {
				fValue = 255;
			}
			
			int d = 255 - fValue;

			Color c = new Color(d, 255, d);
			g.setColor(c);

			g.fillRect(p.X * Size, p.Y * Size, Size, Size);

		}

		g.setColor(Color.LIGHT_GRAY);
		// draw grids
		int nTemp = 0;
		for (int i = 0; i < map.Height + 1; i++) { // to draw last line
			nTemp = i * Size;
			g.drawLine(0, nTemp, map.Width * Size, nTemp);
			g.drawString(Integer.toString(i - 1), 0, nTemp);
		}

		nTemp = 0;
		for (int i = 0; i < map.Width + 1; i++) { // to draw last line
			nTemp = i * Size;
			g.drawLine(nTemp, 0, nTemp, map.Height * Size);
			g.drawString(Integer.toString(i), nTemp, Size);
		}

		// draw obstacles
		g.setColor(Color.LIGHT_GRAY);
		for (Obstacle iObstacle : map.vObstacle) {
			for (Point iPoint : iObstacle.points) {
				g.fillRect(iPoint.X * Size, iPoint.Y * Size, Size, Size);
			}
		}

		// draw start and end points
		g.setColor(Color.orange);
		g.fillRect(map.start.X * Size, map.start.Y * Size, Size, Size);

		g.setColor(Color.RED);
		g.fillRect(map.end.X * Size, map.end.Y * Size, Size, Size);

		//draw ants
		g.setColor(Color.BLACK);
		for (Ant iAnt : map.ants) {
			g.fillRect(iAnt.getCurrentPosition().X * Size + AntGap, iAnt.getCurrentPosition().Y * Size + AntGap, AntSize, AntSize);
		}

	}
}
