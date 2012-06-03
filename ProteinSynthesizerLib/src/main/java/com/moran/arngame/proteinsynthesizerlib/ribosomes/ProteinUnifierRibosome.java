/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib.ribosomes;

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dmoranj
 */
public class ProteinUnifierRibosome extends Ribosome {

    public final static String PATTERN_COMBINE_PROTEINS="TG([CATG][CATG])*GG([CATG][CATG])*CA";

    public String getPattern() {
        return "TG<Protein1>GG<Protein2>CA";
    }

    public String getDescription() {
        return "Combines two proteins into a new one, controlled by the current player.";
    }

    @Override
    public List<String> processARN(String owner, String arnChain, List<Aminoacid> aminoacids, List<Protein> proteins) {
        Pattern p= Pattern.compile(PATTERN_COMBINE_PROTEINS);

        Matcher m = p.matcher(arnChain);
        boolean end;
        int init=0;

        arnLoop: while(end = m.find(init)) {
            String group = m.group().substring(2, m.group().length()-2);
            init = m.start()+1;

            if (!group.contains("GG")||group.indexOf("GG")<2||group.indexOf("GG")>group.length()-4||group.indexOf("GG")%2!=0)
                continue;

            Protein prot1= new Protein(group.substring(0, group.indexOf("GG")), owner);
            Protein prot2= new Protein(group.substring(group.indexOf("GG")+2, group.length()), owner);

            if (!proteins.contains(prot1)||!proteins.contains(prot2))
                continue;

            List<Protein> proteinsToRemove = new ArrayList<Protein>();

            for (Protein prot: proteins)
                if (prot.equals(prot1)) {
                    proteinsToRemove.add(prot);
                    break;
                }

            for (Protein prot: proteins)
                if (prot.equals(prot2)) {
                    proteinsToRemove.add(prot);
                    break;
                }

            for (Protein prot: proteinsToRemove)
                proteins.remove(prot);

            Protein prot = new Protein(prot1.getFormula() + prot2.getFormula(), owner);
            proteins.add(prot);
        }

        return null;
    }
}
