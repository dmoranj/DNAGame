package com.moran.arngame.proteinsynthesizerlib.ribosomes;


import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dmoranj
 */
public abstract class Ribosome {

    public final static Map<Character, Character> opposites = new HashMap<Character, Character>();

    static {
        opposites.put('C', 'G');
        opposites.put('G', 'C');
        opposites.put('A', 'T');
        opposites.put('T', 'A');
    }

    public abstract List<String> processARN(String owner, String arnChain, List<Aminoacid> aminoacids, List<Protein> proteins);

    public abstract String getPattern();
    public abstract String getDescription();
}
