package servlet;

public class ExampleController {
    @RequestMapping(path = "/greeting", supportedMethods = HttpRequest.HttpRequestMethod.GET)
    public HttpResponse greeting() {
        return HttpResponse.fromPlanText("Hello World!");
    }

    @RequestMapping(path = "/users/(?<username>\\w+)", supportedMethods = HttpRequest.HttpRequestMethod.GET)
    public HttpResponse greetUser(@HttpPathVariable("username") String username) {
        return HttpResponse.fromPlanText(String.format("Hello %s!", username));
    }

    @RequestMapping(
            path = "/users",
            supportedMethods = {HttpRequest.HttpRequestMethod.GET, HttpRequest.HttpRequestMethod.POST}
    )
    public @HttpResponseBody(encoder = HttpBodyTextConverter.class)
    String greetUserAgain(@HttpRequestParam("username") String username) {
        return String.format("Hello %s!", username);
    }

    @RequestMapping(
            path = "/users",
            supportedMethods = HttpRequest.HttpRequestMethod.POST
    )
    public @HttpResponseBody(encoder = HttpBodyJsonConverter.class)
    User greetUser(@HttpRequestBody(decoder = HttpBodyJsonConverter.class) User user) {
        return user;
    }
}
