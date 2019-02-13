package pa3;

import java.util.ArrayList;
import java.util.Collections;

public class RBTree {

	private Website root; //instance variable root

	//null constructor creates a null tree
	public RBTree() {
		root = null;
	}

	// builds the RB tree by random insertion
	public void buildRB(ArrayList<Website> array) {
		// generates a randomized list from 0 to arraysize
		ArrayList<Integer> insertionOrder = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			insertionOrder.add(i);
		}
		Collections.shuffle(insertionOrder); // randomizes the insertion order list
			
		for (int i = 0; i < array.size(); i++) { // Inserts all the Sites from the List into the Tree at the randomized
			System.out.println(array.get(insertionOrder.get(i))+"  ");								// order
			RBInsert(array.get(insertionOrder.get(i)));
		}
		System.out.println("");
		//Prints out results for testing purposes
		
		this.RBPrint(this.getRoot());
		System.out.println(""); //formatting for cleaner results
		
	}

	// runs a binary search on the RB Tree and returns the site if it is there
	public Website RBSearch(Website site, int value) {
		if (site == null || value == site.getPageRank()) { // If the site is null or the correct site, return the site
			return site; // return statement
		}
		if (site.getPageRank() < value) { // if the site's pagerank is lower, meaning it's value is higher than the
											// value needed, it goes to the left
			return RBSearch(site.getLeft(), value); // search to the left
		} else {
			return RBSearch(site.getRight(), value); // search to the right otherwise
		}
	}

	// Rotates the nodes of the RedBlackTree to the Left
	public void leftRotate(Website root, Website x) {
		Website y = x.getRight(); 	//set y
		x.setRight(y.getLeft());	//turn y's left subtree into x's right subtree
		if (y.getLeft() != null) {	//if y.left exists,
			y.getLeft().setParent(x);	//y.left's parent becomes x
		}
		y.setParent(x.getParent());	//link x's parent to y
		if (x.getParent() == null)  //if x is the root
			setRoot(y);				//y is now the root
		else if (x == x.getParent().getLeft())	//if x is the left child
			x.getParent().setLeft(y);	//x's parent sets its left as y
		else {
			x.getParent().setRight(y);		//if it is not the left child, it is the right child and x's parent sets its right as y
		}
		y.setLeft(x); 	//x on y's left
		x.setParent(y);	// link x to y
	}

	// Rotates the nodes of the RedBlackTree to the Right
	public void rightRotate(Website root, Website x) {
		Website y = x.getLeft();	//set y
		x.setLeft(y.getRight());	//turn y's right subtree into x's right subtree
		if (y.getRight() != null) { //if y.right exists,
			y.getRight().setParent(x);	//y.right's parent becomes x
		}
		y.setParent(x.getParent()); //link x's parent to y
		if (x.getParent() == null) { 	//if x is the root
			setRoot(y);					//update the root
		} 
		else if (x == x.getParent().getRight())	//if x is the right child
			x.getParent().setRight(y);			//x parent sets its right as y
		else {
			x.getParent().setLeft(y);	//if it is not the right child, then it is the left child, and x's parent sets its left as y
		}	
		y.setRight(x);	//x on y's right
		x.setParent(y); //link x to y
	}

	// Inserts the site into the RB Tree
	public void RBInsert(Website site) {
		Website y = null; // makes empty pointer y
		Website x = root; // starts at root
		while (x != null) { // if root is not null
			y = x; // points to x
			if (site.getPageRank() >= x.getPageRank()) { // if site has a greater pagerank, its value is smaller, and x
															// is on the left
				x = x.getLeft(); // goes to the left child of x
			} else {
				x = x.getRight(); // goes to the right child of x otherwise
			}
		}
		site.setParent(y); // sets the parent of the inserted Website to y
		if (y == null) { // list is empty
			setRoot(site); // set root to entered website
		} else if (site.getPageRank() >= y.getPageRank()) {// if pageRank is greater, score is smaller, belongs on the
															// left
			y.setLeft(site); // sets left to site
		} else {
			y.setRight(site); // else sets right to the site
		}
		RBInsertFixup(site); //calls fixup to ensure property
	}

	// Fixes the tree's coloring after insert
	public void RBInsertFixup(Website site) {
		if (site == root) {			//catches calling fixup on the root
			site.setIsRed(false);	//if it is the root, set to black
		} else if (site != root) {	//iff it is not the root, continue 
			
			while (site.getParent().getIsRed() == true
					&& site.getParent().getParent() != null) { //while site's parent is red and site's parent's parent is not null
				if (site.getParent() == site.getParent().getParent().getLeft()) { //z's parent is the left child
					Website y = site.getParent().getParent().getRight(); //y is the right uncle

					//case 1
					if (y != null && y.getIsRed() == true) { 	//if y is not a null, which is counted as black, or if y is red
						site.getParent().setIsRed(false); 		//sets site's parent to black
						if (site.getParent().getParent() != null) {	//if it will not cause a null pointer error,
							site.getParent().getParent().setIsRed(true); //sets grandparent to red
							site=site.getParent().getParent();			//sets site to grandparent
						}
						if (y != null) { //checks for null pointer exception
							y.setIsRed(false); //y is black
						}
					} 
					//case 2 rotate
					else if (site == site.getParent().getRight()) {
						site = site.getParent();	//updates site to its parent
						leftRotate(root, site);		//roates updated parent
					} else {	//case 3 rotate
						site.getParent().setIsRed(false);		//parent is black
						site.getParent().getParent().setIsRed(true);	//grandparent is red
						rightRotate(root, site.getParent().getParent());	//rotates to fix tree
					}

				} else {
					Website y = site.getParent().getParent().getLeft(); //y is the left uncle
					//case 1 simple recoloring
					if (y != null && y.getIsRed() == true) {  //if y is not a null, which is counted as black, or if y is red
						site.getParent().setIsRed(false);		//sets site's parent to black
						if (site.getParent().getParent() != null) {	//if it will not cause a null pointer error,
							site.getParent().getParent().setIsRed(true);   //sets grandparent to red
							site=site.getParent().getParent();				//sets site to grandparent
						}
						if (y != null) {	//checks for null pointer exception
							y.setIsRed(false);	//y is black
						}
					} 
						//case 2 rotate 
						else if (site == site.getParent().getLeft()) { 
						site = site.getParent();	//update's site to its parent
						rightRotate(root, site);	//rotates updated parent
					} 
						//case 3 rotate
						else {
						site.getParent().setIsRed(false);	//parent is black
						site.getParent().getParent().setIsRed(true);	//grandparent is red
						leftRotate(root, site.getParent().getParent());	//rotates to fix tree
					}
				}
				
			if(site==root) {         //if site is the root, 
				root.setIsRed(false);	//sets the root/site to black
				break;					//breaks out of the loop
			}
			}
		}
		root.setIsRed(false); //sets root to black just in case
	}

	// Deletes the given Website from the tree
	public void RBDelete(Website root, Website site) {
		Website y=site;
		Website x;	//sets up website x as the afterimage of x
		boolean originalColor=y.getIsRed();	//collects the deleted site's color
		if (site.getLeft() == null||site.getLeft().getPageRank()==0) { // if site has only one child on the right
			 x=site.getRight();			//x is the right
			 
			 //if x is null, create a null node that is black
			 if(x==null) {
					x=new Website("null",0);
					x.setIsRed(false);
					x.setParent(y);
					y.setRight(x);
				}			
			 RBTransplant(root, site, site.getRight()); // replaces the site with its right child
		}
			else if (site.getRight() == null||site.getLeft().getPageRank()==0) { // if site only has left child
				 x=site.getLeft();
				
				 //if x is null, create a null node that is black
				 if(x==null) {
						x=new Website("null",0);
						x.setIsRed(false);
						x.setParent(y);
						y.setLeft(x);
					}
				RBTransplant(root, site, site.getLeft()); // replaces site with left child
			}
		else { // two children case
			y = treeMinimum(site.getRight()); // finds minimum on the right side aka Successor
			originalColor=y.getIsRed();		//update's original color of y
			 x=y.getRight();				//x is the afterimage of y
			
			 //if x is null, create a null node that is black
			if(x==null) {
				x=new Website("null",0);
				x.setIsRed(false);
				x.setParent(y);
				y.setRight(x);
			}
			if (y.getParent() == site) { // if the successor is not already directly linked to the site
				x.setParent(y);				//links x to the y
			}
			else {
				RBTransplant(root, y, y.getRight()); // transplants the sucessor and its right child
				y.setRight(site.getRight()); // replaces y's right with site's right
				if(y.getRight()!=null)
				y.getRight().setParent(y); // sets y's new right parent to y
			}
			
			RBTransplant(root, site, y); // transplants the successor with the node to be deleted
			y.setLeft(site.getLeft()); // fixes the left pointers
			y.getLeft().setParent(y); // fixes left pointers
			y.setIsRed(site.getIsRed()); //sets y's color to the site's color
		
		}
		
		//calls fixup on the afterimage of the y, x
		if(!originalColor) {
			RBDeleteFixup(root,x);
		}
	}

	public void RBDeleteFixup(Website root, Website x) {
		while(x!=root&&x.getParent()!=null&&!x.getIsRed()) {	//while x is not the root, its parent is not null, and x is red,
			if(x==x.getParent().getLeft()) {	//if x is the left child
				Website w=x.getParent().getRight(); //sets up uncle W
				
				//case 1 uncle is red
				if(w.getIsRed()) {
					w.setIsRed(false);	//uncle is black
					x.getParent().setIsRed(true);	//x's parent is red
					leftRotate(root,x.getParent());	//rotates parent to the left
					w=x.getParent().getRight();	//new uncle is the right node now, falls through to other cases
				}
				
				//if w.getRight is null, sets up a null node
				if(w.getRight()==null) {
					Website sentinel=new Website("null",0);
					sentinel.setIsRed(false);
					sentinel.setParent(w);
					w.setRight(sentinel);
				}
				//if w.getRight is null, sets up a null node
				if(w.getLeft()==null) {
					Website sentinel=new Website("null",0);
					sentinel.setIsRed(false);
					sentinel.setParent(w);
					w.setLeft(sentinel);
				}
				//case 2 w's children are black
				if(!w.getRight().getIsRed()&&!w.getLeft().getIsRed()) {
					RBClean(this.root);	//cleans out added null nodes becasue condition is already checked
					w.setIsRed(true);	//w is red
					x=x.getParent();	//x moves up to x's parent
				}
				
				
				else {
					RBClean(this.root); //removes added in nodes
					
					//case 3, right child is black, left is assumed red
					if(w.getRight()==null||!w.getRight().getIsRed()) {
				
					w.getLeft().setIsRed(false);	//set red left to black
					w.setIsRed(true);				//set w to red
					rightRotate(root,w);			//rotates the w so its left child is the parent
					w=x.getParent().getRight();		// reset's w to x's uncle
				}
					//case 4
				w.setIsRed(x.getParent().getIsRed());	//sets w to x's uncle's color,
				x.getParent().setIsRed(false);			//sets parent to black
				w.getRight().setIsRed(false);			//sets w's right to black
				leftRotate(root,x.getParent());			//rotates from parent to fix it
				x=root;	//breaks out of the loop
				}
			}
			else {//if x is right child
				Website w=x.getParent().getLeft(); //sets up uncle W
				
				//case 1 uncle is red 
				if(w.getIsRed()) {
					w.setIsRed(false);					//uncle is black
					x.getParent().setIsRed(true);		//x's parent is red
					rightRotate(root,x.getParent());	//rotates x's parent to the right
					w=x.getParent().getLeft();			//new uncle is the right node now, falls through to other cases
				}
				//if w.getRight is null, sets up a null node
				if(w.getRight()==null) {
					Website sentinel=new Website("null",0);
					sentinel.setIsRed(false);
					sentinel.setParent(w);
					w.setRight(sentinel);
				}
				//if w.getRight is null, sets up a null node
				if(w.getLeft()==null) {
					Website sentinel=new Website("null",0);
					sentinel.setIsRed(false);
					sentinel.setParent(w);
					w.setLeft(sentinel);
				}
				//case 2 if w's children are red
				if(!w.getRight().getIsRed()&&!w.getLeft().getIsRed()) {
					RBClean(this.root);		//cleans out null nodes
					w.setIsRed(true);		//uncle is red
					x=x.getParent();		//x is x's parent
				}
				
				else {
					//case 3, right is assumed red, left is black and not null
					if(w.getRight()==null||!w.getLeft().getIsRed()) {
					w.getRight().setIsRed(false); //sets uncle's right to false
					w.setIsRed(true);				//sets uncle to red
					leftRotate(root,w);				//rotates on uncle
					w=x.getParent().getLeft();		//resets w's color to x's uncle
				}
				w.setIsRed(x.getParent().getIsRed()); //sets w to x's uncle's color 
				x.getParent().setIsRed(false);			//sets x's parent to black
				w.getLeft()	.setIsRed(false);			//sets uncle's left to black
				rightRotate(root,x.getParent());		//rotates to fix coloring
				x=root;									//break statement
				
			}
		}
		}
		//sets x to red after finishing
		x.setIsRed(false);		
	}

	// Replaces Website U with Website V through pointer manipulation
	public void RBTransplant(Website root, Website u, Website v) {
		if (u.getParent() == null) { // If U is the root
			setRoot(v); // Root is now V
		} else if (u == u.getParent().getLeft()) {// If left child,
			u.getParent().setLeft(v); // parent takes V as new Child
		} else {
			u.getParent().setRight(v); // else U is a right Child
		}
		if(v!=null) {
		v.setParent(u.getParent()); // updates its parent pointer to u's old parent
		}
	}

	// Gets Minimum of subtree given root
	public Website treeMinimum(Website site) {
		Website localSite = site; // Local pointer to given Site
		while (localSite.getLeft() != null&&localSite.getLeft().getPageRank()!=0) { // Traversing Loop, stops at null node
			localSite = localSite.getLeft(); // incrementation Condition
		}
		return localSite; // Returns minimum
	}

	//Prints tree from pageRank 30 to PageRank 1 without any of the added null vectors
	public void RBPrint(Website root) {
	
		if (root != null&&root.getPageRank()!=0) {// continues until it reaches the bottom of the tree

			RBPrint(root.getLeft());// Prints Left subtree
			System.out.println(root);// Prints Root
			RBPrint(root.getRight()); // Prints Right subtree
		}
		
		else {
			root=null;
		}
	}
	
	//Removes added null nodes using recursive stepping
		public void RBClean(Website site) {
			//if there is not a null node, goes to left or right 
			if(site!=null&&site.getPageRank()!=0) {
				RBClean(site.getLeft());// Cleans Left subtree
				RBClean(site.getRight()); // Cleans Right subtree
			}
			
			//if there is a null node, cbeck if it is the right or left child
			else if(site!=null){
				if(site.getParent().getLeft()!=null&&site.getParent().getLeft()==site){
					site.getParent().setLeft(null);
				}
				else if(site.getParent().getRight()!=null&&site.getParent().getRight()==site){
				site.getParent().setRight(null);
			}
		}
		}

	// Getter for Root
	public Website getRoot() {
		return this.root;
	}

	// Setter for Root
	public void setRoot(Website site) {
		this.root = site;
	}
}
