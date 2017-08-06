#pragma once
#include <type_traits>
#include <cstdint>
#define isNegative(value) (((0x1 << ((sizeof(value) << 3) - 1))) & (value))
#define dropNoSignificantBit(bitsLen, value) (((value) & (~(~(0x0) << (bitsLen))) ^ (value)))
const int byte_size = 8;
const int ieee754_double_precision_bits = 52;
int divide (int a, int b);
int mod (int a, int b);
uint32_t mod3 (uint32_t a);
uint64_t bitcount (uint64_t a);
int karatsuba (int a, int b);
int exp_mod (int base, int exp, int m);
long long unsigned pow_sqrt (unsigned long long base, unsigned int exp);

template <typename T>
T sum (T a, T b) {
	auto sum = a;
	auto carry = b;
	while (carry) {
		auto temp = sum;
		sum = temp ^ carry;
		carry = (temp & carry) << 1;
	}
	return sum;
}

template <typename T>
T subtract (T minuend, T subtrahend) {
	// subtract is same as add two's complement of subtrahend discarding the overflow
	return sum <T>(minuend, sum(~subtrahend, 1));
}

template <typename T>
T multiply (T multiplicand, T multiplier) {
	bool isNegative = false;
	T product = 0;
	if (std::is_signed <T>::value) {
		if (isNegative(multiplicand)) {
			multiplicand = sum <T>(~multiplicand, 1);
			isNegative ^= true;
		}
		if (isNegative(multiplier)) {
			multiplier = sum <T>(~multiplier, 1);
			isNegative ^= true;
		}
	}
	while (multiplier) {
		if (multiplier & 0x1) { // 0x1 mark
			product = sum(product, multiplicand);
		}
		multiplicand <<= 1;
		multiplier >>= 1;
	}
	if (isNegative) {
		product = sum <T>(~product, 1);
	}
	return product;
}

template <typename T>
int find_first_significant_bit_position (T n) {
	// Divide and conquer
	int position = sizeof(T) * byte_size;
	//__asm {
	//	bsr eax, n
	//	mov position, eax
	//} // finish
	// check size
	int chunk_size = position >> 1; // divide 2
	T mark = subtract <T>(0x1 << chunk_size, 1);
	while (chunk_size) {
		if (n >> chunk_size & mark) {
			// & mark, avoiding signed shift
			// set new value
			n = n >> chunk_size & mark;
		} else {
			// update position
			position = position - chunk_size;
		}
		// check size divide 2
		chunk_size = chunk_size >> 1;
		// generate new mark
		mark = subtract <T>(0x1 << chunk_size, 1);
	}
	return position;
}
