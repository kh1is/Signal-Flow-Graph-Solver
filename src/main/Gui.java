package main;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import java.awt.FlowLayout;

public class Gui {

	private JFrame frame;
	private JPanel PanelTitleBar;
	private JPanel panelSolutionArea;
	private JPanel panelChooseSteps;
	private JPanel panelGraph;
	private JTextField txtNoOfNodes;
	private JTextField txtStartingVertix;
	private JTextField txtEndingVertix;
	private JTextField txtWeight;
	private JButton btnStart;
	private JButton btnAddEdge;
	private JButton btnCalculate;
	private JCheckBox chckbxSteps;
	private JTextArea textArea;
	
	GraphBuild graphBuild = new GraphBuild();
	GainCalculate gainCalculate = new GainCalculate();
	
	private static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g =
			new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnStart){
				graphBuild.setPanel(panelGraph);
				graphBuild.setAllVerices(txtNoOfNodes.getText());
				panelGraph.validate();
				panelGraph.repaint();
			}else if(e.getSource() == btnAddEdge){
				graphBuild.setPanel(panelGraph);
				if(!txtStartingVertix.getText().isEmpty() && !txtEndingVertix.getText().isEmpty() && !txtWeight.getText().isEmpty()) {
					graphBuild.addEdge(txtStartingVertix.getText(), txtEndingVertix.getText(), txtWeight.getText());
				}else if(!txtStartingVertix.getText().isEmpty() && !txtEndingVertix.getText().isEmpty() && txtWeight.getText().isEmpty()) {
					graphBuild.addEdge(txtStartingVertix.getText(), txtEndingVertix.getText(), "1");
				}
				
				panelGraph.validate();
				panelGraph.repaint();
			}else if(e.getSource() == btnCalculate){
				gainCalculate.setGraph(graphBuild.g);
				if(chckbxSteps.isSelected()) {
					textArea.setText(gainCalculate.steps());
					textArea.validate();
					textArea.repaint();
				}else {
					textArea.setText(gainCalculate.noSteps());
					textArea.validate();
					textArea.repaint();
				}
			}
		}
		
	};

	/**
	 * Launch the application.
	 */
	public void mainGui() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//---------------------------------Start Initialize------------------------------------//

		frame = new JFrame("Signal Flow Graph Counter");
		frame.getContentPane().setForeground(Color.DARK_GRAY);
		//frame.setBackground(Color.WHITE);
		//frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(-5, -5, 1380, 740);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//---------------------------------Start Initialize------------------------------------//

		//----------------------------------Start Title Bar------------------------------------//

		
		PanelTitleBar = new JPanel();
		PanelTitleBar.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 0, 0), new Color(128, 128, 128)));
		PanelTitleBar.setBackground(Color.DARK_GRAY);
		PanelTitleBar.setBounds(0, 0, 1364, 45);
		frame.getContentPane().add(PanelTitleBar);
		PanelTitleBar.setLayout(null);
		
		JLabel lblNoOfNodes = new JLabel("No. of Nodes :");
		lblNoOfNodes.setToolTipText("No. of Nodes :");
		lblNoOfNodes.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblNoOfNodes.setForeground(SystemColor.control);
		lblNoOfNodes.setBounds(10, 11, 119, 23);
		PanelTitleBar.add(lblNoOfNodes);
		
		txtNoOfNodes = new JTextField();
		txtNoOfNodes.setToolTipText("Please enter here ...");
		txtNoOfNodes.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		txtNoOfNodes.setBounds(135, 14, 111, 20);
		PanelTitleBar.add(txtNoOfNodes);
		txtNoOfNodes.setColumns(10);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBorder(new LineBorder(new Color(0, 0, 0)));
		horizontalBox.setBackground(Color.BLACK);
		horizontalBox.setForeground(Color.BLACK);
		horizontalBox.setBounds(300, 2, 3, 40);
		PanelTitleBar.add(horizontalBox);
		
		btnStart = new JButton("");
		btnStart.setToolTipText("Click me");
		btnStart.setIcon(new ImageIcon("E:\\Mine\\Faculty\\Control\\Signal Flow Graph\\src\\res\\icons8-checkmark-30.png"));
		btnStart.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 11));
		btnStart.setBackground(Color.DARK_GRAY);
		btnStart.setBounds(256, 7, 36, 30);
		btnStart.setContentAreaFilled(false);
		btnStart.setFocusPainted(false);
		btnStart.setBorderPainted(false);
		btnStart.addActionListener(actionListener);
		PanelTitleBar.add(btnStart);
		
		JLabel lblStartingVertix = new JLabel("Starting Vertix :");
		lblStartingVertix.setToolTipText("Starting Vertix :");
		lblStartingVertix.setForeground(SystemColor.menu);
		lblStartingVertix.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblStartingVertix.setBounds(310, 11, 131, 23);
		PanelTitleBar.add(lblStartingVertix);
		
		txtStartingVertix = new JTextField();
		txtStartingVertix.setToolTipText("Please enter here ...");
		txtStartingVertix.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		txtStartingVertix.setColumns(10);
		txtStartingVertix.setBounds(451, 14, 111, 20);
		PanelTitleBar.add(txtStartingVertix);
		
		JLabel lblEndigVertix = new JLabel("Endig Vertix :");
		lblEndigVertix.setToolTipText("Endig Vertix :");
		lblEndigVertix.setForeground(SystemColor.menu);
		lblEndigVertix.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblEndigVertix.setBounds(572, 11, 119, 23);
		PanelTitleBar.add(lblEndigVertix);
		
		txtEndingVertix = new JTextField();
		txtEndingVertix.setToolTipText("Please enter here ...");
		txtEndingVertix.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		txtEndingVertix.setColumns(10);
		txtEndingVertix.setBounds(701, 14, 111, 20);
		PanelTitleBar.add(txtEndingVertix);
		
		JLabel lblWeight = new JLabel("Weight :");
		lblWeight.setToolTipText("Weight :");
		lblWeight.setForeground(SystemColor.menu);
		lblWeight.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblWeight.setBounds(822, 11, 80, 23);
		PanelTitleBar.add(lblWeight);
		
		txtWeight = new JTextField();
		txtWeight.setToolTipText("Please enter here ...");
		txtWeight.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		txtWeight.setColumns(10);
		txtWeight.setBounds(912, 14, 111, 20);
		PanelTitleBar.add(txtWeight);
		
		btnAddEdge = new JButton("");
		btnAddEdge.setIcon(new ImageIcon("E:\\Mine\\Faculty\\Control\\Signal Flow Graph\\src\\res\\icons8-checkmark-30.png"));
		btnAddEdge.setToolTipText("Click me");
		btnAddEdge.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 11));
		btnAddEdge.setFocusPainted(false);
		btnAddEdge.setContentAreaFilled(false);
		btnAddEdge.setBorderPainted(false);
		btnAddEdge.setBackground(Color.DARK_GRAY);
		btnAddEdge.setBounds(1033, 7, 36, 30);
		btnAddEdge.addActionListener(actionListener);
		PanelTitleBar.add(btnAddEdge);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		horizontalBox_1.setForeground(Color.BLACK);
		horizontalBox_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		horizontalBox_1.setBackground(Color.BLACK);
		horizontalBox.setBorder(new LineBorder(new Color(0, 0, 0)));
		horizontalBox.setBackground(Color.BLACK);
		horizontalBox.setForeground(Color.BLACK);
		horizontalBox_1.setBounds(1080, 2, 3, 40);
		PanelTitleBar.add(horizontalBox_1);
		
		JLabel lblCalculate = new JLabel("Calculate :");
		lblCalculate.setToolTipText("Calculate :");
		lblCalculate.setForeground(SystemColor.menu);
		lblCalculate.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 18));
		lblCalculate.setBounds(1144, 11, 95, 23);
		PanelTitleBar.add(lblCalculate);
		
	    btnCalculate = new JButton("");
		btnCalculate.setIcon(new ImageIcon("E:\\Mine\\Faculty\\Control\\Signal Flow Graph\\src\\res\\icons8-checkmark-30.png"));
		btnCalculate.setToolTipText("Click me");
		btnCalculate.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 11));
		btnCalculate.setFocusPainted(false);
		btnCalculate.setContentAreaFilled(false);
		btnCalculate.setBorderPainted(false);
		btnCalculate.setBackground(Color.DARK_GRAY);
		btnCalculate.setBounds(1262, 7, 36, 30);
		btnCalculate.addActionListener(actionListener);
		PanelTitleBar.add(btnCalculate);
		
		//-----------------------------------End Title Bar-------------------------------------//

		//-------------------------------Start Calculate Area----------------------------------//
		
		panelSolutionArea = new JPanel();
		panelSolutionArea.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY));
		panelSolutionArea.setBounds(1080, 45, 284, 656);
		frame.getContentPane().add(panelSolutionArea);
		panelSolutionArea.setLayout(null);
		
		panelChooseSteps = new JPanel();
		panelChooseSteps.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY));
		panelChooseSteps.setBounds(0, 0, 284, 45);
		panelSolutionArea.add(panelChooseSteps);
		panelChooseSteps.setLayout(null);
		
		JLabel lblGainCalculation = new JLabel("Gain Calculation");
		lblGainCalculation.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
		lblGainCalculation.setBounds(10, 11, 110, 25);
		panelChooseSteps.add(lblGainCalculation);
		
		chckbxSteps = new JCheckBox("Steps");
		chckbxSteps.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 11));
		chckbxSteps.setBounds(181, 13, 97, 23);
		panelChooseSteps.add(chckbxSteps);
		
		textArea = new JTextArea();
		textArea.setBounds(3, 44, 278, 612);
		panelSolutionArea.add(textArea);
		
		//---------------------------------End Calculate Area----------------------------------//

		//----------------------------------Start Graph Area-----------------------------------//

		panelGraph = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelGraph.getLayout();
		flowLayout.setVgap(100);
		flowLayout.setHgap(100);
		panelGraph.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY));
		panelGraph.setBounds(0, 45, 1080, 656);
		frame.getContentPane().add(panelGraph);
		
		//-----------------------------------End Graph Area------------------------------------//


	}
}
