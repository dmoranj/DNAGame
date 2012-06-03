package com.moran.arngame.proteinsynthesizerlib;

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.AminoacidCreationRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinUnifierRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinSynthetizerRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinDisolverRibosome;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import java.util.Set;
import java.util.HashSet;
import org.junit.Before;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static junit.framework.Assert.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dmoranj
 */
public class TestPositiveMatches {

    private static final String OWNER1="OWNER1";
    private static final String OWNER2="OWNER2";

    Ribosome rib;

    @Before
    public void setup() {
    }

    @Test
    public void shouldCreateAminoacid() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new AminoacidCreationRibosome();

        String arnChain="CTTAAG";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(1==aminoacids.size());
        assertEquals(new Aminoacid("TA"), aminoacids.get(0));
    }

    @Test
    public void overlappingPatternsShouldCreateMultipleAminoacids() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new AminoacidCreationRibosome();

        String arnChain="CTCTAGAG";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(2==aminoacids.size());

        Set<String> aSet = new HashSet<String>();
        for(Aminoacid am: aminoacids)
            aSet.add(am.getFormula());

        assertTrue(aSet.contains("CT")&&aSet.contains("AG"));
    }

    @Test
    public void shouldCreateSimpleProtein() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinSynthetizerRibosome();

        aminoacids.add(new Aminoacid("GA"));

        String arnChain="TAGAGAT";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(1==protein.size());
        assertEquals(OWNER1, protein.get(0).getOwner());
        assertEquals("GA", protein.get(0).getFormula());
        assertTrue(0==aminoacids.size());
    }

    @Test
    public void shouldCreateComplexProtein() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinSynthetizerRibosome();

        aminoacids.add(new Aminoacid("GG"));
        aminoacids.add(new Aminoacid("TT"));
        aminoacids.add(new Aminoacid("AA"));

        String arnChain="TATTGAAGGGGAT";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(1==protein.size());
        assertEquals(OWNER1, protein.get(0).getOwner());
        assertEquals("TTAAGG", protein.get(0).getFormula());
        assertTrue(0==aminoacids.size());
    }

    @Test
    public void shouldCombineTwoSimpleProteinsWithFullMatching() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinUnifierRibosome();

        Protein p1 = new Protein("AT", OWNER1);
        Protein p2 = new Protein("GC", OWNER1);

        protein.add(p1);
        protein.add(p2);
        
        String arnChain="TGATGGGCCA";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(1==protein.size());
        assertEquals(OWNER1, protein.get(0).getOwner());
        assertEquals("ATGC", protein.get(0).getFormula());
    }

    @Test
    public void shouldCombineComplexProteins() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinUnifierRibosome();

        Protein p1 = new Protein("ATTA", OWNER1);
        Protein p2 = new Protein("GCCG", OWNER1);

        protein.add(p1);
        protein.add(p2);

        String arnChain="TGATTAGGGCCGCA";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(1==protein.size());
        assertEquals(OWNER1, protein.get(0).getOwner());
        assertEquals("ATTAGCCG", protein.get(0).getFormula());
    }

    @Test
    public void combineShouldChangeOwnership() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> protein = new ArrayList<Protein>();
        rib = new ProteinUnifierRibosome();

        Protein p1 = new Protein("AT", OWNER2);
        Protein p2 = new Protein("GC", OWNER2);

        protein.add(p1);
        protein.add(p2);
        
        String arnChain="TGATGGGCCA";

        rib.processARN(OWNER1, arnChain, aminoacids, protein);

        assertTrue(1==protein.size());
        assertEquals(OWNER1, protein.get(0).getOwner());
    }


    @Test
    public void disolveShouldCreateTwoProteinsFromOne() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> proteins = new ArrayList<Protein>();
        rib = new ProteinDisolverRibosome();

        Protein p1 = new Protein("ATGCTCGT", OWNER1);

        proteins.add(p1);

        String arnChain="GAAGCC";

        rib.processARN(OWNER1, arnChain, aminoacids, proteins);

        assertTrue(2==proteins.size());

        int found=0;
        for (Protein prot: proteins) {
            assertEquals(OWNER1, prot.getOwner());

            if (prot.getFormula().equals("ATGC")||prot.getFormula().equals("GT"))
                found++;
        }

        assertTrue(found==2);
    }

    @Test
    public void disolveShouldEraseAtomicProteins() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> proteins = new ArrayList<Protein>();
        rib = new ProteinDisolverRibosome();

        Protein p1 = new Protein("TC", OWNER1);

        proteins.add(p1);

        String arnChain="GAAGCC";

        rib.processARN(OWNER1, arnChain, aminoacids, proteins);

        assertTrue(0==proteins.size());
    }

    @Test
    public void disolveShouldNotCreateEmptyProteins() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> proteins = new ArrayList<Protein>();
        rib = new ProteinDisolverRibosome();

        Protein p1 = new Protein("ATGCTC", OWNER1);

        proteins.add(p1);

        String arnChain="GAAGCC";

        rib.processARN(OWNER1, arnChain, aminoacids, proteins);

        assertTrue(1==proteins.size());
        assertEquals("ATGC", proteins.get(0).getFormula());
    }

    @Test
    public void disolveShouldChangeOwnerShip() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> proteins = new ArrayList<Protein>();
        rib = new ProteinDisolverRibosome();

        Protein p1 = new Protein("ATGCTCAT", OWNER2);

        proteins.add(p1);

        String arnChain="GAAGCC";

        rib.processARN(OWNER1, arnChain, aminoacids, proteins);

        assertTrue(2==proteins.size());

        for (Protein prot: proteins) {
            assertEquals(OWNER1, prot.getOwner());
        }
    }

    @Test
    public void disolveShouldDivideSmallerProteinsFirst() {
        List<Aminoacid> aminoacids = new ArrayList<Aminoacid>();
        List<Protein> proteins = new ArrayList<Protein>();
        rib = new ProteinDisolverRibosome();

        Protein p1 = new Protein("ATGCTCAT", OWNER2);
        Protein p2 = new Protein("TCGT", OWNER2);

        proteins.add(p1);
        proteins.add(p2);

        String arnChain="GAAGCC";

        rib.processARN(OWNER1, arnChain, aminoacids, proteins);

        assertTrue(2==proteins.size());

        int found=0;
        for (Protein prot: proteins) {
            if (prot.getFormula().equals("ATGCTCAT")||prot.getFormula().equals("GT"))
                    found++;
        }
    }


    @Test
    public void proteinCreationShouldReturnLogs() {
        // TODO: implement feature
    }

    @Test
    public void aminoacidCreationShouldReturnLogs() {
        // TODO: implement feature
    }

    @Test
    public void proteinCombinationShouldReturnLogs() {
        // TODO: implement feature
    }

    @Test
    public void proteinDisolutionShouldReturnLogs() {
        // TODO: implement feature
    }
}
