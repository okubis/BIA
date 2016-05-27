package population;

import gene.InputNode;
import gene.NodeTuple;
import gene.OutputNode;

import java.util.HashMap;

public class HistoricalMarkingManager {
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
}