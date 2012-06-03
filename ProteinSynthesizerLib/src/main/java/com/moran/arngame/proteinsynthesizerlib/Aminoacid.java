/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.moran.arngame.proteinsynthesizerlib;

/**
 *
 * @author dmoranj
 */
public class Aminoacid {

    private String formula;

    public Aminoacid(String formula) {
        this.formula=formula;
    }

    /**
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    @Override
    public String toString() {
        return formula;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Aminoacid) {
            return this.formula.equals(((Aminoacid)o).formula);
        } else if (o instanceof String) {
            return this.formula.equals(o);
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return formula.hashCode();
    }
}
