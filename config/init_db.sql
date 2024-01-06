CREATE TABLE resume
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT                 NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL,
    resume_uuid CHAR(36) NOT NULL REFERENCES public.resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);

CREATE TABLE section
(
    id           SERIAL,
    resume_uuid  CHAR(36) NOT NULL REFERENCES public.resume (uuid) ON DELETE CASCADE,
    text         TEXT     NOT NULL,
    section_type TEXT
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);
CREATE UNIQUE INDEX section_resume_uuid_index
    ON section (resume_uuid, section_type);


