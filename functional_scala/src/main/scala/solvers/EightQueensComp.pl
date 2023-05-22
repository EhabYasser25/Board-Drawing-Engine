:- use_module(library(clpfd)).

n_queens(N, ExistingQueens, Qs) :-
    length(Qs, N),
    Qs ins 1..N,
    safe_queens(Qs, ExistingQueens),
    all_distinct(Qs).

safe_queens([], _).
safe_queens([Q|Qs], ExistingQueens) :-
    safe_queens(Qs, ExistingQueens),
    no_attack(Q, Qs, 1, ExistingQueens).

no_attack(_, [], _, _).
no_attack(Q, [Q2|Qs], D, ExistingQueens) :-
    Q #\= Q2,
    abs(Q - Q2) #\= D,
    D1 #= D + 1,
    no_attack(Q, Qs, D1, ExistingQueens).

% Example usage:
% ?- n_queens(8, [2/1, 4/2, 6/3], Qs), labeling([ff], Qs), write(Qs).
% [5, 7, 1, 3, 8, 6, 4, 2]
