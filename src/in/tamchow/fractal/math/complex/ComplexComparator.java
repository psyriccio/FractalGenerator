package in.tamchow.fractal.math.complex;
import in.tamchow.fractal.math.Comparator;
/**
 * Compares 2 {@link Complex} objects using lexicographical ordering.
 */
public class ComplexComparator {
    private ComplexComparator() {
    }
    public static boolean compare(Complex a, Complex b, Comparator type) {
        switch (type) {
            case LESS_THAN:
                return lessThan(a, b);
            case GREATER_THAN:
                return greaterThan(a, b);
            case EQUAL_TO:
                return equalTo(a, b);
            case LESS_THAN_OR_EQUAL_TO:
                return lessThanOrEqualTo(a, b);
            case GREATER_THAN_OR_EQUAL_TO:
                return greaterThanOrEqualTo(a, b);
            case NOT_EQUAL_TO:
                return notEqualTo(a, b);
            default:
                throw new UnsupportedOperationException("Invalid " + type.getClass() + ": with alias " + type.getAlias());
        }
    }
    public static boolean lessThan(Complex a, Complex b) {
        return (a.real() == b.real() ? a.imaginary() < b.imaginary() : a.real() < b.real());
    }
    public static boolean equalTo(Complex a, Complex b) {
        return a.real() == b.real() && a.imaginary() == b.imaginary();
    }
    public static boolean lessThanOrEqualTo(Complex a, Complex b) {
        return lessThan(a, b) || equalTo(a, b);
    }
    public static boolean greaterThanOrEqualTo(Complex a, Complex b) {
        return !lessThan(a, b);
    }
    public static boolean greaterThan(Complex a, Complex b) {
        return !(lessThanOrEqualTo(a, b));
    }
    public static boolean notEqualTo(Complex a, Complex b) {
        return !equalTo(a, b);
    }
}