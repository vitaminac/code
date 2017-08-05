#pragma once
#include <type_traits>
#define isPositive(size, value) (((0x1 << ((size) - 1)) | (value)) ^ (value))
int subtract (int a, int b);
int karatsuba (int a, int b);
int exp_mod (int base, int exp, int m);
long long unsigned pow_sqrt(unsigned long long base, unsigned int exp);

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
T multiply (T multiplicand, T multiplier) {
	bool isNegative = false;
	T product = 0;
	if (std::is_signed <T>::value) {
		auto size = sizeof(T) * 8;
		if (!isPositive(size,multiplicand)) {
			multiplicand = sum <T>(~multiplicand, 1);
			isNegative ^= true;
		}
		if (!isPositive(size,multiplier)) {
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
