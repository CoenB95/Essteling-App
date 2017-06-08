package nl.l15vdef.essteling;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by larsv on 8-6-2017.
 */

public class DatabaseConnector {
    private Connection con;
    private LocalDate date;
    private LocalTime time;

    public DatabaseConnector(String host , String database , String username , String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        this.con= DriverManager.getConnection(
                "jdbc:mysql://" + host + "/" + database ,username,password);
        System.out.println("Connection made!");

    }

    /**
     *
     * @param select
     * @param from
     * @param columns
     * @return ArrayList
     * @throws SQLException
     * select for the columns you want, from to choose the table, columns for the amount of columns.
     */
    public ArrayList<String> voerQueryUit(String select, String from, int columns){
        ArrayList<String> list = new ArrayList<>();
        String query = "";
        query = ("SELECT " + select + " FROM " + from);
        System.out.println(query);
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            String addString = "";
            while(rs.next()){
                for (int i = 1 ; i <= columns ; i++){
                    if(i == columns){
                        addString = (addString + rs.getString(i));
                    }
                    else {
                        addString = (addString + rs.getString(i) + "~");
                    }
                }
                list.add(addString);
                addString = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param select
     * @param from
     * @param where
     * @param columns
     * @return
     * @trows SQLException
     * "select" for the columns you want, "from" to choose the table, "where" to give a requirement, columns for the amount of columns.
     */
    public ArrayList<String> voerSelectedQueryUit(String select , String from, String where , int columns){
        ArrayList<String> list = new ArrayList<>();
        String query = "";
        query = ("SELECT " + select + " FROM " + from + " Where " + where);
        System.out.println(query);
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            String addString = "";
            while(rs.next()){
                for (int i = 1 ; i <= columns ; i++){
                    if(i == columns){
                        addString = (addString + rs.getString(i));
                    }
                    else {
                        addString = (addString + rs.getString(i) + "~");
                    }
                }
                list.add(addString);
                addString = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param select
     * @param from
     * @param order
     * @param columns
     * @return
     * "select" for the columns you want, "from" to choose the table, order to order the return data, columns for the amount of columns.
     */
    public ArrayList<String> voerOrderedQueryUit(String select, String from, String order, int columns){
        ArrayList<String> list = new ArrayList<>();
        String query = "";
        query = ("SELECT " + select + " FROM " + from + " Order by " + order);
        System.out.println(query);
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            String addString = "";
            while(rs.next()){
                for (int i = 1 ; i <= columns ; i++){
                    if(i == columns){
                        addString = (addString + rs.getString(i));
                    }
                    else {
                        addString = (addString + rs.getString(i) + "~");
                    }
                }
                list.add(addString);
                addString = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param select
     * @param from
     * @param where
     * @param order
     * @param columns
     * @return
     * "select" for the columns you want, "from" to choose the table, "where" to give a requirement, "order" to order the return data, "columns" for the amount of columns.
     */
    public ArrayList<String> voerSelectedOrderedQueryUit(String select, String from,String where, String order, int columns){
        ArrayList<String> list = new ArrayList<>();
        String query = "";
        query = ("SELECT " + select + " FROM " + from + " Where " + where + " Order by " + order);
        System.out.println(query);
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            String addString = "";
            while(rs.next()){
                for (int i = 1 ; i <= columns ; i++){
                    if(i == columns){
                        addString = (addString + rs.getString(i));
                    }
                    else {
                        addString = (addString + rs.getString(i) + "~");
                    }
                }
                list.add(addString);
                addString = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param table
     * @param name
     * @param score
     * @throws SQLException
     * "table" to select the target table, "name" to give someones name, "score" is the score.
     */

    public void voegDataLineToe(String table , String name, int score) throws SQLException {
        date = date.now();
        time = time.now();
        Statement stmt = con.createStatement();
        stmt.executeUpdate("INSERT INTO " + table + " (Naam, Score, Datum , Tijd)" +
                "VALUES (" + "\"" + name + "\" "+"," + "\"" + score +"\"" + "," + "\"" + date +"\"" + "," + "\"" + time +"\"" + ")");
        System.out.println("Data toegevoegd voor: " + name + " , met score: " + score + " , op datum en tijd: " + date + " " + time);
    }

    /**
     * close the database connection.
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        con.close();
        System.out.println("Connection Closed");
    }




}