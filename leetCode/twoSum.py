# encoding=utf8
class Solution:
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """

        # assuming that array was ordered
        # assuming that has only one solution
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
        # try right and left, if is correted finish
        # increments left if less,decrements right if greater
        while (right - left) > 0:
            intent = nums[right] + nums[left]
            if intent == target:
                return [left, right]
            elif intent > target:
                right -= 1
            else:
                left += 1


from functools import reduce


def test(given_nums: list, target: int) -> bool:
    # test driven
    solution = Solution()
    sol = []
    try:
        sol = solution.twoSum(given_nums, target)
        assert sol
    except Exception as e:
        sol = solution.twoSum(given_nums, target)
    t = reduce(lambda x, y: x + y, list(map(lambda x: given_nums[x], sol)))
    print(sol, ": ", t)
    return t == target


from random import randint

for i in range(10000):
    s = frozenset([randint(0, 999999999999) for x in range(30)])
    s = sorted(s)
    l = len(s)
    x = randint(0, l - 2)
    y = randint(x + 1, l - 1)
    result = s[x] + s[y]
    print(s, "result ", x, " ", y, "finding ", result, end=" ", flush=True)
    assert test(s, result)
