package swen221.shapes;

import java.util.ArrayList;
	//analyze command type
	//analyze command and break into small pieces
	//one line at a time
public class StringProcessing {
	//normal command
	// "  fill     x     #abcxyz  "
	// " draw [  2  , 33,  12,4] #ancdyx  "
	//return{draw,[a,b,c,d]/varName,color}
	public ArrayList<String> norCmd(String input){//the first word is either fill or draw
		//we know that because this doesnt has '=' or any expression
		ArrayList<String> tmp = new ArrayList<>();
		//first let trim()
		input=input.trim();
		//main error is mistaking fill/draw
		//fils, drew
		//lay chu cai dau tien thi kha de, chi can substring tu 0 den whitespace dau tien
		int wordend=0;
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)==' ')
			{
				wordend=i;
				break;
			}
		}
		String word = input.substring(0, wordend);
		if(!(word.equalsIgnoreCase("draw"))&&!(word.equalsIgnoreCase("fill")))
			throw new IllegalArgumentException();//exception if the word is not draw and is not fill
		tmp.add(word);
		//missing color or wrong syntax color
		//ex:
		//draw x
		//draw [123, 345, 345, 345] #0 a s d /#0asd2werwe
		//sau dau # phai co du 6 character lien nhau, moi character la 1 digit hoac 1 letter
		//sau #abcdef thi khong duoc co bat cu ky tu nao
		int color_start = 0;
		int color_end=0;
		for(int i=input.length()-1;i>=0;i--)
		{
			if(input.charAt(i)=='#')
			{
				color_start = i;
				break;
			}
		}
		if(color_start == 0) throw new IllegalArgumentException();
		color_end=color_start+7;
		if(color_end!=input.length()) throw new IllegalArgumentException();
		String color = input.substring(color_start, color_end);
		//#abcxyz
		for(int i=1;i<color.length();i++)
		{
			//neu 1 char trong color kp la chu va cx kp la so thi throw
			if(!Character.isLetterOrDigit(color.charAt(i)))	throw new IllegalArgumentException();
		}
		//con lai nhung cai gi o giua whitespace dau tien va #
		String leftover = input.substring(wordend, color_start);
		leftover = leftover.trim();
		//substring cai nay ta se duoc string VarName hoac string Shape
		tmp.add(leftover);
		tmp.add(color);
		return tmp;
	}
	
	public boolean isNorCMD(String input){
		input=input.trim();
		if(input.contains("fill")||input.contains("draw"))
		return true;
		else return false;
	}
	//to see if the input String is a normal command or it has expression
	//search for the fill/draw
	
	
	
	//co 1 cap []
	public Shape GetShape(String input){
		Rectangle tmp = new Rectangle();
		input=input.trim();
		int openbracket=0;
		int closebracket=0;
		if(input.charAt(openbracket)!='[')
			throw new IllegalArgumentException();
		for(int i = 0;i<input.length();i++)
		{
			if(input.charAt(i)==']')
			{
				closebracket=i;
				break;
			}
		}
		if(closebracket==0) throw new IllegalArgumentException();
		input = input.substring(openbracket+1, closebracket);
		input = input.trim();
		int countcomma = 0;
		for(int i=0;i<input.length();i++)
		{
			//chi co the la space, comma, digit
			if(input.charAt(i)!=' ')
			{
				if(input.charAt(i)!=',')
				{
					if(!Character.isDigit(input.charAt(i)))
						throw new IllegalArgumentException();
				}
				else countcomma++;
			}
		}
		if(countcomma!=3) throw new IllegalArgumentException();
		//split by ','
		String[] split = input.split(",");
		for(int i=0;i<split.length;i++)
		{
			split[i]=split[i].trim();
		}
		
		for(int i=0;i<split.length;i++)
		{
			String temp = split[i];
			for(int j=0;j<temp.length();j++)
			{
				if(!Character.isDigit(temp.charAt(j)))
					throw new IllegalArgumentException();
			}
		}
		
		String x = split[0];
		String y = split[1];
		String width = split[2];
		String height = split[3];
		if((x.equalsIgnoreCase(""))||(y.equalsIgnoreCase(""))||(height.equalsIgnoreCase(""))||(width.equalsIgnoreCase("")))
				throw new IllegalArgumentException();
		int X = Integer.parseInt(x);
		int Y = Integer.parseInt(y);
		int WIDTH = Integer.parseInt(width);
		int HEIGHT = Integer.parseInt(height);
		
		tmp.setX(X);
		tmp.setY(Y);
		tmp.setHeight(HEIGHT);
		tmp.setWidth(WIDTH);
		//sau khi get dk 4 string, trim() thi phai check sem co cai nao rong ko
		return tmp;
	}
	//read shape [ 2343, 43, 23,  45](ko duoc co dau cham hoac dau -, neu co throw argument exception)
	//nch la lay phan giua 2 dau [], chi duoc co 3 char ',', digit, ' '
	//4 so nguyen???
	// con ko thi throw IllegalArgumentException
	
	
	
	//phan biet giua String varName va shape
	//not the ideal version of this test
	//the ideal test must be like having a pair of [], three ',' characters
	public boolean isShape(String input){
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)==',')
				return true;
		}
		return false;
	}
	//phan biet 1 string la loai ten var hay la shape[]  x, y, z and [23, 345, 54, 12]
	//shape thi co dau '[',',',']'
	
	
	
	public String RemoveBracket(String input) {//remove the bracket from a string, merge these with wExp/woExp
		//"  c = c - (tl+tr+bl+br) "
		//" y = (y + x)"
		//"x = ([0,0,3,3] + x)"
		//assuming these are just the only cases
		//remove the bracket and change all the expression to the main expression
		input=input.trim();
		//first, find the main expression
		int exp=0;
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)=='+'||input.charAt(i)=='-'||input.charAt(i)=='&')
			{
				exp=i;
				break;
			}
		}
		String express = input.substring(exp, exp+1);
		//then remove the bracket
		/////////////////////////////////////
		int check=0;//check++ looking for open bracket '('
		//check-- ; looking for close bracket ')'
		
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)=='(')
			{
				check++;
				input = input.substring(0, i)+' '+input.substring(i+1);
			}
			if(input.charAt(i)==')')
			{
				check--;
				input = input.substring(0, i)+' '+input.substring(i+1);
			}
		}
		if(check!=0)//number of close and open bracket does not equal
			throw new IllegalArgumentException();
		////////////////////////////////////////////
		
		
		//finally, change all the expression to the main expression
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)=='-'||input.charAt(i)=='+'||input.charAt(i)=='&')
			{
				input = input.substring(0, i)+express+input.substring(i+1);
			}
		}
		return input;
	}
	
	/////woExp and wExp actually return the arraylist of string after processing
	/////if use wExp for without expression operation, then the 2nd element of the list must be ignore
	////other way to say it: the list[1] will not store the expression
	
	//ex: "x=y"
	// ex: " x = [  4,  34,5,  12  ]   "
	/*
	 * the case for command with no expression but with assignment
	 * x=[3,4,5,6]
	 * x=y
	 * y = [3234,    34,   34543,   4454  ]
	 * 
	 */
	public ArrayList<String> woExp(String input){//without expression, only assignment '='
		input=this.RemoveBracket(input);
		//may as well remove all white space and then split by =
		input=this.RemoveBracket(input);
		ArrayList<String> tmp = new ArrayList<>();
		//remove head and tail spaces
		input = input.trim();
		input=input.replace(" ", "");
		/////////////////////////////////////////////////
		/*
		 * before the '=' not allow sign'+'
		 * '-', '&' or throw new IllegalArgumentException();
		 * only allow letter, digit, and white space
		 */
		int check=0;
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)=='=')
			{
				check=i;
			}
		}
		for(int i=0;i<check;i++)
		{
			if(!(Character.isLetter(input.charAt(i))) && !(Character.isDigit(input.charAt(i))) && input.charAt(i)!=' ')
					throw new IllegalArgumentException();
		}
		/////////////////////////////////////////////////////
		
		String[] list = input.split("=");
		for(int i=0;i<list.length;i++)
			tmp.add(list[i]);
		return tmp;
	}
	
	
	
	/* these are the cases for commands with expression
	 * x=x+y
	 * y=x - [2, 22,     4,   9]
	 * z = [4,5,756, 34534]&[0, 0,  0  ,   0    ]
	 * c = c - (tl+tr+bl+br)  --> assuming it only has one pair of brackets
	 * and assuming that after remove the brackets it only has one type of expression
	 * list[0] is the String contains the name of the first variable appeared on each operation 
	 * list[1] is the main expresion - + or &
	 * list[2..n] is the name of the var, or the shape description (from an open [ to a close ])
	 */
	public ArrayList<String> wExp(String input){//with expression
		input=this.RemoveBracket(input);
		//process input and then return a list of string
		ArrayList<String> tmp = new ArrayList<>();
		//String be like "  x=x+y  "
		//arraylist should be {x,+,x,y}
		//" y  =  x - [2, 22,     4,   9]" should return {y,-,x,[2, 22,     4,   9]}
		//clear the white space
		input = input.trim();
		int start1 = 0;//should be the first character
		int end1=0;
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)==' '||input.charAt(i)=='=')
			{
				end1=i;
				break;
			}
		}
		String var1=input.substring(start1, end1);//capture the assigned variable
		tmp.add(var1);
		int exp=0;
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)=='+'||input.charAt(i)=='-'||input.charAt(i)=='&')
			{
				exp=i;
				break;
			}
		}
		String express = input.substring(exp, exp+1);
		tmp.add(express);//capture the main expression
		
		/*
		 * before the '=' not allow sign'+'
		 * '-', '&' or throw new IllegalArgumentException();
		 * only allow letter, digit, and white space
		 */
		int check=0;
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)=='=')
			{
				check=i;
			}
		}
		for(int i=0;i<check;i++)
		{
			if(!(Character.isLetter(input.charAt(i))) && !(Character.isDigit(input.charAt(i))) && input.charAt(i)!=' ')
					throw new IllegalArgumentException();
		}
		//reduce the input to anything behind the '='
		for(int i=0;i<input.length();i++)
		{
			if(input.charAt(i)=='=')
			{
				input=input.substring(i+1, input.length());
				break;
			}
		}
		//oke now we have this"   x - [2, 22,     4,   9]"
		//first we need to trim()
		input=input.trim();
		//we have something like this "x - [2, 22,     4,   9]"
		//just need to split by the main expression and then trim() everything
		if(express.equals("+"))
			express="\\+";
		String[] split = input.split(express);
		for(int i=0;i<split.length;i++)
		{
			split[i]=split[i].trim();
			tmp.add(split[i]);
		}
		return tmp;
	}
	
	///var name store in list[2..n] if it is not in the hashmap<String, ShapeList> then throw exception
	
	//only using wExp, but in that case we have to consider the list[1]
	public boolean hasMainExp(ArrayList<String> list)
	{
		if(list.get(1).equals("+")||list.get(1).equals("-")||list.get(1).equals("&"))
			return true;
		else
		list.remove(1);
		return false;
	}
	
}
