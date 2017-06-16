package nl.l15vdef.essteling.data;

import android.util.Log;

import java.sql.SQLException;

import nl.l15vdef.essteling.DatabaseConnector;

/**
 * Created by larsv on 8-6-2017.
 */

public class ScoreSender {
    private String attractieNaam;

    private String host;
    private String dataBase;
    private String gebNaam;
    private String password;

    public ScoreSender(String attractieNaam){
        this.attractieNaam = attractieNaam;

        this.host = "84.105.109.27";
        this.dataBase = "EsstelingApp";
        this.gebNaam = "PGB3";
        this.password = "B3Essteling";
    }

    public void voegScoreToe(String naam , int score) throws SQLException, ClassNotFoundException {
        DatabaseConnector con = new DatabaseConnector(host , dataBase , gebNaam , password);
        con.voegDataLineToe(attractieNaam , naam , score);
        Log.d("Data","Data toegevoegd");
        con.closeConnection();
    }
}
