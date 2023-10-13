import java.util.Random;

public class PancakeShopConcurrent {
    private static final int MAX_PANCAKES_SHOPKEEPER = 12;
    private static final int MAX_PANCAKES_USER = 5;
    private static final int NUM_USERS = 3;
    private static final int SIMULATION_TIME_SECONDS = 60;

    private static int pancakesMade;
    private static int[] pancakesEaten = new int[NUM_USERS];

    public static void main(String[] args) {
        Thread shopkeeperThread = new Thread(() -> {
            while (pancakesMade < SIMULATION_TIME_SECONDS) {
                int shopkeeperPancakes = generateRandomPancakes(MAX_PANCAKES_SHOPKEEPER);
                pancakesMade += shopkeeperPancakes;
                System.out.println("Shopkeeper made " + shopkeeperPancakes + " pancakes.");
            }
        });

        Thread[] userThreads = new Thread[NUM_USERS];
        for (int i = 0; i < NUM_USERS; i++) {
            final int userIndex = i;
            userThreads[i] = new Thread(() -> {
                while (pancakesEaten[userIndex] < SIMULATION_TIME_SECONDS) {
                    int userPancakes = generateRandomPancakes(MAX_PANCAKES_USER);
                    pancakesEaten[userIndex] += userPancakes;
                    System.out.println("User " + (userIndex + 1) + " ate " + userPancakes + " pancakes.");
                }
            });
        }

        shopkeeperThread.start();
        for (Thread userThread : userThreads) {
            userThread.start();
        }

        try {
            shopkeeperThread.join();
            for (Thread userThread : userThreads) {
                userThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Simulation completed.");
        System.out.println("Total pancakes made: " + pancakesMade);
        for (int i = 0; i < NUM_USERS; i++) {
            System.out.println("User " + (i + 1) + " ate " + pancakesEaten[i] + " pancakes.");
        }
    }

    private static int generateRandomPancakes(int max) {
        Random rand = new Random();
        return rand.nextInt(max + 1);
    }
}

