package io.amigable.wfengine.service;

/**
 * Created by capra on 18-06-2018.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public final class JsonSchemaValidator {


    public static boolean isValidInput(String jsonInput) throws IOException, ProcessingException{

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Reader validateSchemaStream = new InputStreamReader(loader.getResourceAsStream("inSchema.json"));
        final JsonNode validateSchema = JsonLoader.fromReader(validateSchemaStream);
        final JsonNode inputJSONEntry = JsonLoader.fromString(jsonInput);
        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        final JsonSchema schema = factory.getJsonSchema(validateSchema);
        final ProcessingReport report = schema.validate(inputJSONEntry);

        return report.isSuccess();
    }

}
