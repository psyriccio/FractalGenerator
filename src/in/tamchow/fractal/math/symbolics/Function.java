package in.tamchow.fractal.math.symbolics;
import in.tamchow.fractal.helpers.annotations.NotNull;
import in.tamchow.fractal.helpers.strings.StringManipulator;
import in.tamchow.fractal.math.complex.Complex;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Holds a transcendental function chain
 */
public class Function implements Serializable, Comparable<Function> {
    private ArrayList<FunctionTerm> terms;
    private ArrayList<String> signs;
    private String[][] consts;
    private String z_value;
    private String variableCode, oldvariablecode;
    public Function(String variable, String variableCode, String oldvariablecode, @NotNull String[][] varconst) {
        setZ_value(variable);
        setConsts(varconst);
        setVariableCode(variableCode);
        setOldvariablecode(oldvariablecode);
        terms = new ArrayList<>();
        signs = new ArrayList<>();
    }
    public Function() {
        terms = new ArrayList<>();
        signs = new ArrayList<>();
    }
    public static boolean isSpecialFunction(@NotNull String function) {
        return FunctionTerm.isSpecialFunctionTerm(function);
    }
    @NotNull
    public static Function fromString(@NotNull String function, String variableCode, String oldvariablecode, String[][] consts) {
        @NotNull Function poly = new Function(null, variableCode, oldvariablecode, consts);
        @NotNull String[] tokens = StringManipulator.split(function, "|");
        for (@NotNull String token : tokens) {
            if (token.equals("+") || token.equals("-")) {
                poly.signs.add(token.trim());
            } else {
                poly.terms.add(FunctionTerm.fromString(token.trim(), variableCode, consts, oldvariablecode));
            }
        }
        if (poly.signs.size() == poly.terms.size() - 1 || (!poly.signs.get(0).equals("-"))) {
            poly.signs.add(0, "+");
        }
        return poly;
    }
    public String getOldvariablecode() {
        return oldvariablecode;
    }
    public void setOldvariablecode(String oldvariablecode) {
        this.oldvariablecode = oldvariablecode;
    }
    public String getZ_value() {
        return z_value;
    }
    public void setZ_value(String z_value) {
        this.z_value = z_value;
    }
    public String[][] getConsts() {
        return consts;
    }
    public void setConsts(@NotNull String[][] constdec) {
        consts = new String[constdec.length][constdec[0].length];
        for (int i = 0; i < this.consts.length; i++) {
            System.arraycopy(constdec[i], 0, consts[i], 0, consts[i].length);
        }
    }
    public String getVariableCode() {
        return variableCode;
    }
    public void setVariableCode(String variableCode) {
        this.variableCode = variableCode;
    }
    public ArrayList<String> getSigns() {
        return signs;
    }
    public void setSigns(@NotNull ArrayList<String> signs) {
        this.signs.clear();
        this.signs.addAll(signs);
    }
    public ArrayList<FunctionTerm> getTerms() {
        return terms;
    }
    public void setTerms(@NotNull ArrayList<FunctionTerm> terms) {
        this.terms.clear();
        this.terms.addAll(terms);
    }
    @NotNull
    public String derivative(int order) {
        @NotNull String deriv = "";
        switch (order) {
            case 1:
                for (int i = 0, j = 0; i < terms.size() && j < signs.size(); i++, j++) {
                    deriv += " " + signs.get(j) + " " + terms.get(i).derivative(1);
                }
                if (deriv.trim().charAt(0) == '+') {
                    return deriv.trim().substring(1, deriv.trim().length());
                }
                break;
            case 2:
                for (int i = 0, j = 0; i < terms.size() && j < signs.size(); i++, j++) {
                    deriv += " " + signs.get(j) + " " + terms.get(i).derivative(2);
                }
                if (deriv.trim().charAt(0) == '+') {
                    return deriv.trim().substring(1, deriv.trim().length());
                }
                break;
            default:
                throw new IllegalArgumentException("Only 1st and 2nd order derivatives are supported");
        }
        return deriv;
    }
    @NotNull
    public Complex getDegree() {
        @NotNull Complex degree = new Complex(Complex.ZERO);
        for (@NotNull FunctionTerm term : terms) {
            @NotNull Complex vardeg = term.getDegree();
            if (vardeg.modulus() > degree.modulus()) {
                degree = new Complex(vardeg);
            }
        }
        return degree;
    }
    @NotNull
    @Override
    public String toString() {
        @NotNull String function = "";
        for (int i = 0, j = 0; i < terms.size() && j < signs.size(); i++, j++) {
            function += " " + signs.get(j) + " " + terms.get(i);
        }
        if (function.trim().charAt(0) == '+') {
            return function.trim().substring(1, function.trim().length());
        }
        return function.trim();
    }
    @Override
    public int compareTo(@NotNull Function o) {
        return toString().compareTo(o.toString());
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof Function && toString().equals(o.toString());
    }
}