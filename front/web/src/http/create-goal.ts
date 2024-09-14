import type { CreateGoalRequest } from "../types/create-goal-request"

export async function createGoal({ title, desiredWeeklyFrequency }: CreateGoalRequest) {
  await fetch("http://localhost:8080/api/goals", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      title,
      desiredWeeklyFrequency
    })
  })
}
