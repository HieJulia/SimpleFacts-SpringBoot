package com.boot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is responsible for allowing us to categorise data by what class it
 * refers to.
 * For example, if we had a serialised string of an instanced Message object and
 * an instanced User object, we would be able to wrap both of them up and put
 * them in an Envelope which will allow us to know what the data (payload) is,
 * and what class is responsible for controlling the data.
 * 
 */
public class EnvelopeUtil {

    /**
     * This string stores the data we are storing as JSON. 
     */
    private String payload;
    /**
     * This string stores the name of the class or the categorising name that
     * allows us to know what the data we are handling is.
     */
    private String type;

    /**
     * @constructor
     */
    public EnvelopeUtil() {
    }
    
    /**
     * @constructor
     * @param type - What class does this data belong to
     * @param payload - What is the data that we want to store
     */
    public EnvelopeUtil(String type, String payload) {
        this.setPayload(payload);
        this.setType(type);
    }
    
    /**
     * This method is an overload of the above constructor.
     * 
     * This method is called when the passed payload is an object rather than
     * a string. This means that the method will automatically serialise the
     * string.
     * 
     * @constructor
     * @param type - What class does this data belong to
     * @param payload - What is the data that we want to store
     * @throws JsonProcessingException - If the conversion to JSON failed
     */
    public EnvelopeUtil(String type, Object payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);
        
        this.setPayload(json);
        this.setType(type);
    }
    
    /**
     * 
     * @return {@link EnvelopeUtil#payload}
     */
    public String getPayload() {
        return this.payload;
    }

    /**
     * 
     * @param Payload {@link EnvelopeUtil#payload}
     */
    public final void setPayload(String Payload) {
        this.payload = Payload;
    }

    /**
     * 
     * @return {@link EnvelopeUtil#type}
     */
    public String getType() {
        return this.type;
    }

    /**
     * 
     * @param Type {@link EnvelopeUtil#type}
     */
    public final void setType(String Type) {
        this.type = Type;
    }
    
    /**
     * This method returns a serialised version of this object as a String.
     * 
     * @return Serialised string of this object
     * @throws JsonProcessingException - If the conversion to JSON failed
     */
    public String toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return "EnvelopeUtil [type=" + this.type + ", payload=" + this.payload + "]";
    }
}