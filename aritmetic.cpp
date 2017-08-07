#include "aritmetic.hpp"
#include <boost/math/tools/config.hpp>

int divide (int dividend, int divisor, int & remainder) {
	// find the sign of dividend, divisor, and future quotient
	bool willBeNegative = false;
	bool remainder_sign = false;
	if (isNegative(dividend)) {
		dividend = sum(~dividend, 1);
		willBeNegative ^= true;
		remainder_sign = true;
	}
	if (isNegative(divisor)) {
		divisor = sum(~divisor, 1);
		willBeNegative ^= true;
	}

	// invert order and add a highest bit, 
	// for example dividend=101011 invert=1110101, initial invert value 2 will add a highest bit 
	// it's for knowing where the number finish
	// if not dividend=1010, will invert to 0101, the origin zero will be ignore
	// we cant determinate the origin number length
	int invert = 2;
	while (dividend) {
		invert |= dividend & 0x1;
		invert = invert << 1;
		dividend = dividend >> 1;
	}

	int quotient = 0;
	remainder = 0;
	// invert = 2 set highest bit as a delimiter
	while (invert > 1)// until delimiter, the highest bit
	{
		remainder = remainder << 1;
		remainder |= invert & 0x1;
		invert = invert >> 1;
		quotient = quotient << 1;

		if (remainder >= divisor) {
			quotient |= 0x1;
			remainder = subtract(remainder, divisor);
		}
	}

	// add sign of quotient
	if (willBeNegative) {
		quotient = sum(~quotient, 1);
	}

	// remainder sign
	if (remainder_sign) {
		remainder = sum(~remainder, 1);
	}

	return quotient;
}


int divide (int dividend, int divisor) {
	int nouse;
	return divide(dividend, divisor, nouse);
}

// find remainder
int mod (int dividend, int divisor) {
	int remainder;
	divide(dividend, divisor, remainder);
	return remainder;
}

// The operation of modular exponentiation calculates the remainder 
// when an integer b (the base) raised to the eth power (the exponent), b^e, is divided by a positive integer m 
// The straightforward method of calculating b^e directly,
// then to take this number modulo m, is unable to apply in case when exp is large enough
int exp_mod (int base, int exp, int m) {
	int result = 1;
	// exponent e be converted to binary notation
	// e = ∑ n-1, i=0 , ai*2^i; a∈{0,1}
	// b^e = b ^ ∑n-1,i=0 = ∏ n-1, i=0, (b^2^i)^ai
	// result = ∏ n-1, i=0 (b^2^i)^ai (mod m)
	while (exp > 0) {
		// if ai = 0; pass; because n^0 = 1
		// if ai = 1; (b^2^i * (∏ i-1, j=0, (b^2^j)^aj mod m)) mod m
		if (exp & 0x1) {
			// ab mod m ≡ (a * (b mod m)) mod m
			result = mod(multiply(result, base), m);
		}
		// a ≡ b mod m => a^2 ≡ b^2 mod m
		// b^2^(i+1) ≡ (b^2^(i) * b^2^(i)) mod m
		base = mod(base * base, m);
		exp >>= 1;
	}
	return result;
}

// The Karatsuba algorithm is a fast multiplication algorithm
int karatsuba (int a, int b) {
	// if a < base 2, b < base; directly calculate
	if (! ((a >> 1) && (b >> 1))) {
		return (a && b) ? (a >> 1 ? a : b) : 0;
	}
	auto p1 = find_first_significant_bit_position(a);
	auto p2 = find_first_significant_bit_position(b);
	auto m = std::max(p1, p2) >> 1;
	auto x1 = a >> m;
	auto y1 = b >> m;
	auto temp = subtract <int>(pow(2, m), 1);
	auto x0 = a & temp;
	auto y0 = b & temp;
	auto z2 = karatsuba(x1, y1);
	auto z0 = karatsuba(x0, y0);
	auto z1 = subtract(subtract <int>(karatsuba(sum <int>(x1, x0), sum <int>(y1, y0)), z2), z0);
	return sum(sum((z2 << (m << 1)), (z1 << m)), z0);
}

// copy comment from wikipedia: https://en.wikipedia.org/wiki/Exponentiation_by_squaring
// exponentiating by squaring is a general method for fast computation 
// of large positive integer powers of a number n and n is positive
long long unsigned pow_sqrt (unsigned long long base, unsigned int exp) {
	long long unsigned temp = 1;
	while (exp) { // if exp is not 0
		if (exp & 0x1) { //exp is odd
			// cached value to temp variable
			exp ^= 0x1; // exp = exp - 1
			temp = multiply <unsigned long long>(base, temp);
		} else { // exp is even
			base = multiply <unsigned long long>(base, base);
			exp >>= 1; // exp = exp / 2
		}
	}
	return temp;
}

// Hamming weight
// https://en.wikipedia.org/wiki/Hamming_weight
// It is thus equivalent to the Hamming distance from the all-zero string of the same length
uint64_t bitcount (uint64_t a) {
	// obtain bitcout of each 2 bit
	a = ((a >> 1) & 0x5555555555555555) + (a & 0x5555555555555555);
	// obtain sum bitcout of each 2 group of 2 bit, (each overall 4bit)
	a = ((a >> 2) & 0x3333333333333333) + (a & 0x3333333333333333);
	// obtain sum bitcout of each 2 group of 4 bit, (each overall 8bit)
	a = ((a >> 4) & 0x0f0f0f0f0f0f0f0f) + (a & 0x0f0f0f0f0f0f0f0f);
	// obtain sum bitcout of each 2 group of 8 bit, (each overall 16bit)
	a = ((a >> 8) & 0x00ff00ff00ff00ff) + (a & 0x00ff00ff00ff00ff);
	// and so on..
	a = ((a >> 16) & 0x0000ffff0000ffff) + (a & 0x0000ffff0000ffff);
	a = ((a >> 32) & 0x00000000ffffffff) + (a & 0x00000000ffffffff);
	return a;
}

// http://homepage.divms.uiowa.edu/~jones/bcd/mod.shtml
//Computing modulus for poweres of two is trivial on a binary computer,
// the term(b mod m) is zero, so we just take the modulus by examining the least significant bits of the binary representation :
// a mod 2i = a & (2i–1)
// Recall that the & operator means logical and. When applied to integers, 
// this computes each bit of the result as the and of the corresponding bits of the operands.
// For all nonzero positive integers i, the binary representation of 2i–1 consists of i consecutive one bits,
// so anding with 2i–1 preserves the least significant i bits of the operand while forcing all more significant bits to zero.
// for example number 3 is a Mersenne number that is, one less than a power of two.
// we can compute a mod 3 as "a mod 3 = ( (a/4) + (a mod 4) ) mod 3" 
uint32_t mod3 (uint32_t a) {
	// origin version
	//while (a > 5) {
	//	int s = 0; /* accumulator for the sum of the digits */
	//	while (a != 0) {
	//		s = s + (a & 3);
	//		a = a >> 2;
	//	}
	//	a = s;
	//}
	///* note, at this point: a < 6 */
	//if (a > 2)
	//	a = a - 3;
	//return a;

	//// inner loop of before implementation
	//// begin
	//// sum up each two bit (same as & 3)
	//// count sum up all group obtain a of next loop
	//a = ((a >> 2) & 0x33333333) + (a & 0x33333333);
	//// A sum of two base-4 digits will be no greater than 6, 
	//// which can still be represented in 3 bits and so is selected by the mask value 7.
	//a = ((a >> 4) & 0x07070707) + (a & 0x07070707);
	//// A sum of four base-4 digits will be no greater than 12,
	//// which can still be represented in 4 bits and so is selected by the mask value F
	//a = ((a >> 8) & 0x000F000F) + (a & 0x000F000F);
	//// A sum of eight base-4 digits will be no greater than 24,
	//// which can still be represented in 5 bits and so is selected by the mask value 1F
	//a = ((a >> 16)) + (a & 0x0000001F);
	//// end


	//// A careful worst - case analysis shows that the outer loop will iterate at most 3 times.
	//// The input FFFFFFEE16, for example, leads to 3 iterations.Worst - case analysis also
	//// reveals the range of values that are input to each step,
	//// allowing th subsequent iteration to be simplified.Unrolling the loop completely
	///* note, at this point: a <= 48, 3 base-4 digits */
	//a = ((a >> 2) & 0x33333333) + (a & 0x33333333);
	//a = ((a >> 4)) + (a & 0x07070707);
	///* note, at this point: a <= 8, 2 base-4 digits */
	//a = ((a >> 2)) + (a & 0x33333333);
	///* note, at this point: a <= 4, 2 base-4 digits */
	//if (a > 2) {
	//	a = a - 3;
	//}

	// a further optimization can be done avoiding the eccentric constants used in the above code

	a = (a >> 16) + (a & 0xFFFF); /* sum base 2**16 digits
								  a <= 0x1FFFE */
	a = (a >> 8) + (a & 0xFF); /* sum base 2**8 digits
								 a <= 0x2FD */
	a = (a >> 4) + (a & 0xF); /* sum base 2**4 digits
								 a <= 0x3C; worst case 0x3B */
	a = (a >> 2) + (a & 0x3); /* sum base 2**2 digits
								 a <= 0x1D; worst case 0x1B */
	a = (a >> 2) + (a & 0x3); /* sum base 2**2 digits
								 a <= 0x9; worst case 0x7 */
	a = (a >> 2) + (a & 0x3); /* sum base 2**2 digits
								 a <= 0x4 */
	if (a > 2)
		a = a - 3;
	return a;
}


// Booth's multiplication algorithm
// furtuner information can be found here: https://www.quora.com/How-does-Booths-algorithm-work
// comment 
int64_t booth_mul (int32_t multiplicand, int32_t multiplier) {
	// Booth's algorithm can be implemented by repeatedly adding 
	// (with ordinary unsigned binary addition) one of two predetermined values
	// A and S to a product P, then performing a rightward arithmetic shift on P
	// Determine the values of A and S, and the initial value of P.
	// All of these numbers should have a length equal to(x + y + 1).
	// let multiplicand be 32 bit
	// A: Fill the most significant (leftmost) bits with the value of m. Fill the remaining (y + 1) bits with zeros.
	int64_t Added = static_cast <int64_t>(static_cast <uint32_t>(multiplicand)) << 32;
	// S: Fill the most significant bits with the value of (−m) in two's complement notation. Fill the remaining (y + 1) bits with zeros
	int64_t Subtracted = static_cast <int64_t>(static_cast <uint32_t>(sum(~multiplicand, 1))) << 32;
	// let multiplier be 31 bit
	// To the right of this, append the value of r.
	int64_t product = static_cast <int64_t>(static_cast <uint32_t>(multiplier));
	// std::memcpy((&product), &multiplier, sizeof(int32_t));
	product = product << 1;
	// P: Fill the most significant x bits with zeros.
	// std::memset(reinterpret_cast <int *>(&product) + 1, 0, sizeof(int32_t));
	product &= 0xffffffff;
	// Fill the least significant (rightmost) bit with a zero.
	for (auto i = 0; i < 31; i++) {
		// Determine the two least significant(rightmost) bits of P
		switch (product & 0x3) {
			case 0x1:
				// If they are 01, find the value of P + A. Ignore any overflow.
				product += Added;
				break;
			case 0x2:
				// If they are 10, find the value of P + S. Ignore any overflow.
				product += Subtracted;
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
