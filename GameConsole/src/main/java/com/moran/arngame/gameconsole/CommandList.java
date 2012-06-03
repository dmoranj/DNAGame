/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.gameconsole;

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.players.GamePlayer;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;

/**
 *
 * @author dmoranj
 */
public class CommandList extends Command {

    public CommandList(GameConsole gc) {
        super(gc);
    }

    @Override
    public String getPattern() {
        return "list\\s+\\w*";
    }

    @Override
    public String getSyntax() {
        return "list players|proteins|aminoacids|ribosomes";
    }

    @Override
    public String getDescription() {
        return "List the desired set.";
    }

    @Override
    public void execute(String[] parameters) {

        if ("players".equals(parameters[0])) {
            System.out.println("Players:");
            System.out.println("-----------------------------------------------------------------");

            for (GamePlayer gp: gc.getPlayers().values())
                System.out.println(gp.getId() + ": " + gp.getType());
            
            System.out.println("-----------------------------------------------------------------");
        } else if ("proteins".equals(parameters[0])) {
            System.out.println("Proteins:");
            System.out.println("-----------------------------------------------------------------");

            for (Protein prot: gc.getGm().getProteinList())
                System.out.println(prot.getFormula() + " -> " + prot.getOwner());

            System.out.println("-----------------------------------------------------------------");

        } else if ("aminoacids".equals(parameters[0])) {
            System.out.println("Aminoacids:");
            System.out.println("-----------------------------------------------------------------");

            for (Aminoacid am: gc.getGm().getAminoacids())
                System.out.println(am);

            System.out.println("-----------------------------------------------------------------");
        } else if ("ribosomes".equals(parameters[0])) {
            System.out.println("Ribosomes:");
            System.out.println("-----------------------------------------------------------------");

            for (Ribosome rb: gc.getGm().getRibosomes()) {
                System.out.println(rb.getPattern());
                System.out.println(rb.getDescription() + "\n");
            }

            System.out.println("-----------------------------------------------------------------");
        } else {
            System.out.println("Selected category not found");
        }
    }

}
