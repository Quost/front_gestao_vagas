package io.github.mqdev.front_gestao_vagas.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FormatErrorMessage {
    
    public static String format(String message) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(message);
            if (node.isArray()){
                return format(node);
            }
            return node.asText();
        } catch (Exception e) {
            return message;
        }
    }

    public static String format(JsonNode node) {
        StringBuilder formattedMessage = new StringBuilder();

        for (JsonNode error : node) {
            formattedMessage.append(error.get("message").asText()).append("\n");
        }

        return formattedMessage.toString();
    }
}
