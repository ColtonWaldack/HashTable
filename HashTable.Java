import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HashTable {
    static int arraySize;
    final ArrayList<String>[] HashArray = new ArrayList[2000];
    public static void main(String[] args) throws IOException {
        // adds all the words to a list
        ArrayList<String> text = new ArrayList<>();
        try (BufferedReader read = new BufferedReader(new FileReader("words.txt"))) {
            String currentline = null;
            while ((currentline = read.readLine()) != null) {
                text.add(currentline);
                arraySize++;
            }
        }
        // converts the list to an array.
        String[] wordArray = text.toArray(new String[0]);
        HashTable function = new HashTable();
        function.hashFunction(wordArray, function.HashArray);
        boolean next = true;
        while (next = true) {
            int reply = JOptionPane.showConfirmDialog(null, "Would you like to Search for a specific word?", "Word Search?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                String word = JOptionPane.showInputDialog("Please enter a word");
                function.findword(word.toLowerCase());
            } else {
                JOptionPane.showMessageDialog(null, "Thanks for using my program.");
                System.exit(0);
            }
        }
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
            if (index > 1999) {
                System.out.println("INDEX ABOVE 1999 This should never happen");
                System.exit(0);
            }
            if (hashArray[index] != null) {
                System.out.println("Collision while adding word " + newWord + " at index " + index + " adding to a list at that index");
                hashArray[index].add(newWord);
                collisionCount++;
            } else {
                System.out.println("Modulus Value= " + index + " for word " + newWord);
                hashArray[index] = new ArrayList<>();
                hashArray[index].add(newWord);
            }
        }
        double collisionpercentraw = 100f * collisionCount / arraySize;
        DecimalFormat df = new DecimalFormat("###.###");
        JOptionPane.showMessageDialog(null, "Under my formula to calculate hash values for the strings " + collisionCount + " of the " + arraySize + " entries had the same hash value as another. \n That means %" + df.format(collisionpercentraw) + " of strings shared a hash value with another. I mitigated this by creating lists at each shared index. \n That way the list which contains a word can easily be found, and then the word can be found in that list. \n About 37 of the 2000 lists are null which helps explain why there are collisions. ");
    }
    public void findword(String word) {
        int len = arraySize;
        int sum = 0;
        for (int i = 0; i < word.length(); i++) {
            sum += ((int) word.charAt(i)) * (1 << i);
        }
        int arrayIndexHash = (sum % len) % 1999;
        // Find the words original hash key
        if (HashArray[arrayIndexHash] != null) {
            if (HashArray[arrayIndexHash].contains(word)) {
                // Found the word so return it
                JOptionPane.showMessageDialog(null, " the word " + " ' " + word + " '" + " was found in a list at index " + arrayIndexHash + "\n the index of " + arrayIndexHash + " contains a list of " + HashArray[arrayIndexHash].size() + " words." + "\nthe word " + " ' " + word + " '" + " is found at the " + "list index of " + HashArray[arrayIndexHash].indexOf(word));
            } else {
                JOptionPane.showMessageDialog(null, " the word " + word + " was not found ");
            }
            // Check next Index
            ++arrayIndexHash;
            // Index back to 0
            arrayIndexHash %= arraySize;
        }
    }
}
