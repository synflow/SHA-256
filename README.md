sha-256
=======

This is a sample implementation of SHA-256 written with the [Cx programming language](http://cx-lang.org) by [Synflow](https://www.synflow.com). It demonstrates how easy it is to write Cx code straight from a standard, and how to gradually optimize performance of the design.

The repository contains three implementations:

1. The basic implementation is not optimized at all, it uses a big array of 64x32 bits to store the preprocessed message W.
2. The first step to optimize was to move the "K" array into a separate task to make it a ROM. 
3. The second step was to replace the big W array by a shift register, which is 4x smaller, and requires much less logic around it (fewer muxes).

To create a much more optimized implementation (at the expense of a huge increase in area), one could unroll the main "for" loop and transform it to a big pipeline to produce one hash every cycle.
