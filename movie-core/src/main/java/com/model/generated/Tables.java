/*
 * This file is generated by jOOQ.
*/
package com.model.generated;


import com.model.generated.tables.SchemaVersion;
import com.model.generated.tables.User;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>schema_version</code>.
     */
    public static final SchemaVersion SCHEMA_VERSION = com.model.generated.tables.SchemaVersion.SCHEMA_VERSION;

    /**
     * The table <code>user</code>.
     */
    public static final User USER = com.model.generated.tables.User.USER;
}
