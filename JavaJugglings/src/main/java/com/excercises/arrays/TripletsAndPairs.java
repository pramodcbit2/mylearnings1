package com.excercises.arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TripletsAndPairs {
    public static void main(String[] args) {
        int[] input = {2, 3, 4, 5, 8, 1, 6};
        int x = 9;
        producetripletsAndPairs(input, x);
    }

    private static void producetripletsAndPairs(int[] input, int x) {
        //map to store the sums of individual pairs by sum
        HashMap<Integer, String> sums = new HashMap<>();
        findPairs(input, x, sums);
        findTriplets(input, x, sums);
    }

    /**
     * Take an input array and an integer and produce unique pairs whose sum is given integer
     * @param input
     * @param x
     * @param sums
     */
    private static void findPairs(int[] input, int x, HashMap<Integer, String> sums) {
        //loop over each element of input array to find the other number with which the sum is X
        for (int i = 0; i < input.length; i++) {
            int on = input[i];
            for (int j = 0; j < input.length; j++) {
                int sum = on + input[j];
                //if this sum is not present in sum map add it with combination
                if (!sums.containsKey(sum)) {
                    //remove pairs of same element which will not be a valid combination
                    if (input[i] != input[j])
                        sums.put(sum, " " + input[i] + "," + input[j] + "");
                } else if (isNotDuplicatePair(sums.get(sum), input[i], input[j])) {
                    //add new unique combination for this sum to existing combination
                    sums.put(sum, sums.get(sum) + " " + input[i] + "," + input[j]);
                }
            }
        }
        //Now we have all sums and their unique combinations in sums map, fetch the pairs for given sum
        System.out.println("pairs are " + sums.get(x));
    }

    /**
     * Take a Pair and a combination to check whether they are not same and also not of repetition of numbers like 1,1 or 2,2
     * @param s
     * @param i
     * @param i1
     * @return
     */
    private static boolean isNotDuplicatePair(String s, int i, int i1) {
        return !s.contains("" + i1 + "," + i) && i != i1;
    }

    /**
     * Take an input array and an integer and produce unique triplets whose sum is given integer
     * @param input
     * @param x
     * @param sums
     */
    private static void findTriplets(int[] input, int x, HashMap<Integer, String> sums) {
        //create a placeholder for triplets to remove duplicates
        List<String> triplets = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            int on = input[i];
            //temporary sum which we need to find pairs whose sum with on is given x
            int tsum = x - on;
            //fetch pairs of these sum if avaialble
            String tvalue = sums.containsKey(tsum) ? sums.get(tsum) : "";
            if (!"".equals(tvalue)) {
                //split the pairs and check each combination of pair and on whether its a match triplet
                String[] pairs = tvalue.split(" ");
                pairLoop:
                for (String pair : pairs) {
                    //remove combination of repetion of numbers
                    if (!"".equals(pair) && !pair.contains(on + "")) {
                        //skip further processing if this valid combination is duplicate
                        if (isNotDuplicateTriplet(triplets, on, pair))
                            continue pairLoop;
                        String triplet = on + "," + pair;
                        triplets.add(triplet);
                        System.out.println("triplets are " + triplet);
                    }
                }
            }
        }
    }

    /**
     * Take list of triplets and a combination of pair and number
     *  @param triplets
     * @param on
     * @param pair
     * @return
     */
    private static boolean isNotDuplicateTriplet(List<String> triplets, int on, String pair) {
        for (String t : triplets) {
            if (t.contains(on + "")) {
                String dt = t.replaceAll(",", "");
                dt = dt.replace(on + "", "");
                if (pair.replace(",", "").equals(dt)) {
                    return true;
                }
            }
        }
        return false;
    }
}
