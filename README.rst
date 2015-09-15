kata
====
For practice coding

Originally, it will include algorithm exercises for the following languages:

* Go
* Python

Go
---
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
