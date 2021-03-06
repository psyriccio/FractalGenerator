package in.tamchow.fractal.math.symbolics;
import in.tamchow.fractal.helpers.annotations.NotNull;
import in.tamchow.fractal.helpers.strings.StringManipulator;
import in.tamchow.fractal.math.complex.Complex;

import java.io.Serializable;
/**
 * Support for transcendental FUNCTION_DATA for derivative-requiring fractal modes
 */
public class FunctionTerm implements Serializable, Comparable<FunctionTerm> {
    private final FunctionTermData[] FUNCTION_DATA = {new FunctionTermData("sin", "$v * ( cos $ )", "( ( - ( sin $ ) ) * $v ) + ( $vv * cos $ )"),
            new FunctionTermData("cos", "( - ( sin $ ) ) * $v", "( ( - ( cos $ ) ) * $v ) + ( $vv * ( - ( sin $ ) ) )"),
            new FunctionTermData("log", " $v / $", "( ( - ( $v * $v ) ) / ( $ * $ ) ) + ( ( $vv * $v ) / $ )"),
            new FunctionTermData("exp", "$v * ( exp $ )", "exp $ * ( $v + $vv )"),
            new FunctionTermData("sinh", "$v * ( cosh $ )", "( $v * ( sinh $ ) ) + ( $vv * ( cosh $ ) )"),
            new FunctionTermData("cosh", "$v * ( sinh $ )", "( $v * ( cosh $ ) ) + ( $vv * ( sinh $ ) )"),
            new FunctionTermData("tan", "$v * ( ( sec $ ) ^ 2 )", "( ( sec $ ) ^ 2 ) * ( ( 2 * ( $v ^ 2 ) * tan $ ) + $vv )"),
            new FunctionTermData("tanh", "$v * ( ( sech $ ) ^ 2 )", "( ( sech $ ) ^ 2 ) * ( $vv - ( 2 * ( $v ^ 2 ) * tanh $ ) )"),
            new FunctionTermData("cot", "- ( $v * ( ( cosec $ ) ^ 2 ) )", "( ( cosec $ ) ^ 2 ) * ( ( 2 * ( $v ^ 2 ) * cot $ ) - $vv )"),
            new FunctionTermData("coth", "- ( $v * ( ( cosech $ ) ^ 2 ) )", "( ( cosech $ ) ^ 2 ) * ( ( 2 * ( $v ^ 2 ) * coth $ ) - $vv )"),
            new FunctionTermData("sec", "$v * ( ( sec $ ) * ( tan $ ) )", "$vv * tan $ * sec $ + ( $v  ^ 2 ) * ( ( sec $ ) ^ 3 ) + ( $v  ^ 2 ) * ( ( tan $ ) ^ 2 ) * ( sec $ ) - ( ( $v  ^ 2 ) * ( ( sec $ ) ^ 3 ) )"),
            new FunctionTermData("sech", "$v * ( - ( tanh $ ) * ( sech $ ) )", "( $v  ^ 2 ) * ( ( tan $ ) ^ 2 ) * ( sec $ ) - ( $vv * tan $ * sec $ )"),
            new FunctionTermData("cosec", "$v * ( - ( cosec $ ) * ( cot $ ) )", "( $v ^ 2 ) * ( ( cosec $ ) ^ 3 ) + ( $v ^ 2 ) * ( ( cot $ ) ^ 2 ) * ( cosec $ ) - ( $vv * cot $ * cosec $ )"),
            new FunctionTermData("cosech", "$v * ( - ( cosech $ ) * ( coth $ ) )", "( $v ^ 2 ) * ( ( cosech $ ) ^ 3 ) + ( $v ^ 2 ) * ( ( coth $ ) ^ 2 ) * ( cosech $ ) - ( $vv * coth $ * cosech $ )")};
    private String function, constant, variableCode, oldvariablecode, z_value;
    private Polynomial coefficient, argument;
    private String[][] consts;
    public FunctionTerm() {
    }
    public FunctionTerm(String variable, String variableCode, String oldvariablecode, @NotNull String[][] varconst) {
        setZ_value(variable);
        setConsts(varconst);
        setVariableCode(variableCode);
        setOldvariablecode(oldvariablecode);
    }
    @NotNull
    public static FunctionTerm fromString(@NotNull String function, String variableCode, @NotNull String[][] consts, String oldvariablecode) {
        @NotNull FunctionTerm f = new FunctionTerm(null, variableCode, oldvariablecode, consts);
        @NotNull String[] parts = StringManipulator.split(function, ";");
        f.coefficient = Polynomial.fromString(parts[0]);
        f.coefficient.setVariableCode(variableCode);
        f.coefficient.setOldvariablecode(oldvariablecode);
        f.coefficient.setConstdec(consts);
        if (parts.length == 4) {
            f.constant = parts[3];
        } else {
            f.constant = "0";
        }
        f.function = parts[1];
        f.argument = Polynomial.fromString(parts[2]);
        f.argument.setVariableCode(variableCode);
        f.argument.setOldvariablecode(oldvariablecode);
        f.argument.setConstdec(consts);
        return f;
    }
    static boolean isSpecialFunctionTerm(@NotNull String function) {
        return getUsedFunctionTermIndex(function) != -1;
    }
    private static int getUsedFunctionTermIndex(@NotNull String function) {
        for (int i = 0; i < new FunctionTerm().FUNCTION_DATA.length; i++) {
            if (function.contains(new FunctionTerm().FUNCTION_DATA[i].function)) {
                return i;
            }
        }
        return -1;
    }
    public String[][] getConsts() {
        return consts;
    }
    public void setConsts(@NotNull String[][] constdec) {
        this.consts = new String[constdec.length][constdec[0].length];
        for (int i = 0; i < this.consts.length; i++) {
            System.arraycopy(constdec[i], 0, this.consts[i], 0, this.consts[i].length);
        }
    }
    @NotNull
    @Override
    public String toString() {
        return coefficient + " * ( " + function.trim() + " ( " + argument + " ) ) + ( " + constant.trim() + " )";
    }
    @NotNull
    public String derivative(int order) {
        coefficient.setConstdec(consts);
        argument.setConstdec(consts);
        @NotNull String deriv = "";
        switch (order) {
            case 1:
                deriv += "( # * fv ) + ( #v * f)";
                break;
            case 2:
                deriv += "( # * fvv ) + ( 2 * ( #v * fv ) ) + ( #vv * f )";
                break;
            default:
                throw new IllegalArgumentException("Only 1st and 2nd order derivatives are supported");
        }
        @NotNull final String[][] REPLACEMENTS = {{"fvv", "( " + FUNCTION_DATA[getUsedFunctionTermIndex(function)].derivative2.trim() + " )"}, {"fv", "( " + FUNCTION_DATA[getUsedFunctionTermIndex(function)].derivative1.trim() + " )"}, {"f", "( " + function.trim() + " $ )"}, {"#vv", "( " + coefficient.derivative().derivative().toString().trim() + " )"}, {"#v", "( " + coefficient.derivative().toString().trim() + " )"}, {"#", "( " + coefficient.toString().trim() + " )"}, {"$vv", "( " + argument.derivative().derivative().toString().trim() + " )"}, {"$v", "( " + argument.derivative().toString().trim() + " )"}, {"$", "( " + argument.toString().trim() + " )"}};
        return StringManipulator.format(deriv, REPLACEMENTS);
    }
    @NotNull
    public Complex getDegree() {
        return coefficient.getDegree();
    }
    @Override
    public int compareTo(@NotNull FunctionTerm o) {
        return toString().compareTo(o.toString());
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof FunctionTerm && toString().equals(o.toString());
    }
    public String getVariableCode() {
        return variableCode;
    }
    public void setVariableCode(String variableCode) {
        this.variableCode = variableCode;
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
    private class FunctionTermData {
        String function, derivative1, derivative2;
        public FunctionTermData(String function, String derivative1, String derivative2) {
            this.function = function;
            this.derivative1 = derivative1;
            this.derivative2 = derivative2;
        }
        public FunctionTermData(@NotNull FunctionTermData old) {
            function = old.function;
            derivative1 = old.derivative1;
            derivative2 = old.derivative2;
        }
    }
}