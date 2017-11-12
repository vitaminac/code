#pragma once
#include <stdexcept>
#include "string"
using std::to_string;
using std::exception;

template <typename T>
class Overflow : exception {
private:
	T operandA;
	T operandB;
	T result_;
public:
	Overflow (T a, T b, T result) : exception(), operandA(a), operandB(b), result_(result) {
	};

	virtual char const * what () const override {
		return ("overflow occurred when we were operating with " + to_string(this->operandA) + ", " +
		        to_string(this->operandB)).c_str();
	};

	virtual T getResult () {
		return this->result_;
	}
};
