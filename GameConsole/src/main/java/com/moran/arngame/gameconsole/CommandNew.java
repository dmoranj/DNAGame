/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.gameconsole;

import com.moran.arngame.proteinsynthesizerlib.GameManager;
import com.moran.arngame.proteinsynthesizerlib.players.HumanPlayer;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.AminoacidCreationRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinDisolverRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinSynthetizerRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinUnifierRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import com.moran.arngame.proteinsynthesizerlib.strategies.EndStrategy;
import com.moran.arngame.proteinsynthesizerlib.strategies.SimpleEndStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dmoranj
 */
public class CommandNew extends Command {

    public CommandNew(GameConsole gc) {
        super(gc);
    }

    @Override
    public String getPattern() {
        return "new\\s+\\w*";
    }

    @Override
    public String getSyntax() {
        return "new <owner_name>";
    }

    @Override
    public String getDescription() {
        return "Creates a new game with the given owner and resets all the info.";
    }

    @Override
    public void execute(String [] parameters) {
        List<Ribosome> ribs = new ArrayList<Ribosome>();

        ribs.add(new AminoacidCreationRibosome());
        ribs.add(new ProteinSynthetizerRibosome());
        ribs.add(new ProteinDisolverRibosome());
        ribs.add(new ProteinUnifierRibosome());
        
        List<EndStrategy> strategies = new ArrayList<EndStrategy>();
        strategies.add(new SimpleEndStrategy(4));

        GameManager gm =new GameManager(ribs, strategies);
        gm.newGame(parameters[0]);

        gc.getPlayers().clear();
        gc.getPlayers().put(parameters[0], new HumanPlayer(parameters[0], gm));
        gc.setGm(gm);
    }

}
