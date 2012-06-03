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
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinSynthetizerRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 *
 * @author dmoranj
 */
public class TestGameManagerCorrect {

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

    @Test
    public void shouldRegisterOwnerPlayer() {
        // Given
        //------------------------------------------------


        // When
        //------------------------------------------------
        gm.newGame(OWNER);

        // Then
        //------------------------------------------------
        boolean found=false;
        for (String owned: gm.getPlayers())
            if (owned.equals(OWNER)) {
                found=true;
                break;
            }

        assertTrue(found);
    }


    @Test
    public void ownerPlayerShouldStart() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);

        // When
        //------------------------------------------------
        String currentPlayer=gm.getCurrentPlayer();

        // Then
        //------------------------------------------------
        assertEquals(OWNER, currentPlayer);
    }

    @Test
    public void shouldInsertNewPlayer() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);

        // When
        //------------------------------------------------
        gm.joinGame(PLAYER1);

        // Then
        //------------------------------------------------
                boolean found=false;
        for (String owned: gm.getPlayers())
            if (owned.equals(PLAYER1)) {
                found=true;
                break;
            }

        assertTrue(found);
    }


    @Test
    public void shouldChangePlayerWhenAChainIsIssued() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);
        gm.startGame();

        // When
        //------------------------------------------------
        gm.issueChain(OWNER, "ACTAGA");

        // Then
        //------------------------------------------------
        assertEquals(PLAYER1, gm.getCurrentPlayer());
    }

    @Test
    public void arnSizeShouldChangeWhenAChainIsIssued() {
        // Given
        //------------------------------------------------
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);
        gm.startGame();

        // When
        //------------------------------------------------
        long initialChainSize=gm.getCurrentChainLength();
        gm.issueChain(OWNER, "ACTAGA");
        long resultingChainSize=gm.getCurrentChainLength();

        // Then
        //------------------------------------------------
        assertTrue(initialChainSize+1==resultingChainSize);
    }

    @Test
    public void allRibosomesShouldBeExecutedAgainstTheARNChain() {
        // Given
        //------------------------------------------------
        gm = new GameManager(ribs, strategies, 13);
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);
        gm.startGame();

        // When
        //------------------------------------------------
        gm.issueChain(OWNER, "CTTAAGTATAGAT");

        // Then
        //------------------------------------------------
        assertTrue(gm.getAminoacids().isEmpty());
        assertTrue(gm.getProteinList().size()==1);
        assertEquals("TA", gm.getProteinList().get(0).getFormula());
    }

    @Test
    public void shouldEvaluateEndStrategies() {
        // Given
        //------------------------------------------------
        gm = new GameManager(ribs, strategies, 12);
        gm.newGame(OWNER);
        gm.joinGame(PLAYER1);
        gm.startGame();

        // When and test
        //------------------------------------------------
        String result;
        result=gm.issueChain(OWNER, "AAAAAAAAAAAA");
        assertNull(result);
        result=gm.issueChain(PLAYER1, "CTTAAGTATAGAT");
        assertNull(result);
        result=gm.issueChain(OWNER, "AAAAAAAAAAAAAA");
        assertNull(result);
        result=gm.issueChain(PLAYER1, "CTTAAGTATAGATAA");
        assertNull(result);
        result=gm.issueChain(OWNER, "AAAAAAAAAAAAAAAA");
        assertEquals(PLAYER1, result);
    }
}
