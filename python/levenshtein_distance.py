#!/usr/bin/env python
"""Returns the Levenshtein distance and the percentage of similarity between two strings.

The percentage of similarity is one minus the division of Levenshtein distance
and the maximum length of two strings. The implementation of Levenshtein
distance comes from http://hetland.org/coding/python/levenshtein.py


    Copyright 2015 Herman Tai

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
"""
from __future__ import print_function
from __future__ import absolute_import
from __future__ import division
from __future__ import unicode_literals

import argparse

import sys


class App(object):
    def __init__(
        self,
    ):
        pass

    def run(self, s1, s2):
        d = levenshtein_distance(s1, s2)
        print("Levenshtein distance:", d)
        similarity = 1 - d / float(max(len(s1), len(s2)))
        print("Similarity:", similarity)
        return 0


def levenshtein_distance(a, b):
    n, m = len(a), len(b)
    if n > m:
        # Make sure n <= m, to use O(min(n,m)) space
        a, b = b, a
        n, m = m, n

    current = range(n + 1)
    for i in range(1, m + 1):
        previous, current = current, [i] + [0] * n
        for j in range(1,n+1):
            add, delete = previous[j] + 1, current[j-1] + 1
            change = previous[j - 1]
            if a[j - 1] != b[i - 1]:
                change = change + 1
            current[j] = min(add, delete, change)

    return current[n]


def parse_args(cmd_args):
    description = __doc__.split("Copyright 2015")[0].strip()

    parser = argparse.ArgumentParser(
        description=description,
        formatter_class=argparse.RawDescriptionHelpFormatter,
    )

    parser.add_argument("s1")
    parser.add_argument("s2")

    args = parser.parse_args(cmd_args)
    return args


def main():
    args = parse_args(sys.argv[1:])

    app = App()
    sys.exit(app.run(args.s1, args.s2))


if __name__ == '__main__':
    main()
