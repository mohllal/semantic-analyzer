package semantic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import semantic.SemanticAnalyzer;

public class TestSemantic {
	public static void main(String[] args) throws IOException {
		if (args.length == 0)
			System.err.println("No file arguments givens");
		else {
			// parse each file argument given
			for (int i = 0; i < args.length; i++) {
				FileReader file;
				
				// attempt to open file
				try {
					file = new FileReader(args[i]);
				} catch (FileNotFoundException e) {
					System.err.println(args[i] + " was not found!");
					continue; // try next file
				}
				
				// create semantic analyzer
				SemanticAnalyzer semantic = new SemanticAnalyzer(file);
				System.out.println("Analyzing " + args[i] + "...");
				
				// initiate parse and clock time
				long startTime = System.currentTimeMillis();
				semantic.analyzeProgram();
				long endTime = System.currentTimeMillis();
				
				// print out statistics
				System.out.println("File has finished analyzing!");
				System.out.println("Execution time: " + (endTime - startTime) + "ms");
				System.out.println(semantic.getErrors() + " errors reported");
				System.out.println("---");
			}
		}
	}
}