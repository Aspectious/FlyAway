package net.eastern.FlyAway.dbm;

public enum DbmResponseType {
    ResponseEmpty,     // When there is no content in the response
    OneResponse,        // When there is only one item in the response
    ResponseList,       // When there are multiple items in the response
}
