package dicegame;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DiceGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] player1Score = new int[7];
        int[] player2Score = new int[7];
        boolean[] categoriesSelected1 = new boolean[7];
        boolean[] categoriesSelected2 = new boolean[7];
        String playerOne = "One";
        String playerTwo = "Two";

        System.out.print("Play game (1) or Exit game (0)> ");
        int input = scanner.nextInt();
        if (input == 0) {
            return;
        } else {
            // Playing 7 rounds with alternating players
            for (int round = 1; round <= 7; round++) {

                System.out.println();

                // Player 1's turn
                displayScoreboard(player1Score, player2Score);
                System.out.println();

                playRound(playerOne, scanner, round, player1Score, categoriesSelected1);

                displayScoreboard(player1Score, player2Score);

                // Player 2's turn
                playRound(playerTwo, scanner, round, player2Score, categoriesSelected2);

            }

            int player1TotalScore = calculateTotalScore(player1Score);
            int player2TotalScore = calculateTotalScore(player2Score);
            displayWinner(player1TotalScore, player2TotalScore);
        }
    }

    private static void playRound(String player, Scanner scanner, int round, int[] playerScore, boolean[] categoriesSelected) {

        int totalTurn = 3;  // In each turn the players are given 3 chances
        int numOfDice = 5; //This variable will be used to keep track of number of dice thrown in each turn
        int score = 0;     //Stores the score in that round
        int occurence = 0; //The total number of category occured in that round

        System.out.println("Player " + player + ", it's your turn in round " + round);
        System.out.println();
        System.out.print("Enter (t) to make a throw, (f) to forfeit: ");
        String ans = scanner.next();

        if (ans.equals("f")) {
            return;

        } else if (ans.equals("t")) {  //If t then it would be the first turn
            game(totalTurn, playerScore, categoriesSelected, score, occurence, player, scanner, ans, numOfDice);
        } else {
            System.out.println("Invalid input");
        }
    }

    //Game method is a breakdown of the playRound method for further clarity
    private static void game(int totalTurn, int[] playerScore, boolean[] categoriesSelected, int score, int occurence, String player, Scanner scanner, String ans, int numOfDice) {
        int[] dice = rollDice(numOfDice);
        System.out.println("You rolled: " + Arrays.toString(dice));

        System.out.print("Enter (s) to select a category or (d) to defer: ");

        String ans2 = scanner.next();

        if ((ans2.equals("d")) || (ans2.equals("s"))) {

            if (ans2.equals("d")) {
                System.out.println("You deferred your category selection to the next round");
                dice = rollDice(numOfDice);
                System.out.println("You rolled: " + Arrays.toString(dice));
                totalTurn -= 1;
            }

            int category = chooseCategory(scanner, categoriesSelected);

            // if the category is 0 then it would be a sequence
            if (category == 0) {
                score = score + calculateScore(dice, category);
                return;
            }

            for (int i = 0; i < totalTurn; i++) {
                int turnLeft = totalTurn - i;
                if (i == 0) {
                    occurence = findOccurrences(category, dice);
                    numOfDice = numOfDice - occurence;

                    score = score + calculateScore(dice, category);

                } else {
                    System.out.println("Player " + player + " is left with " + turnLeft + " turns");
                    System.out.print("Enter (t) to make a throw or (f) to forfeit: ");
                    ans = scanner.next();

                    if (ans.equals("t")) {
                        dice = rollDice(numOfDice);
                        System.out.println("You rolled: " + Arrays.toString(dice));

                        occurence = findOccurrences(category, dice);
                        numOfDice = numOfDice - occurence;

                        score = score + calculateScore(dice, category);

                    } else if (ans.equals("f")) {
                        return;
                    } else {
                        System.out.println("Invalid input");
                    }
                }
            }
            playerScore[category] = score; // this is used to update the category with total final score
            categoriesSelected[category] = true;// this will keep track of the category to not be repeated
            System.out.println("Total score is " + score + " in this round with category " + category);
        } else {
            System.out.println("Invalid input");
        }
    }

    // Provides the number of int that is repeated in the given array
    private static int findOccurrences(int number, int[] array) {

        // Filter dice based on the selected category
        int[] setAsideDice = Arrays.stream(array).filter(d -> d == number).toArray();

        // Display the set-aside dice
        System.out.println("You set aside: " + Arrays.toString(setAsideDice) + "\n");

        return setAsideDice.length;
    }

    // stores the dice results in an array
    private static int[] rollDice(int n) {
        Random random = new Random();
        int[] dice = new int[n];
        for (int i = 0; i < n; i++) {
            dice[i] = random.nextInt(6) + 1;
        }
        return dice;
    }

    // asks user to choose among 6 categories and adds the oen that is chosen and provides the category
    private static int chooseCategory(Scanner scanner, boolean[] categoriesSelected) {
        int category;
        do {
            System.out.print("Choose an available category (1-6) or sequence (0): ");
            category = scanner.nextInt();
            if (category < 0 || category > 6) {
                System.out.println(
                        "Invalid category! Please choose a category between 1 and 6 or the sequence category 0.");
            } else if (category > 0 && categoriesSelected[category]) {
                System.out.println("Category " + category + " is already taken. Please choose another category.");
            }
        } while (category < 0 || category > 6 || (category > 0 && categoriesSelected[category]));
        return category;
    }

    // totals the scores of the players
    private static int calculateScore(int[] dice, int category) {
        if (category >= 1 && category <= 6) {
            int count = (int) Arrays.stream(dice).filter(d -> d == category).count();
            return count * category;
        } else {
            // Check for a sequence (1-2-3-4-5 or 2-3-4-5-6)
            Arrays.sort(dice);
            if (Arrays.equals(dice, new int[]{1, 2, 3, 4, 5}) || Arrays.equals(dice, new int[]{2, 3, 4, 5, 6})) {
                return 20;
            }
            return 0; // If it's not a sequence, the score is 0.
        }
    }

    private static void displayScoreboard(int[] player1Score, int[] player2Score) {
        System.out.println("+-----------+-----------+-----------+");
        System.out.println("| Category  | Player 1  | Player 2  |");
        System.out.println("+-----------+-----------+-----------+");

        for (int category = 1; category <= 6; category++) {
            System.out.printf("|   %-7d |   %-7d |   %-7d |%n", category, player1Score[category],
                    player2Score[category]);
            System.out.println("+-----------+-----------+-----------+");
        }

        System.out.printf("| Sequence  |   %-7d |   %-7d |%n", player1Score[0], player2Score[0]);
        System.out.println("+-----------+-----------+-----------+");

        System.out.printf("| TOTAL     |   %-7d |   %-7d |%n", calculateTotalScore(player1Score),
                calculateTotalScore(player2Score));
        System.out.println("+-----------+-----------+-----------+");
    }

    // detemines the scores and gives appropriate results
    private static int calculateTotalScore(int[] playerScore) {
        int total = 0;
        for (int i = 0; i < playerScore.length; i++) {
            total += playerScore[i];
        }
        return total;
    }

    /* after the end of round 7 shows who wins between two player and shows the
       winner*/
    private static void displayWinner(int player1TotalScore, int player2TotalScore) {
        if (player1TotalScore > player2TotalScore) {
            System.out.println("Player 1 wins with a total score of " + player1TotalScore);
        } else if (player2TotalScore > player1TotalScore) {
            System.out.println("Player 2 wins with a total score of " + player2TotalScore);
        } else {
            System.out.println("It's a tie! Both players have a total score of " + player1TotalScore);
        }
    }

}
