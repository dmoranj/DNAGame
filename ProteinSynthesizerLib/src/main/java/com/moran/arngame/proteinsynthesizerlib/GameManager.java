/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib;

import com.moran.arngame.proteinsynthesizerlib.strategies.EndStrategy;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import java.util.ArrayList;
import java.util.List;

// TODO: Get rid of the newGame method and incorporate it in the constructor
/**
 *
 * @author dmoranj
 */
public class GameManager {

    private final static long DEFAULT_INITIAL_LENGTH=6;

    List<String> players=new ArrayList<String>();
    int currentPlayer;

    List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
    List<Protein> proteins = new ArrayList<Protein>();

    List<Ribosome> ribosomes;

    String owner;
    String winner;

    private long currentChainLength= DEFAULT_INITIAL_LENGTH;
    private final List<EndStrategy> strategies;

    enum GameState {STARTUP, STARTED, FINISHED};
    GameState currentState = GameState.STARTUP;

    public GameManager(List<Ribosome> ribosomes, List<EndStrategy> strategies) {
        this.ribosomes = ribosomes;
        this.strategies = strategies;
    }

    public GameManager(List<Ribosome> ribosomes, List<EndStrategy> strategies, long chainLength) {
        this(ribosomes, strategies);
        this.currentChainLength=chainLength;
    }

    public void newGame(String owner) {
        this.players.clear();
        this.players.add(owner);
        this.currentPlayer=0;
        this.owner=owner;
        this.winner=null;
    }

    public void joinGame(String player) {
        if (currentState!=GameState.STARTUP)
            throw new IllegalStateException("Players can only join a game in the Startup stage.");

        players.add(player);
    }

    public List<String> getPlayers() {
        return players;
    }

    public void startGame() {
        currentState=GameState.STARTED;
    }

    /**
     *
     * Executes the given chain of the selected player against all the ribosomes.
     * After the execution, check if the game has finished. If that's the case,
     * return the name of the winner.
     * 
     * @param player
     *      Name of the player who is issuing the chain.
     * 
     * @param chain
     *      The ARN chain of the player to execute.
     *
     * @return
     *      The name of the winner, or null if the game hasn't finished.
     */
    public String issueChain(String player, String chain) {

        if (currentState!= GameState.STARTED)
            throw new IllegalStateException("Chains may be issued only when the game is started and not finished.");
           
        if (!getCurrentPlayer().equals(player))
            throw new IllegalArgumentException("Only the current player may issue chains.");

        if (chain.length()!= currentChainLength)
            throw new IllegalArgumentException("Only chains with the current chain length can be accepted.");

        for (Ribosome rib: ribosomes)
            rib.processARN(player, chain, aminoacids, proteins);

        currentPlayer=(currentPlayer+1)%players.size();
        currentChainLength++;

        return evaluateStrategies();
    }

    private String evaluateStrategies() {
        for (EndStrategy st: strategies) {
            winner = st.checkWinner(aminoacids, proteins, players);

            if (winner!=null) {
                currentState=GameState.FINISHED;
                return winner;
            }
        }

        return null;
    }

    public long getCurrentChainLength() {
        return this.currentChainLength;
    }

    public String getCurrentPlayer() {
        if (players.isEmpty())
            return null;
        else
            return players.get(currentPlayer);
    }

    public List<Protein> getProteinList() {
        return proteins;
    }

    public List<Aminoacid> getAminoacids() {
        return aminoacids;
    }

    public boolean isFinished() {
        return currentState==GameState.FINISHED;
    }

    public List<Ribosome> getRibosomes() {
        return ribosomes;
    }
}
