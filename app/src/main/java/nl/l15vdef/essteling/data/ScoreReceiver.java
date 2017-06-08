package nl.l15vdef.essteling.data;


import org.threeten.bp.LocalDate;

import java.sql.SQLException;
import java.util.ArrayList;

import nl.l15vdef.essteling.DatabaseConnector;

/**
 * Created by larsv on 8-6-2017.
 */

public class ScoreReceiver {
    private String attractieNaam;
    private LocalDate datum;
    private String host;
    private String dataBase;
    private String gebNaam;
    private String password;

    public ScoreReceiver(String attractieNaam){
        this.attractieNaam = attractieNaam;
        datum = LocalDate.now();
        this.host = "84.105.109.27";
        this.dataBase = "EsstelingApp";
        this.gebNaam = "PGB3";
        this.password = "B3Essteling";
    }
    public void geefDatum(){
        System.out.println(datum);
        System.out.println(datum.minusDays(7));
    }

    public ArrayList<String> geefHuidigeDatumScores() throws SQLException, ClassNotFoundException {
        DatabaseConnector con = new DatabaseConnector(host , dataBase , gebNaam , password);
        ArrayList<String> scores = con.voerSelectedOrderedQueryUit("*" , attractieNaam , "Datum='" + datum + "'" , "score DESC" , 5 );
        con.closeConnection();
        return scores;
    }

    public ArrayList<String> geefWeekScores() throws SQLException , ClassNotFoundException{
        DatabaseConnector con = new DatabaseConnector(host , dataBase , gebNaam , password);
        ArrayList<String> scores = con.voerSelectedOrderedQueryUit("*" , attractieNaam , "Datum >='" + datum.minusDays(7) + "'" , "score DESC" , 5);
        con.closeConnection();
        return scores;
    }

    public ArrayList<String> geefMaandScores() throws SQLException , ClassNotFoundException{
        DatabaseConnector con = new DatabaseConnector(host , dataBase , gebNaam , password);
        ArrayList<String> scores = con.voerSelectedOrderedQueryUit("*" , attractieNaam , "Datum >='" + datum.minusMonths(1) + "'" , "score DESC" , 5);
        con.closeConnection();
        return scores;
    }

    public ArrayList<String> geefAllTimeScores() throws SQLException , ClassNotFoundException{
        DatabaseConnector con = new DatabaseConnector(host , dataBase , gebNaam , password);
        ArrayList<String> scores = con.voerOrderedQueryUit("*" , attractieNaam , "score DESC" , 5);
        con.closeConnection();
        return scores;
    }

}

/**
 MyTask as = new MyTask();
 as.execute();

 private class MyTask extends AsyncTask<String, Void, Void> {
 private static final String TAG = "AsyncTask";

 @Override
 protected Void doInBackground(String... params) {
 Log.i(TAG , "Do in background");
 ScoreReceiver rec = new ScoreReceiver("Araconda");
 ArrayList<String> scores = new ArrayList<String>();
 try {
 scores = rec.geefHuidigeDatumScores();
 } catch (SQLException e) {
 e.printStackTrace();
 } catch (ClassNotFoundException e) {
 e.printStackTrace();
 }
 System.out.println(scores);


 return null;
 }
 }
 */
