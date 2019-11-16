#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int find(int *arr, int n)
{
    bool found;
    for (int i = 0; i < n; i++)
    {
        found = false;
        for (int j = 0; j < n; j++)
        {
            if (arr[i] == arr[j] && i != j)
            {
                found = true;
                break;
            }
        }
        if (!found)
        {
            return arr[i];
        }
    }
    return 0;
}

int main()
{
    int arr[9] = {9, 5, 6, 1, 2, 9, 5, 6, 1};
    printf("%d\n", find(arr, 9));
}