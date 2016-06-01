package population;

import gene.InputNode;
import gene.NodeTuple;
import gene.OutputNode;

import java.io.Serializable;
import java.util.HashMap;

public class HistoricalMarkingManager implements Serializable {
	private HashMap<NodeTuple, Integer> connections;
	
	private HashMap<NodeTuple, Integer> nodes;
	private int nextMarkCon;
	private int nextMarkNode = InputNode.INPUTS_COUNT + OutputNode.OUTPUTS_COUNT;

	public HistoricalMarkingManager(){
		connections = new HashMap<NodeTuple, Integer>();
		nodes = new HashMap<NodeTuple, Integer>();
		nextMarkCon=0;
	}
	
	public int getConnectionMark(NodeTuple tuple){
		Integer mark = connections.get(tuple);
		if(mark == null){
			mark = nextMarkCon++;
			connections.put(tuple, mark);
		}
		return mark;
	}
	
	public int getNodeMark(NodeTuple tuple){
		Integer mark = nodes.get(tuple);
		if(mark == null){
			mark = nextMarkNode++;
			nodes.put(tuple, mark);
		}
		return mark;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder("Nodes: " + nodes.size() + " \n");
		String[] marksStrings = new String[nodes.size() + InputNode.INPUTS_COUNT + OutputNode.OUTPUTS_COUNT];
		for (NodeTuple t : nodes.keySet()) {
			marksStrings[nodes.get(t)] = ("From: " + t.getStart() + " , to: " + t.getEnd() + " , mark: " + nodes.get(t) + " \n");
		}
		for(int i = InputNode.INPUTS_COUNT + OutputNode.OUTPUTS_COUNT; i < nodes.size() + InputNode.INPUTS_COUNT + OutputNode.OUTPUTS_COUNT; i++){
			s.append(marksStrings[i]);
		}
		s.append("Connections: " + connections.size() + " \n");
		marksStrings = new String[connections.size()];
		for (NodeTuple t : connections.keySet()) {
			marksStrings[connections.get(t)] = ("From: " + t.getStart() + " , to: " + t.getEnd() + " , connection: " + connections.get(t) + " \n");
		}
		for(int i = 0; i < connections.size(); i++){
			s.append(marksStrings[i]);
		}
		return s.toString();
	}
}
