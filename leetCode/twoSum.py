#!/usr/bin/python
# -*- coding: utf-8 -*-
# encoding=utf-8
# hashMap complexity was O(1)
class WrongSolution:
    def twoSum (self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """

        # assuming that array was ordered
        # assuming that has only one solution
        # O(n) overall complexity
        # binary search for the greatest number smaller than target
        # O(log n) complexity
        left = 0
        right = len(nums) - 1
        while (right - left) > 0:
            middle = (right + left) // 2
            if nums[middle] > target:
                right = middle - 1
            else:
                left = middle + 1
        # reset position, left to 0, and right is greatest number smaller than target
        right = left
        left = 0
        # O(n) complexity
        # try nums[left] + nums[right], if is correted finish
        # increments left if less, decrements right if greater
        while (right - left) > 0:
            intent = nums[right] + nums[left]
            if intent == target:
                return [left, right]
            elif intent > target:
                right -= 1
            else:
                left += 1


class Solution:
    def twoSum (self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        buffer_dic = {}
        for i in range(len(nums)):
            if nums[i] in buffer_dic:
                return [buffer_dic[nums[i]], i]
            else:
                buffer_dic[target - nums[i]] = i


from functools import reduce
from random import randint


def test (test_time: int, list_size: int = 50, up_num: int = 999999999999) -> None:
    # test driven
    solution = Solution()
    sol = []
    for i in range(test_time):
        # with up nums enough big, ensure by probabilistic that only have one solution, although not 100%
        given_nums = frozenset([randint(0, up_num) for x in range(randint(5, list_size))])
        given_nums = sorted(given_nums)
        l = len(given_nums)
        x = randint(0, l - 2)
        y = randint(x + 1, l - 1)
        target = given_nums[x] + given_nums[y]
        print(given_nums, "result ", x, " ", y, "finding ", target, end=" ", flush=True)
        try:
            sol = solution.twoSum(given_nums, target)
            assert sol
        except Exception as e:
            sol = solution.twoSum(given_nums, target)
        t = reduce(lambda x, y: x + y, list(map(lambda x: given_nums[x], sol)))
        print(sol, ": ", t)
        assert t == target
        assert sol[0] == x
        assert sol[1] == y
    print("test success")


test(1000, 100, 99999999999999999999999999999999999999)
