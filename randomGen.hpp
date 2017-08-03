#pragma once
#include <limits>
#include <ctime>

const int maxlimit = std::numeric_limits <int>::max();
typedef boost::mt19937 RNGType;
const int maxRangeRight = static_cast <int>(sqrt(maxlimit));

class Iterator
{
	int n;
	int seed;
	std::uniform_int_distribution <> dist;
	RNGType rng;
public:
	Iterator (): Iterator(0, 0)
	{
	};

	Iterator (int seed, int number) : n(number), seed(seed)
	{
		dist = std::uniform_int_distribution <>(0, maxRangeRight);
		rng.seed(static_cast <int>(std::time(nullptr)) ^ seed);
	};

	Iterator operator ++ (int);

	Iterator operator ++ ();

	int operator* () const;
};

inline Iterator Iterator::operator++ (int)
{
	auto temp = *this;
	n = dist(rng);
	return temp;
}

inline Iterator Iterator::operator++ ()
{
	n = dist(rng);
	return *this;
}

inline int Iterator::operator* () const
{
	return n;
}
