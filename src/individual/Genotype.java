package individual;

import gene.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import com.sun.xml.internal.ws.api.pipe.NextAction;

public class Genotype {
	private ArrayList<Gene> genotype;
	private int nodeCount;
	
	public int getGenotypeSize(){
		return genotype.size();
	}

	public Genotype(ArrayList<Gene> genotype, int nodeCount) {
		this.genotype = genotype;
		this.nodeCount = nodeCount;
	}
	
	public Genotype(){
		genotype = new ArrayList<Gene>();
		nodeCount = 0;
	}
	
	public Gene get(int index){
		return genotype.get(index);
	}
	
	public int getNodeCount() {
		return nodeCount;
	}

	public boolean addNode(Node node){
		if(getNodeByMark(node.getMark()) == null){
			genotype.add(nodeCount++, node);
			return true;
		}
		return false;
	}
	
	public boolean addHiddenNodeAtIndex(HiddenNode node, int index){
		if(getNodeByMark(node.getMark()) == null){
			genotype.add(Math.max(index, InputNode.INPUTS_COUNT), node);
			nodeCount++;
			return true;
		}
		return false;
	}
	
	public boolean addHiddenNode(HiddenNode node, Connection con){
		int index = genotype.indexOf(getNodeByMark(con.getStart()));
		if(index >= 0 && genotype.indexOf(getNodeByMark(con.getEnd())) > index && getNodeByMark(node.getMark()) == null){
			genotype.add(Math.max(index + 1, InputNode.INPUTS_COUNT), node);
			nodeCount++;
			return true;
		}
		return false;
	}
	
	public boolean addConnection(Connection con){
		if(getConnectionByMark(con.getMark()) == null){
			int indexEnd = genotype.indexOf(getNodeByMark(con.getEnd()));
			int indexStart = genotype.indexOf(getNodeByMark(con.getStart()));
			Node start = (Node) genotype.get(indexStart);
			Node end = (Node) genotype.get(indexEnd);
			if(indexStart >= 0 && indexEnd > indexStart && !start.isOutput() && !end.isInput()){
				genotype.add(con);
				return true;
			}
		}
		return false;
	}
	
	protected void addConnectionWithoutCheck(Connection con){
		genotype.add(con);
	}
	
	public boolean addConnectionRecurrent(Connection con){
		if(getConnectionByMark(con.getMark()) == null){
			int indexEnd = genotype.indexOf(getNodeByMark(con.getEnd()));
			int indexStart = genotype.indexOf(getNodeByMark(con.getStart()));
			Node start = (Node) genotype.get(indexStart);
			Node end = (Node) genotype.get(indexEnd);
			if(!start.isOutput() && !end.isInput()){
				genotype.add(con);
				return true;
			}
		}
		return false;
	}
	
	public boolean disableConnection(Connection con){
		Connection c = (Connection) genotype.get(genotype.indexOf(con));
		if(c != null){
			c.disable();
			return true;
		}
		return false;
	}
	
	public boolean enableConnection(Connection con){
		Connection c = (Connection) genotype.get(genotype.indexOf(con));
		if(c != null){
			c.enable();
			return true;
		}
		return false;
	}
	
	public Node getNodeByMark(int mark){
		Node node = null;
		for (int i = 0; i < nodeCount; i++) {
			if(genotype.get(i).getMark() == mark){
				node = (Node) genotype.get(i);
				break;
			}
		}
		return node;
	}
	
	public Connection getConnectionByMark(int mark){
		Connection con = null;
		for (int i = nodeCount; i < getGenotypeSize(); i++) {
			if(genotype.get(i).getMark() == mark){
				con = (Connection) genotype.get(i);
				break;
			}
		}
		return con;
	}
	
	public boolean containsGene(Gene gen){
		if(gen.isConnection()){
			if( getConnectionByMark(((Connection) gen).getMark()) == null ) return false;
		}else{
			if( getNodeByMark(((Node) gen).getMark()) == null ) return false;
		}
		return true;
	}
	
	public void sortNodes(int sortedIndex, boolean[][] reachable){
		ArrayList<Node> toSort = new ArrayList<Node>();
		for (int i = nodeCount - 1; i >= sortedIndex; i--) {
			toSort.add((Node) genotype.get(i));
			genotype.remove(i);
		}
		int index = sortedIndex - 1;
		while(!toSort.isEmpty() && index >= 0){
			Node current = (Node) genotype.get(index);
			if(!current.isOutput()){
				if(current.isInput()){
					for (int i = toSort.size() - 1; i >= 0; i--) {
						genotype.add(index + 1, toSort.get(i));
						toSort.remove(i);
					}
				}else{
					for (int i = toSort.size() - 1; i >= 0; i--) {
						if(reachable[toSort.get(i).getMark()][current.getMark()]){
							genotype.add(index + 1, toSort.get(i));
							toSort.remove(i);
						}
					}
				}
			}
			index--;
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Gene count: " + this.getGenotypeSize() + " \n");
		s.append("Node count: " + this.nodeCount + " \n");
		for (int i = 0; i < this.getGenotypeSize(); i++) {
			s.append(genotype.get(i).toString() + " \n");
		}
		return s.toString();
	}
	
	
}
