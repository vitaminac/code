//! functionsTest.cpp
#define BOOST_TEST_MAIN

#define BOOST_TEST_MODULE aritmetic
#include "aritmetic.hpp"
#include <boost/test/unit_test.hpp>
#include <boost/test/data/test_case.hpp>
#include "boost/random.hpp"
#include "boost/generator_iterator.hpp"
#include "randomGen.hpp"

namespace bdata = boost::unit_test::data;

BOOST_AUTO_TEST_CASE(Sum) {
	BOOST_TEST(sum(-1, 1) == 0);
	BOOST_TEST(sum(1, 1) == 2);
	BOOST_TEST(sum(-1, -1) == -2);
	BOOST_TEST(sum(0, 0) == 0);
	BOOST_TEST(sum(0, 1) == 1);
	BOOST_TEST(sum(1, 0) == 1);
}

BOOST_AUTO_TEST_CASE(Subtract) {
	BOOST_TEST(subtract(0, 0) == 0);
	BOOST_TEST(subtract(-1, 0) == -1);
	BOOST_TEST(subtract(0, -1) == 1);
	BOOST_TEST(subtract(-1, -1) == 0);
	BOOST_TEST(subtract(-1, 1) == -2);
	BOOST_TEST(subtract(1, 1) == 0);
	BOOST_TEST(subtract(2, 1) == 1);
	BOOST_TEST(subtract(1, 2) == -1);
}

BOOST_AUTO_TEST_CASE(Multiply) {
	BOOST_TEST(multiply(10086, 43378) == 10086 * 43378);
	BOOST_TEST(multiply(1, 43378) == 1 * 43378);
	BOOST_TEST(multiply(43378, 1) == 1 * 43378);
	BOOST_TEST(multiply(43378, 0) == 0);
	BOOST_TEST(multiply(0, 0) == 0);
	BOOST_TEST(multiply(1, 1) == 1 * 1);
	BOOST_TEST(multiply(2, 10) == 2 * 10);
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

BOOST_AUTO_TEST_CASE(Modular_exponentiation) {
	BOOST_TEST(exp_mod(1,10, 1) == 0);
	BOOST_TEST(exp_mod(2, 9999, 2) == 0);
	BOOST_TEST(exp_mod(2, 20, 3) == (static_cast <long long unsigned>(pow(2,20)) % 3));
	BOOST_TEST(exp_mod(7, 17, 73) == (static_cast<long long unsigned>(pow(7,17)) % 73));
	BOOST_TEST(exp_mod(13, 9, 71) == (static_cast<long long unsigned>(pow(13,9)) % 71));
}

BOOST_AUTO_TEST_CASE(Pow_Sqrt) {
	BOOST_TEST(pow_sqrt(0, 10) == 0);
	BOOST_TEST(pow_sqrt(1, 10) == 1);
	BOOST_TEST(pow_sqrt(1, 11) == 1);
	BOOST_TEST(pow_sqrt(2, 30) == static_cast <long long unsigned>(pow(2, 30)));

}

// BOOST_DATA_TEST_CASE(test1, bdata::random((bdata::seed = 100UL, bdata::distribution = std::uniform_int_distribution<>(1, maxlimit), bdata::engine = rng)) ^ bdata::make(random()), a, b)
BOOST_AUTO_TEST_CASE(randomnessTest) {
	auto itr = Iterator(1000);
	for (auto i = itr.begin(); !itr.end(); ++itr) {
		auto a = *itr;
		auto b = *(++itr);
		BOOST_TEST(a + b == sum(a, b));
		BOOST_TEST(a - b == subtract(a, b));
		BOOST_TEST(a * b == multiply(a, b));
		BOOST_TEST(a * b == karatsuba(a, b));
	}
}
