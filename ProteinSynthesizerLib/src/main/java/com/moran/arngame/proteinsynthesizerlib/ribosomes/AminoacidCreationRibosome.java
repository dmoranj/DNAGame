/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib.ribosomes;

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dmoranj
 */
public class AminoacidCreationRibosome extends Ribosome {

    public final static String PATTERN_SYNTETHIZE_AMINOACID ="CT[CATG][CATG]AG";

    public String getPattern() {
        return "CTXXAG";
    }

    public String getDescription() {
        return "Create a new aminoacid with formula XX.";
    }

    @Override
    public List<String> processARN(String owner, String arnChain, List<Aminoacid> aminoacids, List<Protein> proteins) {
        Pattern p= Pattern.compile(PATTERN_SYNTETHIZE_AMINOACID);

        Matcher m = p.matcher(arnChain);
        boolean end;
        int init=0;

        while(end = m.find(init)) {
            String group = m.group();
            init = m.start()+1;
            aminoacids.add(new Aminoacid(group.substring(2, group.length()-2)));
        }

        return null;
    }
}
