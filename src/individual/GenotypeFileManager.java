package individual;

import gene.Connection;
import gene.HiddenNode;
import gene.InputNode;
import gene.OutputNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;

public class GenotypeFileManager {
	public static void writeToFile(String filename, Genotype gen) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		writer.println(gen.toString());
		writer.close();
	}
	
	public static Genotype readFromFile(String filename) throws FileNotFoundException{
		Genotype gen = new Genotype();
		Scanner scan = new Scanner(new File(filename));
		scan.useDelimiter("\\s+");
		scan.useLocale(Locale.US);
		int gc = nextInt(scan);
		int nc = nextInt(scan);
		int hidden = nc - InputNode.INPUTS_COUNT - OutputNode.OUTPUTS_COUNT;
		int mark;
		double param;
		double bias;
		for (int i = 0; i < InputNode.INPUTS_COUNT; i++) {
			param = nextDouble(scan);
			bias = nextDouble(scan);
			mark = nextInt(scan);
			gen.addNode(new InputNode(param, mark, bias));
		}
		for (int i = 0; i < hidden; i++) {
			param = nextDouble(scan);
			bias = nextDouble(scan);
			mark = nextInt(scan);
			gen.addNode(new HiddenNode(param, mark, bias));
		}
		for (int i = 0; i < OutputNode.OUTPUTS_COUNT; i++) {
			param = nextDouble(scan);
			bias = nextDouble(scan);
			mark = nextInt(scan);
			gen.addNode(new OutputNode(param, mark, bias));
		}
		int start;
		int end;
		boolean en;
		boolean isRec;
		for (int i = 0; i < gc - nc; i++) {
			start = nextInt(scan);
			end = nextInt(scan);
			param = nextDouble(scan);
			en = nextBoolean(scan);
			isRec = nextBoolean(scan);
			mark = nextInt(scan);
			gen.addConnectionWithoutCheck(new Connection(start, end, mark, param, en));
		}
		scan.close();
		return gen;
	}
	
	private static int nextInt(Scanner scan){
		while(!scan.hasNextInt()) scan.next();
		return scan.nextInt();
	}
	
	private static double nextDouble(Scanner scan){
		while(!scan.hasNextDouble()) scan.next();
		return scan.nextDouble();
	}
	
	private static boolean nextBoolean(Scanner scan){
		while(!scan.hasNextBoolean()) scan.next();
		return scan.nextBoolean();
	}
}
