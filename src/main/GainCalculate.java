package main;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.cycle.DirectedSimpleCycles;
import org.jgrapht.alg.cycle.HawickJamesSimpleCycles;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.ListSingleSourcePathsImpl;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.specifics.DirectedEdgeContainer;

public class GainCalculate {

	private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> G 
		 		= new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

	public void setGraph(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph) {
		G = graph;
		}
	
	private StringBuilder s = new StringBuilder();
	private StringBuilder s2 = new StringBuilder();

	public String steps() {

		ArrayList<List<String>> pathLists = forwardPaths();
		s.append("All forwardPaths:");
		s.append("\n");
		for (int i = 0; i < pathLists.size(); i++) {
			s.append(pathLists.get(i));
			s.append("= ");
			s.append(calGainPath(pathLists.get(i)));
			s.append("  ");
			s.append("\n");
		}
		s.append("\n");

		ArrayList<List<String>> loopLists = individualLoops();
		s.append("All Loops:");
		s.append("\n");
		for (int i = 0; i < loopLists.size(); i++) {
			s.append(loopLists.get(i));
			s.append("= ");
			s.append(calGainPath(loopLists.get(i)));
			s.append("  ");
			s.append("\n");
		}
		s.append("\n");

		ArrayList<ArrayList<List<String>>> pathNonTouchLoop = pathsNonTouchLoops(loopLists, pathLists);
		for (int i = 0; i < pathNonTouchLoop.size(); i++) {
			s.append("Loops Non-Touching path");
			s.append(" ");
			s.append((i + 1));
			s.append("\n");
			for (int j = 0; j < pathNonTouchLoop.get(i).size(); j++) {
				s.append(pathNonTouchLoop.get(i).get(j));
				s.append("= ");
				s.append(calGainPath(pathNonTouchLoop.get(i).get(j)));
				s.append("  ");
				s.append("\n");

			}
			s.append("\n");
		}
		s.append("\n");

		ArrayList<ArrayList<List<String>>> twoNonTouching = twoNonTouchingLoops(loopLists);

		ArrayList<ArrayList<ArrayList<List<String>>>> allNonTouching = allNonTouchingLoops(twoNonTouching, loopLists);

		for (int i = 0; i < allNonTouching.size(); i++) {
			s.append((i + 2));
			s.append(" ");
			s.append("non-touching Loops:");
			s.append("\n");
			for (int j = 0; j < allNonTouching.get(i).size(); j++) {
				for (int k = 0; k < allNonTouching.get(i).get(j).size(); k++) {
					s.append(allNonTouching.get(i).get(j).get(k));
					s.append("= ");
					s.append(calGainPath(allNonTouching.get(i).get(j).get(k)));
					s.append("  ");
					s.append("\n");

				}
				s.append("\n");
			}
			s.append("\n");
		}
		s.append("\n");
		double DELTA = calDELTA(allNonTouching, loopLists);
		s.append("DELTA= ");
		s.append(DELTA);

		s.append("\n");
		s.append("\n");
		double[] DELTAs = calDELTAs(pathNonTouchLoop);

		for (int i = 0; i < DELTAs.length; i++) {
			s.append("DELTA");
			s.append(" ");
			s.append((i + 1));
			s.append("= ");
			s.append(DELTAs[i]);
			s.append("\n");
		}

		s.append("\n");

		s.append("T.F(gain)= ");
		s2.append("T.F(gain)= ");
		double temp = calTF(DELTA, DELTAs, pathLists);
		s.append(temp);
		s2.append(temp);

		return s.toString();

	}
	
	public String noSteps() {
		this.steps();
		return s2.toString();
	}

	private ArrayList<List<String>> forwardPaths() {

		AllDirectedPaths<String, DefaultWeightedEdge> paths = new AllDirectedPaths<>(G);

		List<GraphPath<String, DefaultWeightedEdge>> Lists = paths.getAllPaths(G.vertexSet().toArray()[0].toString(),
				G.vertexSet().toArray()[G.vertexSet().size() - 1].toString(), true, 100);

		ArrayList<List<String>> list = new ArrayList<List<String>>();
		for (int i = 0; i < Lists.size(); i++) {
			list.add(Lists.get(i).getVertexList());
		}
		return list;

	}

	private ArrayList<List<String>> individualLoops() {

		DirectedSimpleCycles<String, DefaultWeightedEdge> cycles = new HawickJamesSimpleCycles<>(G);

		ArrayList<List<String>> list = (ArrayList<List<String>>) cycles.findSimpleCycles();

		for (int i = 0; i < list.size(); i++) {
			List<String> s = list.get(i);
			for (int j = 0; j < s.size() / 2; j++) {
				String temp = s.get(j);
				s.set(j, s.get(s.size() - 1 - j));
				s.set(s.size() - 1 - j, temp);
			}
			s.add(s.get(0));

		}

		return list;
	}

	private ArrayList<ArrayList<List<String>>> twoNonTouchingLoops(ArrayList<List<String>> loopLists) {

		ArrayList<ArrayList<List<String>>> list = new ArrayList<ArrayList<List<String>>>();

		for (int i = 0; i < loopLists.size() - 1; i++) {
			for (int j = i + 1; j < loopLists.size(); j++) {
				if (isTwoNonTouched(loopLists.get(i), loopLists.get(j))) {
					ArrayList<List<String>> list2 = new ArrayList<List<String>>();
					list2.add(loopLists.get(i));
					list2.add(loopLists.get(j));
					list.add(list2);
				}
			}
		}
		return list;

	}

	private ArrayList<ArrayList<ArrayList<List<String>>>> allNonTouchingLoops(
			ArrayList<ArrayList<List<String>>> twoNonTouch, ArrayList<List<String>> loopLists) {

		ArrayList<ArrayList<ArrayList<List<String>>>> lists = new ArrayList<ArrayList<ArrayList<List<String>>>>();

		lists.add(twoNonTouch);

		while (lists.get(lists.size() - 1).size() > 1) {
			ArrayList<ArrayList<List<String>>> list = new ArrayList<ArrayList<List<String>>>();
			for (int i = 0; i < lists.get(lists.size() - 1).size(); i++) {
				for (int j = 0; j < loopLists.size(); j++) {
					if (isNonTouched(lists.get(lists.size() - 1).get(i), loopLists.get(j))) {
						ArrayList<List<String>> list2 = new ArrayList<List<String>>();
						for (int k = 0; k < lists.get(lists.size() - 1).get(i).size(); k++) {
							list2.add(lists.get(lists.size() - 1).get(i).get(k));
						}
						list2.add(loopLists.get(j));
						list.add(list2);
					}
				}

			}
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = i + 1; j < list.size(); j++) {
					if (isEqual(list.get(i), list.get(j))) {
						list.remove(j);
						i--;

					}
				}
			}

			lists.add(list);
		}

		return lists;

	}

	private boolean isTwoNonTouched(List<String> l1, List<String> l2) {

		for (int i = 0; i < l1.size() - 1; i++) {
			for (int j = 0; j < l2.size() - 1; j++) {
				if (l1.get(i) == l2.get(j)) {
					return false;
				}
			}
		}
		return true;

	}

	private boolean isEqual(ArrayList<List<String>> l1, ArrayList<List<String>> l2) {

		for (int i = 0; i < l1.size(); i++) {
			if (!(l2.contains(l1.get(i)))) {
				return false;
			}
		}

		return true;

	}

	private boolean isNonTouched(ArrayList<List<String>> l1, List<String> l2) {

		if (l1.contains(l2)) {
			return false;
		} else {
			for (int i = 0; i < l1.size(); i++) {
				if (!(isTwoNonTouched(l1.get(i), l2))) {
					return false;
				}
			}
		}
		return true;

	}

	private double calGainPath(List<String> loop) {

		double gain = 1.0;
		for (int i = 0; i < loop.size() - 1; i++) {
			gain *= G.getEdgeWeight(G.getEdge(loop.get(i), loop.get(i + 1)));
		}

		return gain;

	}

	private double calDELTA(ArrayList<ArrayList<ArrayList<List<String>>>> nontouchingLoops,
			ArrayList<List<String>> loopLists) {
		double sumOfAllLoops = 0;
		double sumOfNonTouchingLoops = 0;

		for (int i = 0; i < loopLists.size(); i++) {
			sumOfAllLoops += calGainPath(loopLists.get(i));
		}

		for (int i = 0; i < nontouchingLoops.size(); i++) {
			double temp = 0;
			for (int j = 0; j < nontouchingLoops.get(i).size(); j++) {
				double temp1 = 1.0;
				for (int k = 0; k < nontouchingLoops.get(i).get(j).size(); k++) {
					temp1 *= calGainPath(nontouchingLoops.get(i).get(j).get(k));
				}
				temp += temp1;
			}
			if ((i % 2) == 1) {
				temp = temp * -1.0;
			}
			sumOfNonTouchingLoops += temp;
		}
		return (1.0 - sumOfAllLoops + sumOfNonTouchingLoops);

	}

	private ArrayList<ArrayList<List<String>>> pathsNonTouchLoops(ArrayList<List<String>> loopLists,
			ArrayList<List<String>> pathLists) {
		ArrayList<ArrayList<List<String>>> list = new ArrayList<ArrayList<List<String>>>();
		for (int i = 0; i < pathLists.size(); i++) {
			ArrayList<List<String>> temp = new ArrayList<List<String>>();
			for (int j = 0; j < loopLists.size(); j++) {
				if (isTwoNonTouched(pathLists.get(i), loopLists.get(j))) {
					temp.add(loopLists.get(j));
				}
			}
			list.add(temp);
		}
		return list;
	}

	private double[] calDELTAs(ArrayList<ArrayList<List<String>>> pathsNonTouchLoops) {
		double[] DELTAs = new double[pathsNonTouchLoops.size()];

		for (int i = 0; i < pathsNonTouchLoops.size(); i++) {
			double temp = 0;
			for (int j = 0; j < pathsNonTouchLoops.get(i).size(); j++) {
				temp += calGainPath(pathsNonTouchLoops.get(i).get(j));
			}
			DELTAs[i] = 1.0 - temp;
		}
		return DELTAs;
	}

	private double calTF(double DELTA, double[] DELTAs, ArrayList<List<String>> pathLists) {
		double gain = 0;
		double temp = 0;
		for (int i = 0; i < pathLists.size(); i++) {
			temp += (DELTAs[i] * calGainPath(pathLists.get(i)));
		}
		gain = temp / DELTA;

		return gain;
	}
}
