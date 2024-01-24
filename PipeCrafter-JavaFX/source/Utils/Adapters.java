package Utils;

import java.io.File;
import java.io.IOException;
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
import Utils.OutputParameters.Format;
import Utils.OutputParameters.OutputStream;

/** Class containing adapter classes */
public class Adapters {

    public static class SaveAdapter implements JsonSerializer<Save>, JsonDeserializer<Save> {

        @Override
        public Save deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            Save save = new Save(null);

            save.executionDirectoryPath = Utils.cwd.resolve(jsonObject.get("executionDirectoryPath").getAsString())
                    .normalize().toString();

            save.outputSavingDirectoryPath = Utils.cwd
                    .resolve(jsonObject.get("outputSavingDirectoryPath").getAsString())
                    .normalize().toString();

            save.usedShell = Business.Settings.Shell.valueOf(jsonObject.get("usedShell").getAsString());

            JsonArray jsonArray = jsonObject.getAsJsonArray("commands");

            for (JsonElement element : jsonArray) {
                try {
                    Views.Command cmdView = Business.App.getMainController().newCommandView();
                    CommandAdapter.viewOfCurrentCmd = cmdView;
                    Business.Command.addCommand(cmdView,
                            context.deserialize(element, Models.Command.class));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new JsonParseException(
                            "Error when building a command");
                }

            }

            return save;
        }

        @Override
        public JsonElement serialize(Save src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject obj = new JsonObject();
            obj.addProperty("executionDirectoryPath", src.executionDirectoryPath);
            obj.addProperty("outputSavingDirectoryPath", src.outputSavingDirectoryPath);
            obj.addProperty("usedShell", src.usedShell.toString());

            JsonArray cmdArray = new JsonArray();
            for (Command cmd : src.commands) {
                cmdArray.add(context.serialize(cmd));
            }
            obj.add("commands", cmdArray);
            return obj;
        }

    }

    public static class CommandAdapter implements JsonSerializer<Command>, JsonDeserializer<Command> {

        /** Used to pass a view as a parameter for the deserialization of a command */
        public static Views.Command viewOfCurrentCmd = null;

        @Override
        public Command deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            Models.Command commandModel = new Command();

            commandModel.setName(jsonObject.get("name").getAsString());
            commandModel.setCmd(jsonObject.get("cmd").getAsString());

            JsonArray jsonArray = jsonObject.getAsJsonArray("argumentList");

            for (JsonElement element : jsonArray) {

                Business.Argument.setAddedArg(context.deserialize(element, Models.Argument.class));

                try {
                    Business.Argument.addArgument(viewOfCurrentCmd.newArgumentView(), commandModel);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new JsonParseException(
                            "Error when building an argument for command: " + commandModel.getName());
                }

            }

            return commandModel;
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
            return obj;
        }

    }

    public static class ArgumentAdapter implements JsonSerializer<Argument>, JsonDeserializer<Argument> {

        @Override
        public Argument deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            Models.Argument.Type type = Models.Argument.Type.valueOf(jsonObject.get("type").getAsString());

            switch (type) {
                case TEXT:
                    return new Argument(type, jsonObject.get("objectValue").getAsString());
                case FILE:
                    return new Argument(type, Utils.cwd.resolve(jsonObject.get("objectValue").getAsString()).toFile());
                case INVALID:
                case OUTPUT:

                    JsonElement objectValue = jsonObject.get("objectValue");
                    if (objectValue.isJsonNull()) {
                        return new Argument(type, null);
                    }
                    Argument arg = new Argument(type, context.deserialize(objectValue, OutputParameters.class));
                    arg.getOutputParameter().getCmdToUse().getReferringArgumentList().add(arg);
                    return arg;
            }
            return null;
        }

        @Override
        public JsonElement serialize(Argument src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("type", src.getType().toString());

            switch (src.getType()) {
                case TEXT:
                    obj.addProperty("objectValue", src.getObjectValue().toString());
                    break;
                case FILE:
                    obj.addProperty("objectValue",
                            Utils.cwd.relativize(((File) src.getObjectValue()).toPath()).toString());
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

    public static class OutputParametersAdapter
            implements JsonSerializer<OutputParameters>, JsonDeserializer<OutputParameters> {

        @Override
        public OutputParameters deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            JsonObject jsonObject = json.getAsJsonObject();

            Command cmdToUse = Business.Command.getCommands().get(jsonObject.get("cmdToUse").getAsInt());

            OutputStream stream = OutputStream.valueOf(jsonObject.get("stream").getAsString());

            Format format = Format.valueOf(jsonObject.get("format").getAsString());

            return new OutputParameters(cmdToUse, stream, format);
        }

        @Override
        public JsonElement serialize(OutputParameters src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject obj = new JsonObject();

            obj.addProperty("cmdToUse", src.getCmdToUse().getIndex());
            obj.addProperty("stream", src.getStream().toString());
            obj.addProperty("format", src.getFormat().toString());

            return obj;
        }

    }

    public static class TupleAdapter implements JsonSerializer<Tuple>, JsonDeserializer<Tuple> {

        @Override
        public Tuple deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            JsonElement JSONstate = jsonObject.get("state");
            JsonElement JSONexitCode = jsonObject.get("exitCode");

            return new Tuple<Models.Command.State, Integer>(
                    Models.Command.State.valueOf(JSONstate.isJsonNull() ? null : JSONstate.getAsString()),
                    JSONexitCode.isJsonNull() ? null : JSONexitCode.getAsInt());
        }

        @Override
        public JsonElement serialize(Tuple src, Type typeOfSrc, JsonSerializationContext context) {

            JsonObject obj = new JsonObject();
            if (src.first != null) {
                obj.addProperty("state", src.first.toString());
            } else {
                obj.addProperty("state", (String) null);
            }
            if (src.second != null) {
                obj.addProperty("exitCode", src.second.toString());
            } else {
                obj.addProperty("exitCode", (String) null);
            }

            return obj;
        }

    }
}
