package com.moran.arngame.proteinsynthesizerlib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dmoranj
 */
public class Protein implements Comparable {

    private String formula;
    private String owner;

    public Protein(String formula, String owner) {
        if (formula.length()%2!=0)
            throw new IllegalArgumentException("Protein's formula must allways be composed of pairs");
        
        this.formula = formula;
        this.owner = owner;
    }

    /**
     * @return the formula
     */
    public String getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof Protein)
            return ((Protein)o).formula.equals(this.formula);

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public int compareTo(Object t) {

        if (t instanceof Protein){
            return this.formula.length() - ((Protein)t).formula.length();
        } else {
            throw new IllegalArgumentException("Proteins can only be compared with proteins. Instead was found: " + t.getClass().getName());
        }
    }
}
