# CSC 207: Text Editor REDO

**Author**: Finn Rowles

## Resources Used

+ Oracle String Library Standard Edition 8
+ Oracle Array Library Standard Edition 8
+ Lab 5 Referenced to review testing.
+ Owen Block, general discussion of the concepts
+ Emily Lackershire,  general discussion of the concepts
+ Apache NetBeans
+ Professor Osera's Wesbite and the lab description.
+ Java version 17.0.14
+ Lanterna and its relevant libraries
+ Owen B and Emily L for UI issues on a high level


## Changelog

REDO FIXES:

---
Now passes all the autograder tests.
Project runs and UI is present. 
The program is also visually correct, with a working cursor. Saves and reads from files.
No checkstyle violations, and test suite is more comprehensive.
---

insert Analysis:
relevant input(s) : char ch, a character to be added
string: a string, which may or may not end up being split

Critical operations: string + ch (concatonation; O(1) time)
substringing: O(n) time, depending on size. 
{string.substring(0, curPos) + ch + 
                   string.substring(curPos, sz); }

Mathematical model: derivation :

T(n) = (sz-pos)/sz * n + 1 + |(pos-sz)|/sz * n

T(n) = n + 1 (worst case)

Big O : T(n)

Justification: since Java strings are immutable, to simulate a text editor
we will need to "split" strings by using substring at the cursor's position.
We'll create a "before cursor" and "after cursor" component to place our char between.
Then, we need to concatonate the first part, char, and last part.
Creating these two strings, in total, requires *n* steps through the string.
Then, concatonation is constant (+1). 
