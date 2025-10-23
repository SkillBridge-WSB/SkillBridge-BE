-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- users
CREATE TABLE IF NOT EXISTS users (
    id          uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    email       varchar NOT NULL UNIQUE,
    password    varchar NOT NULL,
    name        varchar NOT NULL,
    bio         text,
    image_url   varchar,
    role        varchar,
    created_at  BIGINT NOT NULL
);

-- subjects (many-to-one to users)
CREATE TABLE IF NOT EXISTS subjects (
    id             uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name           varchar NOT NULL,
    user_id        uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    cost_per_hour  integer,
    availability   varchar
);
CREATE UNIQUE INDEX IF NOT EXISTS uq_subjects_user_name ON subjects(user_id, name);
CREATE INDEX IF NOT EXISTS idx_subjects_user_id ON subjects(user_id);

-- lessons (references users; subject_name kept as provided)
CREATE TABLE IF NOT EXISTS lessons (
    student_id    uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    tutor_id      uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    subject_name  varchar NOT NULL,
    time          timestamp NOT NULL,
    PRIMARY KEY (student_id, tutor_id, subject_name, time)
);
CREATE INDEX IF NOT EXISTS idx_lessons_student ON lessons(student_id);
CREATE INDEX IF NOT EXISTS idx_lessons_tutor ON lessons(tutor_id);

-- chats
CREATE TABLE IF NOT EXISTS chats (
    chat_id     uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    student_id  uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    tutor_id    uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS uq_chats_pair ON chats(student_id, tutor_id);

-- messages (FK to chats by chat_id)
CREATE TABLE IF NOT EXISTS messages (
    id       uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    message  text NOT NULL,
    time     timestamp NOT NULL DEFAULT now(),
    chat_id  uuid NOT NULL REFERENCES chats(chat_id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_messages_chat_id ON messages(chat_id);

-- calendars (many-to-one to users)
CREATE TABLE IF NOT EXISTS calendars (
    id           uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id      uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    lesson_time  timestamp NOT NULL,
    available    boolean NOT NULL DEFAULT true
);
CREATE INDEX IF NOT EXISTS idx_calendars_user_id ON calendars(user_id);
