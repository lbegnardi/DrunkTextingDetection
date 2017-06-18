package begnardi.luca.thesis_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * The FileReadWrite class is a wrapping class used
 * to read from and write to file in CSV, using the Result class methods.
 * Can communicate events through ClientEventDispatcher and has
 * as only field the name of a file.
 */

public class FileManager {

    private String file;

    /**
     * Constructor
     *
     * @param file string containing the absolute path of the file.
     */
    public FileManager(String file) {
        this.file = file;
    }

    /**
     * @return a string containing the file name.
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file string containing the absolute path of the file.
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * This method takes a Sample object and writes it on the file as a CSV,
     * raising an IOException if it encounters any problem or a SuccessEvent otherwise.
     *
     * @param s the result to write on the file
     */
    public void fileWrite(Sample s) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
            out.write(s.toCSV());
            out.write("\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileWrite(String s) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
            out.write(s);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method reads line per line the file, saving everything in a string that will be returned.
     * Various Exceptions are raised if something goes wrong.
     */
    public ArrayList<String> fileRead() {
        ArrayList<String> resultList = new ArrayList<String>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String s;
            while((s = in.readLine()) != null) {
                resultList.add(s);
            }
            in.close();
        } catch (FileNotFoundException e) {
            //raised if no permission or no file found
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    //only used to generate the classifier file on the first run of the application
    public void fill(InputStream in) throws IOException {

        OutputStream out = new FileOutputStream(new File(file));

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}