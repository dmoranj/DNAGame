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
public interface GamePlayer {

    String getId();
    void joinGame(GameManager gm);
    String issueChain(long length) throws Exception;
    String getType();
    
}
