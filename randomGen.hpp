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
	int itr_time;
	std::uniform_int_distribution <> dist;
	RNGType rng;
public:
	Iterator (): Iterator(0, 0)
	{
	};

	explicit Iterator (int time, int seed = 0) : n(0), seed(seed), itr_time(time)
	{
		dist = std::uniform_int_distribution <>(0, maxRangeRight);
		rng.seed(static_cast <int>(std::time(nullptr)) ^ seed);
	};

	Iterator operator ++ (int);

	Iterator & operator ++ ();

	int operator* () const;

	Iterator & begin ();

	int end () const;
};

inline Iterator Iterator::operator++ (int)
{
	auto temp = *this;
	n = dist(rng);
	return temp;
}

inline Iterator & Iterator::operator++ ()
{
	n = dist(rng);
	itr_time--;
	return *this;
}

inline int Iterator::operator* () const
{
	return n;
}

inline Iterator & Iterator::begin ()
{
	++(*this);
	return *this;
}

inline int Iterator::end () const
{
	return itr_time <= 0;
}
