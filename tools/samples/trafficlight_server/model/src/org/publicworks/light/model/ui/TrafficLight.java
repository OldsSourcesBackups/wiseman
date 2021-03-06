/*
 * Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 ** Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 **  
 ** Authors: Simeon Pinder (simeon.pinder@hp.com), Denis Rachal (denis.rachal@hp.com), 
 ** Nancy Beers (nancy.beers@hp.com), William Reichardt 
 **
 **$Log: not supported by cvs2svn $
 ** 
 *
 * $Id: TrafficLight.java,v 1.3 2007-05-30 20:30:26 nbeers Exp $
 */
package org.publicworks.light.model.ui;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * A swing frame that represents the traffic light resource.
*/
public class TrafficLight extends javax.swing.JFrame {
	private TrafficLightJPanel trafficLightJPanel1;
	private String name;
	private int x;
	private int y;
	private String color;
	private static int instance=0;
	private static int x0=0;
	private static int y0=0;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		TrafficLight inst = new TrafficLight();
		inst.setVisible(true);
	}
	
	public TrafficLight() {
		super();
		initGUI();
		instance++;		
		x0=x0+10;
		y0=y0+10;

	}

	public void defaultInit() {
		setName("Light"+instance);
		setX(x0);
		setY(y0);
		setColor("none");
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setMinimumSize(new java.awt.Dimension(68, 165));
			this.addComponentListener(new ComponentAdapter() {
				public void componentShown(ComponentEvent evt) {
					rootComponentShown(evt);
				}
			});
			{
				trafficLightJPanel1 = new TrafficLightJPanel();
				getContentPane().add(trafficLightJPanel1, BorderLayout.CENTER);
			}
			pack();
			this.setSize(199, 165);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getColor() {
		return trafficLightJPanel1.getColor();
	}

	public void setColor(String color) {
		this.color = color;
		trafficLightJPanel1.setColor(color);
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run() {
					setTitle(name);
				}});
		} catch (InterruptedException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public int getX() {
		if(!isVisible())
			return x;
		Point loc = getLocationOnScreen();		
		return loc.x;
	}

	public void setX(final int x) {
		this.x = x;
		try {
		SwingUtilities.invokeAndWait(new Runnable(){
			public void run() {
				Point loc = getLocationOnScreen();
				setLocation(x,loc.y);
			}});
		} catch (InterruptedException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public int getY() {
		if(!isVisible())
			return y;
		Point loc = getLocationOnScreen();		

		return loc.y;
	}

	public void setY(final int y) {
		this.y = y;
		try {
		SwingUtilities.invokeAndWait(new Runnable(){
			public void run() {
				Point loc = getLocationOnScreen();
				setLocation(loc.x,y);
			}});
	} catch (InterruptedException e) {
		throw new RuntimeException(e.getMessage());
	} catch (InvocationTargetException e) {
		throw new RuntimeException(e.getMessage());
	}
	}
	
	private void rootComponentShown(ComponentEvent evt) {
		System.out.println("this.componentShown, event=" + evt);

	}

}
