package com.bahtiyartan.heuristic.antcolony.visualshortestpath;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.IMapUI;
import com.bahtiyartan.heuristic.antcolony.visualshortestpath.core.Map;

public class Frame extends JFrame implements ActionListener, IMapUI {

	private MapUI mapUI;
	private Map map;

	JButton clearbutton;
	JButton startbutton;

	public Frame(Map pMap) {
		super("com.bahtiyartan.heuristic.antcolony");

		//init model
		map = pMap;
		map.setUI(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		int nWidth = pMap.Width * MapUI.Size + 25;
		int nHeight = pMap.Height * MapUI.Size + 100;

		this.setMinimumSize(new Dimension(nWidth, nHeight));
		this.setSize(new Dimension(nWidth, nHeight));
		this.setPreferredSize(new Dimension(nWidth, nHeight));
		this.setLocationRelativeTo(null);

		clearbutton = new JButton("clear");
		clearbutton.addActionListener(this);
		clearbutton.setActionCommand("clear");

		startbutton = new JButton("start");
		startbutton.addActionListener(this);
		startbutton.setActionCommand("start");

		JPanel jContentPane = new JPanel(new BorderLayout());

		JPanel JButtonsPanel = new JPanel(new GridLayout(1, 2, 0, 0));
		JButtonsPanel.add(clearbutton);
		JButtonsPanel.add(startbutton);

		jContentPane.add(JButtonsPanel, BorderLayout.NORTH);

		mapUI = new MapUI(pMap);
		jContentPane.add(mapUI, BorderLayout.CENTER);

		this.setContentPane(jContentPane);
	}

	@Override
	public void stateChanged() {
		this.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("clear")) {
			map.clear();
		} else if(e.getActionCommand().equals("start")) {
			map.addAnt(map.Width * 2);
			map.init();
		}
		
		
	}
}
