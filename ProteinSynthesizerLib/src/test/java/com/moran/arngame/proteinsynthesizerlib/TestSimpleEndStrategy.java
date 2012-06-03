/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib;

import com.moran.arngame.proteinsynthesizerlib.Protein;
import com.moran.arngame.proteinsynthesizerlib.GameManager;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.AminoacidCreationRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinSynthetizerRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import com.moran.arngame.proteinsynthesizerlib.strategies.EndStrategy;
import com.moran.arngame.proteinsynthesizerlib.strategies.SimpleEndStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 *
 * @author dmoranj
 */
public class TestSimpleEndStrategy {

    private static final String PLAYER_1="Godzilla";
    private static final String PLAYER_2="Gamera";

    GameManager gm;
    SimpleEndStrategy est;

    @Before
    public void setup() {
        List<Ribosome> ribs = new ArrayList<Ribosome>();

        ribs.add(new AminoacidCreationRibosome());
        ribs.add(new ProteinSynthetizerRibosome());

        List<EndStrategy> strategies = new ArrayList<EndStrategy>();
        est = new SimpleEndStrategy(3);
        strategies.add(est);

        gm = new GameManager(ribs, strategies);

        gm.getPlayers().add(PLAYER_1);
        gm.getPlayers().add(PLAYER_2);

        Protein p1= new Protein("TA", PLAYER_1);
        gm.getProteinList().add(p1);
        Protein p2= new Protein("GA", PLAYER_2);
        gm.getProteinList().add(p2);
        Protein p3= new Protein("GATATT", PLAYER_1);
        gm.getProteinList().add(p3);
        Protein p4= new Protein("TATA", PLAYER_2);
        gm.getProteinList().add(p4);
    }

    @Test
    public void shouldFinishWhenTurnsHasPassedWithTheMaximumProtein() {

        String result;
        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);

        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);

        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertEquals(PLAYER_1, result);
    }

    @Test
    public void shouldntFinishIfTheMaximumProteinChanges() {
        String result;
        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);

        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);

        Protein p4= new Protein("TATAATATAT", PLAYER_2);
        gm.getProteinList().add(p4);

        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);
    }

    @Test
    public void numberOfTurnsShouldResetWhenWinnerChanges() {
                String result;
        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);

        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);

        Protein p4= new Protein("TATAATATAT", PLAYER_2);
        gm.getProteinList().add(p4);

        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);

        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertNull(result);

        result= est.checkWinner(gm.getAminoacids(), gm.getProteinList(), gm.getPlayers());
        assertEquals(PLAYER_2, result);

    }

    public void shouldntResetCounterWhenWinningPlayerIssueABiggerChain() {
        // TODO: implement test
    }
}
