Argon is a toy LISP interpreter written in Java.

## Features

Argon supports the following features:
- Lexical scoping
- Functions: `define` and `lambda`
- Control flow: `if` and `cond`
- List processing: `cons`, `list`, `car`, and `cdr`
- Basic arithmetic: `+`, `-`, `<`, and `=`

## Data types

Argon supports the following data types:
- Integers, e.g. `10` and `-5`
- Booleans, using `#t` for true and `#f` for false
- Strings, e.g. `"abc"`
- Symbols, e.g. `foo`
- Lists, e.g. `(10 20 30)`
- Empty list, denoted by `()` or `null`

## Examples

```scheme
Welcome to Argon!
> 10
10
> (+ 1 2 3)
6
> (define c 100)
100
> (+ c 10)
110
> (define (fib n) (if (< n 2) 1 (+ (fib (- n 1)) (fib (- n 2)))))
#<procedure:fib>
> (fib 10)
89
> (define fib (lambda (n) (if (< n 2) 1 (+ (fib (- n 1)) (fib (- n 2))))))
#<procedure>
> (fib 10)
89
> ((lambda (x) (+ x 1)) 5)
6
> (define x (list 1 2 3))
(1 2 3)
> (car x)
1
> (cdr x)
(2 3)
```
