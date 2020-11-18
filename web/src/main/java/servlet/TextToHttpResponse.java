package servlet;

import converter.TypeSpecificConverter;

public class TextToHttpResponse implements TypeSpecificConverter<String, HttpResponse> {
    @Override
    public HttpResponse convert(String text) {
        return HttpResponse.fromPlanText(text);
    }
}
