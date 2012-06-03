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
 * Finish the game when a player has controlled the bigges protein for a certain
 * number of turns.
 *
 */
public class SimpleEndStrategy implements EndStrategy {

    int turnNumbers;

    String currentWinner=null;
    int winnedTurns=0;

    public SimpleEndStrategy(int turnNumbers) {
        this.turnNumbers=turnNumbers;
    }

    public String checkWinner(List<Aminoacid> aminoacids, List<Protein> proteins, List<String> players) {

        Protein winnerProtein=null;

        for (Protein prot: proteins) {
            if (winnerProtein==null||winnerProtein.getFormula().length() < prot.getFormula().length())
                winnerProtein=prot;
        }

        if (winnerProtein==null)
            return null;

        if (winnerProtein.getOwner().equals(currentWinner)) {
            winnedTurns++;

            if (winnedTurns==turnNumbers)
                return currentWinner;
        } else {
            winnedTurns=1;
            currentWinner= winnerProtein.getOwner();
        }

        return null;
    }

}
