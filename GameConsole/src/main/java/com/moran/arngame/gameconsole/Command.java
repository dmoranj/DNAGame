/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.gameconsole;


/**
 *
 * @author dmoranj
 */
public abstract class Command {

    protected GameConsole gc;
    protected String chain;

    public Command(GameConsole gc) {
        this.gc=gc;
    }

    public abstract String getPattern();
    public abstract String getSyntax();
    public abstract String getDescription();
    public abstract void execute(String[] parameters);

    public boolean match(String command) {
        if (command.matches(getPattern())) {

            String [] parameters=new String[0];

            if (command.indexOf(" ")> 0)
                parameters= command.substring(command.indexOf(" ")+1).split(" ");
            
            execute(parameters);
            return true;
        }
        
        return false;
    }
}
