package main;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GraphBuild {

	public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g =
			new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
	private JPanel panel ;

//	public void setGraph(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g) {
//		this.g = g;
//	}
	
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public void setAllVerices(String s) {
		int n = Integer.parseInt(s);
		for (int i = 1; i <= n; i++) {
			g.addVertex(Integer.toString(i, 10));
		}
		printGraph();
	}

	public void addEdge(String start, String end, String weight) {
		
		// Make an edge object
		DefaultWeightedEdge e1 = g.addEdge(start, end);
		
		// Set the weight of the edge
		g.setEdgeWeight(e1, (double)Integer.parseInt(weight));
		
		// print the new graph
		printGraph();
		
	}

	private void printGraph() {

		// Make a mxGraph object
		mxGraph mxGraph = new mxGraph();

		// Change the grapht to graphx using adapter
		JGraphXAdapter graphAdapter = new JGraphXAdapter(this.g);

		// Setting the value of the graphx
		mxGraph = graphAdapter;
		
		// Apply vertix style
		applyStyle(mxGraph);

		// Setting the layout of the mxgraph
		mxHierarchicalLayout layout = new mxHierarchicalLayout(mxGraph);

		// Setting the orientation of the mxgraph
		layout.setOrientation(SwingConstants.WEST);
		
		// Setting some properties to the layout
		layout.setInterRankCellSpacing(140);
		layout.setIntraCellSpacing(120);
		
		// Executing layout graph
		layout.execute(mxGraph.getDefaultParent());

		// Make a graph component of mxgraph
		mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
		
		// Refresh to apply style
		graphComponent.setAutoExtend(true);
		graphComponent.setBorder(new EmptyBorder(10, 10, 100, 10));
		graphComponent.repaint();
		graphComponent.validate();
		graphComponent.refresh();

		// Set graphComponent to null
		//graphComponent.setBorder(null);
		
		//Add the panel to the Gui
		this.panel.removeAll();
		this.panel.add(graphComponent);
		this.panel.setBorder(null);
		this.panel.setBounds(0, 100, 1080, 656);
		this.panel.repaint();
		this.panel.validate();
		
	}
	
	private void applyStyle(mxGraph graph) {
		
	    // Settings for vertix
	    Map<String, Object> vertix = new HashMap<String, Object>();
	    vertix.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_LABEL);
	    //vertix.put(mxConstants.STYLE_INDICATOR_SPACING, 30);
	    //vertix.put(mxConstants.STYLE_FONTSIZE, 20);
	    //vertix.put(mxConstants.STYLE_SPACING_TOP, 30);
	    //vertix.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
	    //vertix.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
	    vertix.put(mxConstants.STYLE_FILLCOLOR, "white");
	    vertix.put(mxConstants.STYLE_STROKECOLOR, "#000000");
	    vertix.put(mxConstants.STYLE_FONTCOLOR, "#000000");
	    
	    // Settings for edges
	    Map<String, Object> edge = new HashMap<String, Object>();
	    //edge.put(mxConstants.STYLE_ROUNDED, true);
	    //edge.put(mxConstants.STYLE_ORTHOGONAL, false);
	    //edge.put(mxConstants.STYLE_FONTSIZE, 20);
	    //edge.put(mxConstants.STYLE_ARCSIZE, 20);
	    edge.put(mxConstants.STYLE_EDGE, "elbowEdgeStyle");//elbowEdgeStyle
	    edge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
	    edge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
	    edge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
	    edge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
	    edge.put(mxConstants.STYLE_STROKECOLOR, "#911b8b");
	    edge.put(mxConstants.STYLE_FONTCOLOR, "#000000");

	    mxStylesheet styleSheet = new mxStylesheet();
	    styleSheet.setDefaultVertexStyle(vertix);
	    //styleSheet.setDefaultEdgeStyle(edge);
	    graph.setStylesheet(styleSheet);
	}
	
}
