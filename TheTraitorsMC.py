# -*- coding: utf-8 -*-
"""
Created on Mon Feb 12 15:25:29 2024

@author: Syed
"""

from random import sample

def simulation(n: int, m: int) -> bool:
    part = set(range(0, n + 1))
    trait = set(sample(sorted(part), m))
    faith = set(part) - set(trait)
    
    # special case
    if len(trait) > len(faith):
        return 1
    
    while len(faith) > 0 and len(trait) > 0 and len(part) > 2:
        # killing a faithful
        killed = faith.pop()
        part.remove(killed)
        
        # eleminating a random participant
        eleminated = part.pop()
        if eleminated in trait:
            trait.remove(eleminated)
        else:
            faith.remove(eleminated)
    
    return len(part - faith) == 0 # faithful win


def win_prob(n: int, m: int, sims: int) -> float:
    probs = [simulation(n, m) for _ in range(sims)]
    return round(1 - (sum(probs) / len(probs)), 4)


n = int(input("Participants: "))
m = int(input("Traitors: "))
sims = int(input("Simulations: "))
print(win_prob(n, m, sims))
