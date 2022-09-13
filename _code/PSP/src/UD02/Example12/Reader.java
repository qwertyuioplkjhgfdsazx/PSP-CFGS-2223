/*
 * Copyright (C) 2022 David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package UD02.Example12;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
public class Reader implements Callable<String> {

    @Override
    public String call() throws Exception {
        String readedText = "I like action movies";
        Thread.sleep(1000);
        return readedText;
    }

    public static void main(String[] args) {
        try {
            Reader reader = new Reader();
            ExecutorService executionService = Executors.newSingleThreadExecutor();
            Future<String> result = executionService.submit(reader);
            String text = result.get();
            if (result.isDone()) {
                System.out.println(text);
                System.out.println("Process finished");
            } else if (result.isCancelled()) {
                System.out.println("Process cancelled");
            }
            executionService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
