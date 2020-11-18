package servlet;

import converter.TypeSpecificConverter;

public interface HttpResponseBodyEncoder<DTO> extends TypeSpecificConverter<DTO, HttpResponse> {
    HttpResponse convert(DTO dto);
}
