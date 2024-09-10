CREATE TABLE goal_completions (
  id UUID NOT NULL,
   goal_id UUID NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CONSTRAINT pk_goal_completions PRIMARY KEY (id)
);

ALTER TABLE goal_completions ADD CONSTRAINT FK_GOAL_COMPLETIONS_ON_GOAL FOREIGN KEY (goal_id) REFERENCES goals (id);