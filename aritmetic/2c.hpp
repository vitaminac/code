#pragma once
#define NOP
#include "overflow.hpp"

/*
 * Basic Arithmetic Operation of Two's complement Signed Number
 * Using Only Bitwise Operator
 */
const int leastSignificantBitMark = 0x1;

template <typename T>
bool zero (T value) {
	T result = 0;
	const auto size = (sizeof(value) << 3);
	for (auto i = 0; i < size; i++) {
		result |= value & leastSignificantBitMark;
		value >>= 1;
	}
	return !(result & leastSignificantBitMark);
}

template <typename T>
T to2c (T operand) throw(Overflow <T>) {
	if (zero <T>(operand)) {
		return static_cast <T>(0);
	} else if (zero <T>(operand << 1)) {
		throw Overflow <T>(0, operand, operand);
	} else {
		T result = ~operand;
		T rightZerosMark = ~0;
		T rightMostBit = 0x1;
		while (zero <T>(operand & leastSignificantBitMark)) {
			operand >>= 1;
			rightZerosMark <<= 1;
			rightMostBit <<= 1;
		}
		return (result & rightZerosMark) | rightMostBit;
	}
}

template <typename T>
T dec (T value) {
	// calculate decrement by 1 exploting Two's complement without use function sub
	return ~to2c <T>(value);
}

// check isNegative of a signed number
template <typename T>
bool isNegative (T value) {
	T size = dec <T>(sizeof(value) << 3);
	T result = value >> size;
	return !zero <T>(result);
}


// assume two value are signed value
template <typename T>
T add (T summand, T addend) throw(Overflow <T>) {
	T carry = addend;
	T result = summand;
	while (carry) {
		auto temp = result;
		result = temp ^ carry;
		carry = (temp & carry) << 1;
	}

	// check overflow
	if (!isNegative <T>(summand ^ addend) && isNegative <T>(summand ^ result)) {
		throw Overflow <T>(summand, addend, result);
	}
	return result;
}


template <typename T>
T sub (T minuend, T subtrahend) throw(Overflow <T>) {
	// subtract is same as add two's complement of subtrahend discarding the overflow
	return add <T>(minuend, to2c <T>(subtrahend));
}

template <typename T>
bool less (T a, T b) {
	return isNegative <T>(sub(a, b));
}

template <typename T>
bool greater (T a, T b) {
	return !less <T>(a, b);
}

template <typename T>
T mul (T multiplicand, T multiplier) {
	bool willBeNegative = false;
	T product = 0;
	if (isNegative <T>(multiplicand)) {
		multiplicand = add <T>(~multiplicand, 1);
		willBeNegative = true;
	}
	if (isNegative <T>(multiplier)) {
		multiplier = add <T>(~multiplier, 1);
		willBeNegative = !willBeNegative;
	}
	while (multiplier) {
		if (multiplier & leastSignificantBitMark) {
			product = add <T>(product, multiplicand);
		}
		multiplicand <<= 1;
		multiplier >>= 1;
	}
	if (willBeNegative) {
		product = to2c <T>(product);
	}
	return product;
}

// Booth's multiplication algorithm
// furtuner informations can be found here: https://www.quora.com/How-does-Booths-algorithm-work
// comment was copied from wikipedia https://en.wikipedia.org/wiki/Booth%27s_multiplication_algorithm
template <typename T>
T IMul (T multiplicand, T multiplier) {
	// Booth's algorithm can be implemented by repeatedly adding 
	// (with ordinary unsigned binary addition) one of two predetermined values
	// A and S to a product P, then performing a rightward arithmetic shift on P
	// Determine the values of A and S, and the initial value of P.
	// All of these numbers should have a length equal to(x + y + 1).
	// let m and r represent the number of bits in multiplicand and multiplier.
	// let multiplicand be 32 bit
	T r = sizeof(multiplicand) << 2;
	T m = add <T>(r, 1);
	// A: Fill the most significant (leftmost) bits with the value of m. Fill the remaining (y + 1) bits with zeros.
	T addend = multiplicand << m;
	// S: Fill the most significant bits with the value of (−m) in two's complement notation. Fill the remaining (y + 1) bits with zeros
	T subtrahend = to2c <T>(multiplicand) << m;
	// let multiplier be 31 bit
	// To the right of this, append the value of r.
	// P: Fill the most significant x bits with zeros.
	T product = ~(static_cast <T>(~0) << m) & (multiplier << 1);
	// Fill the least significant (rightmost) bit with a zero.
	while (r) {
		r = dec(r);
		// Determine the two least significant(rightmost) bits of P
		switch (product & 0x3) {
			case 0x1:
				// If they are 01, find the value of P + A. Ignore any overflow.
				product = add <T>(product, addend);
				break;
			case 0x2:
				// If they are 10, find the value of P + S. Ignore any overflow.
				product = add <T>(product, subtrahend);
				break;
			default:
				// If they are 00, do nothing. Use P directly in the next step.
				// If they are 11, do nothing. Use P directly in the next step.
				NOP; // no operation
		}
		// Arithmetically shift the value obtained in the 2nd step by a single place to the right. 
		// Let P now equal this new value.
		product >>= 1;
	}
	// Drop the least significant(rightmost) bit from P.
	product >>= 1;
	// This is the product of m and r.
	return product;
}

template <typename T>
T divide (T dividend, T divisor, T & remainder) {
	// determinate the sign of dividend and divisor, and future quotient
	bool willBeNegative = false;
	bool remainder_sign = false;
	if (isNegative(dividend)) {
		dividend = to2c <T>(dividend);
		willBeNegative = true;
		remainder_sign = true;
	}
	if (isNegative(divisor)) {
		divisor = to2c <T>(divisor);
		willBeNegative ^= true;
	}

	// invert order and add a highest bit, 
	// for example dividend=101011 invert=1110101, initial invert value 2 will add a highest bit 
	// it's for knowing where the number finish
	// if not dividend=1010, will invert to 0101, the origin zero will be ignore
	// we cant determinate the origin number length
	T invert = 2;
	while (!zero <T>(dividend)) {
		invert |= dividend & 0x1;
		invert = invert << 1;
		dividend = dividend >> 1;
	}

	T quotient = 0;
	remainder = 0;
	// invert = 2 set highest bit as a delimiter
	while (!zero <T>(invert & ~static_cast <T>(0x1)))// until delimiter, the highest bit
	{
		remainder = remainder << 1;
		remainder |= invert & 0x1;
		invert = invert >> 1;
		quotient = quotient << 1;

		if (greater <T>(remainder, divisor)) {
			quotient |= 0x1;
			remainder = sub(remainder, divisor);
		}
	}

	// apply sign to the quotient
	if (willBeNegative) {
		quotient = to2c <T>(quotient);
	}

	// apply sign to the remainder
	if (remainder_sign) {
		remainder = to2c <T>(remainder);
	}

	return quotient;
}

// find remainder
template <typename T>
T mod (T dividend, T divisor) {
	T remainder;
	divide <T>(dividend, divisor, remainder);
	return remainder;
}

template <typename T>
T divide (T dividend, T divisor) {
	T remainder;
	return divide <T>(dividend, divisor, remainder);
}
