# Raven — Entity Reference

> Living reference for Raven's entities, attributes, data types, and relationships.
> Updated as the project is built.

---

# Tenant

### Purpose

Represents a customer/account using Raven.

---

### Attributes

#### id

- Java Type: Long
- Database Type: BIGSERIAL
- Nullable: No
- Description: Primary key.

#### name

- Java Type: String
- Database Type: VARCHAR(255)
- Nullable: No
- Description: Name of the tenant.

#### email

- Java Type: String
- Database Type: VARCHAR(255)
- Nullable: No
- Constraints: UNIQUE
- Description: Tenant's login/contact email.

#### passwordHash

- Java Type: String
- Database Type: VARCHAR(255)
- Nullable: No
- Description: Hashed tenant password. Never stores the plain-text password.

#### createdAt

- Java Type: LocalDateTime
- Database Type: TIMESTAMP
- Nullable: No
- Description: When the tenant was created.

#### updatedAt

- Java Type: LocalDateTime
- Database Type: TIMESTAMP
- Nullable: No
- Description: When the tenant was last updated.

#### deletedAt

- Java Type: LocalDateTime
- Database Type: TIMESTAMP
- Nullable: Yes
- Description: When the tenant was soft-deleted.

---

### Relationships

> None yet.

Relationships will be added as they are implemented.

---

### Database Table

tenants

### Current Structure

Tenant
│
├── id              → Long
├── name            → String
├── email           → String
├── passwordHash    → String
├── createdAt       → LocalDateTime
├── updatedAt       → LocalDateTime
└── deletedAt       → LocalDateTime

