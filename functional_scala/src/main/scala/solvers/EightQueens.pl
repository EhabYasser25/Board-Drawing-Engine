% queens8.pl

:- dynamic initial_board/1.
:- dynamic solution/1.

read_board(File, Board) :-
    open(File, read, Stream),
    read_lines(Stream, Lines),
    close(Stream),
    convert_lines(Lines, Board).

read_lines(Stream, Lines) :-
    read_line(Stream, Line),
    (Line == end_of_file -> Lines = [] ;
        read_lines(Stream, Rest),
        Lines = [Line | Rest]).

read_line(Stream, Line) :-
    read_line_to_codes(Stream, Codes),
    atom_codes(Atom, Codes),
    atomic_list_concat(Atoms, ' ', Atom),
    maplist(atom_number, Atoms, Line).

convert_lines(Lines, Board) :-
    maplist(convert_line, Lines, Board).

convert_line(Line, Row) :-
    maplist(convert_entry, Line, Row).

convert_entry(0, 0).  % Convert '0' to 0
convert_entry(_, 1).  % Convert any other value to 1

convert_entry(0, 0).
convert_entry(_, 1).

% Solve the 8-queens problem
solve :-
    consult('queens8.pl'),
    initial_board(Board),
    solve(Board),
    retractall(initial_board(_)).

% Solve the 8-queens problem given the initial board
solve(Board) :-
    length(Board, N),
    length(Solution, N),
    queens(Board, Solution),
    assertz(solution(Solution)).

% Predicate to check if a position is safe for a queen
safe(_, []).
safe(X, [Y | Others]) :-
    X =\= Y,
    X - Y =\= 0,
    X + Y =\= 0,
    safe(X, Others).

% Predicate to place queens on the board
queens([], []).
queens([X | Others], [Y | Solution]) :-
    queens(Others, Solution),
    member(Y, [1, 2, 3, 4, 5, 6, 7, 8]),
    safe(X, Solution).

% Save the solution to a file
save_solution(File) :-
    open(File, write, Stream),
    solution(Solution),
    save_solution(Stream, Solution),
    close(Stream).

% Save the solution to a stream
save_solution(_, []).
save_solution(Stream, [X | Others]) :-
    write(Stream, X),
    write(Stream, ' '),
    save_solution(Stream, Others).
