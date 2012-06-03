/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moran.arngame.droolsplayer;

import com.moran.arngame.proteinsynthesizerlib.ribosomes.ProteinDisolverRibosome;

/**
 *
 * @author dmoranj
 */
public class ComputationResult {
            private String chain;
        private boolean closed=false;

        public static String fill(long length) {
            StringBuffer chain= new StringBuffer();

            for (int i=0; i < length; i++) {
                int base = (int) Math.round(Math.random()*4);

                if (base==0) {
                    chain.append("C");
                } else if (base==1) {
                    chain.append("T");
                } else if (base==2) {
                    chain.append("A");
                } else {
                    chain.append("G");
                }
            }

            return chain.toString();
        }

        public static String complement(String chain) {

            StringBuffer sb = new StringBuffer();
            for (int i=0; i < chain.length(); i++)
                sb.append(ProteinDisolverRibosome.opposites.get(chain.charAt(i)));

            return sb.toString();
        }

        /**
         * @return the chain
         */
        public String getChain() {
            return chain;
        }

        /**
         * @param chain the chain to set
         */
        public void setChain(String chain) {
            this.chain = chain;
            setClosed(true);
        }

        /**
         * @return the closed
         */
        public boolean isClosed() {
            return closed;
        }

        /**
         * @param closed the closed to set
         */
        public void setClosed(boolean closed) {
            this.closed = closed;
        }
}
