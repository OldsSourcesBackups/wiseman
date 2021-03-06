/*
 * Copyright 2006 Sun Microsystems, Inc.
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
 **Revision 1.3  2007/05/30 20:30:27  nbeers
 **Add HP copyright header
 **
 ** 
 *
 * $Id: Installer.java,v 1.4 2007-06-02 04:00:54 simeonpinder Exp $
 */
package com.sun.ws.management.installer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Installer {
	
	public static String CONTINUE="Continue";
	public static String EXIT="Exit";
	private static String warning = "     *  *  * ##### WARNING #####  *  *  *      \n";
	
	OutputStream wrapper = null;
	
	private JFrame launcher = null;
	
	public Installer(){
		//Frame details
		launcher = new JFrame("Wiseman 1.0_RC2 installer");
		launcher.setSize(400,400);
		launcher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		launcher.setLocationRelativeTo(null);
		
		//Load content from files.
		String projectLicenseTxt = "";
		String errorText = "";
		String licResource = "license-intro.txt";
		String antErrorResource = "ant-error.txt";
		InputStream fileStream = Installer.class.getResourceAsStream(licResource);
		InputStream antErrorStream = Installer.class.getResourceAsStream(antErrorResource);
		
		//Component details.
		JScrollPane scrollPane =null;
		JPanel buttonContainer = null;
		ActionListener buttonListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(Installer.EXIT.equals(e.getActionCommand().trim())){
					System.exit(0);
				}else{
					proceedWithInstall();
				}
			}
		};
		
		JTextArea display ;
//		BufferedReader br,br2 = null; 
		BufferedReader br = null; 
		try {
			br = new BufferedReader( new InputStreamReader(fileStream));
//			br2 = new BufferedReader(new InputStreamReader(antErrorStream));
			String read = null;
			projectLicenseTxt = "";
			while((read=br.readLine())!=null){
				projectLicenseTxt+=read+"\n";
			}
//			errorText = "";
//			while((read=br2.readLine())!=null){
//				errorText+=read+"\n";
//			}
			
			display = new JTextArea();
			display.setLineWrap(true); 
			display.setWrapStyleWord(true); 
			display.setEditable(false);
			display.append(projectLicenseTxt);
			scrollPane = new JScrollPane(display);
		
			buttonContainer = new JPanel();
			JButton proceed = new JButton(CONTINUE);
			   proceed.addActionListener(buttonListener);
			buttonContainer.add(proceed);
			JButton exit = new JButton(EXIT);
			buttonContainer.add(exit);
			   exit.addActionListener(buttonListener);

//			//Check the ant version.
//			if(System.getProperty("ant.home")==null){   
//				display.append("\n"+warning+errorText);
//				proceed.setEnabled(false);
//			}
			   
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		launcher.getContentPane().add(scrollPane,BorderLayout.CENTER);
		launcher.getContentPane().add(buttonContainer,BorderLayout.SOUTH);
		launcher.setVisible(true);
		
	}
	
	private void proceedWithInstall(){
		//Check to see if this version of ant is good.
		String[] arguments={"-f","build.xml"};
			org.apache.tools.ant.launch.Launcher.main(arguments);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Installer instance = new Installer();
	}
}

