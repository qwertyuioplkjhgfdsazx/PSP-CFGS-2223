package UD01;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es)
 */
public class Ejemplo02 {

    public static void main(String[] args) {
        String[] cmd = {"ls", "-l"};
        String line = "";
        ProcessBuilder pb = new ProcessBuilder(cmd);

        try {
            Process p = pb.start();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            System.out.println("Process output:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());

        }
    }
}