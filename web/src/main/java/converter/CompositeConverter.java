package converter;

import java.util.ArrayList;
import java.util.List;

public class CompositeConverter implements Converter {
    private final List<Converter> converters = new ArrayList<>();

    @Override
    public boolean canSupport(Class<?> sourceType, Class<?> destinationType) {
        return converters.stream().anyMatch(converter -> converter.canSupport(sourceType, destinationType));
    }

    @Override
    public <T> T convert(Object source, Class<T> destinationType) {
        return converters
                .stream()
                .filter(converter -> converter.canSupport(source.getClass(), destinationType))
                .findAny()
                .map(converter -> converter.convert(source, destinationType))
                .get();
    }

    public void addConverter(Converter converter) {
        this.converters.add(converter);
    }
}
