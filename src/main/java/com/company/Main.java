package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {

    public static void main(String[] args) {
        try {
            // import vendors from file
            List<Vendors> vendors = importVendors();

            // loop through vendors list so each fighter has their chance at the round robin
            for (int y = 0; y < vendors.size(); y++) {
                // fighter 1 will fight all other fighters
                Vendors fighter1 = vendors.get(y);
                // load list of other vendors, cannot include fighter1, they can't fight themselves
                List<Vendors> opponents = loadOpponents(vendors, fighter1.getVendor());
                // have fighter1 fight every other fighter
                for (int x = 0; x < opponents.size(); x++) {
                    System.out.println(fighter1.getVendor() + " fights: " + opponents.get(x).getVendor());
                    doFight(fighter1, vendors.get(x));
                }
            }
            // determine winner
            printWinLose(vendors);
            determineWinner(vendors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void determineWinner(List<Vendors> vendors) {
        int highestNumberOfWins = 0;
        StringBuilder winner = new StringBuilder();
        for (Vendors v : vendors) {
            if (v.getWins() > highestNumberOfWins) {
                winner = new StringBuilder(v.getVendor());
                highestNumberOfWins = v.getWins();
            } else if (v.getWins() == highestNumberOfWins) {
                winner.append(", ").append(v.getVendor());
            }
        }
        System.out.println(winner.append(" wins with ").append(highestNumberOfWins));
    }

    private static void printWinLose(List<Vendors> vendors) {
        for (Vendors v : vendors) {
            System.out.println("_______________________");
            System.out.println(v.getVendor() + ": " );
            System.out.println("Wins: " + v.getWins());
            System.out.println("Loses: " + v.getLoses());
            System.out.println();
        }
    }

    private static List<Vendors> loadOpponents(List<Vendors> vendors, String fighter1) {
        List<Vendors> opponents = new ArrayList<>();
        for (int x = 0; x < vendors.size(); x++) {
            if (!vendors.get(x).getVendor().equalsIgnoreCase(fighter1)) {
                opponents.add(vendors.get(x));
            }
        }
        return opponents;
    }

    private static void doFight(Vendors fighter1, Vendors fighter2) {
        boolean winnerFound = false;
        // keeps track of turns and tracks who goes first
        boolean turnFlag = determineInitiative(fighter1, fighter2);
        boolean hit = false;
        boolean crit = false;
        int damage = 0;
        int roundCount = 1;

        // used to restore fighters to their original health after their fight
        int tempHealthFighter1 = fighter1.getHealth();
        int tempHealthFighter2 = fighter2.getHealth();

        printFightContestants(fighter1, fighter2);
        while (!winnerFound) {
            if (turnFlag) {
                hit = determineDodge(fighter2);
                if (hit) {
                    crit = determineCrit(fighter1);
                    if (crit) {
                        damage = fighter1.getDamage() * 2;
                        critMessage(fighter1, fighter2);
                    } else {
                        damage = fighter1.getDamage();
                        hitMessage(fighter1, fighter2);
                    }
                    reduceHealth(fighter2, damage);
                    winnerFound = determineIfWinnerFound(fighter2);
                } else {
                    missMessage(fighter1, fighter2);
                }
            } else {
                hit = determineDodge(fighter1);
                if (hit) {
                    crit = determineCrit(fighter2);
                    if (crit) {
                        damage = fighter2.getDamage() * 2;
                        critMessage(fighter2, fighter1);
                    } else {
                        damage = fighter2.getDamage();
                        hitMessage(fighter2, fighter1);
                    }
                    reduceHealth(fighter1, damage);
                    winnerFound = determineIfWinnerFound(fighter1);
                } else {
                    missMessage(fighter2, fighter1);
                }
            }
            turnFlag = !turnFlag;
            roundCount++;
        }
        allocateWinLose(fighter1);
        fighter1.setHealth(tempHealthFighter1);
        fighter2.setHealth(tempHealthFighter2);

    }

    private static void allocateWinLose(Vendors fighter1) {
        if (fighter1.getHealth() <= 0) {
            fighter1.setLoses(fighter1.getLoses() + 1);
            System.out.println("Fighter 1 loses!");
        } else {
            fighter1.setWins(fighter1.getWins() + 1);
            System.out.println("Fighter 1 wins!");
        }
    }

    private static boolean determineIfWinnerFound(Vendors fighter) {
        if (fighter.getHealth() <= 0) {
            return true;
        }
        return false;
    }

    private static void missMessage(Vendors fighter1, Vendors fighter2) {
        System.out.println(fighter1.getVendor() + " missed when attacking " + fighter2.getVendor());
    }

    private static void hitMessage(Vendors fighter1, Vendors fighter2) {
        int damage = fighter1.getDamage();
        System.out.println(fighter1.getVendor() + " hit " + fighter2.getVendor() + " with an attack for " + damage);
    }

    private static void reduceHealth(Vendors fighter1, int damage) {
        fighter1.setHealth(fighter1.getHealth() - damage);
    }

    private static void critMessage(Vendors fighter1, Vendors fighter2) {
        int critDamage = fighter1.getDamage() * 2;
        System.out.println(fighter1.getVendor() + " hit " + fighter2.getVendor() + " with a critical attack for " + critDamage);
    }

    private static boolean determineCrit(Vendors fighter) {
        int critChance = getRandomNumber();
        if (critChance <= fighter.getCritical()) {
            return true;
        }
        return false;
    }

    private static boolean determineDodge(Vendors fighter) {
        int dodgeChance = getRandomNumber();
        if (dodgeChance >= fighter.getDodge()) {
            return true;
        }
        return false;
    }

    private static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(100);
    }

    private static boolean determineInitiative(Vendors fighter1, Vendors fighter2) {
        int initiativeOne = getRandomNumber() + fighter1.getInitiative();
        int initiativeTwo = getRandomNumber() + fighter2.getInitiative();
        if (initiativeOne > initiativeTwo) {
            System.out.println("Fighter 1 is randomly selected to go first (" + initiativeOne + ">" + initiativeTwo + ")");
            return true;
        } else if (initiativeTwo > initiativeOne) {
            System.out.println("Fighter 2 is randomly selected to go first (" + initiativeTwo + ">" + initiativeOne + ")");
            return false;
        } else {
            System.out.println("Fighter 1 is randomly selected to go first (" + initiativeOne + ">" + initiativeTwo + ")");
            return true;
        }
    }

    private static void printFightContestants(Vendors fighter1, Vendors fighter2) {
        System.out.println("Fighter 1");
        System.out.println(fighter1.toString());
        System.out.println();
        System.out.println("Fighter 2");
        System.out.println(fighter2);
        System.out.println();
    }

    public static List<Vendors> importVendors() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FighterInfo pp1 = mapper.readValue(readFile(), FighterInfo.class);
        return pp1.getVendors();
    }

    private static String readFile() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/main/resources/vendor_fighters.json")));
        return json;
    }
}
