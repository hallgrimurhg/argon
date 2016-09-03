java_binary(
    name = "argon",
    srcs = [
        "Argon.java",
        "BuiltinFunction.java",
        "Closure.java",
        "Environment.java",
        "EvalCond.java",
        "EvalDefine.java",
        "EvalIf.java",
        "EvalLambda.java",
        "EvalQuote.java",
        "Evaluator.java",
        "Function.java",
    ],
    deps = [
        ":lexer",
        ":parser",
    ],
    main_class = "argon.Argon",
)

java_library(
    name = "parser",
    srcs = [
        "Parser.java",
        "Symbol.java",
        "Pair.java",
    ],
    deps = [
        ":lexer",
    ],
    visibility = ["//visibility:public"],
)

java_library(
    name = "lexer",
    srcs = [
        "Lexer.java",
        "Token.java",
        "PeekReader.java",
    ],
    visibility = ["//visibility:public"],
)
