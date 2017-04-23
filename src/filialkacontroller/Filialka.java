/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filialkacontroller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Podzimek Vojtěch
 */
public class Filialka {

    /**
     * constanta wait time for request
     */
    private final int TIMEOUT = 2000;
    private int TIMERTIME = 3000;
    /**
     * Filialka name
     */
    private String name;
    /**
     * Filialka adress
     */
    private String ipv4Adress;
    /**
     * is fililka avaible?
     */
    private boolean isAvaible = false;
    /**
     * Timer for auto refresh
     */
    private Timer timer;
    /**
     *
     */
    private boolean autoTestRefresh = false;

    /**
     *
     * @param name filialka name
     * @param ipv4Adress filialka adress
     */
    public Filialka(String name, String ipv4Adress) {
        this.name = name;
        this.ipv4Adress = ipv4Adress;
        timer = new Timer();
    }

    /**
     * Turn off or turn on autorefresh frekvence setTIMERTIME true start timer
     * schedule false cancle all schedule
     *
     * @param autoRefresh
     */
    public void setAutoRefresh(boolean autoRefresh) {
        if (autoRefresh) {
            startTimer();
        } else {
            stopTimer();
        }
    }

    /**
     * test Avaible filialka result is in isAvaible
     * return bool if method was completed
     * 
     */
    public boolean refreshAvaible() {
        try {
            InetAddress server = InetAddress.getByName(ipv4Adress);
            isAvaible = server.isReachable(TIMEOUT);
            return true;

        } catch (UnknownHostException ex) {
            Logger.getLogger(Filialka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Filialka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    ;
    /**
     * Start timer schedule with testAvaible task frekvence of schedule is
     * TIMERTIME
     */
    private void startTimer() {
        autoTestRefresh = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshAvaible();
            }
        }, TIMERTIME);
    }

    /**
     * stop all schedule of timer autoTestRefresh = false
     */
    private void stopTimer() {
        timer.cancel();
        autoTestRefresh = false;
    }

    /**
     * GETERSS SETTERS *
     */
    public boolean getIsAvaible() {
        return isAvaible;
    }

    public String getName() {
        return name;
    }

    public String getIpv4Adress() {
        return ipv4Adress;
    }

    public int getTIMERTIME() {
        return TIMERTIME;
    }

    public void setTIMERTIME(int TIMERTIME) {
        this.TIMERTIME = TIMERTIME;
    }

}
