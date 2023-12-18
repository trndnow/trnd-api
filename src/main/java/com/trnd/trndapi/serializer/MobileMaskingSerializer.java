package com.trnd.trndapi.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MobileMaskingSerializer extends JsonSerializer<String> {
    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param value       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     *                    serializing Objects value contains, if any.
     */
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(maskMobileNumber(value));
    }
    private String maskMobileNumber(String mobileNumber) {
        if (mobileNumber == null || mobileNumber.length() <= 9) {
            return mobileNumber;
        }
        // Keeping the first 2 and last 2 characters visible
        String visibleStart = mobileNumber.substring(0, 3);
        String visibleEnd = mobileNumber.substring(mobileNumber.length() - 3);

        return visibleStart + "****" + visibleEnd;
    }
}
