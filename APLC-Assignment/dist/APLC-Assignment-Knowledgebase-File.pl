finder(X, [X|_]).
finder(X, [_|T]):- finder(X, T).
grade(zhenyie, a).
