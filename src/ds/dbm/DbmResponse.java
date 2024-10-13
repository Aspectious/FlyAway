package net.eastern.FlyAway.dbm;

public class DbmResponse {
    private final DbmResponseType responseType;
    private final String[] content;
    // For when DB has no response
    public DbmResponse(DbmResponseType type) {
        if (type != DbmResponseType.ResponseEmpty) throw new IllegalStateException("Invalid DbmResponse Type");
        this.responseType = type;
        this.content = new String[1];
        this.content[0] = "null";
    }
    // For when DB has only one response
    public DbmResponse(DbmResponseType type, String content) {
        if (type != DbmResponseType.OneResponse) throw new IllegalStateException("Invalid DbmResponse Type");
        this.responseType = type;
        this.content = new String[1];
        this.content[0] = content;
    }
    // For when DB has multiple responses
    public DbmResponse(DbmResponseType type, int responseLength, String[] columns) {
        if (type != DbmResponseType.ResponseList) throw new IllegalStateException("Invalid DbmResponse Type");
        this.responseType = type;
        this.content = new String[responseLength];
        System.arraycopy(columns, 0, this.content, 0, responseLength);
    }

    public DbmResponseType getType() {
        return this.responseType;
    }
    public String[] getContentArray() {
        return this.content;
    }
}
