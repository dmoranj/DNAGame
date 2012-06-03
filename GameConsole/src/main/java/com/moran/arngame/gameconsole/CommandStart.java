/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.gameconsole;

import com.moran.arngame.proteinsynthesizerlib.GameManager;

/**
 *
 * @author dmoranj
 */
public class CommandStart extends Command {

    public CommandStart(GameConsole gc) {
        super(gc);
    }

    @Override
    public String getPattern() {
        return "start";
    }

    @Override
    public String getSyntax() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start the current game";
    }

    @Override
    public void execute(String[] parameters) {
        gc.getGm().startGame();
        gc.setStarted(true);
        System.out.println("The Game has Started");
    }

}
