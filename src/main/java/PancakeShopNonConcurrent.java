import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PancakeShopNonConcurrent {
    private static final int MAX_PANCAKES_SHOPKEEPER = 12;
    private static final int MAX_PANCAKES_USER = 5;
    private static final int NUM_USERS = 3;
    private static final int SIMULATION_TIME_SECONDS = 60;

    private static int pancakesMade;
    private static int[] pancakesEaten = new int[NUM_USERS];

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private int timeElapsed = 0;

            @Override
            public void run() {
                int shopkeeperPancakes = generateRandomPancakes(MAX_PANCAKES_SHOPKEEPER);
                int[] userPancakes = new int[NUM_USERS];

                for (int i = 0; i < NUM_USERS; i++) {
                    userPancakes[i] = generateRandomPancakes(MAX_PANCAKES_USER);
                    pancakesEaten[i] += userPancakes[i];
                }

                int totalPancakesEaten = 0;
                for (int pancakes : userPancakes) {
                    totalPancakesEaten += pancakes;
                }

                pancakesMade += shopkeeperPancakes;

                System.out.println("Time: " + timeElapsed + "s - " + (timeElapsed + 30) + "s");
                System.out.println("Shopkeeper made " + shopkeeperPancakes + " pancakes.");
                System.out.println("Users ate " + totalPancakesEaten + " pancakes.");
                System.out.println("Shopkeeper " + (shopkeeperPancakes >= totalPancakesEaten ? "met" : "did not meet") + " the users' needs.");
                System.out.println("Wasted pancakes: " + (shopkeeperPancakes - totalPancakesEaten));
                System.out.println();

                timeElapsed += 30;
                if (timeElapsed >= SIMULATION_TIME_SECONDS) {
                    timer.cancel();
                    System.out.println("Simulation completed.");
                    System.out.println("Total pancakes made: " + pancakesMade);
                    for (int i = 0; i < NUM_USERS; i++) {
                        System.out.println("User " + (i + 1) + " ate " + pancakesEaten[i] + " pancakes.");
                    }
                }
            }
        }, 0, 30000);
    }

    private static int generateRandomPancakes(int max) {
        Random rand = new Random();
        return rand.nextInt(max + 1);
    }
}

