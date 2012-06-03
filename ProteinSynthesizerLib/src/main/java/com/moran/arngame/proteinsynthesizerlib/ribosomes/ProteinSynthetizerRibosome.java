/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib.ribosomes;

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dmoranj
 */
public class ProteinSynthetizerRibosome extends Ribosome{

    public final static String PATTERN_SYNTETHIZE_PROTEIN ="TA([CATG][CATG]G)+AT";

    public String getPattern() {
        return "TA(XXG)+AT";
    }

    public String getDescription() {
        return "Combines any number of aminoacids into a new protein.";
    }

    @Override
    public List<String> processARN(String owner, String arnChain, List<Aminoacid> aminoacids, List<Protein> proteins) {
        Pattern p= Pattern.compile(PATTERN_SYNTETHIZE_PROTEIN);

        Matcher m = p.matcher(arnChain);
        boolean end;
        int init=0;

        arnLoop: while(end = m.find(init)) {
            String group = m.group().substring(2, m.group().length()-2);
            init = m.start()+1;

            StringBuffer sb = new StringBuffer();

            if (group.length()%3!=0)
                throw new IllegalArgumentException("Bad ARN sequence in protein creation: " + m.group());

            aminoacidLoop: for (int i=0; i < group.length(); i+=3) {
                String aminoacid= group.charAt(i) + "" + group.charAt(i+1);

                if (!aminoacids.contains(new Aminoacid(aminoacid)))
                    continue arnLoop;
            }

            for (int i=0; i < group.length(); i+=3) {
                String aminoacid= group.charAt(i) + "" + group.charAt(i+1);

                aminoacids.remove(new Aminoacid(aminoacid));
                sb.append(aminoacid);
            }

            Protein prot = new Protein(sb.toString(), owner);
            proteins.add(prot);
        }
        
        return null;
    }
}
