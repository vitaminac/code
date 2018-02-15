package utils;

public class NumberUtilities {

	public static Number addNumbers(Number a, Number b) {
		if (a instanceof Double || b instanceof Double) {
			return new Double(a.doubleValue() + b.doubleValue());
		} else if (a instanceof Float || b instanceof Float) {
			return new Float(a.floatValue() + b.floatValue());
		} else if (a instanceof Long || b instanceof Long) {
			return new Long(a.longValue() + b.longValue());
		} else {
			return new Integer(a.intValue() + b.intValue());
		}
	}

	public static Number subNumbers(Number a, Number b) {
		if (a instanceof Double || b instanceof Double) {
			return new Double(a.doubleValue() - b.doubleValue());
		} else if (a instanceof Float || b instanceof Float) {
			return new Float(a.floatValue() - b.floatValue());
		} else if (a instanceof Long || b instanceof Long) {
			return new Long(a.longValue() - b.longValue());
		} else {
			return new Integer(a.intValue() - b.intValue());
		}
	}

	public static Number multNumbers(Number a, Number b) {
		if (a instanceof Double || b instanceof Double) {
			return new Double(a.doubleValue() * b.doubleValue());
		} else if (a instanceof Float || b instanceof Float) {
			return new Float(a.floatValue() * b.floatValue());
		} else if (a instanceof Long || b instanceof Long) {
			return new Long(a.longValue() * b.longValue());
		} else {
			return new Integer(a.intValue() * b.intValue());
		}
	}

	
	
}
