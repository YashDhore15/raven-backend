CREATE TABLE api_keys (
    id BIGSERIAL PRIMARY KEY,
    tenant_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    key_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    revoked_at TIMESTAMP,
    CONSTRAINT fk_api_keys_tenant
        FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);