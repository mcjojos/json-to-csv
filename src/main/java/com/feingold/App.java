package com.feingold;

import com.feingold.json2csv.Challenge1;
import com.feingold.mergesort.Challenge2;

import java.io.IOException;

/**
 * Main application point
 *
 * @author karanikasg@gmail.com
 */
public class App {

    public static void main(String[] args) {

        System.out.println("Starting challenge 1");
        Challenge1 challenge1 = new Challenge1();

        try {
            challenge1.start();
            System.out.println("Exiting challenge 1");
        } catch (IOException e) {
            System.out.println("unable to run challenge 1");
        }

        System.out.println("Starting challenge 2");
        Challenge2 challenge2 = new Challenge2();
        challenge2.start();
        System.out.println("Exiting challenge 2");

    }
}
