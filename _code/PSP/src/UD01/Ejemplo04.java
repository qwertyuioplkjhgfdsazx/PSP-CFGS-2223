package UD01;

import java.io.*;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|iesmre.com)
 */
public class Ejemplo03 {

    public static void main(String[] args) throws IOException {
        //create File Object where the example is located
        File folder = new File("/usr/bin");
        //process to execute
        ProcessBuilder pb = new ProcessBuilder("java", "--version");
        //move to that folder
        pb.directory(folder);
        System.out.format("Working folder: %s%n", pb.directory());
        //run the process
        Process p = pb.start();
        //get the output of the process
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))){
            while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
//        try {
//            InputStream is = p.getInputStream();
//            int c;
//            while ((c = is.read()) != -1) {
//                System.out.print((char) c);
//            }
//            is.close();           
//        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
