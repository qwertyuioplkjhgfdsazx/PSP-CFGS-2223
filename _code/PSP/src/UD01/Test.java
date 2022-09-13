/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UD01;

import java.io.*;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|iesmre.com)
 */
public class Test {

    public static void main(String[] args) throws IOException {
        //create File Object where the example is located
        File folder = new File(".\\bin");
        //process to execute
        ProcessBuilder pb = new ProcessBuilder("java", "example");
        //move to that folder
        pb.directory(folder);
        System.out.format("Working folder: %s%n", pb.directory());
        //run the process
        Process p = pb.start();
        //get the output of the process
        try {
            InputStream is = p.getInputStream();
            int c;
            while ((c = is.read()) != -1) {
                System.out.println((char) c);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
