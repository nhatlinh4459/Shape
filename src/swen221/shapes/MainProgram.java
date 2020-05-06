package swen221.shapes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainProgram {
	//read text file and ui show
	public static void main(String[] args) throws IOException {
		File shapes = new File("C:\\Users\\phamx\\eclipse-workspace\\SHAPE\\src\\swen221\\shapes\\shapes.txt");   
		BufferedReader br = new BufferedReader(new FileReader(shapes)); 
		String input="";	
		String temp;
		StringBuilder sb1 = new StringBuilder(); 
		  while ((temp = br.readLine()) != null) 
		  {
			  sb1.append(temp);
			  sb1.append("\n");
		  }
		input=sb1.toString();
		br.close();
		Interpreter test = new Interpreter(input);
		Canvas cv = test.run();
		cv.show();
		//////////////////////////////////////////////
		File smiley = new File("C:\\Users\\phamx\\eclipse-workspace\\SHAPE\\src\\swen221\\shapes\\smiley.txt");   
		br = new BufferedReader(new FileReader(smiley)); 
		StringBuilder sb2 = new StringBuilder(); 
		  while ((temp = br.readLine()) != null) 
		  {
			  sb2.append(temp);
			  sb2.append("\n");
		  }
		input=sb2.toString();
		br.close();
		test = new Interpreter(input);
		cv = test.run();
		cv.show();
		////////////////////////////////////////////////
		File squares = new File("C:\\Users\\phamx\\eclipse-workspace\\SHAPE\\src\\swen221\\shapes\\squares.txt");   
		br = new BufferedReader(new FileReader(squares)); 
		StringBuilder sb3 = new StringBuilder(); 
		  while ((temp = br.readLine()) != null) 
		  {
			  sb3.append(temp);
			  sb3.append("\n");
		  }
		input=sb3.toString();
		br.close();
		test = new Interpreter(input);
		cv = test.run();
		cv.show();
	}

}
