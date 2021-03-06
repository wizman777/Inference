package org.rdswitchboard.libraries.orcid;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

public class ValueDeserializer  extends JsonDeserializer<String> {

	private static final String VALUE = "value";
	
	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);
		
		if (node.isNull())
			return null;

		if (node.isValueNode())
			return node.asText();
		
		if (node.isObject()) {
			final JsonNode nodeValue = node.get(VALUE);
			if (null == nodeValue) 
				return null;
			
			return nodeValue.asText();
		}
		
		throw new JsonMappingException("Unable to parse value, the node type is " + node.getNodeType());		
	}
}
