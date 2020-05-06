package swen221.shapes;

import java.util.ArrayList;

public class ShapeList {
	/*
	 * contain all the rectangle in the shape
	 */
	ArrayList<Shape> list = new ArrayList<>();
	
	public ArrayList<Shape> getList() {
		return this.list;
	}
	/*
	 * support three kind of operator, 1 at a time
	 */
	private boolean Union = false;
	private boolean	Diff = false;
	private boolean Inter = false;
	public void changeUnion()
	{
		this.Union=true;
	}
	public void changeDiff()
	{
		this.Diff=true;
	}
	public void changeInter()
	{
		this.Inter=true;
	}
	/*
	 * add shape to the list
	 */
	public void addShape(Shape s)
	{
		this.list.add(s);
		return;
	}
	/*
	 * add shapes to the list
	 */
	public void addShape(ArrayList<Shape> s)
	{
		for(int i=0;i<s.size();i++)
		{
			this.list.add(s.get(i));
		}
	}
	/*
	 * draw the bounding box of one rectangle
	 */
	public void drawShape(Shape s, Color c, Canvas cv)
	{
		Rectangle bound = s.boundingBox();
		for(int i=bound.getX();i<bound.getWidth()+bound.getX();i++)
		{
			cv.draw(i, bound.getY(), c);
			cv.draw(i, bound.getY()+bound.getHeight()-1, c);
		}
		
		for(int i=bound.getY();i<bound.getHeight()+bound.getY();i++)
		{
			cv.draw(bound.getX(), i, c);
			cv.draw(bound.getX()+bound.getWidth()-1, i, c);
		}
	}
	/*
	 * fill one rectangle with color c on canvas cv
	 */
	public void fillShape(Shape s, Color c, Canvas cv)
	{
		Rectangle bound = s.boundingBox();
		for(int i=bound.getX();i<bound.getWidth()+bound.getX();i++)
		{
			for(int j=bound.getY();j<bound.getHeight()+bound.getY();j++)
			{
				cv.draw(i, j, c);
			}
		}
	}
	/*
	 * this method draws bounding box of one rectangle, or all the rectangle in the list with given operator
	 */
	public void draw(Color c, Canvas cv) {
		if(this.list.size()==1)
		{
			this.drawShape(this.list.get(0), c, cv);
		}
		else if(this.Union)
		{
			this.drawUnion(c, cv);
		}
		else if(this.Diff)
		{
			this.drawDiff(c, cv);
		}
		else if(this.Inter)
		{
			this.drawInter(c, cv);
		}
	}
	/*
	 * this method fills one rectangle, or all the rectangle in the list with given operator
	 */
	public void fill(Color c, Canvas cv) {
		if(this.list.size()==1)
		{
			this.fillShape(this.list.get(0), c, cv);
		}
		else if(this.Union)
		{
			this.fillUnion(c, cv);
		}
		else if(this.Diff)
		{
			this.fillDiff(c, cv);
		}
		else if(this.Inter)
		{
			this.fillInter(c, cv);
		}
	}
	/*
	 * draw union of n shapes
	 */
	public void drawUnion(Color c, Canvas cv) {
		//for cac shape
		for(int i=0;i<this.list.size();i++)
		{
			//for cac diem thuoc shape nay
			int thisRect = i;
			Rectangle temp = this.list.get(i).boundingBox();
			int x1 = temp.getX();
			int x2 = temp.getX()+temp.getWidth()-1;
			int y1 = temp.getY();
			int y2 = temp.getY()+temp.getHeight()-1;
			/////////////////////////////////
			//cac diem thuoc 2 canh tren duoi
			//[x1-x2, y1], [x1-x2, y2]
			for(int x=x1; x<=x2; x++)
			{
				int check1=0;//kiem tra sem canh tren cuoi cung co dk ve ko
				int check2=0;//kiem tra sem canh duoi cuoi cung co dk ve ko
				//xet cac hinh con lai
				for(int j = 0; j < this.list.size(); j++)
				{
					if(j!=thisRect)
					{
						//ktra hinh co trung nhau hay ko
						Rectangle another = this.list.get(j).boundingBox();
						if(another.shading(temp))//co override nhau
						{
							//ve cac cac diem cua hinh nay ma no phai thuoc bound tat ca cac hinh khac
							//hoac khong thuoc bound 1 hinhf nao ca
							if(another.contains(x, y1))
							{
								//nam o trong ma kp nam o bound thi vut
								
							if(!another.boundContains(x, y1))
								check1++;
							else if(!temp.boundContains(x, y1))
								check1++;
							}
							if(another.contains(x, y2))
							{
								//nam o trong ma kp nam o bound thi vut
								
							if(!another.boundContains(x, y2))
								check2++;
							else if(!temp.boundContains(x, y2))
								check2++;
							}
						}
						else//ko override
						{
						//kiem tra diem tren co thuoc hinh khac nay khong
							if(another.contains(x, y1))
								check1++;
							else if(another.nextToBound(x, y1))
							{			if(!(another.isCorner(x, y1)||temp.isCorner(x, y1)))
										check1++;}
						//kiem tra diem duoi co thuoc hinh khac nay khong
							if(another.contains(x, y2))
								check2++;
							else if(another.nextToBound(x, y2))
							{
									if(!(another.isCorner(x, y2)||temp.isCorner(x, y2)))
										check2++;}
						}
					}
				}
				if(check1==0)
					cv.draw(x, y1, c);//ve diem x,y1
				if(check2==0)
					cv.draw(x, y2, c);//ve diem x,y2
			}
			////////////////////////////////
			//cac diem thuoc 2 canh phai trai
			//[x1, y1-y2], [x2, y1-y2]
			for(int y = y1; y<=y2;y++)
			{
				int check1 = 0;//kiem tra canh ben trai cuoi cung co dk ve ko
				int check2 = 0;//kiem tra canh ben phai cuoi cung co dk ve ko
				//xet cac hinh con lai
				for(int j = 0; j < this.list.size(); j++)
				{
					if(j!=thisRect)
					{
						Rectangle another = this.list.get(j).boundingBox();
						if(another.shading(temp))//co override nhau
						{
							//ve cac cac diem cua hinh nay ma no phai thuoc bound tat ca cac hinh khac
							//hoac khong thuoc bound 1 hinhf nao ca
							if(another.contains(x1, y))
							{
								//nam o trong ma kp nam o bound thi vut
								
							if(!another.boundContains(x1, y))
								check1++;
							else if(!temp.boundContains(x1, y))
								check1++;
							}
							if(another.contains(x2, y))
							{
								//nam o trong ma kp nam o bound thi vut
								
							if(!another.boundContains(x2, y))
								check2++;
							else if(!temp.boundContains(x2, y))
								check2++;
							}
						}
						else//ko override
						{
						//kiem tra diem tren co thuoc hinh khac nay khong
						if(another.contains(x1, y))
							check1++;
						else if(another.nextToBound(x1, y))
						{	if(!(another.isCorner(x1, y)||temp.isCorner(x1, y)))
							check1++;}
						//kiem tra diem duoi co thuoc hinh khac nay khong
						if(another.contains(x2, y))
							check2++;
						else if(another.nextToBound(x2, y))
						{	if(!(another.isCorner(x2, y)||temp.isCorner(x2, y)))
							check2++;}
						}
					}
				}
				if(check1==0)
					cv.draw(x1, y, c);//ve diem x1,y
				if(check2==0)
					cv.draw(x2, y, c);//ve diem x2,y
			}
		}
	}
	/*
	 * fill union of n shapes
	 * fill every shape in the list
	 */
	public void fillUnion(Color c, Canvas cv) {
		for(int i=0;i<this.list.size();i++)
		{
			Shape temp = this.list.get(i);
			this.fillShape(temp, c, cv);
		}
	}
	/*
	 * draw different of n shapes
	 * x = a - b - c - d
	 * draw every points on the bounding box of a that is not in b, c, d
	 * for b, c, d draw every point which is in a but not in other shapes 
	 */
	public void drawDiff(Color c, Canvas cv) {
		//shape dau tien A: draw cac bound ma ko thuoc cac shape con lai
		Rectangle A = this.list.get(0).boundingBox();
		//shape A
		int x1 = A.getX();
		int x2 = A.getX()+A.getWidth()-1;
		int y1 = A.getY();
		int y2 = A.getY()+A.getHeight()-1;
		for(int x=x1; x<=x2; x++)
		{
			int check1=0;//kiem tra sem canh tren co thuoc hinh nao khong
			int check2=0;//kiem tra sem canh duoi co thuoc hinh nao khong
			//xet cac hinh con lai
			for(int j = 1; j < this.list.size(); j++)
			{
					Rectangle another = this.list.get(j).boundingBox();
					//kiem tra diem tren co thuoc hinh nay khong
					if(another.contains(x, y1))
						check1++;
					//kiem tra diem duoi co thuoc hinh nay khong
					if(another.contains(x, y2))
						check2++;
			}
			if(check1==0)
				cv.draw(x, y1, c);//ve diem x,y1
			if(check2==0)
				cv.draw(x, y2, c);//ve diem x,y2
		}
		for(int y = y1; y<=y2;y++)
		{
			int check1 = 0;//kiem tra canh ben trai co thuoc hinh nao khong
			int check2 = 0;//kiem tra canh ben phai co thuoc hinh nao khong
			//xet cac hinh con lai
			for(int j = 1; j < this.list.size(); j++)
			{
					Rectangle another = this.list.get(j).boundingBox();
					//kiem tra diem ben trai co thuoc hinh khong
					if(another.contains(x1, y)) check1++;
					//kiem tra diem ben phai co thuoc hinh khong
					if(another.contains(x2, y)) check2++;
			}
			if(check1==0)
				cv.draw(x1, y, c);//ve diem x1,y
			if(check2==0)
				cv.draw(x2, y, c);//ve diem x2,y
		}
		//cac shape tiep theo: chi draw cac canh ben canh bound ma nam trong shape ban dau
		//dong thoi ko nam trong cac shapes con lai
		for(int i=1;i<this.list.size();i++)
		{
			int thisRec = i;
			Rectangle temp = this.list.get(i).boundingBox();
			int X1 = temp.getX();
			int X2 = temp.getX()+temp.getWidth()-1;
			int Y1 = temp.getY();
			int Y2 = temp.getY()+temp.getHeight()-1;
			//x1,y1-1-->x2,y1-1
			//x1,y2+1-->x2,y2+1
			//x1-1,y1-->x1-1,y2
			//x2+1,y1-->x2+1,y2
			
			for(int x = X1; x<= X2; x++)
			{
				if(A.contains(x, Y1-1))
				{
					int check = 0;
					for(int j = 1; j<this.list.size();j++)
					{
						if(j!=thisRec)
						{
							if(this.list.get(j).boundingBox().contains(x, Y1-1))
								{
									check++;
									break;
								}
						}
					}
					if(check==0) cv.draw(x, Y1-1, c);
				}
			}
			for(int x = X1; x<= X2; x++)
			{
				if(A.contains(x, Y2+1))
				{
					int check = 0;
					for(int j = 1; j<this.list.size();j++)
					{
						if(j!=thisRec)
						{
							if(this.list.get(j).boundingBox().contains(x, Y2+1))
								{
									check++;
									break;
								}
						}
					}
					if(check==0) cv.draw(x, Y2+1, c);
				}
			}
			for(int y = Y1; y <= Y2;y++)
			{
				if(A.contains(X1-1, y))
				{
					int check = 0;
					for(int j = 1; j<this.list.size();j++)
					{
						if(j!=thisRec)
						{
							if(this.list.get(j).boundingBox().contains(X1-1, y))
								{
									check++;
									break;
								}
						}
					}
					if(check==0) cv.draw(X1-1, y, c);
				}
			}
			for(int y = Y1; y <= Y2;y++)
			{
				if(A.contains(X2+1, y))
				{
					int check = 0;
					for(int j = 1; j<this.list.size();j++)
					{
						if(j!=thisRec)
						{
							if(this.list.get(j).boundingBox().contains(X2+1, y))
								{
									check++;
									break;
								}
						}
					}
					if(check==0) cv.draw(X2+1, y, c);
				}
			}
		}
	}
	/*
	 * fill different of n shapes
	 * x = a - b - c - d
	 * draw every point on a that is not in b, c or d
	 */
	public void fillDiff(Color c, Canvas cv) {
		Rectangle A = this.list.get(0).boundingBox();
		//shape A
		int x1 = A.getX();
		int x2 = A.getX()+A.getWidth()-1;
		int y1 = A.getY();
		int y2 = A.getY()+A.getHeight()-1;
		//xet cac diem thuoc shape A, neu cac diem do ko thuoc tat ca cac shape con lai thi ve
		for(int x = x1;x<=x2;x++)
			for(int y = y1;y<=y2;y++)
			{
				int check=0;
				//ktra so hinh khac ma cac diem cua A thuoc ve
				//check = 0
				//xet cac hinh con laji
				for(int i = 1; i < this.list.size(); i++)
				{
					Rectangle temp = this.list.get(i).boundingBox();
					if(temp.contains(x, y))
					{
						check++;
					}
				}
				if(check==0)
					cv.draw(x, y, c);
			}
	}
	/*
	 * draw intersection of n shapes
	 * for every shapes
	 * draw the point on its bound
	 * which is contained in all other shapes
	 */
	public void drawInter(Color c, Canvas cv) {
		//voi moi shape
		//draw cac diem thuoc bound cua shape do
		//va co trong cac shape con lai
		for(int i=0;i<this.list.size();i++)
		{
			Rectangle temp = this.list.get(i).boundingBox();
			int RecIndex = i; 
			//rect temp
			int x1 = temp.getX();
			int x2 = temp.getX()+temp.getWidth()-1;
			int y1 = temp.getY();
			int y2 = temp.getY()+temp.getHeight()-1;
			for(int x=x1; x<=x2; x++)
			{
				int check1=0;//kiem tra sem canh tren co thuoc hinh nao khong
				int check2=0;//kiem tra sem canh duoi co thuoc hinh nao khong
				//xet cac hinh con lai
				for(int j = 0; j < this.list.size(); j++)
				{
					if(j!=RecIndex)
					{
						Rectangle another = this.list.get(j).boundingBox();
						//kiem tra diem tren co thuoc hinh nay khong
						if(another.contains(x, y1))
							check1++;
						//kiem tra diem duoi co thuoc hinh nay khong
						if(another.contains(x, y2))
							check2++;
					}
				}
				if(check1==this.list.size()-1)
					cv.draw(x, y1, c);//ve diem x,y1
				if(check2==this.list.size()-1)
					cv.draw(x, y2, c);//ve diem x,y2
			}
			for(int y = y1; y<=y2;y++)
			{
				int check1 = 0;//kiem tra canh ben trai co thuoc hinh nao khong
				int check2 = 0;//kiem tra canh ben phai co thuoc hinh nao khong
				//xet cac hinh con lai
				for(int j = 0; j < this.list.size(); j++)
				{
					if(j!=RecIndex)
					{
						Rectangle another = this.list.get(j).boundingBox();
						//kiem tra diem ben trai co thuoc hinh khong
						if(another.contains(x1, y)) check1++;
						//kiem tra diem ben phai co thuoc hinh khong
						if(another.contains(x2, y)) check2++;
					}
				}
				if(check1==this.list.size()-1)
					cv.draw(x1, y, c);//ve diem x1,y
				if(check2==this.list.size()-1)
					cv.draw(x2, y, c);//ve diem x2,y
			}
		}
	}
	/*
	 * fill intersection of n shape
	 * draw every point on first shape that is in every other shapes
	 */
	public void fillInter(Color c, Canvas cv) {
		Rectangle A = this.list.get(0).boundingBox();
		//shape A
		int x1 = A.getX();
		int x2 = A.getX()+A.getWidth()-1;
		int y1 = A.getY();
		int y2 = A.getY()+A.getHeight()-1;
		//xet cac diem thuoc shape A, neu cac diem do thuoc tat ca cac shape con lai thi ve
		for(int x = x1;x<=x2;x++)
			for(int y = y1;y<=y2;y++)
			{
				int check=0;
				//ktra so hinh khac ma cac diem cua A thuoc ve
				//check = list.length - 1
				//xet cac hinh con laji
				for(int i = 1; i < this.list.size(); i++)
				{
					Rectangle temp = this.list.get(i).boundingBox();
					if(temp.contains(x, y))
					{
						check++;
					}
				}
				if(check==this.list.size()-1)
					cv.draw(x, y, c);
			}
	}
}
