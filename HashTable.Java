import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class HashTable {

	ArrayList<String>[] HashArray = new ArrayList[2000];
	int arraySize;


	public static void main(String[] args) throws IOException {




		// adds all the words to a list
		ArrayList<String> text = new ArrayList<String>();
    	BufferedReader read = null;
    	try {
        	read = new BufferedReader(new FileReader("words.txt"));
        	String currentline = null;
        	while ((currentline = read.readLine()) != null) {
            	text.add(currentline); }
    		}
    	// Makes sure that the reader is closed even if there is some kind of exception
    	finally {
        read.close();
    }
    	// converts the list to an array.
    	String[] wordArray = text.toArray(new String[0]);

	/*	int n = 0;
    	while (n < wordArray.length ) {
			System.out.println(wordArray[n]);
			n ++;
		}

	 */


    	HashTable function = new HashTable();

		function.hashFunction(wordArray, function.HashArray);



	}


	public void hashFunction(String[] words, ArrayList<String>[] hashArray) {
		int collisionCount = 0;

		for (int n = 0; n < words.length; n++) {

			String newWord = words[n];
			int len = words.length;
			int sum = 0;
			for (int i = 0; i < newWord.length(); i++) {
				sum += ((int) newWord.charAt(i)) * (1 << i);
			}

			int index = (sum % len) % 1999;

			if (index > 1999){
			    System.out.println("INDEX ABOVE 1999");
			    System.exit(0);
			}
			
			if (hashArray[index] != null) {
				System.out.println("Collision while adding word " + newWord + " at index " + index + " adding to a list at that index");
			    hashArray[index].add(newWord);
			    System.out.println(hashArray[index]);
			    collisionCount ++;
			    System.out.println(collisionCount);


			}
			else{
				System.out.println("Modulus Value= " + index + " for word " + newWord);
			    hashArray[index] = new ArrayList<String>();
			    hashArray[index].add(newWord);
			}

		}
	}

}
