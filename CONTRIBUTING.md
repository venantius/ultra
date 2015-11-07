# How to Contribute

Got something to make Ultra even more awesome? Radical. Let's talk about how to get the most mileage out of it.

## Filing Issues

Ultra is composed of multiple dependencies, features, and code paths. When filing an issue, particularly related to bugs or performance problems, it's really helpful to narrow down which component of Ultra is responsible for the problem in question. Please [selectively disable](https://github.com/venantius/ultra#configuration) Ultra's features until you figure out which of the main four features is responsible for the behavior you're reporting.

In addition, it's helpful to include the following information:
 - Your JDK version
 - Your Leiningen version
 - Which version of Ultra you're using

## Tests

I will be the first person to admit that this library is quite short on test coverage. That has become something of a problem over time, with PRs being submitted with breaking changes in other, unexpected areas. My policy going forward is going to be to require tests as part of a pull request; I simply can't keep accepting pull requests that I have to go back and re-write later because the original author didn't test the new behavior thoroughly enough.
