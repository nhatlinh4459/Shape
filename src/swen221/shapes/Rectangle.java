package swen221.shapes;

public class Rectangle implements Shape {
	private int x;
	private int y;
	private int width;
	private int height;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		if(x<0) throw new IllegalArgumentException();
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		if(y<0) throw new IllegalArgumentException();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		if(width<=0) throw new IllegalArgumentException();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		if(height<=0) throw new IllegalArgumentException();
	}

	public Rectangle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	/*
	 * to check if a point with x and y cordinate is in this rectangle or not 
	 */
	public boolean contains(int x, int y) {
		if((x>=this.getX())&&(x<this.getX()+this.getWidth())&&(y>=this.getY())&&(y<this.getY()+this.getHeight()))
		return true;
		else
		return false;
	}
	
	/*
	 * to check if a point with x, y cordinate is in the bound of the rectangle or not
	 */
	public boolean boundContains(int x, int y)
	{
		if(this.LeftBound(x, y)||this.RightBound(x, y)||this.LowerBound(x, y)||this.UpperBound(x, y))
			return true;
		return false;
	}
	public boolean UpperBound(int x, int y)
	{
		int x1=this.getX();
		int x2=this.getX()+this.getWidth()-1;
		int y1=this.getY();
		int y2=this.getY()+this.getHeight()-1;
		if(y==y1)
			if(x>=x1&&x<=x2)
				return true;
		return false;
	}
	public boolean LowerBound(int x, int y)
	{
		int x1=this.getX();
		int x2=this.getX()+this.getWidth()-1;
		int y1=this.getY();
		int y2=this.getY()+this.getHeight()-1;
		if(y==y2)
			if(x>=x1&&x<=x2)
		return true;
		return false;
	}
	public boolean RightBound(int x, int y)
	{
		int x1=this.getX();
		int x2=this.getX()+this.getWidth()-1;
		int y1=this.getY();
		int y2=this.getY()+this.getHeight()-1;
		if(x==x2)
			if(y>=y1&&y<=y2)
				return true;
		return false;
	}
	public boolean LeftBound(int x, int y)
	{
		int x1=this.getX();
		int x2=this.getX()+this.getWidth()-1;
		int y1=this.getY();
		int y2=this.getY()+this.getHeight()-1;
		if(x==x1)
			if(y>=y1&&y<=y2)
		return true;
		return false;
	}
	/*
	 * to check if a point with x, y cordinate is right next to the bound of the rectangle or not
	 */
	public boolean nextToBound(int x, int y)
	{
		int x1=this.getX();
		int x2=this.getX()+this.getWidth()-1;
		int y1=this.getY();
		int y2=this.getY()+this.getHeight()-1;
		if(x==x1-1||x==x2+1)
			if(y<=y2&&y>=y1)
				return true;
		if(y==y1-1||y==y2+1)
			if(x>=x1&&x<=x2)
				return true;
		return false;
	}
	/*
	 * to check if a point with x, y cordinate is around the corner of the rectangle or not
	 */
	public boolean isCorner(int x, int y)
	{
		int x1=this.getX();
		int x2=this.getX()+this.getWidth()-1;
		int y1=this.getY();
		int y2=this.getY()+this.getHeight()-1;
		for(int i=x1-1;i<=x1;i++)
			for(int j=y1-1;j<=y1;j++)
			{
				if(x==i&&y==j)
					return true;
			}
		for(int i=x2;i<=x2+1;i++)
			for(int j=y1-1;j<=y1;j++)
			{
				if(x==i&&y==j)
					return true;
			}
		for(int i=x1-1;i<=x1;i++)
			for(int j=y2;j<=y2+1;j++)
			{
				if(x==i&&y==j)
					return true;
			}
		for(int i=x2;i<=x2+1;i++)
			for(int j=y2;j<=y2+1;j++)
			{
				if(x==i&&y==j)
					return true;
			}
		return false;
	}
	/*
	 * to check if two rectangle has any common point
	 * the one in the bound is not included
	 */
	public boolean shading(Rectangle X) {
		//neu co chung canh hoac 2 canh de len nhau thi ko tinh
		//check if two triangle override
		//check diem o ben trong x
		int x1 = X.getX();
		int x2 = X.getX()+X.getWidth()-1;
		int y1 = X.getY();
		int y2 = X.getY()+X.getHeight()-1;
		//xet cac diem cua X 
		//neu co diem nao thuoc rectangle nam o trong thi ok
		for(int x = x1; x <= x2; x++)
			for(int y = y1; y<=y2;y++)
			{
					if(this.contains(x, y))
					{
						return true;
					}
			}
		return false;
	}
	@Override
	/*
	 * bound of a rectangle
	 */
	public Rectangle boundingBox() {
		int x = this.getX();
		int y = this.getY();
		int height =  this.getHeight();
		int width = this.getWidth();
		Rectangle bound = new Rectangle();
		bound.setX(x);
		bound.setY(y);
		bound.setHeight(height);
		bound.setWidth(width);
		return bound;
	}
}
