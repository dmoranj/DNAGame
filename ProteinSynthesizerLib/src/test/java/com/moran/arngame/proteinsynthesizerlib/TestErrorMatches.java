package com.moran.arngame.proteinsynthesizerlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinUnifierRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinSynthetizerRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import org.junit.Before;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 *
 * @author dmoranj
 */
public class TestErrorMatches {

    private static final String OWNER1="OWNER1";
    private static final String OWNER2="OWNER2";

    Ribosome rib;

    @Before
    public void setup() {
    }

    @Test
    public void proteinCreationShouldFailOnAminoacidAbsence() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinSynthetizerRibosome();

        aminoacids.add(new Aminoacid("GG"));
        aminoacids.add(new Aminoacid("AA"));

        String arnChain="TATTGAAGGGGAT";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(0==protein.size());
    }

    @Test
    public void proteinCreationShouldNotEraseAminoacidsWithTheSameName() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinSynthetizerRibosome();

        aminoacids.add(new Aminoacid("GG"));
        aminoacids.add(new Aminoacid("GG"));
        aminoacids.add(new Aminoacid("TT"));
        aminoacids.add(new Aminoacid("AA"));

        String arnChain="TATTGAAGGGGAT";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(1==aminoacids.size());
        assertEquals(new Aminoacid("GG"), aminoacids.get(0));
    }

    @Test
    public void combinationShouldFailIfProteinsAreNotPresent() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinUnifierRibosome();

        String arnChain="TGATGGGCCA";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(0==protein.size());
    }

    @Test
    public void combinationShouldFailOnPartialMatching() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinUnifierRibosome();

        Protein p1 = new Protein("ATTA", OWNER1);
        Protein p2 = new Protein("GCTC", OWNER1);

        protein.add(p1);
        protein.add(p2);

        String arnChain="TGATGGGCCA";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(2==protein.size());

        for (Protein pi: protein) {
            assertTrue(pi.getFormula().length()==4);
        }
    }

}
