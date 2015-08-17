package com.dmi.devbook.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import retrofit.mime.TypedString;

public class TextConverter implements Converter {

    private static final int BUFFER_SIZE = 1024;

    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        final String charset;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType());
        } else {
            charset = "UTF-8";
        }

        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(body.in(), charset);

            final StringBuilder builder = new StringBuilder();
            final char[] buffer = new char[BUFFER_SIZE];

            int read;
            while ((read = reader.read(buffer)) > 0) {
                builder.append(buffer, 0, read);
            }

            return builder.toString();
        } catch (IOException e) {
            throw new ConversionException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        return new TypedString(object.toString());
    }
}
