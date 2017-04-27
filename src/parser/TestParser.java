package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import ast.Program;
import parser.Parser;
import visitor.PrintVisitor;

public class TestParser {
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
				
				// create parser
				Parser parser = new Parser(file);
				System.out.println("Parsing " + args[i] + "...");
				
				// initiate parse and clock time
				long startTime = System.currentTimeMillis();
				Program program = parser.parseProgram();
				long endTime = System.currentTimeMillis();
				
				// print out statistics
				System.out.println("File has finished parsing!");
				System.out.println("Execution time: " + (endTime - startTime) + "ms");
				System.out.println(parser.getErrors() + " errors reported");
				System.out.println("---");
				
				// print out ASTs
				PrintVisitor printer = new PrintVisitor();
				printer.visit(program);
				System.out.println();
			}
		}
	}
}