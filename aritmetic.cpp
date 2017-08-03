#include "aritmetic.hpp"
#include <iso646.h>
#include <boost/math/tools/config.hpp>

int sum (int a, int b)
{
	auto sum = a;
	auto carry = b;
	while (carry)
	{
		auto temp = sum;
		sum = temp ^ carry;
		carry = (temp & carry) << 1;
	}
	return sum;
}

int subtract (int a, int b)
{
	return sum(a, sum(~b, 1));
}

int multiply (int a, int b)
{
	auto mark = 0x1;
	auto multiplicand = (a << 1) >> 1;
	auto multiplier = (b << 1) >> 1;
	auto product = 0;
	while (multiplier)
	{
		if (multiplier & mark)
		{
			product = sum(product, multiplicand);
		}
		multiplicand <<= 1;
		multiplier >>= 1;
	}
	if (((a ^ b) << 1) >> 1 != (a ^ b))
	{
		product = sum(~product, 1);
	}
	return product;
}

int find_first_significant_bit_position (int n)
{
	//auto p = new unsigned long(0);
	//_BitScanReverse(p, n);
	//n = static_cast <int>(*p);
	//delete p;
	//return n;
	__asm{
		bsr eax, n
	}
}

// The Karatsuba algorithm is a fast multiplication algorithm
int karatsuba (int a, int b)
{
	// if a < base 2, b < base; directly calculate
	if (not (a >> 1 and b >> 1))
	{
		return (a && b) ? (a >> 1 ? a : b) : 0;
	}
	auto p1 = find_first_significant_bit_position(a);
	auto p2 = find_first_significant_bit_position(b);
	auto m = sum(std::max(p1, p2), 1) >> 1;
	auto x1 = a >> m;
	auto y1 = b >> m;
	auto temp = subtract(pow(2, m), 1);
	auto x0 = a & temp;
	auto y0 = b & temp;
	auto z2 = karatsuba(x1, y1);
	auto z0 = karatsuba(x0, y0);
	auto z1 = subtract(subtract(karatsuba(sum(x1, x0), sum(y1, y0)), z2), z0);
	return sum(sum((z2 << (m << 1)), (z1 << m)), z0);
}
