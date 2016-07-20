/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Admin
 */
public class EnvelopeUtil {

    private String payload;
    private String type;

    public EnvelopeUtil() {
    }
    
    public EnvelopeUtil(String type, String payload) {
        this.setPayload(payload);
        this.setType(type);
    }
    
    public EnvelopeUtil(String type, Object payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);
        
        this.setPayload(json);
        this.setType(type);
    }

    public String getPayload() {
        return this.payload;
    }

    public final void setPayload(String Payload) {
        this.payload = Payload;
    }

    public String getType() {
        return this.type;
    }

    public final void setType(String Type) {
        this.type = Type;
    }
    
    
    public String toJSON() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return "EnvelopeUtil [type=" + this.type + ", payload=" + this.payload + "]";
    }
}
