import type { PendingGoalsResponse } from "../types/pending-goals"

export async function getPendingGoals(): Promise<PendingGoalsResponse[]> {
  const response = await fetch("http://localhost:8080/api/goals/pending")
  const data = await response.json()

  return data.pendingGoals
}
