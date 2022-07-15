package sample;


import java.io.*;
import java.util.ArrayList;


/**
 * The Class Database.
 */
public class Database implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID=42L;
    
    /** The max level. */
    private int maxLevel;

    /** The database files. */
    private ArrayList<DataTable> databaseFiles;

    /**
     * Instantiates a new database.
     */
    public Database() {
        this.maxLevel = 1;
        databaseFiles = new ArrayList<DataTable>();
    }


    /**
     * Gets the max level.
     *
     * @return the max level
     */
    public int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Sets the max level.
     *
     * @param maxLevel the new max level
     */
    public void setMaxLevel(int maxLevel) {
        if (this.maxLevel < maxLevel) this.maxLevel = maxLevel;
    }

    /**
     * Adds the data.
     *
     * @param d the d
     */
    public void addData(DataTable d) {
        databaseFiles.add(d);
    }

    /**
     * Removes the data.
     *
     * @param d the d
     */
    public void removeData(DataTable d) {
        if (databaseFiles.contains(d)) databaseFiles.remove(d);
    }

    /**
     * Delete all progress.
     */

    /**
     * Gets the database files.
     *
     * @return the database files
     */
    public ArrayList<DataTable> getDatabaseFiles() {
        return databaseFiles;
    }
}

