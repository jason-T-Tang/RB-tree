package pa3;

//Makes a Website, a type of Node that can inserted into a tree, used as a head for a doubly Linked List, and data
public class Website {
	private String name;// name of website
	private int frequency;// Frequency of the key word and location of it
	private int age; // Age of the site
	private int links; // links from this site to others
	private int payment; // amount of money paid for advertisement
	private int PageScore;// Page's total score
	private int PageRank; // Page Rank relative to others
	private Website left; // left child of Website
	private Website right; // right child of Website
	private boolean isRed; // color indicator
	private Website p; // parent of website
	private int index; // index of Website

	// constructor for Websites
	public Website(String name) {
		this.name = name; // name is given as argument
		frequency = (int) (Math.random() * 100); // randomly generated frequency value
		age = (int) (Math.random() * 100);// randomly generated age
		links = (int) (Math.random() * 100);// randomly generated links score
		payment = (int) (Math.random() * 100);// randomly generated payment
		PageScore = frequency + age + links + payment;// PageScore as an accumulation of all the scores
		PageRank = 1;// Placeholder PageRank
		isRed = true; // Initializes websites as red
	}

	// constructor for Websites with chosen pageRank
	public Website(String name, int PageRank) {
		this.name = name; // name is given as argument
		frequency = (int) (Math.random() * 100); // randomly generated frequency value
		age = (int) (Math.random() * 100);// randomly generated age
		links = (int) (Math.random() * 100);// randomly generated links score
		payment = (int) (Math.random() * 100);// randomly generated payment
		//PageScore = frequency + age + links + payment;// PageScore as an accumulation of all the scores
		this.PageScore=frequency+age+links+payment;
		this.PageRank = PageRank;// initializes with PageRank
		if(PageScore==0) {
			isRed=false;
		}
		else {
		isRed = true; // Initializes websites as red
		}
	}
	// toString for Websites
	public String toString() {
		String color;		 //Converts boolean into String Red or Black
		if (isRed) {
		 color="Red";
	}
		else {
		color="Black";
	}
		return      " PageRank is: "+ PageRank+ color
				   +" Index is " + index + " PageScore is:" + PageScore + " URL is: " + name;
	}

	
	
	// Setter for page Score
	public void setPageScore(int pageScore) {
		PageScore = pageScore;
	}

	// Setter for PageRank
	public void setPageRank(int pageRank) {
		PageRank = pageRank;
	}

	// getter for PageScore
	public int getPageScore() {
		return PageScore;
	}

	// Getter for Left Child
	public Website getLeft() {
		return left;
	}

	// Getter for Right Child
	public Website getRight() {
		return right;
	}

	// Getter for Parent
	public Website getParent() {
		return p;
	}

	// Setter for left
	public void setLeft(Website site) {
		left = site;
	}

	// Setter for right
	public void setRight(Website site) {
		right = site;
	}

	// setter for Parent
	public void setParent(Website site) {
		p = site;
	}

	// Getter for PageRank
	public int getPageRank() {
		return PageRank;
	}

	// setter for index
	public void setIndex(int i) {
		index = i;
	}

	// Setter for isRed
	public void setIsRed(boolean truth) {
		isRed = truth;
	}

	// Getter for isRed
	public boolean getIsRed() {
		return isRed;
	}
}
