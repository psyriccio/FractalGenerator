package in.tamchow.fractal.math.complex;
import in.tamchow.fractal.helpers.StringManipulator;
import in.tamchow.fractal.math.symbolics.Polynomial;
/**
 * Implements an iterative evaluator for functions described in ComplexOperations,
 * making heavy use of string replacement;
 */
public class FunctionEvaluator {
    private String[][] constdec;
    private String z_value, oldvalue, variableCode, oldvariablecode;
    private boolean hasBeenSubstituted;
    private boolean advancedDegree;
    public FunctionEvaluator(String variable, String variableCode, String[][] varconst) {
        this(variable, variableCode, varconst, true);
    }
    public FunctionEvaluator(String variable, String variableCode, String[][] varconst, boolean advancedDegree) {
        setZ_value(variable);
        setConstdec(varconst); setVariableCode(variableCode); setOldvariablecode(variableCode + "_p");
        hasBeenSubstituted = false;
        setAdvancedDegree(advancedDegree);
    }
    public FunctionEvaluator(String variableCode, String[][] varconst, boolean advancedDegree) {
        setConstdec(varconst); setVariableCode(variableCode); setOldvariablecode(variableCode + "_p");
        hasBeenSubstituted = false;
        setAdvancedDegree(advancedDegree);
    }
    public FunctionEvaluator(String variableCode, String[][] varconst, String oldvariablecode, boolean advancedDegree) {
        setConstdec(varconst); setVariableCode(variableCode); setOldvariablecode(oldvariablecode);
        hasBeenSubstituted = false; setAdvancedDegree(advancedDegree);
    }
    public FunctionEvaluator(String variable, String variableCode, String[][] varconst, String oldvariablecode, boolean advancedDegree) {
        setZ_value(variable); setConstdec(varconst); setVariableCode(variableCode); setOldvariablecode(oldvariablecode);
        hasBeenSubstituted = false; setAdvancedDegree(advancedDegree);
    }
    public static FunctionEvaluator prepareIFS(String variableCode, String r_code, String t_code, String p_code, double x, double y) {
        String[][] varconst = {{"0", "0"}};
        FunctionEvaluator fe = new FunctionEvaluator(variableCode, x + "", varconst);
        fe.addConstant(new String[]{r_code, Math.sqrt(x * x + y * y) + ""});
        fe.addConstant(new String[]{t_code, Math.atan2(y, x) + ""});
        fe.addConstant(new String[]{p_code, Math.atan2(x, y) + ""}); return fe;
    }
    public void addConstant(String[] constant) {
        String[][] tmpconsts = new String[constdec.length][2]; for (int i = 0; i < constdec.length; i++) {
            System.arraycopy(constdec[i], 0, tmpconsts[i], 0, tmpconsts.length);
        } constdec = new String[tmpconsts.length + 1][2]; for (int i = 0; i < tmpconsts.length; i++) {
            System.arraycopy(tmpconsts[i], 0, constdec[i], 0, constdec.length);
        } System.arraycopy(constant, 0, constdec[constdec.length - 1], 0, constant.length);
    }
    public String getOldvariablecode() {return oldvariablecode;}
    public void setOldvariablecode(String oldvariablecode) {this.oldvariablecode = oldvariablecode;}
    public String getOldvalue() {return oldvalue;}
    public void setOldvalue(String oldvalue) {this.oldvalue = oldvalue;}
    public boolean isAdvancedDegree() {
        return advancedDegree;
    }
    public void setAdvancedDegree(boolean advancedDegree) {
        this.advancedDegree = advancedDegree;
    }
    public String getVariableCode() {
        return variableCode;
    }
    public void setVariableCode(String variableCode) {
        this.variableCode = variableCode;
    }
    public Complex getDegree(String function) {
        function = function.replace(oldvariablecode, variableCode);
        Complex degree = new Complex(Complex.ZERO);
        if ((function.contains(variableCode) && (!function.contains("^")))) {
            degree = new Complex(Complex.ONE); return degree;
        } if (!hasBeenSubstituted) {
            hasBeenSubstituted = true; return getDegree(substitute(function, true));
        } if (function.contains("exp")) {
            int startidx = function.indexOf("exp");
            int endidx = StringManipulator.findMatchingCloser('(', function, function.indexOf('(', startidx + 1));
            String function2 = function.replace(function.substring(startidx, endidx + 1), "");
            return getDegree(function2);
        } if (function.contains("log")) {
            int startidx = function.indexOf("log");
            int endidx = StringManipulator.findMatchingCloser('(', function, function.indexOf('(', startidx + 1));
            String function2 = function.replace(function.substring(startidx, endidx + 1), "");
            return getDegree(function2);
        }
        if ((function.contains("*") || function.contains("/")) && advancedDegree) {
            for (int i = 0; i < function.length(); i++) {
                if (function.charAt(i) == '*' || function.charAt(i) == '/') {
                    int closeLeftIndex = StringManipulator.indexOfBackwards(function, i, ')');
                    int openLeftIndex = StringManipulator.findMatchingOpener(')', function, closeLeftIndex);
                    Complex dl = getDegree(function.substring(openLeftIndex, closeLeftIndex + 1));
                    int openRightIndex = function.indexOf('(', i);
                    int closeRightIndex = StringManipulator.findMatchingCloser('(', function, openRightIndex);
                    Complex dr = getDegree(function.substring(openRightIndex, closeRightIndex + 1));
                    Complex tmpdegree = new Complex(Complex.ZERO); if (function.charAt(i) == '*') {
                        tmpdegree = ComplexOperations.add(dl, dr);
                    } else if (function.charAt(i) == '/') {tmpdegree = ComplexOperations.subtract(dl, dr);}
                    String function2 = function.replace(function.substring(openLeftIndex, closeRightIndex + 1), "z ^ " + tmpdegree);
                    return getDegree(function2);
                }
            }
        } int idx = 0, varidx = 0; while (function.indexOf('^', idx) != -1) {
            varidx = function.indexOf(variableCode, varidx) + 1; idx = function.indexOf('^', varidx) + 1;
            Complex nextDegree = new Complex(function.substring(idx + 1, function.indexOf(' ', idx + 1)));
            degree = (nextDegree.modulus() > degree.modulus()) ? nextDegree : degree;
        } return degree;
    }
    public Complex getDegree(Polynomial polynomial) {return getDegree(limitedEvaluate(polynomial.toString(), polynomial.countVariableTerms() * 2 + polynomial.countConstantTerms()));}
    public String getZ_value() {
        return z_value;
    }
    public void setZ_value(String z_value) {
        this.z_value = z_value;
    }
    public String[][] getConstdec() {
        return constdec;
    }
    public void setConstdec(String[][] constdec) {
        this.constdec = constdec;
    }
    public double evaluateForIFS(String expr) {
        return evaluate(expr, false).modulus();
    }
    public Complex evaluate(String expr, boolean isSymbolic) {
        String subexpr = substitute(expr, isSymbolic); Complex ztmp; int flag = 0; do {
            ztmp = eval(process(subexpr)); if (!(subexpr.lastIndexOf('(') == -1 || subexpr.indexOf(')') == -1)) {
                subexpr = subexpr.replace(subexpr.substring((subexpr.lastIndexOf('(')), subexpr.indexOf(')', subexpr.lastIndexOf('(') + 1) + 1), "" + ztmp);
            } else {++flag;}
        } while (flag <= 1); return ztmp;
    }
    private Complex eval(String[] processed) {
        Complex ztmp = new Complex(Complex.ZERO); if (processed.length == 1) {
            return new Complex(processed[0]);
        } for (int i = 0; i < processed.length - 1; i++) {
            try {
                switch (processed[i]) {
                    case "+": ztmp = ComplexOperations.add(ztmp, new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "-": ztmp = ComplexOperations.subtract(ztmp, new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "*": ztmp = ComplexOperations.multiply(ztmp, new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "/": ztmp = ComplexOperations.divide(ztmp, new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "^": ztmp = ComplexOperations.power(ztmp, new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "exp": ztmp = ComplexOperations.exponent(new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "log": ztmp = ComplexOperations.principallog(new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "log2": ztmp = ComplexOperations.log(ztmp, new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "sin": ztmp = ComplexOperations.sin(new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "sinh": ztmp = ComplexOperations.sinh(new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "cos": ztmp = ComplexOperations.cos(new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break;
                    case "cosh": ztmp = ComplexOperations.cosh(new Complex(processed[i + 1])); if (i < (processed.length - 1)) {
                        ++i;
                    } break; case "inv": ztmp = ztmp.inverse(); if (i < (processed.length - 1)) {++i;} break;
                    case "conj": ztmp = ztmp.conjugate(); if (i < (processed.length - 1)) {++i;} break;
                    case "re": ztmp = new Complex(ztmp.real(), 0); if (i < (processed.length - 1)) {++i;} break;
                    case "im": ztmp = new Complex(0, ztmp.imaginary()); if (i < (processed.length - 1)) {++i;} break;
                    case "flip": ztmp = ComplexOperations.flip(ztmp); if (i < (processed.length - 1)) {++i;} break;
                    default: ztmp = new Complex(processed[i]);
                }
            } catch (ArrayIndexOutOfBoundsException ae) {
                throw new IllegalArgumentException("Function Input Error", ae);
            }
        } return ztmp;
    }
    private String[] process(String subexpr) {
        String expr; if (subexpr.lastIndexOf('(') == -1 || subexpr.indexOf(')') == -1) {expr = subexpr;} else {
            expr = subexpr.substring(subexpr.lastIndexOf('(') + 1, subexpr.indexOf(')', subexpr.lastIndexOf('(') + 1));
        } expr = expr.trim(); return expr.split(" ");
    }
    private String substitute(String expr, boolean isSymbolic) {
        String[] mod = expr.split(" "); String sub = ""; for (int i = 0; i < mod.length; i++) {
            if (mod[i].equalsIgnoreCase(variableCode) && (!isSymbolic)) {
                mod[i] = "" + z_value;
            } else if ((mod[i].equalsIgnoreCase(oldvariablecode)) && (!isSymbolic)) {
                mod[i] = "" + z_value;
            } else if (getConstant(mod[i]) != null) {mod[i] = getConstant(mod[i]);}
        } for (String aMod : mod) {sub += aMod + " ";} return sub.trim();
    }
    private String getConstant(String totry) {
        String val; for (String[] aConstdec : constdec) {
            if (aConstdec[0].equals(totry)) {
                val = aConstdec[1]; return val;
            }
        } return null;
    }
    protected String limitedEvaluate(String expr, int depth) {
        String subexpr = substitute(expr, true); Complex ztmp; int flag = 0, ctr = 0; do {
            ztmp = eval(process(subexpr));
            if (!(subexpr.lastIndexOf('(') == -1 || subexpr.indexOf(')') == -1)) {
                subexpr = subexpr.replace(subexpr.substring((subexpr.lastIndexOf('(')), subexpr.indexOf(')', subexpr.lastIndexOf('(') + 1) + 1), "" + ztmp);
                ctr++;
            } else {++flag;}
        } while (flag <= 1 && ctr <= depth); return subexpr;
    }
}