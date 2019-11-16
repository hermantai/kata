# kata #

For practice coding for different languages:

* Go
* Python
* Java

## Algorithms ##

Track multiple pointers
* Multiple pointers moving
* Partitioning in single sweep
* Sliding window

Recursions
* Dynamic programming
* Memoization
* Recursions
* Backtracking
* Divide and conquer (usually some kind of merge sort)

Searching
* Sorting, topological sorting
* Binary search
* Breath-first search
* Depth-first search

Using data structures
* hash tables
* trie
* suffix tree, suffix array
* segment trees

Precomputation:
* Precomputing matrices
* Prefix sums array
* KMP

Misc thinking
* Going from backwards maybe easier

## Lanugages ##

### Go ###

The problem for Go projects with svc like github is that I cannot check-in the
workspace into the svc. That means I cannot just ask you to clone the repo and
run a simple command. Instead, I need to give you my repo specific setup
instructions.

1. Create a directory for your Go workspace, e.g. ~/gows
2. Set the environment variables::

    export GOPATH=~/gows
    export PATH="$PATH:$GOPATH/bin"

3. Install the script you want, e.g. for reverseslice::

    go get github.com/hermantai/kata/go/reverseslice

4. Now you can run it::

    reverseslice

