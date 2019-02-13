package pa3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GoogleSearchSimulator {
	 //Insertion sorts given ArrayList by Score
    public static void insertionSortByScore(ArrayList<Website> sites) 
    { 
        for (int i=0; i<sites.size(); ++i) 
        { 
            Website key = sites.get(i); 
            int j = i-1; 
            
            while (j>=0 && sites.get(j).getPageScore() > key.getPageScore()) 
            { 
                sites.set(j+1,sites.get(j)); 
                j = j-1; 
            } 
            sites.set(j+1,key); 
        } 
    }
 // /**
	// tester for the program
	public static void main(String[] args) {

		ArrayList<Website> array = new ArrayList<Website>();// sets up array to be populated by websites
		Crawler crawler = new Crawler(); // initializes the crawler
		Scanner input = new Scanner(System.in);// initializes the scanner
		System.out.println("Please enter your search term"); // prompts user for search term
		String searchTerm = input.next();// takes in search term
		String[] urls = new String[29];// sets up an array to put urls in with size 30
		try { //catches possible exceptions
			urls = crawler.crawl(searchTerm); //populates the url array
		} catch (IOException e) {
			e.printStackTrace();
		}

		//for all urls, add them into the arraylist of websites after converting them to Websites
		for (int i = 0; i < urls.length; i++) {
			array.add(i, new Website(urls[i]));
		}
		int counter = array.size(); //initializes the counter with 
		int indexCounter = 1;//initializes index counter to 1
		
		insertionSortByScore(array); //sorts the array 
		for (Website site : array) { //for every Website
			site.setPageRank(counter); //assigns a PageRank to it. First element is smallest and so on
			counter--; //decrements the counter since the pagerank was used
			site.setIndex(indexCounter); // assigns the index to the website
			indexCounter++;//increments the indexCounter
		}


		RBTree tree = new RBTree();//initalizes the tree
		tree.buildRB(array); //builds it off of the array and randomizes it
		int sentinel = 1; //initializes sentinel value for switch
		while (sentinel != 0) { // if sentinel is not 0
			counter = array.size(); //corrects pagerank conter
			indexCounter = 1; //resets index counter to 1
			System.out.println(
					"\n Press 2 to do RB search \n Press 3 to Insert into RB Tree \n Press 4 to Delete \n Press 5 to print Tree \n Press 0 to exit");
			sentinel = input.nextInt(); // input reader
			switch (sentinel) { // switch controller
			case 2://shows search result
				sentinel = 2;
				System.out.println("Search check");
				int value2 = input.nextInt();
				System.out.println(tree.RBSearch(tree.getRoot(), value2));
				break;

			case 3: 
				sentinel = 3;
				System.out.println("Enter website name");
				Website newSite = new Website(input.next());//creates a new website off user input
				array.add(newSite); //adds that site to the array
				counter = array.size(); //recalculates arraysize for pagerank counter
				indexCounter = 1; //resets indexCounter
				insertionSortByScore(array); //sorts array to reRank and ReIndex array
				for (Website site : array) {//reRank and reIndexs the array
					site.setPageRank(counter);
					counter--;
					site.setIndex(indexCounter);
					indexCounter++;
				}
				tree.RBInsert(newSite);//inserts the website with updated index and pageRanks
				break;

			case 4:
				sentinel = 4;
				System.out.println("Enter the PageRank to delete");
				int value = input.nextInt();
				Website deleteSite = tree.RBSearch(tree.getRoot(), value); //findds the value from the tree
				if(deleteSite==null) {
					System.out.println("Not in Tree");
					break;
				}
				tree.RBDelete(tree.getRoot(), deleteSite); //does the delete
				tree.RBClean(tree.getRoot());
				array.remove(deleteSite); //deletes the value from the reindexing Array

				//reranks and reIndexes all websites
				counter = array.size();
				indexCounter=1;
				insertionSortByScore(array);
				for (Website site : array) {
					site.setPageRank(counter);
					counter--;
					site.setIndex(indexCounter);
					indexCounter++;
				}
				
				break;
			case 5:
				sentinel = 5;
				tree.RBPrint(tree.getRoot()); //prints the tree in PreOrder 
				break;
			}

		}
		input.close();

	}
	//*/
}