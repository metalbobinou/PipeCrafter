package Utils;

import java.io.File;
import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import Models.Argument;
import Models.Command;

/** Class containing adapter classes */
public class Adapters {

    public static class CommandAdapter implements JsonSerializer<Command>, JsonDeserializer<Command> {

        @Override
        public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'deserialize'");
        }

        @Override
        public JsonElement serialize(Command src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject obj = new JsonObject();
            obj.addProperty("name", src.getName());
            obj.addProperty("position", src.getPosition());
            obj.addProperty("cmd", src.getCmd());

            JsonArray argArray = new JsonArray();
            for (Argument arg : src.getArgumentList()) {
                argArray.add(context.serialize(arg));
            }
            obj.add("argumentList", argArray);
            obj.addProperty("exitCode", src.getExitCode());
            return obj;
        }

    }

    public static class ArgumentAdapter implements JsonSerializer<Argument>, JsonDeserializer<Argument> {

        @Override
        public Argument deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'deserialize'");
        }

        @Override
        public JsonElement serialize(Argument src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("type", src.getType().toString());

            switch (src.getType()) {
                case TEXT:
                case FILE:
                    obj.addProperty("objectValue", src.getObjectValue().toString());
                    break;
                case INVALID:
                case OUTPUT:
                    if (src.getObjectValue() instanceof OutputParameters) {
                        JsonObject opJson = new JsonObject();
                        OutputParameters op = src.getOutputParameter();

                        opJson.addProperty("cmdToUse", op.getCmdToUse().getIndex());
                        opJson.addProperty("stream", op.getStream().toString());
                        opJson.addProperty("format", op.getFormat().toString());

                        obj.add("objectValue", opJson);
                    }
                    break;
            }
            return obj;
        }
    }
}
