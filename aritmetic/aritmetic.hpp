#pragma once
#include <type_traits>
#include <cstdint>
#include "2c.hpp"
#define dropNoSignificantBit(bitsLen, value) (((value) & (~(~(0x0) << (bitsLen))) ^ (value)))
const int byte_size = 8;
const int ieee754_double_precision_bits = 52;
uint32_t mod3 (uint32_t a);
uint64_t bitcount (uint64_t a);
int karatsuba (int a, int b);
int exp_mod (int base, int exp, int m);
long long unsigned pow_sqrt (unsigned long long base, unsigned int exp);


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
	T mark = sub <T>(0x1 << chunk_size, 1);
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
		mark = sub <T>(0x1 << chunk_size, 1);
	}
	return position;
}
