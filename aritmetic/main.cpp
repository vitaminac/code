//! functionsTest.cpp
#define BOOST_TEST_MAIN

#define BOOST_TEST_MODULE aritmetic
#include "aritmetic.hpp"
#include <boost/test/unit_test.hpp>
#include <boost/test/data/test_case.hpp>
#include "boost/random.hpp"
#include "boost/generator_iterator.hpp"
#include "randomGen.hpp"
#include "overflow.hpp"
#include <limits>
using std::numeric_limits;
namespace bdata = boost::unit_test::data;

BOOST_AUTO_TEST_CASE(Convert2c) {
	BOOST_CHECK_THROW(to2c<int>(numeric_limits<int>().min()), Overflow<int>);
}

BOOST_AUTO_TEST_CASE(Zero) {
	BOOST_TEST(zero<int>(0));
	BOOST_TEST(!zero<int>(0-1));
	BOOST_TEST(!zero<int>(1));
	BOOST_TEST(!zero<int>(32768));
}

BOOST_AUTO_TEST_CASE(Sum) {
	BOOST_TEST(add(-1, 1) == 0);
	BOOST_TEST(add(1, 1) == 2);
	BOOST_TEST(add(-1, -1) == -2);
	BOOST_TEST(add(0, 0) == 0);
	BOOST_TEST(add(0, 1) == 1);
	BOOST_TEST(add(1, 0) == 1);
	BOOST_TEST(add(-5, -3) == -8);
	BOOST_CHECK_THROW(add(numeric_limits<int>().max(),static_cast<int>(1)), Overflow<int>);
	BOOST_CHECK_THROW(add(numeric_limits<int>().min(), static_cast<int>(-1)), Overflow<int>);
}

BOOST_AUTO_TEST_CASE(Subtract) {
	BOOST_TEST(sub(0, 0) == 0);
	BOOST_TEST(sub(-1, 0) == -1);
	BOOST_TEST(sub(0, -1) == 1);
	BOOST_TEST(sub(-1, -1) == 0);
	BOOST_TEST(sub(-1, 1) == -2);
	BOOST_TEST(sub(1, 1) == 0);
	BOOST_TEST(sub(2, 1) == 1);
	BOOST_TEST(sub(1, 2) == -1);
	BOOST_CHECK_NO_THROW(sub(-1, numeric_limits<int>().max()));
	BOOST_CHECK_THROW(sub(-2,numeric_limits<int>().max()), Overflow<int>);
	BOOST_CHECK_THROW(sub(1,numeric_limits<int>().min()), Overflow<int>);
}

BOOST_AUTO_TEST_CASE(Multiply) {
	BOOST_TEST(mul(10086, 43378) == 10086 * 43378);
	BOOST_TEST(mul(1, 43378) == 1 * 43378);
	BOOST_TEST(mul(43378, 1) == 1 * 43378);
	BOOST_TEST(mul(43378, 0) == 0);
	BOOST_TEST(mul(0, 0) == 0);
	BOOST_TEST(mul(1, 1) == 1 * 1);
	BOOST_TEST(mul(2, 10) == 2 * 10);
	BOOST_TEST(mul(-1, -1) == 1);
	BOOST_TEST(mul(-1, 1) == -1);
	BOOST_TEST(mul(1, -1) == -1);
	BOOST_TEST(mul(static_cast < long long unsigned>(815730721), static_cast < long long unsigned>(28561)) ==
		23298085122481);
}

BOOST_AUTO_TEST_CASE(Booth_Multiplication) {
	BOOST_TEST(IMul(3, -4) == -12);
	BOOST_TEST(IMul(1, 1) == 1);
	BOOST_TEST(IMul(16, 1) == 16);
	BOOST_TEST(IMul<int64_t>(10086, 43378) == 10086 * 43378);
	BOOST_TEST(IMul<int64_t>(1, 43378) == 1 * 43378);
	BOOST_TEST(IMul<int64_t>(43378, 1) == 1 * 43378);
	BOOST_TEST(IMul(43378, 0) == 0);
	BOOST_TEST(IMul(0, 0) == 0);
	BOOST_TEST(IMul(1, 1) == 1 * 1);
	BOOST_TEST(IMul(2, 10) == 2 * 10);
	BOOST_TEST(IMul(-1, -1) == 1);
	BOOST_TEST(IMul(-1, 1) == -1);
	BOOST_TEST(IMul(1, -1) == -1);
}

BOOST_AUTO_TEST_CASE(Divide) {
	BOOST_TEST(divide(10086, 23) == 10086 / 23);
	BOOST_TEST(divide(-10086, 23) == -10086 / 23);
	BOOST_TEST(divide(-1, -1) == 1);
	BOOST_TEST(divide(-1, 1) == -1);
	BOOST_TEST(divide(1, -1) == -1);
	BOOST_TEST(divide(0, -1) == 0);
	BOOST_TEST(divide(0, 1) == 0);
}

BOOST_AUTO_TEST_CASE(Mod) {
	BOOST_TEST(mod(10086, 23) == 10086 % 23);
	BOOST_TEST(mod(-10086, 23) == -10086 % 23);
	BOOST_TEST(mod(-1, -1) == 0);
	BOOST_TEST(mod(-1, 1) == 0);
	BOOST_TEST(mod(1, -1) == 0);
}

BOOST_AUTO_TEST_CASE(Karatsuba) {
	BOOST_TEST(karatsuba(10086, 43378) == 10086 * 43378);
	BOOST_TEST(karatsuba(1, 43378) == 1 * 43378);
	BOOST_TEST(karatsuba(43378, 1) == 1 * 43378);
	BOOST_TEST(karatsuba(43378, 0) == 0);
	BOOST_TEST(karatsuba(0, 0) == 0);
	BOOST_TEST(karatsuba(1, 1) == 1 * 1);
	BOOST_TEST(karatsuba(2, 10) == 2 * 10);
}

BOOST_AUTO_TEST_CASE(Mod3) {
	BOOST_TEST(bitcount(17895) == 9);
	BOOST_TEST(mod3(17895) == 17895 % 3);
}

BOOST_AUTO_TEST_CASE(Pow_Sqrt) {
	BOOST_TEST(pow_sqrt(0, 10) == 0);
	BOOST_TEST(pow_sqrt(1, 10) == 1);
	BOOST_TEST(pow_sqrt(1, 11) == 1);
	BOOST_TEST(pow_sqrt(2, 30) == static_cast <long long unsigned>(pow(2, 30)));
	BOOST_TEST(pow_sqrt(12, 8) == static_cast <long long unsigned>(pow(12, 8)));
	BOOST_TEST(pow_sqrt(9, 10) == static_cast <long long unsigned>(pow(9, 10)));
	BOOST_TEST(pow_sqrt(13, 12) == static_cast <long long unsigned>(pow(13, 12)));
	BOOST_TEST(pow_sqrt(11, 17) == 505447028499293771);
	BOOST_TEST(pow_sqrt(17, 14) == 168377826559400929);
	BOOST_TEST(pow_sqrt(17, 13) == 9904578032905937);
	BOOST_TEST(pow_sqrt(9, 18) == 150094635296999121);
}

BOOST_AUTO_TEST_CASE(Modular_exponentiation) {
	BOOST_TEST(exp_mod(1, 10, 1) == 0);
	BOOST_TEST(exp_mod(2, 9999, 2) == 0);
	BOOST_TEST(exp_mod(2, 20, 3) == pow_sqrt(2, 20) % 3);
	BOOST_TEST(exp_mod(7, 17, 73) == pow_sqrt(7, 17) % 73);
	BOOST_TEST(exp_mod(13, 9, 71) == pow_sqrt(13, 9) % 71);
	BOOST_TEST(exp_mod(15, 14, 2) == pow_sqrt(15, 14) % 2);
}

// BOOST_DATA_TEST_CASE(test1, bdata::random((bdata::seed = 100UL, bdata::distribution = std::uniform_int_distribution<>(1, maxlimit), bdata::engine = rng)) ^ bdata::make(random()), a, b)
BOOST_AUTO_TEST_CASE(randomnessTest) {
	auto itr = Iterator(1000);
	auto smaller_itr = Iterator(1000, 18, 1, 20);
	for (auto i = itr.begin(), s = smaller_itr.begin(); !(itr.end() || smaller_itr.end()); ++itr, ++smaller_itr) {
		auto a = *itr;
		auto b = *(++itr);
		BOOST_TEST(a + b == add(a, b));
		BOOST_TEST(a - b == sub(a, b));
		BOOST_TEST(a * b == mul(a, b));
		BOOST_TEST(a * b == IMul<int64_t>(a, b));
		BOOST_TEST(a * b == karatsuba(a, b));
		BOOST_TEST(a / b == divide(a, b));
		BOOST_TEST(a % b == mod(a, b));
		BOOST_TEST(a % 3 == mod3(a));
		auto base = *smaller_itr;
		auto exp = *(++smaller_itr);
		auto modulo = *(++smaller_itr);
		auto p = pow(base, exp);
		auto pi = static_cast <unsigned long long>(p);
		if (pi == p) {
			auto drop_bits_size = (sizeof(long long) * 8 - ieee754_double_precision_bits + 1);
			BOOST_TEST(dropNoSignificantBit(drop_bits_size, pow_sqrt(base, exp)) == dropNoSignificantBit(drop_bits_size, pi),
				"falied in base " << base <<" exponent " << exp);
			BOOST_TEST(exp_mod(base, exp, modulo) == pow_sqrt(base, exp) % modulo, "falied in base " << base << " exponent " <<
				exp << " modulo " << modulo);
		}
	}
}
