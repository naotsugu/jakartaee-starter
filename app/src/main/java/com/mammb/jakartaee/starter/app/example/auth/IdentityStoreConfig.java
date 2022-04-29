package com.mammb.jakartaee.starter.app.example.auth;

import com.mammb.jakartaee.starter.app.DataSourceConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import java.util.Map;

@DatabaseIdentityStoreDefinition(
    dataSourceLookup = DataSourceConfig.DS_NAME,
    callerQuery = IdentityStoreConfig.CALLER_QUERY,
    groupsQuery = IdentityStoreConfig.GROUPS_QUERY,
    hashAlgorithmParameters = "${identityStoreConfig.hashAlgorithmParameters}"
)
@Named
@ApplicationScoped
public class IdentityStoreConfig {

    public static final String CALLER_QUERY = """
        select users.password
        from users
        where users.name = ?""";

    public static final String GROUPS_QUERY = """
        select groups.name
        from users
            join users_groups on users.id = users_groups.users_id
            join groups on users_groups.groups_id = groups.id
        where users.name = ?""";

    public static Map<String, String> HASH_PARAMS = Map.of(
        "Pbkdf2PasswordHash.Iterations","3072",
        "Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512",
        "Pbkdf2PasswordHash.SaltSizeBytes", "64");

    public String[] getHashAlgorithmParameters() {
        return HASH_PARAMS.entrySet().stream()
            .map(e -> e.getKey() + "=" + e.getValue()).toArray(String[]::new);
    }

}
