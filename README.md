# huffman-experiment
*Looking for a huffman compression implementation? This is not it, sorry. This is in NO shape or form some thing that
anybody will find useful*

Some experiments with huffman encoding.


I came across a sorta interesting question recently.

You are given:
- a list of encoding strings (essentially the leave nodes of a huffman encoding tree minus the actual symbols)
- an encoded string
- a unit test that verfies correct decoding
- a basic API that specifies a decode method that receives the encoded string and the list of encoding strings as parmaters

So the question is: is it actually possible to make this unit test pass?
Building up the encoding tree is trivial, but without actual symbols to assign to the leaf nodes,it is 
not worth much.

The specified decode interface does not receive any hints of the alphabet, so we can't even try to build up a 
frequency table based on that (which we could maybe have used to build up the tree, or take some guesses at leaf values).

My eventual conclusion is simple that "No, this is not possible....without cheating".

So I cheated.

## How did I cheat?
Basically, for each tree in the set of unit tests, prepared a cheat file.
In this cheat file, I place the known correct testValue, with the known encoded string.
Proceed to build up the tree with provided leaf nodes.
Then basically randomly assign values to each leaf node (to which you naturally keep references), run a decode, 
and compare the decoded string with the known correct value.
Repeat until you have a match. 

Efficient and optimal? 
NO.

Does the unit test pass?
YES.

# WHY did you do this?
I can not fully disclose that information at this time. 
Probably never.

Sometimes you just have an itch that need to be scratched, ok?



