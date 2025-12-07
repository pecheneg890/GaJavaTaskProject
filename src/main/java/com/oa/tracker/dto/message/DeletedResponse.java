package com.oa.tracker.dto.message;

public class DeletedResponse extends AbstractApiMessage {
    public DeletedResponse() {
        super("Entity deleted!");
    }
}