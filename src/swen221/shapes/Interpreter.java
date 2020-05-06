package swen221.shapes;

import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {
    private String input;
    private int index;
    /*
     * a hashmap between shapelist Name with its shape list
     * e.g: x = a + b + c
     * then a shapelist name x with element [a,b,c] is added to the hashmap
     */
    public HashMap<String, ShapeList> list_of_shapes  = new HashMap<String, ShapeList>();
    
    public void updateHMap(String name, ShapeList s)
    {
    	if(this.list_of_shapes.containsKey(name))
    	{
    		this.list_of_shapes.replace(name, s);
    	}
    	else
    	{
    		this.list_of_shapes.put(name, s);
    	}
    }
    
    public Interpreter(String input) {
    	this.input = input;
    	this.index = 0;
    }

    /**
     * This method should return a canvas to which the input commands have been applied.
     * @return a canvas that shows the result of the input.
     */
	public Canvas run() {
		Canvas canvas = new Canvas();
		
		skipWhiteSpace();
		int test=index;//beginning of a cmd
		
		while (test <= input.length()) {
			if(test==input.length())
			{
				evaluateNextCommand(canvas);
				canvas = new Canvas(canvas);//update canvas
				test++;
			}
			else
			{
			if(input.charAt(test)=='\n')
			{
			evaluateNextCommand(canvas);
			canvas = new Canvas(canvas);//update canvas
			index = test;//after next line
			
			skipWhiteSpace();
			test=index;//begining of a cmnd
			}
			else
			test++;
			}
        }
		return canvas;
 	}
	/*
	 * this method analyzes and execute command
	 * one line at a time
	 */
	private void evaluateNextCommand(Canvas canvas) {
		skipWhiteSpace();
		//substring from begin of a cmd to the nearest '\n'//or the end of the string
		int nearest_nl=0;
		for(int i=index;i<input.length();i++)
		{
			if(input.charAt(i)=='\n')
			{
				nearest_nl = i;
				break;
			}
		}
		if(nearest_nl==0) nearest_nl=input.length();
		String newCmd = input.substring(index, nearest_nl);
		newCmd = newCmd.trim();
		if(newCmd.isEmpty()) return;
		///////////////////////////////////////
		StringProcessing sp = new StringProcessing();
		if(sp.isNorCMD(newCmd)) {
			ArrayList<String> splitCmd = sp.norCmd(newCmd);
			//draw/fill varname/shape[] color
			String varName="";
			if(sp.isShape(splitCmd.get(1)))
			{
				//norman Cmd with only shape-->execute right away
				Shape s = sp.GetShape(splitCmd.get(1));
				Color c = new Color(splitCmd.get(2));
				ShapeList tmp = new ShapeList();
				tmp.addShape(s);
				String Command = splitCmd.get(0);
				if(Command.equalsIgnoreCase("draw"))
				{
					tmp.draw(c, canvas);
				}
				if(Command.equalsIgnoreCase("fill"))
				{
					tmp.fill(c, canvas);
				}
				//new shapelist then add the getShape
				//-->draw/fill color
			}
			else 
				{
						//getting more tricky
						varName = splitCmd.get(1);
						//find varname in list_of_shapes
						//if cannot find then throw loi
						if(this.list_of_shapes.containsKey(varName))
						{
							ShapeList tmp = this.list_of_shapes.get(varName);
							
							Color c = new Color(splitCmd.get(2));
							
							String Command = splitCmd.get(0);
							if(Command.equalsIgnoreCase("draw"))
							{
								tmp.draw(c, canvas);
							}
							if(Command.equalsIgnoreCase("fill"))
							{
								tmp.fill(c, canvas);
							}
						}
						else
							throw new IllegalArgumentException();
				}
			
			}
		else//not a normal cmd
		{
			//cmd with operator
			//first let split command
			ArrayList<String> splitCmd = sp.wExp(newCmd);
			if(sp.hasMainExp(splitCmd))
			{
				//has main expression like + - &
				//listName,mainExp,varName/Shape
				String listName = splitCmd.get(0);
				ShapeList tmp = new ShapeList();
				String mainExp = splitCmd.get(1);
				if(mainExp.equals("+"))
				{
					tmp.changeUnion();
				}
				else if(mainExp.equals("-"))
				{
					tmp.changeDiff();
				}
				else if(mainExp.equals("&"))
				{
					tmp.changeInter();
				}
				else throw new IllegalArgumentException();
				//for for list[2..n]
				for(int i=2;i<splitCmd.size();i++)
				{
					String UnKnown = splitCmd.get(i);
					//check if a shape or a varname
					if(sp.isShape(UnKnown))
					{
						Shape s = sp.GetShape(UnKnown);
						tmp.addShape(s);
					}
					else
					{
						//is varname
						//check for existence, if not exist then throw 
						if(this.list_of_shapes.containsKey(UnKnown))
						{
							ArrayList<Shape> newList = this.list_of_shapes.get(UnKnown).getList();
							tmp.addShape(newList);
						}	
						else
							throw new IllegalArgumentException();
					}
				}
				
				//update hashmap
				this.updateHMap(listName, tmp);
			}
			else
			{	//list0=name of shape list
				//list1=shape[]/variablename
				//only assignment (=) operator
				String listName = splitCmd.get(0);
				ShapeList tmp = new ShapeList();
				if(sp.isShape(splitCmd.get(1)))
						{
							//x=shape[]
							Shape s = sp.GetShape(splitCmd.get(1));
							tmp.addShape(s);
							this.updateHMap(listName, tmp);
						}
				else
				{
					//list1 is variable name
					String varName = splitCmd.get(1);
					//check for existence and throw exception
					if(this.list_of_shapes.containsKey(varName))
					{
						ShapeList temp = this.list_of_shapes.get(varName);
						tmp.addShape(temp.getList());
						this.updateHMap(listName, tmp);
					}
					else throw new IllegalArgumentException();
				}
			}
		}
	}
	//doesnt use at all
    @SuppressWarnings("unused")
	private String readWord() {
        int start = index;
        while(index < input.length() && Character.isLetter(input.charAt(index))) {
                index++;
        }
        return input.substring(start,index);
    }
    private void skipWhiteSpace() {         
        while (index < input.length()
                        && (input.charAt(index) == ' ' || input.charAt(index) == '\n')) {
                index = index + 1;
        }
    }   
}
