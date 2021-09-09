import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Hangman {
    static int missCount = 0;
    public static String setRandomWord(ArrayList<String> words) {
        return words.get((int)(Math.random() * words.size()));
    }

    public static char[] setHiddenWord(String randomWord) {
        char[] hiddenWord = new char[randomWord.length()];
        for (int i = 0; i < randomWord.length(); i++)
            hiddenWord[i] = '*';
        return hiddenWord;
    }

    public static char getUserInput(char[] hiddenWord) {
        Scanner input = new Scanner(System.in);
        System.out.print("(Guess) Enter a letter in word " );
        System.out.print(hiddenWord);
        System.out.print(" > ");
        String temp = input.nextLine();
        return temp.charAt(0);
    }

    public static void guessing(String randomWord, char[] hiddenWord) {
        char guess = getUserInput(hiddenWord);
        boolean incorrectGuess = true;
        for (int i = 0; i < randomWord.length(); i++) {
            if (randomWord.charAt(i) == guess) {
                if (hiddenWord[i] == guess) {
                    System.out.println(guess + " is already in the word");
                    break;
                }
                hiddenWord[i] = guess;
                incorrectGuess = false;
            }
            if (incorrectGuess && i == randomWord.length() - 1) {
                System.out.println(guess + " is not in the word");
                missCount++;
            }
        }
    }

    public static boolean hasWon(String randomWord,char[] hiddenWord) {
        return Arrays.equals(hiddenWord, randomWord.toCharArray());
    }

    public static char getAnswer(String randomWord, char[] hiddenWord) {
        char answer = ' ';
        if (hasWon(randomWord, hiddenWord)) {
            System.out.print("The word is " + randomWord + ". You missed " + missCount + "."
                    + "\n" + "Do you want to play again? Enter y or n> ");
            Scanner input = new Scanner(System.in);
            String tempAnswer = input.nextLine();
            answer = tempAnswer.charAt(0);
        }
        return answer;
    }

    public static boolean playAgain(String randomWord, char[] hiddenWord) {
        return getAnswer(randomWord, hiddenWord) == 'y';
    }

    public static void setMissCount(int newMissCount) {
        missCount = newMissCount;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<String> words = new ArrayList<>();

        java.io.File file = new java.io.File("/Users/wesleytorrez/Downloads/test_text.txt");

        try (Scanner input = new Scanner(file)) {
            while (input.hasNext()) {
                words.add(input.next());
            }
        }

        String randomWord = setRandomWord(words);
        char[] hiddenWords = setHiddenWord(randomWord);

       while (!hasWon(randomWord, hiddenWords)) {

           guessing(randomWord, hiddenWords);

           if (playAgain(randomWord, hiddenWords)) {
               randomWord = setRandomWord(words);
               hiddenWords = setHiddenWord(randomWord);
               setMissCount(0);
           }
       }
    }
}

