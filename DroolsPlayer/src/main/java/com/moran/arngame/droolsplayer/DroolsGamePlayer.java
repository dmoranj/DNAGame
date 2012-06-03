/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moran.arngame.droolsplayer;

import com.moran.arngame.proteinsynthesizerlib.Aminoacid;
import com.moran.arngame.proteinsynthesizerlib.GameManager;
import com.moran.arngame.proteinsynthesizerlib.Protein;
import com.moran.arngame.proteinsynthesizerlib.players.GamePlayer;
import com.moran.arngame.proteinsynthesizerlib.ribosomes.Ribosome;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 *
 * @author dmoranj
 */
public class DroolsGamePlayer implements GamePlayer {

    private final String id;
    private GameManager gm;
    private final KnowledgeBase kbase;
    private StatefulKnowledgeSession session;

    public DroolsGamePlayer(String id, GameManager gm) {
        this.id = id;
        this.gm=gm;

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        kbuilder.add(ResourceFactory.newClassPathResource("ProteinKnowledge.drl", getClass()), ResourceType.DRL);

        if (kbuilder.hasErrors()) {
            System.err.println(kbuilder.getErrors().toString());
        }

        kbase= KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void joinGame(GameManager gm) {
        this.gm = gm;
    }

    @Override
    public String issueChain(long length) throws Exception {
        session = kbase.newStatefulKnowledgeSession();

        populateFacts();

        return computeAnswer(length);
    }

    @Override
    public String getType() {
        return "drooler";
    }

    private void populateFacts() {

        for (Ribosome rib: gm.getRibosomes())
            session.insert(rib);

        for (Protein prot: gm.getProteinList()) {
            session.insert(prot);
        }

        for (Aminoacid am: gm.getAminoacids()) {
            session.insert(am);
        }

        session.insert(gm);
        session.insert(this);
    }

    private String computeAnswer(long length) {
        ComputationResult cr = new ComputationResult();

        session.setGlobal("cr", cr);
        
        session.fireAllRules();

        if (cr.getChain()!=null)
            return cr.getChain();
        else
            return ComputationResult.fill(length);
    }
}
