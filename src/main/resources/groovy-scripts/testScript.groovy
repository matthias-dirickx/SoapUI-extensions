toCheck = returnSomeText(argument);

private String returnSomeText(String varFromBinding) {
    return "This is some text from a $argument function"
}

assert toCheck.equals("This is some text from a Groovy function")

