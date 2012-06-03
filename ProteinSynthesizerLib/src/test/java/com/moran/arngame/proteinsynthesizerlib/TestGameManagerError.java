/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib;

import com.moran.arngame.proteinsynthesizerlib.GameManager;
import com.moran.arngame.proteinsynthesizerlib.strategies.SimpleEndStrategy;
import com.moran.arngame.proteinsynthesizerlib.strategies.EndStrategy;
import org.junit.Before;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.AminoacidCreationRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinDisolverRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinSynthetizerRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinUnifierRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 *
 * @author dmoranj
 */
public class TestGameManagerError {

    private final static String OWNER="Godzilla";
    private final static String PLAYER1="Gamera";

    GameManager gm;
    List<Ribosome> ribs;
    List<EndStrategy> strategies;

    @Before
    public void setup() {

        ribs = new ArrayList<Ribosome>();

        ribs.add(new AminoacidCreationRibosome());
        ribs.add(new ProteinSynthetizerRibosome());

        strategies = new ArrayList<EndStrategy>();
        strategies.add(new SimpleEndStrategy(4));

        gm = new GameManager(ribs, strategies);
    }

    @Test(expected=IllegalArgumentException.class)
    public void chainsGreaterThanTheCurrentLengthShouldBeRejected() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);
        gm.startGame();

        // When
        //------------------------------------------------
        gm.issueChain(OWNER, "ACTAGAAGT");

        // Then
        //------------------------------------------------
        assertEquals(PLAYER1, gm.getCurrentPlayer());
    }

    @Test(expected=IllegalArgumentException.class)
    public void chainsLesserThanTheCurrentLengthShouldBeRejected() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);
        gm.startGame();

        // When
        //------------------------------------------------
        gm.issueChain(OWNER, "ACT");

        // Then
        //------------------------------------------------
        assertEquals(PLAYER1, gm.getCurrentPlayer());
    }

    @Test(expected=IllegalStateException.class)
    public void aPlayerShouldntBeInsertedOnceTheGameHasStarted() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);
        gm.startGame();
        gm.joinGame(PLAYER1);

        // When
        //------------------------------------------------
        gm.issueChain(OWNER, "ACT");

        // Then
        //------------------------------------------------
        assertEquals(PLAYER1, gm.getCurrentPlayer());
    }

    @Test(expected=IllegalStateException.class)
    public void aChainMustNotBeInsertedBeforeStartingTheGame() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);

        // When
        //------------------------------------------------
        gm.issueChain(OWNER, "ACT");

        // Then
        //------------------------------------------------
        assertEquals(PLAYER1, gm.getCurrentPlayer());
    }

    @Test(expected=IllegalArgumentException.class)
    public void onlyChainsFromTheCurrentPlayerShouldBeAccepted() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);
        gm.startGame();

        // When
        //------------------------------------------------
        gm.issueChain(PLAYER1, "ACTTAC");

        // Then
        //------------------------------------------------
        assertEquals(PLAYER1, gm.getCurrentPlayer());
    }

    @Test(expected=IllegalStateException.class)
    public void chainsShouldntBeExecutedOnceTheGameHasFinished() {
        // Given
        //------------------------------------------------
        gm = new GameManager(ribs, strategies, 12);
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);
        gm.startGame();

        String result;
        result=gm.issueChain(OWNER, "AAAAAAAAAAAA");
        result=gm.issueChain(PLAYER1, "CTTAAGTATAGAT");
        result=gm.issueChain(OWNER, "AAAAAAAAAAAAAA");
        result=gm.issueChain(PLAYER1, "CTTAAGTATAGATAA");
        result=gm.issueChain(OWNER, "AAAAAAAAAAAAAAAA");

        // When and test
        //------------------------------------------------
        gm.issueChain(PLAYER1, "AAAAAAAAAAAAAAAAA");

        
    }

    public void getCurrentPlayerShouldFailWhenNoNewGameHasBeenCreated() {
        // TODO: implement test
    }

    public void gameShouldnBeStartedOnceItsAlreadyStarted() {
        // TODO: implement test
    }
}
