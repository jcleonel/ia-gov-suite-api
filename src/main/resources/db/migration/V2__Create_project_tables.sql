CREATE TYPE risk_level AS ENUM ('LOW', 'MEDIUM', 'HIGH');

CREATE TABLE analysis_project (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    client_name VARCHAR(255),
    user_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE document_file (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    analysis_project_id UUID NOT NULL UNIQUE,
    original_filename VARCHAR(255) NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    file_size_bytes BIGINT NOT NULL,
    storage_key VARCHAR(1024) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_analysis_project
        FOREIGN KEY(analysis_project_id)
        REFERENCES analysis_project(id)
        ON DELETE CASCADE
);

CREATE TABLE annotation (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    analysis_project_id UUID NOT NULL,
    risk_level risk_level NOT NULL,
    selected_text TEXT,
    recommendation TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_analysis_project
        FOREIGN KEY(analysis_project_id)
        REFERENCES analysis_project(id)
        ON DELETE CASCADE
);

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON analysis_project
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TRIGGER set_timestamp
BEFORE UPDATE ON annotation
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();