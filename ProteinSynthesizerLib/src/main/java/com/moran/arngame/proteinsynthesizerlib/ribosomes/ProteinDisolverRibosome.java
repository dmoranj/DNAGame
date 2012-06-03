/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib.ribosomes;

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dmoranj
 */
public class ProteinDisolverRibosome extends Ribosome {

    public final static String PATTERN_DISOLVE_PROTEIN ="GA[CATG][CATG]CC";

    public String getPattern() {
        return "GAXXCC";
    }

    public String getDescription() {
        return "Dissolve a protein that contains the complement to pair XX, removing this pair from the formula.";
    }

    @Override
    public List<String>  processARN(String owner, String arnChain, List<Aminoacid> aminoacids, List<Protein> proteins) {
        Pattern p= Pattern.compile(PATTERN_DISOLVE_PROTEIN);

        Matcher m = p.matcher(arnChain);
        boolean end;
        int init=0;

        Collections.sort(proteins);

        arnLoop: while(end = m.find(init)) {
            String group = m.group().substring(2, m.group().length()-2);
            init = m.start()+1;

            if (group==null||group.length()!=2)
                continue;

            String opposite= Ribosome.opposites.get(group.charAt(0)) + "" + Ribosome.opposites.get(group.charAt(1));

            List<Protein> proteinsToRemove=new ArrayList<Protein>();

            for (Protein prot: proteins) {

                if (prot.getFormula().contains(opposite)) {
                    proteinsToRemove.add(prot);

                    String group1=prot.getFormula().substring(0, prot.getFormula().indexOf(opposite));
                    String group2=prot.getFormula().substring(prot.getFormula().indexOf(opposite) + 2, prot.getFormula().length());

                    if (!group1.isEmpty())
                        proteins.add(new Protein(group1, owner));

                    if (!group2.isEmpty())
                        proteins.add(new Protein(group2, owner));

                    break;
                }
            }

            for (Protein prot: proteinsToRemove)
                proteins.remove(prot);
        }

        return null;
    }
}
