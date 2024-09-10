CREATE TABLE IF NOT EXISTS goals (
   id UUID NOT NULL,
   title TEXT NOT NULL,
   desired_weekly_frequency INTEGER NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CONSTRAINT pk_goals PRIMARY KEY (id)
);