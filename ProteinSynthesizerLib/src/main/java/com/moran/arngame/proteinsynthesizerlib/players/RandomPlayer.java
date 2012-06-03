/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib.players;

import com.moran.arngame.proteinsynthesizerlib.GameManager;

/**
 *
 * @author dmoranj
 */
public class RandomPlayer implements GamePlayer {

    private final String id;
    private GameManager gm;

    public RandomPlayer(String id, GameManager gm) {
        this.id=id;
        this.gm=gm;
    }

    public String getId() {
        return id;
    }

    public void joinGame(GameManager gm) {
        this.gm=gm;
    }

    public String issueChain(long length) throws Exception {

        StringBuffer chain= new StringBuffer();

        for (int i=0; i < length; i++) {
            int base = (int) Math.round(Math.random()*4);

            if (base==0) {
                chain.append("C");
            } else if (base==1) {
                chain.append("T");
            } else if (base==2) {
                chain.append("A");
            } else {
                chain.append("G");
            }
        }

        return chain.toString();
    }

    public String getType() {
        return "random";
    }

}
