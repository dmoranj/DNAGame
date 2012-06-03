/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib.strategies;

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.GameManager;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import java.util.List;

/**
 *
 * @author dmoranj
 */
public interface EndStrategy {

    /**
     * Checks whether there is a winner or not in a given game.
     * 
     * @return
     *
     *      The String id of the winner if there is one. Null otherwise.
     */
    public String checkWinner(List<Aminoacid> aminoacids, List<Protein> proteins, List<String> players);
}
