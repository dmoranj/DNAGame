/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib.players;

import com.moran.arngame.proteinsynthesizerlib.GameManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author dmoranj
 */
public class HumanPlayer implements GamePlayer {

    String id;
    GameManager currentGm;
    private String currentString;

    public HumanPlayer(String id, GameManager gm) {
        this.id=id;
        this.currentGm=gm;
    }

    public String getId() {
        return id;
    }

    public void joinGame(GameManager gm) {
        this.currentGm=gm;
    }

    public void setCurrentString(String str) {
        this.currentString=str;
    }

    public String issueChain(long length) throws IOException {
        return currentString;
    }

    public String getType() {
        return "human";
    }

}
