/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.gameconsole;

import com.moran.arngame.droolsplayer.DroolsGamePlayer;
import com.moran.arngame.proteinsynthesizerlib.players.HumanPlayer;
import com.moran.arngame.proteinsynthesizerlib.players.RandomPlayer;

/**
 *
 * @author dmoranj
 */
public class CommandJoin extends Command {

    public CommandJoin(GameConsole gc) {
        super(gc);
    }

    @Override
    public String getPattern() {
        return "join\\s+\\w*\\s+\\w*";
    }

    @Override
    public String getSyntax() {
        return "join <player_name> <player_type>";
    }

    @Override
    public String getDescription() {
        return "Adds a new player to the current game of the selected type: human, random, drooler";
    }

    @Override
    public void execute(String[] parameters) {

        gc.getGm().joinGame(parameters[0]);

        if ("human".equals(parameters [1]))
            gc.getPlayers().put(parameters[0], new HumanPlayer(parameters[0], gc.getGm()));
        else if ("random".equals(parameters [1]))
            gc.getPlayers().put(parameters[0], new RandomPlayer(parameters[0], gc.getGm()));
        else if ("drooler".equals(parameters [1]))
            gc.getPlayers().put(parameters[0], new DroolsGamePlayer(parameters[0], gc.getGm()));
        
        System.out.println(parameters[0] + " the " + parameters [1] + " has joined the game.");
    }

}
