package population;

import gene.NodeTuple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;

public class MarkingsFileManager {

	public static void writeToFile(String filename, HistoricalMarkingManager hms) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		writer.print(hms.toString());
		writer.close();
	}
	
	public static HistoricalMarkingManager readFromFile(String filename) throws FileNotFoundException{
		HistoricalMarkingManager hms = new HistoricalMarkingManager();
		Scanner scan = new Scanner(new File(filename));
		scan.useDelimiter("\\s+");
		scan.useLocale(Locale.US);
		int nodes = nextInt(scan);
		for (int i = 0; i < nodes; i++) {
			hms.getNodeMark(new NodeTuple(nextInt(scan), nextInt(scan)));
			nextInt(scan);
		}
		int conns = nextInt(scan);
		for (int i = 0; i < conns; i++) {
			hms.getConnectionMark(new NodeTuple(nextInt(scan), nextInt(scan)));
			nextInt(scan);
		}
		return hms;
	}
	
	
	private static int nextInt(Scanner scan){
		while(!scan.hasNextInt()) scan.next();
		return scan.nextInt();
	}
}
