package com.powerreaderapi.powerreaderapi.exception;

public class EntityNotFoundException extends AppException {

    public EntityNotFoundException(String entity, String identifier) {super(String.format("Entity %s with identifier %s is not found.", entity, identifier));}

}
